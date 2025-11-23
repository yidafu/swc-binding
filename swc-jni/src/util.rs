use jni::{objects::{JObject, JString}, sys::jstring, JNIEnv};
use serde::de::DeserializeOwned;
use std::{
    any::type_name,
    panic::{catch_unwind, AssertUnwindSafe},
};
use swc_core::base::config::ErrorFormat;
use swc_core::base::{try_with_handler, HandlerOpts, TransformOutput};
use swc_core::common::{errors::Handler, sync::Lrc, SourceMap, GLOBALS};
use swc_core::ecma::ast::Program;
use thiserror::Error;

use anyhow::{anyhow, Error};

use anyhow::Context;
use tracing::instrument;

/// Safely get a Java string, returning an error instead of panicking
pub(crate) fn get_java_string_safe(env: &mut JNIEnv, jstring: &JString) -> Result<String, String> {
    env.get_string(jstring)
        .map(|jstr| jstr.into())
        .map_err(|e| format!("Failed to get Java string: {:?}", e))
}

/// Get Java string and throw exception on error, returning default JString
pub(crate) fn get_java_string_or_throw(env: &mut JNIEnv, jstring: &JString) -> Option<String> {
    match get_java_string_safe(env, jstring) {
        Ok(s) => Some(s),
        Err(e) => {
            let _ = env.throw(e);
            None
        }
    }
}

/// Get Java string for async context (can't throw exception, just return None)
pub(crate) fn get_java_string_async(env: &mut JNIEnv, jstring: &JString) -> Option<String> {
    get_java_string_safe(env, jstring).ok()
}

/// Setup async callback: get JVM and create global reference to callback
pub(crate) fn setup_async_callback(
    env: &mut JNIEnv,
    callback: JObject,
) -> Option<(jni::JavaVM, jni::objects::GlobalRef)> {
    let jvm = env.get_java_vm().ok()?;
    let callback_ref = env.new_global_ref(callback).ok()?;
    Some((jvm, callback_ref))
}

/// Execute work with panic protection and convert panic to error
pub(crate) fn execute_with_panic_protection<F, T, E>(
    work: F,
    error_msg: &str,
) -> Result<T, E>
where
    F: FnOnce() -> Result<T, E> + std::panic::UnwindSafe,
    E: From<String>,
{
    match std::panic::catch_unwind(std::panic::AssertUnwindSafe(work)) {
        Ok(result) => result,
        Err(_) => Err(error_msg.to_string().into()),
    }
}

pub type SwcResult<T> = std::result::Result<T, SwcException>;

#[derive(Debug, Error)]
pub enum SwcException {
    #[error("swc fail: {msg}")]
    SwcAnyException { msg: String },
}

pub trait MapErr<T>: Into<Result<T, anyhow::Error>> {
    fn convert_err(self) -> SwcResult<T> {
        self.into().map_err(|err| {
            // Format complete error stack information
            let mut error_msg = format!("{:?}", err);
            
            // If error has a cause chain, add complete stack
            if let Some(source) = err.source() {
                error_msg.push_str(&format!("\n\nCaused by:\n    {}", source));
                let mut current = source.source();
                while let Some(cause) = current {
                    error_msg.push_str(&format!("\n    {}", cause));
                    current = cause.source();
                }
            }
            
            SwcException::SwcAnyException { msg: error_msg }
        })
    }
}

impl<T> MapErr<T> for Result<T, anyhow::Error> {}

#[instrument(level = "trace", skip_all)]
pub fn try_with<F, Ret>(
    cm: Lrc<SourceMap>,
    skip_filename: bool,
    _error_format: ErrorFormat,
    op: F,
) -> Result<Ret, Error>
where
    F: FnOnce(&Handler) -> Result<Ret, Error>,
{
    GLOBALS.set(&Default::default(), || {
        try_with_handler(
            cm,
            HandlerOpts {
                skip_filename,
                ..Default::default()
            },
            |handler| {
                //
                let result = catch_unwind(AssertUnwindSafe(|| op(handler)));

                let p = match result {
                    Ok(v) => return v,
                    Err(v) => v,
                };

                if let Some(s) = p.downcast_ref::<String>() {
                    Err(anyhow!("failed to handle: {}", s))
                } else if let Some(s) = p.downcast_ref::<&str>() {
                    Err(anyhow!("failed to handle: {}", s))
                } else {
                    Err(anyhow!("failed to handle with unknown panic message"))
                }
            },
        )
        .map_err(|e| anyhow::anyhow!("Error: {:?}", e))
    })
}

pub fn deserialize_json<T>(json: &str) -> Result<T, serde_json::Error>
where
    T: DeserializeOwned,
{
    // Use serde_json_path_to_error to get more detailed error path information
    // It provides specific error paths to help locate issues
    let result: Result<T, _> = serde_json_path_to_error::from_str(json);
    
    // Convert error to serde_json::Error (preserving path information)
    match result {
        Ok(_) => Ok(result.unwrap()),
        Err(e) => Err(e.into_inner())
    }
}

pub fn get_deserialized<T, B>(buffer: B) -> SwcResult<T>
where
    T: DeserializeOwned,
    B: AsRef<[u8]>,
{
    let mut deserializer = serde_json::Deserializer::from_slice(buffer.as_ref());
    deserializer.disable_recursion_limit();

    let v = T::deserialize(&mut deserializer)
        .with_context(|| {
            format!(
                "Failed to deserialize buffer as {}\nJSON: {}",
                type_name::<T>(),
                String::from_utf8_lossy(buffer.as_ref())
            )
        })
        .convert_err()?;

    Ok(v)
}

pub(crate) fn process_result(mut env: JNIEnv, result: Result<Program, SwcException>) -> jstring {
    // https://github.com/jni-rs/jni-rs/issues/76
    match result {
        Ok(program) => {
            let ast_json = match serde_json::to_string(&program) {
                Ok(json) => json,
                Err(e) => {
                    let error_msg = format!("Failed to serialize program: {}", e);
                    let _ = env.throw(error_msg);
                    return JString::default().into_raw();
                }
            };

            match env.new_string(ast_json) {
                Ok(output) => output.into_raw(),
                Err(e) => {
                    let error_msg = format!("Couldn't create java string: {:?}", e);
                    let _ = env.throw(error_msg);
                    JString::default().into_raw()
                }
            }
        }
        Err(e) => {
            match e {
                SwcException::SwcAnyException { msg } => {
                    let _ = env.throw(msg);
                }
            }
            JString::default().into_raw()
        }
    }
}

pub(crate) fn process_output(
    mut env: JNIEnv,
    result: Result<TransformOutput, SwcException>,
) -> jstring {
    // https://github.com/jni-rs/jni-rs/issues/76
    match result {
        Ok(output) => {
            let ast_json = match serde_json::to_string(&output) {
                Ok(json) => json,
                Err(e) => {
                    let error_msg = format!("Failed to serialize output: {}", e);
                    let _ = env.throw(error_msg);
                    return JString::default().into_raw();
                }
            };

            match env.new_string(ast_json) {
                Ok(output) => output.into_raw(),
                Err(e) => {
                    let error_msg = format!("Couldn't create java string: {:?}", e);
                    let _ = env.throw(error_msg);
                    JString::default().into_raw()
                }
            }
        }
        Err(e) => {
            match e {
                SwcException::SwcAnyException { msg } => {
                    let _ = env.throw(msg);
                }
            }
            JString::default().into_raw()
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use serde::Deserialize;

    #[derive(Debug, Deserialize, PartialEq)]
    struct TestStruct {
        name: String,
        value: i32,
    }

    #[test]
    fn test_deserialize_json_valid() {
        let json = r#"{"name": "test", "value": 42}"#;
        let result: Result<TestStruct, _> = deserialize_json(json);
        assert!(result.is_ok());
        let data = result.unwrap();
        assert_eq!(data.name, "test");
        assert_eq!(data.value, 42);
    }

    #[test]
    fn test_deserialize_json_deep_nesting() {
        // Test deep nesting - serde_json recursion limit is disabled
        let deep_json = (0..100).fold(String::from("1"), |acc, _| format!("[{}]", acc));
        let result: Result<serde_json::Value, _> = deserialize_json(&deep_json);
        assert!(result.is_ok());
    }

    #[test]
    fn test_get_deserialized_valid() {
        let json = r#"{"name": "test", "value": 42}"#;
        let result: SwcResult<TestStruct> = get_deserialized(json.as_bytes());
        assert!(result.is_ok());
        let data = result.unwrap();
        assert_eq!(data.name, "test");
        assert_eq!(data.value, 42);
    }

    #[test]
    fn test_get_deserialized_invalid() {
        let invalid_json = r#"{"name": "test", "value": }"#;
        let result: SwcResult<TestStruct> = get_deserialized(invalid_json.as_bytes());
        assert!(result.is_err());
    }

    #[test]
    fn test_map_err_conversion() {
        let error_result: Result<i32, anyhow::Error> = Err(anyhow!("test error"));
        let converted = error_result.convert_err();
        assert!(converted.is_err());
        match converted {
            Err(SwcException::SwcAnyException { msg }) => {
                assert!(msg.contains("test error"));
            }
            _ => panic!("Expected SwcAnyException"),
        }
    }

    #[test]
    fn test_map_err_ok_value() {
        let ok_result: Result<i32, anyhow::Error> = Ok(42);
        let converted = ok_result.convert_err();
        assert!(converted.is_ok());
        assert_eq!(converted.unwrap(), 42);
    }

    #[test]
    fn test_deserialize_json_invalid() {
        let invalid_json = r#"{"name": "test", "value": }"#;
        let result: Result<TestStruct, _> = deserialize_json(invalid_json);
        assert!(result.is_err());
    }

    #[test]
    fn test_deserialize_json_empty_string() {
        let empty_json = "";
        let result: Result<TestStruct, _> = deserialize_json(empty_json);
        assert!(result.is_err());
    }

    #[test]
    fn test_deserialize_json_missing_fields() {
        let incomplete_json = r#"{"name": "test"}"#;
        let result: Result<TestStruct, _> = deserialize_json(incomplete_json);
        assert!(result.is_err());
    }

    #[test]
    fn test_get_deserialized_empty_buffer() {
        let empty_buffer = b"";
        let result: SwcResult<TestStruct> = get_deserialized(empty_buffer);
        assert!(result.is_err());
    }

    #[test]
    fn test_get_deserialized_malformed_json() {
        let malformed = b"{name: test}";
        let result: SwcResult<TestStruct> = get_deserialized(malformed);
        assert!(result.is_err());
    }

    #[test]
    fn test_map_err_with_context() {
        let error_result: Result<i32, anyhow::Error> = Err(anyhow!("original error").context("additional context"));
        let converted = error_result.convert_err();
        assert!(converted.is_err());
        match converted {
            Err(SwcException::SwcAnyException { msg }) => {
                assert!(msg.contains("original error"));
                assert!(msg.contains("additional context"));
            }
            _ => panic!("Expected SwcAnyException"),
        }
    }
}

use jni::{objects::JString, sys::jstring, JNIEnv};
use serde::de::DeserializeOwned;
use std::{
    any::type_name,
    panic::{catch_unwind, AssertUnwindSafe},
};
use swc::{config::ErrorFormat, try_with_handler, HandlerOpts, TransformOutput};
use swc_common::{errors::Handler, sync::Lrc, SourceMap, GLOBALS};
use swc_ecma_ast::Program;
use thiserror::Error;

use anyhow::{anyhow, Error};

use anyhow::Context;
use tracing::instrument;

pub type SwcResult<T> = std::result::Result<T, SwcException>;

#[derive(Debug, Error)]
pub enum SwcException {
    #[error("swc fail: {msg}")]
    SwcAnyException { msg: String },
}

pub trait MapErr<T>: Into<Result<T, anyhow::Error>> {
    fn convert_err(self) -> SwcResult<T> {
        self.into().map_err(|err| SwcException::SwcAnyException {
            msg: format!("{:?}", err),
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
    let mut deserializer = serde_json::Deserializer::from_str(json);
    deserializer.disable_recursion_limit();

    T::deserialize(&mut deserializer)
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
            let ast_json = serde_json::to_string(&program).unwrap();

            let output = env
                .new_string(ast_json)
                .expect("Couldn't create java string!");
            output.into_raw()
        }
        Err(e) => {
            match e {
                SwcException::SwcAnyException { msg } => {
                    // env.throw(("dev/yidafu/swc/SwcException", msg.to_string())).unwrap();
                    env.throw(msg).unwrap();
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
            let ast_json = serde_json::to_string(&output).unwrap();

            let output = env
                .new_string(ast_json)
                .expect("Couldn't create java string!");
            output.into_raw()
        }
        Err(e) => {
            match e {
                SwcException::SwcAnyException { msg } => {
                    // env.throw(("dev/yidafu/swc/SwcException", msg.to_string())).unwrap();
                    env.throw(msg).unwrap();
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
}

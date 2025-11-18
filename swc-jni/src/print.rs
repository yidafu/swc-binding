use std::error::Error;
use std::thread;

use jni::objects::{JClass, JObject, JString};
use jni::sys::jstring;
use jni::JNIEnv;
use jni_fn::jni_fn;

use swc::config::{Options, SourceMapsConfig};
use swc::{PrintArgs, TransformOutput};
use swc_common::GLOBALS;
use swc_ecma_ast::Program;

use anyhow::Context;
use crate::async_utils::callback_java;
use crate::util::{deserialize_json, get_deserialized, process_output, MapErr, SwcException, SwcResult};

use crate::get_compiler;

#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn printSync(mut env: JNIEnv, _: JClass, program: JString, options: JString) -> jstring {
    let program_str = match crate::util::get_java_string_or_throw(&mut env, &program) {
        Some(s) => s,
        None => return JString::default().into_raw(),
    };
    let opts = match crate::util::get_java_string_or_throw(&mut env, &options) {
        Some(s) => s,
        None => return JString::default().into_raw(),
    };

    let result = perform_print_sync_work(&program_str, &opts);
    process_output(env, result)
}

/// Helper function to perform synchronous print work
fn perform_print_sync_work(program_str: &str, opts: &str) -> SwcResult<TransformOutput> {
    let c = get_compiler();

    let program: Program = deserialize_json(program_str)
        .map_err(|e| {
            // Format complete error stack information, including serde_json error path
            let error_str = format!("{}", e);
            let mut error_msg = format!("Failed to deserialize program\n{:?}", e);
            
            // serde_json::Error's Display implementation includes path information
            if !error_str.is_empty() {
                error_msg.push_str(&format!("\n\nError details: {}", error_str));
            }
            
            // If error has a cause chain, add complete stack
            if let Some(source) = e.source() {
                error_msg.push_str(&format!("\n\nCaused by:\n    {}", source));
                let mut current = source.source();
                while let Some(cause) = current {
                    error_msg.push_str(&format!("\n    {}", cause));
                    current = cause.source();
                }
            }
            
            anyhow::anyhow!(error_msg)
        })
        .context("Failed to deserialize program")
        .convert_err()?;

    let options: Options = get_deserialized(opts)?;

    // Defaults to es3
    let _codegen_target = options.codegen_target().unwrap_or_default();
    let result = GLOBALS.set(&Default::default(), || {
        let print_args = PrintArgs {
            output_path: options.output_path,
            inline_sources_content: true,
            source_map: options
                .source_maps
                .clone()
                .unwrap_or(SourceMapsConfig::Bool(false)),
            ..Default::default()
        };
        c.print(&program, print_args).map_err(|e| {
            // Format complete error stack information
            let mut error_msg = format!("Print failed\n{:?}", e);
            
            // If error has a cause chain, add complete stack
            if let Some(source) = e.source() {
                error_msg.push_str(&format!("\n\nCaused by:\n    {}", source));
                let mut current = source.source();
                while let Some(cause) = current {
                    error_msg.push_str(&format!("\n    {}", cause));
                    current = cause.source();
                }
            }
            
            SwcException::SwcAnyException { msg: error_msg }
        })
    });
    result
}

/// Async print method - uses callback
#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn printAsync(
    mut env: JNIEnv,
    _: JClass,
    program: JString,
    options: JString,
    callback: JObject,
) {
    let Some((jvm, callback_ref)) = crate::util::setup_async_callback(&mut env, callback) else {
        return;
    };

    let Some(program) = crate::util::get_java_string_async(&mut env, &program) else {
        return;
    };
    let Some(opts) = crate::util::get_java_string_async(&mut env, &options) else {
        return;
    };

    thread::spawn(move || {
        let callback_result = crate::util::execute_with_panic_protection(
            || perform_print_work(&program, &opts),
            "Internal error: panic in print work",
        );
        callback_java(jvm, callback_ref, callback_result);
    });
}

/// Helper function to actually perform print work
fn perform_print_work(program_str: &str, opts: &str) -> Result<String, String> {
    let c = get_compiler();

    let program: Program = deserialize_json(program_str)
        .map_err(|e| {
            // Format complete error stack information, including serde_json error path
            let mut error_msg = format!("Failed to deserialize program\n{:?}", e);
            
            // serde_json::Error's Display implementation includes path information
            let error_str = format!("{}", e);
            if !error_str.is_empty() {
                error_msg.push_str(&format!("\n\nError details: {}", error_str));
            }
            
            // If error has a cause chain, add complete stack
            if let Some(source) = e.source() {
                error_msg.push_str(&format!("\n\nCaused by:\n    {}", source));
                let mut current = source.source();
                while let Some(cause) = current {
                    error_msg.push_str(&format!("\n    {}", cause));
                    current = cause.source();
                }
            }
            
            error_msg
        })?;

    let options: Options = get_deserialized(opts).map_err(|e| {
        // SwcException already has complete error information (formatted in convert_err)
        match e {
            SwcException::SwcAnyException { msg } => msg,
        }
    })?;

    // Defaults to es3
    let _codegen_target = options.codegen_target().unwrap_or_default();

    let result = GLOBALS.set(&Default::default(), || {
        let print_args = PrintArgs {
            output_path: options.output_path,
            inline_sources_content: true,
            source_map: options
                .source_maps
                .clone()
                .unwrap_or(SourceMapsConfig::Bool(false)),
            ..Default::default()
        };
        c.print(&program, print_args)
            .map_err(|e| {
                // Format complete error stack information
                let mut error_msg = format!("Print failed\n{:?}", e);
                
                // If error has a cause chain, add complete stack
                if let Some(source) = e.source() {
                    error_msg.push_str(&format!("\n\nCaused by:\n    {}", source));
                    let mut current = source.source();
                    while let Some(cause) = current {
                        error_msg.push_str(&format!("\n    {}", cause));
                        current = cause.source();
                    }
                }
                
                anyhow::anyhow!(error_msg)
            })
            .convert_err()
    });

    match result {
        Ok(output) => {
            let json_str = serde_json::to_string(&output)
                .map_err(|e| {
                    // Format complete error stack information
                    let mut error_msg = format!("Failed to serialize output\n{:?}", e);
                    
                    // If error has a cause chain, add complete stack
                    if let Some(source) = e.source() {
                        error_msg.push_str(&format!("\n\nCaused by:\n    {}", source));
                        let mut current = source.source();
                        while let Some(cause) = current {
                            error_msg.push_str(&format!("\n    {}", cause));
                            current = cause.source();
                        }
                    }
                    
                    error_msg
                })?;
            Ok(json_str)
        },
        Err(e) => {
            // Extract error message from SwcException
            let error_msg = match e {
                crate::util::SwcException::SwcAnyException { msg } => msg,
            };
            Err(error_msg)
        },
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::parse::perform_parse_work;

    fn create_print_options_json(minify: bool, sourcemap: bool) -> String {
        format!(
            r#"{{
                "jsc": {{
                    "parser": {{
                        "syntax": "ecmascript"
                    }},
                    "target": "es2020",
                    "minify": {{}}
                }},
                "minify": {},
                "sourceMaps": {}
            }}"#,
            minify,
            if sourcemap { r#""inline""# } else { "false" }
        )
    }

    fn parse_simple_code() -> String {
        let code = "const x = 42; console.log(x);";
        let parse_opts = r#"{
            "syntax": "ecmascript",
            "target": "es2020",
            "isModule": true,
            "comments": false
        }"#;
        perform_parse_work(code, parse_opts, "").unwrap()
    }

    #[test]
    fn test_perform_print_work_basic() {
        let ast = parse_simple_code();
        let opts = create_print_options_json(false, false);

        let result = perform_print_work(&ast, &opts);
        assert!(result.is_ok(), "Print should succeed: {:?}", result);

        let output = result.unwrap();
        assert!(output.contains("code"));
    }

    #[test]
    fn test_perform_print_work_with_sourcemap() {
        let ast = parse_simple_code();
        let opts = create_print_options_json(false, true);

        let result = perform_print_work(&ast, &opts);
        assert!(
            result.is_ok(),
            "Print with sourcemap should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_print_work_minified() {
        let ast = parse_simple_code();
        let opts = create_print_options_json(true, false);

        let result = perform_print_work(&ast, &opts);
        assert!(
            result.is_ok(),
            "Print minified should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_print_work_invalid_ast() {
        let invalid_ast = r#"{"invalid": "ast"}"#;
        let opts = create_print_options_json(false, false);

        let result = perform_print_work(invalid_ast, &opts);
        assert!(result.is_err(), "Print should fail with invalid AST");
    }

    #[test]
    fn test_perform_print_work_invalid_options() {
        let ast = parse_simple_code();
        let invalid_opts = r#"{"invalid": "options"}"#;

        let result = perform_print_work(&ast, invalid_opts);
        assert!(result.is_err(), "Print should fail with invalid options");
    }

    #[test]
    fn test_perform_print_work_different_targets() {
        let ast = parse_simple_code();

        // Test with es5 target
        let opts = r#"{
            "jsc": {
                "parser": {"syntax": "ecmascript"},
                "target": "es5"
            }
        }"#;

        let result = perform_print_work(&ast, opts);
        assert!(
            result.is_ok(),
            "Print with ES5 target should succeed: {:?}",
            result
        );
    }
}

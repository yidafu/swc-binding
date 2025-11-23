use std::thread;

use jni::{
    objects::{JClass, JObject, JString},
    sys::{jboolean, jstring},
    JNIEnv,
};
use jni_fn::jni_fn;
use serde_json;

use std::collections::HashMap as AHashMap;
use swc_core::base::config::ErrorFormat;
use swc_core::common::{sync::Lrc, FileName, SourceFile, SourceMap};

use crate::async_utils::callback_java;
#[allow(unused_imports)]
use crate::{get_compiler, get_fresh_compiler, util::process_output};

use crate::util::{get_deserialized, try_with, MapErr, SwcResult};
use swc_core::base::TransformOutput;

enum MinifyTarget {
    /// Code to minify.
    Single(String),
    /// JSON string representing `{ filename: code }` - will be parsed internally
    Json(String),
}

impl MinifyTarget {
    fn to_file(&self, cm: Lrc<SourceMap>) -> Lrc<SourceFile> {
        match self {
            MinifyTarget::Single(code) => cm.new_source_file(FileName::Anon.into(), code.clone()),
            MinifyTarget::Json(json) => {
                let codes: AHashMap<String, String> = serde_json::from_str(json)
                    .expect("Invalid JSON");
                assert_eq!(
                    codes.len(),
                    1,
                    "swc.minify does not support concatting multiple files yet"
                );

                let (filename, code) = codes.iter().next().unwrap();

                cm.new_source_file(FileName::Real(filename.clone().into()).into(), code.clone())
            }
        }
    }
}

#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn minifySync(mut env: JNIEnv, _: JClass, code: JString, opts: JString, is_json: jboolean) -> jstring {
    let code_str = match crate::util::get_java_string_or_throw(&mut env, &code) {
        Some(s) => s,
        None => return JString::default().into_raw(),
    };
    let opts_str = match crate::util::get_java_string_or_throw(&mut env, &opts) {
        Some(s) => s,
        None => return JString::default().into_raw(),
    };
    let is_json_bool = is_json == 1;
    let result = perform_minify_sync_work(&code_str, &opts_str, is_json_bool);
    process_output(env, result)
}

/// Helper function to perform synchronous minify work
fn perform_minify_sync_work(code: &str, opts: &str, is_json: bool) -> SwcResult<TransformOutput> {
    let input = if is_json {
        // Validate JSON format before creating MinifyTarget
        let _: AHashMap<String, String> = serde_json::from_str(code)
            .map_err(|e| crate::util::SwcException::SwcAnyException { 
                msg: format!("Failed to parse JSON: {:?}", e) 
            })?;
        MinifyTarget::Json(code.to_string())
    } else {
        MinifyTarget::Single(code.to_string())
    };
    let opts = get_deserialized(opts.as_bytes())?;

    let c = get_fresh_compiler();

    let fm = input.to_file(c.cm.clone());

    let result = try_with(
        c.cm.clone(),
        false,
        // TODO(kdy1): Maybe make this configurable?
        ErrorFormat::Normal,
        |handler| c.minify(fm, handler, &opts, Default::default()),
    )
    .convert_err();

    result
}

/// Async minify method - uses callback
#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn minifyAsync(mut env: JNIEnv, _: JClass, code: JString, opts: JString, is_json: jboolean, callback: JObject) {
    let Some((jvm, callback_ref)) = crate::util::setup_async_callback(&mut env, callback) else {
        return;
    };

    let Some(code) = crate::util::get_java_string_async(&mut env, &code) else {
        return;
    };
    let Some(opts) = crate::util::get_java_string_async(&mut env, &opts) else {
        return;
    };
    let is_json_bool = is_json == 1;

    thread::spawn(move || {
        let callback_result = crate::util::execute_with_panic_protection(
            || perform_minify_work(&code, &opts, is_json_bool),
            "Internal error: panic in minify work",
        );
        callback_java(jvm, callback_ref, callback_result);
    });
}

/// Helper function to actually perform minify work
fn perform_minify_work(code: &str, opts: &str, is_json: bool) -> Result<String, String> {
    let input = if is_json {
        // Validate JSON format before creating MinifyTarget
        let _: AHashMap<String, String> = serde_json::from_str(code)
            .map_err(|e| format!("Failed to parse JSON: {:?}", e))?;
        MinifyTarget::Json(code.to_string())
    } else {
        MinifyTarget::Single(code.to_string())
    };
    let opts = get_deserialized(opts.as_bytes())
        .map_err(|e| format!("Failed to parse options: {:?}", e))?;

    let c = get_fresh_compiler();
    let fm = input.to_file(c.cm.clone());

    let result = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
        c.minify(fm, handler, &opts, Default::default())
    })
    .convert_err();

    match result {
        Ok(output) => {
            let json_str = serde_json::to_string(&output)
                .map_err(|e| format!("Failed to serialize output: {:?}", e))?;
            Ok(json_str)
        },
        Err(e) => Err(format!("Minify error: {:?}", e)),
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_minify_target_single() {
        let code = "const x = 42; console.log(x);";
        let target = MinifyTarget::Single(code.to_string());

        let c = get_compiler();
        let fm = target.to_file(c.cm.clone());

        assert!(!fm.src.is_empty());
    }

    #[test]
    fn test_minify_target_json_single_entry() {
        let code = r#"{"test.js": "const x = 42;"}"#;
        let target = MinifyTarget::Json(code.to_string());

        let c = get_compiler();
        let fm = target.to_file(c.cm.clone());

        assert!(!fm.src.is_empty());
    }

    #[test]
    #[should_panic(expected = "swc.minify does not support concatting multiple files yet")]
    fn test_minify_target_json_multiple_entries() {
        let code = r#"{"file1.js": "const x = 42;", "file2.js": "const y = 10;"}"#;
        let target = MinifyTarget::Json(code.to_string());

        let c = get_compiler();
        let _fm = target.to_file(c.cm.clone());
    }

    #[test]
    fn test_perform_minify_work_basic() {
        let code = "function test() { const x = 42; return x + 1; }";
        let opts = r#"{"compress": {"unused": true}, "mangle": false}"#;

        let result = perform_minify_work(code, opts, false);
        assert!(result.is_ok(), "Minify should succeed: {:?}", result);

        let output = result.unwrap();
        assert!(output.contains("code"));
    }

    #[test]
    fn test_perform_minify_work_with_mangle() {
        let code = "function longFunctionName() { const veryLongVariableName = 42; return veryLongVariableName; }";
        let opts = r#"{"compress": {}, "mangle": true}"#;

        let result = perform_minify_work(code, opts, false);
        assert!(
            result.is_ok(),
            "Minify with mangle should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_minify_work_invalid_code() {
        let code = "const x = ;";
        let opts = r#"{"compress": {}, "mangle": false}"#;

        let result = perform_minify_work(code, opts, false);
        // Invalid syntax should result in an error
        assert!(result.is_err(), "Minify should fail on invalid code");
    }

    #[test]
    fn test_perform_minify_work_invalid_options() {
        let code = "const x = 42;";
        let invalid_opts = r#"{"invalid": "option"}"#;

        let result = perform_minify_work(code, invalid_opts, false);
        assert!(result.is_err(), "Minify should fail with invalid options");
    }

    #[test]
    fn test_perform_minify_work_json_format() {
        let code = r#"{"test.js": "const x = 42; const y = 10; console.log(x + y);"}"#;
        let opts = r#"{"compress": {"unused": true}, "mangle": false}"#;

        let result = perform_minify_work(code, opts, true);
        assert!(
            result.is_ok(),
            "Minify with JSON format should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_minify_work_empty_code() {
        let code = "";
        let opts = r#"{"compress": {}, "mangle": false}"#;

        let result = perform_minify_work(code, opts, false);
        // Empty code might succeed or fail depending on parser, but should not panic
        assert!(result.is_ok() || result.is_err());
    }

    #[test]
    fn test_perform_minify_work_with_compress_options() {
        let code = "function unusedFunction() { return 42; } const x = 1 + 2;";
        let opts = r#"{"compress": {"unused": true, "dead_code": true}, "mangle": false}"#;

        let result = perform_minify_work(code, opts, false);
        assert!(
            result.is_ok(),
            "Minify with compress options should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_minify_work_with_mangle_and_compress() {
        let code = "function longFunctionName() { const veryLongVariableName = 42; return veryLongVariableName; }";
        let opts = r#"{"compress": true, "mangle": true}"#;

        let result = perform_minify_work(code, opts, false);
        assert!(
            result.is_ok(),
            "Minify with mangle and compress should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_minify_sync_work_basic() {
        let code = "const x = 42; console.log(x);";
        let opts = r#"{"compress": {}, "mangle": false}"#;

        let result = perform_minify_sync_work(code, opts, false);
        assert!(result.is_ok(), "Minify sync should succeed: {:?}", result);
    }

    #[test]
    fn test_perform_minify_sync_work_with_json() {
        let code = r#"{"test.js": "const x = 42; const y = 10; console.log(x + y);"}"#;
        let opts = r#"{"compress": {}, "mangle": false}"#;

        let result = perform_minify_sync_work(code, opts, true);
        assert!(result.is_ok(), "Minify sync with JSON should succeed: {:?}", result);
    }

    #[test]
    fn test_perform_minify_work_with_json_and_mangle() {
        let code = r#"{"app.js": "function longFunctionName() { const veryLongVariableName = 42; return veryLongVariableName; }"}"#;
        let opts = r#"{"compress": {}, "mangle": true}"#;

        let result = perform_minify_work(code, opts, true);
        assert!(
            result.is_ok(),
            "Minify JSON with mangle should succeed: {:?}",
            result
        );
        let output = result.unwrap();
        assert!(output.contains("code"));
    }

    #[test]
    fn test_perform_minify_work_with_json_and_compress() {
        let code = r#"{"utils.js": "function unusedFunction() { return 42; } const x = 1 + 2; console.log(x);"}"#;
        let opts = r#"{"compress": {"unused": true, "dead_code": true}, "mangle": false}"#;

        let result = perform_minify_work(code, opts, true);
        assert!(
            result.is_ok(),
            "Minify JSON with compress should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_minify_work_with_json_complex_code() {
        let code = r#"{"main.js": "class Calculator { add(a, b) { return a + b; } multiply(a, b) { return a * b; } } const calc = new Calculator(); console.log(calc.add(1, 2));"}"#;
        let opts = r#"{"compress": true, "mangle": true}"#;

        let result = perform_minify_work(code, opts, true);
        assert!(
            result.is_ok(),
            "Minify complex JSON code should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_minify_work_with_invalid_json_format() {
        // Invalid JSON format (not a map)
        let code = r#"["not", "a", "map"]"#;
        let opts = r#"{"compress": {}, "mangle": false}"#;

        let result = perform_minify_work(code, opts, true);
        assert!(result.is_err(), "Minify should fail with invalid JSON format");
    }

    #[test]
    fn test_perform_minify_work_with_json_empty_code() {
        let code = r#"{"empty.js": ""}"#;
        let opts = r#"{"compress": {}, "mangle": false}"#;

        let result = perform_minify_work(code, opts, true);
        // Empty code might succeed or fail depending on parser, but should not panic
        assert!(result.is_ok() || result.is_err());
    }

    #[test]
    fn test_perform_minify_sync_work_with_json_and_compress() {
        let code = r#"{"lib.js": "function helper() { const x = 1; const y = 2; return x + y; }"}"#;
        let opts = r#"{"compress": {"unused": true}, "mangle": false}"#;

        let result = perform_minify_sync_work(code, opts, true);
        assert!(result.is_ok(), "Minify sync JSON with compress should succeed: {:?}", result);
    }

    #[test]
    fn test_perform_minify_work_with_json_arrow_functions() {
        let code = r#"{"arrow.js": "const add = (a, b) => a + b; const multiply = (x, y) => x * y; const result = add(1, 2) + multiply(3, 4);"}"#;
        let opts = r#"{"compress": true, "mangle": true}"#;

        let result = perform_minify_work(code, opts, true);
        assert!(
            result.is_ok(),
            "Minify JSON with arrow functions should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_minify_work_with_json_different_filename() {
        let code = r#"{"src/components/Button.tsx": "const Button = () => { return 'Click me'; };"}"#;
        let opts = r#"{"compress": {}, "mangle": false}"#;

        let result = perform_minify_work(code, opts, true);
        assert!(
            result.is_ok(),
            "Minify JSON with different filename should succeed: {:?}",
            result
        );
    }
}

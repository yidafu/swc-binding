use std::thread;

use jni::{
    objects::{JClass, JObject, JString},
    sys::jstring,
    JNIEnv,
};
use jni_fn::jni_fn;
use serde::Deserialize;

use swc::config::ErrorFormat;
use swc_common::{sync::Lrc, FileName, SourceFile, SourceMap};
use std::collections::HashMap as AHashMap;

use crate::async_utils::callback_java;
use crate::{get_compiler, util::process_output};

use crate::util::{get_deserialized, try_with, MapErr};

#[derive(Deserialize)]
#[serde(untagged)]
enum MinifyTarget {
    /// Code to minify.
    Single(String),
    /// `{ filename: code }`
    Map(AHashMap<String, String>),
}

impl MinifyTarget {
    fn to_file(&self, cm: Lrc<SourceMap>) -> Lrc<SourceFile> {
        match self {
            MinifyTarget::Single(code) => cm.new_source_file(FileName::Anon.into(), code.clone()),
            MinifyTarget::Map(codes) => {
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
pub fn minifySync(mut env: JNIEnv, _: JClass, code: JString, opts: JString) -> jstring {
    let code: String = env
        .get_string(&code)
        .expect("Couldn't get java string!")
        .into();
    let opts: String = env
        .get_string(&opts)
        .expect("Couldn't get java string!")
        .into();
    // crate::util::init_default_trace_subscriber();
    let code: MinifyTarget = get_deserialized(code.as_bytes()).unwrap();
    let opts = get_deserialized(opts.as_bytes()).unwrap();

    let c = get_compiler();

    let fm = code.to_file(c.cm.clone());

    let result = try_with(
        c.cm.clone(),
        false,
        // TODO(kdy1): Maybe make this configurable?
        ErrorFormat::Normal,
        |handler| c.minify(fm, handler, &opts, Default::default()),
    )
    .convert_err();

    process_output(env, result)
}

/// 异步压缩方法 - 使用回调
#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn minifyAsync(
    mut env: JNIEnv,
    _: JClass,
    code: JString,
    opts: JString,
    callback: JObject,
) {
    let jvm = match env.get_java_vm() {
        Ok(vm) => vm,
        Err(e) => {
            eprintln!("Failed to get JavaVM: {:?}", e);
            return;
        }
    };
    
    let callback_ref = match env.new_global_ref(callback) {
        Ok(r) => r,
        Err(e) => {
            eprintln!("Failed to create global ref: {:?}", e);
            return;
        }
    };
    
    let code: String = env
        .get_string(&code)
        .expect("Couldn't get java string!")
        .into();
    let opts: String = env
        .get_string(&opts)
        .expect("Couldn't get java string!")
        .into();
    
    thread::spawn(move || {
        let result = perform_minify_work(&code, &opts);
        callback_java(jvm, callback_ref, result);
    });
}

/// 实际执行压缩工作的辅助函数
fn perform_minify_work(code: &str, opts: &str) -> Result<String, String> {
    let code: MinifyTarget = get_deserialized(code.as_bytes())
        .map_err(|e| format!("Failed to deserialize code: {:?}", e))?;
    let opts = get_deserialized(opts.as_bytes())
        .map_err(|e| format!("Failed to parse options: {:?}", e))?;
    
    let c = get_compiler();
    let fm = code.to_file(c.cm.clone());
    
    let result = try_with(
        c.cm.clone(),
        false,
        ErrorFormat::Normal,
        |handler| c.minify(fm, handler, &opts, Default::default()),
    )
    .convert_err();
    
    match result {
        Ok(output) => serde_json::to_string(&output)
            .map_err(|e| format!("Failed to serialize output: {:?}", e)),
        Err(e) => Err(format!("Minify error: {:?}", e)),
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_minify_target_single() {
        let code = r#""const x = 42; console.log(x);""#;
        let target: MinifyTarget = get_deserialized(code.as_bytes()).unwrap();
        
        let c = get_compiler();
        let fm = target.to_file(c.cm.clone());
        
        assert!(!fm.src.is_empty());
    }

    #[test]
    fn test_minify_target_map_single_entry() {
        let code = r#"{"test.js": "const x = 42;"}"#;
        let target: MinifyTarget = get_deserialized(code.as_bytes()).unwrap();
        
        let c = get_compiler();
        let fm = target.to_file(c.cm.clone());
        
        assert!(!fm.src.is_empty());
    }

    #[test]
    #[should_panic(expected = "swc.minify does not support concatting multiple files yet")]
    fn test_minify_target_map_multiple_entries() {
        let code = r#"{"file1.js": "const x = 42;", "file2.js": "const y = 10;"}"#;
        let target: MinifyTarget = get_deserialized(code.as_bytes()).unwrap();
        
        let c = get_compiler();
        let _fm = target.to_file(c.cm.clone());
    }

    #[test]
    fn test_perform_minify_work_basic() {
        let code = r#""function test() { const x = 42; return x + 1; }""#;
        let opts = r#"{"compress": {"unused": true}, "mangle": false}"#;
        
        let result = perform_minify_work(code, opts);
        assert!(result.is_ok(), "Minify should succeed: {:?}", result);
        
        let output = result.unwrap();
        assert!(output.contains("code"));
    }

    #[test]
    fn test_perform_minify_work_with_mangle() {
        let code = r#""function longFunctionName() { const veryLongVariableName = 42; return veryLongVariableName; }""#;
        let opts = r#"{"compress": {}, "mangle": true}"#;
        
        let result = perform_minify_work(code, opts);
        assert!(result.is_ok(), "Minify with mangle should succeed: {:?}", result);
    }

    #[test]
    fn test_perform_minify_work_invalid_code() {
        let code = r#""const x = ;""#;
        let opts = r#"{"compress": {}, "mangle": false}"#;
        
        let result = perform_minify_work(code, opts);
        // Invalid syntax should result in an error
        assert!(result.is_err(), "Minify should fail on invalid code");
    }

    #[test]
    fn test_perform_minify_work_invalid_options() {
        let code = r#""const x = 42;""#;
        let invalid_opts = r#"{"invalid": "option"}"#;
        
        let result = perform_minify_work(code, invalid_opts);
        assert!(result.is_err(), "Minify should fail with invalid options");
    }

    #[test]
    fn test_perform_minify_work_map_format() {
        let code = r#"{"test.js": "const x = 42; const y = 10; console.log(x + y);"}"#;
        let opts = r#"{"compress": {"unused": true}, "mangle": false}"#;
        
        let result = perform_minify_work(code, opts);
        assert!(result.is_ok(), "Minify with map format should succeed: {:?}", result);
    }
}

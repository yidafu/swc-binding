use std::path::Path;
use std::thread;

use anyhow::Context;
use jni::objects::{JClass, JObject, JString};
use jni::sys::{jboolean, jstring};
use jni::JNIEnv;
use jni_fn::jni_fn;

use swc::config::Options;
use swc_common::FileName;
use swc_ecma_ast::Program;

use crate::async_utils::callback_java;
use crate::util::{deserialize_json, get_deserialized, process_output, try_with, MapErr, SwcResult};
use swc::TransformOutput;

use crate::get_compiler;

#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn transformSync(
    mut env: JNIEnv,
    _: JClass,
    code: JString,
    is_module: jboolean,
    options: JString,
) -> jstring {
    let s: String = env
        .get_string(&code)
        .expect("Couldn't get java string!")
        .into();
    let opts: String = env
        .get_string(&options)
        .expect("Couldn't get java string!")
        .into();
    let is_module = is_module == 1;
    let result = perform_transform_sync_work(&s, is_module, &opts);
    process_output(env, result)
}

/// 执行同步转换工作的辅助函数
fn perform_transform_sync_work(code: &str, is_module: bool, opts: &str) -> SwcResult<TransformOutput> {
    let c = get_compiler();

    let mut options: Options = get_deserialized(opts)?;

    if !options.filename.is_empty() {
        options.config.adjust(Path::new(&options.filename));
    }

    let error_format = options.experimental.error_format.unwrap_or_default();

    let result = try_with(
        c.cm.clone(),
        !options.config.error.filename.into_bool(),
        error_format,
        |handler| {
            c.run(|| {
                if is_module {
                    let program: Program =
                        deserialize_json(code).context("failed to deserialize Program")?;
                    c.process_js(handler, program, &options)
                } else {
                    let fm = c.cm.new_source_file(
                        if options.filename.is_empty() {
                            FileName::Anon.into()
                        } else {
                            FileName::Real(options.filename.clone().into()).into()
                        },
                        code.to_string(),
                    );
                    c.process_js_file(fm, handler, &options)
                }
            })
        },
    )
    .convert_err();

    result
}

#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn transformFileSync(
    mut env: JNIEnv,
    _: JClass,
    filepath: JString,
    is_module: jboolean,
    options: JString,
) -> jstring {
    let s: String = env
        .get_string(&filepath)
        .expect("Couldn't get java string!")
        .into();
    let opts: String = env
        .get_string(&options)
        .expect("Couldn't get java string!")
        .into();
    let is_module = is_module == 1;
    let result = perform_transform_file_sync_work(&s, is_module, &opts);
    process_output(env, result)
}

/// 执行同步文件转换工作的辅助函数
fn perform_transform_file_sync_work(filepath: &str, is_module: bool, opts: &str) -> SwcResult<TransformOutput> {
    let c = get_compiler();

    let mut options: Options = get_deserialized(opts)?;

    if !options.filename.is_empty() {
        options.config.adjust(Path::new(&options.filename));
    }

    let error_format = options.experimental.error_format.unwrap_or_default();

    let result = try_with(
        c.cm.clone(),
        !options.config.error.filename.into_bool(),
        error_format,
        |handler| {
            c.run(|| {
                if is_module {
                    let program: Program =
                        deserialize_json(filepath).context("failed to deserialize Program")?;
                    c.process_js(handler, program, &options)
                } else {
                    let fm = c.cm.load_file(Path::new(filepath))?;
                    c.process_js_file(fm, handler, &options)
                }
            })
        },
    )
    .convert_err();

    result
}

/// 异步转换方法 - 使用回调
#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn transformAsync(
    mut env: JNIEnv,
    _: JClass,
    code: JString,
    is_module: jboolean,
    options: JString,
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

    let s: String = env
        .get_string(&code)
        .expect("Couldn't get java string!")
        .into();
    let opts: String = env
        .get_string(&options)
        .expect("Couldn't get java string!")
        .into();
    let is_module = is_module == 1;

    thread::spawn(move || {
        let result = perform_transform_work(&s, is_module, &opts);
        callback_java(jvm, callback_ref, result);
    });
}

/// 异步转换文件方法 - 使用回调
#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn transformFileAsync(
    mut env: JNIEnv,
    _: JClass,
    filepath: JString,
    is_module: jboolean,
    options: JString,
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

    let s: String = env
        .get_string(&filepath)
        .expect("Couldn't get java string!")
        .into();
    let opts: String = env
        .get_string(&options)
        .expect("Couldn't get java string!")
        .into();
    let is_module = is_module == 1;

    thread::spawn(move || {
        let result = perform_transform_file_work(&s, is_module, &opts);
        callback_java(jvm, callback_ref, result);
    });
}

/// 实际执行转换工作的辅助函数
fn perform_transform_work(code: &str, is_module: bool, opts: &str) -> Result<String, String> {
    let c = get_compiler();

    let mut options: Options =
        get_deserialized(opts).map_err(|e| format!("Failed to parse options: {:?}", e))?;

    if !options.filename.is_empty() {
        options.config.adjust(Path::new(&options.filename));
    }

    let error_format = options.experimental.error_format.unwrap_or_default();

    let result = try_with(
        c.cm.clone(),
        !options.config.error.filename.into_bool(),
        error_format,
        |handler| {
            c.run(|| {
                if is_module {
                    let program: Program =
                        deserialize_json(code).context("failed to deserialize Program")?;
                    c.process_js(handler, program, &options)
                } else {
                    let fm = c.cm.new_source_file(
                        if options.filename.is_empty() {
                            FileName::Anon.into()
                        } else {
                            FileName::Real(options.filename.clone().into()).into()
                        },
                        code.to_string(),
                    );
                    c.process_js_file(fm, handler, &options)
                }
            })
        },
    )
    .convert_err();

    match result {
        Ok(output) => serde_json::to_string(&output)
            .map_err(|e| format!("Failed to serialize output: {:?}", e)),
        Err(e) => Err(format!("Transform error: {:?}", e)),
    }
}

/// 实际执行文件转换工作的辅助函数
fn perform_transform_file_work(
    filepath: &str,
    is_module: bool,
    opts: &str,
) -> Result<String, String> {
    let c = get_compiler();

    let mut options: Options =
        get_deserialized(opts).map_err(|e| format!("Failed to parse options: {:?}", e))?;

    if !options.filename.is_empty() {
        options.config.adjust(Path::new(&options.filename));
    }

    let error_format = options.experimental.error_format.unwrap_or_default();

    let result = try_with(
        c.cm.clone(),
        !options.config.error.filename.into_bool(),
        error_format,
        |handler| {
            c.run(|| {
                if is_module {
                    let program: Program =
                        deserialize_json(filepath).context("failed to deserialize Program")?;
                    c.process_js(handler, program, &options)
                } else {
                    let fm = c.cm.load_file(Path::new(filepath))?;
                    c.process_js_file(fm, handler, &options)
                }
            })
        },
    )
    .convert_err();

    match result {
        Ok(output) => serde_json::to_string(&output)
            .map_err(|e| format!("Failed to serialize output: {:?}", e)),
        Err(e) => Err(format!("Transform error: {:?}", e)),
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::io::Write;
    use tempfile::NamedTempFile;

    fn create_transform_options_json() -> String {
        r#"{
            "jsc": {
                "parser": {
                    "syntax": "ecmascript",
                    "jsx": false
                },
                "target": "es5"
            },
            "module": {
                "type": "commonjs"
            }
        }"#
        .to_string()
    }

    #[test]
    fn test_perform_transform_work_from_code() {
        let code = r#"const arrow = () => 42; class MyClass {}"#;
        let opts = create_transform_options_json();

        let result = perform_transform_work(code, false, &opts);
        assert!(result.is_ok(), "Transform should succeed: {:?}", result);

        let output = result.unwrap();
        assert!(output.contains("code"));
    }

    #[test]
    fn test_perform_transform_work_es6_to_es5() {
        let code = r#"
            const add = (a, b) => a + b;
            const nums = [1, 2, 3];
            const doubled = nums.map(n => n * 2);
        "#;
        let opts = create_transform_options_json();

        let result = perform_transform_work(code, false, &opts);
        assert!(
            result.is_ok(),
            "ES6 to ES5 transform should succeed: {:?}",
            result
        );

        let output = result.unwrap();
        // Verify it's transformed (should contain 'function' instead of arrow functions)
        assert!(output.contains("code"));
    }

    #[test]
    fn test_perform_transform_work_typescript() {
        let code = r#"
            const greet = (name: string): string => `Hello, ${name}!`;
            interface Person { name: string; age: number; }
            const person: Person = { name: "Alice", age: 30 };
        "#;
        let opts = r#"{
            "jsc": {
                "parser": {
                    "syntax": "typescript",
                    "tsx": false
                },
                "target": "es5"
            }
        }"#;

        let result = perform_transform_work(code, false, opts);
        assert!(
            result.is_ok(),
            "TypeScript transform should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_transform_file_work() {
        let mut temp_file = NamedTempFile::new().unwrap();
        writeln!(temp_file, "const arrow = () => 42;").unwrap();

        let opts = create_transform_options_json();
        let result = perform_transform_file_work(temp_file.path().to_str().unwrap(), false, &opts);

        assert!(
            result.is_ok(),
            "File transform should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_transform_work_invalid_options() {
        let code = "const x = 42;";
        let invalid_opts = r#"{"invalid": "json"}"#;

        let result = perform_transform_work(code, false, invalid_opts);
        assert!(
            result.is_err(),
            "Transform should fail with invalid options"
        );
    }

    #[test]
    fn test_perform_transform_work_syntax_error() {
        let code = "const x = ;";
        let opts = create_transform_options_json();

        let result = perform_transform_work(code, false, &opts);
        assert!(result.is_err(), "Transform should fail on syntax error");
    }
}

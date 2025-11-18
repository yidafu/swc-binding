use std::path::Path;
use std::thread;

use jni::{
    objects::{JClass, JObject, JString},
    sys::jstring,
    JNIEnv,
};
use jni_fn::jni_fn;
use swc::config::{ErrorFormat, ParseOptions};
use swc_common::{comments::Comments, FileName, Mark};
use swc_ecma_transforms_base::resolver;

use swc_ecma_visit::VisitMutWith;

use crate::async_utils::callback_java;
use crate::get_compiler;

use crate::util::{get_deserialized, process_result, try_with, MapErr, SwcResult};
use swc_ecma_ast::Program;

#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn parseSync(
    mut env: JNIEnv,
    _: JClass,
    code: JString,
    options: JString,
    filename: JString,
) -> jstring {
    let src = match crate::util::get_java_string_or_throw(&mut env, &code) {
        Some(s) => s,
        None => return JString::default().into_raw(),
    };
    let opts = match crate::util::get_java_string_or_throw(&mut env, &options) {
        Some(s) => s,
        None => return JString::default().into_raw(),
    };
    let filename_str = match crate::util::get_java_string_or_throw(&mut env, &filename) {
        Some(s) => s,
        None => return JString::default().into_raw(),
    };
    let result = perform_parse_sync_work(&src, &opts, &filename_str);
    process_result(env, result)
}

/// Helper function to perform synchronous parse work
fn perform_parse_sync_work(src: &str, opts: &str, filename: &str) -> SwcResult<Program> {
    let c = get_compiler();

    let options: ParseOptions = get_deserialized(opts)?;
    let filename = if filename.is_empty() {
        FileName::Anon
    } else {
        FileName::Real(filename.into())
    };

    let result = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
        c.run(|| {
            let fm = c.cm.new_source_file(filename.into(), src.to_string());

            let comments = if options.comments {
                Some(c.comments() as &dyn Comments)
            } else {
                None
            };

            let mut p = c.parse_js(
                fm,
                handler,
                options.target,
                options.syntax,
                options.is_module,
                comments,
            )?;

            p.visit_mut_with(&mut resolver(
                Mark::new(),
                Mark::new(),
                options.syntax.typescript(),
            ));

            Ok(p)
        })
    })
    .convert_err();

    result
}

#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn parseFileSync(mut env: JNIEnv, _: JClass, filepath: JString, options: JString) -> jstring {
    // Safely get Java strings, return error if failed
    let opts = match crate::util::get_java_string_safe(&mut env, &options) {
        Ok(s) => s,
        Err(e) => {
            if let Err(throw_err) = env.throw(e) {
                eprintln!("Failed to throw exception: {:?}", throw_err);
            }
            return JString::default().into_raw();
        }
    };
    let path = match crate::util::get_java_string_safe(&mut env, &filepath) {
        Ok(s) => s,
        Err(e) => {
            if let Err(throw_err) = env.throw(e) {
                eprintln!("Failed to throw exception: {:?}", throw_err);
            }
            return JString::default().into_raw();
        }
    };

    // crate::util::init_default_trace_subscriber();
    let result = perform_parse_file_sync_work(&path, &opts);
    process_result(env, result)
}

/// Helper function to perform synchronous file parse work
fn perform_parse_file_sync_work(path: &str, opts: &str) -> SwcResult<Program> {
    let c = get_compiler();
    let options: ParseOptions = get_deserialized(opts)?;

    let result = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
        let fm = c.cm.load_file(Path::new(path))?;

        let comments = if options.comments {
            Some(c.comments() as &dyn Comments)
        } else {
            None
        };

        let mut p = c.parse_js(
            fm,
            handler,
            options.target,
            options.syntax,
            options.is_module,
            comments,
        )?;
        p.visit_mut_with(&mut resolver(
            Mark::new(),
            Mark::new(),
            options.syntax.typescript(),
        ));

        Ok(p)
    })
    .convert_err();

    result
}

/// Async parse method - uses callback
#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn parseAsync(
    mut env: JNIEnv,
    _: JClass,
    code: JString,
    options: JString,
    filename: JString,
    callback: JObject,
) {
    // 1. Get JavaVM (for cross-thread Java calls)
    let Some((jvm, callback_ref)) = crate::util::setup_async_callback(&mut env, callback) else {
        return;
    };

    let Some(src) = crate::util::get_java_string_async(&mut env, &code) else {
        return;
    };
    let Some(opts) = crate::util::get_java_string_async(&mut env, &options) else {
        return;
    };
    let Some(file) = crate::util::get_java_string_async(&mut env, &filename) else {
        return;
    };

    thread::spawn(move || {
        let callback_result = crate::util::execute_with_panic_protection(
            || perform_parse_work(&src, &opts, &file),
            "Internal error: panic in parse work",
        );
        callback_java(jvm, callback_ref, callback_result);
    });

    // Method returns immediately
}

/// Async parse file method - uses callback
#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn parseFileAsync(
    mut env: JNIEnv,
    _: JClass,
    filepath: JString,
    options: JString,
    callback: JObject,
) {
    let Some((jvm, callback_ref)) = crate::util::setup_async_callback(&mut env, callback) else {
        return;
    };

    let Some(opts) = crate::util::get_java_string_async(&mut env, &options) else {
        return;
    };
    let Some(path) = crate::util::get_java_string_async(&mut env, &filepath) else {
        return;
    };

    thread::spawn(move || {
        let callback_result = crate::util::execute_with_panic_protection(
            || perform_parse_file_work(&path, &opts),
            "Internal error: panic in parse file work",
        );
        callback_java(jvm, callback_ref, callback_result);
    });
}

/// Helper function to actually perform parse work
pub(crate) fn perform_parse_work(src: &str, opts: &str, file: &str) -> Result<String, String> {
    let c = get_compiler();

    let options: ParseOptions =
        get_deserialized(opts).map_err(|e| format!("Failed to parse options: {:?}", e))?;

    let filename = if file.is_empty() {
        FileName::Anon
    } else {
        FileName::Real(file.into())
    };

    let result = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
        c.run(|| {
            let fm =
                c.cm.new_source_file(filename.clone().into(), src.to_string());

            let comments = if options.comments {
                Some(c.comments() as &dyn Comments)
            } else {
                None
            };

            let mut p = c.parse_js(
                fm,
                handler,
                options.target,
                options.syntax,
                options.is_module,
                comments,
            )?;

            p.visit_mut_with(&mut resolver(
                Mark::new(),
                Mark::new(),
                options.syntax.typescript(),
            ));

            Ok(p)
        })
    })
    .convert_err();

    match result {
        Ok(program) => {
            serde_json::to_string(&program).map_err(|e| format!("Failed to serialize AST: {:?}", e))
        }
        Err(e) => Err(format!("Parse error: {:?}", e)),
    }
}

/// Helper function to actually perform file parse work
fn perform_parse_file_work(path: &str, opts: &str) -> Result<String, String> {
    let c = get_compiler();

    let options: ParseOptions =
        get_deserialized(opts).map_err(|e| format!("Failed to parse options: {:?}", e))?;

    let result = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
        let fm = c.cm.load_file(Path::new(path))?;

        let comments = if options.comments {
            Some(c.comments() as &dyn Comments)
        } else {
            None
        };

        let mut p = c.parse_js(
            fm,
            handler,
            options.target,
            options.syntax,
            options.is_module,
            comments,
        )?;

        p.visit_mut_with(&mut resolver(
            Mark::new(),
            Mark::new(),
            options.syntax.typescript(),
        ));

        Ok(p)
    })
    .convert_err();

    match result {
        Ok(program) => {
            serde_json::to_string(&program).map_err(|e| format!("Failed to serialize AST: {:?}", e))
        }
        Err(e) => Err(format!("Parse error: {:?}", e)),
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::io::Write;
    use tempfile::NamedTempFile;

    fn create_parse_options_json(syntax: &str, is_module: bool, comments: bool) -> String {
        format!(
            r#"{{
                "syntax": "{}",
                "target": "es2020",
                "isModule": {},
                "comments": {}
            }}"#,
            syntax, is_module, comments
        )
    }

    #[test]
    fn test_perform_parse_work_javascript() {
        let code = r#"const x = 42; console.log(x);"#;
        let opts = create_parse_options_json("ecmascript", true, false);

        let result = perform_parse_work(code, &opts, "");
        assert!(result.is_ok(), "Parse should succeed: {:?}", result);

        let ast = result.unwrap();
        assert!(ast.contains("type"));
        assert!(ast.contains("Module") || ast.contains("Script"));
    }

    #[test]
    fn test_perform_parse_work_typescript() {
        let code = r#"const greet = (name: string): string => `Hello, ${name}!`;"#;
        let opts = create_parse_options_json("typescript", true, false);

        let result = perform_parse_work(code, &opts, "test.ts");
        assert!(
            result.is_ok(),
            "TypeScript parse should succeed: {:?}",
            result
        );

        let ast = result.unwrap();
        assert!(ast.contains("type"));
    }

    #[test]
    fn test_perform_parse_work_with_comments() {
        let code = r#"
            // This is a comment
            const x = 42;
            /* Block comment */
            function test() {}
        "#;
        let opts = create_parse_options_json("ecmascript", true, true);

        let result = perform_parse_work(code, &opts, "");
        assert!(
            result.is_ok(),
            "Parse with comments should succeed: {:?}",
            result
        );
    }

    #[test]
    fn test_perform_parse_work_syntax_error() {
        let code = r#"const x = ;"#;
        let opts = create_parse_options_json("ecmascript", true, false);

        let result = perform_parse_work(code, &opts, "");
        assert!(result.is_err(), "Parse should fail on syntax error");
    }

    #[test]
    fn test_perform_parse_file_work() {
        let mut temp_file = NamedTempFile::new().unwrap();
        writeln!(temp_file, "const message = 'Hello from file';").unwrap();

        let opts = create_parse_options_json("ecmascript", true, false);
        let result = perform_parse_file_work(temp_file.path().to_str().unwrap(), &opts);

        assert!(result.is_ok(), "File parse should succeed: {:?}", result);
        let ast = result.unwrap();
        assert!(ast.contains("type"));
    }

    #[test]
    fn test_perform_parse_file_work_not_found() {
        let opts = create_parse_options_json("ecmascript", true, false);
        let result = perform_parse_file_work("/non/existent/file.js", &opts);

        assert!(result.is_err(), "Parse should fail for non-existent file");
    }

    #[test]
    fn test_perform_parse_work_invalid_options() {
        let code = "const x = 42;";
        let invalid_opts = r#"{"invalid": "json"}"#;

        let result = perform_parse_work(code, invalid_opts, "");
        assert!(result.is_err(), "Parse should fail with invalid options");
    }
}

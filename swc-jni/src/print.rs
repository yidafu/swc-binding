use std::thread;

use jni::objects::{JClass, JObject, JString};
use jni::sys::jstring;
use jni::JNIEnv;
use jni_fn::jni_fn;

use swc::config::{Options, SourceMapsConfig};
use swc::PrintArgs;
use swc_common::GLOBALS;
use swc_ecma_ast::Program;

use crate::async_utils::callback_java;
use crate::util::{deserialize_json, get_deserialized, process_output, MapErr};

use crate::get_compiler;

#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn printSync(mut env: JNIEnv, _: JClass, program: JString, options: JString) -> jstring {
    let program: String = env
        .get_string(&program)
        .expect("Couldn't get java string!")
        .into();
    let opts: String = env
        .get_string(&options)
        .expect("Couldn't get java string!")
        .into();
    // crate::util::init_default_trace_subscriber();

    let c = get_compiler();

    let program: Program = deserialize_json(program.as_str()).unwrap();

    let options: Options = get_deserialized(&opts).unwrap();

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
        c.print(&program, print_args).convert_err()
    });
    process_output(env, result)
}

/// 异步打印方法 - 使用回调
#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn printAsync(
    mut env: JNIEnv,
    _: JClass,
    program: JString,
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
    
    let program: String = env
        .get_string(&program)
        .expect("Couldn't get java string!")
        .into();
    let opts: String = env
        .get_string(&options)
        .expect("Couldn't get java string!")
        .into();
    
    thread::spawn(move || {
        let result = perform_print_work(&program, &opts);
        callback_java(jvm, callback_ref, result);
    });
}

/// 实际执行打印工作的辅助函数
fn perform_print_work(program_str: &str, opts: &str) -> Result<String, String> {
    let c = get_compiler();
    
    let program: Program = deserialize_json(program_str)
        .map_err(|e| format!("Failed to deserialize program: {:?}", e))?;
    
    let options: Options = get_deserialized(opts)
        .map_err(|e| format!("Failed to parse options: {:?}", e))?;
    
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
        c.print(&program, print_args).convert_err()
    });
    
    match result {
        Ok(output) => serde_json::to_string(&output)
            .map_err(|e| format!("Failed to serialize output: {:?}", e)),
        Err(e) => Err(format!("Print error: {:?}", e)),
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
        assert!(result.is_ok(), "Print with sourcemap should succeed: {:?}", result);
    }

    #[test]
    fn test_perform_print_work_minified() {
        let ast = parse_simple_code();
        let opts = create_print_options_json(true, false);
        
        let result = perform_print_work(&ast, &opts);
        assert!(result.is_ok(), "Print minified should succeed: {:?}", result);
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
        assert!(result.is_ok(), "Print with ES5 target should succeed: {:?}", result);
    }
}

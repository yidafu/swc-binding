use jni::objects::GlobalRef;
use jni::JavaVM;

/// 通用的 Java 回调函数
/// 
/// 此函数用于从 Rust 后台线程回调 Java/Kotlin 代码
/// 
/// # 参数
/// * `jvm` - JavaVM 实例，用于附加当前线程
/// * `callback_ref` - 回调对象的全局引用
/// * `result` - 操作结果，成功时为 JSON 字符串，失败时为错误信息
pub fn callback_java(jvm: JavaVM, callback_ref: GlobalRef, result: Result<String, String>) {
    // 附加当前线程到 JVM
    let mut env = match jvm.attach_current_thread() {
        Ok(env) => env,
        Err(e) => {
            eprintln!("Failed to attach thread to JVM: {:?}", e);
            return;
        }
    };
    
    match result {
        Ok(json_string) => {
            // 成功：调用 onSuccess(String result)
            let result_jstring = match env.new_string(json_string) {
                Ok(s) => s,
                Err(e) => {
                    eprintln!("Failed to create Java string: {:?}", e);
                    return;
                }
            };
            
            if let Err(e) = env.call_method(
                &callback_ref,
                "onSuccess",
                "(Ljava/lang/String;)V",
                &[(&result_jstring).into()],
            ) {
                eprintln!("Failed to call onSuccess: {:?}", e);
            }
        }
        Err(error) => {
            // 失败：调用 onError(String error)
            let error_jstring = match env.new_string(error) {
                Ok(s) => s,
                Err(e) => {
                    eprintln!("Failed to create error string: {:?}", e);
                    return;
                }
            };
            
            if let Err(e) = env.call_method(
                &callback_ref,
                "onError",
                "(Ljava/lang/String;)V",
                &[(&error_jstring).into()],
            ) {
                eprintln!("Failed to call onError: {:?}", e);
            }
        }
    }
}


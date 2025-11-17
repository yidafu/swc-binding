use jni::objects::GlobalRef;
use jni::JavaVM;

/// Generic Java callback function
///
/// This function is used to callback Java/Kotlin code from Rust background threads
///
/// # Arguments
/// * `jvm` - JavaVM instance for attaching the current thread
/// * `callback_ref` - Global reference to the callback object
/// * `result` - Operation result, JSON string on success, error message on failure
pub fn callback_java(jvm: JavaVM, callback_ref: GlobalRef, result: Result<String, String>) {
    // Attach current thread to JVM
    let mut env = match jvm.attach_current_thread() {
        Ok(env) => env,
        Err(_) => return,
    };

    match result {
        Ok(json_string) => {
            // Success: call onSuccess(String result)
            let result_jstring = match env.new_string(json_string) {
                Ok(s) => s,
                Err(_) => return,
            };

            let _ = env.call_method(
                &callback_ref,
                "onSuccess",
                "(Ljava/lang/String;)V",
                &[(&result_jstring).into()],
            );
        }
        Err(error) => {
            // Failure: call onError(String error)
            let error_jstring = match env.new_string(error) {
                Ok(s) => s,
                Err(_) => return,
            };

            let _ = env.call_method(
                &callback_ref,
                "onError",
                "(Ljava/lang/String;)V",
                &[(&error_jstring).into()],
            );
        }
    }
}

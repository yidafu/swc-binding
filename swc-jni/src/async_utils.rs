use jni::objects::GlobalRef;
use jni::JavaVM;
use std::panic::{catch_unwind, AssertUnwindSafe};

/// Generic Java callback function
///
/// This function is used to callback Java/Kotlin code from Rust background threads
/// It includes comprehensive error handling to prevent crashes when JVM state is abnormal.
///
/// # Arguments
/// * `jvm` - JavaVM instance for attaching the current thread
/// * `callback_ref` - Global reference to the callback object
/// * `result` - Operation result, JSON string on success, error message on failure
pub fn callback_java(jvm: JavaVM, callback_ref: GlobalRef, result: Result<String, String>) {
    // Wrap the entire callback in panic protection to prevent crashes
    let _callback_result = catch_unwind(AssertUnwindSafe(|| {
        // Attach current thread to JVM
        // If attachment fails, JVM might be in a bad state, so we return early
        let mut env = match jvm.attach_current_thread() {
            Ok(env) => env,
            Err(_) => return,
        };

        // Check if JVM is in a valid state by checking for exceptions
        if env.exception_check().is_err() {
            return;
        }

        // Clear any pending exceptions before proceeding
        if env.exception_check().unwrap_or(false) {
            let _ = env.exception_clear();
        }

        match result {
            Ok(json_string) => {
                // Success: call onSuccess(String result)
                let result_jstring = match env.new_string(json_string) {
                    Ok(s) => s,
                    Err(_) => {
                        // Check for exceptions after failed operation
                        if env.exception_check().unwrap_or(false) {
                            let _ = env.exception_clear();
                        }
                        return;
                    }
                };

                // Call the callback method, but handle errors gracefully
                match env.call_method(
                    &callback_ref,
                    "onSuccess",
                    "(Ljava/lang/String;)V",
                    &[(&result_jstring).into()],
                ) {
                    Ok(_) => {
                        // Check for exceptions after the call
                        if env.exception_check().unwrap_or(false) {
                            let _ = env.exception_clear();
                        }
                    }
                    Err(_) => {
                        // Check for exceptions after failed operation
                        if env.exception_check().unwrap_or(false) {
                            let _ = env.exception_clear();
                        }
                    }
                }
            }
            Err(error) => {
                // Failure: call onError(String error)
                let error_jstring = match env.new_string(error) {
                    Ok(s) => s,
                    Err(_) => {
                        // Check for exceptions after failed operation
                        if env.exception_check().unwrap_or(false) {
                            let _ = env.exception_clear();
                        }
                        return;
                    }
                };

                // Call the callback method, but handle errors gracefully
                match env.call_method(
                    &callback_ref,
                    "onError",
                    "(Ljava/lang/String;)V",
                    &[(&error_jstring).into()],
                ) {
                    Ok(_) => {
                        // Check for exceptions after the call
                        if env.exception_check().unwrap_or(false) {
                            let _ = env.exception_clear();
                        }
                    }
                    Err(_) => {
                        // Check for exceptions after failed operation
                        if env.exception_check().unwrap_or(false) {
                            let _ = env.exception_clear();
                        }
                    }
                }
            }
        }
    }));
}

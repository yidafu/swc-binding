mod async_utils;
mod minify;
mod parse;
mod print;
mod transform;
mod util;

use std::sync::Arc;

use swc_core::base::Compiler;
use swc_core::common::{sync::Lazy, FilePathMapping, SourceMap};

static COMPILER: Lazy<Arc<Compiler>> = Lazy::new(|| {
    let cm = Arc::new(SourceMap::new(FilePathMapping::empty()));

    Arc::new(Compiler::new(cm))
});

fn get_compiler() -> Arc<Compiler> {
    COMPILER.clone()
}

fn get_fresh_compiler() -> Arc<Compiler> {
    let cm = Arc::new(SourceMap::new(FilePathMapping::empty()));
    Arc::new(Compiler::new(cm))
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_get_compiler() {
        let compiler = get_compiler();
        assert!(!Arc::ptr_eq(
            &compiler,
            &Arc::new(Compiler::new(Arc::new(SourceMap::new(
                FilePathMapping::empty()
            ))))
        ));
    }

    #[test]
    fn test_get_compiler_is_singleton() {
        let compiler1 = get_compiler();
        let compiler2 = get_compiler();
        assert!(Arc::ptr_eq(&compiler1, &compiler2));
    }

    #[test]
    fn test_get_fresh_compiler_creates_new_instance() {
        let compiler1 = get_fresh_compiler();
        let compiler2 = get_fresh_compiler();
        assert!(!Arc::ptr_eq(&compiler1, &compiler2), "get_fresh_compiler should create new instances");
    }

    #[test]
    fn test_get_fresh_compiler_different_from_singleton() {
        let singleton = get_compiler();
        let fresh = get_fresh_compiler();
        assert!(!Arc::ptr_eq(&singleton, &fresh), "get_fresh_compiler should create a different instance from singleton");
    }
}

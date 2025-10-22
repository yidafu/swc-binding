mod async_utils;
mod minify;
mod parse;
mod print;
mod transform;
mod util;

use std::sync::Arc;

use swc::Compiler;
use swc_common::{sync::Lazy, FilePathMapping, SourceMap};

static COMPILER: Lazy<Arc<Compiler>> = Lazy::new(|| {
    let cm = Arc::new(SourceMap::new(FilePathMapping::empty()));

    Arc::new(Compiler::new(cm))
});

fn get_compiler() -> Arc<Compiler> {
    COMPILER.clone()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_get_compiler() {
        let compiler = get_compiler();
        assert!(!Arc::ptr_eq(&compiler, &Arc::new(Compiler::new(Arc::new(SourceMap::new(FilePathMapping::empty()))))));
    }

    #[test]
    fn test_get_compiler_is_singleton() {
        let compiler1 = get_compiler();
        let compiler2 = get_compiler();
        assert!(Arc::ptr_eq(&compiler1, &compiler2));
    }
}

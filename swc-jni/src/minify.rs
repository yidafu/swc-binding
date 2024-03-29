use jni::{
    objects::{JClass, JString},
    sys::jstring,
    JNIEnv,
};
use jni_fn::jni_fn;
use serde::Deserialize;

use swc::config::ErrorFormat;
use swc_common::{collections::AHashMap, sync::Lrc, FileName, SourceFile, SourceMap};

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
            MinifyTarget::Single(code) => cm.new_source_file(FileName::Anon, code.clone()),
            MinifyTarget::Map(codes) => {
                assert_eq!(
                    codes.len(),
                    1,
                    "swc.minify does not support concatting multiple files yet"
                );

                let (filename, code) = codes.iter().next().unwrap();

                cm.new_source_file(FileName::Real(filename.clone().into()), code.clone())
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
        |handler| c.minify(fm, handler, &opts),
    )
    .convert_err();

    process_output(env, result)
}

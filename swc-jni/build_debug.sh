#!/bin/bash
cargo build --target x86_64-apple-darwin
rm -fr ../swc-binding/src/main/resources/darwin-x64
mkdir -p ../swc-binding/src/main/resources/darwin-x64
cp ./target/x86_64-apple-darwin/debug/libswc_jni.dylib ../swc-binding/src/main/resources/darwin-x64/
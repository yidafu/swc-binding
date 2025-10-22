#!/bin/bash
rm -fr ../swc-binding/src/main/resources/*

mkdir -p ../swc-binding/src/main/resources/darwin-x64
mkdir -p ../swc-binding/src/main/resources/darwin-arm64
# mkdir -p ../swc-binding/src/main/resources/darwin-universal
# mkdir -p ../swc-binding/src/main/resources/linux-x64-musl
mkdir -p ../swc-binding/src/main/resources/linux-x64-gnu
mkdir -p ../swc-binding/src/main/resources/linux-arm-gnueabihf
mkdir -p ../swc-binding/src/main/resources/win32-x64-msvc
mkdir -p ../swc-binding/src/main/resources/win32-arm64-msvc

############################## Mac OS X ##############################
# https://gist.github.com/surpher/bbf88e191e9d1f01ab2e2bbb85f9b528
cargo build --release --target x86_64-apple-darwin
cp ./target/x86_64-apple-darwin/release/libswc_jni.dylib  ../swc-binding/src/main/resources/darwin-x64
cargo build --release --target aarch64-apple-darwin
cp ./target/aarch64-apple-darwin/release/libswc_jni.dylib  ../swc-binding/src/main/resources/darwin-arm64
# cargo zigbuild --release --target universal2-apple-darwin
# cp ./target/universal2-apple-darwin/release/libswc_jni.dylib  ../swc-binding/src/main/resources/darwin-universal
############################## Mac OS X ##############################

# Alternative zigbuild
# TARGET_CC=x86_64-linux-musl-gcc cargo build --release --target x86_64-unknown-linux-musl
# cp target/x86_64-unknown-linux-musl/release/libswc_jni.so ../swc-binding/src/main/resources/linux-x64-musl

############################## Linux ##############################
cargo zigbuild --release --target arm-unknown-linux-gnueabihf
cp target/arm-unknown-linux-gnueabihf/release/libswc_jni.so ../swc-binding/src/main/resources/linux-arm-gnueabihf
cargo build --release --target x86_64-unknown-linux-gnu
cp target/x86_64-unknown-linux-gnu/release/libswc_jni.so ../swc-binding/src/main/resources/linux-x64-gnu
############################## Linux ##############################

############################## Windows ##############################
# cargo build --release --target x86_64-pc-windows-gnu
cargo xwin build --release --target x86_64-pc-windows-msvc
cp target/x86_64-pc-windows-msvc/release/swc_jni.dll ../swc-binding/src/main/resources/win32-x64-msvc
cargo xwin build --release --target aarch64-pc-windows-msvc
cp target/aarch64-pc-windows-msvc/release/swc_jni.dll ../swc-binding/src/main/resources/win32-arm64-msvc
############################## Windows ##############################
#!/bin/bash

sudo apt update
sudo apt upgrade
sudo apt install build-essential
sudo apt install llvm
cargo install cargo-zigbuild
cargo install cargo-xwin

cd swc-jni

cargo zigbuild --release --target x86_64-apple-darwin
cargo zigbuild --release --target aarch64-apple-darwin
cargo zigbuild --release --target universal2-apple-darwin
cargo zigbuild --release --target x86_64-unknown-linux-gnu
cargo zigbuild --release --target aarch64-unknown-linux-gnu
cargo xwin build --release --target x86_64-pc-windows-msvc


mkdir -p ../swc-binding/src/main/resources/darwin-x64
mkdir -p ../swc-binding/src/main/resources/darwin-arm64
mkdir -p ../swc-binding/src/main/resources/linux-x64-gnu
mkdir -p ../swc-binding/src/main/resources/linux-aarch64
mkdir -p ../swc-binding/src/main/resources/win32-x64-msvc

cp ./target/x86_64-apple-darwin/release/libswc_jni.dylib  ../swc-binding/src/main/resources/darwin-x64
cp ./target/aarch64-apple-darwin/release/libswc_jni.dylib  ../swc-binding/src/main/resources/darwin-arm64
cp target/arm-unknown-linux-aarch64/release/libswc_jni.so ../swc-binding/src/main/resources/linux-arm-aarch64
cp target/x86_64-unknown-linux-gnu/release/libswc_jni.so ../swc-binding/src/main/resources/linux-x64-gnu
cp target/x86_64-pc-windows-msvc/release/swc_jni.dll ../swc-binding/src/main/resources/win32-x64-msvc

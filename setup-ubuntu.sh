#!/bin/bash

sudo apt update
sudo apt upgrade
sudo apt install build-essential
sudo apt install llvm
cargo install cargo-zigbuild
cargo install cargo-xwin

cd swc-jni

cargo zigbuild --release --target x86_64-unknown-linux-gnu
cargo zigbuild --release --target aarch64-unknown-linux-gnu

mkdir -p ../swc-binding/src/main/resources/linux-x64-gnu
mkdir -p ../swc-binding/src/main/resources/linux-aarch64-gnu

cp target/arm-unknown-linux-aarch64/release/libswc_jni.so ../swc-binding/src/main/resources/linux-aarch64-gnu
cp target/x86_64-unknown-linux-gnu/release/libswc_jni.so ../swc-binding/src/main/resources/linux-x64-gnu
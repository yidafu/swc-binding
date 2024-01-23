#!/bin/bash

sudo apt update
sudo apt upgrade
sudo apt install build-essential
cargo install cargo-zigbuild
cargo install cargo-xwin

cd swc-jni

cargo zigbuild --release --target x86_64-apple-darwin
cargo zigbuild --release --target aarch64-apple-darwin
cargo zigbuild --release --target universal2-apple-darwin
cargo zigbuild --release --target x86_64-unknown-linux-gnu
cargo zigbuild --release --target aarch64-unknown-linux-gnu
cargo xwin build --release --target x86_64-pc-windows-msvc

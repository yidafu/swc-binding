#!/bin/bash
rm -fr ../swc-binding/src/main/resources/*
cargo zigbuild --release --target x86_64-apple-darwin
cargo zigbuild --release --target aarch64-apple-darwin
cargo zigbuild --release --target universal2-apple-darwin
cargo zigbuild --release --target x86_64-unknown-linux-gnu
cargo zigbuild --release --target aarch64-unknown-linux-gnu
cargo xwin build --release --target x86_64-pc-windows-msvc

# Create resource directories for all supported platforms
# Format: {os}-{arch}-{toolchain}
mkdir -p ../swc-binding/src/main/resources/darwin-x64-apple     # macOS Intel (Apple toolchain)
mkdir -p ../swc-binding/src/main/resources/darwin-arm64-apple   # macOS ARM64 (Apple toolchain)
# mkdir -p ../swc-binding/src/main/resources/darwin-universal-apple  # macOS Universal Binary
mkdir -p ../swc-binding/src/main/resources/linux-x64-musl       # Linux x64 (musl static)
mkdir -p ../swc-binding/src/main/resources/linux-arm64-gnu      # Linux ARM64 (GNU toolchain)
mkdir -p ../swc-binding/src/main/resources/windows-x64-gnu      # Windows x64 (GNU toolchain)
mkdir -p ../swc-binding/src/main/resources/windows-arm64-gnu    # Windows ARM64 (GNU toolchain)

############################## Mac OS X ##############################
# https://gist.github.com/surpher/bbf88e191e9d1f01ab2e2bbb85f9b528
cargo zigbuild --release --target x86_64-apple-darwin
cp ./target/x86_64-apple-darwin/release/libswc_jni.dylib ../swc-binding/src/main/resources/darwin-x64-apple
cargo zigbuild --release --target aarch64-apple-darwin
cp ./target/aarch64-apple-darwin/release/libswc_jni.dylib ../swc-binding/src/main/resources/darwin-arm64-apple
# cargo zigbuild --release --target universal2-apple-darwin
# cp ./target/universal2-apple-darwin/release/libswc_jni.dylib  ../swc-binding/src/main/resources/darwin-universal
############################## Mac OS X ##############################


############################## Linux ##############################
# Linux x64 using musl for static linking and better compatibility
cargo zigbuild --release --target x86_64-unknown-linux-musl
cp ./target/x86_64-unknown-linux-musl/release/libswc_jni.so ../swc-binding/src/main/resources/linux-x64-musl
# Linux ARM64 using gnu for better compatibility (musl doesn't support cdylib)
cargo zigbuild --release --target aarch64-unknown-linux-gnu
cp ./target/aarch64-unknown-linux-gnu/release/libswc_jni.so ../swc-binding/src/main/resources/linux-arm64-gnu
############################## Linux ##############################

############################## Windows ##############################
# Windows x64 using zigbuild with GNU toolchain for better compatibility
cargo zigbuild --release --target x86_64-pc-windows-gnu
cp ./target/x86_64-pc-windows-gnu/release/swc_jni.dll ../swc-binding/src/main/resources/windows-x64-gnu
# Windows ARM64 using zigbuild with gnullvm target for better compatibility
cargo zigbuild --release --target aarch64-pc-windows-gnullvm
cp ./target/aarch64-pc-windows-gnullvm/release/swc_jni.dll ../swc-binding/src/main/resources/windows-arm64-gnu
############################## Windows ##############################

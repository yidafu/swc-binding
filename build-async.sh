#!/bin/bash

# 异步 JNI 方法构建脚本
# 此脚本用于编译 Rust 代码、复制动态库、生成 JNI 头文件并运行测试

set -e  # 遇到错误立即退出

echo "============================================"
echo "  Building Async JNI Methods"
echo "============================================"

# 获取脚本所在目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# 检测操作系统
OS="$(uname -s)"
case "${OS}" in
    Linux*)     PLATFORM=linux;;
    Darwin*)    PLATFORM=macos;;
    CYGWIN*|MINGW*|MSYS*) PLATFORM=windows;;
    *)          PLATFORM="unknown";;
esac

echo "Platform detected: $PLATFORM"

# Step 1: 编译 Rust 代码
echo ""
echo "Step 1: Compiling Rust code..."
echo "----------------------------------------"
cd swc-jni

# 可选：先清理之前的构建
# cargo clean

# 编译（release 模式）
echo "Running: cargo build --release"
cargo build --release

if [ $? -ne 0 ]; then
    echo "❌ Rust compilation failed!"
    exit 1
fi

echo "✅ Rust compilation successful"

# Step 2: 复制动态库到正确位置
echo ""
echo "Step 2: Copying native library..."
echo "----------------------------------------"

TARGET_DIR="target/release"
DEST_DIR="../swc-binding/src/main/resources"

# 创建目标目录（如果不存在）
mkdir -p "$DEST_DIR"

case "${PLATFORM}" in
    linux)
        LIB_FILE="libswc_jni.so"
        ;;
    macos)
        LIB_FILE="libswc_jni.dylib"
        ;;
    windows)
        LIB_FILE="swc_jni.dll"
        ;;
    *)
        echo "❌ Unsupported platform: $PLATFORM"
        exit 1
        ;;
esac

if [ -f "$TARGET_DIR/$LIB_FILE" ]; then
    cp "$TARGET_DIR/$LIB_FILE" "$DEST_DIR/"
    echo "✅ Copied $LIB_FILE to $DEST_DIR/"
else
    echo "❌ Library file not found: $TARGET_DIR/$LIB_FILE"
    exit 1
fi

cd ..

# Step 3: 编译 Kotlin 代码并生成 JNI 头文件
echo ""
echo "Step 3: Building Kotlin project..."
echo "----------------------------------------"

echo "Running: ./gradlew :swc-binding:build"
./gradlew :swc-binding:build

if [ $? -ne 0 ]; then
    echo "❌ Kotlin build failed!"
    exit 1
fi

echo "✅ Kotlin build successful"

# Step 4: 运行测试
echo ""
echo "Step 4: Running tests..."
echo "----------------------------------------"

echo "Running async tests..."
./gradlew :swc-binding:test --tests "dev.yidafu.swc.AsyncParseTest"

if [ $? -ne 0 ]; then
    echo "⚠️  Some tests failed, but continuing..."
else
    echo "✅ All tests passed"
fi

# Step 5: 显示构建摘要
echo ""
echo "============================================"
echo "  Build Summary"
echo "============================================"
echo "✅ Rust code compiled"
echo "✅ Native library copied to resources"
echo "✅ Kotlin code compiled"
echo "✅ Tests executed"
echo ""
echo "Native library location:"
echo "  $DEST_DIR/$LIB_FILE"
echo ""
echo "To run the examples:"
echo "  ./gradlew :swc-binding:run -PmainClass=dev.yidafu.swc.sample.AsyncSamplesKt"
echo ""
echo "To run tests:"
echo "  ./gradlew :swc-binding:test --tests 'dev.yidafu.swc.AsyncParseTest'"
echo ""
echo "============================================"
echo "  Build Complete!"
echo "============================================"


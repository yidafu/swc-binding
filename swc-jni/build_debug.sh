#!/bin/bash

# 检测本地 CPU 架构
ARCH=$(uname -m)

if [ "$ARCH" = "arm64" ]; then
    # Apple Silicon (M1/M2/M3 等)
    TARGET="aarch64-apple-darwin"
    RESOURCE_DIR="darwin-arm64-apple"
elif [ "$ARCH" = "x86_64" ]; then
    # Intel Mac
    TARGET="x86_64-apple-darwin"
    RESOURCE_DIR="darwin-x64-apple"
else
    echo "错误: 不支持的架构: $ARCH"
    exit 1
fi

echo "检测到架构: $ARCH"
echo "构建目标: $TARGET"
echo "资源目录: $RESOURCE_DIR"

# 构建
cargo build --target $TARGET

# 清理并创建资源目录
rm -fr ../swc-binding/src/main/resources/$RESOURCE_DIR
mkdir -p ../swc-binding/src/main/resources/$RESOURCE_DIR

# 复制动态库
cp ./target/$TARGET/debug/libswc_jni.dylib ../swc-binding/src/main/resources/$RESOURCE_DIR/
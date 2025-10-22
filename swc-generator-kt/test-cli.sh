#!/bin/bash

# SWC Generator Kotlin - 命令行参数测试脚本
# 展示使用 kotlinx-cli 的各种方式

set -e

echo "========================================="
echo "SWC Generator Kotlin - CLI 测试"
echo "========================================="
echo ""

# 测试 1: 显示帮助信息
echo "测试 1: 显示帮助信息"
echo "命令: ./gradlew :swc-generator-kt:run --args='--help'"
echo ""
./gradlew :swc-generator-kt:run --args="--help" --quiet
echo ""
echo "----------------------------------------"
echo ""

# 测试 2: 使用位置参数（最简单的方式）
echo "测试 2: 使用位置参数"
echo "命令: ./gradlew run --args='test-simple.d.ts'"
echo ""
echo "按 Enter 继续..."
read
./gradlew :swc-generator-kt:run --args="test-simple.d.ts" --quiet || true
echo ""
echo "----------------------------------------"
echo ""

# 测试 3: 使用 -i 参数指定输入文件
echo "测试 3: 使用短选项 -i"
echo "命令: ./gradlew run --args='-i tests/01-base-types.d.ts'"
echo ""
echo "按 Enter 继续..."
read
./gradlew :swc-generator-kt:run --args="-i tests/01-base-types.d.ts" --quiet || true
echo ""
echo "----------------------------------------"
echo ""

# 测试 4: 使用 --input 参数（长选项）
echo "测试 4: 使用长选项 --input"
echo "命令: ./gradlew run --args='--input tests/01-base-types.d.ts'"
echo ""
echo "按 Enter 继续..."
read
./gradlew :swc-generator-kt:run --args="--input tests/01-base-types.d.ts" --quiet || true
echo ""
echo "----------------------------------------"
echo ""

# 测试 5: 指定所有输出路径
echo "测试 5: 指定所有输出路径"
echo "命令: ./gradlew run --args='-i test-simple.d.ts -o output/types.kt -s output/serializer.kt -d output/dsl'"
echo ""
echo "按 Enter 继续..."
read
./gradlew :swc-generator-kt:run --args="-i test-simple.d.ts -o output/types.kt -s output/serializer.kt -d output/dsl" --quiet || true
echo ""
echo "----------------------------------------"
echo ""

# 测试 6: 启用详细日志
echo "测试 6: 启用详细日志 (-v)"
echo "命令: ./gradlew run --args='-i test-simple.d.ts -v'"
echo ""
echo "按 Enter 继续..."
read
./gradlew :swc-generator-kt:run --args="-i test-simple.d.ts -v" --quiet || true
echo ""
echo "----------------------------------------"
echo ""

# 测试 7: 启用调试日志
echo "测试 7: 启用调试日志 (--debug)"
echo "命令: ./gradlew run --args='-i test-simple.d.ts --debug'"
echo ""
echo "按 Enter 继续..."
read
./gradlew :swc-generator-kt:run --args="-i test-simple.d.ts --debug" --quiet || true
echo ""
echo "----------------------------------------"
echo ""

# 测试 8: Dry-run 模式
echo "测试 8: Dry-run 模式（不实际生成文件）"
echo "命令: ./gradlew run --args='-i test-simple.d.ts --dry-run -v'"
echo ""
echo "按 Enter 继续..."
read
./gradlew :swc-generator-kt:run --args="-i test-simple.d.ts --dry-run -v" --quiet || true
echo ""
echo "----------------------------------------"
echo ""

# 测试 9: 组合多个选项
echo "测试 9: 组合多个选项"
echo "命令: ./gradlew run --args='-i tests/01-base-types.d.ts -o output/types.kt --verbose --dry-run'"
echo ""
echo "按 Enter 继续..."
read
./gradlew :swc-generator-kt:run --args="-i tests/01-base-types.d.ts -o output/types.kt --verbose --dry-run" --quiet || true
echo ""
echo "----------------------------------------"
echo ""

# 测试 10: 错误处理 - 缺少输入文件
echo "测试 10: 错误处理 - 缺少输入文件"
echo "命令: ./gradlew run --args=''"
echo ""
echo "按 Enter 继续..."
read
./gradlew :swc-generator-kt:run --args="" --quiet || true
echo ""
echo "----------------------------------------"
echo ""

echo "========================================="
echo "所有测试完成！"
echo "========================================="
echo ""
echo "常用命令示例："
echo ""
echo "1. 最简单的用法："
echo "   ./gradlew :swc-generator-kt:run --args='test-simple.d.ts'"
echo ""
echo "2. 指定输出路径："
echo "   ./gradlew :swc-generator-kt:run --args='-i input.d.ts -o types.kt -s serializer.kt -d dsl/'"
echo ""
echo "3. 启用详细日志："
echo "   ./gradlew :swc-generator-kt:run --args='-i input.d.ts -v'"
echo ""
echo "4. Dry-run 模式（查看将生成什么而不实际生成）："
echo "   ./gradlew :swc-generator-kt:run --args='-i input.d.ts --dry-run -v'"
echo ""
echo "5. 查看帮助："
echo "   ./gradlew :swc-generator-kt:run --args='--help'"
echo ""


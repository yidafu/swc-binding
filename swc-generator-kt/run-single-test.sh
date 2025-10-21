#!/bin/bash
# 运行单个测试文件

if [ $# -eq 0 ]; then
    echo "用法: ./run-single-test.sh <测试编号>"
    echo ""
    echo "可用的测试:"
    echo "  01 - 基础类型配置"
    echo "  02 - 字面量联合类型"
    echo "  03 - 混合联合类型"
    echo "  04 - 交叉类型"
    echo "  05 - 引用联合类型"
    echo "  06 - 接口继承关系"
    echo "  07 - 数组类型"
    echo "  08 - 可选属性"
    echo "  09 - 索引签名"
    echo "  10 - 泛型类型"
    echo "  11 - 函数类型"
    echo "  12 - 递归类型"
    echo ""
    echo "示例: ./run-single-test.sh 01"
    exit 1
fi

# 颜色定义
BLUE='\033[0;34m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

TEST_NUM=$1
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TEST_FILE="$SCRIPT_DIR/tests/${TEST_NUM}-*.d.ts"

# 查找测试文件
TEST_FILE=$(find "$SCRIPT_DIR/tests" -name "${TEST_NUM}-*.d.ts" | head -n 1)

if [ -z "$TEST_FILE" ]; then
    echo "错误: 找不到测试 $TEST_NUM"
    exit 1
fi

echo -e "${BLUE}运行测试: $(basename "$TEST_FILE")${NC}"
echo ""

# 使用命令行参数运行
cd "$(dirname "$SCRIPT_DIR")" && ./gradlew :swc-generator-kt:run --args="$TEST_FILE"

echo ""
echo -e "${GREEN}测试完成！${NC}"
echo -e "${YELLOW}查看生成的代码:${NC}"
echo "  types.kt: $SCRIPT_DIR/swc/types/dev/yidafu/swc/types/types.kt"
echo "  serializer.kt: $SCRIPT_DIR/swc/types/dev/yidafu/swc/types/serializer.kt"
echo "  dsl/: $SCRIPT_DIR/swc/dsl/"


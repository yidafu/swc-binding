#!/bin/bash
# 批量执行所有测试 .d.ts 文件

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 项目根目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

# 测试目录
TESTS_DIR="$SCRIPT_DIR/tests"
OUTPUT_DIR="$SCRIPT_DIR/test-outputs"

# 创建输出目录
mkdir -p "$OUTPUT_DIR"

# 统计变量
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

echo -e "${BLUE}======================================${NC}"
echo -e "${BLUE}    SWC Generator Kotlin - 测试套件${NC}"
echo -e "${BLUE}======================================${NC}"
echo ""

# 清理之前的输出
rm -rf "$OUTPUT_DIR"/*
echo -e "${YELLOW}清理之前的测试输出...${NC}"
echo ""

# 获取所有测试文件
TEST_FILES=($(find "$TESTS_DIR" -name "*.d.ts" | sort))

if [ ${#TEST_FILES[@]} -eq 0 ]; then
    echo -e "${RED}错误: 没有找到测试文件${NC}"
    exit 1
fi

echo -e "${GREEN}找到 ${#TEST_FILES[@]} 个测试文件${NC}"
echo ""

# 遍历执行每个测试
for test_file in "${TEST_FILES[@]}"; do
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    
    test_name=$(basename "$test_file" .d.ts)
    echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
    echo -e "${BLUE}测试 $TOTAL_TESTS/${#TEST_FILES[@]}: $test_name${NC}"
    echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
    
    # 创建测试专用输出目录
    test_output_dir="$OUTPUT_DIR/$test_name"
    mkdir -p "$test_output_dir/types"
    mkdir -p "$test_output_dir/dsl"
    
    # 设置环境变量
    export SWC_TYPES_PATH="$test_file"
    
    # 执行测试
    echo -e "${YELLOW}执行: $test_file${NC}"
    
    if cd "$PROJECT_ROOT" && ./gradlew :swc-generator:run --args="$test_file" --quiet > "$test_output_dir/output.log" 2>&1; then
        PASSED_TESTS=$((PASSED_TESTS + 1))
        echo -e "${GREEN}✓ 测试通过${NC}"
        
        # 复制生成的文件到测试输出目录
        # KotlinPoet 按包名生成目录结构
        TYPES_FILE="$SCRIPT_DIR/swc/types/dev/yidafu/swc/types/types.kt"
        SERIALIZER_FILE="$SCRIPT_DIR/swc/types/dev/yidafu/swc/types/serializer.kt"
        DSL_DIR="$SCRIPT_DIR/swc/dsl"
        
        if [ -f "$TYPES_FILE" ]; then
            cp "$TYPES_FILE" "$test_output_dir/types/"
            echo -e "  生成: types.kt ($(wc -l < "$TYPES_FILE") 行)"
        fi
        
        if [ -f "$SERIALIZER_FILE" ]; then
            cp "$SERIALIZER_FILE" "$test_output_dir/types/"
            serializer_lines=$(wc -l < "$SERIALIZER_FILE")
            echo -e "  生成: serializer.kt ($serializer_lines 行)"
        fi
        
        if [ -d "$DSL_DIR" ]; then
            cp -r "$DSL_DIR/"* "$test_output_dir/dsl/" 2>/dev/null || true
            dsl_count=$(find "$DSL_DIR" -name "*.kt" 2>/dev/null | wc -l)
            echo -e "  生成: $dsl_count 个 DSL 文件"
        fi
        
    else
        FAILED_TESTS=$((FAILED_TESTS + 1))
        echo -e "${RED}✗ 测试失败${NC}"
        echo -e "${RED}查看日志: $test_output_dir/output.log${NC}"
        
        # 显示错误信息的最后几行
        echo -e "${YELLOW}错误信息:${NC}"
        tail -n 20 "$test_output_dir/output.log" | sed 's/^/  /'
    fi
    
    echo ""
done

# 生成测试报告
REPORT_FILE="$OUTPUT_DIR/test-report.txt"

cat > "$REPORT_FILE" << EOF
======================================
    测试报告
======================================

执行时间: $(date '+%Y-%m-%d %H:%M:%S')

总测试数: $TOTAL_TESTS
通过: $PASSED_TESTS
失败: $FAILED_TESTS
成功率: $(awk "BEGIN {printf \"%.2f\", ($PASSED_TESTS/$TOTAL_TESTS)*100}")%

======================================
    详细结果
======================================

EOF

# 添加每个测试的详细信息
for test_file in "${TEST_FILES[@]}"; do
    test_name=$(basename "$test_file" .d.ts)
    test_output_dir="$OUTPUT_DIR/$test_name"
    
    if [ -f "$test_output_dir/types/types.kt" ]; then
        status="✓ 通过"
        types_lines=$(wc -l < "$test_output_dir/types/types.kt")
        dsl_count=$(find "$test_output_dir/dsl" -name "*.kt" 2>/dev/null | wc -l)
    else
        status="✗ 失败"
        types_lines=0
        dsl_count=0
    fi
    
    cat >> "$REPORT_FILE" << EOF
测试: $test_name
状态: $status
生成代码: types.kt ($types_lines 行), $dsl_count 个 DSL 文件
输出目录: $test_output_dir

EOF
done

# 显示总结
echo -e "${BLUE}======================================${NC}"
echo -e "${BLUE}    测试总结${NC}"
echo -e "${BLUE}======================================${NC}"
echo ""
echo -e "总测试数: $TOTAL_TESTS"
echo -e "${GREEN}通过: $PASSED_TESTS${NC}"
echo -e "${RED}失败: $FAILED_TESTS${NC}"
echo -e "成功率: $(awk "BEGIN {printf \"%.2f\", ($PASSED_TESTS/$TOTAL_TESTS)*100}")%"
echo ""
echo -e "测试报告: ${YELLOW}$REPORT_FILE${NC}"
echo -e "输出目录: ${YELLOW}$OUTPUT_DIR${NC}"
echo ""

# 退出码
if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "${GREEN}所有测试通过！${NC}"
    exit 0
else
    echo -e "${RED}有 $FAILED_TESTS 个测试失败${NC}"
    exit 1
fi


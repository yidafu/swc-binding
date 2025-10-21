// 测试 2: 字面量联合类型
// 处理函数: processLiteralUnion
// 预期: 生成 object 包含常量属性

// 字符串字面量联合
type MethodKind = "method" | "getter" | "setter";

// 操作符字面量联合（测试 literalNameMap）
type BinaryOperator = "+" | "-" | "*" | "/" | "%";

// 更多操作符
type ComparisonOperator = "==" | "!=" | "===" | "!==" | "<" | ">" | "<=" | ">=";

// 数字字面量联合
type StatusCode = 200 | 404 | 500;


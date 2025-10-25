// 测试 2: SWC 字面量联合类型
// 处理函数: processLiteralUnion
// 预期: 生成 object 包含常量属性

// 字符串字面量联合
export type MethodKind = "method" | "getter" | "setter";

// 操作符字面量联合（测试 literalNameMap）
export type BinaryOperator = "+" | "-" | "*" | "/" | "%";

// 更多操作符
export type ComparisonOperator = "==" | "!=" | "===" | "!==" | "<" | ">" | "<=" | ">=";

// 数字字面量联合
export type StatusCode = 200 | 404 | 500;

// SWC 目标版本
export type JscTarget = "es3" | "es5" | "es2015" | "es2016" | "es2017" | "es2018" | "es2019" | "es2020" | "es2021" | "es2022" | "es2023" | "es2024" | "esnext";

// 变量声明类型
export type VariableDeclarationKind = "var" | "let" | "const";

// 可访问性修饰符
export type Accessibility = "public" | "private" | "protected";


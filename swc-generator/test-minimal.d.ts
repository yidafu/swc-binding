// 最小测试文件 - 覆盖所有 Generator 处理逻辑

/* ========== 1. 基础类型（Base Types）========== */
// Span 会被转换为 Kotlin class（toKotlinClass 配置）
interface Span {
    start: number;
    end: number;
    ctxt: number;
}

// Node 是 sealed interface（sealedInterface 配置）
interface Node {
    type: string;
}

// HasSpan 是保留接口（keepInterface 配置，不生成 Impl）
interface HasSpan {
    span: Span;
}

/* ========== 2. 字面量联合类型（Literal Union）========== */
// 处理函数: processLiteralUnion
// 生成为 object，包含常量属性
type MethodKind = "method" | "getter" | "setter";
type BinaryOp = "+" | "-" | "*" | "/" | "%";

/* ========== 3. 混合联合类型（Mixed Union）========== */
// 处理函数: processMixedUnion
// 生成为 typealias，可能是 Union.U2, Union.U3 等
type SimpleValue = string | number;
type MixedType = string | number | boolean;

/* ========== 4. 交叉类型（Intersection Type）========== */
// 处理函数: processIntersectionType
// 生成 Base interface 和 实现类
interface ExpressionBase extends Node, HasSpan {
    baseField: string;
}

/* ========== 5. 引用联合类型（Ref Union）========== */
// 处理函数: processRefUnion
// 生成 sealed interface，建立继承关系
type Expression = Identifier | StringLiteral | NumericLiteral;

/* ========== 6. 标准接口（Normal Interface）========== */
// 处理函数: processInterfaces
// 基本接口，继承 ExpressionBase
interface Identifier extends ExpressionBase {
    type: "Identifier";
    value: string;
    optional: boolean;
}

interface StringLiteral extends Node, HasSpan {
    type: "StringLiteral";
    value: string;
    raw?: string;
}

interface NumericLiteral extends Node, HasSpan {
    type: "NumericLiteral";
    value: number;
    raw?: string;
}

/* ========== 7. 数组类型 ========== */
interface ArrayContainer {
    items: string[];
    elements: (Expression | undefined)[];
}

/* ========== 8. 可选属性 ========== */
interface OptionalFields {
    required: string;
    optional?: number;
    readonly readonlyField: boolean;
}

/* ========== 9. 索引签名 ========== */
interface IndexSignature {
    [key: string]: any;
}

/* ========== 10. 泛型类型 ========== */
interface GenericInterface<T> {
    value: T;
}

/* ========== 11. 函数类型 ========== */
interface FunctionTypes {
    callback: (arg: string) => number;
}

/* ========== 12. 递归类型 ========== */
type Pattern = Identifier | ArrayPattern;

interface ArrayPattern extends Node, HasSpan {
    type: "ArrayPattern";
    elements: (Pattern | undefined)[];
}


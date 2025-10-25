// 测试 3: SWC 混合联合类型
// 处理函数: processMixedUnion
// 预期: 生成 typealias 或 Union.U2, Union.U3 等

// 双类型联合
export type StringOrNumber = string | number;
export type BooleanOrString = boolean | string;

// 三类型联合
export type TripleUnion = string | number | boolean;

// 包含 undefined 的联合
export type MaybeString = string | undefined;
export type NullableNumber = number | null;

// 复杂混合
export type MixedValue = string | number | boolean | null;

// SWC 表达式联合类型
export type Expression = Identifier | StringLiteral | BinaryExpression | CallExpression;

// SWC 语句联合类型
export type Statement = BlockStatement | ExpressionStatement | IfStatement | ReturnStatement;

// SWC 模式联合类型
export type Pattern = BindingIdentifier | ArrayPattern | ObjectPattern;

// 基础类型定义
export interface Identifier {
    type: "Identifier";
    value: string;
    optional: boolean;
}

export interface StringLiteral {
    type: "StringLiteral";
    value: string;
    raw?: string;
}

export interface BinaryExpression {
    type: "BinaryExpression";
    left: Expression;
    right: Expression;
    operator: BinaryOperator;
}

export interface CallExpression {
    type: "CallExpression";
    callee: Expression;
    arguments: any[];
}

export interface BlockStatement {
    type: "BlockStatement";
    stmts: Statement[];
}

export interface ExpressionStatement {
    type: "ExpressionStatement";
    expression: Expression;
}

export interface IfStatement {
    type: "IfStatement";
    test: Expression;
    consequent: Statement;
    alternate?: Statement;
}

export interface ReturnStatement {
    type: "ReturnStatement";
    argument?: Expression;
}

export interface BindingIdentifier {
    type: "Identifier";
    value: string;
    optional: boolean;
}

export interface ArrayPattern {
    type: "ArrayPattern";
    elements: (Pattern | undefined)[];
    optional: boolean;
}

export interface ObjectPattern {
    type: "ObjectPattern";
    properties: any[];
    optional: boolean;
}

export type BinaryOperator = "+" | "-" | "*" | "/" | "%";


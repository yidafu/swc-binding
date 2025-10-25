// 测试 12: SWC 递归类型
// 测试类型的递归引用

export interface TreeNode {
    value: string;
    children?: TreeNode[];
}

// 互相递归
export interface NodeA {
    type: "A";
    next?: NodeB;
}

export interface NodeB {
    type: "B";
    next?: NodeA;
}

// 递归联合类型
export type RecursiveUnion = string | RecursiveUnion[];

// 链表
export interface ListNode {
    data: number;
    next?: ListNode;
}

// SWC AST 递归类型
export interface Program {
    type: string;
    body: ModuleItem[];
}

export type ModuleItem = ModuleDeclaration | Statement;

export interface Module extends Program {
    type: "Module";
    body: ModuleItem[];
}

export interface Script extends Program {
    type: "Script";
    body: Statement[];
}

// SWC 表达式递归
export type Expression = Identifier | StringLiteral | BinaryExpression | CallExpression | ArrayExpression | ObjectExpression;

export interface ArrayExpression extends ExpressionBase {
    type: "ArrayExpression";
    elements: (ExprOrSpread | undefined)[];
}

export interface ObjectExpression extends ExpressionBase {
    type: "ObjectExpression";
    properties: Property[];
}

// SWC 语句递归
export type Statement = BlockStatement | ExpressionStatement | IfStatement | ReturnStatement | ForStatement | WhileStatement;

export interface ForStatement extends Node, HasSpan {
    type: "ForStatement";
    init?: VariableDeclaration | Expression;
    test?: Expression;
    update?: Expression;
    body: Statement;
}

export interface WhileStatement extends Node, HasSpan {
    type: "WhileStatement";
    test: Expression;
    body: Statement;
}

// 基础类型
export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
}

export interface Node {
    type: string;
}

export interface HasSpan {
    span: Span;
}

export interface Span {
    start: number;
    end: number;
    ctxt: number;
}

export type ModuleDeclaration = any;
export type Statement = any;
export type Identifier = any;
export type StringLiteral = any;
export type BinaryExpression = any;
export type CallExpression = any;
export type ExprOrSpread = any;
export type Property = any;
export type BlockStatement = any;
export type ExpressionStatement = any;
export type IfStatement = any;
export type ReturnStatement = any;
export type VariableDeclaration = any;
export type Expression = any;


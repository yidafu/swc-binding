// 测试 9: SWC JSX 类型
// 测试 JSX 元素和属性类型

export interface Span {
    start: number;
    end: number;
    ctxt: number;
}

export interface Node {
    type: string;
}

export interface HasSpan {
    span: Span;
}

export interface JSXElement extends Node, HasSpan {
    type: "JSXElement";
    opening: JSXOpeningElement;
    children: JSXElementChild[];
    closing?: JSXClosingElement;
}

export interface JSXFragment extends Node, HasSpan {
    type: "JSXFragment";
    opening: JSXOpeningFragment;
    children: JSXElementChild[];
    closing: JSXClosingFragment;
}

export interface JSXOpeningElement extends Node, HasSpan {
    type: "JSXOpeningElement";
    name: JSXElementName;
    attributes: JSXAttributeOrSpread[];
    selfClosing: boolean;
}

export interface JSXClosingElement extends Node, HasSpan {
    type: "JSXClosingElement";
    name: JSXElementName;
}

export interface JSXOpeningFragment extends Node, HasSpan {
    type: "JSXOpeningFragment";
}

export interface JSXClosingFragment extends Node, HasSpan {
    type: "JSXClosingFragment";
}

export type JSXElementName = Identifier | JSXMemberExpression | JSXNamespacedName;

export interface JSXMemberExpression extends Node {
    type: "JSXMemberExpression";
    object: JSXObject;
    property: Identifier;
}

export interface JSXNamespacedName extends Node {
    type: "JSXNamespacedName";
    namespace: Identifier;
    name: Identifier;
}

export type JSXObject = JSXMemberExpression | Identifier;

export type JSXElementChild = JSXText | JSXExpressionContainer | JSXSpreadChild | JSXElement | JSXFragment;

export interface JSXText extends Node, HasSpan {
    type: "JSXText";
    value: string;
    raw?: string;
}

export interface JSXExpressionContainer extends Node, HasSpan {
    type: "JSXExpressionContainer";
    expression: JSXExpression;
}

export interface JSXSpreadChild extends Node, HasSpan {
    type: "JSXSpreadChild";
    expression: Expression;
}

export type JSXExpression = JSXEmptyExpression | Expression;

export interface JSXEmptyExpression extends Node, HasSpan {
    type: "JSXEmptyExpression";
}

export type JSXAttributeOrSpread = JSXAttribute | SpreadElement;

export interface JSXAttribute extends Node, HasSpan {
    type: "JSXAttribute";
    name: JSXAttributeName;
    value?: JSXAttrValue;
}

export interface SpreadElement extends Node {
    type: "SpreadElement";
    spread: Span;
    expression: Expression;
}

export type JSXAttributeName = Identifier | JSXNamespacedName;

export type JSXAttrValue = Literal | JSXExpressionContainer | JSXElement | JSXFragment;

export type Literal = StringLiteral | BooleanLiteral | NullLiteral | NumericLiteral | BigIntLiteral | JSXText;

export interface StringLiteral extends Node, HasSpan {
    type: "StringLiteral";
    value: string;
    raw?: string;
}

export interface BooleanLiteral extends Node, HasSpan {
    type: "BooleanLiteral";
    value: boolean;
}

export interface NullLiteral extends Node, HasSpan {
    type: "NullLiteral";
}

export interface NumericLiteral extends Node, HasSpan {
    type: "NumericLiteral";
    value: number;
    raw?: string;
}

export interface BigIntLiteral extends Node, HasSpan {
    type: "BigIntLiteral";
    value: bigint;
    raw?: string;
}

export type Expression = Identifier | StringLiteral | BinaryExpression | CallExpression;

export interface Identifier extends ExpressionBase {
    type: "Identifier";
    value: string;
    optional: boolean;
}

export interface BinaryExpression extends ExpressionBase {
    type: "BinaryExpression";
    left: Expression;
    right: Expression;
    operator: BinaryOperator;
}

export interface CallExpression extends ExpressionBase {
    type: "CallExpression";
    callee: Expression;
    arguments: Argument[];
}

export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
}

export type BinaryOperator = "==" | "!=" | "===" | "!==" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" | "%";

export interface Argument {
    spread?: Span;
    expression: Expression;
}

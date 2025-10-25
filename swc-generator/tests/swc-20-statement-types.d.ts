// 测试 6: SWC 语句类型
// 测试控制流语句和基本语句

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

export type Statement = BlockStatement | EmptyStatement | IfStatement | SwitchStatement | ForStatement | WhileStatement | DoWhileStatement | ReturnStatement | BreakStatement | ContinueStatement | TryStatement | ThrowStatement;

export interface BlockStatement extends Node, HasSpan {
    type: "BlockStatement";
    stmts: Statement[];
}

export interface EmptyStatement extends Node, HasSpan {
    type: "EmptyStatement";
}

export interface IfStatement extends Node, HasSpan {
    type: "IfStatement";
    test: Expression;
    consequent: Statement;
    alternate?: Statement;
}

export interface SwitchStatement extends Node, HasSpan {
    type: "SwitchStatement";
    discriminant: Expression;
    cases: SwitchCase[];
}

export interface SwitchCase extends Node, HasSpan {
    type: "SwitchCase";
    test?: Expression;
    consequent: Statement[];
}

export interface ForStatement extends Node, HasSpan {
    type: "ForStatement";
    init?: VariableDeclaration | Expression;
    test?: Expression;
    update?: Expression;
    body: Statement;
}

export interface ForInStatement extends Node, HasSpan {
    type: "ForInStatement";
    left: VariableDeclaration | Pattern;
    right: Expression;
    body: Statement;
}

export interface ForOfStatement extends Node, HasSpan {
    type: "ForOfStatement";
    await?: Span;
    left: VariableDeclaration | Pattern;
    right: Expression;
    body: Statement;
}

export interface WhileStatement extends Node, HasSpan {
    type: "WhileStatement";
    test: Expression;
    body: Statement;
}

export interface DoWhileStatement extends Node, HasSpan {
    type: "DoWhileStatement";
    test: Expression;
    body: Statement;
}

export interface ReturnStatement extends Node, HasSpan {
    type: "ReturnStatement";
    argument?: Expression;
}

export interface BreakStatement extends Node, HasSpan {
    type: "BreakStatement";
    label?: Identifier;
}

export interface ContinueStatement extends Node, HasSpan {
    type: "ContinueStatement";
    label?: Identifier;
}

export interface TryStatement extends Node, HasSpan {
    type: "TryStatement";
    block: BlockStatement;
    handler?: CatchClause;
    finalizer?: BlockStatement;
}

export interface CatchClause extends Node, HasSpan {
    type: "CatchClause";
    param?: Pattern;
    body: BlockStatement;
}

export interface ThrowStatement extends Node, HasSpan {
    type: "ThrowStatement";
    argument: Expression;
}

export interface LabeledStatement extends Node, HasSpan {
    type: "LabeledStatement";
    label: Identifier;
    body: Statement;
}

export interface WithStatement extends Node, HasSpan {
    type: "WithStatement";
    object: Expression;
    body: Statement;
}

export interface DebuggerStatement extends Node, HasSpan {
    type: "DebuggerStatement";
}

export type Expression = Identifier | StringLiteral | BinaryExpression | CallExpression;

export interface Identifier extends ExpressionBase {
    type: "Identifier";
    value: string;
    optional: boolean;
}

export interface StringLiteral extends Node, HasSpan {
    type: "StringLiteral";
    value: string;
    raw?: string;
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

export type Pattern = BindingIdentifier | ArrayPattern | ObjectPattern;

export interface BindingIdentifier extends PatternBase {
    type: "Identifier";
    value: string;
    optional: boolean;
}

export interface ArrayPattern extends PatternBase {
    type: "ArrayPattern";
    elements: (Pattern | undefined)[];
    optional: boolean;
}

export interface ObjectPattern extends PatternBase {
    type: "ObjectPattern";
    properties: ObjectPatternProperty[];
    optional: boolean;
}

export interface PatternBase extends Node, HasSpan {
    // 基础模式接口
}

export type ObjectPatternProperty = KeyValuePatternProperty | AssignmentPatternProperty;

export interface KeyValuePatternProperty extends Node {
    type: "KeyValuePatternProperty";
    key: PropertyName;
    value: Pattern;
}

export interface AssignmentPatternProperty extends Node, HasSpan {
    type: "AssignmentPatternProperty";
    key: Identifier;
    value?: Expression;
}

export type PropertyName = Identifier | StringLiteral | NumericLiteral;

export interface NumericLiteral extends Node, HasSpan {
    type: "NumericLiteral";
    value: number;
    raw?: string;
}

export type VariableDeclaration = VariableDeclaration;

export interface VariableDeclaration extends Node, HasSpan {
    type: "VariableDeclaration";
    kind: VariableDeclarationKind;
    declare: boolean;
    declarations: VariableDeclarator[];
}

export type VariableDeclarationKind = "var" | "let" | "const";

export interface VariableDeclarator extends Node, HasSpan {
    type: "VariableDeclarator";
    id: Pattern;
    init?: Expression;
}

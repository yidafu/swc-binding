// 测试 5: SWC 表达式类型
// 测试各种表达式类型和操作符

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

export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
}

export type Expression = Identifier | Literal | BinaryExpression | UnaryExpression | CallExpression | MemberExpression | ConditionalExpression;

export interface Identifier extends ExpressionBase {
    type: "Identifier";
    value: string;
    optional: boolean;
}

export type Literal = StringLiteral | BooleanLiteral | NullLiteral | NumericLiteral | BigIntLiteral;

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

export interface BinaryExpression extends ExpressionBase {
    type: "BinaryExpression";
    left: Expression;
    right: Expression;
    operator: BinaryOperator;
}

export type BinaryOperator = "==" | "!=" | "===" | "!==" | "<" | "<=" | ">" | ">=" | "<<" | ">>" | ">>>" | "+" | "-" | "*" | "/" | "%" | "|" | "^" | "&" | "||" | "&&" | "in" | "instanceof" | "**" | "??";

export interface UnaryExpression extends ExpressionBase {
    type: "UnaryExpression";
    argument: Expression;
    operator: UnaryOperator;
}

export type UnaryOperator = "-" | "+" | "!" | "~" | "typeof" | "void" | "delete";

export interface UpdateExpression extends ExpressionBase {
    type: "UpdateExpression";
    argument: Expression;
    operator: UpdateOperator;
    prefix: boolean;
}

export type UpdateOperator = "++" | "--";

export interface CallExpression extends ExpressionBase {
    type: "CallExpression";
    callee: Expression;
    arguments: Argument[];
}

export interface Argument {
    spread?: Span;
    expression: Expression;
}

export interface NewExpression extends ExpressionBase {
    type: "NewExpression";
    callee: Expression;
    arguments: Argument[];
}

export interface MemberExpression extends ExpressionBase {
    type: "MemberExpression";
    object: Expression;
    property: Expression;
    computed: boolean;
    optional: boolean;
}

export interface ConditionalExpression extends ExpressionBase {
    type: "ConditionalExpression";
    test: Expression;
    consequent: Expression;
    alternate: Expression;
}

export interface AssignmentExpression extends ExpressionBase {
    type: "AssignmentExpression";
    left: Pattern | MemberExpression;
    right: Expression;
    operator: AssignmentOperator;
}

export type AssignmentOperator = "=" | "+=" | "-=" | "*=" | "/=" | "%=" | "<<=" | ">>=" | ">>>=" | "|=" | "^=" | "&=" | "**=" | "&&=" | "||=" | "??=";

export interface ArrayExpression extends ExpressionBase {
    type: "ArrayExpression";
    elements: (ExprOrSpread | undefined)[];
}

export interface ExprOrSpread {
    spread?: Span;
    expression: Expression;
}

export interface ObjectExpression extends ExpressionBase {
    type: "ObjectExpression";
    properties: Property[];
}

export type Property = Identifier | KeyValueProperty | AssignmentProperty | MethodProperty;

export interface KeyValueProperty extends PropBase {
    type: "KeyValueProperty";
    value: Expression;
}

export interface AssignmentProperty extends Node {
    type: "AssignmentProperty";
    key: Identifier;
    value: Expression;
}

export interface MethodProperty extends PropBase, Fn {
    type: "MethodProperty";
}

export interface PropBase extends Node {
    key: PropertyName;
}

export type PropertyName = Identifier | StringLiteral | NumericLiteral | ComputedPropName | BigIntLiteral;

export interface ComputedPropName extends Node, HasSpan {
    type: "Computed";
    expression: Expression;
}

export interface Fn extends HasSpan, HasDecorator {
    params: Param[];
    body?: BlockStatement;
    generator: boolean;
    async: boolean;
}

export interface HasDecorator {
    decorators?: Decorator[];
}

export interface Decorator extends Node, HasSpan {
    type: "Decorator";
    expression: Expression;
}

export interface Param extends Node, HasSpan, HasDecorator {
    type: "Parameter";
    pat: Pattern;
}

export interface BlockStatement extends Node, HasSpan {
    type: "BlockStatement";
    stmts: Statement[];
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

export type Statement = BlockStatement | ExpressionStatement | IfStatement | ReturnStatement;

export interface ExpressionStatement extends Node, HasSpan {
    type: "ExpressionStatement";
    expression: Expression;
}

export interface IfStatement extends Node, HasSpan {
    type: "IfStatement";
    test: Expression;
    consequent: Statement;
    alternate?: Statement;
}

export interface ReturnStatement extends Node, HasSpan {
    type: "ReturnStatement";
    argument?: Expression;
}

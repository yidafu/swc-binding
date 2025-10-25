// 测试 4: SWC 声明类型
// 测试类声明、函数声明、变量声明

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

export interface HasDecorator {
    decorators?: Decorator[];
}

export interface Decorator extends Node, HasSpan {
    type: "Decorator";
    expression: any;
}

export interface Class extends HasSpan, HasDecorator {
    body: ClassMember[];
    superClass?: Expression;
    isAbstract: boolean;
}

export type ClassMember = Constructor | ClassMethod | ClassProperty;

export interface ClassProperty extends ClassPropertyBase {
    type: "ClassProperty";
    key: PropertyName;
    isAbstract: boolean;
    declare: boolean;
}

export interface ClassPropertyBase extends Node, HasSpan, HasDecorator {
    value?: Expression;
    isStatic: boolean;
    accessibility?: Accessibility;
    isOptional: boolean;
    isOverride: boolean;
    readonly: boolean;
    definite: boolean;
}

export interface Constructor extends Node, HasSpan {
    type: "Constructor";
    key: PropertyName;
    params: Param[];
    body?: BlockStatement;
    accessibility?: Accessibility;
    isOptional: boolean;
}

export interface ClassMethod extends ClassMethodBase {
    type: "ClassMethod";
    key: PropertyName;
}

export interface ClassMethodBase extends Node, HasSpan {
    function: Fn;
    kind: MethodKind;
    isStatic: boolean;
    accessibility?: Accessibility;
    isAbstract: boolean;
    isOptional: boolean;
    isOverride: boolean;
}

export interface Fn extends HasSpan, HasDecorator {
    params: Param[];
    body?: BlockStatement;
    generator: boolean;
    async: boolean;
}

export interface Param extends Node, HasSpan, HasDecorator {
    type: "Parameter";
    pat: Pattern;
}

export interface BlockStatement extends Node, HasSpan {
    type: "BlockStatement";
    stmts: Statement[];
}

export type MethodKind = "method" | "getter" | "setter";

export type Accessibility = "public" | "private" | "protected";

export type PropertyName = Identifier | StringLiteral | NumericLiteral;

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

export interface NumericLiteral extends Node, HasSpan {
    type: "NumericLiteral";
    value: number;
    raw?: string;
}

export type Expression = Identifier | StringLiteral | BinaryExpression | CallExpression;

export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
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

export type Declaration = ClassDeclaration | FunctionDeclaration | VariableDeclaration;

export interface ClassDeclaration extends Class, Node {
    type: "ClassDeclaration";
    identifier: Identifier;
    declare: boolean;
}

export interface FunctionDeclaration extends Fn {
    type: "FunctionDeclaration";
    identifier: Identifier;
    declare: boolean;
}

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

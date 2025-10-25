// 测试 12: SWC 函数类型
// 测试函数接口和函数表达式

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
    expression: Expression;
}

export interface Fn extends HasSpan, HasDecorator {
    params: Param[];
    body?: BlockStatement;
    generator: boolean;
    async: boolean;
    typeParams?: TsTypeParameterDeclaration;
    returnType?: TsTypeAnnotation;
}

export interface Param extends Node, HasSpan, HasDecorator {
    type: "Parameter";
    pat: Pattern;
    typeAnnotation?: TsTypeAnnotation;
    optional: boolean;
}

export interface FunctionDeclaration extends Fn {
    type: "FunctionDeclaration";
    identifier: Identifier;
    declare: boolean;
}

export interface FunctionExpression extends Fn, ExpressionBase {
    type: "FunctionExpression";
    identifier?: Identifier;
}

export interface ArrowFunctionExpression extends ExpressionBase {
    type: "ArrowFunctionExpression";
    params: Param[];
    body: BlockStatement | Expression;
    generator: boolean;
    async: boolean;
    typeParams?: TsTypeParameterDeclaration;
    returnType?: TsTypeAnnotation;
}

export interface ClassExpression extends Class, ExpressionBase {
    type: "ClassExpression";
    identifier?: Identifier;
}

export interface Class extends HasSpan, HasDecorator {
    body: ClassMember[];
    superClass?: Expression;
    isAbstract: boolean;
    typeParams?: TsTypeParameterDeclaration;
    superTypeParams?: TsTypeParameterInstantiation;
    implements: TsExpressionWithTypeArguments[];
}

export interface ClassMember extends Node, HasSpan {
    // 类成员基础接口
}

export interface BlockStatement extends Node, HasSpan {
    type: "BlockStatement";
    stmts: Statement[];
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

export type Expression = Identifier | StringLiteral | BinaryExpression | CallExpression | FunctionExpression | ArrowFunctionExpression | ClassExpression;

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

export type Pattern = BindingIdentifier | ArrayPattern | ObjectPattern | RestElement | AssignmentPattern;

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

export interface RestElement extends PatternBase {
    type: "RestElement";
    rest: Span;
    argument: Pattern;
}

export interface AssignmentPattern extends PatternBase {
    type: "AssignmentPattern";
    left: Pattern;
    right: Expression;
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

export type PropertyName = Identifier | StringLiteral | NumericLiteral | ComputedPropName | BigIntLiteral;

export interface NumericLiteral extends Node, HasSpan {
    type: "NumericLiteral";
    value: number;
    raw?: string;
}

export interface ComputedPropName extends Node, HasSpan {
    type: "Computed";
    expression: Expression;
}

export interface BigIntLiteral extends Node, HasSpan {
    type: "BigIntLiteral";
    value: bigint;
    raw?: string;
}

// TypeScript 相关类型
export interface TsTypeAnnotation extends Node, HasSpan {
    type: "TsTypeAnnotation";
    typeAnnotation: TsType;
}

export interface TsTypeParameterDeclaration extends Node, HasSpan {
    type: "TsTypeParameterDeclaration";
    parameters: TsTypeParameter[];
}

export interface TsTypeParameter extends Node, HasSpan {
    type: "TsTypeParameter";
    name: Identifier;
    in: boolean;
    out: boolean;
    constraint?: TsType;
    default?: TsType;
}

export type TsType = TsKeywordType | TsTypeReference | TsTypeLiteral | TsFunctionType | TsConstructorType;

export interface TsKeywordType extends Node, HasSpan {
    type: "TsKeywordType";
    kind: TsKeywordTypeKind;
}

export type TsKeywordTypeKind = "any" | "unknown" | "number" | "object" | "boolean" | "bigint" | "string" | "symbol" | "void" | "undefined" | "null" | "never" | "intrinsic";

export interface TsTypeReference extends Node, HasSpan {
    type: "TsTypeReference";
    typeName: TsEntityName;
    typeParams?: TsTypeParameterInstantiation;
}

export interface TsTypeLiteral extends Node, HasSpan {
    type: "TsTypeLiteral";
    members: TsTypeElement[];
}

export interface TsFunctionType extends Node, HasSpan {
    type: "TsFunctionType";
    params: TsFnParameter[];
    typeParams?: TsTypeParameterDeclaration;
    typeAnnotation: TsTypeAnnotation;
}

export interface TsConstructorType extends Node, HasSpan {
    type: "TsConstructorType";
    params: TsFnParameter[];
    typeParams?: TsTypeParameterDeclaration;
    typeAnnotation: TsTypeAnnotation;
    isAbstract: boolean;
}

export type TsEntityName = TsQualifiedName | Identifier;

export interface TsQualifiedName extends Node {
    type: "TsQualifiedName";
    left: TsEntityName;
    right: Identifier;
}

export interface TsTypeParameterInstantiation extends Node, HasSpan {
    type: "TsTypeParameterInstantiation";
    params: TsType[];
}

export type TsTypeElement = TsPropertySignature | TsMethodSignature | TsIndexSignature;

export interface TsPropertySignature extends Node, HasSpan {
    type: "TsPropertySignature";
    readonly: boolean;
    key: Expression;
    computed: boolean;
    optional: boolean;
    typeAnnotation?: TsTypeAnnotation;
}

export interface TsMethodSignature extends Node, HasSpan {
    type: "TsMethodSignature";
    readonly: boolean;
    key: Expression;
    computed: boolean;
    optional: boolean;
    params: TsFnParameter[];
    typeAnn?: TsTypeAnnotation;
    typeParams?: TsTypeParameterDeclaration;
}

export interface TsIndexSignature extends Node, HasSpan {
    type: "TsIndexSignature";
    params: TsFnParameter[];
    typeAnnotation?: TsTypeAnnotation;
    readonly: boolean;
    static: boolean;
}

export type TsFnParameter = BindingIdentifier | ArrayPattern | RestElement | ObjectPattern;

export interface TsExpressionWithTypeArguments extends Node, HasSpan {
    type: "TsExpressionWithTypeArguments";
    expression: TsEntityName;
    typeArgs?: TsTypeParameterInstantiation;
}

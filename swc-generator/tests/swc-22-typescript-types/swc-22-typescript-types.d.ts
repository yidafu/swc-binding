// 测试 8: SWC TypeScript 类型
// 测试 TypeScript 特有的类型和声明

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

export interface TsTypeParameterInstantiation extends Node, HasSpan {
    type: "TsTypeParameterInstantiation";
    params: TsType[];
}

export interface TsInterfaceDeclaration extends Node, HasSpan {
    type: "TsInterfaceDeclaration";
    id: Identifier;
    typeParams?: TsTypeParameterDeclaration;
    extends?: TsExpressionWithTypeArguments[];
    body: TsInterfaceBody;
    declare: boolean;
}

export interface TsTypeAliasDeclaration extends Node, HasSpan {
    type: "TsTypeAliasDeclaration";
    id: Identifier;
    typeParams?: TsTypeParameterDeclaration;
    typeAnnotation: TsType;
    declare: boolean;
}

export interface TsEnumDeclaration extends Node, HasSpan {
    type: "TsEnumDeclaration";
    id: Identifier;
    declare: boolean;
    isConst: boolean;
    members: TsEnumMember[];
}

export interface TsEnumMember extends Node, HasSpan {
    type: "TsEnumMember";
    id: Identifier | StringLiteral;
    init?: Expression;
}

export interface TsModuleDeclaration extends Node, HasSpan {
    type: "TsModuleDeclaration";
    id: TsModuleName;
    body?: TsNamespaceBody;
    declare: boolean;
    global: boolean;
}

export type TsModuleName = Identifier | StringLiteral;

export type TsNamespaceBody = TsNamespaceDeclaration | TsModuleBlock;

export interface TsNamespaceDeclaration extends Node, HasSpan {
    type: "TsNamespaceDeclaration";
    id: Identifier;
    body: TsNamespaceBody;
    declare: boolean;
}

export interface TsModuleBlock extends Node, HasSpan {
    type: "TsModuleBlock";
    body: Statement[];
}

export type TsType = TsKeywordType | TsTypeReference | TsTypeLiteral | TsArrayType | TsUnionType | TsIntersectionType | TsFunctionType | TsConstructorType;

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

export type TsEntityName = TsQualifiedName | Identifier;

export interface TsQualifiedName extends Node {
    type: "TsQualifiedName";
    left: TsEntityName;
    right: Identifier;
}

export interface TsTypeLiteral extends Node, HasSpan {
    type: "TsTypeLiteral";
    members: TsTypeElement[];
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

export interface TsArrayType extends Node, HasSpan {
    type: "TsArrayType";
    elemType: TsType;
}

export interface TsUnionType extends Node, HasSpan {
    type: "TsUnionType";
    types: TsType[];
}

export interface TsIntersectionType extends Node, HasSpan {
    type: "TsIntersectionType";
    types: TsType[];
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

export type TsFnParameter = BindingIdentifier | ArrayPattern | RestElement | ObjectPattern;

export interface TsExpressionWithTypeArguments extends Node, HasSpan {
    type: "TsExpressionWithTypeArguments";
    expression: TsEntityName;
    typeArgs?: TsTypeParameterInstantiation;
}

export interface TsInterfaceBody extends Node, HasSpan {
    type: "TsInterfaceBody";
    body: TsTypeElement[];
}

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

export type ObjectPatternProperty = KeyValuePatternProperty | AssignmentPatternProperty | RestElement;

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

export interface RestElement extends PatternBase {
    type: "RestElement";
    rest: Span;
    argument: Pattern;
}

export type Statement = BlockStatement | ExpressionStatement | IfStatement | ReturnStatement;

export interface BlockStatement extends Node, HasSpan {
    type: "BlockStatement";
    stmts: Statement[];
}

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

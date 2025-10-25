// 测试 10: SWC 类类型
// 测试类、类成员、方法和属性

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

export interface Class extends HasSpan, HasDecorator {
    body: ClassMember[];
    superClass?: Expression;
    isAbstract: boolean;
    typeParams?: TsTypeParameterDeclaration;
    superTypeParams?: TsTypeParameterInstantiation;
    implements: TsExpressionWithTypeArguments[];
}

export type ClassMember = Constructor | ClassMethod | PrivateMethod | ClassProperty | PrivateProperty | StaticBlock;

export interface ClassProperty extends ClassPropertyBase {
    type: "ClassProperty";
    key: PropertyName;
    isAbstract: boolean;
    declare: boolean;
}

export interface PrivateProperty extends ClassPropertyBase {
    type: "PrivateProperty";
    key: PrivateName;
}

export interface ClassPropertyBase extends Node, HasSpan, HasDecorator {
    value?: Expression;
    typeAnnotation?: TsTypeAnnotation;
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
    params: (TsParameterProperty | Param)[];
    body?: BlockStatement;
    accessibility?: Accessibility;
    isOptional: boolean;
}

export interface ClassMethod extends ClassMethodBase {
    type: "ClassMethod";
    key: PropertyName;
}

export interface PrivateMethod extends ClassMethodBase {
    type: "PrivateMethod";
    key: PrivateName;
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

export interface StaticBlock extends Node, HasSpan {
    type: "StaticBlock";
    body: BlockStatement;
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

export interface BlockStatement extends Node, HasSpan {
    type: "BlockStatement";
    stmts: Statement[];
}

export type MethodKind = "method" | "getter" | "setter";

export type Accessibility = "public" | "private" | "protected";

export type PropertyName = Identifier | StringLiteral | NumericLiteral | ComputedPropName | BigIntLiteral;

export interface PrivateName extends ExpressionBase {
    type: "PrivateName";
    id: Identifier;
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

export interface TsTypeParameterInstantiation extends Node, HasSpan {
    type: "TsTypeParameterInstantiation";
    params: TsType[];
}

export interface TsExpressionWithTypeArguments extends Node, HasSpan {
    type: "TsExpressionWithTypeArguments";
    expression: TsEntityName;
    typeArgs?: TsTypeParameterInstantiation;
}

export type TsEntityName = TsQualifiedName | Identifier;

export interface TsQualifiedName extends Node {
    type: "TsQualifiedName";
    left: TsEntityName;
    right: Identifier;
}

export type TsType = TsKeywordType | TsTypeReference | TsTypeLiteral;

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

export interface RestElement extends PatternBase {
    type: "RestElement";
    rest: Span;
    argument: Pattern;
}

export interface TsParameterProperty extends Node, HasSpan, HasDecorator {
    type: "TsParameterProperty";
    accessibility?: Accessibility;
    override: boolean;
    readonly: boolean;
    param: TsParameterPropertyParameter;
}

export type TsParameterPropertyParameter = BindingIdentifier | AssignmentPattern;

export interface AssignmentPattern extends PatternBase {
    type: "AssignmentPattern";
    left: Pattern;
    right: Expression;
}

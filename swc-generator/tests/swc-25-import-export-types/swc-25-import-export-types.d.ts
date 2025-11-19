// 测试 11: SWC 导入导出类型
// 测试 import/export 声明和说明符

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

export interface ImportDeclaration extends Node, HasSpan {
    type: "ImportDeclaration";
    specifiers: ImportSpecifier[];
    source: StringLiteral;
    typeOnly: boolean;
    asserts?: ObjectExpression;
}

export interface ExportDeclaration extends Node, HasSpan {
    type: "ExportDeclaration";
    declaration: Declaration;
}

export interface ExportNamedDeclaration extends Node, HasSpan {
    type: "ExportNamedDeclaration";
    specifiers: ExportSpecifier[];
    source?: StringLiteral;
    typeOnly: boolean;
    asserts?: ObjectExpression;
}

export interface ExportDefaultDeclaration extends Node, HasSpan {
    type: "ExportDefaultDeclaration";
    decl: DefaultDecl;
}

export interface ExportDefaultExpression extends Node, HasSpan {
    type: "ExportDefaultExpression";
    expression: Expression;
}

export interface ExportAllDeclaration extends Node, HasSpan {
    type: "ExportAllDeclaration";
    source: StringLiteral;
    asserts?: ObjectExpression;
}

export type ImportSpecifier = NamedImportSpecifier | ImportDefaultSpecifier | ImportNamespaceSpecifier;

export interface NamedImportSpecifier extends Node, HasSpan {
    type: "ImportSpecifier";
    local: Identifier;
    imported?: ModuleExportName;
    isTypeOnly: boolean;
}

export interface ImportDefaultSpecifier extends Node, HasSpan {
    type: "ImportDefaultSpecifier";
    local: Identifier;
}

export interface ImportNamespaceSpecifier extends Node, HasSpan {
    type: "ImportNamespaceSpecifier";
    local: Identifier;
}

export type ExportSpecifier = ExportNamespaceSpecifier | ExportDefaultSpecifier | NamedExportSpecifier;

export interface ExportNamespaceSpecifier extends Node, HasSpan {
    type: "ExportNamespaceSpecifier";
    name: ModuleExportName;
}

export interface ExportDefaultSpecifier extends Node, HasSpan {
    type: "ExportDefaultSpecifier";
    exported: Identifier;
}

export interface NamedExportSpecifier extends Node, HasSpan {
    type: "ExportSpecifier";
    orig: ModuleExportName;
    exported?: ModuleExportName;
    isTypeOnly: boolean;
}

export type ModuleExportName = Identifier | StringLiteral;

export type DefaultDecl = ClassExpression | FunctionExpression | TsInterfaceDeclaration;

export type Declaration = ClassDeclaration | FunctionDeclaration | VariableDeclaration | TsInterfaceDeclaration | TsTypeAliasDeclaration | TsEnumDeclaration | TsModuleDeclaration;

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

export interface TsModuleDeclaration extends Node, HasSpan {
    type: "TsModuleDeclaration";
    id: TsModuleName;
    body?: TsNamespaceBody;
    declare: boolean;
    global: boolean;
}

export interface StringLiteral extends Node, HasSpan {
    type: "StringLiteral";
    value: string;
    raw?: string;
}

export interface ObjectExpression extends ExpressionBase {
    type: "ObjectExpression";
    properties: Property[];
}

export type Expression = Identifier | StringLiteral | BinaryExpression | CallExpression | ClassExpression | FunctionExpression;

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

export interface ClassExpression extends Class, ExpressionBase {
    type: "ClassExpression";
}

export interface FunctionExpression extends Fn, ExpressionBase {
    type: "FunctionExpression";
}

export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
}

export type BinaryOperator = "==" | "!=" | "===" | "!==" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" | "%";

export interface Argument {
    spread?: Span;
    expression: Expression;
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

export type PropertyName = Identifier | StringLiteral | NumericLiteral;

export interface NumericLiteral extends Node, HasSpan {
    type: "NumericLiteral";
    value: number;
    raw?: string;
}

export interface Class extends HasSpan, HasDecorator {
    body: ClassMember[];
    superClass?: Expression;
    isAbstract: boolean;
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

export interface ClassMember extends Node, HasSpan {
    // 类成员基础接口
}

export interface Param extends Node, HasSpan, HasDecorator {
    type: "Parameter";
    pat: Pattern;
}

export interface BlockStatement extends Node, HasSpan {
    type: "BlockStatement";
    stmts: Statement[];
}

export type VariableDeclarationKind = "var" | "let" | "const";

export interface VariableDeclarator extends Node, HasSpan {
    type: "VariableDeclarator";
    id: Pattern;
    init?: Expression;
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

export interface TsExpressionWithTypeArguments extends Node, HasSpan {
    type: "TsExpressionWithTypeArguments";
    expression: TsEntityName;
    typeArgs?: TsTypeParameterInstantiation;
}

export interface TsTypeParameterInstantiation extends Node, HasSpan {
    type: "TsTypeParameterInstantiation";
    params: TsType[];
}

export interface TsInterfaceBody extends Node, HasSpan {
    type: "TsInterfaceBody";
    body: TsTypeElement[];
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

export interface TsTypeAnnotation extends Node, HasSpan {
    type: "TsTypeAnnotation";
    typeAnnotation: TsType;
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

export type TsEntityName = TsQualifiedName | Identifier;

export interface TsQualifiedName extends Node {
    type: "TsQualifiedName";
    left: TsEntityName;
    right: Identifier;
}

export type TsFnParameter = BindingIdentifier | ArrayPattern | RestElement | ObjectPattern;

export interface RestElement extends PatternBase {
    type: "RestElement";
    rest: Span;
    argument: Pattern;
}

export interface TsEnumMember extends Node, HasSpan {
    type: "TsEnumMember";
    id: Identifier | StringLiteral;
    init?: Expression;
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

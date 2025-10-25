// 测试 3: SWC 模块类型
// 测试 Program, Module, Script 和相关的模块声明

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

export interface HasInterpreter {
    interpreter: string;
}

export type Program = Module | Script;

export interface Module extends Node, HasSpan, HasInterpreter {
    type: "Module";
    body: ModuleItem[];
}

export interface Script extends Node, HasSpan, HasInterpreter {
    type: "Script";
    body: Statement[];
}

export type ModuleItem = ModuleDeclaration | Statement;

export type ModuleDeclaration = ImportDeclaration | ExportDeclaration | ExportNamedDeclaration | ExportDefaultDeclaration;

export interface ImportDeclaration extends Node, HasSpan {
    type: "ImportDeclaration";
    specifiers: ImportSpecifier[];
    source: StringLiteral;
    typeOnly: boolean;
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
}

export interface ExportDefaultDeclaration extends Node, HasSpan {
    type: "ExportDefaultDeclaration";
    decl: DefaultDecl;
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

export type DefaultDecl = ClassExpression | FunctionExpression;

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

export interface ClassExpression extends Class, ExpressionBase {
    type: "ClassExpression";
}

export interface FunctionExpression extends Fn, ExpressionBase {
    type: "FunctionExpression";
}

export interface Class extends HasSpan, HasDecorator {
    body: any[];
    superClass?: Expression;
    isAbstract: boolean;
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

export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
}

// 测试 13: SWC Export 声明支持
// 测试 export interface 和 export type 的提取

export interface ExportedInterface {
    name: string;
    value: number;
}

export type ExportedLiteralUnion = "option1" | "option2" | "option3";

export type ExportedTypeAlias = string | number;

// 同时测试非 export 声明（应该都能被提取）
export interface NonExportedInterface {
    id: number;
}

export type NonExportedType = boolean;

// SWC 导出声明
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

export interface ExportAllDeclaration extends Node, HasSpan {
    type: "ExportAllDeclaration";
    source: StringLiteral;
}

// SWC 导入声明
export interface ImportDeclaration extends Node, HasSpan {
    type: "ImportDeclaration";
    specifiers: ImportSpecifier[];
    source: StringLiteral;
    typeOnly: boolean;
}

// 基础类型
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

export type Declaration = any;
export type ExportSpecifier = any;
export type StringLiteral = any;
export type DefaultDecl = any;
export type ImportSpecifier = any;


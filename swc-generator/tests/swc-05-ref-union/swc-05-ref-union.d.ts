// 测试 5: SWC 引用联合类型
// 处理函数: processRefUnion
// 预期: 生成 sealed interface，建立继承关系

export interface BaseType {
    kind: string;
}

export interface TypeA extends BaseType {
    kind: "A";
    valueA: string;
}

export interface TypeB extends BaseType {
    kind: "B";
    valueB: number;
}

export interface TypeC extends BaseType {
    kind: "C";
    valueC: boolean;
}

// 引用联合：sealed interface
export type UnionType = TypeA | TypeB | TypeC;

// 另一个引用联合
export interface ItemOne {
    id: number;
}

export interface ItemTwo {
    name: string;
}

export type Item = ItemOne | ItemTwo;

// SWC 模块项联合类型
export interface ModuleDeclaration {
    type: string;
}

export interface Statement {
    type: string;
}

export type ModuleItem = ModuleDeclaration | Statement;

// SWC 导入说明符联合类型
export interface NamedImportSpecifier {
    type: "ImportSpecifier";
    local: Identifier;
    imported?: ModuleExportName;
    isTypeOnly: boolean;
}

export interface ImportDefaultSpecifier {
    type: "ImportDefaultSpecifier";
    local: Identifier;
}

export interface ImportNamespaceSpecifier {
    type: "ImportNamespaceSpecifier";
    local: Identifier;
}

export type ImportSpecifier = NamedImportSpecifier | ImportDefaultSpecifier | ImportNamespaceSpecifier;

// 基础类型
export interface Identifier {
    type: "Identifier";
    value: string;
    optional: boolean;
}

export type ModuleExportName = Identifier | StringLiteral;

export interface StringLiteral {
    type: "StringLiteral";
    value: string;
    raw?: string;
}


// 测试 13: Export 声明支持
// 测试 export interface 和 export type 的提取

export interface ExportedInterface {
    name: string;
    value: number;
}

export type ExportedLiteralUnion = "option1" | "option2" | "option3";

export type ExportedTypeAlias = string | number;

// 同时测试非 export 声明（应该都能被提取）
interface NonExportedInterface {
    id: number;
}

type NonExportedType = boolean;


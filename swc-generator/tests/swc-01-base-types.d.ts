// 测试 1: SWC 基础类型配置
// 测试 toKotlinClass, sealedInterface, keepInterface 配置

// Span 会被转换为 Kotlin class（toKotlinClass 配置）
export interface Span {
    start: number;
    end: number;
    ctxt: number;
}

// Node 是 sealed interface（sealedInterface 配置）
export interface Node {
    type: string;
}

// HasSpan 是保留接口（keepInterface 配置，不生成 Impl）
export interface HasSpan {
    span: Span;
}

// HasDecorator 是保留接口（keepInterface 配置）
export interface HasDecorator {
    decorators?: Decorator[];
}

// Decorator 接口
export interface Decorator extends Node, HasSpan {
    type: "Decorator";
    expression: any;
}


export interface Class extends HasSpan, HasDecorator {
    body: any[]
}


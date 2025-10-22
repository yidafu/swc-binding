// 测试 1: 基础类型配置
// 测试 toKotlinClass, sealedInterface, keepInterface 配置

// Span 会被转换为 Kotlin class（toKotlinClass 配置）
interface Span {
    start: number;
    end: number;
    ctxt: number;
}

// Node 是 sealed interface（sealedInterface 配置）
interface Node {
    type: string;
}

// HasSpan 是保留接口（keepInterface 配置，不生成 Impl）
interface HasSpan {
    span: Span;
}


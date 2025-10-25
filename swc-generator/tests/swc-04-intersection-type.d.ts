// 测试 4: SWC 交叉类型
// 处理函数: processIntersectionType
// 预期: 生成 Base interface 和实现类

export interface BaseNode {
    type: string;
}

export interface BaseSpan {
    span: Span;
}

export interface Span {
    start: number;
    end: number;
    ctxt: number;
}

// 交叉类型：多继承模拟
export interface ExprBase extends BaseNode, BaseSpan {
    extraField: string;
}

// 另一个交叉类型示例
export interface StmtBase extends BaseNode, BaseSpan {
    stmtField: boolean;
}

// SWC 表达式基础接口
export interface ExpressionBase extends BaseNode, BaseSpan {
    // 基础表达式接口
}

// SWC 语句基础接口
export interface StatementBase extends BaseNode, BaseSpan {
    // 基础语句接口
}

// SWC 模式基础接口
export interface PatternBase extends BaseNode, BaseSpan {
    // 基础模式接口
}


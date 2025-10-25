// 测试 2: SWC 核心 AST 类型
// 测试基础的 AST 节点接口和继承关系

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
    expression: any;
}

// 测试接口继承
export interface Class extends HasSpan, HasDecorator {
    body: any[];
    superClass?: any;
    isAbstract: boolean;
}

export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
}

export interface StatementBase extends Node, HasSpan {
    // 基础语句接口
}

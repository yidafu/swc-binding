// 测试 7: SWC 数组类型
// 测试各种数组类型的处理

export interface ArrayTypes {
    // 基本数组
    stringArray: string[];
    numberArray: number[];
    booleanArray: boolean[];
    
    // 嵌套数组
    matrix: number[][];
    
    // 联合类型数组
    mixedArray: (string | number)[];
    
    // 可选元素数组
    optionalArray: (string | undefined)[];
}

// SWC 数组表达式
export interface ArrayExpression extends ExpressionBase {
    type: "ArrayExpression";
    elements: (ExprOrSpread | undefined)[];
}

export interface ExprOrSpread {
    spread?: Span;
    expression: Expression;
}

// SWC 数组模式
export interface ArrayPattern extends PatternBase {
    type: "ArrayPattern";
    elements: (Pattern | undefined)[];
    optional: boolean;
}

// SWC 参数数组
export interface Param extends Node, HasSpan, HasDecorator {
    type: "Parameter";
    pat: Pattern;
    typeAnnotation?: TsTypeAnnotation;
    optional: boolean;
}

// 基础类型
export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
}

export interface PatternBase extends Node, HasSpan {
    // 基础模式接口
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

export interface Span {
    start: number;
    end: number;
    ctxt: number;
}

export interface Decorator extends Node, HasSpan {
    type: "Decorator";
    expression: any;
}

export type Expression = string;
export type Pattern = string;
export type TsTypeAnnotation = string;


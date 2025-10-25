// 测试 9: SWC 索引签名
// 测试 Map 类型的生成

// 字符串索引
export interface StringIndex {
    [key: string]: string;
}

// 数字索引
export interface NumberIndex {
    [index: number]: string;
}

// 混合索引和具名属性
export interface MixedIndex {
    [key: string]: any;
    specificProp: number;
}

// 值类型为复杂类型的索引
export interface ComplexValueIndex {
    [key: string]: {
        value: string;
        count: number;
    };
}

// SWC TypeScript 索引签名
export interface TsIndexSignature extends Node, HasSpan {
    type: "TsIndexSignature";
    params: TsFnParameter[];
    typeAnnotation?: TsTypeAnnotation;
    readonly: boolean;
    static: boolean;
}

// SWC 对象模式索引
export interface ObjectPattern extends PatternBase {
    type: "ObjectPattern";
    properties: ObjectPatternProperty[];
    optional: boolean;
}

export type ObjectPatternProperty = KeyValuePatternProperty | AssignmentPatternProperty | RestElement;

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

export interface RestElement extends PatternBase {
    type: "RestElement";
    rest: Span;
    argument: Pattern;
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

export interface PatternBase extends Node, HasSpan {
    // 基础模式接口
}

export type TsFnParameter = any;
export type TsTypeAnnotation = any;
export type PropertyName = any;
export type Pattern = any;
export type Identifier = any;
export type Expression = any;


// 测试 11: SWC 函数类型
// 测试函数签名的转换

export interface FunctionSignatures {
    // 简单函数
    simpleFunc: () => void;
    
    // 带参数
    withParams: (a: string, b: number) => boolean;
    
    // 可选参数
    optionalParam: (required: string, optional?: number) => void;
    
    // Rest 参数
    restParam: (...args: string[]) => void;
    
    // 返回复杂类型
    complexReturn: () => { x: number; y: number };
}

// 函数类型别名
export type Callback = (data: string) => void;
export type Predicate<T> = (value: T) => boolean;
export type Transformer<T, U> = (input: T) => U;

// SWC 函数声明
export interface FunctionDeclaration extends Fn {
    type: "FunctionDeclaration";
    identifier: Identifier;
    declare: boolean;
}

// SWC 函数表达式
export interface FunctionExpression extends Fn, ExpressionBase {
    type: "FunctionExpression";
    identifier?: Identifier;
}

// SWC 箭头函数
export interface ArrowFunctionExpression extends ExpressionBase {
    type: "ArrowFunctionExpression";
    params: Param[];
    body: BlockStatement | Expression;
    generator: boolean;
    async: boolean;
    typeParams?: TsTypeParameterDeclaration;
    returnType?: TsTypeAnnotation;
}

// SWC TypeScript 函数类型
export interface TsFunctionType extends Node, HasSpan {
    type: "TsFunctionType";
    params: TsFnParameter[];
    typeParams?: TsTypeParameterDeclaration;
    typeAnnotation: TsTypeAnnotation;
}

export interface TsConstructorType extends Node, HasSpan {
    type: "TsConstructorType";
    params: TsFnParameter[];
    typeParams?: TsTypeParameterDeclaration;
    typeAnnotation: TsTypeAnnotation;
    isAbstract: boolean;
}

// 基础类型
export interface Fn extends HasSpan, HasDecorator {
    params: Param[];
    body?: BlockStatement;
    generator: boolean;
    async: boolean;
    typeParams?: TsTypeParameterDeclaration;
    returnType?: TsTypeAnnotation;
}

export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
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

export type Identifier = any;
export type Param = any;
export type BlockStatement = any;
export type Expression = any;
export type TsTypeParameterDeclaration = any;
export type TsTypeAnnotation = any;
export type TsFnParameter = any;


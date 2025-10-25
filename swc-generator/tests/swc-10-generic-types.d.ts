// 测试 10: SWC 泛型类型
// 测试泛型参数的处理

// 单泛型
export interface Container<T> {
    value: T;
}

// 多泛型
export interface Pair<K, V> {
    key: K;
    value: V;
}

// 泛型约束
export interface Constrained<T extends string> {
    data: T;
}

// 泛型数组
export interface GenericArray<T> {
    items: T[];
}

// 嵌套泛型
export interface Nested<T> {
    wrapper: Container<T>;
}

// SWC TypeScript 泛型
export interface TsTypeParameterDeclaration extends Node, HasSpan {
    type: "TsTypeParameterDeclaration";
    parameters: TsTypeParameter[];
}

export interface TsTypeParameter extends Node, HasSpan {
    type: "TsTypeParameter";
    name: Identifier;
    in: boolean;
    out: boolean;
    constraint?: TsType;
    default?: TsType;
}

export interface TsTypeParameterInstantiation extends Node, HasSpan {
    type: "TsTypeParameterInstantiation";
    params: TsType[];
}

export interface TsTypeReference extends Node, HasSpan {
    type: "TsTypeReference";
    typeName: TsEntityName;
    typeParams?: TsTypeParameterInstantiation;
}

// SWC 函数泛型
export interface Fn extends HasSpan, HasDecorator {
    params: Param[];
    body?: BlockStatement;
    generator: boolean;
    async: boolean;
    typeParams?: TsTypeParameterDeclaration;
    returnType?: TsTypeAnnotation;
}

// 基础类型
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
export type TsType = any;
export type TsEntityName = any;
export type Param = any;
export type BlockStatement = any;
export type TsTypeAnnotation = any;


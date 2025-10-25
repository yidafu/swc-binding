// 测试 8: SWC 可选属性和修饰符
// 测试 optional, readonly 等属性修饰符

export interface PropertyModifiers {
    // 必需属性
    required: string;
    
    // 可选属性
    optional?: number;
    
    // 只读属性
    readonly readonlyField: boolean;
    
    // 可选 + 只读
    readonly optionalReadonly?: string;
}

// 全部可选
export interface AllOptional {
    field1?: string;
    field2?: number;
    field3?: boolean;
}

// 全部必需
export interface AllRequired {
    field1: string;
    field2: number;
    field3: boolean;
}

// SWC 类属性修饰符
export interface ClassProperty extends ClassPropertyBase {
    type: "ClassProperty";
    key: PropertyName;
    isAbstract: boolean;
    declare: boolean;
}

export interface ClassPropertyBase extends Node, HasSpan, HasDecorator {
    value?: Expression;
    typeAnnotation?: TsTypeAnnotation;
    isStatic: boolean;
    accessibility?: Accessibility;
    isOptional: boolean;
    isOverride: boolean;
    readonly: boolean;
    definite: boolean;
}

// SWC 方法修饰符
export interface ClassMethod extends ClassMethodBase {
    type: "ClassMethod";
    key: PropertyName;
}

export interface ClassMethodBase extends Node, HasSpan {
    function: Fn;
    kind: MethodKind;
    isStatic: boolean;
    accessibility?: Accessibility;
    isAbstract: boolean;
    isOptional: boolean;
    isOverride: boolean;
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
    expression: object;
}

export type PropertyName = object;
export type Expression = object;
export type TsTypeAnnotation = object;
export type Accessibility = "public" | "private" | "protected";
export type MethodKind = "method" | "getter" | "setter";
export interface Fn extends HasSpan, HasDecorator {
    params: Param[];
    body?: BlockStatement;
    generator: boolean;
    async: boolean;
}
export type Param = object;
export type BlockStatement = object;


// 测试 6: SWC 接口继承关系
// 处理函数: buildInheritanceRelationship, processInterfaces
// 预期: 建立继承关系图，生成 interface + Impl 类

export interface ParentInterface {
    parentProp: string;
}

export interface ChildInterface extends ParentInterface {
    childProp: number;
}

export interface GrandChildInterface extends ChildInterface {
    grandChildProp: boolean;
}

// 多重继承
export interface MixinA {
    methodA: string;
}

export interface MixinB {
    methodB: number;
}

export interface Combined extends MixinA, MixinB {
    ownProp: boolean;
}

// SWC 类继承关系
export interface Class extends HasSpan, HasDecorator {
    body: ClassMember[];
    superClass?: Expression;
    isAbstract: boolean;
}

export interface ClassDeclaration extends Class, Node {
    type: "ClassDeclaration";
    identifier: Identifier;
    declare: boolean;
}

export interface ClassExpression extends Class, ExpressionBase {
    type: "ClassExpression";
    identifier?: Identifier;
}

// SWC 函数继承关系
export interface Fn extends HasSpan, HasDecorator {
    params: Param[];
    body?: BlockStatement;
    generator: boolean;
    async: boolean;
}

export interface FunctionDeclaration extends Fn {
    type: "FunctionDeclaration";
    identifier: Identifier;
    declare: boolean;
}

export interface FunctionExpression extends Fn, ExpressionBase {
    type: "FunctionExpression";
    identifier?: Identifier;
}

// 基础类型
export interface HasSpan {
    span: Span;
}

export interface HasDecorator {
    decorators?: Decorator[];
}

export interface Node {
    type: string;
}

export interface ExpressionBase extends Node, HasSpan {
    // 基础表达式接口
}

export interface Span {
    start: number;
    end: number;
    ctxt: number;
}

export interface Decorator extends Node, HasSpan {
    type: "Decorator";
    expression: string;
}

export type ClassMember = string;
export type Expression = string;
export type Identifier = string;
export type Param = string;
export type BlockStatement = string;


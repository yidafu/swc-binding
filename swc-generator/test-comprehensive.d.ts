// 1. 基础类型系统
// 基本接口定义(Span, Node, HasSpan)
// 字面量类型("method" | "getter" | "setter")
// 2. 类型组合
// ✅ 联合类型(type SimpleUnion = string | number)
// ✅ 交叉类型(type IntersectionType = Node & HasSpan)
// ✅ 复杂联合类型(多个类型的联合)
// 3. 接口特性
// ✅ 接口继承(extends )
// ✅ 多重继承(extends Node, HasSpan)
// ✅ 可选属性(optional ?)
// ✅ 只读属性(readonly)
// 4. 集合类型
// ✅ 数组类型(string[], number[][])
// ✅ 元组类型([string, number], [string, number, boolean ?])
// 5. 泛型
// ✅ 单泛型(GenericInterface<T>)
// ✅ 多泛型(MultipleGenerics<T, U>)
// ✅ 泛型约束(U extends string)
// ✅ 嵌套泛型(T[])
// 6. 函数类型
// ✅ 简单函数类型((arg: string) => number)
// ✅ 可选参数(optional ?: number)
// ✅ Rest 参数(...args: string[])
// ✅ 泛型函数(<T>(value: T) => T)
// 7. 高级类型
// ✅ 索引签名([key: string]: any)
// ✅ 条件类型(T extends string ? "string" : "other")
// ✅ 映射类型([P in keyof T]: T[P])
// ✅ 类型操作符(keyof, Readonly)
// ✅ Rest 类型(RestElement)
// 8. 递归类型
// ✅ 递归类型定义(children ?: RecursiveType[])
// 9. 实用类型
// ✅ Record 类型(Record<string, string>)
// ✅ Partial 类型模式
// ✅ Readonly 类型模式
// 10. 特殊场景
// ✅ undefined 联合((Expression | undefined)[])
// ✅ 多级嵌套类型引用

// 综合测试文件 - 包含所有主要 TypeScript 语法特性

// ============ 基础接口 ============
interface Span {
    start: number;
    end: number;
    ctxt: number;
}

interface Node {
    type: string;
}

interface HasSpan {
    span: Span;
}

// ============ 字面量类型 ============
type StringLiteralType = "method" | "getter" | "setter";
type NumericLiteralType = 0 | 1 | 2 | 3;

// ============ 联合类型 ============
type SimpleUnion = string | number;
type ComplexUnion = Identifier | StringLiteral | NumericLiteral;

// ============ 交叉类型 ============
type IntersectionType = Node & HasSpan;

// ============ 接口继承 ============
interface BaseExpression extends Node, HasSpan {
    baseField: string;
}

interface Identifier extends BaseExpression {
    type: "Identifier";
    value: string;
    optional: boolean;
}

// ============ 数组类型 ============
interface ArrayContainer {
    items: string[];
    nestedArray: number[][];
}

// ============ 元组类型 ============
type SimpleTuple = [string, number];
type ComplexTuple = [string, number, boolean?];

// ============ 可选属性 ============
interface OptionalFields {
    required: string;
    optional?: number;
    readonly readonlyField: boolean;
}

// ============ 泛型 ============
interface GenericInterface<T> {
    value: T;
    items: T[];
}

interface MultipleGenerics<T, U extends string> {
    first: T;
    second: U;
}

// ============ 函数类型 ============
interface FunctionTypes {
    simpleFunc: (arg: string) => number;
    optionalParam: (required: string, optional?: number) => void;
    restParam: (...args: string[]) => void;
    genericFunc: <T>(value: T) => T;
}

// ============ 索引签名 ============
interface IndexSignature {
    [key: string]: any;
    specificKey: number;
}

// ============ 类型别名与递归 ============
type RecursiveType = {
    value: string;
    children?: RecursiveType[];
};

type ConditionalType<T> = T extends string ? "string" : "other";

// ============ 映射类型 ============
type ReadonlyType<T> = {
    readonly [P in keyof T]: T[P];
};

type PartialType<T> = {
    [P in keyof T]?: T[P];
};

// ============ 类型操作符 ============
interface TypeOperators<T> {
    keys: keyof T;
    readonlyValue: Readonly<T>;
}

// ============ 字面量对象类型 ============
interface StringLiteral extends Node, HasSpan {
    type: "StringLiteral";
    value: string;
    raw?: string;
}

interface NumericLiteral extends Node, HasSpan {
    type: "NumericLiteral";
    value: number;
    raw?: string;
}

interface BooleanLiteral extends Node, HasSpan {
    type: "BooleanLiteral";
    value: boolean;
}

// ============ 复杂联合类型 ============
type Expression = 
    | Identifier 
    | StringLiteral 
    | NumericLiteral 
    | BooleanLiteral 
    | ArrayExpression
    | ObjectExpression;

// ============ 带可选泛型的接口 ============
interface CallExpression extends BaseExpression {
    type: "CallExpression";
    callee: Expression;
    arguments: Argument[];
    typeArguments?: TypeParameterInstantiation;
}

interface ArrayExpression extends BaseExpression {
    type: "ArrayExpression";
    elements: (Expression | undefined)[];
}

interface ObjectExpression extends BaseExpression {
    type: "ObjectExpression";
    properties: Property[];
}

// ============ Rest 类型 ============
interface RestElement extends Node, HasSpan {
    type: "RestElement";
    rest: Span;
    argument: Pattern;
}

// ============ 嵌套泛型 ============
interface TypeParameterInstantiation extends Node, HasSpan {
    type: "TypeParameterInstantiation";
    params: Type[];
}

// ============ 复杂类型组合 ============
type Pattern = 
    | Identifier 
    | ArrayPattern 
    | RestElement 
    | ObjectPattern 
    | AssignmentPattern;

interface ArrayPattern extends Node, HasSpan {
    type: "ArrayPattern";
    elements: (Pattern | undefined)[];
    optional: boolean;
}

interface ObjectPattern extends Node, HasSpan {
    type: "ObjectPattern";
    properties: ObjectPatternProperty[];
    optional: boolean;
}

interface AssignmentPattern extends Node, HasSpan {
    type: "AssignmentPattern";
    left: Pattern;
    right: Expression;
}

type ObjectPatternProperty = KeyValueProperty | RestElement;

interface KeyValueProperty extends Node {
    type: "KeyValueProperty";
    key: string;
    value: Pattern;
}

// ============ 类型别名的不同形式 ============
type Type = KeywordType | ArrayType | UnionType | IntersectionType;

interface KeywordType extends Node, HasSpan {
    type: "KeywordType";
    kind: "string" | "number" | "boolean" | "any" | "unknown" | "void" | "never";
}

interface ArrayType extends Node, HasSpan {
    type: "ArrayType";
    elemType: Type;
}

interface UnionType extends Node, HasSpan {
    type: "UnionType";
    types: Type[];
}

interface IntersectionType extends Node, HasSpan {
    type: "IntersectionType";
    types: Type[];
}

// ============ 记录类型 ============
type RecordType = Record<string, string>;
type Argument = Expression | SpreadElement;

interface SpreadElement extends Node {
    type: "SpreadElement";
    spread: Span;
    argument: Expression;
}

// ============ 多级继承 ============
interface Property extends Node, HasSpan {
    key: string;
}


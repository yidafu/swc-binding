// 测试 3: 混合联合类型
// 处理函数: processMixedUnion
// 预期: 生成 typealias 或 Union.U2, Union.U3 等

// 双类型联合
type StringOrNumber = string | number;
type BooleanOrString = boolean | string;

// 三类型联合
type TripleUnion = string | number | boolean;

// 包含 undefined 的联合
type MaybeString = string | undefined;
type NullableNumber = number | null;

// 复杂混合
type MixedValue = string | number | boolean | null;


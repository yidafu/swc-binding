// 测试 9: 索引签名
// 测试 Map 类型的生成

// 字符串索引
interface StringIndex {
    [key: string]: string;
}

// 数字索引
interface NumberIndex {
    [index: number]: string;
}

// 混合索引和具名属性
interface MixedIndex {
    [key: string]: any;
    specificProp: number;
}

// 值类型为复杂类型的索引
interface ComplexValueIndex {
    [key: string]: {
        value: string;
        count: number;
    };
}


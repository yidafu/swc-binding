// 测试 7: 数组类型
// 测试各种数组类型的处理

interface ArrayTypes {
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


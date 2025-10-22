// 测试 11: 函数类型
// 测试函数签名的转换

interface FunctionSignatures {
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
type Callback = (data: string) => void;
type Predicate<T> = (value: T) => boolean;
type Transformer<T, U> = (input: T) => U;


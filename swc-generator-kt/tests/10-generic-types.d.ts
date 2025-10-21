// 测试 10: 泛型类型
// 测试泛型参数的处理

// 单泛型
interface Container<T> {
    value: T;
}

// 多泛型
interface Pair<K, V> {
    key: K;
    value: V;
}

// 泛型约束
interface Constrained<T extends string> {
    data: T;
}

// 泛型数组
interface GenericArray<T> {
    items: T[];
}

// 嵌套泛型
interface Nested<T> {
    wrapper: Container<T>;
}


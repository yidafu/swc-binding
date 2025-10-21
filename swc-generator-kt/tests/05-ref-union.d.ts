// 测试 5: 引用联合类型
// 处理函数: processRefUnion
// 预期: 生成 sealed interface，建立继承关系

interface BaseType {
    kind: string;
}

interface TypeA extends BaseType {
    kind: "A";
    valueA: string;
}

interface TypeB extends BaseType {
    kind: "B";
    valueB: number;
}

interface TypeC extends BaseType {
    kind: "C";
    valueC: boolean;
}

// 引用联合：sealed interface
type UnionType = TypeA | TypeB | TypeC;

// 另一个引用联合
interface ItemOne {
    id: number;
}

interface ItemTwo {
    name: string;
}

type Item = ItemOne | ItemTwo;


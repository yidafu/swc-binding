// 简单的测试文件 - 不使用 export 以便 AST 遍历器可以找到
interface TestInterface {
    name: string;
    value: number;
}

type TestType = "a" | "b" | "c";

type TestUnion = TestInterface | string;

interface AnotherInterface extends TestInterface {
    extra: boolean;
}


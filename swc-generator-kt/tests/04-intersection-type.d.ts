// 测试 4: 交叉类型
// 处理函数: processIntersectionType
// 预期: 生成 Base interface 和实现类

interface BaseNode {
    type: string;
}

interface BaseSpan {
    span: {
        start: number;
        end: number;
    };
}

// 交叉类型：多继承模拟
interface ExprBase extends BaseNode, BaseSpan {
    extraField: string;
}

// 另一个交叉类型示例
interface StmtBase extends BaseNode, BaseSpan {
    stmtField: boolean;
}


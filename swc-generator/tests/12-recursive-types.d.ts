// 测试 12: 递归类型
// 测试类型的递归引用

interface TreeNode {
    value: string;
    children?: TreeNode[];
}

// 互相递归
interface NodeA {
    type: "A";
    next?: NodeB;
}

interface NodeB {
    type: "B";
    next?: NodeA;
}

// 递归联合类型
type RecursiveUnion = string | RecursiveUnion[];

// 链表
interface ListNode {
    data: number;
    next?: ListNode;
}


// 测试 6: 接口继承关系
// 处理函数: buildInheritanceRelationship, processInterfaces
// 预期: 建立继承关系图，生成 interface + Impl 类

interface ParentInterface {
    parentProp: string;
}

interface ChildInterface extends ParentInterface {
    childProp: number;
}

interface GrandChildInterface extends ChildInterface {
    grandChildProp: boolean;
}

// 多重继承
interface MixinA {
    methodA: string;
}

interface MixinB {
    methodB: number;
}

interface Combined extends MixinA, MixinB {
    ownProp: boolean;
}


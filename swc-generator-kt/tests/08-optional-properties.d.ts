// 测试 8: 可选属性和修饰符
// 测试 optional, readonly 等属性修饰符

interface PropertyModifiers {
    // 必需属性
    required: string;
    
    // 可选属性
    optional?: number;
    
    // 只读属性
    readonly readonlyField: boolean;
    
    // 可选 + 只读
    readonly optionalReadonly?: string;
}

// 全部可选
interface AllOptional {
    field1?: string;
    field2?: number;
    field3?: boolean;
}

// 全部必需
interface AllRequired {
    field1: string;
    field2: number;
    field3: boolean;
}


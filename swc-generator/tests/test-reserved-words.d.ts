// 测试Kotlin保留字处理
interface Node {
    type: string;
}

interface class extends Node {
    name: string;
}

interface interface extends Node {
    value: number;
}

interface fun extends Node {
    method: string;
}

interface val extends Node {
    data: boolean;
}

interface var extends Node {
    count: number;
}

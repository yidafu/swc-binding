// 测试 SWC Kotlin 保留字处理
export interface Node {
    type: string;
}

export interface jsClass extends Node {
    name: string;
}

export interface jsInterface extends Node {
    value: number;
}

export interface jsFun extends Node {
    method: string;
}

export interface jsVal extends Node {
    data: boolean;
}

export interface jsVar extends Node {
    count: number;
}

// SWC 保留字测试
export interface jsObject extends Node {
    properties: any[];
}

export interface jsInline extends Node {
    content: string;
}

export interface jsIn extends Node {
    target: any;
}

export interface jsSuper extends Node {
    property: string;
}

export interface jsWhen extends Node {
    condition: any;
}

export interface jsIs extends Node {
    type: string;
}

export interface jsAs extends Node {
    value: any;
}

export interface jsType extends Node {
    name: string;
}

export interface jsImport extends Node {
    source: string;
}

export interface jsPackage extends Node {
    name: string;
}

// 测试 1: SWC 配置类型
// 测试基本的配置接口和类型别名

export interface Plugin {
    (module: Program): Program;
}

export interface Options extends Config {
    script?: boolean;
    cwd?: string;
    caller?: CallerOptions;
    filename?: string;
}

export interface Config {
    test?: string | string[];
    exclude?: string | string[];
    env?: EnvConfig;
    jsc?: JscConfig;
    module?: ModuleConfig;
    minify?: boolean;
    sourceMaps?: boolean | "inline";
}

export interface JscConfig {
    parser?: ParserConfig;
    transform?: TransformConfig;
    target?: JscTarget;
    keepClassNames?: boolean;
}

export type JscTarget = "es3" | "es5" | "es2015" | "es2016" | "es2017" | "es2018" | "es2019" | "es2020" | "es2021" | "es2022" | "es2023" | "es2024" | "esnext";

export type ParserConfig = TsParserConfig | EsParserConfig;

export interface TsParserConfig {
    syntax: "typescript";
    tsx?: boolean;
    decorators?: boolean;
}

export interface EsParserConfig {
    syntax: "ecmascript";
    jsx?: boolean;
}

export interface TransformConfig {
    react?: ReactConfig;
}

export interface ReactConfig {
    pragma?: string;
    pragmaFrag?: string;
    throwIfNamespace?: boolean;
    development?: boolean;
    useBuiltins?: boolean;
    refresh?: boolean;
}

export interface EnvConfig {
    mode?: "usage" | "entry";
    debug?: boolean;
    targets?: any;
}

export type ModuleConfig = Es6Config | CommonJsConfig | UmdConfig | AmdConfig;

export interface BaseModuleConfig {
    type: string;
}

export interface Es6Config extends BaseModuleConfig {
    type: "es6";
}

export interface CommonJsConfig extends BaseModuleConfig {
    type: "commonjs";
}

export interface UmdConfig extends BaseModuleConfig {
    type: "umd";
}

export interface AmdConfig extends BaseModuleConfig {
    type: "amd";
}

export interface CallerOptions {
    name: string;
    [key: string]: any;
}

// 需要的基本类型
export interface Program {
    type: string;
}

export interface Module extends Program {
    type: "Module";
    body: any[];
}

export interface Script extends Program {
    type: "Script";
    body: any[];
}

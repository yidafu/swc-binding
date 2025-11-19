# swc-generator

[查看英文版本: README.md](README.md)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](../LICENSE)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.21-blue.svg)](https://kotlinlang.org/)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-green.svg)](https://github.com/swc-project/swc)
[![JVM](https://img.shields.io/badge/JVM-17+-red.svg)](https://www.oracle.com/java/)

用于为 SWC Kotlin/JVM 绑定生成代码的 Kotlin 工具。它读取 SWC 类型定义并生成强类型的 Kotlin API 与 DSL 辅助方法。

## 特性

- 从 `@swc/types` 生成 Kotlin 数据模型
- 提供用于构建 AST 的 Kotlin DSL
+- 生成配置的序列化/反序列化辅助
 - 生成结果稳定、可重复
- 为生成代码提供回归测试

## 快速开始

### 构建

```bash
./gradlew :swc-generator:build
```

### 运行测试

```bash
./swc-generator/run-tests.sh
```

### 输入

- `swc-generator/ast.json` 或 `tests/*.ts` 作为类型来源
- `swc-generator/swc-generator-config.yaml` 作为生成配置

### 输出

- 生成的 Kotlin 源码位于 `swc-binding/src/main/kotlin/...`
- 测试产物位于 `swc-generator/test-outputs`

### 配置要点（Union）

- `rules.namingRules.union.includeNullabilityInToken`: 是否在 Union 命名去重 token 中包含可空性标记（默认 `false`，开启后可避免同构但空性不同的碰撞）。
- `rules.union.factoryArity`: 生成的 Union 工厂支持的元数（默认 `[2,3,4,5]`）。
- `rules.union.hotFixedCombos`: 需要固化为 object 的高频组合列表（字符串化 key，生成器内部解释）。

### 特殊字段处理（ctxt）

某些 Rust 结构体包含独立的 `ctxt: SyntaxContext` 字段（非 `span` 内的 `ctxt`），这些字段没有 `serde(default)`，因此反序列化时是必需的。代码生成器会自动为以下类添加 `ctxt: Int = 0` 字段并添加 `@EncodeDefault` 注解：

**语句相关：**
- `BlockStatement`：对应 Rust `BlockStmt` 结构体

**表达式相关：**
- `CallExpression`：对应 Rust `CallExpr` 结构体
- `NewExpression`：对应 Rust `NewExpr` 结构体
- `ArrowFunctionExpression`：对应 Rust `ArrowExpr` 结构体
- `TaggedTemplateExpression`：对应 Rust `TaggedTpl` 结构体

**声明相关：**
- `FunctionDeclaration`：对应 Rust `Function` 结构体（通过 `FnDecl` 的 `#[serde(flatten)]` 展开）
- `VariableDeclaration`：对应 Rust `VarDecl` 结构体

**类相关：**
- `Class`：对应 Rust `Class` 结构体
- `PrivateProperty`：对应 Rust `PrivateProp` 结构体
- `Constructor`：对应 Rust `Constructor` 结构体

**标识符相关：**
- `Identifier`：对应 Rust `Ident` 结构体

这个特殊处理确保生成的 Kotlin 代码与 Rust 端的反序列化要求兼容。

**注意**：虽然 Rust 端的 `Span` 结构体本身不包含 `ctxt` 字段（只有 `start` 和 `end`），但为了保持与 TypeScript 定义和现有代码的兼容性，Kotlin 端的 `Span` 类仍保留 `ctxt` 字段。这些节点级别的独立 `ctxt` 字段是额外的，用于匹配 Rust 端的反序列化要求。

## 开发

- 参考 `swc-generator/ARCHITECTURE.md` 与 `ARCHITECTURE_OVERVIEW.md`
- 代码生成规则：`KOTLIN_CODE_GENERATION_RULES.md`
- 脚本：`run-tests.sh`、`run-single-test.sh`、`generate-test-types.sh`

## 许可协议

MIT



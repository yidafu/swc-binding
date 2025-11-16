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

## 开发

- 参考 `swc-generator/ARCHITECTURE.md` 与 `ARCHITECTURE_OVERVIEW.md`
- 代码生成规则：`KOTLIN_CODE_GENERATION_RULES.md`
- 脚本：`run-tests.sh`、`run-single-test.sh`、`generate-test-types.sh`

## 许可协议

MIT



# swc-jni

[查看英文版本: README.md](README.md)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](../LICENSE)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-green.svg)](https://github.com/swc-project/swc)
[![Rust](https://img.shields.io/badge/rust-1.70+-orange.svg)](https://www.rust-lang.org/)
[![JVM](https://img.shields.io/badge/JVM-17+-red.svg)](https://www.oracle.com/java/)

面向 Kotlin/Java 的 SWC JNI 绑定，提供同步与异步 API，覆盖解析、转换、压缩与代码生成。

## 特性

- 🚀 基于 SWC 43.0.0 的极速 JS/TS 解析
- 🔄 代码转换（ES6 → ES5、TypeScript → JavaScript）
- 📦 代码压缩（支持压缩与 mangling）
- 🎨 代码生成（支持 Source Map）
- ⚡ 同步与异步 API（协程与回调）
- 🧪 覆盖核心功能的测试套件
- 🔧 使用最新稳定依赖版本

## 依赖

使用最新稳定版本：

| 依赖 | 版本 | 说明 |
|-----|------|------|
| swc | 43.0.0 | SWC 编译核心 |
| swc_common | 15.0.0 | 通用工具 |
| swc_ecma_ast | 16.0.0 | ECMAScript AST |
| swc_ecma_codegen | 18.0.0 | 代码生成 |
| swc_ecma_transforms | 36.0.0 | 代码转换 |
| swc_ecma_transforms_base | 28.0.0 | 转换基础 |
| swc_ecma_visit | 16.0.0 | AST 访问器 |
| anyhow | 1.0.100 | 错误处理 |
| serde | 1.0.225 | 序列化 |
| serde_json | 1.0.115 | JSON 序列化 |
| tracing | 0.1.41 | 日志 |
| thiserror | 2.0.17 | 错误类型 |
| jni | 0.21.1 | Java Native Interface |
| jni_fn | 0.1.2 | JNI 函数宏 |
| tempfile | 3.23.0 | 临时文件（dev） |

版本以 `Cargo.toml` 为准。

## 测试

```bash
# 运行全部测试
cargo test

# 使用脚本
./run-tests.sh

# 仅单元测试
cargo test --lib

# 仅集成测试
cargo test --test integration_test
```

## 开发

```bash
# Debug 编译
cargo build

# Release 编译
cargo build --release

# macOS 动态库（Debug）
./build_debug.sh

# 所有平台动态库
./build.sh

# 格式化与 Lint
cargo fmt
cargo clippy
```

## 项目结构

```
swc-jni/
├── src/
│   ├── lib.rs
│   ├── async_utils.rs
│   ├── parse.rs
│   ├── transform.rs
│   ├── minify.rs
│   ├── print.rs
│   └── util.rs
├── tests/
│   └── integration_test.rs
├── test-fixtures/
└── Cargo.toml
```

## 许可协议

MIT



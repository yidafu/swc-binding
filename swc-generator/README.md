# swc-generator

[Read this in Chinese: README.zh-CN.md](README.zh-CN.md)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](../LICENSE)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.21-blue.svg)](https://kotlinlang.org/)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-green.svg)](https://github.com/swc-project/swc)
[![JVM](https://img.shields.io/badge/JVM-17+-red.svg)](https://www.oracle.com/java/)

Kotlin-based code generator for SWC Kotlin/JVM bindings. It reads SWC type definitions and generates strongly-typed Kotlin APIs and DSL helpers.

## Features

- Generate Kotlin models from `@swc/types`
- Kotlin DSL builders for AST construction
- Config serialization/deserialization helpers
- Deterministic generation and stable formatting
- Regression tests for generated code

## Getting Started

### Build

```bash
./gradlew :swc-generator:build
```

### Run Tests

```bash
./swc-generator/run-tests.sh
```

### Inputs

- `swc-generator/ast.json` or `tests/*.ts` for type sources
- `swc-generator/swc-generator-config.yaml` for generation config

### Outputs

- Generated Kotlin sources under `swc-binding/src/main/kotlin/...`
- Test fixtures under `swc-generator/test-outputs`

### Special Field Handling (ctxt)

Some Rust structs contain independent `ctxt: SyntaxContext` fields (not within `span`), and these fields don't have `serde(default)`, making them required during deserialization. The code generator automatically adds `ctxt: Int = 0` fields with `@EncodeDefault` annotations to the following classes:

**Statement-related:**
- `BlockStatement`: corresponds to Rust `BlockStmt` struct

**Expression-related:**
- `CallExpression`: corresponds to Rust `CallExpr` struct
- `NewExpression`: corresponds to Rust `NewExpr` struct
- `ArrowFunctionExpression`: corresponds to Rust `ArrowExpr` struct
- `TaggedTemplateExpression`: corresponds to Rust `TaggedTpl` struct

**Declaration-related:**
- `FunctionDeclaration`: corresponds to Rust `Function` struct (expanded through `FnDecl`'s `#[serde(flatten)]`)
- `VariableDeclaration`: corresponds to Rust `VarDecl` struct

**Class-related:**
- `Class`: corresponds to Rust `Class` struct
- `PrivateProperty`: corresponds to Rust `PrivateProp` struct
- `Constructor`: corresponds to Rust `Constructor` struct

**Identifier-related:**
- `Identifier`: corresponds to Rust `Ident` struct

This special handling ensures the generated Kotlin code is compatible with Rust's deserialization requirements.

**Note**: Although the Rust `Span` struct itself does not contain a `ctxt` field (only `start` and `end`), the Kotlin `Span` class retains the `ctxt` field for compatibility with TypeScript definitions and existing code. These node-level independent `ctxt` fields are additional fields needed to match Rust's deserialization requirements.

## Development

- See `swc-generator/ARCHITECTURE.md` and `ARCHITECTURE_OVERVIEW.md`
- Coding rules: `KOTLIN_CODE_GENERATION_RULES.md`
- Scripts: `run-tests.sh`, `run-single-test.sh`, `generate-test-types.sh`

## License

MIT



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

## Development

- See `swc-generator/ARCHITECTURE.md` and `ARCHITECTURE_OVERVIEW.md`
- Coding rules: `KOTLIN_CODE_GENERATION_RULES.md`
- Scripts: `run-tests.sh`, `run-single-test.sh`, `generate-test-types.sh`

## License

MIT



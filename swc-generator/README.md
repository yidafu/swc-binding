# swc-generator

[Read this in Chinese: README.zh-CN.md](README.zh-CN.md)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](../LICENSE)
[![Kotlin](https://img.shields.io/badge/kotlin-2.2.10-blue.svg)](https://kotlinlang.org/)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-98D982?logo=swc&logoColor=white)](https://github.com/swc-project/swc)
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

### Explicit Type Field Handling

Some classes require explicit `type` fields even though they have `@JsonClassDiscriminator` and `@SerialName` annotations. This is because when these classes are serialized as concrete types (e.g., `Array<VariableDeclarator>`), the discriminator doesn't automatically add the `type` field. The following classes have explicit `type` fields added:

- `VariableDeclarator`
- `Param` (Kotlin class name, serialized as "Parameter")
- `BlockStatement`
- `JSXOpeningElement`
- `JSXClosingElement`

### Property Type Overrides

Certain properties require special type mappings that differ from their TypeScript definitions:

**Global Type Overrides:**

Properties that are declared as `any` in TypeScript but are mapped to `String?` in Kotlin:

- `global_defs` / `globalDefs`
- `targets`
- `sequences`
- `pure_getters` / `pureGetters`
- `top_retain` / `topRetain`
- `toplevel`

**Interface-Specific Overrides:**

- `ForOfStatement.await`: Declared as `Span?` in TypeScript but actually should be `Boolean?` in Kotlin. This is a SWC-specific case that requires special handling.

### Kotlin Reserved Word Mapping

To avoid conflicts with Kotlin keywords, certain property names are automatically mapped:

- `object` → `jsObject`
- `inline` → `jsInline`
- `in` → `jsIn`
- `super` → `jsSuper`
- `class` → `jsClass`
- `interface` → `jsInterface`
- `fun` → `jsFun`
- `val` → `jsVal`
- `var` → `jsVar`
- `when` → `jsWhen`
- `is` → `jsIs`
- `as` → `jsAs`
- `import` → `jsImport`
- `package` → `jsPackage`

### Type Name Overrides

To avoid conflicts with Kotlin built-in types or reserved names, certain type names are mapped:

- `Class` → `JsClass`
- `Super` → `JsSuper`
- `Import` → `JsImport`
- `ExprOrSpread` → `Argument` (unified mapping to avoid dual-type decoding/DSL conflicts)

### Span Property Handling

Special handling for `span` and span coordinate properties:

- **`span` property**: Uses `emptySpan()` function call as default value, ensuring the `ctxt` field is included
- **Span coordinate properties** (`start`, `end`, `ctxt` in `Span` class): Use `Int` type with default value `0`
- **`start` and `end`**: Annotated with `@EncodeDefault` to ensure serialization
- **`ctxt`**: Not annotated with `@EncodeDefault` because `@swc/core` doesn't output default `ctxt` values of 0

### Serialization Annotations

The generator automatically adds appropriate serialization annotations:

- **`@Serializable`**: Added to sealed interfaces and final classes
- **`@JsonClassDiscriminator`**: Added to base interfaces for polymorphic serialization
- **`@SerialName`**: Added to properties to ensure correct serialization names
- **`@SwcDslMarker`**: Added to interfaces and implementation classes for DSL type safety
- **`@OptIn(ExperimentalSerializationApi)`**: Added to implementation classes that use experimental serialization features

## Development

- See `swc-generator/ARCHITECTURE.md` and `ARCHITECTURE_OVERVIEW.md`
- Coding rules: `KOTLIN_CODE_GENERATION_RULES.md`
- Scripts: `run-tests.sh`, `run-single-test.sh`, `generate-test-types.sh`

## License

MIT

# swc-binding (module)

[Read this in Chinese: README.zh-CN.md](README.zh-CN.md)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](../LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/dev.yidafu.swc/swc-binding.svg)](https://search.maven.org/artifact/dev.yidafu.swc/swc-binding)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.21-blue.svg)](https://kotlinlang.org/)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-green.svg)](https://github.com/swc-project/swc)
[![JVM](https://img.shields.io/badge/JVM-17+-red.svg)](https://www.oracle.com/java/)

Kotlin/JVM library that provides idiomatic bindings to SWC via JNI. This is the published artifact used by applications.

## Installation

```kotlin
implementation("dev.yidafu.swc:swc-binding:0.7.0")
```

## Quick Start

```kotlin
val swc = SwcNative()
val output = swc.transformSync(
    code = "const x: number = 42",
    isModule = true,
    options = Options().apply {
        jsc = jscConfig {
            parser = ParserConfig().apply { syntax = "typescript" }
        }
    }
)
println(output.code)
```

## Documentation

- API Docs: https://yidafu.github.io/swc-binding/docs/
- Samples: `swc-binding/src/test`

## Important Notes

### Manual AST Construction

When manually constructing AST nodes using the DSL, you **must** explicitly set all boolean fields to avoid serialization errors. The Rust backend expects non-null boolean values, but Kotlin's nullable boolean fields (`Boolean?`) will serialize as `null` if not set.

**Example - Correct:**
```kotlin
val mod = module {
    span = emptySpan()
    body = arrayOf(
        variableDeclaration {
            span = emptySpan()
            kind = VariableDeclarationKind.CONST
            declare = false  // ✅ Explicitly set
            declarations = arrayOf(
                variableDeclarator {
                    id = identifier {
                        value = "x"
                        optional = false  // ✅ Explicitly set
                    }
                    init = numericLiteral { ... }
                }
            )
        }
    )
}
```

**Example - Incorrect:**
```kotlin
val mod = module {
    body = arrayOf(
        variableDeclaration {
            kind = VariableDeclarationKind.CONST
            // ❌ Missing 'declare = false' - will cause serialization error
            declarations = arrayOf(...)
        }
    )
}
```

**Common boolean fields that must be set:**
- `Identifier.optional: Boolean?` → set to `false`
- `VariableDeclaration.declare: Boolean?` → set to `false`
- `ArrowFunctionExpression.async: Boolean?` → set to `false`
- `ArrowFunctionExpression.generator: Boolean?` → set to `false`
- `TemplateElement.tail: Boolean?` → set to `false` or `true` (depending on position)

**Recommended approach:** Use `parseSync` to parse code into AST instead of manually constructing it, as parsed ASTs have all fields properly initialized.

```kotlin
// ✅ Recommended: Parse code instead of manual construction
val mod = swcNative.parseSync("const x = 42;", esParseOptions { }, "test.js") as Module
val output = swcNative.printSync(mod, options { })
```

## License

MIT



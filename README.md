# swc-binding

[Read this in Chinese: README.zh-CN.md](README.zh-CN.md)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/dev.yidafu.swc/swc-binding.svg)](https://search.maven.org/artifact/dev.yidafu.swc/swc-binding)
[![Kotlin](https://img.shields.io/badge/kotlin-2.2.10-blue.svg)](https://kotlinlang.org/)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-98D982?logo=swc&logoColor=white)](https://github.com/swc-project/swc)
[![Rust](https://img.shields.io/badge/rust-1.70+-CE412B?logo=rust&logoColor=white)](https://www.rust-lang.org/)
[![JVM](https://img.shields.io/badge/JVM-17+-red.svg)](https://www.oracle.com/java/)

[SWC](https://github.com/swc-project/swc) JVM binding in Kotlin.

## Table of Contents

- [swc-binding](#swc-binding)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
  - [Version Compatibility](#version-compatibility)
  - [Documentation](#documentation)
  - [Quick Start](#quick-start)
  - [Usage](#usage)
    - [Transform code](#transform-code)
    - [Parse code](#parse-code)
    - [Async Methods (Coroutine Support)](#async-methods-coroutine-support)
      - [Using Coroutines (Recommended)](#using-coroutines-recommended)
      - [Using Callbacks](#using-callbacks)
      - [Available Async Methods](#available-async-methods)
      - [Threading Model](#threading-model)
  - [API](#api)
  - [Development](#development)
    - [Building](#building)
    - [Testing](#testing)
    - [Publishing](#publishing)
  - [AST DSL](#ast-dsl)
    - [Build AST segment](#build-ast-segment)
    - [Boolean | T options](#boolean--t-options)
  - [Article](#article)
  - [Known Issues](#known-issues)
    - [externalHelpers Configuration](#externalhelpers-configuration)
  - [License](#license)

## Installation

```kotlin
implementation("dev.yidafu.swc:swc-binding:0.7.0")
```

## Version Compatibility

| swc-binding | Rust SWC | @swc/types | Notes |
|-------------|----------|------------|-------|
| 0.7.0       | 43.0.0   | 0.1.25     | Latest stable release with full async support |
| 0.6.0       | 0.270.25   | 0.1.5     |  |

## Documentation

- [API Documentation - Kotlin Doc](https://yidafu.github.io/swc-binding/docs/)
- [Project Wiki - Complete Guide](docs/wiki.md) - Auto-generated from repo wiki, includes architecture, usage guides, and detailed explanations

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

## Usage

### Transform code

```kotlin
val swc = SwcNative()
val res = swc.transformSync(
    """
     import x from './test.js';
     class Foo {
       bar: string
     }
    """.trimIndent(),
    false,
    Options().apply {
        jsc  = jscConfig {
            parser = ParserConfig().apply {
                syntax = "ecmascript"
            }
        }
    }
)
```

### Parse code

```kotlin
val ast = SwcNative().parseSync(
    """
     import x from './test.js';
     class Foo {
       bar: string
     }
    """.trimIndent(),
    ParseOptions().apply { syntax = "typescript" },
    "temp.js"
)
```

### Async Methods (Coroutine Support)

All SWC methods now support asynchronous execution using Kotlin coroutines. Async methods run in background threads and don't block the calling thread.

#### Using Coroutines (Recommended)

```kotlin
import kotlinx.coroutines.*

// Async parse
suspend fun parseCode() {
    val swc = SwcNative()
    val options = ParserConfig(
        syntax = Syntax.Typescript(TsSyntax(tsx = true)),
        target = JscTarget.Es2020
    )
    
    val ast = swc.parseAsync(
        code = "const x: number = 42;",
        options = options,
        filename = "example.ts"
    )
    println("Parsed: ${ast.type}")
}

// Parallel parsing
suspend fun parseMultiple() = coroutineScope {
    val swc = SwcNative()
    val options = ParserConfig(/* ... */)
    
    val results = listOf("code1.ts", "code2.ts", "code3.ts")
        .map { file ->
            async { swc.parseFileAsync(file, options) }
        }
        .awaitAll()
}
```

#### Using Callbacks

```kotlin
val swc = SwcNative()
swc.parseAsync(
    code = "const x: number = 42;",
    options = parseOptions,
    filename = "example.ts",
    onSuccess = { ast -> println("Success: ${ast.type}") },
    onError = { error -> println("Error: $error") }
)
```

#### Available Async Methods

All synchronous methods have async counterparts:

- `parseAsync()` - Asynchronously parse code
- `parseFileAsync()` - Asynchronously parse file
- `transformAsync()` - Asynchronously transform code
- `transformFileAsync()` - Asynchronously transform file
- `printAsync()` - Asynchronously print AST
- `minifyAsync()` - Asynchronously minify code

#### Threading Model

- Async methods immediately return and execute work in background threads
- Callbacks are invoked from Rust background threads
- Use coroutines for structured concurrency and easy thread management
- No blocking of the calling thread

See `AsyncSamples.kt` for more examples.

## API

### parseSync

[see more](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/parse-sync.html)

> parsing React source code

see [swc#parseSync](https://swc.rs/docs/usage/core#parsesync)

Kotlin DSL Wrapper method

```kotlin
@Throws(RuntimeException::class)
fun parseSync(code: String, options: ParserConfig, filename: String?): Program 
```

Native method

```kotlin
@Throws(RuntimeException::class)
fun parseSync(code: String, options: String, filename: String?): String
```

### parseFileSync

[see more](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/parse-file-sync.html)

[swc#parseFileSync](https://swc.rs/docs/usage/core#parsefilesync)

Kotlin DSL Wrapper method

```kotlin
@Throws(RuntimeException::class)
fun parseFileSync(filepath: String, options: ParserConfig): Program 
```

Native method

```kotlin
@Throws(RuntimeException::class)
fun parseFileSync(filepath: String, options: String): String
```

### transformSync

[see more](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/transform-sync.html)

[swc#transformSync](https://swc.rs/docs/usage/core#transformsync)

Kotlin DSL Wrapper method

```kotlin
@Throws(RuntimeException::class)
fun transformSync(code: String, isModule: Boolean, options: Options): TransformOutput
```

Native method

```kotlin
@Throws(RuntimeException::class)
fun transformSync(code: String, isModule: Boolean, options: String): String
```

### transformFileSync

[see more](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/transform-file-sync.html)

[swc#transformFileSync](https://swc.rs/docs/usage/core#transformfilesync)

Kotlin DSL Wrapper method

```kotlin
@Throws(RuntimeException::class)
fun transformFileSync(filepath: String, isModule: Boolean, options: Options): TransformOutput
```

Native method

```kotlin
@Throws(RuntimeException::class)
fun transformFileSync(filepath: String, isModule: Boolean, options: String): String
```

### printSync

[see more](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/print-sync.html)

[swc#printSync](https://swc.rs/docs/usage/core#printsync)

Kotlin DSL Wrapper method

```kotlin
@Throws(RuntimeException::class)
fun printSync(program: Program, options: Options): TransformOutput
```

Native method

```kotlin
@Throws(RuntimeException::class)
fun printSync(program: String, options: String): String
```

### minifySync

[see more](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/minify-sync.html)

[swc#minifySync](https://swc.rs/docs/usage/core#minifysync)

Kotlin DSL Wrapper method

```kotlin
@Throws(RuntimeException::class)
fun minifySync(program: Program, options: Options): TransformOutput
```

Native method

```kotlin
@Throws(RuntimeException::class)
fun minifySync(program: String, options: String): String
```

## Development

### Building

To build the entire project:

```bash
./gradlew build
```

### Testing

Run all tests:

```bash
./gradlew test
```

Run tests for a specific module:

```bash
./gradlew :swc-binding:test
```

### Publishing

This project uses the NMCP (New Maven Central Publisher) plugin for publishing to Maven Central.

#### Prerequisites

1. **Maven Central Account**: Create an account at [Maven Central Portal](https://central.sonatype.com/)
2. **Namespace Verification**: Verify your namespace (`dev.yidafu.swc`) in the portal
3. **GPG Key**: Set up a GPG key for signing artifacts

#### Configuration

Create a `local.properties` file in the project root:

```properties
# Maven Central Portal credentials
centralUsername=your-portal-username
centralPassword=your-portal-password

# GPG signing credentials
signing.key=your-gpg-private-key
signing.password=your-gpg-password
```

Or use environment variables:

```bash
export CENTRAL_USERNAME=your-portal-username
export CENTRAL_PASSWORD=your-portal-password
export SIGNING_KEY=your-gpg-private-key
export SIGNING_PASSWORD=your-gpg-password
```

#### Publishing to Maven Central

To publish to Maven Central Portal:

```bash
./gradlew :swc-binding:publishSonatypePublicationToCentralPortal
```

Or publish all publications:

```bash
./gradlew :swc-binding:publishAllPublicationsToCentralPortal
```

#### Publishing to Local Repository

For testing, you can publish to your local Maven repository:

```bash
./gradlew :swc-binding:publishToMavenLocal
```

#### Important Notes

- **Version Requirements**: Central Portal does NOT support SNAPSHOT versions. Only release versions are allowed
- **Publication Type**: Currently configured as `USER_MANAGED`, which requires manual approval in the Central Portal UI
- **Namespace**: Make sure your namespace (`dev.yidafu.swc`) is verified in the Central Portal before publishing

## AST DSL

```js
import x from './test.js';
class Foo {
    bar: string
}
```

The JS code above is equivalent to the Kotlin AST DSL below.

```kotlin
module {
    body = arrayOf(
        importDeclaration {
            specifiers = arrayOf(
                importDefaultSpecifier {
                    local = createIdentifier {
                        span = emptySpan()
                        value = "x"
                    }
                }
            )
            source = stringLiteral {
                value=  "./test.js"
                raw =  "./test.js"
                span = emptySpan()
            }
            typeOnly = false
            span = emptySpan()
        },

        classDeclaration {
            identifier = createIdentifier {  }
            span = emptySpan()
            body = arrayOf(
                classProperty {
                    span = emptySpan()
                    typeAnnotation = tsTypeAnnotation {
                        span = emptySpan()
                        typeAnnotation = tsKeywordType {
                            span = emptySpan()
                            kind = TsKeywordTypeKind.STRING
                        }
                    }
                }
            )
        }
    )
}
```

### Build AST segment

If you want to create an AST segment, call the `createXxx` function to create the segment.

```kotlin
createVariableDeclaration  {
    span = span(0, 17, 0)
    kind = 'const'
    declare = false
    declarations = arrayOf(
        variableDeclaratorImpl {
            span = span(6, 17, 0)
            id = identifier {
                span = span(5, 9, 0)
                value = "foo"
            }
            init = stringLiteral {
                span = span(12,17, 0)
                value = "bar"
                raw = "'bar'"
            }
        }
    )
}
```

### Boolean | T options

Some SWC options accept a union type `boolean | T`, for example:

```ts
export interface Config {
    sourceMaps?: boolean | "inline";
}
```

You can express this directly using `Union.U2<Boolean, T>`:

```kotlin
import dev.yidafu.swc.Union

options {
    sourceMaps = Union.U2<Boolean, String>(b = "inline")
    // or
    sourceMaps = Union.U2<Boolean, String>(a = false)
}
```

`Union.U2` can also be applied to properties like `configFile`, `isModule`, and `lazy`, providing a general way to support union types such as `boolean | Array<T>` and `boolean | MatchPattern[]`.

## Article

[How to implement SWC JVM binding -- English translation](docs/how-to-implement-swc-jvm-binding.md) -- [中文原文](docs/how-to-implement-swc-jvm-binding.zh-CN.md)

## Known Issues

### externalHelpers Configuration

When using `externalHelpers = false`, SWC should inline helper functions directly into the output code instead of importing them from `@swc/helpers`. However, the current Rust implementation of SWC used in this project does not respect this configuration correctly.

**Current Behavior:**
- With `externalHelpers = false`: SWC still generates imports from `@swc/helpers`
- With `externalHelpers = true`: SWC generates imports from `@swc/helpers`

**Expected Behavior:**
- With `externalHelpers = false`: SWC should inline helper functions (no imports from `@swc/helpers`)
- With `externalHelpers = true`: SWC should generate imports from `@swc/helpers`

This is a known difference between the Rust and Node.js versions of SWC. The configuration is correctly parsed and passed to SWC, but the Rust implementation does not inline helpers even when `external_helpers` is set to `false`.

**Workaround:**
For now, tests have been updated to match the actual Rust behavior rather than the expected behavior. This issue may be addressed in a future update when the Rust implementation is fixed or when a workaround is identified.

## License

MIT

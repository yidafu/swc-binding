# swc-binding

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/dev.yidafu.swc/swc-binding.svg)](https://search.maven.org/artifact/dev.yidafu.swc/swc-binding)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.21-blue.svg)](https://kotlinlang.org/)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-green.svg)](https://github.com/swc-project/swc)
[![Rust](https://img.shields.io/badge/rust-1.70+-orange.svg)](https://www.rust-lang.org/)
[![JVM](https://img.shields.io/badge/JVM-17+-red.svg)](https://www.oracle.com/java/)

[SWC](https://github.com/swc-project/swc) jvm binding by kotlin.


## Installtion

```kotlin
implementation("dev.yidafu.swc:swc-binding:0.7.0")
```

## Version Compatibility

| swc-binding | Rust SWC | @swc/types | Notes |
|-------------|----------|------------|-------|
| 0.7.0       | 43.0.0   | 0.1.25     | Latest stable release with full async support |
| 0.6.0       | 0.270.25   | 0.1.5     |  |

## Documentation

[SWC Binding - Kotlin Doc](https://yidafu.github.io/swc-binding/docs/)

## Usage

### transform code

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

### parse code

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

> passing parse react source code

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

## Ast dsl

```js
import x from './test.js';
class Foo {
    bar: string
}
```

Js code above, equal to kotlin ast dsl below.

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

### build ast segment

If you want create ast segment, call `createXxx` function to create segment.

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

### Booleanable

SWC configuration maybe `boolean | T`.

Such as

```ts
export interface Config {
    // ...
    sourceMaps?: boolean | "inline";
    // ...
}
```

So, you should using `Booleanable<T>`

```kotlin
options {
    // ...
    sourceMaps = BooleanableString.ofValue("inline")
    // or
    sourceMaps = BooleanableString.ofFalse()

}
```

Booleanable typealias

+ BooleanableString
+ BooleanableFloat
+ BooleanableInt
+ BooleanableArrayString
+ BooleanableTerserCompressOptions
+ BooleanableTerserMangleOptions
+ BooleanableArrayMatchPattern

## Article

[How to implement SWC JVM binding -- English translation](docs/how-to-implement-swc-jvm-binding.md) -- [中文原文](docs/how-to-implement-swc-jvm-binding.zh-CN.md)
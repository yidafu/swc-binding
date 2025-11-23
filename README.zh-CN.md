# swc-binding

[查看英文版本: README.md](README.md)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/dev.yidafu.swc/swc-binding.svg)](https://search.maven.org/artifact/dev.yidafu.swc/swc-binding)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.21-blue.svg)](https://kotlinlang.org/)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-green.svg)](https://github.com/swc-project/swc)
[![Rust](https://img.shields.io/badge/rust-1.70+-orange.svg)](https://www.rust-lang.org/)
[![JVM](https://img.shields.io/badge/JVM-17+-red.svg)](https://www.oracle.com/java/)

[SWC](https://github.com/swc-project/swc) 的 JVM 绑定，使用 Kotlin 实现。

## 安装

```kotlin
implementation("dev.yidafu.swc:swc-binding:0.8.0")
```

## 版本兼容性

| swc-binding | Rust SWC | @swc/types | 说明 |
|-------------|----------|------------|------|
| 0.8.0       | swc_core 48.0.4   | 0.1.25     | 最新稳定版，迁移至 swc_core |
| 0.7.0       | 43.0.0   | 0.1.25     | 之前的稳定版 |
| 0.6.0       | 0.270.25 | 0.1.5      | 旧版本，使用独立的 swc crates |

## 文档

[SWC Binding - Kotlin Doc](https://yidafu.github.io/swc-binding/docs/)

## 使用

### 代码转换

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

### 代码解析

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

### 异步方法（协程支持）

所有 SWC 方法均提供 Kotlin 协程的异步版本。异步方法在后台线程运行，不会阻塞调用线程。

#### 使用协程（推荐）

```kotlin
import kotlinx.coroutines.*

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

suspend fun parseMultiple() = coroutineScope {
    val swc = SwcNative()
    val options = ParserConfig(/* ... */)
    val results = listOf("code1.ts", "code2.ts", "code3.ts")
        .map { file -> async { swc.parseFileAsync(file, options) } }
        .awaitAll()
}
```

#### 使用回调

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

#### 可用的异步方法

- `parseAsync()` - 异步解析代码
- `parseFileAsync()` - 异步解析文件
- `transformAsync()` - 异步转换代码
- `transformFileAsync()` - 异步转换文件
- `printAsync()` - 异步打印 AST
- `minifyAsync()` - 异步压缩代码

#### 线程模型

- 异步方法立即返回，工作在后台线程执行
- 回调在 Rust 后台线程触发
- 建议使用协程以获得结构化并发
- 不阻塞调用线程

## API

### parseSync

[更多信息](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/parse-sync.html)

> 解析 React 源码

参考 [swc#parseSync](https://swc.rs/docs/usage/core#parsesync)

Kotlin DSL 封装方法

```kotlin
@Throws(RuntimeException::class)
fun parseSync(code: String, options: ParserConfig, filename: String?): Program 
```

Native 方法

```kotlin
@Throws(RuntimeException::class)
fun parseSync(code: String, options: String, filename: String?): String
```

### parseFileSync

[更多信息](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/parse-file-sync.html)

[swc#parseFileSync](https://swc.rs/docs/usage/core#parsefilesync)

Kotlin DSL 封装方法

```kotlin
@Throws(RuntimeException::class)
fun parseFileSync(filepath: String, options: ParserConfig): Program 
```

Native 方法

```kotlin
@Throws(RuntimeException::class)
fun parseFileSync(filepath: String, options: String): String
```

### transformSync

[更多信息](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/transform-sync.html)

[swc#transformSync](https://swc.rs/docs/usage/core#transformsync)

Kotlin DSL 封装方法

```kotlin
@Throws(RuntimeException::class)
fun transformSync(code: String, isModule: Boolean, options: Options): TransformOutput
```

Native 方法

```kotlin
@Throws(RuntimeException::class)
fun transformSync(code: String, isModule: Boolean, options: String): String
```

### transformFileSync

[更多信息](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/transform-file-sync.html)

[swc#transformFileSync](https://swc.rs/docs/usage/core#transformfilesync)

Kotlin DSL 封装方法

```kotlin
@Throws(RuntimeException::class)
fun transformFileSync(filepath: String, isModule: Boolean, options: Options): TransformOutput
```

Native 方法

```kotlin
@Throws(RuntimeException::class)
fun transformFileSync(filepath: String, isModule: Boolean, options: String): String
```

### printSync

[更多信息](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/print-sync.html)

[swc#printSync](https://swc.rs/docs/usage/core#printsync)

Kotlin DSL 封装方法

```kotlin
@Throws(RuntimeException::class)
fun printSync(program: Program, options: Options): TransformOutput
```

Native 方法

```kotlin
@Throws(RuntimeException::class)
fun printSync(program: String, options: String): String
```

### minifySync

[更多信息](https://yidafu.github.io/swc-binding/docs/swc-binding/dev.yidafu.swc/-swc-native/minify-sync.html)

[swc#minifySync](https://swc.rs/docs/usage/core#minifysync)

Kotlin DSL 封装方法

```kotlin
@Throws(RuntimeException::class)
fun minifySync(program: Program, options: Options): TransformOutput
```

Native 方法

```kotlin
@Throws(RuntimeException::class)
fun minifySync(program: String, options: String): String
```

## AST DSL

```js
import x from './test.js';
class Foo {
    bar: string
}
```

上面的 JS 代码等价于下面的 Kotlin AST DSL：

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

### 构建 AST 片段

如需仅创建 AST 片段，可调用 `createXxx` 系列函数：

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

### Boolean | T 配置

部分 SWC 配置项接受联合类型 `boolean | T`，例如：

```ts
export interface Config {
    sourceMaps?: boolean | "inline";
}
```

可直接使用 `Union.U2<Boolean, T>` 表达：

```kotlin
import dev.yidafu.swc.Union

options {
    sourceMaps = Union.U2<Boolean, String>(b = "inline")
    // 或者
    sourceMaps = Union.U2<Boolean, String>(a = false)
}
```

`Union.U2` 也适用于 `configFile`、`isModule`、`lazy` 等属性，统一支持如 `boolean | Array<T>`、`boolean | MatchPattern[]` 的联合类型。

## 文章

[如何实现 SWC JVM 绑定（英文译文）](docs/how-to-implement-swc-jvm-binding.md) — [中文原文](docs/how-to-implement-swc-jvm-binding.zh-CN.md)



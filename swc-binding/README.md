# swc-binding (module)

[Read this in Chinese: README.zh-CN.md](README.zh-CN.md)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](../LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/dev.yidafu.swc/swc-binding.svg)](https://search.maven.org/artifact/dev.yidafu.swc/swc-binding)
[![Kotlin](https://img.shields.io/badge/kotlin-2.2.10-blue.svg)](https://kotlinlang.org/)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-98D982?logo=swc&logoColor=white)](https://github.com/swc-project/swc)
[![Rust](https://img.shields.io/badge/rust-1.70+-CE412B?logo=rust&logoColor=white)](https://www.rust-lang.org/)
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

## SwcNative Usage Guide

`SwcNative` is the main entry point for all SWC operations. It provides both synchronous and asynchronous methods for parsing, transforming, printing, and minifying JavaScript/TypeScript code.

### Creating an Instance

```kotlin
val swc = SwcNative()
```

The `SwcNative` instance automatically loads the native library (`swc_jni`) when created. The library is loaded from the JAR resources if not found in the system library path.

### Synchronous Methods

All synchronous methods block the calling thread until the operation completes. Use them for simple scripts or when blocking is acceptable.

#### Parsing Code

Parse JavaScript/TypeScript code into an AST:

```kotlin
val swc = SwcNative()
val ast = swc.parseSync(
    code = "const x = 42;",
    options = esParseOptions { },
    filename = "test.js" // Optional
)
```

**TypeScript parsing:**

```kotlin
val ast = swc.parseSync(
    code = "const x: number = 42;",
    options = tsParseOptions {
        tsx = true
        decorators = true
    },
    filename = "test.ts"
)
```

**JSX parsing:**

```kotlin
val ast = swc.parseSync(
    code = """
        function App() {
            return <div>Hello</div>;
        }
    """.trimIndent(),
    options = esParseOptions {
        jsx = true
    },
    filename = "App.jsx"
)
```

#### Parsing Files

Parse a file directly:

```kotlin
val ast = swc.parseFileSync(
    filepath = "src/index.ts",
    options = tsParseOptions {
        tsx = true
    }
)
```

#### Transforming Code

Transform code (transpile, minify, apply plugins, etc.):

```kotlin
val output = swc.transformSync(
    code = "const arrow = () => 42;",
    isModule = false,
    options = options {
        jsc {
            target = JscTarget.ES5
            parser {
                syntax = Syntax.ECMASCRIPT
            }
        }
    }
)
println(output.code) // Transformed code
```

**Transforming TypeScript to JavaScript:**

```kotlin
val output = swc.transformSync(
    code = "const x: number = 42;",
    isModule = true,
    options = options {
        jsc {
            target = JscTarget.ES2020
            parser {
                syntax = Syntax.TYPESCRIPT
                tsx = true
            }
        }
    }
)
```

**Transforming files:**

```kotlin
val output = swc.transformFileSync(
    filepath = "src/index.ts",
    isModule = true,
    options = options {
        jsc {
            target = JscTarget.ES2020
            parser {
                syntax = Syntax.TYPESCRIPT
            }
        }
    }
)
```

#### Printing AST

Convert an AST back to code:

```kotlin
val ast = swc.parseSync("const x = 42;", esParseOptions { }, "test.js")
val output = swc.printSync(ast, options { })
println(output.code) // "const x = 42;"
```

#### Minifying Code

Minify JavaScript/TypeScript code:

```kotlin
val code = """
    function add(a, b) {
        return a + b;
    }
""".trimIndent()

val output = swc.minifySync(
    src = code,
    options = JsMinifyOptions()
)
println(output.code) // Minified code
```

### Asynchronous Methods

Asynchronous methods execute in background threads and don't block the calling thread. They are available in two forms: callback-based (Java-friendly) and coroutine-based (Kotlin-idiomatic).

#### Using Coroutines (Recommended for Kotlin)

All async methods have suspend function variants that work seamlessly with Kotlin coroutines:

```kotlin
import kotlinx.coroutines.*

suspend fun parseCode() {
    val swc = SwcNative()
    val ast = swc.parseAsync(
        code = "const x = 42;",
        options = esParseOptions { },
        filename = "test.js"
    )
    println("Parsed: ${ast.type}")
}

// Usage
runBlocking {
    parseCode()
}
```

**Parallel parsing:**

```kotlin
suspend fun parseMultipleFiles() = coroutineScope {
    val swc = SwcNative()
    val options = esParseOptions { }
    
    val results = listOf("file1.js", "file2.js", "file3.js")
        .map { file ->
            async { swc.parseFileAsync(file, options) }
        }
        .awaitAll()
    
    results.forEach { ast ->
        println("Parsed: ${ast.type}")
    }
}
```

**Async transform:**

```kotlin
suspend fun transformCode() {
    val swc = SwcNative()
    val output = swc.transformAsync(
        code = "const arrow = () => 42;",
        isModule = false,
        options = options {
            jsc {
                target = JscTarget.ES5
                parser {
                    syntax = Syntax.ECMASCRIPT
                }
            }
        }
    )
    println(output.code)
}
```

**Async minify:**

```kotlin
suspend fun minifyCode() {
    val swc = SwcNative()
    val output = swc.minifyAsync(
        src = "function add(a, b) { return a + b; }",
        options = JsMinifyOptions()
    )
    println(output.code)
}
```

#### Using Callbacks (Java-friendly)

Callback-based methods work in both Kotlin and Java:

```kotlin
val swc = SwcNative()
swc.parseAsync(
    code = "const x = 42;",
    options = esParseOptions { },
    filename = "test.js",
    onSuccess = { ast ->
        println("Success: ${ast.type}")
    },
    onError = { error ->
        println("Error: $error")
    }
)
```

**Java usage:**

```java
SwcNative swc = new SwcNative();
swc.parseAsync("const x = 42;", options, null,
    (Program ast) -> System.out.println("Success: " + ast.getType()),
    (String error) -> System.err.println("Error: " + error)
);
```

### Available Methods Summary

#### Synchronous Method List

- `parseSync(code, options, filename?)` - Parse code to AST
- `parseFileSync(filepath, options)` - Parse file to AST
- `transformSync(code, isModule, options)` - Transform code
- `transformFileSync(filepath, isModule, options)` - Transform file
- `printSync(program, options)` - Print AST to code
- `minifySync(src, options)` - Minify code

#### Asynchronous Method List (Coroutines)

- `parseAsync(code, options, filename?)` - Parse code to AST (suspend)
- `parseFileAsync(filepath, options)` - Parse file to AST (suspend)
- `transformAsync(code, isModule, options)` - Transform code (suspend)
- `transformFileAsync(filepath, isModule, options)` - Transform file (suspend)
- `printAsync(program, options)` - Print AST to code (suspend)
- `minifyAsync(src, options)` - Minify code (suspend)

#### Asynchronous Method List (Callbacks)

- `parseAsync(code, options, filename?, onSuccess, onError)` - Parse code with callbacks
- `parseFileAsync(filepath, options, onSuccess, onError)` - Parse file with callbacks
- `transformAsync(code, isModule, options, onSuccess, onError)` - Transform with callbacks
- `transformFileAsync(filepath, isModule, options, onSuccess, onError)` - Transform file with callbacks
- `printAsync(program, options, onSuccess, onError)` - Print AST with callbacks
- `minifyAsync(src, options, onSuccess, onError)` - Minify with callbacks

### Error Handling

All methods throw `RuntimeException` on failure. For async methods using coroutines, exceptions are propagated:

```kotlin
try {
    val ast = swc.parseSync("invalid syntax", esParseOptions { }, "test.js")
} catch (e: RuntimeException) {
    println("Parse failed: ${e.message}")
}
```

**With coroutines:**

```kotlin
try {
    val ast = swc.parseAsync("invalid syntax", esParseOptions { }, "test.js")
} catch (e: RuntimeException) {
    println("Parse failed: ${e.message}")
}
```

**With callbacks:**

```kotlin
swc.parseAsync(
    code = "invalid syntax",
    options = esParseOptions { },
    filename = "test.js",
    onSuccess = { /* ... */ },
    onError = { error ->
        println("Parse failed: $error")
    }
)
```

### Threading Model

- **Synchronous methods**: Execute on the calling thread and block until completion.
- **Asynchronous methods**: Execute in background threads managed by the Rust runtime. Callbacks are invoked from Rust background threads. Coroutines suspend the calling coroutine and resume when the operation completes.

### SwcNative Best Practices

- **Use async methods for production**: Async methods don't block threads and provide better performance for concurrent operations.

- **Prefer coroutines in Kotlin**: Use suspend functions for cleaner, more idiomatic Kotlin code.

- **Reuse SwcNative instances**: Creating a new `SwcNative()` instance is lightweight, but reusing instances is fine for multiple operations.

- **Handle errors appropriately**: Always wrap operations in try-catch blocks or handle errors in callbacks.

- **Use appropriate parser options**: Choose `esParseOptions` for JavaScript/JSX and `tsParseOptions` for TypeScript/TSX.

## Documentation

- API Docs: [https://yidafu.github.io/swc-binding/docs/](https://yidafu.github.io/swc-binding/docs/)
- Samples: `swc-binding/src/test`

## DSL Usage Guide

The swc-binding library provides a type-safe DSL (Domain-Specific Language) for building AST nodes and configuration objects. The DSL uses Kotlin's builder pattern to create SWC AST nodes in an idiomatic way.

### Importing DSL Functions

```kotlin
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
```

### Creating AST Nodes

#### Module

A `Module` represents an ES module with a body containing statements and declarations:

```kotlin
val mod = module {
    span = emptySpan()
    body = arrayOf(
        variableDeclaration {
            span = emptySpan()
            kind = VariableDeclarationKind.CONST
            declare = false
            declarations = arrayOf(
                variableDeclarator {
                    span = emptySpan()
                    id = identifier {
                        span = emptySpan()
                        value = "x"
                        optional = false
                    }
                    init = numericLiteral {
                        span = emptySpan()
                        value = 42.0
                        raw = "42"
                    }
                }
            )
        }
    )
}
```

#### Variable Declaration

```kotlin
variableDeclaration {
    span = emptySpan()
    kind = VariableDeclarationKind.CONST // or LET, VAR
    declare = false
    declarations = arrayOf(
        variableDeclarator {
            span = emptySpan()
            id = identifier {
                span = emptySpan()
                value = "myVar"
                optional = false
            }
            init = stringLiteral {
                span = emptySpan()
                value = "Hello"
                raw = "\"Hello\""
            }
        }
    )
}
```

#### Function Declaration

```kotlin
functionDeclaration {
    span = emptySpan()
    identifier = identifier {
        span = emptySpan()
        value = "add"
        optional = false
    }
    declare = false
    async = false
    generator = false
    params = arrayOf(
        param {
            span = emptySpan()
            pat = identifier {
                span = emptySpan()
                value = "a"
                optional = false
            }
        },
        param {
            span = emptySpan()
            pat = identifier {
                span = emptySpan()
                value = "b"
                optional = false
            }
        }
    )
    body = blockStatement {
        span = emptySpan()
        stmts = arrayOf(
            returnStatement {
                span = emptySpan()
                arg = binaryExpression {
                    span = emptySpan()
                    operator = BinaryOperator.PLUS
                    left = identifier {
                        span = emptySpan()
                        value = "a"
                        optional = false
                    }
                    right = identifier {
                        span = emptySpan()
                        value = "b"
                        optional = false
                    }
                }
            }
        )
    }
}
```

#### Class Declaration

```kotlin
classDeclaration {
    span = emptySpan()
    identifier = identifier {
        span = emptySpan()
        value = "MyClass"
        optional = false
    }
    declare = false
    abstract = false
    body = arrayOf(
        classProperty {
            span = emptySpan()
            key = identifier {
                span = emptySpan()
                value = "name"
                optional = false
            }
            value = stringLiteral {
                span = emptySpan()
                value = "Example"
                raw = "\"Example\""
            }
            typeAnnotation = tsTypeAnnotation {
                span = emptySpan()
                typeAnnotation = tsKeywordType {
                    span = emptySpan()
                    kind = TsKeywordTypeKind.STRING
                }
            }
        }
    )
    superClass = null
    superTypeParams = null
    implements = arrayOf()
    decorators = arrayOf()
}
```

#### Import Declaration

```kotlin
importDeclaration {
    span = emptySpan()
    specifiers = arrayOf(
        importDefaultSpecifier {
            local = identifier {
                span = emptySpan()
                value = "React"
                optional = false
            }
        }
    )
    source = stringLiteral {
        span = emptySpan()
        value = "react"
        raw = "\"react\""
    }
    typeOnly = false
}
```

### Creating Configuration Objects

#### Parser Options

**TypeScript Parser Options:**

```kotlin
val tsOptions = tsParseOptions {
    tsx = true
    decorators = true
    dynamicImport = true
    target = JscTarget.ES2020
}
```

**ECMAScript Parser Options:**

```kotlin
val esOptions = esParseOptions {
    jsx = true
    allowReturnOutsideFunction = true
    allowAwaitOutsideFunction = false
    allowSuperOutsideMethod = false
    allowImportExportEverywhere = false
    allowUndeclaredExports = false
}
```

#### Transform Options

```kotlin
val transformOptions = options {
    jsc {
        target = JscTarget.ES2020
        parser {
            syntax = Syntax.TYPESCRIPT
            tsx = true
            decorators = true
        }
        transform {
            react {
                runtime = ReactConfigRuntime.CLASSIC
                pragma = "React.createElement"
                pragmaFrag = "React.Fragment"
            }
        }
        minify {
            compress = JsMinifyCompressOptions()
            mangle = JsMinifyMangleOptions()
        }
    }
    module {
        type = ModuleConfigType.ES6
    }
    minify = false
    isModule = true
}
```

### Common Patterns

#### Creating Literals

**String Literal:**

```kotlin
stringLiteral {
    span = emptySpan()
    value = "Hello, World!"
    raw = "\"Hello, World!\""
}
```

**Numeric Literal:**

```kotlin
numericLiteral {
    span = emptySpan()
    value = 42.0
    raw = "42"
}
```

**Boolean Literal:**

```kotlin
booleanLiteral {
    span = emptySpan()
    value = true
}
```

#### Creating Expressions

**Binary Expression:**

```kotlin
binaryExpression {
    span = emptySpan()
    operator = BinaryOperator.PLUS // or MINUS, MULTIPLY, etc.
    left = identifier { value = "a"; span = emptySpan(); optional = false }
    right = identifier { value = "b"; span = emptySpan(); optional = false }
}
```

**Call Expression:**

```kotlin
callExpression {
    span = emptySpan()
    callee = identifier {
        span = emptySpan()
        value = "console"
        optional = false
    }
    arguments = arrayOf(
        argument {
            expr = stringLiteral {
                span = emptySpan()
                value = "Hello"
                raw = "\"Hello\""
            }
        }
    )
}
```

**Member Expression:**

```kotlin
memberExpression {
    span = emptySpan()
    obj = identifier {
        span = emptySpan()
        value = "console"
        optional = false
    }
    prop = identifier {
        span = emptySpan()
        value = "log"
        optional = false
    }
    computed = false
}
```

### Important Notes

#### Span Fields

All AST nodes require a `span` field. Use `emptySpan()` to create a default span:

```kotlin
span = emptySpan() // Creates a span with start=0, end=0, ctxt=0
```

#### Boolean Fields

When manually constructing AST nodes, you **must** explicitly set all boolean fields to avoid serialization errors. The Rust backend expects non-null boolean values.

**Common boolean fields that must be set:**

- `Identifier.optional: Boolean?` → set to `false`
- `VariableDeclaration.declare: Boolean?` → set to `false`
- `FunctionDeclaration.async: Boolean?` → set to `false`
- `FunctionDeclaration.generator: Boolean?` → set to `false`
- `ArrowFunctionExpression.async: Boolean?` → set to `false`
- `ArrowFunctionExpression.generator: Boolean?` → set to `false`
- `TemplateElement.tail: Boolean?` → set to `false` or `true` (depending on position)

#### Best Practices

- **Prefer parsing over manual construction**: Use `parseSync` to parse code into AST instead of manually constructing it, as parsed ASTs have all fields properly initialized.

```kotlin
// ✅ Recommended: Parse code instead of manual construction
val mod = swcNative.parseSync("const x = 42;", esParseOptions { }, "test.js") as Module
val output = swcNative.printSync(mod, options { })
```

- **Use DSL for modifications**: The DSL is most useful when you need to modify or create specific AST nodes programmatically.

- **Always set span fields**: Every AST node requires a `span` field. Use `emptySpan()` for default values.

- **Set boolean fields explicitly**: Always set boolean fields to avoid serialization errors.

For more examples, see the test files in `swc-binding/src/test`.

## License

MIT

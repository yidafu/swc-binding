# swc-binding

[SWC](https://github.com/swc-project/swc) jvm binding by kotlin.

## Installtion

```kts
implementation("dev.yidafu.swc:swc-binding:0.4.2")
```

## Usage

### transform code

```kt
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

## API

### parseSync

see [swc#parseSync](https://swc.rs/docs/usage/core#parsesync)

```kotlin
@Throws(RuntimeException::class)
fun parseSync(code: String, options: ParserConfig, filename: String?): Program 
```

Native method

```kotlin
@Throws(RuntimeException::class)
fun parseSync(code: String, options: String, filename: String?): String
```

Kotlin DSL Wrapper method

### parseFileSync

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

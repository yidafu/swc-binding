# swc-binding（模块）

[查看英文版本: README.md](README.md)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](../LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/dev.yidafu.swc/swc-binding.svg)](https://search.maven.org/artifact/dev.yidafu.swc/swc-binding)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.21-blue.svg)](https://kotlinlang.org/)
[![SWC](https://img.shields.io/badge/SWC-43.0.0-green.svg)](https://github.com/swc-project/swc)
[![JVM](https://img.shields.io/badge/JVM-17+-red.svg)](https://www.oracle.com/java/)

提供通过 JNI 访问 SWC 的 Kotlin/JVM 库。该模块即对外发布的依赖产物。

## 安装

```kotlin
implementation("dev.yidafu.swc:swc-binding:0.7.0")
```

## 快速上手

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

## 文档

- API 文档：https://yidafu.github.io/swc-binding/docs/
- 示例：`swc-binding/src/test`

## 重要提示

### 手动构建 AST

使用 DSL 手动构建 AST 节点时，**必须**显式设置所有布尔字段，以避免序列化错误。Rust 后端期望非空的布尔值，但如果 Kotlin 的可空布尔字段（`Boolean?`）未设置，它们会被序列化为 `null`。

**示例 - 正确：**
```kotlin
val mod = module {
    span = emptySpan()
    body = arrayOf(
        variableDeclaration {
            span = emptySpan()
            kind = VariableDeclarationKind.CONST
            declare = false  // ✅ 显式设置
            declarations = arrayOf(
                variableDeclarator {
                    id = identifier {
                        value = "x"
                        optional = false  // ✅ 显式设置
                    }
                    init = numericLiteral { ... }
                }
            )
        }
    )
}
```

**示例 - 错误：**
```kotlin
val mod = module {
    body = arrayOf(
        variableDeclaration {
            kind = VariableDeclarationKind.CONST
            // ❌ 缺少 'declare = false' - 会导致序列化错误
            declarations = arrayOf(...)
        }
    )
}
```

**必须设置的常见布尔字段：**
- `Identifier.optional: Boolean?` → 设置为 `false`
- `VariableDeclaration.declare: Boolean?` → 设置为 `false`
- `ArrowFunctionExpression.async: Boolean?` → 设置为 `false`
- `ArrowFunctionExpression.generator: Boolean?` → 设置为 `false`
- `TemplateElement.tail: Boolean?` → 根据位置设置为 `false` 或 `true`

**推荐做法：** 使用 `parseSync` 解析代码生成 AST，而不是手动构建，因为解析得到的 AST 所有字段都已正确初始化。

```kotlin
// ✅ 推荐：解析代码而不是手动构建
val mod = swcNative.parseSync("const x = 42;", esParseOptions { }, "test.js") as Module
val output = swcNative.printSync(mod, options { })
```

## 许可协议

MIT



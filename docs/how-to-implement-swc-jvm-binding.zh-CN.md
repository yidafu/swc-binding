# 如何实现 SWC JVM binding

## 背景

在使用 Kotlin Jupiter Kennel 的过程中发现没有3D绘制库，只能使用 JS 来绘制数据。只能通过`HTML(...)`函数来写 JS，非常不方便。所以，我写了 [kotlin-jupyter-js](https://github.com/yidafu/kotlin-jupyter-js) 插件来支持`%js` line magics。`kotlin-jupyter-js`插件的核心问题是：在 JVM 支持编译 JS 代码成 AST。为此需要一个工具将 JS 代码转换成 AST，最好还能支持 TS 和 JSX。

我的想法是实现 SWC 的 JVM binding 来解决这个问题。SWC 本身提供 Node 的 binding，所以 JVM binding 实现难度没有那么大。而且，SWC 支持 TS/JSX 编译，可以让`kotlin-jupyter-js`支持`typescript`和`React`。

## 实现思路

SWC JVM binding 实现了分成两部分。1. 将 SWC 的 Rust 代码编译成 JNI 动态库；2. JVM 侧，实现配置类和 AST 类。

SWC 是给 JS 使用的，只提供了支持 Node binding。我们需要参考 Node binding，来实现 JVM 的 binding。

SWC Node binding 暴露的 API 出参、入参都是 JSON 字符串，在 Node 里 JSON 字符串很容易转为对象，在 JVM 里则需要相应的类声明。

> SWC 提供了 WASM binding，可以基于 WASM 来封装 SWC，好处是不需要实现 JNI binding，但是需要额外引入 WASM Runtime。故没有考虑。

## SWC binding

### Rust JNI FFI

将 Rust 编译成 JNI 动态库，需要 Rust 的 JNI FFI。直接使用 [jni](https://crates.io/crates/jni) 即可支持。

这个库提供可以很方便地桥接 Rust 和 Java。可以看一下 `jni` 的官方例子。

在 JVM 侧代码。

```kotlin
class HelloWorld {
    init {
        System.loadLibrary("mylib");
    }

    external fun hello(input: String): String;
}
```

在 Rust 代码只需要写一下胶水代码即可。

```rust
#[no_mangle]
pub extern "system" fn Java_HelloWorld_hello<'local>(mut env: JNIEnv<'local>, class: JClass<'local>, input: JString<'local>) -> jstring {
    let input: String =
        env.get_string(&input).expect("Couldn't get java string!").into();

    // your business logic
    let output = env.new_string(format!("Hello, {}!", input))
        .expect("Couldn't create java string!");

    output.into_raw()
}
```

调用`HelloWorld().hello("JNI")`，通过 JNI 会调用Rust 代码返回`Hello, JNI!`.

上面 Rust 代码里桥接函数的申明比较长，可以使用 [jni_fn](https://crates.io/crates/jni_fn) 通过宏自动生成桥接函数声明，简化声明。

```rust
#[jni_fn("HelloWorld")]
pub fn hello<'local>(...) -> jstring
```

通过 `jni` 和 `jni_fn` 我们可以将 Rust 代码编译成 JNI 动态库。

### Binding

SWC Node binding 提供了以下方法

+ transform
  + transform
  + transformSync
  + transformFile
  + transformFileSync
+ parse
  + parse
  + parseSync
  + parseFile
  + parseFileSync
+ minify
  + minify
  + minifySync
+ print
  + print
  + printSync

SWC Node binding 通过 [napi](https://crates.io/crates/napi) 提供同步和异步方法。但是 JVM 的 FFI `jni` 并不只支持异步，所以我们只实现同步 API：`transformSync`,`transformFileSync`,`parseSync`,`parseFileSync`,`minifySync`,`printSync`。

### pase_sync

下面以`pase_sync`为例，解释如何实现。

#### 依赖

SWC 本身只考虑了 Node binding。[swc_core](https://crates.io/crates/swc_core) 实现了与 Node 绑定的逻辑、聚合其他 SWC 子包依赖。NMP 包`@swc/core`也是封装`swc_core`。我们不能直接使用`swc_core`库，需要替换其他 SWC 子包调用。

比如，从`swc_core`引入`Compiler`：

```rs
use swc_core::{
    base::{
        Compiler,
    },
}
```

需要改为从 [swc](https://crates.io/crates/swc) 引入。

```rust
use swc::Compiler;
```

`swc_core`转换后的所有 SWC 相关依赖:

```toml
[dependencies]
# ...
swc = "0.270.25"
swc_common = "0.33.9"
swc_ecma_ast = { version ="0.110.10", features = ["serde-impl"] }
swc_ecma_transforms = "0.227.19"
swc_ecma_transforms_base = "0.135.11"
swc_ecma_visit = "0.96.10"
swc_ecma_codegen = "0.146.39"
# ...
```

#### 出入参

理论上，需要做的工作很简单：将所有 `napi` 相关逻辑替换成`jni`即可。如何 SWC 如何实现具体功能，我们都不需要改动。

参考 [SWC - binding_core_node](https://github.com/swc-project/swc/tree/main/bindings/binding_core_node) 的 `pase_sync` 实现 [binding_core_node/src/parse.rs#L168](https://github.com/swc-project/swc/blob/828190c035d61e6521280e2260c511bc02b81327/bindings/binding_core_node/src/parse.rs#L168), `parseSync` 大部分逻辑都直接复制，但需要修改入参、出参的处理。

`binding_core_node` 的 `pase_sync` 实现：

```rust
#[napi]
pub fn parse_sync(src: String, opts: Buffer, filename: Option<String>) -> napi::Result<String> {
    // ...


    Ok(serde_json::to_string(&program)?)
}
```

需要修改签名和出入参处理：

```rust
#[jni_fn("dev.yidafu.swc.SwcNative")]
pub fn parseSync(mut env: JNIEnv, _: JClass, code: JString, options: JString, filename: JString) -> jstring {
    // process parameter
    let src: String = env
        .get_string(&code)
        .expect("Couldn't get java string!")
        .into();
    let opts: String = env
        .get_string(&options)
        .expect("Couldn't get java string!")
        .into();
    let filename: String = env
        .get_string(&filename)
        .expect("Couldn't get java string!")
        .into();

    // ...

    // process return value
    let output = env
        .new_string(ast_json)
        .expect("Couldn't create java string!");
    
    output.into_raw()
}
```

获取 JVM 传过来的字符串，需要调用`JNIEnv`的`get_string`。

将 Rust 字符串转为Java字符串也需要调用 `JNIEnv`的`new_string`在转为`jstring`类型。

#### 异常处理

如果 SWC 处理 JS 代码失败了（比如JS代码有语法错误），需要抛出异常到 JVM，由 JVM 侧进行处理。

首先捕获 Rust 抛出的代码，再转换成 JVM 的异常抛出。

`binding_core_node` 处理时对于`Result`实现了`MapErr<T>` trait,通过`convert_err` 方法将 Rust 异常转为了`napi`的异常，最后在 Node 里抛出。

SWC 的异常处理 [swc/bindings/binding_core_node/src/parse.rs#L179](https://github.com/swc-project/swc/blob/828190c035d61e6521280e2260c511bc02b81327/bindings/binding_core_node/src/parse.rs#L179)

```rust
let program = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
    // ....
}).convert_err()?;
```

我们需要抛出 JVM 的异常，所以要实现 JVM 的 `MapErr<T>` trait，将Rust异常转为 `jni`的异常，让`jni`抛出到 JVM。

抄一下 SWC 的 `MapErr<T>` trait。

```rust
pub trait MapErr<T>: Into<Result<T, anyhow::Error>> {
    fn convert_err(self) -> SwcResult<T> {
        self.into().map_err(|err| SwcException::SwcAnyException {
            msg: format!("{:?}", err),
        })
    }
}
```

`Result`实现`MapErr<T>`

```rust
impl<T> MapErr<T> for Result<T, anyhow::Error> {}
```

这里 `jni` 抛出异常需要注意，函数依然需要返回值，一般返回空串。这里 [jni-rs#76](https://github.com/jni-rs/jni-rs/issues/76) 解释了原因。

> You still have to return to the JVM, even if you've thrown an exception. Remember that unwinding across the ffi boundary is always undefined behavior, so any panics need to be caught and recovered from in your extern functions.

最后异常处理像这样

```rust
let result = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
    // ...
}).convert_err();

match result {
    Ok(program) => {
        // ...
    }
    Err(e) => {
        match e {
            SwcException::SwcAnyException { msg } => {
                env.throw(msg).unwrap();
            }
        }
        return JString::default().into_raw();
    }
}
```

### SwcNative

实现Rust编译成动态库，下一步就需要实现 JVM 侧胶水代码，下面是 Kotlin 实现。

```kotlin
class SwcNative {
    init {
        System.loadLibrary("swc_jni")
    }

    @Throws(RuntimeException::class)
    external fun parseSync(code: String, options: String, filename: String?): String
}
```

JVM 加载`swc_jni`时，会按照规则从文件系统寻找动态库，但是不会从 jar 的 `resources`目录寻找。所以，通过` System.loadLibrary("swc_jni")`如果本地没有`swc_jni`动态库，就会加载失败。用户从 maven 安装，本地肯定没有`swc_jni`。

解决方案，参考这个回答 [Load Native Library from Class path](https://stackoverflow.com/questions/23189776/load-native-library-from-class-path)，如果`System.loadLibrary("swc_jni")` 加载失败就将 jar 的动态库复制到临时目录再加载。

```kotlin
    init {
        try {
            System.loadLibrary("swc_jni")
        } catch (e: UnsatisfiedLinkError) {
            // 加载失败，复制DLL到临时目录
            val dllPath = DllLoader.copyDll2Temp("swc_jni")
            // 再次加载
            System.load(dllPath)
        }
    }
```

### 小结

像其他方法就像`parse_sync`依葫芦画瓢实现就可以了。

到这一步我们已经可以在 JVM 里的编译 JS 了。

```kotlin
SwcNative().parseSync(
    "var foo = 'bar'", 
    """{"syntax": "ecmascript";}""",
    "test.js",
)

```

<details>
<summary>output string</summary>

```json
{
  "type": "Module",
  "span": {
    "start": 0,
    "end": 15,
    "ctxt": 0
  },
  "body": [
    {
      "type": "VariableDeclaration",
      "span": {
        "start": 0,
        "end": 15,
        "ctxt": 0
      },
      "kind": "var",
      "declare": false,
      "declarations": [
        {
          "type": "VariableDeclarator",
          "span": {
            "start": 4,
            "end": 15,
            "ctxt": 0
          },
          "id": {
            "type": "Identifier",
            "span": {
              "start": 4,
              "end": 7,
              "ctxt": 2
            },
            "value": "foo",
            "optional": false,
            "typeAnnotation": null
          },
          "init": {
            "type": "StringLiteral",
            "span": {
              "start": 10,
              "end": 15,
              "ctxt": 0
            },
            "value": "bar",
            "raw": "'bar'"
          },
          "definite": false
        }
      ]
    }
  ],
  "interpreter": null
}

```

</code>
</details>

## Kotlin AST DSL

现在我们得到 AST JSON 字符串，如果想要对AST进行操作还是很不方便的。我们需要 JSON 字符串将其转换为类，这样遍历、修改都会非常方便。

而且 `parseSync` 的第二个`options`也不知道类型，需要约束配置项。

那我们如何在 Kotlin 实现 SWC AST 和配置项参数的类型描述呢？

我尝试过是 AI 将 Rust 转换为 Kotlin，效果相当不错。唯一的问题的就是需要氪金，我承认没钱是我的问题。

从头写 SWC 类定义？工作做恐怕有的大了。SWC 有 200+ 的 AST 和配置项类型。

最好的解决方式就是通过脚本来生成 Kotlin 类。恰好，SWC 提供了 TS 声明文件 [@swc/types](https://www.npmjs.com/package/@swc/types)。

### @swc/types

打开 `@swc/types` 的声明文件，里面都是 `type` 和 `interface` 声明，结构非常简单。

可以分为一下情况:

1. type alias
   1. literal union type: `type T = 'foo' | 'bar'`
   2. primary union type: `type T = string | number`
   3. type alias and object literal type: `type T = S & { foo: string }`
   4. type alias union type: `type T = S | E`
2. interface

Type alias 的情况相对复杂，主要还是因为 JS 的灵活性。

### type alias

对于一些特殊情况我们需要减少类型的动态性，方便我们进行处理。

像 `T | T[]` 我们可以转为 `T[]`，避免在 Kotlin 里无法定义类型。

比如：

```ts
export interface Config {
    test?: string | string[];
    // ...
}
```

就转换为：

```kotlin
class Config {
    var test: Array<String>? = null
}
```

像 `props: 'foo' | 'bar'` 这样的字面量联合类型应该直接转为基础类型： `val props: String?`。

`type T = S & { foo: string }` 需要将对象字面量类型提取单独的类型，T 来继承 S 和 提取出来的新类型。转换成 kotlin应该是这样的：

```kotlin
interface BaseT {
    val foo: String;
}

class T : S, BaseT {}
```

### interface

对于 `interface` 处理，分为3部分：1. TS interface 转为 Kotlin 类；2. 继承关系；3. 序列化。

#### TS interface 转为 Kotlin 类

定义个 `KotlinClass` 来表示要装换成的 Kotlin 类。这样实现`toString()`即可方便地将其转为 kotlin 类。

```ts
export class KotlinClass {
    klassName: string = '';
    headerComment: string = ''
    annotations: string[] = []
    modifier: string = ''
    parents: string[] = []
    properties: KotlinClassProperty[] = []
}
```

通过遍历 TS interface 的 AST，就可以生成 `KotlinClass`。

在遍历 interface 属性时，需要递归遍历父类的属性，继承自父类型的属性需要将`KotlinClassProperty.isOverride`设为 true，方便生成 kotlin 类是加上`override`修饰符。

```ts
class KotlinClassProperty {
    modifier: string = 'var'
    name: string = ''
    type: string = ''
    comment: string = ''
    defaultValue: string = ''
    isOverride: boolean = false
    discriminator: string = 'type'
}
```

#### 继承关系

TS interface 直接继承的父 interface 直接加入 `KotlinClass.parents` 数组即可。

但是，对于 `type T = S | E` 需要进行单独处理

举个例子

```ts
export interface VariableDeclarator extends Node, HasSpan {
    init?: Expression;

    // other props...
}

export type Expression =
    | ThisExpression
    | ArrayExpression
    | ....

export interface ArrayExpression extends ExpressionBase  {
    // ...
}
```

这里 Expression 是所有 `XxxExpression` 的父类型。这样`variableDeclarator.init = thisExpression` 或者 `variableDeclarator.init = arrayExpression` 赋值才合法。

因为 TS 里 `Expression` 是 type alias 转换 kotlin 要变成一个空接口。 转换成 Kotlin 结果像这样

```kotlin
interface Expression {}

class VariableDeclarator : Node, HasSpan {
    val init: Expression?;

    // other props...
}
class ArrayExpression : ExpressionBase, Expression {
    // ...
}
```

所以，对于 `type T = S | E`，`T` 是 `S` 和 `E`的父类，需要将 `T` 加入`S`,`E` 的 `KotlinClass.parents` 数组。

#### 序列化

AST 节点序列化时，会遇到多态序列化的问题。

比如，序列化`Expression`，而`Expression`是空接口，这时`toJson`就不知道如何处理`ThisExpression`和`ArrayExpression`的属性，这时只能抛出异常或者输出空对象，都不符合我们的期望。

```kotlin
val thisExpression: ThisExpression = ThisExpression()
val arrayExpression: ArrayExpression = ArrayExpression()

var expression: Expression = thisExpression
toJson(expression)

expression = arrayExpression
toJson(expression)
```

反序列化也是一样的。`parseJson` 也不知道将字符串转为`ThisExpression`还是`ArrayExpression`。

```kotlin
val thisExpression = """ {"type":"ThisExpression", "props": "any value" } """
val arrayExpression = """ {"type":"ThisExpression", "elements": [] } """

var expression: Expression = parseJson(thisExpression)
var expression: Expression = parseJson(arrayExpression)
```

使用 kotlinx serialization 来序列化，它支持[多态序列化](https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md)，需要将改造kotlin代码。

在类上注解`JsonClassDiscriminator`标明通过哪个字段来区分类型，`SerialName`注解标明序列后类型的名称。反序列化时可以根据这个类型名称找到具体类型。

```kotlin
interface ArrayExpression : ExpressionBase, Expression {
    // ....
}

@Serializable
@JsonClassDiscriminator("type")
@SerialName("ArrayExpression")
class ArrayExpressionImpl : ArrayExpression {
    // ...
}

interface ThisExpression : ExpressionBase, Expression {
    // ....
}

@Serializable
@JsonClassDiscriminator("type")
@SerialName("ThisExpression")
class ThisExpressionImpl : ThisExpression {
    // ....
}

```

为了序列化和反序列化是能够正确找到具体类型，还需要定义`SerializersModule`。

```kotlin
val swcSerializersModule = SerializersModule {
    // ...
    polymorphic(Expression::class) {
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        // ...
    }

    polymorphic(ThisExpression::class) {
        subclass(ThisExpressionImpl::class)
    }

    polymorphic(ArrayExpression::class) {
        subclass(ArrayExpressionImpl::class)
    }
    // ...
}
```

这样就可以正常序列化多态类型了

```kotlin
val json = Json {
    classDiscriminator = "syntax"
    serializersModule = configSerializer
}

json.decodeFromString<Expression>(""" {"type":"ThisExpression", "elements": [] } """)

val arrayExpression: Expression = ArrayExpression()
json.encodeToString<Expression>(arrayExpression)
```

### DSL

我们生成好了 AST 和配置项的类定义，如果直接使用类来构建配置或者 AST 会发现不太优雅和方便。


```js
const foo = 'bar'
```

<details>
<summary>SWC compile output string</summary>

```json
    {
      "type": "VariableDeclaration",
      "span": {
        "start": 0,
        "end": 17,
        "ctxt": 0
      },
      "kind": "const",
      "declare": false,
      "declarations": [
        {
          "type": "VariableDeclarator",
          "span": {
            "start": 6,
            "end": 17,
            "ctxt": 0
          },
          "id": {
            "type": "Identifier",
            "span": {
              "start": 6,
              "end": 9,
              "ctxt": 2
            },
            "value": "foo",
            "optional": false,
            "typeAnnotation": null
          },
          "init": {
            "type": "StringLiteral",
            "span": {
              "start": 12,
              "end": 17,
              "ctxt": 0
            },
            "value": "bar",
            "raw": "'bar'"
          },
          "definite": false
        }
      ]
    }
```
</details>

上面 JS 代码，如果我们 Kotlin 来构建 AST

```kotlin
VariableDeclarationImpl().apply {
    span = Span(0, 17, 0)
    kind = 'const'
    declare = false
    declarations = arrayOf(
        VariableDeclaratorImpl().apply {
            span = Span(6, 17, 0)
            id = IdentifierImpl().apply {
                span = span(5, 9, 0)
                value = "foo"
            }
            init = StringLiteralImpl().apply {
                span = Span(12,17, 0)
                value = "bar"
                raw = "'bar'"
            }
        }
    )
}
```

通过`apply`来调用简化的属性设置。相对面条式代码，通过`apply`已经比较简洁。还能简洁一点。

```kotlin
variableDeclaration  {
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

现在的 DSL 已经很像输出 AST JSON，写起来也非常简单直白。

需要DSL写法的类，都需要`SwcDslMarker`注解标记。`SwcDslMarker`主要是为了限制作用域，避免访问外层作用域。

```kotlin
@DslMarker
annotation class SwcDslMarker

@SwcDslMarker
class VariableDeclarationImpl {
    // ...
}

fun variableDeclaration(block: VariableDeclaration.() -> Unit): VariableDeclaration {
    return VariableDeclarationImpl().apply(block)
}
```

如何实现可以参考官方文档：[kotlin -- Type-safe builders](https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker)

#### DSL extension function

```kotlin
interface VariableDeclarator : Node, HasSpan {
    val init: Expression?;
    // other props...
}
```

对于`VariableDeclarator`接口，其init字段类型是`Expression`，意味着它的右值可以是`arrayExpression`、`thisExpression`等任意子类型。

```kotlin
variableDeclarator {
    init = arrayExpression { ... }
    // or
    init = thisExpression { ... }
}
```

所以对于`VariableDeclarator`它应该有创建所有 `Expression` 子类的方法。通过扩展函数来实现来添加创建`Expression` 子类。

我们在解析`@swc/types`声明文件时，需要检查属性类型，如果是转换成Kotlin后是类，那就找出其所有非中间子类，然后为其生成扩展函数。

```kotlin
fun VariableDeclarator.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}
```

这样在 `variableDeclarator {}` 里就可以通过 `arrayExpression {}` 函数构建`Expression`类。

#### `TemplateLiteral` vs `TsTemplateLiteralType`

这里还有个特殊情况需要处理。`TemplateLiteral`跟`TsTemplateLiteralType`冲突了，他们的`type`都是`"TemplateLiteral"`。这使得 DSL 构建的 AST 无法序列化。参见 rust 的结构体定义。

```rust
// https://github.com/swc-project/swc/blob/828190c035d61e6521280e2260c511bc02b81327/crates/swc_ecma_ast/src/typescript.rs#L823
#[ast_node("TemplateLiteral")]
#[derive(Eq, Hash, EqIgnoreSpan)]
#[cfg_attr(feature = "arbitrary", derive(arbitrary::Arbitrary))]
pub struct TsTplLitType {
    // ...
}
```

```rust
// https://github.com/swc-project/swc/blob/828190c035d61e6521280e2260c511bc02b81327/crates/swc_ecma_ast/src/expr.rs#L1060
#[ast_node("TemplateLiteral")]
#[derive(Eq, Hash, EqIgnoreSpan)]
#[cfg_attr(feature = "arbitrary", derive(arbitrary::Arbitrary))]
pub struct Tpl {
    pub span: Span,

    #[cfg_attr(feature = "serde-impl", serde(rename = "expressions"))]
    pub exprs: Vec<Box<Expr>>,

    pub quasis: Vec<TplElement>,
}
```

这两个类型需要单独处理，不由脚本来生成。

由一个类同时实现`TemplateLiteral`、`TsTemplateLiteralType`。使用时再向上转型为`TemplateLiteral`、`TsTemplateLiteralType`。

```kotlin
// ignore annotation
interface TemplateLiteral : ExpressionBase, Expression {
    var expressions: Array<Expression>?
    var quasis: Array<TemplateElement>?
    override var span: Span?
}

interface TsTemplateLiteralType : Node, HasSpan, TsLiteral {
    var types: Array<TsType>?
    var quasis: Array<TemplateElement>?
    override var span: Span?
}

class TemplateLiteralImpl : TemplateLiteral, TsTemplateLiteralType {
    override var types: Array<TsType>? = null
    override var expressions: Array<Expression>? = null
    override var quasis: Array<TemplateElement>? = null
    override var span: Span? = null
}

typealias TsTemplateLiteralTypeImpl = TemplateLiteralImpl
```

### 新的 `parseSync`

现在我们可以升级`parseSync`签名了。

```kotlin
@Throws(RuntimeException::class)
fun parseSync(code: String, options: ParserConfig, filename: String?): Program 
```

现在使用时可以保证类型安全和类型提示了。

```kotlin
const program = SwcNative().parseSync(
    """
    function App() {
       return <div>App</div>
    }
    """.trimIndent(),
    esParseOptions {
        jsx = true
        target = "es5"
    },
    "temp.js"
)
if (program is Module) {
    if (program.body?.get(0) is FunctionDeclaration) {
        // ...
    }
}
```

## 总结

到这里，解释了 SWC JVM binding 的实现思路和核心实现要点。1. SWC 支持 JNI；2. AST JSON 序列化成 Kotlin 类；3. 通过 DSL 描述 AST和配置项。

一些细碎的内容没有涉及到，比如，Kotlin 生成脚本一些边界情况的处理、Rust交叉编译等。对细节感兴趣可以阅读源码[yidafu/swc-binding](https://github.com/yidafu/swc-binding)。

如果你有需求在 JVM 编译 JS，SWC JVM binding 已发布到 Maven 中央仓库，请使用 [dev.yidafu.swc:swc-binding:0.5.0](https://mvnrepository.com/artifact/dev.yidafu.swc/swc-binding)

其他问题，欢迎[提Issue](https://github.com/yidafu/swc-binding/issues/new)。

> 思考永无止境

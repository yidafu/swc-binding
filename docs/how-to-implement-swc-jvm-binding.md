# How to implement SWC JVM binding

Translated with DeepL.com (free version)

## Background

In the process of using Kotlin Jupiter Kennel, I found that there is no 3D drawing library, and I can only use JS to draw data. We can only use JS to draw the data by using the `HTML(...) ` function to write JS, which is very inconvenient. So I wrote the [kotlin-jupyter-js](https://github.com/yidafu/kotlin-jupyter-js) plugin to support `%js` line magics. The core problem with the `kotlin-jupyter-js` plugin is: compiling JS code into ASTs is supported in the JVM. The core problem with the `kotlin-jupyter-js` plugin is that the JVM supports compiling JS code into ASTs.

My idea is to implement SWC's JVM binding to solve this problem, SWC itself provides Node binding, so JVM binding is not that difficult to implement. Moreover, SWC supports TS/JSX compilation, which allows `kotlin-jupyter-js` to support `typescript` and `React`.

## Implementation Ideas

The SWC JVM binding implementation is divided into two parts: 1) compiling the SWC Rust code into a JNI dynamic library, and 2) the JVM side, which implements the configuration classes and AST classes.

SWC is for JS and only provides support for Node binding, we need to refer to Node binding to implement JVM binding.

SWC Node binding exposes API output and input parameters are JSON strings, in Node, JSON strings can be easily converted to objects, but in JVM, you need to declare the corresponding classes.

> SWC provides WASM binding, you can encapsulate SWC based on WASM, the advantage is that you don't need to implement JNI binding, but you need to introduce WASM Runtime, so we don't consider it.

## SWC binding

### Rust JNI FFIs

Compiling Rust into a JNI dynamic library requires the Rust JNI FFI, which is supported by using [jni](https://crates.io/crates/jni).

This library provides an easy way to bridge Rust and Java, see the official `jni` example.

On the JVM side of the code.

```kotlin
class HelloWorld {
    init {
        System.loadLibrary("mylib");
    }

    external fun hello(input: String): String;
}
```

In Rust code it's just a matter of writing the glue code.

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

Calling `HelloWorld().hello("JNI")` through JNI will call the Rust code returning `Hello, JNI!`.

The declaration of the bridge function in the above Rust code is quite long, you can use [jni_fn](https://crates.io/crates/jni_fn) to generate the bridge function declaration automatically by macro to simplify the declaration.

```rust
#[jni_fn("HelloWorld")]
pub fn hello<'local>(...) -> jstring
```

With `jni` and `jni_fn` we can compile Rust code into JNI dynamic libraries.

### Binding

SWC Node binding offers the following methods.

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

SWC Node binding provides synchronous and asynchronous methods via [napi](https://crates.io/crates/napi). However, the JVM's FFI `jni` doesn't only support asynchrony, so we only implement the synchronous APIs: `transformSync`,`transformFileSync`,`parseSync`,`parseFileSync`,`minifySync`,`printSync`.

### pase_sync

Below is an example of `pase_sync` to explain how to implement it.

#### Dependencies

SWC itself only considers Node binding.[swc_core](https://crates.io/crates/swc_core) implements the logic of binding to Node, aggregating other SWC sub-package dependencies. NMP package `@swc/core` also wraps `swc_core`. We can't use the `swc_core` library directly, we need to replace other SWC subpackage calls.

For example, `Compiler` from `swc_core`:

```rs
use swc_core::{
    base::{
        Compiler,
    },
}
```

Needs to be changed to be introduced from [swc](https://crates.io/crates/swc).

```rust
use swc::Compiler;
```

All SWC-related dependencies after `swc_core` conversion.

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

#### entry/exit parameter

Theoretically, what needs to be done is simple: replace all `napi` related logic with `jni`. We don't need to change how SWC implements the specific functionality.

See [SWC - binding_core_node](https://github.com/swc-project/swc/tree/main/bindings/binding_core_node) for the `pase_sync` implementation [binding_core_node /src/parse.rs#L168](https://github.com/swc-project/swc/blob/828190c035d61e6521280e2260c511bc02b81327/bindings/binding_core_node/ src/parse.rs#L168), `parseSync` copies most of the logic directly, but requires changes to the handling of incoming and outgoing parameters.

The `pase_sync` implementation of `binding_core_node`:

```rust
#[napi]
pub fn parse_sync(src: String, opts: Buffer, filename: Option<String>) -> napi::Result<String> {
    // ...


    Ok(serde_json::to_string(&program)?)
}
```

Signature changes and entry/exit parameter processing are required:

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

Getting a string passed by the JVM requires a call to `get_string` of `JNIEnv`.

Converting a Rust string to a Java string also requires a call to `new_string` of `JNIEnv` before converting to a `jstring` type.

#### Exception Handling

If SWC fails to process JS code (e.g. JS code has syntax errors), it needs to throw an exception to the JVM, which will be handled by the JVM side.

The code thrown by Rust is first caught and then converted into an exception thrown by the JVM.

The `binding_core_node` handler implements the `MapErr<T>` trait for `Result`, which converts the Rust exception to a `napi` exception via the `convert_err` method, and finally throws it in the Node.

Exception handling in SWC [swc/bindings/binding_core_node/src/parse.rs#L179](https://github.com/swc-project/swc/blob/ 828190c035d61e6521280e2260c511bc02b81327/bindings/binding_core_node/src/parse.rs#L179)


```rust
let program = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
    // ....
}).convert_err()?;
```

We need to throw JVM exceptions, so implement the JVM's `MapErr<T>` trait to turn Rust exceptions into `jni` exceptions for `jni` to throw to the JVM.

Copy the SWC's `MapErr<T>` trait.


```rust
pub trait MapErr<T>: Into<Result<T, anyhow::Error>> {
    fn convert_err(self) -> SwcResult<T> {
        self.into().map_err(|err| SwcException::SwcAnyException {
            msg: format!("{:?}", err),
        })
    }
}
```

`Result` implements `MapErr<T>`.

```rust
impl<T> MapErr<T> for Result<T, anyhow::Error> {}
```

Here `jni` throws an exception and it should be noted that the function still needs to return a value, usually an empty string. Here [jni-rs#76](https://github.com/jni-rs/jni-rs/issues/76) explains why.

> You still have to return to the JVM, even if you've thrown an exception. Remember that unwinding across the ffi boundary is always undefined behavior, so any panics need to be caught and recovered from in your extern functions.

The final exception is handled like this

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

Implementation of Rust compiled into a dynamic library, the next step will need to implement the JVM side of the glue code, the following is the Kotlin implementation.

```kotlin
class SwcNative {
    init {
        System.loadLibrary("swc_jni")
    }

    @Throws(RuntimeException::class)
    external fun parseSync(code: String, options: String, filename: String?): String
}
```

When the JVM loads `swc_jni`, it looks for dynamic libraries from the filesystem as a rule, but not from the `resources` directory of the jar. So, by `System.loadLibrary("swc_jni")` if there is no `swc_jni` dynamic library locally, it will fail to load. The user installs from maven and there is definitely no `swc_jni` locally.

Solution, refer to this answer [Load Native Library from Class path](https://stackoverflow.com/questions/23189776/load-native-library-from-class-path), if `System.loadLibrary("swc_jni")` fails to load, then copy the jar's dynamic library to a temporary directory and load it again.

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

### Summary

Like the other methods just implement them like `parse_sync`.

At this point we can compile JS in the JVM.

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

Now that we get the AST JSON string, it is still inconvenient if we want to manipulate the AST. We need the JSON string to convert it to a class so that traversing and modifying it will be easy.

Also, the second `options` of `parseSync` is not type aware and needs to be constrained to a configuration item.

So how do we implement type descriptions for SWC ASTs and configuration item parameters in Kotlin?

I've tried an AI conversion from Rust to Kotlin and it works pretty well. The only problem is that it requires kryptonite, and I admit that lack of money is my problem.

Writing SWC class definitions from scratch? I'm afraid there's a lot of work to be done, SWC has 200+ ASTs and configuration item types.

The best solution is to generate Kotlin classes via scripts. As it happens, SWC provides the TS declaration file [@swc/types](https://www.npmjs.com/package/@swc/types).

### @swc/types

When you open the declaration file for `@swc/types`, it is full of `type` and `interface` declarations with a very simple structure.

It can be divided into the following cases.

1. type alias
   1. literal union type: `type T = 'foo' | 'bar'`
   2. primary union type: `type T = string | number`
   3. type alias and object literal type: `type T = S & { foo: string }`
   4. type alias union type: `type T = S | E`
2. interface

The case of Type alias is relatively complex, mainly because of the flexibility of JS.

### type alias

For some special cases we need to reduce the dynamics of types to make it easier for us to work with them.

Like `T | T[]` we can convert to `T[]` to avoid not being able to define the type in Kotlin.

For example:

```ts
export interface Config {
    test?: string | string[];
    // ...
}
```

Just convert:

```kotlin
class Config {
    var test: Array<String>? = null
}
```

A literal union type like `props: 'foo' | 'bar' ` should be converted directly to the base type: `val props: String?`.

A `type T = S & { foo: string }` requires that the object literal type be extracted as a separate type, with T inheriting from S and the extracted new type. Conversion to kotlin should look like this:

```kotlin
interface BaseT {
    val foo: String;
}

class T : S, BaseT {}
```

### interface

For `interface` processing, it is divided into 3 parts: 1. TS interface to Kotlin class; 2. inheritance; 3. serialization.

#### TS interface to Kotlin class

Define a `KotlinClass` to represent the Kotlin class to be converted. Implement `toString()` to convert it to a Kotlin class.

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

The `KotlinClass` is generated by traversing the AST of the TS interface.

When traversing interface properties, you need to recursively traverse the properties of the parent class. Properties inherited from the parent type need to set `KotlinClassProperty.isOverride` to true to facilitate the generation of kotlin classes with the `override` modifier.

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

#### Inheritance

The parent interface from which the TS interface directly inherits is simply added to the `KotlinClass.parents` array.

However, `type T = S | E` needs to be handled separately.

As an example

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

Here Expression is the parent of all `XxxExpression`. This makes `variableDeclarator.init = thisExpression` or `variableDeclarator.init = arrayExpression` assignments legal.

Because `Expression` is a type alias in TS, converting kotlin turns it into an empty interface. Converting to Kotlin results in something like this

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

So, for `type T = S | E`, `T` is the parent of `S` and `E`, and `T` needs to be added to the `KotlinClass.parents` array of `S`,`E`.

#### Serialization

When serializing AST nodes, one encounters problems with polymorphic serialization.

For example, serialize `Expression`, and `Expression` is an empty interface, then `toJson` doesn't know how to deal with `ThisExpression` and `ArrayExpression` properties, and then it can only throw an exception or output an empty object, which don't meet our expectation.

```kotlin
val thisExpression: ThisExpression = ThisExpression()
val arrayExpression: ArrayExpression = ArrayExpression()

var expression: Expression = thisExpression
toJson(expression)

expression = arrayExpression
toJson(expression)
```

The same goes for deserialization. `parseJson` also doesn't know whether to convert a string to `ThisExpression` or `ArrayExpression`.

```kotlin
val thisExpression = """ {"type":"ThisExpression", "props": "any value" } """
val arrayExpression = """ {"type":"ThisExpression", "elements": [] } """

var expression: Expression = parseJson(thisExpression)
var expression: Expression = parseJson(arrayExpression)
```

Serialization using kotlinx serialization, which supports [polymorphic serialization](https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md), requires transforming the kotlin code.

Annotate the class with `JsonClassDiscriminator` to indicate by which field the type is distinguished, and `SerialName` to indicate the name of the type after serialization. Deserialization can find the specific type based on this type name.

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

In order for serialization and deserialization to be able to correctly find specific types, it is also necessary to define `SerializersModule`.

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

This allows normal serialization of polymorphic types

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

We have generated class definitions for ASTs and configuration items, and would find it less elegant and convenient to build configuration or ASTs directly using classes.


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

The JS code above, if we Kotlin build the AST

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

Simplified property settings are invoked via `apply`. Relative to spaghetti code, it's already cleaner via `apply`. It could be a bit more succinct.

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

DSL is now very much like outputting AST JSON and is very simple and straightforward to write.

Classes that require DSL writing require the `SwcDslMarker` annotation marker. The `SwcDslMarker` is mainly to restrict the scope and avoid accessing the outer scope.

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

You can refer to the official documentation for how to implement it: [kotlin -- Type-safe builders](https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker)

#### DSL extension function

```kotlin
interface VariableDeclarator : Node, HasSpan {
    val init: Expression?;
    // other props...
}
```

For the `VariableDeclarator` interface, its init field type is `Expression`, meaning that its right value can be any subtype of `arrayExpression`, `thisExpression`, and so on.

```kotlin
variableDeclarator {
    init = arrayExpression { ... }
    // or
    init = thisExpression { ... }
}
```

So for `VariableDeclarator` it should have methods to create all `Expression` subclasses. The creation of `Expression` subclasses is added by extending the function to do so.

When we parse `@swc/types` declaration file, we need to check the type of the attribute, if it is converted to Kotlin and is a class, then find out all its non-intermediate subclasses, and then generate extension functions for it.

```kotlin
fun VariableDeclarator.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}
```

This allows the `Expression` class to be constructed from the `arrayExpression {}` function in `variableDeclarator {}`.

#### `TemplateLiteral` vs `TsTemplateLiteralType`

There's another special case to deal with here. `TemplateLiteral` conflicts with `TsTemplateLiteralType`, whose `type` is `"TemplateLiteral"`. This makes DSL-built ASTs unserializable. See the definition of a structure in rust.

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

These two types need to be handled separately and not generated by a script.

Implement both `TemplateLiteral`, `TsTemplateLiteralType` by one class. When used, it is then up-converted to `TemplateLiteral`, `TsTemplateLiteralType`.

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

Now we can upgrade the `parseSync` signature.

```kotlin
@Throws(RuntimeException::class)
fun parseSync(code: String, options: ParserConfig, filename: String?): Program 
```

Type safety and type hints are now guaranteed when used.

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

## Conclusion

Here, we have explained the idea and core implementation points of SWC JVM binding: 1. SWC supports JNI; 2. AST JSON is serialized into Kotlin classes; 3. ASTs and configurations are described through DSL.

Some details are not covered, such as the handling of boundary cases in Kotlin generated scripts, Rust cross-compilation, etc. For more details, you can read the source code. If you are interested in the details, you can read the source code [yidafu/swc-binding](https://github.com/yidafu/swc-binding).

If you need to compile JS in the JVM, SWC JVM binding has been released to the Maven central repository, use [dev.yidafu.swc:swc-binding:0.5.0](https://mvnrepository.com/artifact/dev.yidafu.swc/). swc-binding)

For other questions, feel free to [mention Issue](https://github.com/yidafu/swc-binding/issues/new).

> Thinking never ends.

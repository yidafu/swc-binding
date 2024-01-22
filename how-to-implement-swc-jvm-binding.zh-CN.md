<!DOCTYPE html>
<html>
<head>
  <title>docs/how-to-implement-swc-jvm-binding.zh-CN.md</title>
  <link rel="stylesheet" href="github-markdown.css">
</head>
<body>
<h1 id="-swc-jvm-binding">如何实现 SWC JVM binding</h1>
<h2 id="-">背景</h2>
<p>在使用 Kotlin Jupiter Kennel 的过程中发现没有3D绘制库，只能使用 JS 来绘制数据。只能通过<code>HTML(...)</code>函数来写 JS，非常不方便。所以，我写了 <a href="https://github.com/yidafu/kotlin-jupyter-js">kotlin-jupyter-js</a> 插件来支持<code>%js</code> line magics。<code>kotlin-jupyter-js</code>插件的核心问题是：在 JVM 支持编译 JS 代码成 AST。为此需要一个工具将 JS 代码转换成 AST，最好还能支持 TS 和 JSX。</p>
<p>我的想法是实现 SWC 的 JVM binding 来解决这个问题。SWC 本身提供 Node 的 binding，所以 JVM binding 实现难度没有那么大。而且，SWC 支持 TS/JSX 编译，可以让<code>kotlin-jupyter-js</code>支持<code>typescript</code>和<code>React</code>。</p>
<h2 id="-">实现思路</h2>
<p>SWC JVM binding 实现了分成两部分。1. 将 SWC 的 Rust 代码编译成 JNI 动态库；2. JVM 侧，实现配置类和 AST 类。</p>
<p>SWC 是给 JS 使用的，只提供了支持 Node binding。我们需要参考 Node binding，来实现 JVM 的 binding。</p>
<p>SWC Node binding 暴露的 API 出参、入参都是 JSON 字符串，在 Node 里 JSON 字符串很容易转为对象，在 JVM 里则需要相应的类声明。</p>
<blockquote>
<p>SWC 提供了 WASM binding，可以基于 WASM 来封装 SWC，好处是不需要实现 JNI binding，但是需要额外引入 WASM Runtime。故没有考虑。</p>
</blockquote>
<h2 id="swc-binding">SWC binding</h2>
<h3 id="rust-jni-ffi">Rust JNI FFI</h3>
<p>将 Rust 编译成 JNI 动态库，需要 Rust 的 JNI FFI。直接使用 <a href="https://crates.io/crates/jni">jni</a> 即可支持。</p>
<p>这个库提供可以很方便地桥接 Rust 和 Java。可以看一下 <code>jni</code> 的官方例子。</p>
<p>在 JVM 侧代码。</p>
<pre><code class="lang-kotlin">class HelloWorld {
    init {
        System.loadLibrary(&quot;mylib&quot;);
    }

    external fun hello(input: String): String;
}
</code></pre>
<p>在 Rust 代码只需要写一下胶水代码即可。</p>
<pre><code class="lang-rust">#[no_mangle]
pub extern &quot;system&quot; fn Java_HelloWorld_hello&lt;&#39;local&gt;(mut env: JNIEnv&lt;&#39;local&gt;, class: JClass&lt;&#39;local&gt;, input: JString&lt;&#39;local&gt;) -&gt; jstring {
    let input: String =
        env.get_string(&amp;input).expect(&quot;Couldn&#39;t get java string!&quot;).into();

    // your business logic
    let output = env.new_string(format!(&quot;Hello, {}!&quot;, input))
        .expect(&quot;Couldn&#39;t create java string!&quot;);

    output.into_raw()
}
</code></pre>
<p>调用<code>HelloWorld().hello(&quot;JNI&quot;)</code>，通过 JNI 会调用Rust 代码返回<code>Hello, JNI!</code>.</p>
<p>上面 Rust 代码里桥接函数的申明比较长，可以使用 <a href="https://crates.io/crates/jni_fn">jni_fn</a> 通过宏自动生成桥接函数声明，简化声明。</p>
<pre><code class="lang-rust">#[jni_fn(&quot;HelloWorld&quot;)]
pub fn hello&lt;&#39;local&gt;(...) -&gt; jstring
</code></pre>
<p>通过 <code>jni</code> 和 <code>jni_fn</code> 我们可以将 Rust 代码编译成 JNI 动态库。</p>
<h3 id="binding">Binding</h3>
<p>SWC Node binding 提供了以下方法</p>
<ul>
<li>transform<ul>
<li>transform</li>
<li>transformSync</li>
<li>transformFile</li>
<li>transformFileSync</li>
</ul>
</li>
<li>parse<ul>
<li>parse</li>
<li>parseSync</li>
<li>parseFile</li>
<li>parseFileSync</li>
</ul>
</li>
<li>minify<ul>
<li>minify</li>
<li>minifySync</li>
</ul>
</li>
<li>print<ul>
<li>print</li>
<li>printSync</li>
</ul>
</li>
</ul>
<p>SWC Node binding 通过 <a href="https://crates.io/crates/napi">napi</a> 提供同步和异步方法。但是 JVM 的 FFI <code>jni</code> 并不只支持异步，所以我们只实现同步 API：<code>transformSync</code>,<code>transformFileSync</code>,<code>parseSync</code>,<code>parseFileSync</code>,<code>minifySync</code>,<code>printSync</code>。</p>
<h3 id="pase_sync">pase_sync</h3>
<p>下面以<code>pase_sync</code>为例，解释如何实现。</p>
<h4 id="-">依赖</h4>
<p>SWC 本身只考虑了 Node binding。<a href="https://crates.io/crates/swc_core">swc_core</a> 实现了与 Node 绑定的逻辑、聚合其他 SWC 子包依赖。NMP 包<code>@swc/core</code>也是封装<code>swc_core</code>。我们不能直接使用<code>swc_core</code>库，需要替换其他 SWC 子包调用。</p>
<p>比如，从<code>swc_core</code>引入<code>Compiler</code>：</p>
<pre><code class="lang-rs">use swc_core::{
    base::{
        Compiler,
    },
}
</code></pre>
<p>需要改为从 <a href="https://crates.io/crates/swc">swc</a> 引入。</p>
<pre><code class="lang-rust">use swc::Compiler;
</code></pre>
<p><code>swc_core</code>转换后的所有 SWC 相关依赖:</p>
<pre><code class="lang-toml">[dependencies]
# ...
swc = &quot;0.270.25&quot;
swc_common = &quot;0.33.9&quot;
swc_ecma_ast = { version =&quot;0.110.10&quot;, features = [&quot;serde-impl&quot;] }
swc_ecma_transforms = &quot;0.227.19&quot;
swc_ecma_transforms_base = &quot;0.135.11&quot;
swc_ecma_visit = &quot;0.96.10&quot;
swc_ecma_codegen = &quot;0.146.39&quot;
# ...
</code></pre>
<h4 id="-">出入参</h4>
<p>理论上，需要做的工作很简单：将所有 <code>napi</code> 相关逻辑替换成<code>jni</code>即可。如何 SWC 如何实现具体功能，我们都不需要改动。</p>
<p>参考 <a href="https://github.com/swc-project/swc/tree/main/bindings/binding_core_node">SWC - binding_core_node</a> 的 <code>pase_sync</code> 实现 <a href="https://github.com/swc-project/swc/blob/828190c035d61e6521280e2260c511bc02b81327/bindings/binding_core_node/src/parse.rs#L168">binding_core_node/src/parse.rs#L168</a>, <code>parseSync</code> 大部分逻辑都直接复制，但需要修改入参、出参的处理。</p>
<p><code>binding_core_node</code> 的 <code>pase_sync</code> 实现：</p>
<pre><code class="lang-rust">#[napi]
pub fn parse_sync(src: String, opts: Buffer, filename: Option&lt;String&gt;) -&gt; napi::Result&lt;String&gt; {
    // ...


    Ok(serde_json::to_string(&amp;program)?)
}
</code></pre>
<p>需要修改签名和出入参处理：</p>
<pre><code class="lang-rust">#[jni_fn(&quot;dev.yidafu.swc.SwcNative&quot;)]
pub fn parseSync(mut env: JNIEnv, _: JClass, code: JString, options: JString, filename: JString) -&gt; jstring {
    // process parameter
    let src: String = env
        .get_string(&amp;code)
        .expect(&quot;Couldn&#39;t get java string!&quot;)
        .into();
    let opts: String = env
        .get_string(&amp;options)
        .expect(&quot;Couldn&#39;t get java string!&quot;)
        .into();
    let filename: String = env
        .get_string(&amp;filename)
        .expect(&quot;Couldn&#39;t get java string!&quot;)
        .into();

    // ...

    // process return value
    let output = env
        .new_string(ast_json)
        .expect(&quot;Couldn&#39;t create java string!&quot;);

    output.into_raw()
}
</code></pre>
<p>获取 JVM 传过来的字符串，需要调用<code>JNIEnv</code>的<code>get_string</code>。</p>
<p>将 Rust 字符串转为Java字符串也需要调用 <code>JNIEnv</code>的<code>new_string</code>在转为<code>jstring</code>类型。</p>
<h4 id="-">异常处理</h4>
<p>如果 SWC 处理 JS 代码失败了（比如JS代码有语法错误），需要抛出异常到 JVM，由 JVM 侧进行处理。</p>
<p>首先捕获 Rust 抛出的代码，再转换成 JVM 的异常抛出。</p>
<p><code>binding_core_node</code> 处理时对于<code>Result</code>实现了<code>MapErr&lt;T&gt;</code> trait,通过<code>convert_err</code> 方法将 Rust 异常转为了<code>napi</code>的异常，最后在 Node 里抛出。</p>
<p>SWC 的异常处理 <a href="https://github.com/swc-project/swc/blob/828190c035d61e6521280e2260c511bc02b81327/bindings/binding_core_node/src/parse.rs#L179">swc/bindings/binding_core_node/src/parse.rs#L179</a></p>
<pre><code class="lang-rust">let program = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
    // ....
}).convert_err()?;
</code></pre>
<p>我们需要抛出 JVM 的异常，所以要实现 JVM 的 <code>MapErr&lt;T&gt;</code> trait，将Rust异常转为 <code>jni</code>的异常，让<code>jni</code>抛出到 JVM。</p>
<p>抄一下 SWC 的 <code>MapErr&lt;T&gt;</code> trait。</p>
<pre><code class="lang-rust">pub trait MapErr&lt;T&gt;: Into&lt;Result&lt;T, anyhow::Error&gt;&gt; {
    fn convert_err(self) -&gt; SwcResult&lt;T&gt; {
        self.into().map_err(|err| SwcException::SwcAnyException {
            msg: format!(&quot;{:?}&quot;, err),
        })
    }
}
</code></pre>
<p><code>Result</code>实现<code>MapErr&lt;T&gt;</code></p>
<pre><code class="lang-rust">impl&lt;T&gt; MapErr&lt;T&gt; for Result&lt;T, anyhow::Error&gt; {}
</code></pre>
<p>这里 <code>jni</code> 抛出异常需要注意，函数依然需要返回值，一般返回空串。这里 <a href="https://github.com/jni-rs/jni-rs/issues/76">jni-rs#76</a> 解释了原因。</p>
<blockquote>
<p>You still have to return to the JVM, even if you&#39;ve thrown an exception. Remember that unwinding across the ffi boundary is always undefined behavior, so any panics need to be caught and recovered from in your extern functions.</p>
</blockquote>
<p>最后异常处理像这样</p>
<pre><code class="lang-rust">let result = try_with(c.cm.clone(), false, ErrorFormat::Normal, |handler| {
    // ...
}).convert_err();

match result {
    Ok(program) =&gt; {
        // ...
    }
    Err(e) =&gt; {
        match e {
            SwcException::SwcAnyException { msg } =&gt; {
                env.throw(msg).unwrap();
            }
        }
        return JString::default().into_raw();
    }
}
</code></pre>
<h3 id="swcnative">SwcNative</h3>
<p>实现Rust编译成动态库，下一步就需要实现 JVM 侧胶水代码，下面是 Kotlin 实现。</p>
<pre><code class="lang-kotlin">class SwcNative {
    init {
        System.loadLibrary(&quot;swc_jni&quot;)
    }

    @Throws(RuntimeException::class)
    external fun parseSync(code: String, options: String, filename: String?): String
}
</code></pre>
<p>JVM 加载<code>swc_jni</code>时，会按照规则从文件系统寻找动态库，但是不会从 jar 的 <code>resources</code>目录寻找。所以，通过<code>System.loadLibrary(&quot;swc_jni&quot;)</code>如果本地没有<code>swc_jni</code>动态库，就会加载失败。用户从 maven 安装，本地肯定没有<code>swc_jni</code>。</p>
<p>解决方案，参考这个回答 <a href="https://stackoverflow.com/questions/23189776/load-native-library-from-class-path">Load Native Library from Class path</a>，如果<code>System.loadLibrary(&quot;swc_jni&quot;)</code> 加载失败就将 jar 的动态库复制到临时目录再加载。</p>
<pre><code class="lang-kotlin">    init {
        try {
            System.loadLibrary(&quot;swc_jni&quot;)
        } catch (e: UnsatisfiedLinkError) {
            // 加载失败，复制DLL到临时目录
            val dllPath = DllLoader.copyDll2Temp(&quot;swc_jni&quot;)
            // 再次加载
            System.load(dllPath)
        }
    }
</code></pre>
<h3 id="-">小结</h3>
<p>像其他方法就像<code>parse_sync</code>依葫芦画瓢实现就可以了。</p>
<p>到这一步我们已经可以在 JVM 里的编译 JS 了。</p>
<pre><code class="lang-kotlin">SwcNative().parseSync(
    &quot;var foo = &#39;bar&#39;&quot;, 
    &quot;&quot;&quot;{&quot;syntax&quot;: &quot;ecmascript&quot;;}&quot;&quot;&quot;,
    &quot;test.js&quot;,
)

</code></pre>
<p>&lt;details&gt;<br>&lt;summary&gt;output string&lt;/summary&gt;<br><br><code>json
{
  &quot;type&quot;: &quot;Module&quot;,
  &quot;span&quot;: {
    &quot;start&quot;: 0,
    &quot;end&quot;: 15,
    &quot;ctxt&quot;: 0
  },
  &quot;body&quot;: [
    {
      &quot;type&quot;: &quot;VariableDeclaration&quot;,
      &quot;span&quot;: {
        &quot;start&quot;: 0,
        &quot;end&quot;: 15,
        &quot;ctxt&quot;: 0
      },
      &quot;kind&quot;: &quot;var&quot;,
      &quot;declare&quot;: false,
      &quot;declarations&quot;: [
        {
          &quot;type&quot;: &quot;VariableDeclarator&quot;,
          &quot;span&quot;: {
            &quot;start&quot;: 4,
            &quot;end&quot;: 15,
            &quot;ctxt&quot;: 0
          },
          &quot;id&quot;: {
            &quot;type&quot;: &quot;Identifier&quot;,
            &quot;span&quot;: {
              &quot;start&quot;: 4,
              &quot;end&quot;: 7,
              &quot;ctxt&quot;: 2
            },
            &quot;value&quot;: &quot;foo&quot;,
            &quot;optional&quot;: false,
            &quot;typeAnnotation&quot;: null
          },
          &quot;init&quot;: {
            &quot;type&quot;: &quot;StringLiteral&quot;,
            &quot;span&quot;: {
              &quot;start&quot;: 10,
              &quot;end&quot;: 15,
              &quot;ctxt&quot;: 0
            },
            &quot;value&quot;: &quot;bar&quot;,
            &quot;raw&quot;: &quot;&#39;bar&#39;&quot;
          },
          &quot;definite&quot;: false
        }
      ]
    }
  ],
  &quot;interpreter&quot;: null
}</code><br><br>&lt;/code&gt;<br>&lt;/details&gt;

</p>
<h2 id="kotlin-ast-dsl">Kotlin AST DSL</h2>
<p>现在我们得到 AST JSON 字符串，如果想要对AST进行操作还是很不方便的。我们需要 JSON 字符串将其转换为类，这样遍历、修改都会非常方便。</p>
<p>而且 <code>parseSync</code> 的第二个<code>options</code>也不知道类型，需要约束配置项。</p>
<p>那我们如何在 Kotlin 实现 SWC AST 和配置项参数的类型描述呢？</p>
<p>我尝试过是 AI 将 Rust 转换为 Kotlin，效果相当不错。唯一的问题的就是需要氪金，我承认没钱是我的问题。</p>
<p>从头写 SWC 类定义？工作做恐怕有的大了。SWC 有 200+ 的 AST 和配置项类型。</p>
<p>最好的解决方式就是通过脚本来生成 Kotlin 类。恰好，SWC 提供了 TS 声明文件 <a href="https://www.npmjs.com/package/@swc/types">@swc/types</a>。</p>
<h3 id="-swc-types">@swc/types</h3>
<p>打开 <code>@swc/types</code> 的声明文件，里面都是 <code>type</code> 和 <code>interface</code> 声明，结构非常简单。</p>
<p>可以分为一下情况:</p>
<ol>
<li>type alias<ol>
<li>literal union type: <code>type T = &#39;foo&#39; | &#39;bar&#39;</code></li>
<li>primary union type: <code>type T = string | number</code></li>
<li>type alias and object literal type: <code>type T = S &amp; { foo: string }</code></li>
<li>type alias union type: <code>type T = S | E</code></li>
</ol>
</li>
<li>interface</li>
</ol>
<p>Type alias 的情况相对复杂，主要还是因为 JS 的灵活性。</p>
<h3 id="type-alias">type alias</h3>
<p>对于一些特殊情况我们需要减少类型的动态性，方便我们进行处理。</p>
<p>像 <code>T | T[]</code> 我们可以转为 <code>T[]</code>，避免在 Kotlin 里无法定义类型。</p>
<p>比如：</p>
<pre><code class="lang-ts">export interface Config {
    test?: string | string[];
    // ...
}
</code></pre>
<p>就转换为：</p>
<pre><code class="lang-kotlin">class Config {
    var test: Array&lt;String&gt;? = null
}
</code></pre>
<p>像 <code>props: &#39;foo&#39; | &#39;bar&#39;</code> 这样的字面量联合类型应该直接转为基础类型： <code>val props: String?</code>。</p>
<p><code>type T = S &amp; { foo: string }</code> 需要将对象字面量类型提取单独的类型，T 来继承 S 和 提取出来的新类型。转换成 kotlin应该是这样的：</p>
<pre><code class="lang-kotlin">interface BaseT {
    val foo: String;
}

class T : S, BaseT {}
</code></pre>
<h3 id="interface">interface</h3>
<p>对于 <code>interface</code> 处理，分为3部分：1. TS interface 转为 Kotlin 类；2. 继承关系；3. 序列化。</p>
<h4 id="ts-interface-kotlin-">TS interface 转为 Kotlin 类</h4>
<p>定义个 <code>KotlinClass</code> 来表示要装换成的 Kotlin 类。这样实现<code>toString()</code>即可方便地将其转为 kotlin 类。</p>
<pre><code class="lang-ts">export class KotlinClass {
    klassName: string = &#39;&#39;;
    headerComment: string = &#39;&#39;
    annotations: string[] = []
    modifier: string = &#39;&#39;
    parents: string[] = []
    properties: KotlinClassProperty[] = []
}
</code></pre>
<p>通过遍历 TS interface 的 AST，就可以生成 <code>KotlinClass</code>。</p>
<p>在遍历 interface 属性时，需要递归遍历父类的属性，继承自父类型的属性需要将<code>KotlinClassProperty.isOverride</code>设为 true，方便生成 kotlin 类是加上<code>override</code>修饰符。</p>
<pre><code class="lang-ts">class KotlinClassProperty {
    modifier: string = &#39;var&#39;
    name: string = &#39;&#39;
    type: string = &#39;&#39;
    comment: string = &#39;&#39;
    defaultValue: string = &#39;&#39;
    isOverride: boolean = false
    discriminator: string = &#39;type&#39;
}
</code></pre>
<h4 id="-">继承关系</h4>
<p>TS interface 直接继承的父 interface 直接加入 <code>KotlinClass.parents</code> 数组即可。</p>
<p>但是，对于 <code>type T = S | E</code> 需要进行单独处理</p>
<p>举个例子</p>
<pre><code class="lang-ts">export interface VariableDeclarator extends Node, HasSpan {
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
</code></pre>
<p>这里 Expression 是所有 <code>XxxExpression</code> 的父类型。这样<code>variableDeclarator.init = thisExpression</code> 或者 <code>variableDeclarator.init = arrayExpression</code> 赋值才合法。</p>
<p>因为 TS 里 <code>Expression</code> 是 type alias 转换 kotlin 要变成一个空接口。 转换成 Kotlin 结果像这样</p>
<pre><code class="lang-kotlin">interface Expression {}

class VariableDeclarator : Node, HasSpan {
    val init: Expression?;

    // other props...
}
class ArrayExpression : ExpressionBase, Expression {
    // ...
}
</code></pre>
<p>所以，对于 <code>type T = S | E</code>，<code>T</code> 是 <code>S</code> 和 <code>E</code>的父类，需要将 <code>T</code> 加入<code>S</code>,<code>E</code> 的 <code>KotlinClass.parents</code> 数组。</p>
<h4 id="-">序列化</h4>
<p>AST 节点序列化时，会遇到多态序列化的问题。</p>
<p>比如，序列化<code>Expression</code>，而<code>Expression</code>是空接口，这时<code>toJson</code>就不知道如何处理<code>ThisExpression</code>和<code>ArrayExpression</code>的属性，这时只能抛出异常或者输出空对象，都不符合我们的期望。</p>
<pre><code class="lang-kotlin">val thisExpression: ThisExpression = ThisExpression()
val arrayExpression: ArrayExpression = ArrayExpression()

var expression: Expression = thisExpression
toJson(expression)

expression = arrayExpression
toJson(expression)
</code></pre>
<p>反序列化也是一样的。<code>parseJson</code> 也不知道将字符串转为<code>ThisExpression</code>还是<code>ArrayExpression</code>。</p>
<pre><code class="lang-kotlin">val thisExpression = &quot;&quot;&quot; {&quot;type&quot;:&quot;ThisExpression&quot;, &quot;props&quot;: &quot;any value&quot; } &quot;&quot;&quot;
val arrayExpression = &quot;&quot;&quot; {&quot;type&quot;:&quot;ThisExpression&quot;, &quot;elements&quot;: [] } &quot;&quot;&quot;

var expression: Expression = parseJson(thisExpression)
var expression: Expression = parseJson(arrayExpression)
</code></pre>
<p>使用 kotlinx serialization 来序列化，它支持<a href="https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md">多态序列化</a>，需要将改造kotlin代码。</p>
<p>在类上注解<code>JsonClassDiscriminator</code>标明通过哪个字段来区分类型，<code>SerialName</code>注解标明序列后类型的名称。反序列化时可以根据这个类型名称找到具体类型。</p>
<pre><code class="lang-kotlin">interface ArrayExpression : ExpressionBase, Expression {
    // ....
}

@Serializable
@JsonClassDiscriminator(&quot;type&quot;)
@SerialName(&quot;ArrayExpression&quot;)
class ArrayExpressionImpl : ArrayExpression {
    // ...
}

interface ThisExpression : ExpressionBase, Expression {
    // ....
}

@Serializable
@JsonClassDiscriminator(&quot;type&quot;)
@SerialName(&quot;ThisExpression&quot;)
class ThisExpressionImpl : ThisExpression {
    // ....
}

</code></pre>
<p>为了序列化和反序列化是能够正确找到具体类型，还需要定义<code>SerializersModule</code>。</p>
<pre><code class="lang-kotlin">val swcSerializersModule = SerializersModule {
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
</code></pre>
<p>这样就可以正常序列化多态类型了</p>
<pre><code class="lang-kotlin">val json = Json {
    classDiscriminator = &quot;syntax&quot;
    serializersModule = configSerializer
}

json.decodeFromString&lt;Expression&gt;(&quot;&quot;&quot; {&quot;type&quot;:&quot;ThisExpression&quot;, &quot;elements&quot;: [] } &quot;&quot;&quot;)

val arrayExpression: Expression = ArrayExpression()
json.encodeToString&lt;Expression&gt;(arrayExpression)
</code></pre>
<h3 id="dsl">DSL</h3>
<p>我们生成好了 AST 和配置项的类定义，如果直接使用类来构建配置或者 AST 会发现不太优雅和方便。</p>
<pre><code class="lang-js">const foo = &#39;bar&#39;
</code></pre>
<p>&lt;details&gt;<br>&lt;summary&gt;SWC compile output string&lt;/summary&gt;<br><br><code>json
    {
      &quot;type&quot;: &quot;VariableDeclaration&quot;,
      &quot;span&quot;: {
        &quot;start&quot;: 0,
        &quot;end&quot;: 17,
        &quot;ctxt&quot;: 0
      },
      &quot;kind&quot;: &quot;const&quot;,
      &quot;declare&quot;: false,
      &quot;declarations&quot;: [
        {
          &quot;type&quot;: &quot;VariableDeclarator&quot;,
          &quot;span&quot;: {
            &quot;start&quot;: 6,
            &quot;end&quot;: 17,
            &quot;ctxt&quot;: 0
          },
          &quot;id&quot;: {
            &quot;type&quot;: &quot;Identifier&quot;,
            &quot;span&quot;: {
              &quot;start&quot;: 6,
              &quot;end&quot;: 9,
              &quot;ctxt&quot;: 2
            },
            &quot;value&quot;: &quot;foo&quot;,
            &quot;optional&quot;: false,
            &quot;typeAnnotation&quot;: null
          },
          &quot;init&quot;: {
            &quot;type&quot;: &quot;StringLiteral&quot;,
            &quot;span&quot;: {
              &quot;start&quot;: 12,
              &quot;end&quot;: 17,
              &quot;ctxt&quot;: 0
            },
            &quot;value&quot;: &quot;bar&quot;,
            &quot;raw&quot;: &quot;&#39;bar&#39;&quot;
          },
          &quot;definite&quot;: false
        }
      ]
    }</code><br>&lt;/details&gt;

</p>
<p>上面 JS 代码，如果我们 Kotlin 来构建 AST</p>
<pre><code class="lang-kotlin">VariableDeclarationImpl().apply {
    span = Span(0, 17, 0)
    kind = &#39;const&#39;
    declare = false
    declarations = arrayOf(
        VariableDeclaratorImpl().apply {
            span = Span(6, 17, 0)
            id = IdentifierImpl().apply {
                span = span(5, 9, 0)
                value = &quot;foo&quot;
            }
            init = StringLiteralImpl().apply {
                span = Span(12,17, 0)
                value = &quot;bar&quot;
                raw = &quot;&#39;bar&#39;&quot;
            }
        }
    )
}
</code></pre>
<p>通过<code>apply</code>来调用简化的属性设置。相对面条式代码，通过<code>apply</code>已经比较简洁。还能简洁一点。</p>
<pre><code class="lang-kotlin">variableDeclaration  {
    span = span(0, 17, 0)
    kind = &#39;const&#39;
    declare = false
    declarations = arrayOf(
        variableDeclaratorImpl {
            span = span(6, 17, 0)
            id = identifier {
                span = span(5, 9, 0)
                value = &quot;foo&quot;
            }
            init = stringLiteral {
                span = span(12,17, 0)
                value = &quot;bar&quot;
                raw = &quot;&#39;bar&#39;&quot;
            }
        }
    )
}
</code></pre>
<p>现在的 DSL 已经很像输出 AST JSON，写起来也非常简单直白。</p>
<p>需要DSL写法的类，都需要<code>SwcDslMarker</code>注解标记。<code>SwcDslMarker</code>主要是为了限制作用域，避免访问外层作用域。</p>
<pre><code class="lang-kotlin">@DslMarker
annotation class SwcDslMarker

@SwcDslMarker
class VariableDeclarationImpl {
    // ...
}

fun variableDeclaration(block: VariableDeclaration.() -&gt; Unit): VariableDeclaration {
    return VariableDeclarationImpl().apply(block)
}
</code></pre>
<p>如何实现可以参考官方文档：<a href="https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker">kotlin -- Type-safe builders</a></p>
<h4 id="dsl-extension-function">DSL extension function</h4>
<pre><code class="lang-kotlin">interface VariableDeclarator : Node, HasSpan {
    val init: Expression?;
    // other props...
}
</code></pre>
<p>对于<code>VariableDeclarator</code>接口，其init字段类型是<code>Expression</code>，意味着它的右值可以是<code>arrayExpression</code>、<code>thisExpression</code>等任意子类型。</p>
<pre><code class="lang-kotlin">variableDeclarator {
    init = arrayExpression { ... }
    // or
    init = thisExpression { ... }
}
</code></pre>
<p>所以对于<code>VariableDeclarator</code>它应该有创建所有 <code>Expression</code> 子类的方法。通过扩展函数来实现来添加创建<code>Expression</code> 子类。</p>
<p>我们在解析<code>@swc/types</code>声明文件时，需要检查属性类型，如果是转换成Kotlin后是类，那就找出其所有非中间子类，然后为其生成扩展函数。</p>
<pre><code class="lang-kotlin">fun VariableDeclarator.arrayExpression(block: ArrayExpression.() -&gt; Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}
</code></pre>
<p>这样在 <code>variableDeclarator {}</code> 里就可以通过 <code>arrayExpression {}</code> 函数构建<code>Expression</code>类。</p>
<h4 id="templateliteral-vs-tstemplateliteraltype"><code>TemplateLiteral</code> vs <code>TsTemplateLiteralType</code></h4>
<p>这里还有个特殊情况需要处理。<code>TemplateLiteral</code>跟<code>TsTemplateLiteralType</code>冲突了，他们的<code>type</code>都是<code>&quot;TemplateLiteral&quot;</code>。这使得 DSL 构建的 AST 无法序列化。参见 rust 的结构体定义。</p>
<pre><code class="lang-rust">// https://github.com/swc-project/swc/blob/828190c035d61e6521280e2260c511bc02b81327/crates/swc_ecma_ast/src/typescript.rs#L823
#[ast_node(&quot;TemplateLiteral&quot;)]
#[derive(Eq, Hash, EqIgnoreSpan)]
#[cfg_attr(feature = &quot;arbitrary&quot;, derive(arbitrary::Arbitrary))]
pub struct TsTplLitType {
    // ...
}
</code></pre>
<pre><code class="lang-rust">// https://github.com/swc-project/swc/blob/828190c035d61e6521280e2260c511bc02b81327/crates/swc_ecma_ast/src/expr.rs#L1060
#[ast_node(&quot;TemplateLiteral&quot;)]
#[derive(Eq, Hash, EqIgnoreSpan)]
#[cfg_attr(feature = &quot;arbitrary&quot;, derive(arbitrary::Arbitrary))]
pub struct Tpl {
    pub span: Span,

    #[cfg_attr(feature = &quot;serde-impl&quot;, serde(rename = &quot;expressions&quot;))]
    pub exprs: Vec&lt;Box&lt;Expr&gt;&gt;,

    pub quasis: Vec&lt;TplElement&gt;,
}
</code></pre>
<p>这两个类型需要单独处理，不由脚本来生成。</p>
<p>由一个类同时实现<code>TemplateLiteral</code>、<code>TsTemplateLiteralType</code>。使用时再向上转型为<code>TemplateLiteral</code>、<code>TsTemplateLiteralType</code>。</p>
<pre><code class="lang-kotlin">// ignore annotation
interface TemplateLiteral : ExpressionBase, Expression {
    var expressions: Array&lt;Expression&gt;?
    var quasis: Array&lt;TemplateElement&gt;?
    override var span: Span?
}

interface TsTemplateLiteralType : Node, HasSpan, TsLiteral {
    var types: Array&lt;TsType&gt;?
    var quasis: Array&lt;TemplateElement&gt;?
    override var span: Span?
}

class TemplateLiteralImpl : TemplateLiteral, TsTemplateLiteralType {
    override var types: Array&lt;TsType&gt;? = null
    override var expressions: Array&lt;Expression&gt;? = null
    override var quasis: Array&lt;TemplateElement&gt;? = null
    override var span: Span? = null
}

typealias TsTemplateLiteralTypeImpl = TemplateLiteralImpl
</code></pre>
<h3 id="-parsesync">新的 <code>parseSync</code></h3>
<p>现在我们可以升级<code>parseSync</code>签名了。</p>
<pre><code class="lang-kotlin">@Throws(RuntimeException::class)
fun parseSync(code: String, options: ParserConfig, filename: String?): Program 
</code></pre>
<p>现在使用时可以保证类型安全和类型提示了。</p>
<pre><code class="lang-kotlin">const program = SwcNative().parseSync(
    &quot;&quot;&quot;
    function App() {
       return &lt;div&gt;App&lt;/div&gt;
    }
    &quot;&quot;&quot;.trimIndent(),
    esParseOptions {
        jsx = true
        target = &quot;es5&quot;
    },
    &quot;temp.js&quot;
)
if (program is Module) {
    if (program.body?.get(0) is FunctionDeclaration) {
        // ...
    }
}
</code></pre>
<h2 id="-">总结</h2>
<p>到这里，解释了 SWC JVM binding 的实现思路和核心实现要点。1. SWC 支持 JNI；2. AST JSON 序列化成 Kotlin 类；3. 通过 DSL 描述 AST和配置项。</p>
<p>一些细碎的内容没有涉及到，比如，Kotlin 生成脚本一些边界情况的处理、Rust交叉编译等。对细节感兴趣可以阅读源码<a href="https://github.com/yidafu/swc-binding">yidafu/swc-binding</a>。</p>
<p>如果你有需求在 JVM 编译 JS，SWC JVM binding 已发布到 Maven 中央仓库，请使用 <a href="https://mvnrepository.com/artifact/dev.yidafu.swc/swc-binding">dev.yidafu.swc:swc-binding:0.5.0</a></p>
<p>其他问题，欢迎<a href="https://github.com/yidafu/swc-binding/issues/new">提Issue</a>。</p>
<blockquote>
<p>思考永无止境</p>
</blockquote>
</body>
</html>

package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import dev.yidafu.swc.generated.dsl.createModule
import dev.yidafu.swc.generated.dsl.createVariableDeclarator
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.string.shouldContain
import kotlinx.serialization.encodeToString

class AstBasicsTest : AnnotationSpec() {
    private val swc = SwcNative()

    @Test
    fun `create Span with span DSL`() {
        val s = span()

        s.shouldNotBeNull()
        s.shouldBeInstanceOf<Span>()
    }

    @Test
    fun `create Span with emptySpan DSL`() {
        val s = emptySpan()

        s.shouldNotBeNull()
        s.start shouldBe 0
        s.end shouldBe 0
        s.ctxt shouldBe 0
    }

    @Test
    fun `create Span with apply`() {
        val s = span().apply {
            start = 10
            end = 20
            ctxt = 0
        }

        s.start shouldBe 10
        s.end shouldBe 20
        s.ctxt shouldBe 0
    }

    @Test
    fun `serialize Span`() {
        val s = span().apply {
            start = 5
            end = 15
            ctxt = 2
        }

        val json = astJson.encodeToString(s)
        json.shouldNotBeNull()
    }

    @Test
    fun `deserialize Span`() {
        val json = """{"start":10,"end":20,"ctxt":5}"""
        val s = astJson.decodeFromString<Span>(json)

        s.start shouldBe 10
        s.end shouldBe 20
        s.ctxt shouldBe 5
    }

    @Test
    fun `serialize Span includes ctxt field`() {
        val s = span().apply {
            start = 5
            end = 15
            ctxt = 2
        }

        val json = astJson.encodeToString(s)
        
        // 验证 JSON 包含 ctxt 字段
        json shouldBe """{"start":5,"end":15,"ctxt":2}"""
    }

    @Test
    fun `serialize Span with default ctxt includes ctxt field`() {
        val s = span().apply {
            start = 1
            end = 10
            // ctxt 使用默认值 0
        }

        val json = astJson.encodeToString(s)
        
        // 验证 JSON 包含 ctxt 字段（即使使用默认值）
        json shouldBe """{"start":1,"end":10,"ctxt":0}"""
        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
    }

    @Test
    fun `serialize Span with zero ctxt includes ctxt field`() {
        val s = span().apply {
            start = 0
            end = 0
            ctxt = 0
        }

        val json = astJson.encodeToString(s)
        
        // 验证 JSON 包含 ctxt 字段
        json shouldBe """{"start":0,"end":0,"ctxt":0}"""
        json shouldContain "\"ctxt\""
    }

    @Test
    fun `deserialize Span without ctxt field uses default`() {
        // 测试从缺少 ctxt 字段的 JSON 反序列化
        val json = """{"start":10,"end":20}"""
        val s = astJson.decodeFromString<Span>(json)

        s.start shouldBe 10
        s.end shouldBe 20
        s.ctxt shouldBe 0 // 应该使用默认值
    }

    @Test
    fun `serialize Identifier with nested Span includes ctxt field`() {
        // 测试嵌套在 AST 节点中的 Span 是否包含 ctxt 字段
        val id = Identifier().apply {
            value = "testVar"
            optional = false
            span = span().apply {
                start = 0
                end = 8
                ctxt = 0
            }
        }

        val json = astJson.encodeToString<Identifier>(id)
        
        // 验证 JSON 中的嵌套 Span 包含 ctxt 字段
        json shouldContain "\"ctxt\""
        json shouldContain "\"span\""
        // 验证 span 对象中包含 ctxt
        val spanJson = json.substringAfter("\"span\":{").substringBefore("}")
        spanJson shouldContain "\"ctxt\""
    }

    @Test
    fun `serialize Module with nested Spans includes ctxt fields`() {
        // 测试包含多个嵌套 Span 的复杂 AST 节点
        val module = swc.parseSync(
            "const x = 1;",
            esParseOptions { },
            "test.js"
        ) as Module

        val json = astJson.encodeToString<Program>(module)
        
        // 验证 JSON 中包含 ctxt 字段（应该出现在所有 Span 对象中）
        json shouldContain "\"ctxt\""
        
        // 检查所有 "span" 对象后是否都有 "ctxt" 字段
        // 使用更精确的方法：查找所有 "span":{ 并检查对应的对象
        var index = 0
        var spanCount = 0
        var ctxtCount = 0
        val jsonLower = json.lowercase()
        
        while (true) {
            val spanIndex = jsonLower.indexOf("\"span\":{", index)
            if (spanIndex == -1) break
            
            spanCount++
            // 找到对应的结束括号（需要处理嵌套）
            var depth = 0
            var endIndex = spanIndex + 8 // 跳过 "span":{
            var found = false
            
            while (endIndex < json.length) {
                when (json[endIndex]) {
                    '{' -> depth++
                    '}' -> {
                        if (depth == 0) {
                            found = true
                            break
                        }
                        depth--
                    }
                }
                endIndex++
            }
            
            if (found) {
                val spanContent = json.substring(spanIndex, endIndex + 1)
                if (spanContent.contains("\"ctxt\"")) {
                    ctxtCount++
                } else {
                    // 打印缺少 ctxt 的 span 对象
                    println("Found span without ctxt: ${spanContent.take(150)}")
                }
            }
            
            index = spanIndex + 8
        }
        
        println("Total spans found: $spanCount, spans with ctxt: $ctxtCount")
        
        // 所有 span 对象都应该包含 ctxt 字段
        if (spanCount > 0) {
            spanCount shouldBe ctxtCount
        }
    }
    
    @Test
    fun `printSync serialized JSON includes ctxt in all Spans`() {
        // 测试 printSync 序列化的 JSON 中所有 Span 都包含 ctxt
        val module = swc.parseSync(
            "const x = 1; const y = 2;",
            esParseOptions { },
            "test.js"
        ) as Module

        // 模拟 printSync 中的序列化过程
        val programJson = astJson.encodeToString<Program>(module)
        val fixedProgram = astJson.decodeFromString<Program>(programJson)
        val finalJson = astJson.encodeToString<Program>(fixedProgram)
        
        // 检查所有 span 对象
        var index = 0
        var spanCount = 0
        var ctxtCount = 0
        val jsonLower = finalJson.lowercase()
        
        while (true) {
            val spanIndex = jsonLower.indexOf("\"span\":{", index)
            if (spanIndex == -1) break
            
            spanCount++
            // 找到对应的结束括号
            var depth = 0
            var endIndex = spanIndex + 8
            var found = false
            
            while (endIndex < finalJson.length) {
                when (finalJson[endIndex]) {
                    '{' -> depth++
                    '}' -> {
                        if (depth == 0) {
                            found = true
                            break
                        }
                        depth--
                    }
                }
                endIndex++
            }
            
            if (found) {
                val spanContent = finalJson.substring(spanIndex, endIndex + 1)
                if (spanContent.contains("\"ctxt\"")) {
                    ctxtCount++
                } else {
                    println("printSync: Found span without ctxt: ${spanContent.take(150)}")
                }
            }
            
            index = spanIndex + 8
        }
        
        println("printSync: Total spans: $spanCount, with ctxt: $ctxtCount")
        
        // 所有 span 对象都应该包含 ctxt 字段
        if (spanCount > 0) {
            spanCount shouldBe ctxtCount
        }
    }

    @Test
    fun `create IdentifierImpl`() {
        val id = Identifier().apply {
            value = "testVar"
            optional = false
            span = emptySpan()
        }

        id.value shouldBe "testVar"
        id.optional shouldBe false
    }

    @Test
    fun `serialize Identifier`() {
        val id = Identifier().apply {
            value = "myVar"
            optional = false
            span = emptySpan()
        }

        val json = astJson.encodeToString<Identifier>(id)
        json.shouldNotBeNull()
    }

    @Test
    fun `deserialize Identifier`() {
        val json = """{"type":"Identifier","value":"test","optional":false,"span":{"start":0,"end":0,"ctxt":0}}"""
        val id = astJson.decodeFromString<Identifier>(json)

        id.value shouldBe "test"
        id.optional shouldBe false
    }

    @Test
    fun `create StringLiteralImpl`() {
        val lit = StringLiteral().apply {
            value = "hello"
            raw = "\"hello\""
            span = emptySpan()
        }

        lit.value shouldBe "hello"
        lit.raw shouldBe "\"hello\""
    }

    @Test
    fun `create NumericLiteralImpl`() {
        val lit = NumericLiteral().apply {
            value = 42.0
            raw = "42"
            span = emptySpan()
        }

        lit.value shouldBe 42.0
        lit.raw shouldBe "42"
    }

    @Test
    fun `create BooleanLiteralImpl`() {
        val lit = BooleanLiteral().apply {
            value = true
            span = emptySpan()
        }

        lit.value shouldBe true
    }

    @Test
    fun `create NullLiteralImpl`() {
        val lit = NullLiteral().apply {
            span = emptySpan()
        }

        lit.shouldNotBeNull()
    }

    @Test
    fun `create ModuleImpl with apply`() {
        val mod = Module().apply {
            span = emptySpan()
            body = arrayOf()
        }

        mod.shouldNotBeNull()
        mod.body.shouldNotBeNull()
        mod.body!!.size shouldBe 0
    }

    @Test
    fun `use module DSL`() {
        val mod = module {
            span = emptySpan()
            body = arrayOf()
        }

        mod.shouldNotBeNull()
        mod.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `serialize Module`() {
        val mod = createModule {
            span = emptySpan()
            body = arrayOf()
        }

        val json = astJson.encodeToString<Module>(mod)
        json.shouldNotBeNull()
    }

    @Test
    fun `create VariableDeclaratorImpl`() {
        val decl = createVariableDeclarator {
            span = emptySpan()
            id = Identifier().apply {
                value = "x"
                span = emptySpan()
            }
            init = NumericLiteral().apply {
                value = 1.0
                raw = "1"
                span = emptySpan()
            }
        }

        decl.shouldNotBeNull()
        decl.id.shouldBeInstanceOf<Identifier>()
    }

    @Test
    fun `Span with same values`() {
        val s1 = span().apply { start = 1; end = 2; ctxt = 0 }
        val s2 = span().apply { start = 1; end = 2; ctxt = 0 }

        s1.shouldNotBeNull()
        s2.shouldNotBeNull()
        s1.start shouldBe s2.start
        s1.end shouldBe s2.end
        s1.ctxt shouldBe s2.ctxt
    }
}

package dev.yidafu.swc

import dev.yidafu.swc.generated.Span
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

/**
 * 测试 Span 类的序列化/反序列化行为，特别是 @EncodeDefault 注解的行为
 * 
 * 问题分析：
 * - Rust 端序列化时，如果 ctxt 字段值为 0（默认值），默认不会序列化该字段
 * - Kotlin 端反序列化时，缺失的 ctxt 会被设置为默认值 0（coerceInputValues = true）
 * - @EncodeDefault 的行为：仅在字段值等于默认值且 encodeDefaults = true 时才会序列化
 * - 关键问题：反序列化时缺失的字段，即使被设置为默认值，也可能不会在再次序列化时输出
 */
class SpanTest : ShouldSpec({
    
    should("直接创建的 Span 对象，ctxt 为默认值 0，序列化时应该包含 ctxt 字段") {
        // 直接创建 Span 对象，ctxt 使用默认值 0
        val span = Span().apply {
            start = 10
            end = 20
            // ctxt 使用默认值 0
        }
        
        val json = astJson.encodeToString(span)
        
        // 验证 JSON 包含 ctxt 字段（@EncodeDefault 应该生效）
        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
        json shouldBe """{"start":10,"end":20,"ctxt":0}"""
    }
    
    should("直接创建的 Span 对象，显式设置 ctxt = 0，序列化时应该包含 ctxt 字段") {
        // 显式设置 ctxt = 0
        val span = Span().apply {
            start = 5
            end = 15
            ctxt = 0
        }
        
        val json = astJson.encodeToString(span)
        
        // 验证 JSON 包含 ctxt 字段
        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
        json shouldBe """{"start":5,"end":15,"ctxt":0}"""
    }
    
    should("从包含 ctxt 字段的 JSON 反序列化，再次序列化时应该包含 ctxt 字段") {
        // 从包含 ctxt 字段的 JSON 反序列化
        val jsonInput = """{"start":10,"end":20,"ctxt":5}"""
        val span = astJson.decodeFromString<Span>(jsonInput)
        
        // 验证反序列化结果
        span.start shouldBe 10
        span.end shouldBe 20
        span.ctxt shouldBe 5
        
        // 再次序列化
        val jsonOutput = astJson.encodeToString(span)
        
        // 验证 JSON 包含 ctxt 字段
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":5"
        jsonOutput shouldBe """{"start":10,"end":20,"ctxt":5}"""
    }
    
    should("从缺少 ctxt 字段的 JSON 反序列化（模拟 Rust 端行为），再次序列化时应该包含 ctxt 字段") {
        // 模拟 Rust 端返回的 JSON：缺少 ctxt 字段（因为值为 0，Rust 默认不序列化）
        val jsonInput = """{"start":10,"end":20}"""
        val span = astJson.decodeFromString<Span>(jsonInput)
        
        // 验证反序列化结果：ctxt 应该被设置为默认值 0（coerceInputValues = true）
        span.start shouldBe 10
        span.end shouldBe 20
        span.ctxt shouldBe 0
        
        // 再次序列化 - 这是关键测试点
        val jsonOutput = astJson.encodeToString(span)
        
        // 验证 JSON 应该包含 ctxt 字段（@EncodeDefault 应该生效）
        // 注意：这是问题的关键 - 如果 @EncodeDefault 没有生效，这里 ctxt 字段会缺失
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":0"
        jsonOutput shouldBe """{"start":10,"end":20,"ctxt":0}"""
    }
    
    should("从缺少 ctxt 字段的 JSON 反序列化，修改 ctxt 后再序列化应该包含 ctxt 字段") {
        // 从缺少 ctxt 字段的 JSON 反序列化
        val jsonInput = """{"start":10,"end":20}"""
        val span = astJson.decodeFromString<Span>(jsonInput)
        
        // 修改 ctxt 值
        span.ctxt = 3
        
        // 序列化
        val jsonOutput = astJson.encodeToString(span)
        
        // 验证 JSON 包含 ctxt 字段
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":3"
        jsonOutput shouldBe """{"start":10,"end":20,"ctxt":3}"""
    }
    
    should("从缺少 ctxt 字段的 JSON 反序列化，显式设置 ctxt = 0 后再序列化应该包含 ctxt 字段") {
        // 从缺少 ctxt 字段的 JSON 反序列化
        val jsonInput = """{"start":10,"end":20}"""
        val span = astJson.decodeFromString<Span>(jsonInput)
        
        // 显式设置 ctxt = 0
        span.ctxt = 0
        
        // 序列化
        val jsonOutput = astJson.encodeToString(span)
        
        // 验证 JSON 包含 ctxt 字段
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":0"
        jsonOutput shouldBe """{"start":10,"end":20,"ctxt":0}"""
    }
    
    should("使用 span() DSL 创建的 Span，序列化时应该包含 ctxt 字段") {
        val span = span(start = 1, end = 10, ctxt = 0)
        
        val json = astJson.encodeToString(span)
        
        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
        json shouldBe """{"start":1,"end":10,"ctxt":0}"""
    }
    
    should("使用 emptySpan() 创建的 Span，序列化时应该包含 ctxt 字段") {
        val span = emptySpan()
        
        val json = astJson.encodeToString(span)
        
        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
        json shouldBe """{"start":0,"end":0,"ctxt":0}"""
    }
    
    should("从缺少 start 和 end 字段的 JSON 反序列化，应该使用默认值") {
        val jsonInput = """{"ctxt":2}"""
        val span = astJson.decodeFromString<Span>(jsonInput)
        
        span.start shouldBe 0
        span.end shouldBe 0
        span.ctxt shouldBe 2
        
        val jsonOutput = astJson.encodeToString(span)
        jsonOutput shouldContain "\"start\""
        jsonOutput shouldContain "\"end\""
        jsonOutput shouldContain "\"ctxt\""
    }
    
    should("从完全空的 JSON 对象反序列化，应该使用所有默认值") {
        val jsonInput = """{}"""
        val span = astJson.decodeFromString<Span>(jsonInput)
        
        span.start shouldBe 0
        span.end shouldBe 0
        span.ctxt shouldBe 0
        
        val jsonOutput = astJson.encodeToString(span)
        // 所有字段都应该被序列化（因为都有 @EncodeDefault）
        jsonOutput shouldContain "\"start\""
        jsonOutput shouldContain "\"end\""
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldBe """{"start":0,"end":0,"ctxt":0}"""
    }
    
    should("多次序列化/反序列化循环，ctxt 字段应该保持一致") {
        // 第一次：创建并序列化
        val span1 = Span().apply {
            start = 10
            end = 20
            ctxt = 0
        }
        val json1 = astJson.encodeToString(span1)
        json1 shouldContain "\"ctxt\""
        
        // 第二次：反序列化并再次序列化
        val span2 = astJson.decodeFromString<Span>(json1)
        val json2 = astJson.encodeToString(span2)
        json2 shouldContain "\"ctxt\""
        
        // 第三次：从缺少 ctxt 的 JSON 反序列化并序列化
        val jsonWithoutCtxt = """{"start":10,"end":20}"""
        val span3 = astJson.decodeFromString<Span>(jsonWithoutCtxt)
        val json3 = astJson.encodeToString(span3)
        json3 shouldContain "\"ctxt\""
        
        // 验证所有 JSON 都包含 ctxt 字段
        json1 shouldContain "\"ctxt\""
        json2 shouldContain "\"ctxt\""
        json3 shouldContain "\"ctxt\""
    }
    
    should("验证 start 和 end 字段的 @EncodeDefault 行为") {
        // 测试 start 和 end 字段的 @EncodeDefault 是否也正常工作
        val span = Span().apply {
            start = 0
            end = 0
            ctxt = 0
        }
        
        val json = astJson.encodeToString(span)
        
        // 所有字段都应该被序列化
        json shouldContain "\"start\""
        json shouldContain "\"end\""
        json shouldContain "\"ctxt\""
        json shouldBe """{"start":0,"end":0,"ctxt":0}"""
    }
    
    should("从缺少 start 字段的 JSON 反序列化，start 应该使用默认值 0，序列化时应该包含 start 字段") {
        val jsonInput = """{"end":20,"ctxt":1}"""
        val span = astJson.decodeFromString<Span>(jsonInput)
        
        span.start shouldBe 0
        span.end shouldBe 20
        span.ctxt shouldBe 1
        
        val jsonOutput = astJson.encodeToString(span)
        jsonOutput shouldContain "\"start\""
        jsonOutput shouldContain "\"start\":0"
        jsonOutput shouldBe """{"start":0,"end":20,"ctxt":1}"""
    }
    
    should("从缺少 end 字段的 JSON 反序列化，end 应该使用默认值 0，序列化时应该包含 end 字段") {
        val jsonInput = """{"start":10,"ctxt":1}"""
        val span = astJson.decodeFromString<Span>(jsonInput)
        
        span.start shouldBe 10
        span.end shouldBe 0
        span.ctxt shouldBe 1
        
        val jsonOutput = astJson.encodeToString(span)
        jsonOutput shouldContain "\"end\""
        jsonOutput shouldContain "\"end\":0"
        jsonOutput shouldBe """{"start":10,"end":0,"ctxt":1}"""
    }
    
    should("从缺少 ctxt 字段的 JSON 反序列化（具体数值），反序列化后应该有 ctxt=0") {
        // 测试具体的 JSON 输入：{"start":4258,"end":4270}
        val jsonInput = """{"start":4258,"end":4270}"""
        val span = astJson.decodeFromString<Span>(jsonInput)
        
        // 验证反序列化结果：ctxt 应该被设置为默认值 0
        span.start shouldBe 4258
        span.end shouldBe 4270
        span.ctxt shouldBe 0
        
        // 再次序列化，验证 ctxt 字段应该被包含
        val jsonOutput = astJson.encodeToString(span)
        jsonOutput shouldContain "\"ctxt\""
        jsonOutput shouldContain "\"ctxt\":0"
        jsonOutput shouldBe """{"start":4258,"end":4270,"ctxt":0}"""
    }
})


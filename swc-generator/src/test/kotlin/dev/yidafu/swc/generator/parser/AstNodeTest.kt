package dev.yidafu.swc.generator.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import kotlinx.serialization.json.*

class AstNodeTest : FunSpec({
    
    test("应该从 JSON 字符串创建 AstNode") {
        val json = """{"type": "Module", "body": []}"""
        val node = AstNode.fromJson(json)
        
        node.type shouldBe "Module"
        node.getFieldNames() shouldHaveSize 2
    }
    
    test("应该从 JsonObject 创建 AstNode") {
        val jsonObject = buildJsonObject {
            put("type", "Script")
            put("body", JsonArray(emptyList()))
        }
        val node = AstNode.fromJsonObject(jsonObject)
        
        node.type shouldBe "Script"
        node.toJsonObject() shouldBe jsonObject
    }
    
    test("应该获取字符串字段") {
        val json = """{"type": "Identifier", "value": "test"}"""
        val node = AstNode.fromJson(json)
        
        node.getString("value") shouldBe "test"
        (node.getString("nonexistent") == null) shouldBe true
    }
    
    test("应该获取数字字段") {
        val json = """{"type": "NumericLiteral", "value": 42}"""
        val node = AstNode.fromJson(json)
        
        node.getInt("value") shouldBe 42
        (node.getInt("nonexistent") == null) shouldBe true
    }
    
    test("应该获取布尔字段") {
        val json = """{"type": "BooleanLiteral", "value": true}"""
        val node = AstNode.fromJson(json)
        
        node.getBoolean("value") shouldBe true
        (node.getBoolean("nonexistent") == null) shouldBe true
    }
    
    test("应该获取子节点") {
        val json = """
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "Test"
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val idNode = node.getNode("id")!!
        idNode.type shouldBe "Identifier"
        idNode.getString("value") shouldBe "Test"
    }
    
    test("应该返回 null 当字段不存在时") {
        val json = """{"type": "Module", "body": []}"""
        val node = AstNode.fromJson(json)
        
        (node.getNode("nonexistent") == null) shouldBe true
    }
    
    test("应该返回 null 当字段为 null 时") {
        val json = """{"type": "Module", "body": null}"""
        val node = AstNode.fromJson(json)
        
        (node.getNode("body") == null) shouldBe true
    }
    
    test("应该获取节点数组") {
        val json = """
        {
            "type": "Module",
            "body": [
                {"type": "Identifier", "value": "a"},
                {"type": "Identifier", "value": "b"}
            ]
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val nodes = node.getNodes("body")
        nodes shouldHaveSize 2
        nodes[0].getString("value") shouldBe "a"
        nodes[1].getString("value") shouldBe "b"
    }
    
    test("应该返回空列表当数组字段不存在时") {
        val json = """{"type": "Module"}"""
        val node = AstNode.fromJson(json)
        
        node.getNodes("body").size shouldBe 0
    }
    
    test("应该返回空列表当数组字段为 null 时") {
        val json = """{"type": "Module", "body": null}"""
        val node = AstNode.fromJson(json)
        
        node.getNodes("body").size shouldBe 0
    }
    
    test("应该检查字段是否存在") {
        val json = """{"type": "Module", "body": []}"""
        val node = AstNode.fromJson(json)
        
        node.has("type") shouldBe true
        node.has("body") shouldBe true
        node.has("nonexistent") shouldBe false
    }
    
    test("应该返回 false 当字段为 null 时") {
        val json = """{"type": "Module", "body": null}"""
        val node = AstNode.fromJson(json)
        
        node.has("body") shouldBe false
    }
    
    test("应该获取原始 JSON 数据") {
        val json = """{"type": "Module", "body": [1, 2, 3]}"""
        val node = AstNode.fromJson(json)
        
        val raw = node.getRaw("body")!!
        (raw is JsonArray) shouldBe true
    }
    
    test("应该获取所有字段名") {
        val json = """{"type": "Module", "body": [], "span": {}}"""
        val node = AstNode.fromJson(json)
        
        val fieldNames = node.getFieldNames()
        fieldNames shouldHaveSize 3
        fieldNames shouldBe setOf("type", "body", "span")
    }
    
    test("应该转换为 JSON 对象") {
        val jsonObject = buildJsonObject {
            put("type", "Module")
            put("body", JsonArray(emptyList()))
        }
        val node = AstNode.fromJsonObject(jsonObject)
        
        node.toJsonObject() shouldBe jsonObject
    }
    
    test("应该获取 span 信息") {
        val json = """
        {
            "type": "Module",
            "span": {
                "start": 0,
                "end": 100,
                "ctxt": 0
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val span = node.getSpan()!!
        span.start shouldBe 0
        span.end shouldBe 100
        span.ctxt shouldBe 0
    }
    
    test("应该返回 null 当 span 不存在时") {
        val json = """{"type": "Module"}"""
        val node = AstNode.fromJson(json)
        
        (node.getSpan() == null) shouldBe true
    }
    
    test("应该判断节点类型") {
        val json = """{"type": "Module"}"""
        val node = AstNode.fromJson(json)
        
        node.isType("Module") shouldBe true
        node.isType("Script") shouldBe false
        node.isType("Module", "Script") shouldBe true
    }
    
    test("toString 应该返回类型信息") {
        val json = """{"type": "Module"}"""
        val node = AstNode.fromJson(json)
        
        node.toString() shouldBe "AstNode(type=Module)"
    }
    
    test("应该处理缺少 type 字段的情况") {
        val json = """{"body": []}"""
        val node = AstNode.fromJson(json)
        
        node.type shouldBe "Unknown"
    }
    
    test("应该处理复杂嵌套结构") {
        val json = """
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "Test"
            },
            "body": {
                "type": "TsInterfaceBody",
                "body": [
                    {
                        "type": "TsPropertySignature",
                        "key": {
                            "type": "Identifier",
                            "value": "name"
                        }
                    }
                ]
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        node.type shouldBe "TsInterfaceDeclaration"
        val id = node.getNode("id")!!
        id.getString("value") shouldBe "Test"
        
        val body = node.getNode("body")!!
        val properties = body.getNodes("body")
        properties shouldHaveSize 1
        properties[0].type shouldBe "TsPropertySignature"
    }
})


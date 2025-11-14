package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.adt.result.GeneratorResult
import dev.yidafu.swc.generator.adt.typescript.*
import dev.yidafu.swc.generator.config.GlobalConfig
import dev.yidafu.swc.generator.parser.AstNode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TypeResolverTest : FunSpec({
    
    beforeEach {
        // 初始化全局配置
        GlobalConfig.updateConfig(null)
    }
    
    test("应该解析 null 类型为 Any") {
        val result = TypeResolver.extractTypeScriptType(null)
        
        result.isSuccess() shouldBe true
        (result.getOrNull() is TypeScriptType.Any) shouldBe true
    }
    
    test("应该解析关键字类型 string") {
        val json = """
        {
            "type": "TsKeywordType",
            "kind": "string"
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.Keyword) shouldBe true
        (tsType as TypeScriptType.Keyword).kind shouldBe KeywordKind.STRING
    }
    
    test("应该解析关键字类型 number") {
        val json = """
        {
            "type": "TsKeywordType",
            "kind": "number"
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.Keyword) shouldBe true
        (tsType as TypeScriptType.Keyword).kind shouldBe KeywordKind.NUMBER
    }
    
    test("应该解析关键字类型 boolean") {
        val json = """
        {
            "type": "TsKeywordType",
            "kind": "boolean"
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.Keyword) shouldBe true
        (tsType as TypeScriptType.Keyword).kind shouldBe KeywordKind.BOOLEAN
    }
    
    test("应该解析类型引用") {
        val json = """
        {
            "type": "TsTypeReference",
            "typeName": {
                "type": "Identifier",
                "value": "String"
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.Reference) shouldBe true
        (tsType as TypeScriptType.Reference).name shouldBe "String"
    }
    
    test("应该解析 Union 类型") {
        val json = """
        {
            "type": "TsUnionType",
            "types": [
                {
                    "type": "TsKeywordType",
                    "kind": "string"
                },
                {
                    "type": "TsKeywordType",
                    "kind": "number"
                }
            ]
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.Union) shouldBe true
        val union = tsType as TypeScriptType.Union
        union.types.size shouldBe 2
    }
    
    test("应该解析 Intersection 类型") {
        val json = """
        {
            "type": "TsIntersectionType",
            "types": [
                {
                    "type": "TsTypeReference",
                    "typeName": {
                        "type": "Identifier",
                        "value": "A"
                    }
                },
                {
                    "type": "TsTypeReference",
                    "typeName": {
                        "type": "Identifier",
                        "value": "B"
                    }
                }
            ]
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.Intersection) shouldBe true
        val intersection = tsType as TypeScriptType.Intersection
        intersection.types.size shouldBe 2
    }
    
    test("应该解析数组类型") {
        val json = """
        {
            "type": "TsArrayType",
            "elemType": {
                "type": "TsKeywordType",
                "kind": "string"
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.Array) shouldBe true
        val array = tsType as TypeScriptType.Array
        (array.elementType is TypeScriptType.Keyword) shouldBe true
    }
    
    test("应该解析字面量类型") {
        val json = """
        {
            "type": "TsLiteralType",
            "literal": {
                "type": "StringLiteral",
                "value": "test"
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.Literal) shouldBe true
    }
    
    test("应该解析类型字面量") {
        val json = """
        {
            "type": "TsTypeLiteral",
            "members": [
                {
                    "type": "TsPropertySignature",
                    "key": {
                        "type": "Identifier",
                        "value": "prop"
                    },
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsKeywordType",
                            "kind": "string"
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.TypeLiteral) shouldBe true
    }
    
    test("应该解析类型为字符串") {
        val json = """
        {
            "type": "TsKeywordType",
            "kind": "string"
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val typeString = TypeResolver.resolveType(node)
        
        typeString.isNotEmpty() shouldBe true
    }
    
    test("应该处理未知类型为 Any") {
        val json = """
        {
            "type": "UnknownType"
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result = TypeResolver.extractTypeScriptType(node)
        
        result.isSuccess() shouldBe true
        val tsType = result.getOrNull()!!
        (tsType is TypeScriptType.Any) shouldBe true
    }
    
    test("应该缓存类型解析结果") {
        val json = """
        {
            "type": "TsKeywordType",
            "kind": "string"
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val result1 = TypeResolver.extractTypeScriptType(node)
        val result2 = TypeResolver.extractTypeScriptType(node)
        
        result1.isSuccess() shouldBe true
        result2.isSuccess() shouldBe true
        result1.getOrNull() shouldBe result2.getOrNull()
    }
})


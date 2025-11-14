package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.config.GlobalConfig
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.parser.TsAstVisitor
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldContain

class TypeAliasExtractorTest : FunSpec({
    
    beforeEach {
        // 初始化全局配置
        GlobalConfig.updateConfig(null)
    }
    
    fun createVisitor(json: String): TsAstVisitor {
        return TsAstVisitor(json).apply { visit() }
    }
    
    test("应该判断是否为字面量 Union") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsUnionType",
                            "types": [
                                {
                                    "type": "TsLiteralType",
                                    "literal": {"type": "StringLiteral", "value": "a"}
                                },
                                {
                                    "type": "TsLiteralType",
                                    "literal": {"type": "StringLiteral", "value": "b"}
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val isLiteralUnion = extractor.isLiteralUnion(typeAliases[0])
        
        isLiteralUnion shouldBe true
    }
    
    test("应该判断是否为混合 Union") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsUnionType",
                            "types": [
                                {
                                    "type": "TsLiteralType",
                                    "literal": {"type": "StringLiteral", "value": "a"}
                                },
                                {
                                    "type": "TsKeywordType",
                                    "kind": "string"
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val isMixedUnion = extractor.isMixedUnion(typeAliases[0])
        
        isMixedUnion shouldBe true
    }
    
    test("应该判断是否为引用 Union") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsUnionType",
                            "types": [
                                {
                                    "type": "TsTypeReference",
                                    "typeName": {"type": "Identifier", "value": "A"}
                                },
                                {
                                    "type": "TsTypeReference",
                                    "typeName": {"type": "Identifier", "value": "B"}
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val isRefUnion = extractor.isRefUnion(typeAliases[0])
        
        isRefUnion shouldBe true
    }
    
    test("应该判断是否为 Intersection 类型") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsIntersectionType",
                            "types": [
                                {
                                    "type": "TsTypeReference",
                                    "typeName": {"type": "Identifier", "value": "A"}
                                },
                                {
                                    "type": "TsTypeLiteral",
                                    "members": []
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val isIntersection = extractor.isIntersectionType(typeAliases[0])
        
        isIntersection shouldBe true
    }
    
    test("应该提取字面量 Union 的属性列表") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsUnionType",
                            "types": [
                                {
                                    "type": "TsLiteralType",
                                    "literal": {"type": "StringLiteral", "value": "a"}
                                },
                                {
                                    "type": "TsLiteralType",
                                    "literal": {"type": "StringLiteral", "value": "b"}
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val properties = extractor.extractLiteralUnionProperties(typeAliases[0])
        
        properties shouldHaveSize 2
        properties[0].name.isNotEmpty() shouldBe true
        properties[1].name.isNotEmpty() shouldBe true
    }
    
    test("应该获取 Union 类型的所有类型名称") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsUnionType",
                            "types": [
                                {
                                    "type": "TsTypeReference",
                                    "typeName": {"type": "Identifier", "value": "A"}
                                },
                                {
                                    "type": "TsTypeReference",
                                    "typeName": {"type": "Identifier", "value": "B"}
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val typeNames = extractor.getUnionTypeNames(typeAliases[0])
        
        typeNames shouldHaveSize 2
        typeNames shouldContain "A"
        typeNames shouldContain "B"
    }
    
    test("应该提取 Intersection 类型信息") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsIntersectionType",
                            "types": [
                                {
                                    "type": "TsTypeReference",
                                    "typeName": {"type": "Identifier", "value": "Parent"}
                                },
                                {
                                    "type": "TsTypeLiteral",
                                    "members": []
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val intersectionInfo = extractor.extractIntersectionInfo(typeAliases[0])
        
        intersectionInfo!!.first shouldBe "Parent"
    }
    
    test("应该检查字面量 Union 的所有类型是否相同") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsUnionType",
                            "types": [
                                {
                                    "type": "TsLiteralType",
                                    "literal": {"type": "StringLiteral", "value": "a"}
                                },
                                {
                                    "type": "TsLiteralType",
                                    "literal": {"type": "StringLiteral", "value": "b"}
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val isAllSame = extractor.isAllLiteralTypeSame(typeAliases[0])
        
        isAllSame shouldBe true
    }
    
    test("应该获取类型别名的类型字符串表示") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
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
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val typeString = extractor.getTypeString(typeAliases[0])
        
        typeString.isNotEmpty() shouldBe true
    }
    
    test("应该提取类型别名名称") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
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
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val name = extractor.getTypeAliasName(typeAliases[0])
        
        name shouldBe "TestType"
    }
    
    test("应该处理布尔字面量") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsUnionType",
                            "types": [
                                {
                                    "type": "TsLiteralType",
                                    "literal": {"type": "BooleanLiteral", "value": true}
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val properties = extractor.extractLiteralUnionProperties(typeAliases[0])
        
        properties shouldHaveSize 1
        (properties[0].type is KotlinType.Boolean) shouldBe true
    }
    
    test("应该处理数字字面量") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
                    "typeAnnotation": {
                        "type": "TsTypeAnnotation",
                        "typeAnnotation": {
                            "type": "TsUnionType",
                            "types": [
                                {
                                    "type": "TsLiteralType",
                                    "literal": {"type": "NumericLiteral", "value": 42}
                                }
                            ]
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val properties = extractor.extractLiteralUnionProperties(typeAliases[0])
        
        properties shouldHaveSize 1
        (properties[0].type is KotlinType.Int) shouldBe true
    }
    
    test("应该返回空列表当不是 Union 类型时") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {"type": "Identifier", "value": "TestType"},
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
        
        val visitor = createVisitor(json)
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        
        val extractor = TypeAliasExtractor(visitor)
        val properties = extractor.extractLiteralUnionProperties(typeAliases[0])
        val typeNames = extractor.getUnionTypeNames(typeAliases[0])
        
        properties.size shouldBe 0
        typeNames.size shouldBe 0
    }
})


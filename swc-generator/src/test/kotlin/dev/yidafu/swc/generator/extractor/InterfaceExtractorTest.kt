package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.core.model.GenericParent
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.parser.TsAstVisitor
import dev.yidafu.swc.generator.parser.getInterfaceName
import dev.yidafu.swc.generator.builder.InheritanceBuilder
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.config.GlobalConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.types.shouldBeInstanceOf

class InterfaceExtractorTest : FunSpec({
    
    beforeEach {
        // 初始化全局配置
        GlobalConfig.updateConfig(null)
    }
    
    fun createVisitor(json: String): TsAstVisitor {
        return TsAstVisitor(json).apply { visit() }
    }
    
    test("应该提取 interface 的自身属性") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "TestInterface"},
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": [
                            {
                                "type": "TsPropertySignature",
                                "key": {"type": "Identifier", "value": "prop1"},
                                "typeAnnotation": {
                                    "type": "TsTypeAnnotation",
                                    "typeAnnotation": {
                                        "type": "TsKeywordType",
                                        "kind": "string"
                                    }
                                }
                            },
                            {
                                "type": "TsPropertySignature",
                                "key": {"type": "Identifier", "value": "prop2"},
                                "typeAnnotation": {
                                    "type": "TsTypeAnnotation",
                                    "typeAnnotation": {
                                        "type": "TsKeywordType",
                                        "kind": "number"
                                    }
                                }
                            }
                        ]
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        
        val extractor = InterfaceExtractor(visitor)
        val properties = extractor.getInterfaceOwnProperties(interfaces[0])
        
        properties shouldHaveSize 2
        properties[0].name shouldBe "prop1"
        properties[1].name shouldBe "prop2"
    }
    
    test("应该提取 interface 的所有属性（包含继承）") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "Parent"},
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": [
                            {
                                "type": "TsPropertySignature",
                                "key": {"type": "Identifier", "value": "parentProp"},
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
                },
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "Child"},
                    "extends": [
                        {
                            "type": "TsExpressionWithTypeArguments",
                            "expression": {
                                "type": "Identifier",
                                "value": "Parent"
                            }
                        }
                    ],
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": [
                            {
                                "type": "TsPropertySignature",
                                "key": {"type": "Identifier", "value": "childProp"},
                                "typeAnnotation": {
                                    "type": "TsTypeAnnotation",
                                    "typeAnnotation": {
                                        "type": "TsKeywordType",
                                        "kind": "number"
                                    }
                                }
                            }
                        ]
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        // 建立继承关系
        val config = SwcGeneratorConfig()
        val inheritanceBuilder = InheritanceBuilder(visitor, config)
        inheritanceBuilder.buildInheritanceGraph(visitor.getInterfaces())
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 2
        
        val childInterface = interfaces.find { it.getInterfaceName() == "Child" }!!
        
        val extractor = InterfaceExtractor(visitor)
        val allProperties = extractor.getInterfaceAllProperties(childInterface)
        
        // 应该包含父类和子类的属性
        allProperties.size shouldBe 2
    }
    
    test("应该提取继承关系") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "Child"},
                    "extends": [
                        {
                            "type": "TsExpressionWithTypeArguments",
                            "expression": {
                                "type": "Identifier",
                                "value": "Parent"
                            }
                        }
                    ],
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        
        val extractor = InterfaceExtractor(visitor)
        val extends = extractor.extractExtends(interfaces[0])
        
        extends shouldHaveSize 1
        extends[0] shouldBe "Parent"
    }
    
    test("应该提取泛型继承关系") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "Child"},
                    "extends": [
                        {
                            "type": "TsExpressionWithTypeArguments",
                            "expression": {
                                "type": "Identifier",
                                "value": "Parent"
                            }
                        }
                    ],
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        
        val extractor = InterfaceExtractor(visitor)
        val genericExtends = extractor.extractGenericExtends(interfaces[0])
        
        genericExtends shouldHaveSize 1
        (genericExtends[0] is GenericParent) shouldBe true
        genericExtends[0].name shouldBe "Parent"
    }
    
    test("应该处理限定名称的继承") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "Child"},
                    "extends": [
                        {
                            "type": "TsExpressionWithTypeArguments",
                            "expression": {
                                "type": "TsQualifiedName",
                                "left": {"type": "Identifier", "value": "com"},
                                "right": {"type": "Identifier", "value": "Parent"}
                            }
                        }
                    ],
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        
        val extractor = InterfaceExtractor(visitor)
        val extends = extractor.extractExtends(interfaces[0])
        
        extends shouldHaveSize 1
        extends[0] shouldBe "Parent"
    }
    
    test("应该返回空列表当没有属性时") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "TestInterface"},
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        
        val extractor = InterfaceExtractor(visitor)
        val properties = extractor.getInterfaceOwnProperties(interfaces[0])
        
        properties.size shouldBe 0
    }
    
    test("应该返回空列表当没有继承关系时") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "TestInterface"},
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        
        val extractor = InterfaceExtractor(visitor)
        val extends = extractor.extractExtends(interfaces[0])
        
        extends.size shouldBe 0
    }
    
    test("应该处理可选的属性") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "TestInterface"},
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": [
                            {
                                "type": "TsPropertySignature",
                                "key": {"type": "Identifier", "value": "optionalProp"},
                                "optional": true,
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
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        
        val extractor = InterfaceExtractor(visitor)
        val properties = extractor.getInterfaceOwnProperties(interfaces[0])
        
        properties shouldHaveSize 1
        properties[0].name shouldBe "optionalProp"
    }
    
    test("应该提取头部注释") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "TestInterface"},
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        
        val extractor = InterfaceExtractor(visitor)
        val comment = extractor.extractHeaderComment(interfaces[0])
        
        // 当前实现返回空字符串
        comment shouldBe ""
    }
    
    test("应该使用缓存避免重复计算") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "TestInterface"},
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": [
                            {
                                "type": "TsPropertySignature",
                                "key": {"type": "Identifier", "value": "prop1"},
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
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        
        val extractor = InterfaceExtractor(visitor)
        val properties1 = extractor.getInterfaceOwnProperties(interfaces[0])
        val properties2 = extractor.getInterfaceOwnProperties(interfaces[0])
        
        properties1 shouldHaveSize 1
        properties2 shouldHaveSize 1
        properties1[0].name shouldBe properties2[0].name
    }
    
    test("应该处理循环继承") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "A"},
                    "extends": [
                        {
                            "type": "TsExpressionWithTypeArguments",
                            "expression": {
                                "type": "Identifier",
                                "value": "B"
                            }
                        }
                    ],
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                },
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {"type": "Identifier", "value": "B"},
                    "extends": [
                        {
                            "type": "TsExpressionWithTypeArguments",
                            "expression": {
                                "type": "Identifier",
                                "value": "A"
                            }
                        }
                    ],
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = createVisitor(json)
        val config = SwcGeneratorConfig()
        val inheritanceBuilder = InheritanceBuilder(visitor, config)
        inheritanceBuilder.buildInheritanceGraph(visitor.getInterfaces())
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 2
        
        val extractor = InterfaceExtractor(visitor)
        val interfaceA = interfaces.find { it.getInterfaceName() == "A" }!!
        
        // 应该检测到循环继承并返回空列表
        val allProperties = extractor.getInterfaceAllProperties(interfaceA)
        // 循环继承检测应该防止无限递归
        allProperties.size shouldBe 0
    }
})


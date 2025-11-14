package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.config.ClassModifiersConfig
import dev.yidafu.swc.generator.config.NamingRulesConfig
import dev.yidafu.swc.generator.config.PathsConfig
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.parser.TsAstVisitor
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldContain

class RefUnionProcessorTest : FunSpec({
    
    fun createConfig(): SwcGeneratorConfig {
        return SwcGeneratorConfig(
            classModifiers = ClassModifiersConfig(),
            namingRules = NamingRulesConfig(),
            paths = PathsConfig()
        )
    }
    
    fun createVisitorWithInterfaces(): TsAstVisitor {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {
                        "type": "Identifier",
                        "value": "TypeA"
                    },
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                },
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {
                        "type": "Identifier",
                        "value": "TypeB"
                    },
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                }
            ]
        }
        """.trimIndent()
        return TsAstVisitor(json).apply { visit() }
    }
    
    test("canProcess 应该返回 true 对于引用联合类型") {
        val config = createConfig()
        val visitor = createVisitorWithInterfaces()
        val processor = RefUnionProcessor(config, visitor)
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "UnionType"
            },
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsUnionType",
                    "types": [
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeA"
                            }
                        },
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeB"
                            }
                        }
                    ]
                }
            }
        }
        """.trimIndent())
        
        processor.canProcess(typeAliasNode) shouldBe true
    }
    
    test("canProcess 应该返回 false 对于字面量联合类型") {
        val config = createConfig()
        val visitor = createVisitorWithInterfaces()
        val processor = RefUnionProcessor(config, visitor)
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "LiteralUnion"
            },
            "typeAnnotation": {
                "type": "TsUnionType",
                "types": [
                    {
                        "type": "TsLiteralType",
                        "literal": {
                            "type": "StringLiteral",
                            "value": "a"
                        }
                    },
                    {
                        "type": "TsLiteralType",
                        "literal": {
                            "type": "StringLiteral",
                            "value": "b"
                        }
                    }
                ]
            }
        }
        """.trimIndent())
        
        processor.canProcess(typeAliasNode) shouldBe false
    }
    
    test("canProcess 应该返回 false 对于非联合类型") {
        val config = createConfig()
        val visitor = createVisitorWithInterfaces()
        val processor = RefUnionProcessor(config, visitor)
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "SimpleType"
            },
            "typeAnnotation": {
                "type": "TsTypeReference",
                "typeName": {
                    "type": "Identifier",
                    "value": "TypeA"
                }
            }
        }
        """.trimIndent())
        
        processor.canProcess(typeAliasNode) shouldBe false
    }
    
    test("processLegacy 应该创建 sealed interface") {
        val config = createConfig()
        val visitor = createVisitorWithInterfaces()
        val processor = RefUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "UnionType"
            },
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsUnionType",
                    "types": [
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeA"
                            }
                        },
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeB"
                            }
                        }
                    ]
                }
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        val classDecl = context.getClassDecl("UnionType")!!
        classDecl.name shouldBe "UnionType"
        classDecl.modifier shouldBe ClassModifier.SealedInterface
        classDecl.properties.size shouldBe 0
        classDecl.annotations.any { it.name == "SwcDslMarker" } shouldBe true
        classDecl.annotations.any { it.name == "Serializable" } shouldBe true
    }
    
    test("processLegacy 应该建立继承关系") {
        val config = createConfig()
        val visitor = createVisitorWithInterfaces()
        val processor = RefUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        // 清理之前的继承关系
        ExtendRelationship.clear()
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "UnionType"
            },
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsUnionType",
                    "types": [
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeA"
                            }
                        },
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeB"
                            }
                        }
                    ]
                }
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        val parents = ExtendRelationship.findParentsByChild("TypeA")
        parents.contains("UnionType") shouldBe true
        
        val parentsB = ExtendRelationship.findParentsByChild("TypeB")
        parentsB.contains("UnionType") shouldBe true
    }
    
    test("processLegacy 应该跳过空名称的类型别名") {
        val config = createConfig()
        val visitor = createVisitorWithInterfaces()
        val processor = RefUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "typeAnnotation": {
                "type": "TsUnionType",
                "types": [
                    {
                        "type": "TsTypeReference",
                        "typeName": {
                            "type": "Identifier",
                            "value": "TypeA"
                        }
                    }
                ]
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        context.getAllClassDecls().size shouldBe 0
    }
    
    test("processLegacy 应该处理单个引用类型") {
        val config = createConfig()
        val visitor = createVisitorWithInterfaces()
        val processor = RefUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        ExtendRelationship.clear()
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "SingleUnion"
            },
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsUnionType",
                    "types": [
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeA"
                            }
                        }
                    ]
                }
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        val classDecl = context.getClassDecl("SingleUnion")!!
        
        val parents = ExtendRelationship.findParentsByChild("TypeA")
        parents.contains("SingleUnion") shouldBe true
    }
    
    test("processLegacy 应该处理多个引用类型") {
        val config = createConfig()
        val visitor = createVisitorWithInterfaces()
        val processor = RefUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        ExtendRelationship.clear()
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "MultiUnion"
            },
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsUnionType",
                    "types": [
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeA"
                            }
                        },
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeB"
                            }
                        },
                        {
                            "type": "TsTypeReference",
                            "typeName": {
                                "type": "Identifier",
                                "value": "TypeC"
                            }
                        }
                    ]
                }
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        val classDecl = context.getClassDecl("MultiUnion")!!
        
        // 验证所有子类型都建立了继承关系
        val parentsA = ExtendRelationship.findParentsByChild("TypeA")
        parentsA.contains("MultiUnion") shouldBe true
        
        val parentsB = ExtendRelationship.findParentsByChild("TypeB")
        parentsB.contains("MultiUnion") shouldBe true
        
        val parentsC = ExtendRelationship.findParentsByChild("TypeC")
        parentsC.contains("MultiUnion") shouldBe true
    }
})


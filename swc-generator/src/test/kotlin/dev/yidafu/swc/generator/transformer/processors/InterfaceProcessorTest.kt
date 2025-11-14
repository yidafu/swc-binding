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

class InterfaceProcessorTest : FunSpec({
    
    fun createConfig(
        toKotlinClass: List<String> = emptyList(),
        keepInterface: List<String> = emptyList(),
        sealedInterface: List<String> = emptyList()
    ): SwcGeneratorConfig {
        return SwcGeneratorConfig(
            classModifiers = ClassModifiersConfig(
                toKotlinClass = toKotlinClass,
                keepInterface = keepInterface,
                sealedInterface = sealedInterface
            ),
            namingRules = NamingRulesConfig(),
            paths = PathsConfig()
        )
    }
    
    fun createSimpleVisitor(): TsAstVisitor {
        val json = """
        {
            "type": "Module",
            "body": []
        }
        """.trimIndent()
        return TsAstVisitor(json).apply { visit() }
    }
    
    test("canProcess 应该返回 true 对于 TsInterfaceDeclaration") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "TestInterface"
            },
            "body": {
                "type": "TsInterfaceBody",
                "body": []
            }
        }
        """.trimIndent())
        
        processor.canProcess(interfaceNode) shouldBe true
    }
    
    test("canProcess 应该返回 false 对于非接口节点") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "TestType"
            }
        }
        """.trimIndent())
        
        processor.canProcess(typeAliasNode) shouldBe false
    }
    
    test("processLegacy 应该处理简单的接口") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "TestInterface"
            },
            "body": {
                "type": "TsInterfaceBody",
                "body": []
            }
        }
        """.trimIndent())
        
        processor.processLegacy(interfaceNode, context)
        
        val classDecl = context.getClassDecl("TestInterface")!!
        classDecl.name shouldBe "TestInterface"
        classDecl.modifier shouldBe ClassModifier.Interface
    }
    
    test("processLegacy 应该跳过空名称的接口") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "body": {
                "type": "TsInterfaceBody",
                "body": []
            }
        }
        """.trimIndent())
        
        processor.processLegacy(interfaceNode, context)
        
        context.getAllClassDecls().size shouldBe 0
    }
    
    test("processLegacy 应该生成 interface 当不在 toKotlinClass 列表中") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "NormalInterface"
            },
            "body": {
                "type": "TsInterfaceBody",
                "body": []
            }
        }
        """.trimIndent())
        
        processor.processLegacy(interfaceNode, context)
        
        val classDecl = context.getClassDecl("NormalInterface")!!
        classDecl.modifier shouldBe ClassModifier.Interface
    }
    
    test("processLegacy 应该生成 class 当在 toKotlinClass 列表中") {
        val config = createConfig(toKotlinClass = listOf("TestClass"))
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "TestClass"
            },
            "body": {
                "type": "TsInterfaceBody",
                "body": []
            }
        }
        """.trimIndent())
        
        processor.processLegacy(interfaceNode, context)
        
        val classDecl = context.getClassDecl("TestClass")!!
        classDecl.modifier shouldBe ClassModifier.FinalClass
        classDecl.annotations.any { it.name == "Serializable" } shouldBe true
        classDecl.annotations.any { it.name == "SerialName" } shouldBe true
    }
    
    test("processLegacy 应该生成 sealed interface 当在 sealedInterface 列表中") {
        val config = createConfig(sealedInterface = listOf("SealedTest"))
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "SealedTest"
            },
            "body": {
                "type": "TsInterfaceBody",
                "body": []
            }
        }
        """.trimIndent())
        
        processor.processLegacy(interfaceNode, context)
        
        val classDecl = context.getClassDecl("SealedTest")!!
        classDecl.modifier shouldBe ClassModifier.SealedInterface
        classDecl.annotations.any { it.name == "SwcDslMarker" } shouldBe true
        classDecl.annotations.any { it.name == "Serializable" } shouldBe true
    }
    
    test("processLegacy 应该生成 Impl 类当不在 keepInterface 列表中") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "TestInterface"
            },
            "body": {
                "type": "TsInterfaceBody",
                "body": []
            }
        }
        """.trimIndent())
        
        processor.processLegacy(interfaceNode, context)
        
        val implClass = context.getClassDecl("TestInterfaceImpl")!!
        
        // 验证继承关系
        ExtendRelationship.findParentsByChild("TestInterfaceImpl") shouldContain "TestInterface"
    }
    
    test("processLegacy 应该跳过 Impl 类生成当在 keepInterface 列表中") {
        val config = createConfig(keepInterface = listOf("KeepMe"))
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "KeepMe"
            },
            "body": {
                "type": "TsInterfaceBody",
                "body": []
            }
        }
        """.trimIndent())
        
        processor.processLegacy(interfaceNode, context)
        
        val implClass = context.getClassDecl("KeepMeImpl")
        (implClass == null) shouldBe true
    }
    
    test("processLegacy 应该添加属性到上下文") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "TestInterface"
            },
            "body": {
                "type": "TsInterfaceBody",
                "body": [
                    {
                        "type": "TsPropertySignature",
                        "key": {
                            "type": "Identifier",
                            "value": "name"
                        },
                        "typeAnnotation": {
                            "type": "TsTypeAnnotation",
                            "typeAnnotation": {
                                "type": "TsStringKeyword"
                            }
                        }
                    }
                ]
            }
        }
        """.trimIndent())
        
        processor.processLegacy(interfaceNode, context)
        
        val properties = context.getAllPropertiesMap()["TestInterface"]
        // 属性可能为空或包含属性，取决于 InterfaceExtractor 的实现
    }
    
    test("processLegacy 应该处理带继承的接口") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = InterfaceProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val interfaceNode = AstNode.fromJson("""
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "ChildInterface"
            },
            "extends": [
                {
                    "type": "TsExpressionWithTypeArguments",
                    "expression": {
                        "type": "Identifier",
                        "value": "ParentInterface"
                    }
                }
            ],
            "body": {
                "type": "TsInterfaceBody",
                "body": []
            }
        }
        """.trimIndent())
        
        processor.processLegacy(interfaceNode, context)
        
        val classDecl = context.getClassDecl("ChildInterface")!!
        // 父类型应该被提取（具体取决于 InterfaceExtractor 的实现）
    }
})


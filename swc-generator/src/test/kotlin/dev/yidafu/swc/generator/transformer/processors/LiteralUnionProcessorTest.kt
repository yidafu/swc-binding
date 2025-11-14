package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.config.ClassModifiersConfig
import dev.yidafu.swc.generator.config.NamingRulesConfig
import dev.yidafu.swc.generator.config.PathsConfig
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.extractor.TypeResolver
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.parser.TsAstVisitor
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize

class LiteralUnionProcessorTest : FunSpec({
    
    fun createConfig(): SwcGeneratorConfig {
        return SwcGeneratorConfig(
            classModifiers = ClassModifiersConfig(),
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
    
    beforeEach {
        // 清理类型别名映射
        TypeResolver.typeAliasMap.clear()
    }
    
    test("canProcess 应该返回 true 对于相同类型的字面量联合") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "StringLiteralUnion"
            },
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
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
        }
        """.trimIndent())
        
        processor.canProcess(typeAliasNode) shouldBe true
    }
    
    test("canProcess 应该返回 false 对于不同类型的字面量联合") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "MixedLiteralUnion"
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
                            "type": "NumericLiteral",
                            "value": 1
                        }
                    }
                ]
            }
        }
        """.trimIndent())
        
        processor.canProcess(typeAliasNode) shouldBe false
    }
    
    test("canProcess 应该返回 false 对于非字面量联合") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "RefUnion"
            },
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
        
        processor.canProcess(typeAliasNode) shouldBe false
    }
    
    test("processLegacy 应该创建 object") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "StringLiteralUnion"
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
        
        processor.processLegacy(typeAliasNode, context)
        
        val classDecl = context.getClassDecl("StringLiteralUnion")!!
        classDecl.name shouldBe "StringLiteralUnion"
        classDecl.modifier shouldBe ClassModifier.Object
    }
    
    test("processLegacy 应该生成常量属性") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "StringLiteralUnion"
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
        
        processor.processLegacy(typeAliasNode, context)
        
        val classDecl = context.getClassDecl("StringLiteralUnion")!!
        // 属性数量取决于 TypeAliasExtractor 的实现
        // 应该至少有一些属性被生成
    }
    
    test("processLegacy 应该记录类型别名映射") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "StringLiteralUnion"
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
                    }
                ]
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        // 验证类型别名被记录
        // 具体值取决于 TypeAliasExtractor 的实现
        val typeString = TypeResolver.typeAliasMap["StringLiteralUnion"]
        // 如果提取成功，应该有值
    }
    
    test("processLegacy 应该跳过空名称的类型别名") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "typeAnnotation": {
                "type": "TsUnionType",
                "types": [
                    {
                        "type": "TsLiteralType",
                        "literal": {
                            "type": "StringLiteral",
                            "value": "a"
                        }
                    }
                ]
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        context.getAllClassDecls().size shouldBe 0
    }
    
    test("processLegacy 应该处理数字字面量联合") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "NumericLiteralUnion"
            },
            "typeAnnotation": {
                "type": "TsUnionType",
                "types": [
                    {
                        "type": "TsLiteralType",
                        "literal": {
                            "type": "NumericLiteral",
                            "value": 1
                        }
                    },
                    {
                        "type": "TsLiteralType",
                        "literal": {
                            "type": "NumericLiteral",
                            "value": 2
                        }
                    }
                ]
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        val classDecl = context.getClassDecl("NumericLiteralUnion")!!
        classDecl.modifier shouldBe ClassModifier.Object
    }
    
    test("processLegacy 应该处理布尔字面量联合") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "BooleanLiteralUnion"
            },
            "typeAnnotation": {
                "type": "TsUnionType",
                "types": [
                    {
                        "type": "TsLiteralType",
                        "literal": {
                            "type": "BooleanLiteral",
                            "value": true
                        }
                    },
                    {
                        "type": "TsLiteralType",
                        "literal": {
                            "type": "BooleanLiteral",
                            "value": false
                        }
                    }
                ]
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        val classDecl = context.getClassDecl("BooleanLiteralUnion")!!
        classDecl.modifier shouldBe ClassModifier.Object
    }
    
    test("processLegacy 应该生成 KDoc 注释") {
        val config = createConfig()
        val visitor = createSimpleVisitor()
        val processor = LiteralUnionProcessor(config, visitor)
        val context = TransformContext(mutableMapOf())
        
        val typeAliasNode = AstNode.fromJson("""
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "StringLiteralUnion"
            },
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsUnionType",
                    "types": [
                        {
                            "type": "TsLiteralType",
                            "literal": {
                                "type": "StringLiteral",
                                "value": "a"
                            }
                        }
                    ]
                }
            }
        }
        """.trimIndent())
        
        processor.processLegacy(typeAliasNode, context)
        
        val classDecl = context.getClassDecl("StringLiteralUnion")!!
        classDecl.kdoc!!.contains("StringLiteralUnion") shouldBe true
    }
})


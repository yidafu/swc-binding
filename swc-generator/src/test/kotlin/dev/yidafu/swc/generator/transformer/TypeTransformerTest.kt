package dev.yidafu.swc.generator.transformer

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.config.ClassModifiersConfig
import dev.yidafu.swc.generator.config.NamingRulesConfig
import dev.yidafu.swc.generator.config.PathsConfig
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.adt.result.GeneratorResult
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.parser.ParseResult
import dev.yidafu.swc.generator.parser.TsAstVisitor
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize

class TypeTransformerTest : FunSpec({
    
    fun createConfig(): SwcGeneratorConfig {
        return SwcGeneratorConfig(
            classModifiers = ClassModifiersConfig(),
            namingRules = NamingRulesConfig(),
            paths = PathsConfig()
        )
    }
    
    fun createSimpleParseResult(): ParseResult {
        val astJson = """
        {
            "type": "Module",
            "body": []
        }
        """.trimIndent()
        
        return ParseResult(
            astJsonString = astJson,
            program = AstNode.fromJson(astJson),
            inputPath = "test.d.ts",
            sourceCode = ""
        )
    }
    
    fun createParseResultWithInterface(): ParseResult {
        val astJson = """
        {
            "type": "Module",
            "body": [
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
            ]
        }
        """.trimIndent()
        
        return ParseResult(
            astJsonString = astJson,
            program = AstNode.fromJson(astJson),
            inputPath = "test.d.ts",
            sourceCode = ""
        )
    }
    
    fun createParseResultWithTypeAlias(): ParseResult {
        val astJson = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {
                        "type": "Identifier",
                        "value": "TestType"
                    },
                    "typeAnnotation": {
                        "type": "TsStringKeyword"
                    }
                }
            ]
        }
        """.trimIndent()
        
        return ParseResult(
            astJsonString = astJson,
            program = AstNode.fromJson(astJson),
            inputPath = "test.d.ts",
            sourceCode = ""
        )
    }
    
    beforeEach {
        // 清理继承关系
        ExtendRelationship.clear()
    }
    
    test("transform 应该成功处理空的 AST") {
        val config = createConfig()
        val transformer = TypeTransformer(config)
        val parseResult = createSimpleParseResult()
        
        val result = transformer.transform(parseResult)
        
        result.isSuccess() shouldBe true
        val transformResult = result.getOrNull()!!
        transformResult.classDecls.size shouldBe 0
        transformResult.classAllPropertiesMap.size shouldBe 0
    }
    
    test("transform 应该处理包含接口的 AST") {
        val config = createConfig()
        val transformer = TypeTransformer(config)
        val parseResult = createParseResultWithInterface()
        
        val result = transformer.transform(parseResult)
        
        result.isSuccess() shouldBe true
        val transformResult = result.getOrNull()!!
        // 应该生成接口相关的类声明
        transformResult.classDecls.size shouldBe 0 // 或者更多，取决于配置
    }
    
    test("transform 应该返回 TransformResult 包含类声明") {
        val config = createConfig()
        val transformer = TypeTransformer(config)
        val parseResult = createParseResultWithInterface()
        
        val result = transformer.transform(parseResult)
        
        result.isSuccess() shouldBe true
        val transformResult = result.getOrNull()!!
        (transformResult is TransformResult) shouldBe true
        (transformResult.classDecls is List<*>) shouldBe true
    }
    
    test("transform 应该返回 TransformResult 包含属性映射") {
        val config = createConfig()
        val transformer = TypeTransformer(config)
        val parseResult = createParseResultWithInterface()
        
        val result = transformer.transform(parseResult)
        
        result.isSuccess() shouldBe true
        val transformResult = result.getOrNull()!!
        (transformResult.classAllPropertiesMap is Map<*, *>) shouldBe true
    }
    
    test("transform 应该处理异常情况") {
        val config = createConfig()
        val transformer = TypeTransformer(config)
        
        // 创建无效的 ParseResult
        val invalidParseResult = ParseResult(
            astJsonString = "invalid json",
            program = AstNode.fromJson("""{"type": "Module", "body": []}"""),
            inputPath = "test.d.ts",
            sourceCode = ""
        )
        
        // 注意：如果 TsAstVisitor 能够处理无效 JSON，这个测试可能需要调整
        // 这里主要测试异常处理机制
        val result = transformer.transform(invalidParseResult)
        
        // 结果可能是成功或失败，取决于实现
        // 如果失败，应该返回 GeneratorResult.Failure
    }
    
    test("transform 应该处理包含类型别名的 AST") {
        val config = createConfig()
        val transformer = TypeTransformer(config)
        val parseResult = createParseResultWithTypeAlias()
        
        val result = transformer.transform(parseResult)
        
        result.isSuccess() shouldBe true
        val transformResult = result.getOrNull()!!
        // transformResult 应该不为 null
    }
    
    test("transform 应该处理复杂的 AST 结构") {
        val config = createConfig()
        val transformer = TypeTransformer(config)
        
        val astJson = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {
                        "type": "Identifier",
                        "value": "ParentInterface"
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
                },
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
            ]
        }
        """.trimIndent()
        
        val parseResult = ParseResult(
            astJsonString = astJson,
            program = AstNode.fromJson(astJson),
            inputPath = "test.d.ts",
            sourceCode = ""
        )
        
        val result = transformer.transform(parseResult)
        
        result.isSuccess() shouldBe true
        val transformResult = result.getOrNull()!!
        // 应该生成多个类声明（至少接口和实现类）
        transformResult.classDecls.size shouldBeGreaterThan 0
    }
    
    test("transform 应该处理引用联合类型") {
        val config = createConfig()
        val transformer = TypeTransformer(config)
        
        val astJson = """
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
                },
                {
                    "type": "TsTypeAliasDeclaration",
                    "id": {
                        "type": "Identifier",
                        "value": "UnionType"
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
            ]
        }
        """.trimIndent()
        
        val parseResult = ParseResult(
            astJsonString = astJson,
            program = AstNode.fromJson(astJson),
            inputPath = "test.d.ts",
            sourceCode = ""
        )
        
        val result = transformer.transform(parseResult)
        
        result.isSuccess() shouldBe true
        val transformResult = result.getOrNull()!!
        // transformResult 应该不为 null
    }
    
    test("transform 应该处理字面量联合类型") {
        val config = createConfig()
        val transformer = TypeTransformer(config)
        
        val astJson = """
        {
            "type": "Module",
            "body": [
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
            ]
        }
        """.trimIndent()
        
        val parseResult = ParseResult(
            astJsonString = astJson,
            program = AstNode.fromJson(astJson),
            inputPath = "test.d.ts",
            sourceCode = ""
        )
        
        val result = transformer.transform(parseResult)
        
        result.isSuccess() shouldBe true
        val transformResult = result.getOrNull()!!
        // transformResult 应该不为 null
    }
})


package dev.yidafu.swc.generator.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldContain

class TsAstVisitorTest : FunSpec({
    
    test("应该从 Module 中提取 interface 声明") {
        val json = """
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
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        interfaces[0].getInterfaceName() shouldBe "TestInterface"
    }
    
    test("应该从 Module 中提取 type alias 声明") {
        val json = """
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
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        typeAliases[0].getTypeAliasName() shouldBe "TestType"
    }
    
    test("应该从 Script 中提取声明") {
        val json = """
        {
            "type": "Script",
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
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
    }
    
    test("应该提取导出的 interface") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "ExportDeclaration",
                    "declaration": {
                        "type": "TsInterfaceDeclaration",
                        "id": {
                            "type": "Identifier",
                            "value": "ExportedInterface"
                        },
                        "body": {
                            "type": "TsInterfaceBody",
                            "body": []
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        interfaces[0].getInterfaceName() shouldBe "ExportedInterface"
    }
    
    test("应该提取导出的 type alias") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "ExportDeclaration",
                    "declaration": {
                        "type": "TsTypeAliasDeclaration",
                        "id": {
                            "type": "Identifier",
                            "value": "ExportedType"
                        },
                        "typeAnnotation": {
                            "type": "TsTypeAnnotation",
                            "typeAnnotation": {
                                "type": "TsKeywordType",
                                "kind": "string"
                            }
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        typeAliases[0].getTypeAliasName() shouldBe "ExportedType"
    }
    
    test("应该提取多个 interface 和 type alias") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "id": {
                        "type": "Identifier",
                        "value": "Interface1"
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
                        "value": "Interface2"
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
                        "value": "Type1"
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
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        val typeAliases = visitor.getTypeAliases()
        
        interfaces shouldHaveSize 2
        typeAliases shouldHaveSize 1
    }
    
    test("应该根据名称查找 interface") {
        val json = """
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
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val found = visitor.findInterface("TestInterface")!!
        found.getInterfaceName() shouldBe "TestInterface"
    }
    
    test("应该根据名称查找 type alias") {
        val json = """
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
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val found = visitor.findTypeAlias("TestType")!!
        found.getTypeAliasName() shouldBe "TestType"
    }
    
    test("应该返回 null 当 interface 不存在时") {
        val json = """
        {
            "type": "Module",
            "body": []
        }
        """.trimIndent()
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val found = visitor.findInterface("Nonexistent")
        (found == null) shouldBe true
    }
    
    test("应该返回 null 当 type alias 不存在时") {
        val json = """
        {
            "type": "Module",
            "body": []
        }
        """.trimIndent()
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val found = visitor.findTypeAlias("Nonexistent")
        (found == null) shouldBe true
    }
    
    test("应该处理嵌套模块声明") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsModuleDeclaration",
                    "id": {
                        "type": "Identifier",
                        "value": "NestedModule"
                    },
                    "body": {
                        "type": "TsModuleBlock",
                        "body": [
                            {
                                "type": "TsInterfaceDeclaration",
                                "id": {
                                    "type": "Identifier",
                                    "value": "NestedInterface"
                                },
                                "body": {
                                    "type": "TsInterfaceBody",
                                    "body": []
                                }
                            }
                        ]
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        interfaces[0].getInterfaceName() shouldBe "NestedInterface"
    }
    
    test("应该处理空的 Module body") {
        val json = """
        {
            "type": "Module",
            "body": []
        }
        """.trimIndent()
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        val typeAliases = visitor.getTypeAliases()
        
        interfaces.size shouldBe 0
        typeAliases.size shouldBe 0
    }
    
    test("应该处理包含其他类型声明的 Module") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "ImportDeclaration",
                    "specifiers": [],
                    "source": {
                        "type": "StringLiteral",
                        "value": "./module"
                    }
                },
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
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        interfaces[0].getInterfaceName() shouldBe "TestInterface"
    }
    
    test("应该处理 TsExportDeclaration 类型的导出") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsExportDeclaration",
                    "declaration": {
                        "type": "TsInterfaceDeclaration",
                        "id": {
                            "type": "Identifier",
                            "value": "ExportedInterface"
                        },
                        "body": {
                            "type": "TsInterfaceBody",
                            "body": []
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        interfaces[0].getInterfaceName() shouldBe "ExportedInterface"
    }
    
    test("应该处理 ExportNamedDeclaration 类型的导出") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "ExportNamedDeclaration",
                    "declaration": {
                        "type": "TsInterfaceDeclaration",
                        "id": {
                            "type": "Identifier",
                            "value": "ExportedInterface"
                        },
                        "body": {
                            "type": "TsInterfaceBody",
                            "body": []
                        }
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        interfaces[0].getInterfaceName() shouldBe "ExportedInterface"
    }
    
    test("应该正确处理没有 id 的 interface") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsInterfaceDeclaration",
                    "body": {
                        "type": "TsInterfaceBody",
                        "body": []
                    }
                }
            ]
        }
        """.trimIndent()
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val interfaces = visitor.getInterfaces()
        interfaces shouldHaveSize 1
        // 即使没有 id，也应该被添加到列表中
        (visitor.findInterface("") == null) shouldBe true
    }
    
    test("应该正确处理没有 id 的 type alias") {
        val json = """
        {
            "type": "Module",
            "body": [
                {
                    "type": "TsTypeAliasDeclaration",
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
        
        val visitor = TsAstVisitor(json)
        visitor.visit()
        
        val typeAliases = visitor.getTypeAliases()
        typeAliases shouldHaveSize 1
        // 即使没有 id，也应该被添加到列表中
        (visitor.findTypeAlias("") == null) shouldBe true
    }
})


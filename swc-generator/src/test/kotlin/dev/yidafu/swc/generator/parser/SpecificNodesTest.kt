package dev.yidafu.swc.generator.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SpecificNodesTest : FunSpec({
    
    test("应该创建 Module 类型的 ProgramNode") {
        val json = """
        {
            "type": "Module",
            "body": []
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val programNode = node.asProgramNode()!!
        
        programNode.type shouldBe "Module"
        programNode.isModule() shouldBe true
        programNode.isScript() shouldBe false
    }
    
    test("应该创建 Script 类型的 ProgramNode") {
        val json = """
        {
            "type": "Script",
            "body": []
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val programNode = node.asProgramNode()!!
        
        programNode.type shouldBe "Script"
        programNode.isModule() shouldBe false
        programNode.isScript() shouldBe true
    }
    
    test("应该返回 null 当节点不是 Program 类型时") {
        val json = """{"type": "Identifier", "value": "test"}"""
        val node = AstNode.fromJson(json)
        val programNode = node.asProgramNode()
        
        (programNode == null) shouldBe true
    }
    
    test("应该遍历所有 body 项") {
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
        val programNode = node.asProgramNode()!!
        
        val items = mutableListOf<String>()
        programNode.forEachItem { items.add(it.getString("value") ?: "") }
        
        items shouldBe listOf("a", "b")
    }
    
    test("应该创建 InterfaceNode") {
        val json = """
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
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val interfaceNode = node.asInterfaceNode()!!
        
        interfaceNode.id shouldBe "TestInterface"
    }
    
    test("应该获取接口的所有属性") {
        val json = """
        {
            "type": "TsInterfaceDeclaration",
            "id": {"type": "Identifier", "value": "Test"},
            "body": {
                "type": "TsInterfaceBody",
                "body": [
                    {"type": "TsPropertySignature", "key": {"type": "Identifier", "value": "prop1"}},
                    {"type": "TsPropertySignature", "key": {"type": "Identifier", "value": "prop2"}}
                ]
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val interfaceNode = node.asInterfaceNode()!!
        
        val properties = interfaceNode.getProperties()
        properties.size shouldBe 2
    }
    
    test("应该获取接口的 extends 名称") {
        val json = """
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
            ]
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val interfaceNode = node.asInterfaceNode()!!
        
        val extendsNames = interfaceNode.getExtendsNames()
        extendsNames.size shouldBe 1
        extendsNames[0] shouldBe "Parent"
    }
    
    test("应该获取限定名称的 extends") {
        val json = """
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
            ]
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val interfaceNode = node.asInterfaceNode()!!
        
        val extendsNames = interfaceNode.getExtendsNames()
        extendsNames.size shouldBe 1
        extendsNames[0] shouldBe "Parent"
    }
    
    test("应该检查 declare 标志") {
        val json = """
        {
            "type": "TsInterfaceDeclaration",
            "id": {"type": "Identifier", "value": "Test"},
            "declare": true
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val interfaceNode = node.asInterfaceNode()!!
        
        interfaceNode.declare shouldBe true
    }
    
    test("应该遍历所有属性") {
        val json = """
        {
            "type": "TsInterfaceDeclaration",
            "id": {"type": "Identifier", "value": "Test"},
            "body": {
                "type": "TsInterfaceBody",
                "body": [
                    {"type": "TsPropertySignature", "key": {"type": "Identifier", "value": "a"}}
                ]
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val interfaceNode = node.asInterfaceNode()!!
        
        val properties = mutableListOf<String>()
        interfaceNode.forEachProperty { 
            properties.add(it.getPropertyName() ?: "")
        }
        
        properties shouldBe listOf("a")
    }
    
    test("应该创建 TypeAliasNode") {
        val json = """
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
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val typeAliasNode = node.asTypeAliasNode()!!
        
        typeAliasNode.id shouldBe "TestType"
    }
    
    test("应该判断是否为 Union Type") {
        val json = """
        {
            "type": "TsTypeAliasDeclaration",
            "id": {"type": "Identifier", "value": "Test"},
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsUnionType",
                    "types": []
                }
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val typeAliasNode = node.asTypeAliasNode()!!
        
        typeAliasNode.isUnionType() shouldBe true
        typeAliasNode.isIntersectionType() shouldBe false
    }
    
    test("应该判断是否为 Intersection Type") {
        val json = """
        {
            "type": "TsTypeAliasDeclaration",
            "id": {"type": "Identifier", "value": "Test"},
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsIntersectionType",
                    "types": []
                }
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val typeAliasNode = node.asTypeAliasNode()!!
        
        typeAliasNode.isIntersectionType() shouldBe true
        typeAliasNode.isUnionType() shouldBe false
    }
    
    test("应该判断是否为 Literal Union") {
        val json = """
        {
            "type": "TsTypeAliasDeclaration",
            "id": {"type": "Identifier", "value": "Test"},
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
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val typeAliasNode = node.asTypeAliasNode()!!
        
        typeAliasNode.isLiteralUnion() shouldBe true
    }
    
    test("应该获取 Union Type 的所有类型") {
        val json = """
        {
            "type": "TsTypeAliasDeclaration",
            "id": {"type": "Identifier", "value": "Test"},
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsUnionType",
                    "types": [
                        {"type": "TsKeywordType", "kind": "string"},
                        {"type": "TsKeywordType", "kind": "number"}
                    ]
                }
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val typeAliasNode = node.asTypeAliasNode()!!
        
        val types = typeAliasNode.getUnionTypes()
        types.size shouldBe 2
    }
    
    test("应该获取 Intersection Type 的所有类型") {
        val json = """
        {
            "type": "TsTypeAliasDeclaration",
            "id": {"type": "Identifier", "value": "Test"},
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsIntersectionType",
                    "types": [
                        {"type": "TsTypeReference", "typeName": {"type": "Identifier", "value": "A"}},
                        {"type": "TsTypeReference", "typeName": {"type": "Identifier", "value": "B"}}
                    ]
                }
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val typeAliasNode = node.asTypeAliasNode()!!
        
        val types = typeAliasNode.getIntersectionTypes()
        types.size shouldBe 2
    }
    
    test("应该创建 PropertySignatureNode") {
        val json = """
        {
            "type": "TsPropertySignature",
            "key": {
                "type": "Identifier",
                "value": "propertyName"
            },
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsKeywordType",
                    "kind": "string"
                }
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val propertyNode = node.asPropertySignatureNode()!!
        
        propertyNode.getName() shouldBe "propertyName"
    }
    
    test("应该获取字符串字面量名称") {
        val json = """
        {
            "type": "TsPropertySignature",
            "key": {
                "type": "StringLiteral",
                "value": "property-name"
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val propertyNode = node.asPropertySignatureNode()!!
        
        propertyNode.getName() shouldBe "property-name"
    }
    
    test("应该检查 optional 标志") {
        val json = """
        {
            "type": "TsPropertySignature",
            "key": {"type": "Identifier", "value": "prop"},
            "optional": true
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val propertyNode = node.asPropertySignatureNode()!!
        
        propertyNode.optional shouldBe true
    }
    
    test("应该检查 readonly 标志") {
        val json = """
        {
            "type": "TsPropertySignature",
            "key": {"type": "Identifier", "value": "prop"},
            "readonly": true
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val propertyNode = node.asPropertySignatureNode()!!
        
        propertyNode.readonly shouldBe true
    }
    
    test("应该获取类型注解节点") {
        val json = """
        {
            "type": "TsPropertySignature",
            "key": {"type": "Identifier", "value": "prop"},
            "typeAnnotation": {
                "type": "TsTypeAnnotation",
                "typeAnnotation": {
                    "type": "TsKeywordType",
                    "kind": "string"
                }
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val propertyNode = node.asPropertySignatureNode()!!
        
        val typeAnnotation = propertyNode.getTypeAnnotationNode()!!
        typeAnnotation.type shouldBe "TsKeywordType"
    }
    
    test("应该创建 ExportDeclarationNode") {
        val json = """
        {
            "type": "ExportDeclaration",
            "declaration": {
                "type": "TsInterfaceDeclaration",
                "id": {"type": "Identifier", "value": "Test"}
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val exportNode = node.asExportDeclarationNode()!!
        
        // exportNode 应该不为 null
    }
    
    test("应该判断是否导出 Interface") {
        val json = """
        {
            "type": "ExportDeclaration",
            "declaration": {
                "type": "TsInterfaceDeclaration",
                "id": {"type": "Identifier", "value": "Test"}
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val exportNode = node.asExportDeclarationNode()!!
        
        exportNode.isExportInterface() shouldBe true
        exportNode.isExportTypeAlias() shouldBe false
    }
    
    test("应该判断是否导出 Type Alias") {
        val json = """
        {
            "type": "ExportDeclaration",
            "declaration": {
                "type": "TsTypeAliasDeclaration",
                "id": {"type": "Identifier", "value": "Test"}
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val exportNode = node.asExportDeclarationNode()!!
        
        exportNode.isExportTypeAlias() shouldBe true
        exportNode.isExportInterface() shouldBe false
    }
    
    test("应该创建 TypeAnnotationNode") {
        val json = """
        {
            "type": "TsTypeAnnotation",
            "typeAnnotation": {
                "type": "TsKeywordType",
                "kind": "string"
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        val typeAnnotationNode = node.asTypeAnnotationNode()!!
        
        val actualType = typeAnnotationNode.getActualType()!!
        actualType.type shouldBe "TsKeywordType"
    }
    
    test("应该返回 null 当节点不是 TsTypeAnnotation 时") {
        val json = """{"type": "Identifier", "value": "test"}"""
        val node = AstNode.fromJson(json)
        val typeAnnotationNode = node.asTypeAnnotationNode()
        
        (typeAnnotationNode == null) shouldBe true
    }
})

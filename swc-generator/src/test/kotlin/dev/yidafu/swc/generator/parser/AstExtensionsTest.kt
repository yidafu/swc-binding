package dev.yidafu.swc.generator.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AstExtensionsTest : FunSpec({
    
    test("应该获取 Identifier 节点的 value") {
        val json = """{"type": "Identifier", "value": "test"}"""
        val node = AstNode.fromJson(json)
        
        node.getIdentifierValue() shouldBe "test"
    }
    
    test("应该返回 null 当节点不是 Identifier 时") {
        val json = """{"type": "StringLiteral", "value": "test"}"""
        val node = AstNode.fromJson(json)
        
        (node.getIdentifierValue() == null) shouldBe true
    }
    
    test("应该获取 StringLiteral 节点的 value") {
        val json = """{"type": "StringLiteral", "value": "hello"}"""
        val node = AstNode.fromJson(json)
        
        node.getStringLiteralValue() shouldBe "hello"
    }
    
    test("应该获取 NumericLiteral 节点的 value") {
        val json = """{"type": "NumericLiteral", "value": 42.5}"""
        val node = AstNode.fromJson(json)
        
        node.getNumericLiteralValue() shouldBe 42.5
    }
    
    test("应该获取 BooleanLiteral 节点的 value") {
        val json = """{"type": "BooleanLiteral", "value": true}"""
        val node = AstNode.fromJson(json)
        
        node.getBooleanLiteralValue() shouldBe true
    }
    
    test("应该判断是否为 Interface 声明") {
        val json = """{"type": "TsInterfaceDeclaration"}"""
        val node = AstNode.fromJson(json)
        
        node.isInterface() shouldBe true
    }
    
    test("应该判断是否为 Type Alias 声明") {
        val json = """{"type": "TsTypeAliasDeclaration"}"""
        val node = AstNode.fromJson(json)
        
        node.isTypeAlias() shouldBe true
    }
    
    test("应该判断是否为 Union Type") {
        val json = """{"type": "TsUnionType"}"""
        val node = AstNode.fromJson(json)
        
        node.isUnionType() shouldBe true
    }
    
    test("应该判断是否为 Intersection Type") {
        val json = """{"type": "TsIntersectionType"}"""
        val node = AstNode.fromJson(json)
        
        node.isIntersectionType() shouldBe true
    }
    
    test("应该判断是否为 Literal Type") {
        val json = """{"type": "TsLiteralType"}"""
        val node = AstNode.fromJson(json)
        
        node.isLiteralType() shouldBe true
    }
    
    test("应该判断是否为 Type Reference") {
        val json = """{"type": "TsTypeReference"}"""
        val node = AstNode.fromJson(json)
        
        node.isTypeReference() shouldBe true
    }
    
    test("应该判断是否为 Type Literal") {
        val json = """{"type": "TsTypeLiteral"}"""
        val node = AstNode.fromJson(json)
        
        node.isTypeLiteral() shouldBe true
    }
    
    test("应该判断是否为 Keyword Type") {
        val json = """{"type": "TsKeywordType"}"""
        val node = AstNode.fromJson(json)
        
        node.isKeywordType() shouldBe true
    }
    
    test("应该判断是否为 Array Type") {
        val json = """{"type": "TsArrayType"}"""
        val node = AstNode.fromJson(json)
        
        node.isArrayType() shouldBe true
    }
    
    test("应该判断是否为 Identifier") {
        val json = """{"type": "Identifier"}"""
        val node = AstNode.fromJson(json)
        
        node.isIdentifier() shouldBe true
    }
    
    test("应该判断是否为 String Literal") {
        val json = """{"type": "StringLiteral"}"""
        val node = AstNode.fromJson(json)
        
        node.isStringLiteral() shouldBe true
    }
    
    test("应该判断是否为 Numeric Literal") {
        val json = """{"type": "NumericLiteral"}"""
        val node = AstNode.fromJson(json)
        
        node.isNumericLiteral() shouldBe true
    }
    
    test("应该判断是否为 Boolean Literal") {
        val json = """{"type": "BooleanLiteral"}"""
        val node = AstNode.fromJson(json)
        
        node.isBooleanLiteral() shouldBe true
    }
    
    test("应该判断是否为 Module") {
        val json = """{"type": "Module"}"""
        val node = AstNode.fromJson(json)
        
        node.isModule() shouldBe true
    }
    
    test("应该判断是否为 Script") {
        val json = """{"type": "Script"}"""
        val node = AstNode.fromJson(json)
        
        node.isScript() shouldBe true
    }
    
    test("应该判断是否为 Export Declaration") {
        val json = """{"type": "ExportDeclaration"}"""
        val node = AstNode.fromJson(json)
        
        node.isExportDeclaration() shouldBe true
    }
    
    test("应该判断是否为 Property Signature") {
        val json = """{"type": "TsPropertySignature"}"""
        val node = AstNode.fromJson(json)
        
        node.isPropertySignature() shouldBe true
    }
    
    test("应该获取 TypeReference 的类型名称") {
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
        
        node.getTypeReferenceName() shouldBe "String"
    }
    
    test("应该获取 TypeReference 的限定名称") {
        val json = """
        {
            "type": "TsTypeReference",
            "typeName": {
                "type": "TsQualifiedName",
                "left": {
                    "type": "Identifier",
                    "value": "com"
                },
                "right": {
                    "type": "Identifier",
                    "value": "example"
                }
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        node.getTypeReferenceName() shouldBe "example"
    }
    
    test("应该返回 null 当不是 TypeReference 时") {
        val json = """{"type": "Identifier", "value": "test"}"""
        val node = AstNode.fromJson(json)
        
        (node.getTypeReferenceName() == null) shouldBe true
    }
    
    test("应该获取 Interface 的名称") {
        val json = """
        {
            "type": "TsInterfaceDeclaration",
            "id": {
                "type": "Identifier",
                "value": "TestInterface"
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        node.getInterfaceName() shouldBe "TestInterface"
    }
    
    test("应该获取 Type Alias 的名称") {
        val json = """
        {
            "type": "TsTypeAliasDeclaration",
            "id": {
                "type": "Identifier",
                "value": "TestType"
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        node.getTypeAliasName() shouldBe "TestType"
    }
    
    test("应该获取 Property Signature 的名称") {
        val json = """
        {
            "type": "TsPropertySignature",
            "key": {
                "type": "Identifier",
                "value": "propertyName"
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        node.getPropertyName() shouldBe "propertyName"
    }
    
    test("应该获取 Property Signature 的字符串字面量名称") {
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
        
        node.getPropertyName() shouldBe "property-name"
    }
    
    test("应该获取 Keyword Type 的 kind") {
        val json = """
        {
            "type": "TsKeywordType",
            "kind": "string"
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        node.getKeywordKind() shouldBe "string"
    }
    
    test("应该获取 Literal Type 的字面量值") {
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
        
        node.getLiteralValue() shouldBe "\"test\""
    }
    
    test("应该获取数字字面量的值") {
        val json = """
        {
            "type": "TsLiteralType",
            "literal": {
                "type": "NumericLiteral",
                "value": 42
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        node.getLiteralValue() shouldBe "42.0"
    }
    
    test("应该获取布尔字面量的值") {
        val json = """
        {
            "type": "TsLiteralType",
            "literal": {
                "type": "BooleanLiteral",
                "value": true
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        node.getLiteralValue() shouldBe "true"
    }
    
    test("应该遍历 Union Type 的所有类型") {
        val json = """
        {
            "type": "TsUnionType",
            "types": [
                {"type": "TsKeywordType", "kind": "string"},
                {"type": "TsKeywordType", "kind": "number"}
            ]
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val types = mutableListOf<String>()
        node.forEachUnionType { types.add(it.type) }
        
        types shouldBe listOf("TsKeywordType", "TsKeywordType")
    }
    
    test("应该遍历 Intersection Type 的所有类型") {
        val json = """
        {
            "type": "TsIntersectionType",
            "types": [
                {"type": "TsTypeReference", "typeName": {"type": "Identifier", "value": "A"}},
                {"type": "TsTypeReference", "typeName": {"type": "Identifier", "value": "B"}}
            ]
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val types = mutableListOf<String>()
        node.forEachIntersectionType { types.add(it.type) }
        
        types shouldBe listOf("TsTypeReference", "TsTypeReference")
    }
    
    test("应该遍历 Interface 的所有属性") {
        val json = """
        {
            "type": "TsInterfaceDeclaration",
            "body": {
                "type": "TsInterfaceBody",
                "body": [
                    {"type": "TsPropertySignature", "key": {"type": "Identifier", "value": "a"}},
                    {"type": "TsPropertySignature", "key": {"type": "Identifier", "value": "b"}}
                ]
            }
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val properties = mutableListOf<String>()
        node.forEachInterfaceProperty { 
            properties.add(it.getPropertyName() ?: "")
        }
        
        properties shouldBe listOf("a", "b")
    }
    
    test("应该遍历 Module/Script 的所有 body 项") {
        val json = """
        {
            "type": "Module",
            "body": [
                {"type": "TsInterfaceDeclaration"},
                {"type": "TsTypeAliasDeclaration"}
            ]
        }
        """.trimIndent()
        val node = AstNode.fromJson(json)
        
        val items = mutableListOf<String>()
        node.forEachBodyItem { items.add(it.type) }
        
        items shouldBe listOf("TsInterfaceDeclaration", "TsTypeAliasDeclaration")
    }
})

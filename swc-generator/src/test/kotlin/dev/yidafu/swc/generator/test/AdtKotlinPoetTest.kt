package dev.yidafu.swc.generator.test

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.codegen.poet.KotlinPoetConverter
import com.squareup.kotlinpoet.*
import java.io.File

/**
 * ADT + KotlinPoet 代码生成测试
 */
object AdtKotlinPoetTest {
    
    fun testKotlinTypeConversion() {
        println("=== 测试 KotlinType 转换 ===")
        
        // 测试基本类型
        val stringType = KotlinTypeFactory.string()
        val intType = KotlinTypeFactory.int()
        val booleanType = KotlinTypeFactory.boolean()
        
        println("String: ${stringType.toTypeName()}")
        println("Int: ${intType.toTypeName()}")
        println("Boolean: ${booleanType.toTypeName()}")
        
        // 测试泛型类型
        val arrayType = KotlinTypeFactory.generic("Array", stringType)
        val nullableType = KotlinTypeFactory.nullable(intType)
        val unionType = KotlinTypeFactory.union(stringType, intType)
        
        println("Array<String>: ${arrayType.toTypeName()}")
        println("Int?: ${nullableType.toTypeName()}")
        println("Union: ${unionType.toTypeName()}")
        
        // 测试复杂类型
        val mapType = KotlinTypeFactory.generic("Map", stringType, intType)
        println("Map<String, Int>: ${mapType.toTypeName()}")
    }
    
    fun testKotlinDeclarationConversion() {
        println("\n=== 测试 KotlinDeclaration 转换 ===")
        
        // 创建属性声明
        val propertyDecl = KotlinDeclaration.PropertyDecl(
            name = "name",
            type = KotlinTypeFactory.string(),
            modifier = PropertyModifier.Val,
            defaultValue = Expression.StringLiteral("default"),
            annotations = listOf(
                KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral("name")))
            ),
            kdoc = "用户名称"
        )
        
        // 转换为 PropertySpec
        val propertySpec = KotlinPoetConverter.convertProperty(propertyDecl)
        println("PropertySpec: $propertySpec")
        
        // 创建类声明
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "User",
            modifier = ClassModifier.DataClass,
            properties = listOf(propertyDecl),
            parents = emptyList(),
            annotations = listOf(
                KotlinDeclaration.Annotation("Serializable"),
                KotlinDeclaration.Annotation("SwcDslMarker")
            ),
            kdoc = "用户数据类"
        )
        
        // 转换为 TypeSpec
        val typeSpec = KotlinPoetConverter.convertDeclaration(classDecl)
        println("TypeSpec: $typeSpec")
    }
    
    fun testTypeAliasConversion() {
        println("\n=== 测试 TypeAlias 转换 ===")
        
        val typeAliasDecl = KotlinDeclaration.TypeAliasDecl(
            name = "UserId",
            type = KotlinTypeFactory.string(),
            kdoc = "用户ID类型别名"
        )
        
        val typeAliasSpec = KotlinPoetConverter.convertTypeAliasDeclaration(typeAliasDecl)
        println("TypeAliasSpec: $typeAliasSpec")
    }
    
    fun testStringParsing() {
        println("\n=== 测试字符串解析 ===")
        
        val testTypes = listOf(
            "String",
            "Int?",
            "Array<String>",
            "Map<String, Int>",
            "List<Array<String>>"
        )
        
        testTypes.forEach { typeStr ->
            val kotlinType = typeStr.parseToKotlinType()
            val typeName = kotlinType.toTypeName()
            println("'$typeStr' -> $kotlinType -> $typeName")
        }
    }
    
    fun testCodeGeneration() {
        println("\n=== 测试代码生成 ===")
        
        // 创建一个简单的类
        val userClass = KotlinDeclaration.ClassDecl(
            name = "User",
            modifier = ClassModifier.DataClass,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "id",
                    type = KotlinTypeFactory.string(),
                    modifier = PropertyModifier.Val
                ),
                KotlinDeclaration.PropertyDecl(
                    name = "name",
                    type = KotlinTypeFactory.string(),
                    modifier = PropertyModifier.Val
                ),
                KotlinDeclaration.PropertyDecl(
                    name = "age",
                    type = KotlinTypeFactory.int(),
                    modifier = PropertyModifier.Val,
                    defaultValue = Expression.NumberLiteral("0")
                )
            ),
            parents = emptyList(),
            annotations = listOf(
                KotlinDeclaration.Annotation("Serializable")
            ),
            kdoc = "用户数据类"
        )
        
        // 生成 TypeSpec
        val typeSpec = KotlinPoetConverter.convertDeclaration(userClass)
        
        // 创建文件
        val fileSpec = FileSpec.builder("dev.yidafu.swc.test", "User")
            .addType(typeSpec)
            .build()
        
        // 输出到控制台
        println("生成的代码:")
        println(fileSpec.toString())
    }
    
    fun runAllTests() {
        try {
            testKotlinTypeConversion()
            testKotlinDeclarationConversion()
            testTypeAliasConversion()
            testStringParsing()
            testCodeGeneration()
            
            println("\n=== 所有测试完成 ===")
        } catch (e: Exception) {
            println("测试失败: ${e.message}")
            e.printStackTrace()
        }
    }
}

fun main() {
    AdtKotlinPoetTest.runAllTests()
}

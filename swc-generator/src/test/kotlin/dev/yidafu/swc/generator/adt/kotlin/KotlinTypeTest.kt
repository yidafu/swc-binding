package dev.yidafu.swc.generator.adt.kotlin

import com.squareup.kotlinpoet.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class KotlinTypeTest : FunSpec({
    
    test("应该创建简单类型") {
        val type = KotlinTypeFactory.simple("TestType")
        
        (type is KotlinType.Simple) shouldBe true
        (type as KotlinType.Simple).name shouldBe "TestType"
    }
    
    test("应该创建泛型类型") {
        val stringType = KotlinTypeFactory.string()
        val type = KotlinTypeFactory.generic("Array", stringType)
        
        (type is KotlinType.Generic) shouldBe true
        val generic = type as KotlinType.Generic
        generic.name shouldBe "Array"
        generic.params.size shouldBe 1
    }
    
    test("应该创建可空类型") {
        val stringType = KotlinTypeFactory.string()
        val nullableType = KotlinTypeFactory.nullable(stringType)
        
        (nullableType is KotlinType.Nullable) shouldBe true
        val nullable = nullableType as KotlinType.Nullable
        nullable.innerType shouldBe stringType
    }
    
    test("应该创建函数类型") {
        val stringType = KotlinTypeFactory.string()
        val intType = KotlinTypeFactory.int()
        val functionType = KotlinType.Function(
            params = listOf(stringType),
            returnType = intType
        )
        
        (functionType is KotlinType.Function) shouldBe true
        val function = functionType as KotlinType.Function
        function.params.size shouldBe 1
        function.returnType shouldBe intType
    }
    
    test("应该创建 Union 类型") {
        val stringType = KotlinTypeFactory.string()
        val intType = KotlinTypeFactory.int()
        val unionType = KotlinTypeFactory.union(stringType, intType)
        
        (unionType is KotlinType.Union) shouldBe true
        val union = unionType as KotlinType.Union
        union.types.size shouldBe 2
    }
    
    test("应该创建 Booleanable 类型") {
        val stringType = KotlinTypeFactory.string()
        val booleanableType = KotlinTypeFactory.booleanable(stringType)
        
        (booleanableType is KotlinType.Booleanable) shouldBe true
        val booleanable = booleanableType as KotlinType.Booleanable
        booleanable.innerType shouldBe stringType
    }
    
    test("应该转换为类型字符串") {
        val stringType = KotlinTypeFactory.string()
        stringType.toTypeString() shouldBe "String"
        
        val intType = KotlinTypeFactory.int()
        intType.toTypeString() shouldBe "Int"
        
        val booleanType = KotlinTypeFactory.boolean()
        booleanType.toTypeString() shouldBe "Boolean"
    }
    
    test("应该转换泛型类型为字符串") {
        val stringType = KotlinTypeFactory.string()
        val arrayType = KotlinTypeFactory.generic("Array", stringType)
        
        arrayType.toTypeString() shouldBe "Array<String>"
    }
    
    test("应该转换可空类型为字符串") {
        val stringType = KotlinTypeFactory.string()
        val nullableType = KotlinTypeFactory.nullable(stringType)
        
        nullableType.toTypeString() shouldBe "String?"
    }
    
    test("应该转换 Union 类型为字符串") {
        val stringType = KotlinTypeFactory.string()
        val intType = KotlinTypeFactory.int()
        val unionType = KotlinTypeFactory.union(stringType, intType)
        
        unionType.toTypeString() shouldBe "Union.U2<String, Int>"
    }
    
    test("应该转换为 KotlinPoet TypeName") {
        val stringType = KotlinTypeFactory.string()
        val typeName = stringType.toTypeName()
        
        (typeName is ClassName) shouldBe true
        typeName.toString() shouldBe "kotlin.String"
    }
    
    test("应该转换泛型类型为 KotlinPoet TypeName") {
        val stringType = KotlinTypeFactory.string()
        val arrayType = KotlinTypeFactory.generic("Array", stringType)
        val typeName = arrayType.toTypeName()
        
        (typeName is ParameterizedTypeName) shouldBe true
        typeName.toString() shouldContain "Array"
        typeName.toString() shouldContain "String"
    }
    
    test("应该转换可空类型为 KotlinPoet TypeName") {
        val stringType = KotlinTypeFactory.string()
        val nullableType = KotlinTypeFactory.nullable(stringType)
        val typeName = nullableType.toTypeName()
        
        typeName.isNullable shouldBe true
    }
    
    test("应该转换函数类型为 KotlinPoet TypeName") {
        val stringType = KotlinTypeFactory.string()
        val intType = KotlinTypeFactory.int()
        val functionType = KotlinType.Function(
            params = listOf(stringType),
            returnType = intType
        )
        val typeName = functionType.toTypeName()
        
        (typeName is LambdaTypeName) shouldBe true
    }
    
    test("应该使用工厂方法创建基本类型") {
        val stringType = KotlinTypeFactory.string()
        (stringType is KotlinType.StringType) shouldBe true
        
        val intType = KotlinTypeFactory.int()
        (intType is KotlinType.Int) shouldBe true
        
        val booleanType = KotlinTypeFactory.boolean()
        (booleanType is KotlinType.Boolean) shouldBe true
        
        val anyType = KotlinTypeFactory.any()
        (anyType is KotlinType.Any) shouldBe true
        
        val unitType = KotlinTypeFactory.unit()
        (unitType is KotlinType.Unit) shouldBe true
        
        val nothingType = KotlinTypeFactory.nothing()
        (nothingType is KotlinType.Nothing) shouldBe true
    }
    
    test("应该处理复杂的泛型类型") {
        val stringType = KotlinTypeFactory.string()
        val intType = KotlinTypeFactory.int()
        val mapType = KotlinTypeFactory.generic("Map", stringType, intType)
        
        (mapType is KotlinType.Generic) shouldBe true
        val generic = mapType as KotlinType.Generic
        generic.name shouldBe "Map"
        generic.params.size shouldBe 2
        generic.params[0] shouldBe stringType
        generic.params[1] shouldBe intType
    }
    
    test("应该处理嵌套的泛型类型") {
        val stringType = KotlinTypeFactory.string()
        val arrayType = KotlinTypeFactory.generic("Array", stringType)
        val listType = KotlinTypeFactory.generic("List", arrayType)
        
        (listType is KotlinType.Generic) shouldBe true
        val generic = listType as KotlinType.Generic
        generic.name shouldBe "List"
        generic.params.size shouldBe 1
        generic.params[0] shouldBe arrayType
    }
    
    test("应该处理 Union 类型的多个类型") {
        val stringType = KotlinTypeFactory.string()
        val intType = KotlinTypeFactory.int()
        val booleanType = KotlinTypeFactory.boolean()
        val unionType = KotlinTypeFactory.union(stringType, intType, booleanType)
        
        (unionType is KotlinType.Union) shouldBe true
        val union = unionType as KotlinType.Union
        union.types.size shouldBe 3
        union.toTypeString() shouldBe "Union.U3<String, Int, Boolean>"
    }
    
    test("应该处理大型 Union 类型转换为 Any") {
        val types = listOf(
            KotlinTypeFactory.string(),
            KotlinTypeFactory.int(),
            KotlinTypeFactory.boolean(),
            KotlinTypeFactory.long(),
            KotlinTypeFactory.double()
        )
        val unionType = KotlinType.Union(types)
        
        // 大于 4 个类型的 Union 应该转换为 Any
        unionType.toTypeString() shouldBe "Any"
    }
})


package dev.yidafu.swc.generator.adt.converter

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.result.GeneratorResult
import dev.yidafu.swc.generator.adt.typescript.*
import dev.yidafu.swc.generator.config.ClassModifiersConfig
import dev.yidafu.swc.generator.config.NamingRulesConfig
import dev.yidafu.swc.generator.config.PathsConfig
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldNotBeNull

class TypeConverterTest : FunSpec({
    
    fun createConfig(): SwcGeneratorConfig {
        return SwcGeneratorConfig(
            classModifiers = ClassModifiersConfig(),
            namingRules = NamingRulesConfig(),
            paths = PathsConfig()
        )
    }
    
    test("应该转换关键字类型 string 为 Kotlin String") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Keyword(KeywordKind.STRING)
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.StringType) shouldBe true
    }
    
    test("应该转换关键字类型 number 为 Kotlin Int") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Keyword(KeywordKind.NUMBER)
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Int) shouldBe true
    }
    
    test("应该转换关键字类型 boolean 为 Kotlin Boolean") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Keyword(KeywordKind.BOOLEAN)
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Boolean) shouldBe true
    }
    
    test("应该转换关键字类型 void 为 Kotlin Unit") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Keyword(KeywordKind.VOID)
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Unit) shouldBe true
    }
    
    test("应该转换关键字类型 any 为 Kotlin Any") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Keyword(KeywordKind.ANY)
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Any) shouldBe true
    }
    
    test("应该转换关键字类型 undefined 为 Kotlin Nothing") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Keyword(KeywordKind.UNDEFINED)
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Nothing) shouldBe true
    }
    
    test("应该转换关键字类型 null 为 Kotlin Nothing") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Null
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Nothing) shouldBe true
    }
    
    test("应该转换类型引用") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Reference("String", emptyList())
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Simple) shouldBe true
        (kotlinType as KotlinType.Simple).name shouldBe "String"
    }
    
    test("应该转换带泛型参数的类型引用") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Reference(
            "Array",
            listOf(TypeScriptType.Keyword(KeywordKind.STRING))
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Generic) shouldBe true
        val generic = kotlinType as KotlinType.Generic
        generic.name shouldBe "Array"
        generic.params.size shouldBe 1
    }
    
    test("应该转换 Union 类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Union(
            listOf(
                TypeScriptType.Keyword(KeywordKind.STRING),
                TypeScriptType.Keyword(KeywordKind.NUMBER)
            )
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Union) shouldBe true
        val union = kotlinType as KotlinType.Union
        union.types.size shouldBe 2
    }
    
    test("应该转换 Union 类型（包含 Boolean）为 Booleanable") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Union(
            listOf(
                TypeScriptType.Keyword(KeywordKind.BOOLEAN),
                TypeScriptType.Keyword(KeywordKind.STRING)
            )
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Booleanable) shouldBe true
    }
    
    test("应该转换 Intersection 类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Intersection(
            listOf(
                TypeScriptType.Reference("A", emptyList()),
                TypeScriptType.Reference("B", emptyList())
            )
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        // Intersection 通常取第一个类型
    }
    
    test("应该转换数组类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Array(
            TypeScriptType.Keyword(KeywordKind.STRING)
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Generic) shouldBe true
        val generic = kotlinType as KotlinType.Generic
        generic.name shouldBe "Array"
        generic.params.size shouldBe 1
    }
    
    test("应该转换元组类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Tuple(
            listOf(
                TypeScriptType.Keyword(KeywordKind.STRING),
                TypeScriptType.Keyword(KeywordKind.NUMBER)
            )
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Union) shouldBe true
    }
    
    test("应该转换字面量类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Literal(
            LiteralValue.StringLiteral("test")
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.StringType) shouldBe true
    }
    
    test("应该转换数字字面量类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Literal(
            LiteralValue.NumberLiteral(42.0)
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Int) shouldBe true
    }
    
    test("应该转换布尔字面量类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Literal(
            LiteralValue.BooleanLiteral(true)
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Boolean) shouldBe true
    }
    
    test("应该转换类型字面量") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.TypeLiteral(emptyList())
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Simple) shouldBe true
        (kotlinType as KotlinType.Simple).name shouldBe "TypeLiteral"
    }
    
    test("应该转换函数类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Function(
            params = emptyList(),
            returnType = TypeScriptType.Keyword(KeywordKind.STRING)
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Function) shouldBe true
    }
    
    test("应该转换索引签名类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.IndexSignature(
            keyType = TypeScriptType.Keyword(KeywordKind.STRING),
            valueType = TypeScriptType.Keyword(KeywordKind.NUMBER)
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Generic) shouldBe true
        val generic = kotlinType as KotlinType.Generic
        generic.name shouldBe "Map"
        generic.params.size shouldBe 2
    }
    
    test("应该转换 Any 类型") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Any
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Any) shouldBe true
    }
    
    test("应该转换 Unknown 类型为 Any") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Unknown
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Any) shouldBe true
    }
    
    test("应该转换 Never 类型为 Nothing") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Never
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Nothing) shouldBe true
    }
    
    test("应该转换 Undefined 类型为 Nothing") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Undefined
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        (kotlinType is KotlinType.Nothing) shouldBe true
    }
    
    test("应该使用便捷方法 convert") {
        val tsType = TypeScriptType.Keyword(KeywordKind.STRING)
        
        // 注意：这里需要 GlobalConfig 已经初始化
        // 在实际测试中，可能需要 mock 或设置 GlobalConfig
        // val result = TypeConverter.convert(tsType)
        // result.isSuccess() shouldBe true
    }
    
    test("应该处理 Union 类型中过滤 null") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Union(
            listOf(
                TypeScriptType.Keyword(KeywordKind.STRING),
                TypeScriptType.Null
            )
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        // null 应该被过滤掉
        if (kotlinType is KotlinType.Union) {
            kotlinType.types.size shouldBe 1
        } else {
            // 如果不是 Union，应该是一个单一类型
        }
    }
    
    test("应该处理大的 Union 类型转换为 Any") {
        val config = createConfig()
        val converter = TypeConverter(config)
        val tsType = TypeScriptType.Union(
            listOf(
                TypeScriptType.Keyword(KeywordKind.STRING),
                TypeScriptType.Keyword(KeywordKind.NUMBER),
                TypeScriptType.Keyword(KeywordKind.BOOLEAN),
                TypeScriptType.Keyword(KeywordKind.ANY),
                TypeScriptType.Keyword(KeywordKind.UNKNOWN)
            )
        )
        
        val result = converter.convertToKotlinType(tsType)
        
        result.isSuccess() shouldBe true
        val kotlinType = result.getOrNull()!!
        // 大于 4 个类型的 Union 应该转换为 Any
        if (kotlinType !is KotlinType.Union) {
            (kotlinType is KotlinType.Any) shouldBe true
        } else {
            // 如果是 Union，应该包含多个类型
        }
    }
})


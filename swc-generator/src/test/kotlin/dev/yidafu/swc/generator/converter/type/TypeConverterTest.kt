package dev.yidafu.swc.generator.converter.type

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.typescript.*
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class TypeConverterTest : ShouldSpec({

    val config = Configuration.default()
    val converter = TypeConverter(config)

    should("test converter creation") {
        converter.shouldNotBeNull()
    }

    should("test convert string type") {
        val result = converter.convert(TypeScriptType.Keyword(KeywordKind.STRING))

        result.shouldNotBeNull()
        if (result.isSuccess()) {
            val kotlinType = result.getOrThrow()
            kotlinType.shouldNotBeNull()
        }
    }

    should("test convert number type") {
        val result = converter.convert(TypeScriptType.Keyword(KeywordKind.NUMBER))

        result.shouldNotBeNull()
    }

    should("test convert boolean type") {
        val result = converter.convert(TypeScriptType.Keyword(KeywordKind.BOOLEAN))

        result.shouldNotBeNull()
    }

    should("test convert any type") {
        val result = converter.convert(TypeScriptType.Any)

        result.shouldNotBeNull()
    }

    should("test convert void type") {
        val result = converter.convert(TypeScriptType.Void)

        result.shouldNotBeNull()
    }

    should("test convert null type") {
        val result = converter.convert(TypeScriptType.Null)

        result.shouldNotBeNull()
    }

    should("test convert undefined type") {
        val result = converter.convert(TypeScriptType.Undefined)

        result.shouldNotBeNull()
    }

    should("test convert generic type") {
        val genericType = TypeScriptType.Reference(
            name = "Array",
            typeParams = listOf(TypeScriptType.Keyword(KeywordKind.STRING))
        )

        val result = converter.convert(genericType)

        result.shouldNotBeNull()
    }

    should("test convert union type") {
        val unionType = TypeScriptType.Union(
            types = listOf(
                TypeScriptType.Keyword(KeywordKind.STRING),
                TypeScriptType.Keyword(KeywordKind.NUMBER)
            )
        )

        val result = converter.convert(unionType)

        result.shouldNotBeNull()
    }

    should("test convert intersection type") {
        val intersectionType = TypeScriptType.Intersection(
            types = listOf(
                TypeScriptType.Keyword(KeywordKind.STRING),
                TypeScriptType.Keyword(KeywordKind.NUMBER)
            )
        )

        val result = converter.convert(intersectionType)

        result.shouldNotBeNull()
    }

    should("test convert simple type") {
        val simpleType = TypeScriptType.Reference("CustomType", emptyList())

        val result = converter.convert(simpleType)

        result.shouldNotBeNull()
    }

    should("test convert array type") {
        val arrayType = TypeScriptType.Array(TypeScriptType.Keyword(KeywordKind.STRING))

        val result = converter.convert(arrayType)

        result.shouldNotBeNull()
    }

    should("test convert optional type") {
        // Optional 类型在 TypeScript 中通常用 Union 表示
        val optionalType = TypeScriptType.Union(
            types = listOf(
                TypeScriptType.Keyword(KeywordKind.STRING),
                TypeScriptType.Undefined
            )
        )

        val result = converter.convert(optionalType)

        result.shouldNotBeNull()
    }

    should("literal unions produce fixed Union types") {
        val literalUnion = TypeScriptType.Union(
            listOf(
                TypeScriptType.Literal(LiteralValue.StringLiteral("a")),
                TypeScriptType.Literal(LiteralValue.StringLiteral("b"))
            )
        )

        val result = converter.convert(literalUnion)
        result.shouldNotBeNull()
        val kotlinType = result.getOrThrow()
        kotlinType.toTypeString() shouldBe "String"
    }

    should("nested type registry produces nested Kotlin types") {
        val registry = mapOf("Inner" to "Parent")
        val nestedConverter = TypeConverter(config, registry)
        val result = nestedConverter.convert(TypeScriptType.Reference("Inner"))
        val nested = result.getOrThrow().shouldBeInstanceOf<KotlinType.Nested>()
        nested.parent shouldBe "Parent"
        nested.name shouldBe "Inner"
    }
})

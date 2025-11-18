package dev.yidafu.swc.generator.converter.type

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.typescript.KeywordKind
import dev.yidafu.swc.generator.model.typescript.LiteralValue
import dev.yidafu.swc.generator.model.typescript.TypeScriptType
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class TypeConverterUnionDedupTest : AnnotationSpec() {

    @Test
    fun `boolean or string-literals should become Union_U2_Boolean_String`() {
        val cfg = Configuration.default()
        val converter = TypeConverter(cfg)
        // boolean | "unknown" | "commonjs"
        val tsUnion = TypeScriptType.Union(
            listOf(
                TypeScriptType.Keyword(KeywordKind.BOOLEAN),
                TypeScriptType.Literal(LiteralValue.StringLiteral("unknown")),
                TypeScriptType.Literal(LiteralValue.StringLiteral("commonjs"))
            )
        )
        val kt = converter.convert(tsUnion).getOrThrow()
        // 期望为 Union.U2<Boolean, String>
        kt.shouldBeInstanceOf<KotlinType.Generic>()
        val g = kt as KotlinType.Generic
        g.name shouldBe "Union.U2"
        g.params.size shouldBe 2
        g.params[0] shouldBe KotlinType.Boolean
        g.params[1] shouldBe KotlinType.StringType
    }

    @Test
    fun `only string literals union should collapse to String`() {
        val cfg = Configuration.default()
        val converter = TypeConverter(cfg)
        // "inline" | "both" | "external"
        val tsUnion = TypeScriptType.Union(
            listOf(
                TypeScriptType.Literal(LiteralValue.StringLiteral("inline")),
                TypeScriptType.Literal(LiteralValue.StringLiteral("both")),
                TypeScriptType.Literal(LiteralValue.StringLiteral("external"))
            )
        )
        val kt = converter.convert(tsUnion).getOrThrow()
        // 期望直接折叠为 String
        kt shouldBe KotlinType.StringType
    }

    @Test
    fun `boolean literal plus string literals should become Union_U2_Boolean_String`() {
        val cfg = Configuration.default()
        val converter = TypeConverter(cfg)
        // false | "inline" | "external"
        val tsUnion = TypeScriptType.Union(
            listOf(
                TypeScriptType.Literal(LiteralValue.BooleanLiteral(false)),
                TypeScriptType.Literal(LiteralValue.StringLiteral("inline")),
                TypeScriptType.Literal(LiteralValue.StringLiteral("external"))
            )
        )
        val kt = converter.convert(tsUnion).getOrThrow()
        // 期望为 Union.U2<Boolean, String>
        kt.shouldBeInstanceOf<KotlinType.Generic>()
        val g = kt as KotlinType.Generic
        g.name shouldBe "Union.U2"
        g.params.size shouldBe 2
        g.params[0] shouldBe KotlinType.Boolean
        g.params[1] shouldBe KotlinType.StringType
    }
}

package dev.yidafu.swc.generator.config

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.nulls.shouldNotBeNull

/**
 * Hardcoded 配置的测试
 * 在重构前添加，确保重构后配置逻辑正确
 */
class HardcodedConfigTest : AnnotationSpec() {

    @Test
    fun `AnnotationNames should map all annotation names correctly`() {
        val className = Hardcoded.AnnotationNames.toClassName("SerialName")
        className.packageName shouldBe "kotlinx.serialization"
        className.simpleName shouldBe "SerialName"
    }

    @Test
    fun `AnnotationNames should handle all known annotations`() {
        val annotations = listOf(
            "SerialName",
            "Serializable",
            "JsonClassDiscriminator",
            "Transient",
            "EncodeDefault",
            "OptIn",
            "Contextual"
        )

        annotations.forEach { name ->
            val className = Hardcoded.AnnotationNames.toClassName(name)
            className.packageName.shouldNotBeNull()
            className.simpleName.shouldNotBeNull()
        }
    }

    @Test
    fun `PropertyRules should identify type property correctly`() {
        Hardcoded.PropertyRules.isTypeProperty("type").shouldBeTrue()
        Hardcoded.PropertyRules.isTypeProperty("`type`").shouldBeTrue()
        Hardcoded.PropertyRules.isTypeProperty("other").shouldBeFalse()
    }

    @Test
    fun `PropertyRules should identify syntax property correctly`() {
        Hardcoded.PropertyRules.isSyntaxProperty("syntax").shouldBeTrue()
        Hardcoded.PropertyRules.isSyntaxProperty("`syntax`").shouldBeTrue()
        Hardcoded.PropertyRules.isSyntaxProperty("other").shouldBeFalse()
    }

    @Test
    fun `PropertyRules should identify span property correctly`() {
        Hardcoded.PropertyRules.isSpanProperty("span").shouldBeTrue()
        Hardcoded.PropertyRules.isSpanProperty("`span`").shouldBeTrue()
        Hardcoded.PropertyRules.isSpanProperty("other").shouldBeFalse()
    }

    @Test
    fun `PropertyRules should identify span coordinates correctly`() {
        Hardcoded.PropertyRules.isSpanCoordinate("start").shouldBeTrue()
        Hardcoded.PropertyRules.isSpanCoordinate("end").shouldBeTrue()
        Hardcoded.PropertyRules.isSpanCoordinate("ctxt").shouldBeTrue()
        Hardcoded.PropertyRules.isSpanCoordinate("other").shouldBeFalse()
    }

    @Test
    fun `PropertyRules should identify span coordinate properties correctly`() {
        Hardcoded.PropertyRules.isSpanCoordinateProperty("Span", "start").shouldBeTrue()
        Hardcoded.PropertyRules.isSpanCoordinateProperty("Span", "end").shouldBeTrue()
        Hardcoded.PropertyRules.isSpanCoordinateProperty("Span", "ctxt").shouldBeTrue()
        Hardcoded.PropertyRules.isSpanCoordinateProperty("Other", "start").shouldBeFalse()
    }

    @Test
    fun `PropertyRules should wrap reserved words`() {
        val reservedWords = listOf(
            "object", "inline", "in", "super", "class", "interface", "fun",
            "val", "var", "when", "is", "as", "import", "package"
        )

        reservedWords.forEach { word ->
            val wrapped = Hardcoded.PropertyRules.wrapReservedWord(word)
            wrapped shouldBe "`$word`"
        }
    }

    @Test
    fun `PropertyRules should not wrap non-reserved words`() {
        val normalWords = listOf("test", "value", "name", "type")

        normalWords.forEach { word ->
            val wrapped = Hardcoded.PropertyRules.wrapReservedWord(word)
            wrapped shouldBe word
        }
    }

    @Test
    fun `CtxtFields should contain all required classes`() {
        val requiredClasses = listOf(
            "BlockStatement",
            "CallExpression",
            "NewExpression",
            "ArrowFunctionExpression",
            "TaggedTemplateExpression",
            "FunctionDeclaration",
            "VariableDeclaration",
            "Class",
            "PrivateProperty",
            "Constructor",
            "Identifier"
        )

        requiredClasses.forEach { className ->
            Hardcoded.CtxtFields.CLASSES_WITH_CTXT.shouldContain(className)
        }
    }

    @Test
    fun `TypeAliasRules should identify force string aliases`() {
        Hardcoded.TypeAliasRules.isForceStringAlias("TerserEcmaVersion").shouldBeTrue()
        Hardcoded.TypeAliasRules.isForceStringAlias("OtherAlias").shouldBeFalse()
    }

    @Test
    fun `TypeAliasRules should identify force nullable interfaces`() {
        Hardcoded.TypeAliasRules.forceNullableForInterface("WasmAnalysisOptions").shouldBeTrue()
        Hardcoded.TypeAliasRules.forceNullableForInterface("OtherInterface").shouldBeFalse()
    }

    @Test
    fun `ConverterRules should identify skipped type aliases`() {
        Hardcoded.ConverterRules.shouldSkipTypeAlias("ToSnakeCase").shouldBeTrue()
        Hardcoded.ConverterRules.shouldSkipTypeAlias("ToSnakeCaseProperties").shouldBeTrue()
        Hardcoded.ConverterRules.shouldSkipTypeAlias("OtherAlias").shouldBeFalse()
    }

    @Test
    fun `InterfaceRules should identify skipped interfaces`() {
        Hardcoded.InterfaceRules.shouldSkipInterface("ExprOrSpread").shouldBeTrue()
        Hardcoded.InterfaceRules.shouldSkipInterface("OptionalChainingCall").shouldBeTrue()
        Hardcoded.InterfaceRules.shouldSkipInterface("OtherInterface").shouldBeFalse()
    }

    @Test
    fun `InterfaceRules should return skip reason`() {
        Hardcoded.InterfaceRules.getSkipReason("ExprOrSpread") shouldBe "统一使用 Argument"
        Hardcoded.InterfaceRules.getSkipReason("OptionalChainingCall") shouldBe "只保留 CallExpression"
        Hardcoded.InterfaceRules.getSkipReason("OtherInterface") shouldBe null
    }

    @Test
    fun `InterfaceRules should replace type references`() {
        Hardcoded.InterfaceRules.replaceTypeReference("OptionalChainingCall") shouldBe "CallExpression"
        Hardcoded.InterfaceRules.replaceTypeReference("OtherType") shouldBe "OtherType"
    }

    @Test
    fun `InterfaceRules should get root discriminator`() {
        Hardcoded.InterfaceRules.getRootDiscriminator("Node") shouldBe "type"
        Hardcoded.InterfaceRules.getRootDiscriminator("Config") shouldBe "syntax"
        Hardcoded.InterfaceRules.getRootDiscriminator("ParserConfig") shouldBe "syntax"
        Hardcoded.InterfaceRules.getRootDiscriminator("Options") shouldBe "syntax"
        Hardcoded.InterfaceRules.getRootDiscriminator("OtherInterface") shouldBe null
    }

    @Test
    fun `Serializer should have correct discriminator constants`() {
        Hardcoded.Serializer.DEFAULT_DISCRIMINATOR shouldBe "type"
        Hardcoded.Serializer.SYNTAX_DISCRIMINATOR shouldBe "syntax"
    }

    @Test
    fun `Serializer should contain additional open bases`() {
        val openBases = Hardcoded.Serializer.additionalOpenBases
        openBases.shouldContain("Node")
        openBases.shouldContain("ModuleItem")
        openBases.shouldContain("ModuleDeclaration")
        openBases.shouldContain("Identifier")
    }

    @Test
    fun `Serializer should contain config interface names`() {
        val configNames = Hardcoded.Serializer.configInterfaceNames
        configNames.shouldContain("BaseParseOptions")
        configNames.shouldContain("ParserConfig")
        configNames.shouldContain("TsParserConfig")
        configNames.shouldContain("EsParserConfig")
        configNames.shouldContain("Options")
        configNames.shouldContain("Config")
    }

    @Test
    fun `Union should have default factory arity`() {
        val arity = Hardcoded.Union.factoryArity
        arity.shouldContain(2)
        arity.shouldContain(3)
        arity.shouldContain(4)
        arity.shouldContain(5)
    }
}


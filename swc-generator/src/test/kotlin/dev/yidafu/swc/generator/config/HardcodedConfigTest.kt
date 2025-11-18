package dev.yidafu.swc.generator.config

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

/**
 * 配置类的测试
 * 测试所有配置类的功能
 */
class HardcodedConfigTest : AnnotationSpec() {

    @Test
    fun `AnnotationConfig should map all annotation names correctly`() {
        val className = AnnotationConfig.toClassName("SerialName")
        className.packageName shouldBe "kotlinx.serialization"
        className.simpleName shouldBe "SerialName"
    }

    @Test
    fun `AnnotationConfig should handle all known annotations`() {
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
            val className = AnnotationConfig.toClassName(name)
            className.packageName.shouldNotBeNull()
            className.simpleName.shouldNotBeNull()
        }
    }

    @Test
    fun `PropertyRulesConfig should identify type property correctly`() {
        PropertyRulesConfig.isTypeProperty("type").shouldBeTrue()
        PropertyRulesConfig.isTypeProperty("`type`").shouldBeTrue()
        PropertyRulesConfig.isTypeProperty("other").shouldBeFalse()
    }

    @Test
    fun `PropertyRulesConfig should identify syntax property correctly`() {
        PropertyRulesConfig.isSyntaxProperty("syntax").shouldBeTrue()
        PropertyRulesConfig.isSyntaxProperty("`syntax`").shouldBeTrue()
        PropertyRulesConfig.isSyntaxProperty("other").shouldBeFalse()
    }

    @Test
    fun `PropertyRulesConfig should identify span property correctly`() {
        PropertyRulesConfig.isSpanProperty("span").shouldBeTrue()
        PropertyRulesConfig.isSpanProperty("`span`").shouldBeTrue()
        PropertyRulesConfig.isSpanProperty("other").shouldBeFalse()
    }

    @Test
    fun `PropertyRulesConfig should identify span coordinates correctly`() {
        PropertyRulesConfig.isSpanCoordinate("start").shouldBeTrue()
        PropertyRulesConfig.isSpanCoordinate("end").shouldBeTrue()
        PropertyRulesConfig.isSpanCoordinate("ctxt").shouldBeTrue()
        PropertyRulesConfig.isSpanCoordinate("other").shouldBeFalse()
    }

    @Test
    fun `PropertyRulesConfig should identify span coordinate properties correctly`() {
        PropertyRulesConfig.isSpanCoordinateProperty("Span", "start").shouldBeTrue()
        PropertyRulesConfig.isSpanCoordinateProperty("Span", "end").shouldBeTrue()
        PropertyRulesConfig.isSpanCoordinateProperty("Span", "ctxt").shouldBeTrue()
        PropertyRulesConfig.isSpanCoordinateProperty("Other", "start").shouldBeFalse()
    }

    @Test
    fun `PropertyRulesConfig should wrap reserved words`() {
        val reservedWords = listOf(
            "object", "inline", "in", "super", "class", "interface", "fun",
            "val", "var", "when", "is", "as", "import", "package"
        )

        reservedWords.forEach { word ->
            val wrapped = PropertyRulesConfig.wrapReservedWord(word)
            wrapped shouldBe "`$word`"
        }
    }

    @Test
    fun `PropertyRulesConfig should not wrap non-reserved words`() {
        val normalWords = listOf("test", "value", "name", "type")

        normalWords.forEach { word ->
            val wrapped = PropertyRulesConfig.wrapReservedWord(word)
            wrapped shouldBe word
        }
    }

    @Test
    fun `CtxtFieldsConfig should contain all required classes`() {
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
            CtxtFieldsConfig.CLASSES_WITH_CTXT.shouldContain(className)
        }
    }

    @Test
    fun `TypeAliasRulesConfig should identify force string aliases`() {
        TypeAliasRulesConfig.isForceStringAlias("TerserEcmaVersion").shouldBeTrue()
        TypeAliasRulesConfig.isForceStringAlias("OtherAlias").shouldBeFalse()
    }

    @Test
    fun `TypeAliasRulesConfig should identify force nullable interfaces`() {
        TypeAliasRulesConfig.forceNullableForInterface("WasmAnalysisOptions").shouldBeTrue()
        TypeAliasRulesConfig.forceNullableForInterface("OtherInterface").shouldBeFalse()
    }

    @Test
    fun `ConverterRulesConfig should identify skipped type aliases`() {
        ConverterRulesConfig.shouldSkipTypeAlias("ToSnakeCase").shouldBeTrue()
        ConverterRulesConfig.shouldSkipTypeAlias("ToSnakeCaseProperties").shouldBeTrue()
        ConverterRulesConfig.shouldSkipTypeAlias("OtherAlias").shouldBeFalse()
    }

    @Test
    fun `InterfaceRulesConfig should identify skipped interfaces`() {
        InterfaceRulesConfig.shouldSkipInterface("ExprOrSpread").shouldBeTrue()
        InterfaceRulesConfig.shouldSkipInterface("OptionalChainingCall").shouldBeTrue()
        InterfaceRulesConfig.shouldSkipInterface("OtherInterface").shouldBeFalse()
    }

    @Test
    fun `InterfaceRulesConfig should return skip reason`() {
        InterfaceRulesConfig.getSkipReason("ExprOrSpread") shouldBe "统一使用 Argument"
        InterfaceRulesConfig.getSkipReason("OptionalChainingCall") shouldBe "只保留 CallExpression"
        InterfaceRulesConfig.getSkipReason("OtherInterface") shouldBe null
    }

    @Test
    fun `InterfaceRulesConfig should replace type references`() {
        InterfaceRulesConfig.replaceTypeReference("OptionalChainingCall") shouldBe "CallExpression"
        InterfaceRulesConfig.replaceTypeReference("OtherType") shouldBe "OtherType"
    }

    @Test
    fun `InterfaceRulesConfig should get root discriminator`() {
        InterfaceRulesConfig.getRootDiscriminator("Node") shouldBe "type"
        InterfaceRulesConfig.getRootDiscriminator("Config") shouldBe "syntax"
        InterfaceRulesConfig.getRootDiscriminator("ParserConfig") shouldBe "syntax"
        InterfaceRulesConfig.getRootDiscriminator("Options") shouldBe "syntax"
        InterfaceRulesConfig.getRootDiscriminator("OtherInterface") shouldBe null
    }

    @Test
    fun `SerializerConfig should have correct discriminator constants`() {
        SerializerConfig.DEFAULT_DISCRIMINATOR shouldBe "type"
        SerializerConfig.SYNTAX_DISCRIMINATOR shouldBe "syntax"
    }

    @Test
    fun `SerializerConfig should contain additional open bases`() {
        val openBases = SerializerConfig.additionalOpenBases
        openBases.shouldContain("Node")
        openBases.shouldContain("ModuleItem")
        openBases.shouldContain("ModuleDeclaration")
        openBases.shouldContain("Identifier")
    }

    @Test
    fun `SerializerConfig should contain config interface names`() {
        val configNames = SerializerConfig.configInterfaceNames
        configNames.shouldContain("BaseParseOptions")
        configNames.shouldContain("ParserConfig")
        configNames.shouldContain("TsParserConfig")
        configNames.shouldContain("EsParserConfig")
        configNames.shouldContain("Options")
        configNames.shouldContain("Config")
    }

    @Test
    fun `UnionConfig should have default factory arity`() {
        val arity = UnionConfig.factoryArity
        arity.shouldContain(2)
        arity.shouldContain(3)
        arity.shouldContain(4)
        arity.shouldContain(5)
    }

    @Test
    fun `SerializerConfig should contain interface to impl map`() {
        val interfaceToImpl = SerializerConfig.interfaceToImplMap
        interfaceToImpl["Identifier"] shouldBe "IdentifierImpl"
        interfaceToImpl["BindingIdentifier"] shouldBe "BindingIdentifierImpl"
        interfaceToImpl["TemplateLiteral"] shouldBe "TemplateLiteralImpl"
        interfaceToImpl["TsTemplateLiteralType"] shouldBe "TsTemplateLiteralTypeImpl"
        interfaceToImpl.size shouldBe 4
    }
}

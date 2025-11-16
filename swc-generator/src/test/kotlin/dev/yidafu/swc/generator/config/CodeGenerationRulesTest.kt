package dev.yidafu.swc.generator.config

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CodeGenerationRulesTest : AnnotationSpec() {

    @Test
    fun `reserved words are wrapped`() {
        CodeGenerationRules.wrapReservedWord("class") shouldBe "`class`"
        CodeGenerationRules.wrapReservedWord("Custom") shouldBe "Custom"
    }

    @Test
    fun `snake case converts to camel case`() {
        CodeGenerationRules.snakeToCamelCase("foo_bar_baz") shouldBe "fooBarBaz"
        CodeGenerationRules.snakeToCamelCase("single") shouldBe "single"
    }

    @Test
    fun `kotlin type name validation rejects invalid inputs`() {
        CodeGenerationRules.isValidKotlinTypeName("ValidName").shouldBeTrue()
        CodeGenerationRules.isValidKotlinTypeName("invalidName").shouldBeFalse()
        CodeGenerationRules.isValidKotlinTypeName("List<String>").shouldBeFalse()
        CodeGenerationRules.isValidKotlinTypeName("").shouldBeFalse()
    }

    @Test
    fun `property type overrides return configured nullable strings`() {
        val override = CodeGenerationRules.getPropertyTypeOverride("global_defs")
        override.shouldBeInstanceOf<KotlinType.Nullable>()
        override.innerType shouldBe KotlinType.StringType
    }

    @Test
    fun `createPropertyDecl adds serialization annotations when renaming`() {
        val property = CodeGenerationRules.createPropertyDecl(
            name = "class",
            type = KotlinType.StringType,
            isOptional = false,
            originalName = "class"
        )

        property.name shouldBe "`class`"
        property.type shouldBe KotlinType.StringType
        property.annotations.map { it.name }.shouldContainExactly(listOf("Serializable", "SerialName"))
    }

    @Test
    fun `createClassDecl wraps parents and names`() {
        val klass = CodeGenerationRules.createClassDecl(
            name = "object",
            modifier = ClassModifier.Interface,
            properties = emptyList(),
            parents = listOf("Node"),
            annotations = listOf(KotlinDeclaration.Annotation("Serializable"))
        )

        klass.name shouldBe "`object`"
        klass.parents.single().toTypeString() shouldBe "Node"
        klass.annotations.single().name shouldBe "Serializable"
    }

    @Test
    fun `skip dsl receiver respects configuration`() {
        CodeGenerationRules.shouldSkipDslReceiver("HasSpan").shouldBeTrue()
        CodeGenerationRules.shouldSkipDslReceiver("Expression").shouldBeFalse()
    }
}

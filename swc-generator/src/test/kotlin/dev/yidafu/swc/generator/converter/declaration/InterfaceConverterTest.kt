package dev.yidafu.swc.generator.converter.declaration

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.typescript.*
import dev.yidafu.swc.generator.model.typescript.TypeMember
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf

class InterfaceConverterTest : AnnotationSpec() {

    private val config = Configuration.default()
    private val converter = InterfaceConverter(config)

    @Test
    fun `test converter creation`() {
        converter.shouldNotBeNull()
    }

    @Test
    fun `test convert simple interface`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "SimpleInterface",
            members = emptyList(),
            typeParameters = emptyList(),
            extends = emptyList()
        )

        val result = converter.convert(tsInterface)

        result.shouldNotBeNull()
        if (result.isSuccess()) {
            val kotlinClass = result.getOrThrow()
            kotlinClass.name.shouldContain("SimpleInterface")
        }
    }

    @Test
    fun `test convert interface with properties`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "InterfaceWithProps",
            members = listOf(
                TypeMember(
                    name = "name",
                    type = TypeScriptType.Keyword(KeywordKind.STRING),
                    optional = false,
                    readonly = false
                )
            ),
            typeParameters = emptyList(),
            extends = emptyList()
        )

        val result = converter.convert(tsInterface)

        result.shouldNotBeNull()
    }

    @Test
    fun `test convert interface with inheritance`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "DerivedInterface",
            members = emptyList(),
            typeParameters = emptyList(),
            extends = listOf(
                TypeReference("BaseInterface", emptyList())
            )
        )

        val result = converter.convert(tsInterface)

        result.shouldNotBeNull()
    }

    @Test
    fun `type property is forced to string`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "HasType",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Keyword(KeywordKind.NUMBER)
                )
            )
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        val typeProperty = kotlinClass.properties.first { it.name.removeSurrounding("`") == "type" }
        typeProperty.type shouldBe KotlinType.StringType
    }

    @Test
    fun `non type properties are nullable`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "HasValue",
            members = listOf(
                TypeMember(
                    name = "value",
                    type = TypeScriptType.Keyword(KeywordKind.STRING),
                    optional = false
                )
            )
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        val valueProperty = kotlinClass.properties.first { it.name == "value" }
        valueProperty.type.shouldBeInstanceOf<KotlinType.Nullable>()
    }

    @Test
    fun `type literal members generate nested interfaces`() {
        val childLiteral = TypeScriptType.TypeLiteral(
            members = listOf(
                TypeMember(
                    name = "enabled",
                    type = TypeScriptType.Keyword(KeywordKind.BOOLEAN)
                )
            )
        )

        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Parent",
            members = listOf(
                TypeMember(
                    name = "config",
                    type = childLiteral
                )
            )
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        kotlinClass.nestedClasses.size shouldBe 1
        kotlinClass.nestedClasses.first().name shouldBe "ParentConfig"
        val propertyType = kotlinClass.properties.first().type.shouldBeInstanceOf<KotlinType.Nullable>()
        val nestedType = propertyType.innerType.shouldBeInstanceOf<KotlinType.Nested>()
        nestedType.parent shouldBe "Parent"
        nestedType.name shouldBe "ParentConfig"
    }
}

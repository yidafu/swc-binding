package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.TypeSpec
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.Expression
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class KotlinPoetConverterTest : AnnotationSpec() {

    @Test
    fun `convertType caches identical Kotlin types`() {
        val first = KotlinPoetConverter.convertType(KotlinType.StringType)
        val second = KotlinPoetConverter.convertType(KotlinType.StringType)
        first shouldBe second
    }

    @Test
    fun `convertClassDeclaration creates public type spec`() {
        val Klass = KotlinDeclaration.ClassDecl(
            name = "Sample",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "value",
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.Val
                )
            ),
            parents = emptyList(),
            annotations = listOf(KotlinDeclaration.Annotation("Serializable"))
        )

        val typeSpec = KotlinPoetConverter.convertClassDeclaration(Klass, emptySet())
        typeSpec.shouldBeInstanceOf<TypeSpec>()
        typeSpec.name shouldBe "Sample"
        typeSpec.annotations.any { it.typeName.toString().contains("Serializable") }.shouldBeTrue()
        typeSpec.propertySpecs.single().name shouldBe "value"
    }

    @Test
    fun `convertAnnotation handles serial name arguments`() {
        val annotation = KotlinDeclaration.Annotation(
            name = "SerialName",
            arguments = listOf(Expression.StringLiteral("raw_name"))
        )

        val spec = KotlinPoetConverter.convertAnnotation(annotation)
        spec.shouldNotBe(null)
        spec!!.members.first().toString() shouldBe "\"raw_name\""
    }
}


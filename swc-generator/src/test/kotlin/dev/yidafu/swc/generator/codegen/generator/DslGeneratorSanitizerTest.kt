package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.shouldBe

class DslGeneratorSanitizerTest : AnnotationSpec() {

    private val sampleClass = KotlinDeclaration.ClassDecl(
        name = "SampleImpl",
        modifier = ClassModifier.Interface,
        properties = listOf(
            KotlinDeclaration.PropertyDecl(
                name = "child",
                type = KotlinType.StringType,
                modifier = PropertyModifier.Var
            )
        ),
        parents = emptyList(),
        annotations = emptyList()
    )

    private fun newGenerator(): DslGenerator = DslGenerator(listOf(sampleClass), emptyMap())

    @Test
    fun `sanitizeTypeName removes generics and fixes casing`() {
        val generator = newGenerator()
        val method = DslGenerator::class.java.getDeclaredMethod("sanitizeTypeName", String::class.java)
        method.isAccessible = true

        val result = method.invoke(generator, " list<invalid*> /*comment*/ ") as String
        result shouldBe "List"
    }

    @Test
    fun `toFunName lowercases first letter and unwraps keywords`() {
        val generator = newGenerator()
        val method = DslGenerator::class.java.getDeclaredMethod("toFunName", String::class.java)
        method.isAccessible = true

        method.invoke(generator, "CreateNode") shouldBe "createNode"
        method.invoke(generator, "Class") shouldBe "jsClass"
    }
}


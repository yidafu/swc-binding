package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.codegen.generator.dsl.DslNamingRules
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.shouldBe

class DslGeneratorSanitizerTest : AnnotationSpec() {

    @Test
    fun `sanitizeTypeName removes generics and fixes casing`() {
        val result = DslNamingRules.sanitizeTypeName(" list<invalid*> /*comment*/ ")
        result shouldBe "List"
    }

    @Test
    fun `toFunName lowercases first letter and unwraps keywords`() {
        DslNamingRules.toFunName("CreateNode") shouldBe "createNode"
        DslNamingRules.toFunName("Class") shouldBe "jsClass"
    }
}

package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.codegen.generator.dsl.DslNamingRules
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class DslGeneratorSanitizerTest : ShouldSpec({

    should("sanitizeTypeName removes generics and fixes casing") {
        val result = DslNamingRules.sanitizeTypeName(" list<invalid*> /*comment*/ ")
        result shouldBe "List"
    }

    should("toFunName lowercases first letter and unwraps keywords") {
        DslNamingRules.toFunName("CreateNode") shouldBe "createNode"
        DslNamingRules.toFunName("Class") shouldBe "jsClass"
    }
})

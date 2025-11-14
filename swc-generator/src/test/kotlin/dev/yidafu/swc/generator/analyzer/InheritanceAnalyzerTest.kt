package dev.yidafu.swc.generator.analyzer

import dev.yidafu.swc.generator.model.typescript.TypeReference
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class InheritanceAnalyzerTest : AnnotationSpec() {

    private fun declaration(name: String, vararg parents: String) =
        TypeScriptDeclaration.InterfaceDeclaration(
            name = name,
            extends = parents.map { TypeReference(it) }
        )

    @Test
    fun `analyzer resolves descendants and roots`() {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node"),
            declaration("Literal", "Expression"),
            declaration("Identifier", "Literal"),
            declaration("Standalone")
        )

        val analyzer = InheritanceAnalyzer(declarations)

        analyzer.findAllChildrenByParent("Expression") shouldBe listOf("Literal")
        analyzer.findAllGrandChildren("Node").shouldContainExactly(listOf("Expression", "Literal", "Identifier"))
        analyzer.findRootTypes().shouldContainExactly(listOf("Node", "Standalone"))
        analyzer.isDescendantOf("Identifier", "Node") shouldBe true
        analyzer.isDescendantOf("Standalone", "Node") shouldBe false
    }

    @Test
    fun `analyzer detects cycles`() {
        val declarations = listOf(
            declaration("A", "B"),
            declaration("B", "A")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        analyzer.detectCycles().isNotEmpty() shouldBe true
    }
}


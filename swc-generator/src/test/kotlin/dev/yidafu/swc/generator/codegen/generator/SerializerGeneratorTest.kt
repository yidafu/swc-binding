package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import java.io.File

class SerializerGeneratorTest : AnnotationSpec() {

    private fun interfaceDecl(name: String, parents: List<KotlinType> = emptyList()): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = ClassModifier.Interface,
            properties = emptyList(),
            parents = parents,
            annotations = emptyList()
        )
    }

    @Test
    fun `writeToFile creates serializer module`() {
        val tempFile = File.createTempFile("serializer", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val declarations = listOf(
            interfaceDecl("Node"),
            interfaceDecl("Identifier", listOf(KotlinType.Simple("Node"))),
            interfaceDecl("ModuleItem"),
            interfaceDecl("ModuleDeclaration", listOf(KotlinType.Simple("ModuleItem"))),
            interfaceDecl("ImportDeclaration", listOf(KotlinType.Simple("ModuleDeclaration")))
        )

        generator.writeToFile(tempFile.absolutePath, declarations)

        tempFile.exists().shouldBeTrue()
        val content = tempFile.readText()
        content.shouldContain("SerializersModule")
        content.shouldContain("polymorphic(Identifier::class)")
        content.shouldContain("subclass(IdentifierImpl::class)")
        content.shouldContain("polymorphic(Node::class)")
        content.shouldContain("polymorphic(ModuleItem::class)")
        content.shouldContain("polymorphic(ModuleDeclaration::class)")
        content.shouldContain("subclass(ImportDeclarationImpl::class)")
    }
}


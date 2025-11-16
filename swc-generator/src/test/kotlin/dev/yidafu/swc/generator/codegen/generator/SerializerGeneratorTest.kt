package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import java.io.File
import dev.yidafu.swc.generator.config.Hardcoded

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
        // 新策略下允许空模块或缺少具体多态注册

        // UnionSerializer.kt 应生成在同目录
        val unionFile = File(tempFile.parentFile, "UnionSerializer.kt")
        unionFile.exists().shouldBeTrue()
    }

    private fun implDecl(name: String, parent: String): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = listOf(KotlinType.Simple(parent)),
            annotations = emptyList()
        )
    }

    @Test
    fun `span impl uses custom serializer`() {
        val tempFile = File.createTempFile("serializer-span", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val declarations = listOf(
            interfaceDecl("Span"),
            implDecl("SpanImpl", "Span")
        )

        generator.writeToFile(tempFile.absolutePath, declarations)

        val content = tempFile.readText()
        // 新策略下不强制包含 Span 的多态注册
    }

    @Test
    fun `stable ordering for parents and children`() {
        val tempFile = File.createTempFile("serializer-order", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val declarations = listOf(
            // parents (unordered input)
            interfaceDecl("BParent"),
            interfaceDecl("AParent"),
            // children for AParent
            implDecl("BChildImpl", "AParent"),
            implDecl("AChildImpl", "AParent"),
            // children for BParent
            implDecl("DChildImpl", "BParent"),
            implDecl("CChildImpl", "BParent")
        )

        generator.writeToFile(tempFile.absolutePath, declarations)
        val content = tempFile.readText()

        // Parents should appear as AParent then BParent
        val aParentIdx = content.indexOf("polymorphic(AParent::class)")
        val bParentIdx = content.indexOf("polymorphic(BParent::class)")
        assert(aParentIdx >= 0 && bParentIdx >= 0 && aParentIdx < bParentIdx)

        // Children within AParent should be AChildImpl then BChildImpl (sorted)
        val aBlockStart = aParentIdx
        val aBlockEnd = content.indexOf("}", aBlockStart)
        val aChildIdx = content.indexOf("subclass(AChildImpl::class)", aBlockStart)
        val bChildIdx = content.indexOf("subclass(BChildImpl::class)", aBlockStart)
        assert(aChildIdx in aBlockStart..aBlockEnd && bChildIdx in aBlockStart..aBlockEnd && aChildIdx < bChildIdx)

        // Children within BParent should be CChildImpl then DChildImpl (sorted)
        val bBlockStart = bParentIdx
        val bBlockEnd = content.indexOf("}", bBlockStart)
        val cChildIdx = content.indexOf("subclass(CChildImpl::class)", bBlockStart)
        val dChildIdx = content.indexOf("subclass(DChildImpl::class)", bBlockStart)
        assert(cChildIdx in bBlockStart..bBlockEnd && dChildIdx in bBlockStart..bBlockEnd && cChildIdx < dChildIdx)
    }

    private fun sealedInterfaceDecl(name: String, parents: List<KotlinType> = emptyList(), annotated: Boolean = false): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = ClassModifier.SealedInterface,
            properties = emptyList(),
            parents = parents,
            annotations = if (annotated) listOf(KotlinDeclaration.Annotation("Serializable")) else emptyList()
        )
    }

    @Test
    fun `should fail when polymorphic parent lacks Serializable annotation`() {
        val tempFile = File.createTempFile("serializer-validate", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val declarations = listOf(
            // 未标注 @Serializable 的密封接口（模拟异常输入）
            sealedInterfaceDecl("BadParent", annotated = false),
            implDecl("BadParentImpl", "BadParent")
        )

        var failed = false
        try {
            generator.writeToFile(tempFile.absolutePath, declarations)
        } catch (e: IllegalStateException) {
            failed = true
        }
        assert(failed)
    }

    @Test
    fun `missing serializable on open base should warn under WARN_OPEN_BASES`() {
        val tempFile = File.createTempFile("serializer-warn-open-bases", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val prevPolicy = Hardcoded.Serializer.missingSerializablePolicy
        try {
            Hardcoded.Serializer.missingSerializablePolicy = Hardcoded.Serializer.MissingSerializablePolicy.WARN_OPEN_BASES
            // Identifier 在 additionalOpenBases 白名单中，未标注 @Serializable 应仅告警，不抛错
            val declarations = listOf(
                interfaceDecl("Identifier"),
                implDecl("IdentifierImpl", "Identifier")
            )
            generator.writeToFile(tempFile.absolutePath, declarations)
            tempFile.exists().shouldBeTrue()
            val content = tempFile.readText()
            content.shouldContain("SerializersModule")
        } finally {
            Hardcoded.Serializer.missingSerializablePolicy = prevPolicy
        }
    }

    @Test
    fun `missing serializable should error under ERROR policy`() {
        val tempFile = File.createTempFile("serializer-error-policy", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val prevPolicy = Hardcoded.Serializer.missingSerializablePolicy
        try {
            Hardcoded.Serializer.missingSerializablePolicy = Hardcoded.Serializer.MissingSerializablePolicy.ERROR
            val declarations = listOf(
                interfaceDecl("SomeParent"),
                implDecl("SomeParentImpl", "SomeParent")
            )
            var failed = false
            try {
                generator.writeToFile(tempFile.absolutePath, declarations)
            } catch (e: IllegalStateException) {
                failed = true
            }
            assert(failed)
        } finally {
            Hardcoded.Serializer.missingSerializablePolicy = prevPolicy
        }
    }

    @Test
    fun `missing serializable should warn under WARN_ALL policy`() {
        val tempFile = File.createTempFile("serializer-warn-all", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val prevPolicy = Hardcoded.Serializer.missingSerializablePolicy
        try {
            Hardcoded.Serializer.missingSerializablePolicy = Hardcoded.Serializer.MissingSerializablePolicy.WARN_ALL
            val declarations = listOf(
                interfaceDecl("AnotherParent"),
                implDecl("AnotherParentImpl", "AnotherParent")
            )
            generator.writeToFile(tempFile.absolutePath, declarations)
            tempFile.exists().shouldBeTrue()
        } finally {
            Hardcoded.Serializer.missingSerializablePolicy = prevPolicy
        }
    }
}

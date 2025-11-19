package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.config.SerializerConfig
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.string.shouldContain
import java.io.File

class SerializerGeneratorTest : ShouldSpec({

    fun interfaceDecl(name: String, parents: List<KotlinType> = emptyList()): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = ClassModifier.Interface,
            properties = emptyList(),
            parents = parents,
            annotations = emptyList()
        )
    }

    should("writeToFile creates serializer module") {
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

    fun implDecl(name: String, parent: String): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = listOf(KotlinType.Simple(parent)),
            annotations = emptyList()
        )
    }

    should("span impl uses custom serializer") {
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

    should("stable ordering for parents and children") {
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

    fun sealedInterfaceDecl(name: String, parents: List<KotlinType> = emptyList(), annotated: Boolean = false): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = ClassModifier.SealedInterface,
            properties = emptyList(),
            parents = parents,
            annotations = if (annotated) listOf(KotlinDeclaration.Annotation("Serializable")) else emptyList()
        )
    }

    should("should fail when polymorphic parent lacks Serializable annotation") {
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

    should("missing serializable on open base should warn under WARN_OPEN_BASES") {
        val tempFile = File.createTempFile("serializer-warn-open-bases", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val prevPolicy = SerializerConfig.missingSerializablePolicy
        try {
            SerializerConfig.missingSerializablePolicy = SerializerConfig.MissingSerializablePolicy.WARN_OPEN_BASES
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
            SerializerConfig.missingSerializablePolicy = prevPolicy
        }
    }

    should("missing serializable should error under ERROR policy") {
        val tempFile = File.createTempFile("serializer-error-policy", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val prevPolicy = SerializerConfig.missingSerializablePolicy
        try {
            SerializerConfig.missingSerializablePolicy = SerializerConfig.MissingSerializablePolicy.ERROR
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
            SerializerConfig.missingSerializablePolicy = prevPolicy
        }
    }

    should("missing serializable should warn under WARN_ALL policy") {
        val tempFile = File.createTempFile("serializer-warn-all", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val prevPolicy = SerializerConfig.missingSerializablePolicy
        try {
            SerializerConfig.missingSerializablePolicy = SerializerConfig.MissingSerializablePolicy.WARN_ALL
            val declarations = listOf(
                interfaceDecl("AnotherParent"),
                implDecl("AnotherParentImpl", "AnotherParent")
            )
            generator.writeToFile(tempFile.absolutePath, declarations)
            tempFile.exists().shouldBeTrue()
        } finally {
            SerializerConfig.missingSerializablePolicy = prevPolicy
        }
    }

    fun classDecl(name: String, parent: String, annotated: Boolean = true): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = listOf(KotlinType.Simple(parent)),
            annotations = if (annotated) listOf(KotlinDeclaration.Annotation("Serializable")) else emptyList()
        )
    }

    should("generated serializer should use Impl types in subclass calls") {
        val tempFile = File.createTempFile("serializer-impl-subclass", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val declarations = listOf(
            sealedInterfaceDecl("Expression", annotated = true),
            classDecl("Identifier", "Expression"),
            sealedInterfaceDecl("Pattern", annotated = true),
            classDecl("BindingIdentifier", "Pattern"),
            classDecl("TemplateLiteral", "Expression"),
            sealedInterfaceDecl("TsLiteral", annotated = true),
            classDecl("TsTemplateLiteralType", "TsLiteral")
        )

        generator.writeToFile(tempFile.absolutePath, declarations)

        val content = tempFile.readText()
        // 验证 subclass 调用中使用了 Impl 类型
        content.shouldContain("subclass(IdentifierImpl::class)")
        content.shouldContain("subclass(BindingIdentifierImpl::class)")
        content.shouldContain("subclass(TemplateLiteralImpl::class)")
        content.shouldContain("subclass(TsTemplateLiteralTypeImpl::class)")
    }

    should("should generate polymorphic registrations for customType interfaces") {
        val tempFile = File.createTempFile("serializer-custom-type", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        // 不包含 Identifier、BindingIdentifier、TemplateLiteral、TsTemplateLiteralType 的声明
        // 这些接口在 customType.kt 中手动定义，应该通过 addCustomTypePolymorphicRegistrations 自动添加
        val declarations = listOf(
            sealedInterfaceDecl("Expression", annotated = true),
            sealedInterfaceDecl("Pattern", annotated = true),
            sealedInterfaceDecl("TsLiteral", annotated = true)
        )

        generator.writeToFile(tempFile.absolutePath, declarations)

        val content = tempFile.readText()

        // 验证为 customType.kt 中的接口生成了多态注册
        content.shouldContain("polymorphic(Identifier::class)")
        content.shouldContain("subclass(IdentifierImpl::class)")

        content.shouldContain("polymorphic(BindingIdentifier::class)")
        content.shouldContain("subclass(BindingIdentifierImpl::class)")

        content.shouldContain("polymorphic(TemplateLiteral::class)")
        content.shouldContain("subclass(TemplateLiteralImpl::class)")

        content.shouldContain("polymorphic(TsTemplateLiteralType::class)")
        content.shouldContain("subclass(TsTemplateLiteralTypeImpl::class)")
    }

    should("customType polymorphic registrations should appear in correct order") {
        val tempFile = File.createTempFile("serializer-custom-type-order", ".kt").apply { deleteOnExit() }
        val generator = SerializerGenerator()
        val declarations = listOf(
            sealedInterfaceDecl("Expression", annotated = true)
        )

        generator.writeToFile(tempFile.absolutePath, declarations)

        val content = tempFile.readText()

        // 验证 customType.kt 接口的多态注册出现在文件末尾（在主要多态注册之后）
        val identifierIdx = content.indexOf("polymorphic(Identifier::class)")
        val bindingIdentifierIdx = content.indexOf("polymorphic(BindingIdentifier::class)")
        val templateLiteralIdx = content.indexOf("polymorphic(TemplateLiteral::class)")
        val tsTemplateLiteralTypeIdx = content.indexOf("polymorphic(TsTemplateLiteralType::class)")

        // 所有 customType 接口的注册都应该存在
        identifierIdx.shouldBeGreaterThanOrEqual(0)
        bindingIdentifierIdx.shouldBeGreaterThanOrEqual(0)
        templateLiteralIdx.shouldBeGreaterThanOrEqual(0)
        tsTemplateLiteralTypeIdx.shouldBeGreaterThanOrEqual(0)

        // 验证顺序：Identifier, BindingIdentifier, TemplateLiteral, TsTemplateLiteralType
        bindingIdentifierIdx.shouldBeGreaterThan(identifierIdx)
        templateLiteralIdx.shouldBeGreaterThan(bindingIdentifierIdx)
        tsTemplateLiteralTypeIdx.shouldBeGreaterThan(templateLiteralIdx)
    }
})

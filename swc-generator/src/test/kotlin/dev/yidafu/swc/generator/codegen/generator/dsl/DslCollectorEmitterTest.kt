package dev.yidafu.swc.generator.codegen.generator.dsl

import dev.yidafu.swc.generator.codegen.generator.DefaultPoetGenerator
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.nio.file.Paths

class DslCollectorEmitterTest : AnnotationSpec() {

    private val nodeInterface = classDecl("Node", ClassModifier.Interface)
    private val literalInterface = classDecl(
        "Literal",
        ClassModifier.Interface,
        parents = listOf(KotlinType.Simple("Node")),
        properties = listOf(prop("value"))
    )
    private val literalImpl = classDecl(
        "LiteralImpl",
        ClassModifier.FinalClass,
        parents = listOf(KotlinType.Simple("Literal"))
    )
    private val nodeKindEnum = classDecl(
        "NodeKind",
        ClassModifier.EnumClass,
        enumEntries = listOf(
            KotlinDeclaration.EnumEntry("Literal"),
            KotlinDeclaration.EnumEntry("Identifier")
        )
    )

    private fun modelContext(): DslModelContext {
        val classes = listOf(
            nodeInterface,
            literalInterface,
            literalImpl,
            nodeKindEnum,
            classDecl("HasSpan", ClassModifier.Interface)
        )
        val props = mapOf(
            "Node" to listOf(
                prop("child", KotlinType.Simple("Literal")),
                prop("kind", KotlinType.Simple("NodeKind"))
            ),
            "HasSpan" to listOf(prop("span"))
        )
        return DslModelContext(classes, props)
    }

    @Test
    fun `collector skips configured receivers`() {
        val collector = DslExtensionCollector(modelContext())
        val result = collector.collect()

        result.groups.keys.shouldContainAll("Node")
        result.groups.keys.shouldNotContain("HasSpan")
    }

    @Test
    fun `collector falls back to interface when impl missing`() {
        val context = modelContext()
        val classesWithoutImpl = context.classDecls.filterNot { it.name == "LiteralImpl" }
        val collector = DslExtensionCollector(DslModelContext(classesWithoutImpl, context.classAllPropertiesMap))

        val result = collector.collect()
        val nodeFns = result.groups["Node"].orEmpty()

        // 新逻辑：没有具体实现类时不再回落到接口，故不生成扩展
        nodeFns.shouldHaveSize(0)
    }

    @Test
    fun `file emitter generates receiver and create files`() {
        val context = modelContext()
        val collector = DslExtensionCollector(context)
        val collection = collector.collect()

        val emitter = DslFileEmitter(context, DefaultPoetGenerator())
        val files = emitter.emit(collection, Paths.get("build/dsl-test"))

        files.shouldHaveSize(collection.groups.size + 1)
        val nodeFile = files.first { it.outputPath.fileName.toString() == "Node.kt" }
        val createFile = files.first { it.outputPath.fileName.toString() == "create.kt" }

        nodeFile.fileSpec!!.toString().shouldContain("fun Node.literal")
        // 新逻辑：仅为具体类生成 create 函数
        createFile.fileSpec!!.toString().shouldContain("fun createLiteralImpl")
    }

    @Test
    fun `collector skips enum typed property`() {
        val collector = DslExtensionCollector(modelContext())
        val result = collector.collect()

        val nodeFunNames = result.groups["Node"].orEmpty().map { it.funName }
        nodeFunNames.shouldContain("Literal")
        nodeFunNames.shouldNotContain("NodeKind")
    }

    private fun classDecl(
        name: String,
        modifier: ClassModifier,
        properties: List<KotlinDeclaration.PropertyDecl> = emptyList(),
        parents: List<KotlinType> = emptyList(),
        enumEntries: List<KotlinDeclaration.EnumEntry> = emptyList()
    ): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = modifier,
            properties = properties,
            parents = parents,
            enumEntries = enumEntries,
            annotations = emptyList()
        )
    }

    private fun prop(
        name: String,
        type: KotlinType = KotlinType.StringType
    ): KotlinDeclaration.PropertyDecl {
        return KotlinDeclaration.PropertyDecl(
            name = name,
            type = type,
            modifier = PropertyModifier.Var,
            annotations = emptyList()
        )
    }
}

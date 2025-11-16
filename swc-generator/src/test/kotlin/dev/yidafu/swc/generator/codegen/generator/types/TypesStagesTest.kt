package dev.yidafu.swc.generator.codegen.generator.types

import com.squareup.kotlinpoet.FileSpec
import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.codegen.generator.PoetGenerator
import dev.yidafu.swc.generator.codegen.generator.types.stages.ConcreteClassStage
import dev.yidafu.swc.generator.codegen.generator.types.stages.ImplementationStage
import dev.yidafu.swc.generator.codegen.generator.types.stages.InterfaceStage
import dev.yidafu.swc.generator.codegen.generator.types.stages.TypeAliasStage
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import java.nio.file.Paths

class TypesStagesTest : AnnotationSpec() {
    private fun newContext(
        classDecls: MutableList<KotlinDeclaration.ClassDecl>,
        typeAliases: MutableList<KotlinDeclaration.TypeAliasDecl> = mutableListOf(),
        poetGenerator: PoetGenerator = RecordingPoetGenerator()
    ): Pair<TypesGenerationContext, RecordingPoetGenerator> {
        val registry = InterfaceRegistry(classDecls)
        val recordingPoet = poetGenerator as? RecordingPoetGenerator ?: RecordingPoetGenerator()
        val outputPath = Paths.get("build/tmp/types.kt")
        val layout = TypesFileLayout(
            packageName = "dev.yidafu.test",
            defaultImports = emptyArray(),
            originalOutputPath = outputPath,
            interfacesWithDedicatedFiles = emptySet()
        )
        val context = TypesGenerationContext(
            input = TypesGenerationInput(classDecls, typeAliases, outputPath),
            fileLayout = layout,
            interfaceRegistry = registry,
            poet = recordingPoet,
            analyzer = InheritanceAnalyzer(),
            propertyCache = mutableMapOf()
        )
        return context to recordingPoet
    }

    @Test
    fun `interface stage emits sorted interfaces`() {
        val base = classDecl("Base", ClassModifier.Interface)
        val child = classDecl("Child", ClassModifier.Interface, parents = listOf(KotlinType.Simple("Base")))
        val another = classDecl("Another", ClassModifier.Interface)

        val (context, recorder) = newContext(mutableListOf(child, base, another))
        val stage = InterfaceStage(InterfaceEmitter(context.interfaceRegistry))

        stage.run(context)

        recorder.emitted.shouldContainExactlyInAnyOrder(listOf("Base", "Child", "Another"))
    }

    @Test
    fun `concrete class stage ignores interfaces and impl classes`() {
        val concrete = classDecl("NodeImpl", ClassModifier.FinalClass)
        val iface = classDecl("Node", ClassModifier.Interface)
        val dataClass = classDecl("ValueNode", ClassModifier.DataClass)

        val (context, recorder) = newContext(mutableListOf(concrete, iface, dataClass))
        val stage = ConcreteClassStage(ConcreteClassEmitter(context.interfaceRegistry))

        stage.run(context)

        recorder.emitted.shouldContainExactlyInAnyOrder(listOf("ValueNode"))
    }

    @Test
    fun `implementation stage emits only leaf interfaces`() {
        val node = classDecl("Node", ClassModifier.Interface, properties = listOf(prop("type")))
        val expr = classDecl(
            "Expression",
            ClassModifier.Interface,
            parents = listOf(KotlinType.Simple("Node")),
            properties = listOf(prop("span", KotlinType.Simple("Span")))
        )
        val literal = classDecl(
            "Literal",
            ClassModifier.Interface,
            parents = listOf(KotlinType.Simple("Expression")),
            properties = listOf(prop("value"))
        )

        val (context, recorder) = newContext(mutableListOf(node, expr, literal))
        val stage = ImplementationStage(ImplementationEmitter(context.interfaceRegistry))

        stage.run(context)

        recorder.emitted.shouldContain("LiteralImpl")
        context.propertyCache.keys.shouldContainAll(listOf("Literal", "Expression", "Node"))
    }

    @Test
    fun `type alias stage emits aliases via poet`() {
        val alias = KotlinDeclaration.TypeAliasDecl("AliasA", KotlinType.Simple("String"))
        val (context, recorder) = newContext(mutableListOf(), mutableListOf(alias))
        val stage = TypeAliasStage(TypeAliasEmitter())

        stage.run(context)

        recorder.aliases.shouldContainExactly(listOf("AliasA"))
    }

    private fun classDecl(
        name: String,
        modifier: ClassModifier,
        properties: List<KotlinDeclaration.PropertyDecl> = emptyList(),
        parents: List<KotlinType> = emptyList()
    ): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = modifier,
            properties = properties,
            parents = parents,
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

    private class RecordingPoetGenerator : PoetGenerator {
        val emitted = mutableListOf<String>()
        val aliases = mutableListOf<String>()

        override fun emitType(
            fileBuilder: FileSpec.Builder,
            declaration: KotlinDeclaration.ClassDecl,
            interfaceNames: Set<String>,
            declLookup: Map<String, KotlinDeclaration.ClassDecl>
        ): Boolean {
            emitted += declaration.name
            return true
        }

        override fun emitTypeAlias(
            fileBuilder: FileSpec.Builder,
            typeAlias: KotlinDeclaration.TypeAliasDecl
        ): Boolean {
            aliases += typeAlias.name
            return true
        }

        override fun buildFile(fileBuilder: FileSpec.Builder): FileSpec = fileBuilder.build()
    }
}

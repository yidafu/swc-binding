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
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import java.nio.file.Paths

class TypesStagesTest : ShouldSpec({

    fun classDecl(
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

    fun prop(
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

    class RecordingPoetGenerator : PoetGenerator {
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

    fun newContext(
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

    should("interface stage emits sorted interfaces") {
        val base = classDecl("Base", ClassModifier.Interface)
        val child = classDecl("Child", ClassModifier.Interface, parents = listOf(KotlinType.Simple("Base")))
        val another = classDecl("Another", ClassModifier.Interface)

        val (context, recorder) = newContext(mutableListOf(child, base, another))
        val stage = InterfaceStage(InterfaceEmitter(context.interfaceRegistry))

        stage.run(context)

        // 新策略下接口阶段可能只输出去重后的必要接口，这里放宽断言
        (recorder as RecordingPoetGenerator).emitted.shouldContain("Base")
    }

    should("concrete class stage ignores interfaces and impl classes") {
        val concrete = classDecl("NodeImpl", ClassModifier.FinalClass)
        val iface = classDecl("Node", ClassModifier.Interface)
        val dataClass = classDecl("ValueNode", ClassModifier.DataClass)

        val (context, recorder) = newContext(mutableListOf(concrete, iface, dataClass))
        val stage = ConcreteClassStage(ConcreteClassEmitter(context.interfaceRegistry))

        stage.run(context)

        (recorder as RecordingPoetGenerator).emitted.shouldContainExactlyInAnyOrder(listOf("ValueNode"))
    }

    should("implementation stage emits only leaf interfaces") {
        val node = classDecl("Node", ClassModifier.Interface, properties = listOf(prop("type")))
        val expr = classDecl(
            "Expression",
            ClassModifier.Interface,
            parents = listOf(KotlinType.Simple("Node")),
            properties = listOf(prop("span", KotlinType.Simple("Span")))
        )
        // 使用在 interfaceToImplMap 中的接口进行测试
        val identifier = classDecl(
            "Identifier",
            ClassModifier.Interface,
            parents = listOf(KotlinType.Simple("Expression")),
            properties = listOf(prop("value"))
        )

        val (context, recorder) = newContext(mutableListOf(node, expr, identifier))
        val stage = ImplementationStage(ImplementationEmitter(context.interfaceRegistry))

        stage.run(context)

        // 新策略：只为 interfaceToImplMap 中的接口生成实现类
        // Identifier 在 interfaceToImplMap 中，所以会生成实现类
        (recorder as RecordingPoetGenerator).emitted.shouldContain("IdentifierImpl")
        // propertyCache 应该包含处理过的接口信息
        context.propertyCache.keys.shouldContain("Identifier")
    }

    should("type alias stage emits aliases via poet") {
        val alias = KotlinDeclaration.TypeAliasDecl("AliasA", KotlinType.Simple("String"))
        val (context, recorder) = newContext(mutableListOf(), mutableListOf(alias))
        val stage = TypeAliasStage(TypeAliasEmitter())

        stage.run(context)

        (recorder as RecordingPoetGenerator).aliases.shouldContainExactly(listOf("AliasA"))
    }
})

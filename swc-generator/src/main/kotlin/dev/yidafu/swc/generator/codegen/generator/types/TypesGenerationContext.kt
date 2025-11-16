package dev.yidafu.swc.generator.codegen.generator.types

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.codegen.generator.PoetGenerator
import dev.yidafu.swc.generator.codegen.pipeline.GenerationContext
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFile
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import java.nio.file.Path
import java.util.LinkedHashMap

data class TypesGenerationInput(
    val classDecls: MutableList<KotlinDeclaration.ClassDecl>,
    val typeAliases: MutableList<KotlinDeclaration.TypeAliasDecl>,
    val outputPath: Path
)

class TypesGenerationContext(
    val input: TypesGenerationInput,
    val fileLayout: TypesFileLayout,
    val interfaceRegistry: InterfaceRegistry,
    val poet: PoetGenerator,
    val analyzer: InheritanceAnalyzer = InheritanceAnalyzer(),
    val propertyCache: MutableMap<String, List<KotlinDeclaration.PropertyDecl>> = LinkedHashMap()
) : GenerationContext {
    override val generatedFiles: MutableList<GeneratedFile> = mutableListOf()
}


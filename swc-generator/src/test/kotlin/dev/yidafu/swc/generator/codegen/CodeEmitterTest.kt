package dev.yidafu.swc.generator.codegen

import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import dev.yidafu.swc.generator.transformer.TransformResult
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import java.io.File
import kotlin.io.path.createTempDirectory

class CodeEmitterTest : AnnotationSpec() {

    private fun sampleClass(): KotlinDeclaration.ClassDecl = KotlinDeclaration.ClassDecl(
        name = "Sample",
        modifier = ClassModifier.Interface,
        properties = listOf(
            KotlinDeclaration.PropertyDecl(
                name = "value",
                type = KotlinType.StringType,
                modifier = PropertyModifier.Var
            )
        ),
        parents = emptyList(),
        annotations = emptyList()
    )

    private fun transformResult(): TransformResult {
        val klass = sampleClass()
        return TransformResult(
            classDecls = listOf(klass),
            classAllPropertiesMap = mapOf(klass.name to klass.properties),
            typeAliases = listOf(
                KotlinDeclaration.TypeAliasDecl("Alias", KotlinType.StringType)
            )
        )
    }

    @Test
    fun `dry run emit succeeds without touching filesystem`() {
        val tempDir = createTempDirectory().toFile()
        val config = GeneratorConfig(
            outputTypesPath = File(tempDir, "types.kt").absolutePath,
            outputSerializerPath = File(tempDir, "serializer.kt").absolutePath,
            outputDslDir = File(tempDir, "dsl").absolutePath,
            dryRun = true
        )
        val emitter = CodeEmitter(config, SwcGeneratorConfig())

        val result = emitter.emit(transformResult())

        result.isSuccess().shouldBeTrue()
        File(config.outputTypesPath!!).exists().shouldBeFalse()
    }

    @Test
    fun `emit writes Kotlin files when not dry run`() {
        val tempDir = createTempDirectory().toFile()
        val config = GeneratorConfig(
            outputTypesPath = File(tempDir, "types.kt").absolutePath,
            outputSerializerPath = File(tempDir, "serializer.kt").absolutePath,
            outputDslDir = File(tempDir, "dsl").absolutePath,
            dryRun = false
        )
        val emitter = CodeEmitter(config, SwcGeneratorConfig())

        val result = emitter.emit(transformResult())

        result.isSuccess().shouldBeTrue()
        File(config.outputTypesPath!!).exists().shouldBeTrue()
        File(config.outputSerializerPath!!).exists().shouldBeTrue()
        File(config.outputDslDir!!, "create.kt").exists().shouldBeTrue()
    }
}

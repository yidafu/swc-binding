package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.codegen.CodeEmitter
import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.transformer.TransformResult
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class CodeGenStageTest : ShouldSpec({

    val config = Configuration.default()
    val container = mockk<DependencyContainer>()
    val emitter = mockk<CodeEmitter>()
    val stage = CodeGenStage(config, container)

    fun sampleDeclarations(): List<KotlinDeclaration> {
        val klass = KotlinDeclaration.ClassDecl(
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
        val alias = KotlinDeclaration.TypeAliasDecl("Alias", KotlinType.StringType)
        return listOf(klass, alias)
    }

    should("codegen stage builds transform result from context metadata") {
        val declarations = sampleDeclarations()
        val context = PipelineContext(config).apply {
            setMetadata("processedDeclarations", declarations)
            setMetadata("classAllPropertiesMap", mapOf("Sample" to (declarations[0] as KotlinDeclaration.ClassDecl).properties))
        }
        val transformSlot = slot<TransformResult>()
        every { container.codeEmitter } returns emitter
        every { emitter.emit(capture(transformSlot)) } returns GeneratorResultFactory.success(Unit)

        val result = stage.execute(emptyList(), context)

        result.isSuccess() shouldBe true
        transformSlot.captured.classDecls.single().name shouldBe "Sample"
        transformSlot.captured.typeAliases.single().name shouldBe "Alias"
        transformSlot.captured.classAllPropertiesMap.keys shouldBe setOf("Sample")
    }
})

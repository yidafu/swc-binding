package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import dev.yidafu.swc.generator.processor.KotlinADTProcessor
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.test.assertNotNull
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ProcessorStageTest : AnnotationSpec() {

    private val config = Configuration.default()
    private val container = mockk<DependencyContainer>()
    private val processor = mockk<KotlinADTProcessor>()
    private val stage = ProcessorStage(config, container)

    @Test
    fun `processor stage stores processed declarations`() {
        val declaration = KotlinDeclaration.ClassDecl(
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
        val context = PipelineContext(config)
        every { container.kotlinADTProcessor } returns processor
        every { container.swcGeneratorConfig } returns SwcGeneratorConfig()
        every { processor.processDeclarations(any(), any()) } returns GeneratorResultFactory.success(listOf(declaration))

        val result = stage.execute(listOf(declaration), context)

        result.isSuccess() shouldBe true
        val stored = assertNotNull(context.getMetadata<List<KotlinDeclaration>>("processedDeclarations"))
        val processed = stored.single() as KotlinDeclaration.ClassDecl
        processed.name shouldBe "Sample"
    }
}

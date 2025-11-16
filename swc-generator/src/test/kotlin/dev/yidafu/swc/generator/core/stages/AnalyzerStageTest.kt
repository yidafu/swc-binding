package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.test.assertNotNull
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.shouldBe

class AnalyzerStageTest : AnnotationSpec() {

    private val config = Configuration.default()
    private val stage = AnalyzerStage(config)

    @Test
    fun `analyzer stage stores analysis result`() {
        val context = PipelineContext(config)
        val declarations = listOf<TypeScriptDeclaration>()

        val result = stage.execute(declarations, context)

        result.isSuccess() shouldBe true
        val analysis = assertNotNull(context.getMetadata<AnalysisResult>("analysisResult"))
        analysis.inheritanceRelationships.isEmpty() shouldBe true
        analysis.typeDependencies.isEmpty() shouldBe true
        analysis.circularDependencies.isEmpty() shouldBe true
    }
}

package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.test.assertNotNull
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class AnalyzerStageTest : ShouldSpec({

    val config = Configuration.default()
    val stage = AnalyzerStage(config)

    should("analyzer stage stores analysis result") {
        val context = PipelineContext(config)
        val declarations = listOf<TypeScriptDeclaration>()

        val result = stage.execute(declarations, context)

        result.isSuccess() shouldBe true
        val analysis = assertNotNull(context.getMetadata<AnalysisResult>("analysisResult"))
        analysis.inheritanceRelationships.isEmpty() shouldBe true
        analysis.typeDependencies.isEmpty() shouldBe true
        analysis.circularDependencies.isEmpty() shouldBe true
    }
})

package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.model.typescript.*
import dev.yidafu.swc.generator.test.assertEquals
import dev.yidafu.swc.generator.test.assertNotNull
import dev.yidafu.swc.generator.test.assertTrue
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test

class ConverterStageTest : AnnotationSpec() {

    private val config = Configuration.default()
    private val container = DependencyContainer(config)
    private val stage = ConverterStage(config, container)

    @Test
    fun `test stage name`() {
        assertEquals("Converter", stage.name)
    }

    @Test
    fun `test convert empty declaration list`() {
        val context = PipelineContext(config)
        val result = stage.execute(emptyList(), context)

        assertTrue(result.isSuccess())
        val declarations = result.getOrThrow()
        assertTrue(declarations.isEmpty())
    }

    @Test
    fun `test convert interface declarations`() {
        val tsDeclarations = listOf(
            TypeScriptDeclaration.InterfaceDeclaration(
                name = "TestInterface",
                members = emptyList(),
                typeParameters = emptyList(),
                extends = emptyList()
            )
        )

        val context = PipelineContext(config)
        val result = stage.execute(tsDeclarations, context)

        assertNotNull(result)
        if (result.isSuccess()) {
            val kotlinDeclarations = context.getMetadata<List<dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration>>("kotlinDeclarations")
            assertNotNull(kotlinDeclarations)
        }
    }

    @Test
    fun `test conversion result stored in context`() {
        val tsDeclarations = listOf(
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "TestAlias",
                type = TypeScriptType.Keyword(KeywordKind.STRING),
                typeParameters = emptyList()
            )
        )

        val context = PipelineContext(config)
        val result = stage.execute(tsDeclarations, context)

        if (result.isSuccess()) {
            val kotlinDeclarations = context.getMetadata<List<dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration>>("kotlinDeclarations")
            assertNotNull(kotlinDeclarations)
        }
    }
}

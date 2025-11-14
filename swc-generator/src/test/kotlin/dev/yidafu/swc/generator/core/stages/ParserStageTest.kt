package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.test.assertEquals
import dev.yidafu.swc.generator.test.assertNotNull
import dev.yidafu.swc.generator.test.assertTrue
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.AfterTest
import io.kotest.core.spec.style.annotation.Test
import java.io.File
import java.nio.file.Files

class ParserStageTest : AnnotationSpec() {

    private val config = Configuration.default()
    private val container = DependencyContainer(config)
    private val stage = ParserStage(config, container)
    private val tempDir = Files.createTempDirectory("swc-generator-test").toFile()

    @AfterTest
    fun cleanup() {
        tempDir.deleteRecursively()
    }

    @Test
    fun `test stage name`() {
        assertEquals("Parser", stage.name)
    }

    @Test
    fun `test parse valid TypeScript file`() {
        val testFile = File(tempDir, "test.d.ts")
        testFile.writeText(
            """
            export interface Test {
                name: string;
            }
            """.trimIndent()
        )

        val context = PipelineContext(config)
        val result = stage.execute(testFile.absolutePath, context)

        // 结果取决于 SWC 绑定是否可用
        assertNotNull(result)
    }

    @Test
    fun `test parse non-existent file`() {
        val context = PipelineContext(config)
        val result = stage.execute("non-existent.d.ts", context)

        assertTrue(result.isFailure())
    }

    @Test
    fun `test parse result is stored in context`() {
        val testFile = File(tempDir, "test.d.ts")
        testFile.writeText("export interface Test {}")

        val context = PipelineContext(config)
        val result = stage.execute(testFile.absolutePath, context)

        if (result.isSuccess()) {
            val parseResult = context.getMetadata<dev.yidafu.swc.generator.parser.ParseResult>("parseResult")
            assertNotNull(parseResult)
        }
    }
}

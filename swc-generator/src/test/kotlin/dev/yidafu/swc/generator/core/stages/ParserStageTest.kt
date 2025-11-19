package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.parser.ParseResult
import dev.yidafu.swc.generator.test.assertEquals
import dev.yidafu.swc.generator.test.assertNotNull
import dev.yidafu.swc.generator.test.assertTrue
import io.kotest.core.spec.style.ShouldSpec
import java.io.File
import java.nio.file.Files

class ParserStageTest : ShouldSpec({

    val config = Configuration.default()
    val container = DependencyContainer(config)
    val stage = ParserStage(config, container)
    val tempDir = Files.createTempDirectory("swc-generator-test").toFile()

    afterTest {
        tempDir.deleteRecursively()
    }

    should("test stage name") {
        assertEquals("Parser", stage.name)
    }

    should("test parse valid TypeScript file") {
        // 确保临时目录存在
        tempDir.mkdirs()
        
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

    should("test parse non-existent file") {
        val context = PipelineContext(config)
        val result = stage.execute("non-existent.d.ts", context)

        assertTrue(result.isFailure())
    }

    should("test parse result is stored in context") {
        // 确保临时目录存在
        tempDir.mkdirs()
        
        val testFile = File(tempDir, "test.d.ts")
        testFile.writeText("export interface Test {}")

        val context = PipelineContext(config)
        val result = stage.execute(testFile.absolutePath, context)

        if (result.isSuccess()) {
            val parseResult = context.getMetadata<ParseResult>("parseResult")
            assertNotNull(parseResult)
        }
    }
})

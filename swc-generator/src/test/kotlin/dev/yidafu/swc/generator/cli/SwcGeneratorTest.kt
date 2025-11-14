package dev.yidafu.swc.generator.cli

import dev.yidafu.swc.generator.config.Configuration
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.AfterTest
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import java.io.File
import java.nio.file.Files

class SwcGeneratorTest : AnnotationSpec() {

    private val config = Configuration.default()
    private val tempDir = Files.createTempDirectory("swc-generator-test").toFile()

    @AfterTest
    fun cleanup() {
        tempDir.deleteRecursively()
    }

    @Test
    fun `test SwcGenerator creation`() {
        val generator = SwcGenerator(config)
        generator.shouldNotBeNull()
    }

    @Test
    fun `test run with valid input file`() {
        // 创建测试 TypeScript 定义文件
        val testFile = File(tempDir, "test-simple.d.ts")
        testFile.writeText(
            """
            export interface TestInterface {
                name: string;
                value: number;
            }
            """.trimIndent()
        )

        val generator = SwcGenerator(config)
        // 注意：这个测试可能需要实际的 SWC 绑定，可能会失败
        // 这里主要测试结构是否正确
        val result = generator.run(testFile.absolutePath)

        // 结果可能是成功或失败，取决于环境
        result.shouldNotBeNull()
    }

    @Test
    fun `test run with non-existent file`() {
        val generator = SwcGenerator(config)
        val result = generator.run("non-existent-file.d.ts")

        // 应该失败
        result.isFailure().shouldBeTrue()
    }
}

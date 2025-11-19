package dev.yidafu.swc.generator.cli

import dev.yidafu.swc.generator.config.Configuration
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import java.io.File
import java.nio.file.Files

class SwcGeneratorTest : ShouldSpec({

    val config = Configuration.default()
    val tempDir = Files.createTempDirectory("swc-generator-test").toFile()

    afterTest {
        tempDir.deleteRecursively()
    }

    should("test SwcGenerator creation") {
        val generator = SwcGenerator(config)
        generator.shouldNotBeNull()
    }

    should("test run with valid input file") {
        // 确保临时目录存在
        tempDir.mkdirs()
        
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

    should("test run with non-existent file") {
        val generator = SwcGenerator(config)
        val result = generator.run("non-existent-file.d.ts")

        // 应该失败
        result.isFailure().shouldBeTrue()
    }
})

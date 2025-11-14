package dev.yidafu.swc.generator.config

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.AfterTest
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.io.File
import java.nio.file.Files

class ConfigurationLoaderTest : AnnotationSpec() {

    private val loader = ConfigurationLoader()
    private val tempDir = Files.createTempDirectory("swc-generator-test").toFile()

    @AfterTest
    fun cleanup() {
        tempDir.deleteRecursively()
    }

    @Test
    fun `test load default configuration when file does not exist`() {
        val result = loader.loadFromFile("non-existent-config.yaml")

        result.isSuccess().shouldBeTrue()
        val config = result.getOrThrow()
        config.shouldNotBeNull()
        config.input.shouldNotBeNull()
        config.output.shouldNotBeNull()
        config.rules.shouldNotBeNull()
        config.behavior.shouldNotBeNull()
    }

    @Test
    fun `test load configuration from valid YAML file`() {
        val configFile = File(tempDir, "test-config.yaml")
        val yamlContent = """
            input:
              inputPath: "test.d.ts"
              verbose: true
              debug: false
            output:
              outputTypesPath: "output/types.kt"
              outputSerializerPath: "output/serializer.kt"
              outputDslDir: "output/dsl"
              dryRun: false
            behavior:
              enableCaching: true
              enableParallelProcessing: false
        """.trimIndent()
        configFile.writeText(yamlContent)

        val result = loader.loadFromFile(configFile.absolutePath)

        result.isSuccess().shouldBeTrue()
        val config = result.getOrThrow()
        config.input.inputPath shouldBe "test.d.ts"
        config.input.verbose.shouldBeTrue()
        config.input.debug.shouldBeFalse()
        config.output.outputTypesPath shouldBe "output/types.kt"
        config.output.outputSerializerPath shouldBe "output/serializer.kt"
        config.output.outputDslDir shouldBe "output/dsl"
        config.behavior.enableCaching.shouldBeTrue()
        config.behavior.enableParallelProcessing.shouldBeFalse()
    }

    @Test
    fun `test load configuration with partial fields`() {
        val configFile = File(tempDir, "partial-config.yaml")
        val yamlContent = """
            input:
              inputPath: "custom.d.ts"
            output:
              dryRun: true
        """.trimIndent()
        configFile.writeText(yamlContent)

        val result = loader.loadFromFile(configFile.absolutePath)

        result.isSuccess().shouldBeTrue()
        val config = result.getOrThrow()
        config.input.inputPath shouldBe "custom.d.ts"
        config.output.dryRun.shouldBeTrue()
        config.rules.shouldNotBeNull()
        config.behavior.shouldNotBeNull()
    }

    @Test
    fun `test generate sample configuration`() {
        val outputPath = File(tempDir, "sample-config.yaml").absolutePath
        val result = loader.generateSampleConfig(outputPath)

        result.isSuccess().shouldBeTrue()

        val generatedFile = File(outputPath)
        generatedFile.exists().shouldBeTrue()

        // 验证生成的配置文件可以再次加载
        val loadResult = loader.loadFromFile(outputPath)
        loadResult.isSuccess().shouldBeTrue()
    }

    @Test
    fun `test load from args`() {
        val config = loader.loadFromArgs(
            inputPath = "custom-input.d.ts",
            outputTypesPath = "custom-types.kt",
            outputSerializerPath = "custom-serializer.kt",
            outputDslDir = "custom-dsl",
            verbose = true,
            debug = true,
            dryRun = true
        )

        config.input.inputPath shouldBe "custom-input.d.ts"
        config.input.verbose.shouldBeTrue()
        config.input.debug.shouldBeTrue()
        config.output.outputTypesPath shouldBe "custom-types.kt"
        config.output.outputSerializerPath shouldBe "custom-serializer.kt"
        config.output.outputDslDir shouldBe "custom-dsl"
        config.output.dryRun.shouldBeTrue()
    }

    @Test
    fun `test merge configurations`() {
        val fileConfig = Configuration.default().copy(
            input = InputConfig.default().copy(inputPath = "file-input.d.ts")
        )
        val argsConfig = Configuration.default().copy(
            input = InputConfig.default().copy(inputPath = "args-input.d.ts", verbose = true)
        )

        val merged = loader.mergeConfigurations(fileConfig, argsConfig)

        // args 配置应该优先
        merged.input.inputPath shouldBe "args-input.d.ts"
        merged.input.verbose.shouldBeTrue()
    }

    @Test
    fun `test configuration validation`() {
        val validConfig = Configuration.default().copy(
            input = InputConfig.default().copy(inputPath = "test-simple.d.ts")
        )

        // 创建测试文件
        val testFile = File(tempDir, "test-simple.d.ts")
        testFile.writeText("export interface Test {}")

        val configWithValidPath = validConfig.copy(
            input = InputConfig.default().copy(inputPath = testFile.absolutePath),
            output = validConfig.output.copy(dryRun = true)
        )

        val validationResult = configWithValidPath.validate()
        validationResult.isSuccess().shouldBeTrue()
    }

    @Test
    fun `test configuration validation with invalid path`() {
        val invalidConfig = Configuration.default().copy(
            input = InputConfig.default().copy(inputPath = "")
        )

        val validationResult = invalidConfig.validate()
        validationResult.isFailure().shouldBeTrue()
    }
}

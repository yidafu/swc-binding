package dev.yidafu.swc.generator.codegen.pipeline

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.string.shouldStartWith
import java.nio.file.Files

class GeneratedFileWriterTest : AnnotationSpec() {

    @Test
    fun `generated files contain auto header`() {
        val tempDir = Files.createTempDirectory("generated-writer-test")
        val targetPath = tempDir.resolve("Sample.kt")

        GeneratedFileWriter(parallelism = 1).use { writer ->
            val file = GeneratedFile(
                outputPath = targetPath,
                contentProducer = { "package dev.yidafu.sample\n" }
            )
            writer.write(listOf(file))
        }

        val content = Files.readString(targetPath)
        val timestampPrefix = "// Auto-generated file. Do not edit. Generated at: "
        content.shouldStartWith(timestampPrefix)
    }
}

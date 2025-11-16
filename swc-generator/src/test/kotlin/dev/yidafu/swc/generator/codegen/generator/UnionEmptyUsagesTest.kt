package dev.yidafu.swc.generator.codegen.generator

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import java.io.File

class UnionEmptyUsagesTest : AnnotationSpec() {

    @Test
    fun `generate empty shell when no usages`() {
        // 不添加任何 usage
        val tempDir = createTempDir(prefix = "union-empty-usages").apply { deleteOnExit() }
        val serializerPath = File(tempDir, "serializer.kt").absolutePath

        SerializerGenerator().use { gen ->
            gen.writeToFile(serializerPath, emptyList())
        }

        val unionFile = File(tempDir, "UnionSerializer.kt")
        unionFile.exists().shouldBeTrue()
        val content = unionFile.readText()
        content.shouldContain("No Union.Ux usages collected.")
    }
}



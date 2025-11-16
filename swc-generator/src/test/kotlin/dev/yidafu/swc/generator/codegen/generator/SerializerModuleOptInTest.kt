package dev.yidafu.swc.generator.codegen.generator

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import java.io.File

class SerializerModuleOptInTest : AnnotationSpec() {

    @Test
    fun `serializers modules annotated with OptIn ExperimentalSerializationApi`() {
        val tempDir = createTempDir(prefix = "serializer-module-optin").apply { deleteOnExit() }
        val serializerPath = File(tempDir, "serializer.kt").absolutePath

        // 仅生成 serializer.kt（无 classDecls 也应生成模块属性）
        SerializerGenerator().use { gen ->
            gen.writeToFile(serializerPath, emptyList())
        }

        val serializerFile = File(tempDir, "serializer.kt")
        serializerFile.exists().shouldBeTrue()
        val content = serializerFile.readText()

        // swcSerializersModule 应带有 OptIn(ExperimentalSerializationApi::class)
        content.shouldContain("@OptIn(ExperimentalSerializationApi::class)")
        content.shouldContain("public val swcSerializersModule: SerializersModule")

        // swcConfigSerializersModule 也应带有 OptIn(ExperimentalSerializationApi::class)
        content.shouldContain("@OptIn(ExperimentalSerializationApi::class)")
        content.shouldContain("public val swcConfigSerializersModule: SerializersModule")
    }
}



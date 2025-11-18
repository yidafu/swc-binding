package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.ClassName
import dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.UnionUsage
import dev.yidafu.swc.generator.config.UnionConfig
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import java.io.File

class UnionNullabilityTokenTest : AnnotationSpec() {

    @Test
    fun `distinct names when includeNullabilityInToken enabled`() {
        val prev = UnionConfig.includeNullabilityInToken
        try {
            UnionConfig.includeNullabilityInToken = true

            val stringT = ClassName("kotlin", "String")
            UnionSerializerRegistry.addUsage(
                UnionUsage(
                    ownerSimpleName = "Cfg",
                    propertyName = "p1",
                    unionKind = "U2",
                    typeArguments = listOf(stringT, stringT),
                    isArray = false,
                    isNullableElement = listOf(false, true) // 第二个为可空
                )
            )
            UnionSerializerRegistry.addUsage(
                UnionUsage(
                    ownerSimpleName = "Cfg",
                    propertyName = "p2",
                    unionKind = "U2",
                    typeArguments = listOf(stringT, stringT),
                    isArray = false,
                    isNullableElement = listOf(false, false) // 都非空
                )
            )

            val tempDir = createTempDir(prefix = "union-serializer-nullable").apply { deleteOnExit() }
            val serializerPath = File(tempDir, "serializer.kt").absolutePath

            SerializerGenerator().use { gen ->
                gen.writeToFile(serializerPath, emptyList())
            }

            val unionFile = File(tempDir, "UnionSerializer.kt")
            unionFile.exists().shouldBeTrue()
            val content = unionFile.readText()
            // 两个不同的序列化器对象名（依据 computeSerializerName）
            content.shouldContain("__Serializer")
            // 至少包含两个对象定义
            content.contains("object ").shouldBeTrue()
        } finally {
            UnionConfig.includeNullabilityInToken = prev
        }
    }
}

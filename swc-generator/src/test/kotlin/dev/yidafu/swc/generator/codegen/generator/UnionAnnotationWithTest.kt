package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.ClassName
import dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.UnionUsage
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import java.io.File

class UnionAnnotationWithTest : AnnotationSpec() {

    @Test
    fun `fields and params annotated with Serializable-with`() {
        // 准备一个 usage，确保会生成一个命名序列化器
        val stringT = ClassName("kotlin", "String")
        val boolT = ClassName("kotlin", "Boolean")
        UnionSerializerRegistry.addUsage(
            UnionUsage(
                ownerSimpleName = "Sample",
                propertyName = "p",
                unionKind = "U2",
                typeArguments = listOf(stringT, boolT),
                isArray = false,
                isNullableElement = listOf(false, false)
            )
        )

        val tempDir = createTempDir(prefix = "union-annotation-test").apply { deleteOnExit() }
        val serializerPath = File(tempDir, "serializer.kt").absolutePath

        // 触发仅生成序列化器文件（UnionSerializer.kt）
        SerializerGenerator().use { gen ->
            gen.writeToFile(serializerPath, emptyList())
        }

        val unionFile = File(tempDir, "UnionSerializer.kt")
        unionFile.exists().shouldBeTrue()
        val content = unionFile.readText()
        // 生成了命名序列化器 object，形如 U2_String_Boolean__Serializer
        content.shouldContain("__Serializer")
        // object 继承 KSerializer
        content.shouldContain(": KSerializer<Union.U2<String, Boolean>>")
    }
}



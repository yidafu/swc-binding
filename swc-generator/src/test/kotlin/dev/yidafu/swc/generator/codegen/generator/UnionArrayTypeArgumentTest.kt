package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.UnionUsage
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import java.io.File

class UnionArrayTypeArgumentTest : AnnotationSpec() {

    @Test
    fun `generate UnionSerializer with Array generic argument`() {
        // 预置一个使用：Config.test : Union.U2<String, Array<String>>
        val stringT = ClassName("kotlin", "String")
        val arrayOfString = ClassName("kotlin", "Array").parameterizedBy(stringT)
        UnionSerializerRegistry.addUsage(
            UnionUsage(
                ownerSimpleName = "Config",
                propertyName = "test",
                unionKind = "U2",
                typeArguments = listOf(stringT, arrayOfString),
                isArray = false,
                isNullableElement = listOf(false, false)
            )
        )

        val tempDir = createTempDir(prefix = "union-serializer-test").apply { deleteOnExit() }
        val serializerPath = File(tempDir, "serializer.kt").absolutePath

        // 触发生成
        SerializerGenerator().use { gen ->
            gen.writeToFile(serializerPath, emptyList())
        }

        val unionFile = File(tempDir, "UnionSerializer.kt")
        unionFile.exists().shouldBeTrue()
        val content = unionFile.readText()
        // 泛型形态
        content.shouldContain("KSerializer<Union.U2<String, Array<String>>>")
        // 组件序列化器应使用 ArraySerializer(serializer<String>())
        content.shouldContain("ArraySerializer(serializer<String>())")
    }
}



package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.UnionUsage
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import java.io.File

class UnionFactoryUsageTest : AnnotationSpec() {

    @Test
    fun `UnionSerializer objects delegate to UnionFactory`() {
        val stringT = ClassName("kotlin", "String")
        val intT = ClassName("kotlin", "Int")
        val arrString = ClassName("kotlin", "Array").parameterizedBy(stringT)

        // 两个使用相同组合
        UnionSerializerRegistry.addUsage(
            UnionUsage(
                ownerSimpleName = "C1",
                propertyName = "a",
                unionKind = "U3",
                typeArguments = listOf(stringT, intT, arrString),
                isArray = false,
                isNullableElement = listOf(false, false, false)
            )
        )
        UnionSerializerRegistry.addUsage(
            UnionUsage(
                ownerSimpleName = "C2",
                propertyName = "b",
                unionKind = "U3",
                typeArguments = listOf(stringT, intT, arrString),
                isArray = true,
                isNullableElement = listOf(false, false, false)
            )
        )

        val tempDir = createTempDir(prefix = "union-factory-test").apply { deleteOnExit() }
        val serializerPath = File(tempDir, "serializer.kt").absolutePath

        SerializerGenerator().use { gen ->
            gen.writeToFile(serializerPath, emptyList())
        }

        val unionFile = File(tempDir, "UnionSerializer.kt")
        unionFile.exists().shouldBeTrue()
        val content = unionFile.readText()

        // 存在工厂与缓存
        content.shouldContain("object UnionFactory")
        content.shouldContain("ConcurrentHashMap")

        // 对象使用 UnionFactory.get(...)
        content.shouldContain("UnionFactory.get(\"U3\"")
    }
}



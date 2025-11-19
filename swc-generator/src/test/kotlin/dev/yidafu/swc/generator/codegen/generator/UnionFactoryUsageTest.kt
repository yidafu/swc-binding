package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.yidafu.swc.generator.codegen.generator.UnionSerializerRegistry.UnionUsage
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import java.io.File

class UnionFactoryUsageTest : ShouldSpec({

    should("UnionSerializer objects do not use UnionFactory and delegate via serializerFor") {
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

        // 不再存在工厂与缓存
        kotlin.runCatching { content.shouldContain("object UnionFactory") }.isFailure.shouldBeTrue()
        kotlin.runCatching { content.shouldContain("ConcurrentHashMap") }.isFailure.shouldBeTrue()

        // 对象应使用 Union.U3.serializerFor(...) 构造委托
        content.shouldContain("Union.U3.serializerFor(")
        // 数组形态应包一层 ArraySerializer(...)
        content.shouldContain("ArraySerializer(")
    }
})

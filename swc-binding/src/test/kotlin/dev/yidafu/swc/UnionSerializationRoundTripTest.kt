package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UnionSerializationRoundTripTest : AnnotationSpec() {

    @Test
    fun `ArrowFunctionExpression body Node roundtrip`() {
        val json = Json {
            serializersModule = swcSerializersModule
            ignoreUnknownKeys = true
        }
        val node = ArrowFunctionExpression().apply {
            // body 的类型是 Node?，可以直接赋值 BlockStatement 或 Expression
            body = BlockStatement()
        }
        val encoded = json.encodeToString(node)
        val decoded = json.decodeFromString<ArrowFunctionExpression>(encoded)
        assertEquals("ArrowFunctionExpression", decoded.type)
        // body 应该不为 null，并且应该是 BlockStatement 类型
        assertNotNull(decoded.body)
    }
}



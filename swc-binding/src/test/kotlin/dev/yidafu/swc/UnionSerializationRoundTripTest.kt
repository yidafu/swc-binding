package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class UnionSerializationRoundTripTest : AnnotationSpec() {

    @Test
    fun `ArrowFunctionExpression body U2 roundtrip`() {
        val json = Json {
            serializersModule = swcSerializersModule
            ignoreUnknownKeys = true
        }
        val node = ArrowFunctionExpression().apply {
            // 使用 U2 的第一个分支（BlockStatement）
            body = Union.U2.ofA<BlockStatement, Expression>(BlockStatement())
        }
        val encoded = json.encodeToString(node)
        val decoded = json.decodeFromString<ArrowFunctionExpression>(encoded)
        assertEquals("ArrowFunctionExpression", decoded.type)
        // a 分支存在即可视为成功往返
        assertEquals(true, decoded.body?.isA() == true)
    }
}



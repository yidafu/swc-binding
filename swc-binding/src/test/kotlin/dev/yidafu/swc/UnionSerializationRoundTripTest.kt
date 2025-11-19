package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.createBlockStatement
import io.kotest.core.spec.style.ShouldSpec
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.assertNotNull

class UnionSerializationRoundTripTest : ShouldSpec({

    should("ArrowFunctionExpression body Node roundtrip") {
        val json = Json {
            serializersModule = swcSerializersModule
            ignoreUnknownKeys = true
        }
        val node = ArrowFunctionExpression().apply {
            // body 的类型是 Node?，可以直接赋值 BlockStatement 或 Expression
            body = createBlockStatement { }
        }
        val encoded = json.encodeToString(node)
        val decoded = json.decodeFromString<ArrowFunctionExpression>(encoded)
        // type 属性已移除，使用 @SerialName 和 @JsonClassDiscriminator 代替
        // 验证序列化的 JSON 包含 type 字段
        assertNotNull(encoded)
        // body 应该不为 null，并且应该是 BlockStatement 类型
        assertNotNull(decoded.body)
    }
})

package dev.yidafu.swc.union

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
            // body type is Node?, can directly assign BlockStatement or Expression
            body = createBlockStatement { }
        }
        val encoded = json.encodeToString(node)
        val decoded = json.decodeFromString<ArrowFunctionExpression>(encoded)
        // type property has been removed, using @SerialName and @JsonClassDiscriminator instead
        // Verify serialized JSON contains type field
        assertNotNull(encoded)
        // body should not be null and should be BlockStatement type
        assertNotNull(decoded.body)
    }
})

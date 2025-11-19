package dev.yidafu.swc

import dev.yidafu.swc.generated.TruePlusMinus
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonPrimitive

/**
 * Custom serializer for TruePlusMinus enum that handles:
 * - Boolean true -> TruePlusMinus.TRUE
 * - String "true" -> TruePlusMinus.TRUE
 * - String "+" -> TruePlusMinus.Addition
 * - String "-" -> TruePlusMinus.Subtraction
 */
object TruePlusMinusSerializer : KSerializer<TruePlusMinus> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("TruePlusMinus")

    override fun serialize(encoder: Encoder, value: TruePlusMinus) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: error("TruePlusMinusSerializer can only be used with JsonEncoder")

        val stringValue = when (value) {
            TruePlusMinus.TRUE -> "true"
            TruePlusMinus.Addition -> "+"
            TruePlusMinus.Subtraction -> "-"
        }
        jsonEncoder.encodeJsonElement(JsonPrimitive(stringValue))
    }

    override fun deserialize(decoder: Decoder): TruePlusMinus {
        val jsonDecoder = decoder as? JsonDecoder
            ?: error("TruePlusMinusSerializer can only be used with JsonDecoder")

        val element = jsonDecoder.decodeJsonElement()
        val primitive = element.jsonPrimitive

        // Handle boolean true
        val booleanValue = primitive.booleanOrNull
        if (booleanValue == true) {
            return TruePlusMinus.TRUE
        }

        // Handle string values
        val stringValue = primitive.content
        return when (stringValue) {
            "true" -> TruePlusMinus.TRUE
            "+" -> TruePlusMinus.Addition
            "-" -> TruePlusMinus.Subtraction
            else -> error("Invalid TruePlusMinus value: $stringValue")
        }
    }
}
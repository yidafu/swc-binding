// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:40:40.096264

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import dev.yidafu.swc.TruePlusMinusSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Int
import kotlin.Nothing
@Serializable(with = TruePlusMinusSerializer::class)
public enum class TruePlusMinus {
    @SerialName("true")
    TRUE,
    @SerialName("+")
    Addition,
    @SerialName("-")
    Subtraction,
}

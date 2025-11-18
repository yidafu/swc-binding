// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.09403

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Int
import kotlin.Nothing
@Serializable
public enum class UnaryOperator {
    @SerialName("-")
    Subtraction,
    @SerialName("+")
    Addition,
    @SerialName("!")
    LogicalNOT,
    @SerialName("~")
    BitwiseNOT,
    @SerialName("typeof")
    TYPEOF,
    @SerialName("void")
    VOID,
    @SerialName("delete")
    DELETE,
}

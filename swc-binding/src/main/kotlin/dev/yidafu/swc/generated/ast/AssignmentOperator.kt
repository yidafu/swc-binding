// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.245588

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Int
import kotlin.Nothing
@Serializable
public enum class AssignmentOperator {
    @SerialName("=")
    Assignment,
    @SerialName("+=")
    AdditionAssignment,
    @SerialName("-=")
    SubtractionAssignment,
    @SerialName("*=")
    MultiplicationAssignment,
    @SerialName("/=")
    DivisionAssignment,
    @SerialName("%=")
    RemainderAssignment,
    @SerialName("<<=")
    LeftShiftAssignment,
    @SerialName(">>=")
    RightShiftAssignment,
    @SerialName(">>>=")
    UnsignedRightShiftAssignment,
    @SerialName("|=")
    BitwiseORAssignment,
    @SerialName("^=")
    BitwiseXORAssignment,
    @SerialName("&=")
    BitwiseANDAssignment,
    @SerialName("**=")
    ExponentiationAssignment,
    @SerialName("&&=")
    LogicalANDAssignment,
    @SerialName("||=")
    LogicalORAssignment,
    @SerialName("??=")
    NullishCoalescingAssignment,
}

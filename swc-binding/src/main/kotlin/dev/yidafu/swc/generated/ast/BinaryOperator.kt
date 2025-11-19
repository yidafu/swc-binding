// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.244183

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Int
import kotlin.Nothing
@Serializable
public enum class BinaryOperator {
    @SerialName("==")
    Equality,
    @SerialName("!=")
    Inequality,
    @SerialName("===")
    StrictEquality,
    @SerialName("!==")
    StrictInequality,
    @SerialName("<")
    LessThan,
    @SerialName("<=")
    LessThanOrEqual,
    @SerialName(">")
    GreaterThan,
    @SerialName(">=")
    GreaterThanOrEqual,
    @SerialName("<<")
    LeftShift,
    @SerialName(">>")
    RightShift,
    @SerialName(">>>")
    UnsignedRightShift,
    @SerialName("+")
    Addition,
    @SerialName("-")
    Subtraction,
    @SerialName("*")
    Multiplication,
    @SerialName("/")
    Division,
    @SerialName("%")
    Remainder,
    @SerialName("|")
    BitwiseOR,
    @SerialName("^")
    BitwiseXOR,
    @SerialName("&")
    BitwiseAND,
    @SerialName("||")
    LogicalOR,
    @SerialName("&&")
    LogicalAND,
    @SerialName("in")
    IN,
    @SerialName("instanceof")
    INSTANCEOF,
    @SerialName("**")
    Exponentiation,
    @SerialName("??")
    NullishCoalescingOperator,
}

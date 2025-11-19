// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.075414

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "AssignmentExpression"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("AssignmentExpression")
@SwcDslMarker
public class AssignmentExpression : ExpressionBase, Expression {
    @EncodeDefault
    public var `operator`: AssignmentOperator? = null
    @EncodeDefault
    public var left: Node? = null
    @EncodeDefault
    public var right: Expression? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.072936

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "UpdateExpression"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("UpdateExpression")
@SwcDslMarker
public class UpdateExpression : ExpressionBase, Expression {
    @EncodeDefault
    public var `operator`: UpdateOperator? = null
    @EncodeDefault
    public var prefix: Boolean? = null
    @EncodeDefault
    public var argument: Expression? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.078082

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
 * remove class property `override var type : String? = "AwaitExpression"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("AwaitExpression")
@SwcDslMarker
public class AwaitExpression : ExpressionBase, Expression {
    @EncodeDefault
    public var argument: Expression? = null

    public override var span: Span = emptySpan()
}

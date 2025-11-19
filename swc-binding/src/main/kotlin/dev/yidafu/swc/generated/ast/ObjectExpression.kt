// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.200028

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "ObjectExpression"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ObjectExpression")
@SwcDslMarker
public class ObjectExpression : ExpressionBase, Expression {
    @EncodeDefault
    public var properties: Array<Node>? = null

    public override var span: Span = emptySpan()
}

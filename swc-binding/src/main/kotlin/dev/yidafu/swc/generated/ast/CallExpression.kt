// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.198543

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
 * remove class property `override var type : String? = "CallExpression"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("CallExpression")
@SwcDslMarker
public class CallExpression : ExpressionBase, Expression {
    @EncodeDefault
    public var callee: Node? = null
    @EncodeDefault
    public var arguments: Array<Argument>? = null
    @EncodeDefault
    public var typeArguments: TsTypeParameterInstantiation? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
    @EncodeDefault
    public var ctxt: Int = 0
}

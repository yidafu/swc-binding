// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.205912

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "ArrowFunctionExpression"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ArrowFunctionExpression")
@SwcDslMarker
public class ArrowFunctionExpression : ExpressionBase, Expression {
    @EncodeDefault
    public var params: Array<Pattern>? = null
    @EncodeDefault
    public var body: Node? = null
    @EncodeDefault
    public var async: Boolean? = null
    @EncodeDefault
    public var generator: Boolean? = null
    @EncodeDefault
    public var typeParameters: TsTypeParameterDeclaration? = null
    @EncodeDefault
    public var returnType: TsTypeAnnotation? = null

    public override var span: Span = emptySpan()
    @EncodeDefault
    public var ctxt: Int = 0
}

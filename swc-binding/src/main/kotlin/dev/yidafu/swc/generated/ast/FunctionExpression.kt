// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.074865

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
 * remove class property `override var type : String? = "FunctionExpression"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("FunctionExpression")
@SwcDslMarker
public class FunctionExpression : Fn, ExpressionBase, Expression, DefaultDecl {
    @EncodeDefault
    public var identifier: Identifier? = null

    public override var span: Span = emptySpan()

    public override var params: Array<Param>? = null

    public override var body: BlockStatement? = null

    public override var generator: Boolean? = null

    public override var async: Boolean? = null

    public override var typeParameters: TsTypeParameterDeclaration? = null

    public override var returnType: TsTypeAnnotation? = null

    public override var decorators: Array<Decorator>? = null
}

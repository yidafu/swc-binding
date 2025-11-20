// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.194438

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
 * remove class property `override var type : String? = "ClassExpression"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ClassExpression")
@SwcDslMarker
public class ClassExpression : JsClass, ExpressionBase, Expression, DefaultDecl {
    @EncodeDefault
    public var identifier: Identifier? = null
    @EncodeDefault
    public override var span: Span = emptySpan()

    public override var body: Array<ClassMember>? = null

    public override var superClass: Expression? = null

    public override var isAbstract: Boolean? = null

    public override var typeParams: TsTypeParameterDeclaration? = null

    public override var superTypeParams: TsTypeParameterInstantiation? = null

    public override var implements: Array<TsExpressionWithTypeArguments>? = null
    @EncodeDefault
    public override var ctxt: Int = 0

    public override var decorators: Array<Decorator>? = null
}

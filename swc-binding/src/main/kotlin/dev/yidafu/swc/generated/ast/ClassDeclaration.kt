// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:08:41.2158

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
 * remove class property `override var type : String? = "ClassDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ClassDeclaration")
@SwcDslMarker
public class ClassDeclaration : JsClass, Node, Declaration {
    @EncodeDefault
    public var identifier: Identifier? = null
    @EncodeDefault
    public var declare: Boolean? = null

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

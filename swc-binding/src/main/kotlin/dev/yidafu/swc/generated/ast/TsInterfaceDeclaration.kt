// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:44.007042

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
 * remove class property `override var type : String? = "TsInterfaceDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsInterfaceDeclaration")
@SwcDslMarker
public class TsInterfaceDeclaration : Node, HasSpan, Declaration, DefaultDecl {
    @EncodeDefault
    public var id: Identifier? = null
    @EncodeDefault
    public var declare: Boolean? = null
    @EncodeDefault
    public var typeParams: TsTypeParameterDeclaration? = null
    @EncodeDefault
    public var extends: Array<TsExpressionWithTypeArguments>? = null
    @EncodeDefault
    public var body: TsInterfaceBody? = null

    public override var span: Span = emptySpan()
}

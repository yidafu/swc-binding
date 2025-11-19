// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.109755

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
 * remove class property `override var type : String? = "TsMethodSignature"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsMethodSignature")
@SwcDslMarker
public class TsMethodSignature : Node, HasSpan, TsTypeElement {
    @EncodeDefault
    public var readonly: Boolean? = null
    @EncodeDefault
    public var key: Expression? = null
    @EncodeDefault
    public var computed: Boolean? = null
    @EncodeDefault
    public var optional: Boolean? = null
    @EncodeDefault
    public var params: Array<TsFnParameter>? = null
    @EncodeDefault
    public var typeAnn: TsTypeAnnotation? = null
    @EncodeDefault
    public var typeParams: TsTypeParameterDeclaration? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.109075

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
 * remove class property `override var type : String? = "TsIndexSignature"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsIndexSignature")
@SwcDslMarker
public class TsIndexSignature : Node, HasSpan, ClassMember, TsTypeElement {
    @EncodeDefault
    public var params: Array<TsFnParameter>? = null
    @EncodeDefault
    public var typeAnnotation: TsTypeAnnotation? = null
    @EncodeDefault
    public var readonly: Boolean? = null
    @EncodeDefault
    public var static: Boolean? = null

    public override var span: Span = emptySpan()
}

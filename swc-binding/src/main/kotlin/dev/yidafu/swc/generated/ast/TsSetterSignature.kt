// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.108837

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "TsSetterSignature"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsSetterSignature")
@SwcDslMarker
public class TsSetterSignature : Node, HasSpan, TsTypeElement {
    @EncodeDefault
    public var readonly: Boolean? = null
    @EncodeDefault
    public var key: Expression? = null
    @EncodeDefault
    public var computed: Boolean? = null
    @EncodeDefault
    public var optional: Boolean? = null
    @EncodeDefault
    public var `param`: TsFnParameter? = null

    public override var span: Span = emptySpan()
}

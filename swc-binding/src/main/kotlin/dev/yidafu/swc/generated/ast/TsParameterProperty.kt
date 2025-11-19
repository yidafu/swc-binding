// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.103637

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
 * remove class property `override var type : String? = "TsParameterProperty"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsParameterProperty")
@SwcDslMarker
public class TsParameterProperty : Node, HasSpan, HasDecorator {
    @EncodeDefault
    public var accessibility: Accessibility? = null
    @EncodeDefault
    public var `override`: Boolean? = null
    @EncodeDefault
    public var readonly: Boolean? = null
    @EncodeDefault
    public var `param`: TsParameterPropertyParameter? = null

    public override var span: Span = emptySpan()

    public override var decorators: Array<Decorator>? = null
}

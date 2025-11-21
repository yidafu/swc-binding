// Auto-generated file. Do not edit. Generated at: 2025-11-21T00:00:07.326611

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "SpreadElement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("SpreadElement")
@SwcDslMarker
public class SpreadElement : Node, JSXAttributeOrSpread {
    @EncodeDefault
    public var spread: Span? = null
    @EncodeDefault
    public var arguments: Expression? = null
    @EncodeDefault
    public var span: Span = emptySpan()
}

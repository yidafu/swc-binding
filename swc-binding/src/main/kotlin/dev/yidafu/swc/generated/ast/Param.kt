// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.964009

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "Parameter"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("Parameter")
@SwcDslMarker
public class Param : Node, HasSpan, HasDecorator {
    @EncodeDefault
    public var pat: Pattern? = null

    public override var span: Span = emptySpan()

    public override var decorators: Array<Decorator>? = null
}

// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.198549

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
@JsonClassDiscriminator("type")
public interface Param : Node, HasSpan, HasDecorator {
    public var pat: Pattern?
}

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "Parameter"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("Parameter")
@SwcDslMarker
public class ParamImpl : Param {
    @EncodeDefault
    public override var pat: Pattern? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
    @EncodeDefault
    public override var decorators: Array<Decorator>? = null
}

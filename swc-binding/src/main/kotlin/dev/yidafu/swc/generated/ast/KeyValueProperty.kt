// Auto-generated file. Do not edit. Generated at: 2025-11-21T00:00:07.345723

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
 * remove class property `override var type : String? = "KeyValueProperty"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("KeyValueProperty")
@SwcDslMarker
public class KeyValueProperty : PropBase, Property {
    @EncodeDefault
    public var `value`: Expression? = null
    @EncodeDefault
    public var span: Span = emptySpan()

    public override var key: PropertyName? = null
}

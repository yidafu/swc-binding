// Auto-generated file. Do not edit. Generated at: 2025-11-21T00:00:07.345134

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
 * remove class property `override var type : String? = "KeyValuePatternProperty"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("KeyValuePatternProperty")
@SwcDslMarker
public class KeyValuePatternProperty : Node, ObjectPatternProperty {
    @EncodeDefault
    public var key: PropertyName? = null
    @EncodeDefault
    public var `value`: Pattern? = null
    @EncodeDefault
    public var span: Span = emptySpan()
}

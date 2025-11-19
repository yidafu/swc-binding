// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.086002

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
import kotlin.String

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "MetaProperty"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("MetaProperty")
@SwcDslMarker
public class MetaProperty : Node, HasSpan, Expression {
    @EncodeDefault
    public var kind: String? = null

    public override var span: Span = emptySpan()
}

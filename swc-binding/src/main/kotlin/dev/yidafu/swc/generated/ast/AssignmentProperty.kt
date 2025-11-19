// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.218138

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
 * remove class property `override var type : String? = "AssignmentProperty"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("AssignmentProperty")
@SwcDslMarker
public class AssignmentProperty : Node, Property {
    @EncodeDefault
    public var key: Identifier? = null
    @EncodeDefault
    public var `value`: Expression? = null
}

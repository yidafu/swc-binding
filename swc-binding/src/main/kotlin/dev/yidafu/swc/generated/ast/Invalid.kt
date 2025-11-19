// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.242146

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "Invalid"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("Invalid")
@SwcDslMarker
public class Invalid : Node, HasSpan, Expression, Pattern {
    public override var span: Span = emptySpan()
}

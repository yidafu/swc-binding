// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.974214

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
import kotlin.String

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "TemplateElement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TemplateElement")
@SwcDslMarker
public class TemplateElement : ExpressionBase {
    @EncodeDefault
    public var tail: Boolean? = null
    @EncodeDefault
    public var cooked: String? = null
    @EncodeDefault
    public var raw: String? = null

    public override var span: Span = emptySpan()
}

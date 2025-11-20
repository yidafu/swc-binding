// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.201655

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
 * remove class property `override var type : String? = "JSXText"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("JSXText")
@SwcDslMarker
public class JSXText : Node, HasSpan, JSXElementChild, Literal {
    @EncodeDefault
    public var `value`: String? = null
    @EncodeDefault
    public var raw: String? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

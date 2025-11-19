// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.178979

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
@JsonClassDiscriminator("type")
public interface JSXClosingElement : Node, HasSpan {
    public var name: JSXElementName?
}

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "JSXClosingElement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("JSXClosingElement")
@SwcDslMarker
public class JSXClosingElementImpl : JSXClosingElement {
    @EncodeDefault
    public override var name: JSXElementName? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

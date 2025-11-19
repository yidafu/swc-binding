// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.213035

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
 * remove class property `override var type : String? = "JSXElement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("JSXElement")
@SwcDslMarker
public class JSXElement : Node, HasSpan, Expression, JSXAttrValue, JSXElementChild {
    @EncodeDefault
    public var opening: JSXOpeningElement? = null
    @EncodeDefault
    public var children: Array<JSXElementChild>? = null
    @EncodeDefault
    public var closing: JSXClosingElement? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.98022

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
 * remove class property `override var type : String? = "JSXFragment"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("JSXFragment")
@SwcDslMarker
public class JSXFragment : Node, HasSpan, Expression, JSXAttrValue, JSXElementChild {
    @EncodeDefault
    public var opening: JSXOpeningFragment? = null
    @EncodeDefault
    public var children: Array<JSXElementChild>? = null
    @EncodeDefault
    public var closing: JSXClosingFragment? = null

    public override var span: Span = emptySpan()
}

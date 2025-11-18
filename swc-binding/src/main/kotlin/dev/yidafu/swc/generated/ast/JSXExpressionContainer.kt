// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.976575

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
 * remove class property `override var type : String? = "JSXExpressionContainer"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("JSXExpressionContainer")
@SwcDslMarker
public class JSXExpressionContainer : Node, HasSpan, JSXAttrValue, JSXElementChild {
    @EncodeDefault
    public var expression: JSXExpression? = null

    public override var span: Span = emptySpan()
}

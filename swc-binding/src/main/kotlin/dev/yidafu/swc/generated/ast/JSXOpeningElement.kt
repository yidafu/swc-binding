// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.978206

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "JSXOpeningElement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("JSXOpeningElement")
@SwcDslMarker
public class JSXOpeningElement : Node, HasSpan {
    @EncodeDefault
    public var name: JSXElementName? = null
    @EncodeDefault
    public var attributes: Array<JSXAttributeOrSpread>? = null
    @EncodeDefault
    public var selfClosing: Boolean? = null
    @EncodeDefault
    public var typeArguments: TsTypeParameterInstantiation? = null

    public override var span: Span = emptySpan()
}

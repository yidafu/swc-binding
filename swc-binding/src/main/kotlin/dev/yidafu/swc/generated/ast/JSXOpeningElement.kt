// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.1805

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
@JsonClassDiscriminator("type")
public interface JSXOpeningElement : Node, HasSpan {
    public var name: JSXElementName?

    public var attributes: Array<JSXAttributeOrSpread>?

    public var selfClosing: Boolean?

    public var typeArguments: TsTypeParameterInstantiation?
}

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "JSXOpeningElement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("JSXOpeningElement")
@SwcDslMarker
public class JSXOpeningElementImpl : JSXOpeningElement {
    @EncodeDefault
    public override var name: JSXElementName? = null
    @EncodeDefault
    public override var attributes: Array<JSXAttributeOrSpread>? = null
    @EncodeDefault
    public override var selfClosing: Boolean? = null
    @EncodeDefault
    public override var typeArguments: TsTypeParameterInstantiation? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

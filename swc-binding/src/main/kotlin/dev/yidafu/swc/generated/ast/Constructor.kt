// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.186899

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
 * remove class property `override var type : String? = "Constructor"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("Constructor")
@SwcDslMarker
public class Constructor : Node, HasSpan, ClassMember {
    @EncodeDefault
    public var key: PropertyName? = null
    @EncodeDefault
    public var params: Array<Node>? = null
    @EncodeDefault
    public var body: BlockStatement? = null
    @EncodeDefault
    public var accessibility: Accessibility? = null
    @EncodeDefault
    public var isOptional: Boolean? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
    @EncodeDefault
    public var ctxt: Int = 0
}

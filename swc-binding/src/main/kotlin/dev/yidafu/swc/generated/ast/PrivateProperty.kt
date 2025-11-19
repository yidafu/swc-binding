// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.065977

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
 * remove class property `override var type : String? = "PrivateProperty"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("PrivateProperty")
@SwcDslMarker
public class PrivateProperty : ClassPropertyBase, ClassMember {
    @EncodeDefault
    public var key: PrivateName? = null

    public override var span: Span = emptySpan()
    @EncodeDefault
    public var ctxt: Int = 0

    public override var `value`: Expression? = null

    public override var typeAnnotation: TsTypeAnnotation? = null

    public override var isStatic: Boolean? = null

    public override var accessibility: Accessibility? = null

    public override var isOptional: Boolean? = null

    public override var isOverride: Boolean? = null

    public override var readonly: Boolean? = null

    public override var definite: Boolean? = null

    public override var decorators: Array<Decorator>? = null
}

// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.196788

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

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "PrivateMethod"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("PrivateMethod")
@SwcDslMarker
public class PrivateMethod : ClassMethodBase, ClassMember {
    @EncodeDefault
    public var key: PrivateName? = null

    public override var span: Span = emptySpan()

    public override var function: Fn? = null

    public override var kind: MethodKind? = null

    public override var isStatic: Boolean? = null

    public override var accessibility: Accessibility? = null

    public override var isAbstract: Boolean? = null

    public override var isOptional: Boolean? = null

    public override var isOverride: Boolean? = null
}

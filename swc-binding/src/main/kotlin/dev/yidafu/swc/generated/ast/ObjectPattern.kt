// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.091357

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
 * remove class property `override var type : String? = "ObjectPattern"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ObjectPattern")
@SwcDslMarker
public class ObjectPattern : PatternBase, Pattern, TsFnParameter {
    @EncodeDefault
    public var properties: Array<ObjectPatternProperty>? = null
    @EncodeDefault
    public var optional: Boolean? = null

    public override var span: Span = emptySpan()

    public override var typeAnnotation: TsTypeAnnotation? = null
}

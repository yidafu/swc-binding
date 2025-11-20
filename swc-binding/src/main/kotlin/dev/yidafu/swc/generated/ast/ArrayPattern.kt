// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.219359

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
 * remove class property `override var type : String? = "ArrayPattern"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ArrayPattern")
@SwcDslMarker
public class ArrayPattern : PatternBase, Pattern, TsFnParameter {
    @EncodeDefault
    public var elements: Array<Pattern?>? = null
    @EncodeDefault
    public var optional: Boolean? = null
    @EncodeDefault
    public override var span: Span = emptySpan()

    public override var typeAnnotation: TsTypeAnnotation? = null
}

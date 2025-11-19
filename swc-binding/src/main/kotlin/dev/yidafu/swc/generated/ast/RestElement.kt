// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.217797

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
 * remove class property `override var type : String? = "RestElement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("RestElement")
@SwcDslMarker
public class RestElement : PatternBase, Pattern, ObjectPatternProperty, TsFnParameter {
    @EncodeDefault
    public var rest: Span? = null
    @EncodeDefault
    public var argument: Pattern? = null

    public override var span: Span = emptySpan()

    public override var typeAnnotation: TsTypeAnnotation? = null
}

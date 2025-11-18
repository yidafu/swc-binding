// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:44.001981

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
 * remove class property `override var type : String? = "TsOptionalType"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsOptionalType")
@SwcDslMarker
public class TsOptionalType : Node, HasSpan, TsType {
    @EncodeDefault
    public var typeAnnotation: TsType? = null

    public override var span: Span = emptySpan()
}

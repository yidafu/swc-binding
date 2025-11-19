// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.115391

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
 * remove class property `override var type : String? = "TsArrayType"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsArrayType")
@SwcDslMarker
public class TsArrayType : Node, HasSpan, TsType {
    @EncodeDefault
    public var elemType: TsType? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.228657

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
 * remove class property `override var type : String? = "TsConditionalType"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsConditionalType")
@SwcDslMarker
public class TsConditionalType : Node, HasSpan, TsType {
    @EncodeDefault
    public var checkType: TsType? = null
    @EncodeDefault
    public var extendsType: TsType? = null
    @EncodeDefault
    public var trueType: TsType? = null
    @EncodeDefault
    public var falseType: TsType? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

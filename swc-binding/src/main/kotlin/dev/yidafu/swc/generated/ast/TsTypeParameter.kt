// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.996809

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
 * remove class property `override var type : String? = "TsTypeParameter"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsTypeParameter")
@SwcDslMarker
public class TsTypeParameter : Node, HasSpan {
    @EncodeDefault
    public var name: Identifier? = null
    @EncodeDefault
    public var `in`: Boolean? = null
    @EncodeDefault
    public var `out`: Boolean? = null
    @EncodeDefault
    public var constraint: TsType? = null
    @EncodeDefault
    public var default: TsType? = null

    public override var span: Span = emptySpan()
}

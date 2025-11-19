// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.119726

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "TsTupleType"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsTupleType")
@SwcDslMarker
public class TsTupleType : Node, HasSpan, TsType {
    @EncodeDefault
    public var elemTypes: Array<TsTupleElement>? = null

    public override var span: Span = emptySpan()
}

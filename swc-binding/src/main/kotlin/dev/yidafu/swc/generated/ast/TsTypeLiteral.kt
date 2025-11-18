// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:44.001725

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
 * remove class property `override var type : String? = "TsTypeLiteral"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsTypeLiteral")
@SwcDslMarker
public class TsTypeLiteral : Node, HasSpan, TsType {
    @EncodeDefault
    public var members: Array<TsTypeElement>? = null

    public override var span: Span = emptySpan()
}

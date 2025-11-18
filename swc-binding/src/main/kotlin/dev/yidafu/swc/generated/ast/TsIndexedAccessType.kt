// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:44.006244

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
 * remove class property `override var type : String? = "TsIndexedAccessType"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsIndexedAccessType")
@SwcDslMarker
public class TsIndexedAccessType : Node, HasSpan, TsType {
    @EncodeDefault
    public var readonly: Boolean? = null
    @EncodeDefault
    public var objectType: TsType? = null
    @EncodeDefault
    public var indexType: TsType? = null

    public override var span: Span = emptySpan()
}

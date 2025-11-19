// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.211703

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Int
import kotlin.Long
import kotlin.Nothing
import kotlin.OptIn
import kotlin.String

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "BigIntLiteral"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("BigIntLiteral")
@SwcDslMarker
public class BigIntLiteral : Node, HasSpan, Literal, PropertyName, TsLiteral {
    @EncodeDefault
    public var `value`: Long? = null
    @EncodeDefault
    public var raw: String? = null

    public override var span: Span = emptySpan()
}

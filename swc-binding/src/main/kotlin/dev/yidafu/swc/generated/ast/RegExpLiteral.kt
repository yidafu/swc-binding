// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.212092

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
import kotlin.String

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "RegExpLiteral"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("RegExpLiteral")
@SwcDslMarker
public class RegExpLiteral : Node, HasSpan, Literal {
    @EncodeDefault
    public var pattern: String? = null
    @EncodeDefault
    public var flags: String? = null

    public override var span: Span = emptySpan()
}

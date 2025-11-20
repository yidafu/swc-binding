// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.237841

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
 * remove class property `override var type : String? = "CatchClause"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("CatchClause")
@SwcDslMarker
public class CatchClause : Node, HasSpan {
    @EncodeDefault
    public var `param`: Pattern? = null
    @EncodeDefault
    public var body: BlockStatement? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

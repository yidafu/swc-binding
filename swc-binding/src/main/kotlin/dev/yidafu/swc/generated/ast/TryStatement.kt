// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.992088

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
 * remove class property `override var type : String? = "TryStatement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TryStatement")
@SwcDslMarker
public class TryStatement : Node, HasSpan, Statement {
    @EncodeDefault
    public var block: BlockStatement? = null
    @EncodeDefault
    public var handler: CatchClause? = null
    @EncodeDefault
    public var finalizer: BlockStatement? = null

    public override var span: Span = emptySpan()
}

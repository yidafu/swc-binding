// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:22.767345

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
@JsonClassDiscriminator("type")
public interface BlockStatement : Node, HasSpan, Statement {
    public var stmts: Array<Statement>?

    public var ctxt: Int
}

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "BlockStatement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("BlockStatement")
@SwcDslMarker
public class BlockStatementImpl : BlockStatement {
    @EncodeDefault
    public override var stmts: Array<Statement>? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
    @EncodeDefault
    public override var ctxt: Int = 0
}

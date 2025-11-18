// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.989425

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
 * remove class property `override var type : String? = "BlockStatement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("BlockStatement")
@SwcDslMarker
public class BlockStatement : Node, HasSpan, Statement {
    @EncodeDefault
    public var stmts: Array<Statement>? = null

    public override var span: Span = emptySpan()
    @EncodeDefault
    public var ctxt: Int = 0
}

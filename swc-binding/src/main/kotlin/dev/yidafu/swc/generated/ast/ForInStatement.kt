// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.237333

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
 * remove class property `override var type : String? = "ForInStatement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ForInStatement")
@SwcDslMarker
public class ForInStatement : Node, HasSpan, Statement {
    @EncodeDefault
    public var left: Node? = null
    @EncodeDefault
    public var right: Expression? = null
    @EncodeDefault
    public var body: Statement? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

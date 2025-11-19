// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.101306

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
 * remove class property `override var type : String? = "ForOfStatement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ForOfStatement")
@SwcDslMarker
public class ForOfStatement : Node, HasSpan, Statement {
    @EncodeDefault
    public var await: Boolean? = null
    @EncodeDefault
    public var left: Node? = null
    @EncodeDefault
    public var right: Expression? = null
    @EncodeDefault
    public var body: Statement? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.993013

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
 * remove class property `override var type : String? = "ForStatement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ForStatement")
@SwcDslMarker
public class ForStatement : Node, HasSpan, Statement {
    @EncodeDefault
    public var `init`: Node? = null
    @EncodeDefault
    public var test: Expression? = null
    @EncodeDefault
    public var update: Expression? = null
    @EncodeDefault
    public var body: Statement? = null

    public override var span: Span = emptySpan()
}

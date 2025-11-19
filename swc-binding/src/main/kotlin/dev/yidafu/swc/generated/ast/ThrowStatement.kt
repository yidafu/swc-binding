// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.108644

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
 * remove class property `override var type : String? = "ThrowStatement"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ThrowStatement")
@SwcDslMarker
public class ThrowStatement : Node, HasSpan, Statement {
    @EncodeDefault
    public var argument: Expression? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.966405

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
 * remove class property `override var type : String? = "VariableDeclarator"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("VariableDeclarator")
@SwcDslMarker
public class VariableDeclarator : Node, HasSpan {
    @EncodeDefault
    public var id: Pattern? = null
    @EncodeDefault
    public var `init`: Expression? = null
    @EncodeDefault
    public var definite: Boolean? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.202008

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
@JsonClassDiscriminator("type")
public interface VariableDeclarator : Node, HasSpan {
    public var id: Pattern?

    public var `init`: Expression?

    public var definite: Boolean?
}

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "VariableDeclarator"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("VariableDeclarator")
@SwcDslMarker
public class VariableDeclaratorImpl : VariableDeclarator {
    @EncodeDefault
    public override var id: Pattern? = null
    @EncodeDefault
    public override var `init`: Expression? = null
    @EncodeDefault
    public override var definite: Boolean? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

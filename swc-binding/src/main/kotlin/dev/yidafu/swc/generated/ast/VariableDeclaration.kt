// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.069924

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "VariableDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("VariableDeclaration")
@SwcDslMarker
public class VariableDeclaration : Node, HasSpan, Declaration {
    @EncodeDefault
    public var kind: VariableDeclarationKind? = null
    @EncodeDefault
    public var declare: Boolean? = null
    @EncodeDefault
    public var declarations: Array<VariableDeclarator>? = null

    public override var span: Span = emptySpan()
    @EncodeDefault
    public var ctxt: Int = 0
}

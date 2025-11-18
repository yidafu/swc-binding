// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.983458

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
 * remove class property `override var type : String? = "ImportDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ImportDeclaration")
@SwcDslMarker
public class ImportDeclaration : Node, HasSpan, ModuleDeclaration {
    @EncodeDefault
    public var specifiers: Array<ImportSpecifier>? = null
    @EncodeDefault
    public var source: StringLiteral? = null
    @EncodeDefault
    public var typeOnly: Boolean? = null
    @EncodeDefault
    public var asserts: ObjectExpression? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:44.010127

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
 * remove class property `override var type : String? = "TsNamespaceDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsNamespaceDeclaration")
@SwcDslMarker
public class TsNamespaceDeclaration : Node, HasSpan, TsNamespaceBody {
    @EncodeDefault
    public var declare: Boolean? = null
    @EncodeDefault
    public var global: Boolean? = null
    @EncodeDefault
    public var id: Identifier? = null
    @EncodeDefault
    public var body: TsNamespaceBody? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.232473

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
 * remove class property `override var type : String? = "TsModuleDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsModuleDeclaration")
@SwcDslMarker
public class TsModuleDeclaration : Node, HasSpan, Declaration {
    @EncodeDefault
    public var declare: Boolean? = null
    @EncodeDefault
    public var global: Boolean? = null
    @EncodeDefault
    public var id: TsModuleName? = null
    @EncodeDefault
    public var body: TsNamespaceBody? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

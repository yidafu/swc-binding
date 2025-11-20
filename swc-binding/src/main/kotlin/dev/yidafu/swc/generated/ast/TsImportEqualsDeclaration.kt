// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.234082

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
 * remove class property `override var type : String? = "TsImportEqualsDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsImportEqualsDeclaration")
@SwcDslMarker
public class TsImportEqualsDeclaration : Node, HasSpan, ModuleDeclaration {
    @EncodeDefault
    public var declare: Boolean? = null
    @EncodeDefault
    public var isExport: Boolean? = null
    @EncodeDefault
    public var isTypeOnly: Boolean? = null
    @EncodeDefault
    public var id: Identifier? = null
    @EncodeDefault
    public var moduleRef: TsModuleReference? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

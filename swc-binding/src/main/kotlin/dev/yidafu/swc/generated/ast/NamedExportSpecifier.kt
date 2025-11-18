// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.986011

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
 * remove class property `override var type : String? = "ExportSpecifier"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ExportSpecifier")
@SwcDslMarker
public class NamedExportSpecifier : Node, HasSpan, ExportSpecifier {
    @EncodeDefault
    public var orig: ModuleExportName? = null
    @EncodeDefault
    public var exported: ModuleExportName? = null
    @EncodeDefault
    public var isTypeOnly: Boolean? = null

    public override var span: Span = emptySpan()
}

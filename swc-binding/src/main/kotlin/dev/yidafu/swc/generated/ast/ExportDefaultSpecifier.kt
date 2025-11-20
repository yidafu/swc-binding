// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.208382

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
 * remove class property `override var type : String? = "ExportDefaultSpecifier"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ExportDefaultSpecifier")
@SwcDslMarker
public class ExportDefaultSpecifier : Node, HasSpan, ExportSpecifier {
    @EncodeDefault
    public var exported: Identifier? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

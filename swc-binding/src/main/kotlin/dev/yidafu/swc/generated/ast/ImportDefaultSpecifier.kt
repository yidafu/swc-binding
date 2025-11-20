// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.206862

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
 * remove class property `override var type : String? = "ImportDefaultSpecifier"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ImportDefaultSpecifier")
@SwcDslMarker
public class ImportDefaultSpecifier : Node, HasSpan, ImportSpecifier {
    @EncodeDefault
    public var local: Identifier? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

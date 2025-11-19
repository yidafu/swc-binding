// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.215924

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
 * remove class property `override var type : String? = "ImportSpecifier"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("ImportSpecifier")
@SwcDslMarker
public class NamedImportSpecifier : Node, HasSpan, ImportSpecifier {
    @EncodeDefault
    public var local: Identifier? = null
    @EncodeDefault
    public var imported: ModuleExportName? = null
    @EncodeDefault
    public var isTypeOnly: Boolean? = null

    public override var span: Span = emptySpan()
}

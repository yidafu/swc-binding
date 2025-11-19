// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.128938

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "TsModuleBlock"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsModuleBlock")
@SwcDslMarker
public class TsModuleBlock : Node, HasSpan, TsNamespaceBody {
    @EncodeDefault
    public var body: Array<ModuleItem>? = null

    public override var span: Span = emptySpan()
}

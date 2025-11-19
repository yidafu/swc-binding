// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.216074

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
import kotlin.String

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "Script"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("Script")
@SwcDslMarker
public class Script : Node, HasSpan, HasInterpreter, Program {
    @EncodeDefault
    public var body: Array<Statement>? = null

    public override var span: Span = emptySpan()

    public override var interpreter: String? = null
}

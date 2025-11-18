// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:44.010201

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
 * remove class property `override var type : String? = "TsExternalModuleReference"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsExternalModuleReference")
@SwcDslMarker
public class TsExternalModuleReference : Node, HasSpan, TsModuleReference {
    @EncodeDefault
    public var expression: StringLiteral? = null

    public override var span: Span = emptySpan()
}

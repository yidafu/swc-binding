// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.126038

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
 * remove class property `override var type : String? = "TsLiteralType"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsLiteralType")
@SwcDslMarker
public class TsLiteralType : Node, HasSpan, TsType {
    @EncodeDefault
    public var literal: TsLiteral? = null

    public override var span: Span = emptySpan()
}

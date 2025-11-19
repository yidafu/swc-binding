// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.132623

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var type : String? = "TsEnumDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsEnumDeclaration")
@SwcDslMarker
public class TsEnumDeclaration : Node, HasSpan, Declaration {
    @EncodeDefault
    public var declare: Boolean? = null
    @EncodeDefault
    public var isConst: Boolean? = null
    @EncodeDefault
    public var id: Identifier? = null
    @EncodeDefault
    public var members: Array<TsEnumMember>? = null

    public override var span: Span = emptySpan()
}

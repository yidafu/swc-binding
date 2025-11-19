// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.125603

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
 * remove class property `override var type : String? = "TsTypeAliasDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsTypeAliasDeclaration")
@SwcDslMarker
public class TsTypeAliasDeclaration : Node, HasSpan, Declaration {
    @EncodeDefault
    public var declare: Boolean? = null
    @EncodeDefault
    public var id: Identifier? = null
    @EncodeDefault
    public var typeParams: TsTypeParameterDeclaration? = null
    @EncodeDefault
    public var typeAnnotation: TsType? = null

    public override var span: Span = emptySpan()
}

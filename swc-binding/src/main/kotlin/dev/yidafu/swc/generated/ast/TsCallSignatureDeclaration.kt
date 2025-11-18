// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:43.997072

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
 * remove class property `override var type : String? = "TsCallSignatureDeclaration"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsCallSignatureDeclaration")
@SwcDslMarker
public class TsCallSignatureDeclaration : Node, HasSpan, TsTypeElement {
    @EncodeDefault
    public var params: Array<TsFnParameter>? = null
    @EncodeDefault
    public var typeAnnotation: TsTypeAnnotation? = null
    @EncodeDefault
    public var typeParams: TsTypeParameterDeclaration? = null

    public override var span: Span = emptySpan()
}

// Auto-generated file. Do not edit. Generated at: 2025-11-20T23:40:25.225133

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
 * remove class property `override var type : String? = "TsFunctionType"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsFunctionType")
@SwcDslMarker
public class TsFunctionType : Node, HasSpan, TsFnOrConstructorType {
    @EncodeDefault
    public var params: Array<TsFnParameter>? = null
    @EncodeDefault
    public var typeParams: TsTypeParameterDeclaration? = null
    @EncodeDefault
    public var typeAnnotation: TsTypeAnnotation? = null
    @EncodeDefault
    public override var span: Span = emptySpan()
}

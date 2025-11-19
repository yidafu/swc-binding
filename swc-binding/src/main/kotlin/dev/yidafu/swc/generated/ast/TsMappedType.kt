// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.234782

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
 * remove class property `override var type : String? = "TsMappedType"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsMappedType")
@SwcDslMarker
public class TsMappedType : Node, HasSpan, TsType {
    @EncodeDefault
    public var readonly: TruePlusMinus? = null
    @EncodeDefault
    public var typeParam: TsTypeParameter? = null
    @EncodeDefault
    public var nameType: TsType? = null
    @EncodeDefault
    public var optional: TruePlusMinus? = null
    @EncodeDefault
    public var typeAnnotation: TsType? = null

    public override var span: Span = emptySpan()
}

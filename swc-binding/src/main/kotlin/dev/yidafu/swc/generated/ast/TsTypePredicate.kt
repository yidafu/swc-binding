// Auto-generated file. Do not edit. Generated at: 2025-11-18T21:58:44.003962

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
 * remove class property `override var type : String? = "TsTypePredicate"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TsTypePredicate")
@SwcDslMarker
public class TsTypePredicate : Node, HasSpan, TsType {
    @EncodeDefault
    public var asserts: Boolean? = null
    @EncodeDefault
    public var paramName: TsThisTypeOrIdent? = null
    @EncodeDefault
    public var typeAnnotation: TsTypeAnnotation? = null

    public override var span: Span = emptySpan()
}

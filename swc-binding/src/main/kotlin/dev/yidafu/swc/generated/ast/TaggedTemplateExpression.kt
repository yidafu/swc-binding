// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.210117

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
 * remove class property `override var type : String? = "TaggedTemplateExpression"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("TaggedTemplateExpression")
@SwcDslMarker
public class TaggedTemplateExpression : ExpressionBase, Expression {
    @EncodeDefault
    public var tag: Expression? = null
    @EncodeDefault
    public var typeParameters: TsTypeParameterInstantiation? = null
    @EncodeDefault
    public var template: TemplateLiteral? = null

    public override var span: Span = emptySpan()
    @EncodeDefault
    public var ctxt: Int = 0
}

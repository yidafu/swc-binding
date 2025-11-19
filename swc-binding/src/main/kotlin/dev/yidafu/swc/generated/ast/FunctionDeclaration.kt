// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.198373

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
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("FunctionDeclaration")
public class FunctionDeclaration : Fn, Declaration {
    @EncodeDefault
    public var identifier: Identifier? = null
    @EncodeDefault
    public var declare: Boolean? = null

    public override var span: Span = emptySpan()
    @EncodeDefault
    public var ctxt: Int = 0

    public override var params: Array<Param>? = null

    public override var body: BlockStatement? = null

    public override var generator: Boolean? = null

    public override var async: Boolean? = null

    public override var typeParameters: TsTypeParameterDeclaration? = null

    public override var returnType: TsTypeAnnotation? = null

    public override var decorators: Array<Decorator>? = null
}

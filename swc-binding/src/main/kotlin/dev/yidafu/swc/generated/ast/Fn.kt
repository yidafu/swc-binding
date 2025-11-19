// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.178119

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
@JsonClassDiscriminator("type")
public interface Fn : HasSpan, HasDecorator {
    public var params: Array<Param>?

    public var body: BlockStatement?

    public var generator: Boolean?

    public var async: Boolean?

    public var typeParameters: TsTypeParameterDeclaration?

    public var returnType: TsTypeAnnotation?
}

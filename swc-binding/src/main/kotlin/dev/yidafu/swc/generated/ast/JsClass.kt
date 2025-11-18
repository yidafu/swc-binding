// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.02771

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing

public interface JsClass : HasSpan, HasDecorator {
    public var body: Array<ClassMember>?

    public var superClass: Expression?

    public var isAbstract: Boolean?

    public var typeParams: TsTypeParameterDeclaration?

    public var superTypeParams: TsTypeParameterInstantiation?

    public var implements: Array<TsExpressionWithTypeArguments>?
}

// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.027716

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing

public interface ClassPropertyBase : Node, HasSpan, HasDecorator {
    public var `value`: Expression?

    public var typeAnnotation: TsTypeAnnotation?

    public var isStatic: Boolean?

    public var accessibility: Accessibility?

    public var isOptional: Boolean?

    public var isOverride: Boolean?

    public var readonly: Boolean?

    public var definite: Boolean?
}

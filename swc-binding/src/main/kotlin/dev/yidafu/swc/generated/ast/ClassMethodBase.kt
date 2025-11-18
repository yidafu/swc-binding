// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.027482

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing

public interface ClassMethodBase : Node, HasSpan {
    public var function: Fn?

    public var kind: MethodKind?

    public var isStatic: Boolean?

    public var accessibility: Accessibility?

    public var isAbstract: Boolean?

    public var isOptional: Boolean?

    public var isOverride: Boolean?
}

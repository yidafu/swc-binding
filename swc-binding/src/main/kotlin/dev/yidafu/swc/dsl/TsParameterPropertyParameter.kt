package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AssignmentPattern
import dev.yidafu.swc.types.AssignmentPatternImpl
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
import dev.yidafu.swc.types.TsParameterPropertyParameter
import kotlin.Unit

/**
 * subtype of TsParameterPropertyParameter
 */
public fun TsParameterPropertyParameter.bindingIdentifier(block: BindingIdentifier.() -> Unit):
    BindingIdentifier = BindingIdentifierImpl().apply(block)

/**
 * subtype of TsParameterPropertyParameter
 */
public fun TsParameterPropertyParameter.assignmentPattern(block: AssignmentPattern.() -> Unit):
    AssignmentPattern = AssignmentPatternImpl().apply(block)

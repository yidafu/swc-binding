package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsQualifiedName
import kotlin.Unit

/**
 * TsQualifiedName#type: String
 * extension function for create String -> String
 */
public fun TsQualifiedName.string(block: String.() -> Unit): String = String().apply(block)

/**
 * TsQualifiedName#right: Identifier
 * extension function for create Identifier -> IdentifierImpl
 */
public fun TsQualifiedName.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

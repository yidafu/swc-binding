// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:09:39.248145

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.BlockStatementImpl
import dev.yidafu.swc.generated.GetterProperty
import dev.yidafu.swc.generated.TsTypeAnnotation
import kotlin.Unit

/**
 * GetterProperty#typeAnnotation: TsTypeAnnotation?
 * extension function for create TsTypeAnnotation? -> TsTypeAnnotation
 */
public fun GetterProperty.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotation().apply(block)

/**
 * GetterProperty#body: BlockStatement?
 * extension function for create BlockStatement? -> BlockStatement
 */
public fun GetterProperty.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:16.976772

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.Fn
import dev.yidafu.swc.generated.Param
import dev.yidafu.swc.generated.TsTypeAnnotation
import dev.yidafu.swc.generated.TsTypeParameterDeclaration
import kotlin.Unit

/**
 * Fn#params: Array<Param>?
 * extension function for create Array<Param>? -> Param
 */
public fun Fn.`param`(block: Param.() -> Unit): Param = Param().apply(block)

/**
 * Fn#body: BlockStatement?
 * extension function for create BlockStatement? -> BlockStatement
 */
public fun Fn.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatement().apply(block)

/**
 * Fn#typeParameters: TsTypeParameterDeclaration?
 * extension function for create TsTypeParameterDeclaration? -> TsTypeParameterDeclaration
 */
public fun Fn.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclaration().apply(block)

/**
 * Fn#returnType: TsTypeAnnotation?
 * extension function for create TsTypeAnnotation? -> TsTypeAnnotation
 */
public fun Fn.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotation().apply(block)

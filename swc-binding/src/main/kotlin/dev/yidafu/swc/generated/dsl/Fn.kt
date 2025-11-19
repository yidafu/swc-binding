// Auto-generated file. Do not edit. Generated at: 2025-11-19T22:42:23.093051

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.BlockStatementImpl
import dev.yidafu.swc.generated.Fn
import dev.yidafu.swc.generated.Param
import dev.yidafu.swc.generated.ParamImpl
import dev.yidafu.swc.generated.TsTypeAnnotation
import dev.yidafu.swc.generated.TsTypeParameterDeclaration
import kotlin.Unit

/**
 * Fn#params: Array<Param>?
 * extension function for create Array<Param>? -> Param
 */
public fun Fn.`param`(block: Param.() -> Unit): Param = ParamImpl().apply(block)

/**
 * Fn#body: BlockStatement?
 * extension function for create BlockStatement? -> BlockStatement
 */
public fun Fn.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

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

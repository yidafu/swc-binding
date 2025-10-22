package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
import dev.yidafu.swc.types.DefaultDecl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.TsInterfaceDeclaration
import dev.yidafu.swc.types.TsInterfaceDeclarationImpl
import kotlin.Unit

/**
 * subtype of DefaultDecl
 */
public fun DefaultDecl.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * subtype of DefaultDecl
 */
public fun DefaultDecl.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression
    = FunctionExpressionImpl().apply(block)

/**
 * subtype of DefaultDecl
 */
public fun DefaultDecl.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

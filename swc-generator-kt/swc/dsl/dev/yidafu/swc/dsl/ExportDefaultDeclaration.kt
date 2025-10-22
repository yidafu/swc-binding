package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
import dev.yidafu.swc.types.ExportDefaultDeclaration
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.TsInterfaceDeclaration
import dev.yidafu.swc.types.TsInterfaceDeclarationImpl
import kotlin.Unit

/**
 * ExportDefaultDeclaration#type: String
 * extension function for create String -> String
 */
public fun ExportDefaultDeclaration.string(block: String.() -> Unit): String = String().apply(block)

/**
 * ExportDefaultDeclaration#decl: DefaultDecl
 * extension function for create DefaultDecl -> ClassExpressionImpl
 */
public fun ExportDefaultDeclaration.classExpression(block: ClassExpression.() -> Unit):
    ClassExpression = ClassExpressionImpl().apply(block)

/**
 * ExportDefaultDeclaration#decl: DefaultDecl
 * extension function for create DefaultDecl -> FunctionExpressionImpl
 */
public fun ExportDefaultDeclaration.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * ExportDefaultDeclaration#decl: DefaultDecl
 * extension function for create DefaultDecl -> TsInterfaceDeclarationImpl
 */
public
    fun ExportDefaultDeclaration.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * ExportDefaultDeclaration#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun ExportDefaultDeclaration.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

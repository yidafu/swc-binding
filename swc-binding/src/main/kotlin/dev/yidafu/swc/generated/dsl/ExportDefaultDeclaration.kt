// Auto-generated file. Do not edit. Generated at: 2025-11-19T01:00:17.027755

package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ClassExpression
import dev.yidafu.swc.generated.ExportDefaultDeclaration
import dev.yidafu.swc.generated.FunctionExpression
import dev.yidafu.swc.generated.TsInterfaceDeclaration
import kotlin.Unit

/**
 * ExportDefaultDeclaration#decl: DefaultDecl?
 * extension function for create DefaultDecl? -> FunctionExpression
 */
public fun ExportDefaultDeclaration.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpression().apply(block)

/**
 * ExportDefaultDeclaration#decl: DefaultDecl?
 * extension function for create DefaultDecl? -> ClassExpression
 */
public fun ExportDefaultDeclaration.classExpression(block: ClassExpression.() -> Unit):
    ClassExpression = ClassExpression().apply(block)

/**
 * ExportDefaultDeclaration#decl: DefaultDecl?
 * extension function for create DefaultDecl? -> TsInterfaceDeclaration
 */
public
    fun ExportDefaultDeclaration.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclaration().apply(block)

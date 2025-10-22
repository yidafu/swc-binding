package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.TsModuleBlock
import dev.yidafu.swc.types.TsModuleBlockImpl
import dev.yidafu.swc.types.TsNamespaceBody
import dev.yidafu.swc.types.TsNamespaceDeclaration
import dev.yidafu.swc.types.TsNamespaceDeclarationImpl
import kotlin.Unit

/**
 * subtype of TsNamespaceBody
 */
public fun TsNamespaceBody.tsModuleBlock(block: TsModuleBlock.() -> Unit): TsModuleBlock =
    TsModuleBlockImpl().apply(block)

/**
 * subtype of TsNamespaceBody
 */
public fun TsNamespaceBody.tsNamespaceDeclaration(block: TsNamespaceDeclaration.() -> Unit):
    TsNamespaceDeclaration = TsNamespaceDeclarationImpl().apply(block)

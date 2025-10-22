package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ExportDefaultSpecifier
import dev.yidafu.swc.types.ExportDefaultSpecifierImpl
import dev.yidafu.swc.types.ExportNamespaceSpecifier
import dev.yidafu.swc.types.ExportNamespaceSpecifierImpl
import dev.yidafu.swc.types.ExportSpecifier
import dev.yidafu.swc.types.NamedExportSpecifier
import dev.yidafu.swc.types.NamedExportSpecifierImpl
import kotlin.Unit

/**
 * subtype of ExportSpecifier
 */
public fun ExportSpecifier.exportNamespaceSpecifier(block: ExportNamespaceSpecifier.() -> Unit):
    ExportNamespaceSpecifier = ExportNamespaceSpecifierImpl().apply(block)

/**
 * subtype of ExportSpecifier
 */
public fun ExportSpecifier.exportDefaultSpecifier(block: ExportDefaultSpecifier.() -> Unit):
    ExportDefaultSpecifier = ExportDefaultSpecifierImpl().apply(block)

/**
 * subtype of ExportSpecifier
 */
public fun ExportSpecifier.namedExportSpecifier(block: NamedExportSpecifier.() -> Unit):
    NamedExportSpecifier = NamedExportSpecifierImpl().apply(block)

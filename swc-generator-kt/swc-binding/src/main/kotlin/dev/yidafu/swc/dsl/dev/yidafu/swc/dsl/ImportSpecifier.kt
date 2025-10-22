package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ImportDefaultSpecifier
import dev.yidafu.swc.types.ImportDefaultSpecifierImpl
import dev.yidafu.swc.types.ImportNamespaceSpecifier
import dev.yidafu.swc.types.ImportNamespaceSpecifierImpl
import dev.yidafu.swc.types.ImportSpecifier
import dev.yidafu.swc.types.NamedImportSpecifier
import dev.yidafu.swc.types.NamedImportSpecifierImpl
import kotlin.Unit

/**
 * subtype of ImportSpecifier
 */
public fun ImportSpecifier.namedImportSpecifier(block: NamedImportSpecifier.() -> Unit):
    NamedImportSpecifier = NamedImportSpecifierImpl().apply(block)

/**
 * subtype of ImportSpecifier
 */
public fun ImportSpecifier.importDefaultSpecifier(block: ImportDefaultSpecifier.() -> Unit):
    ImportDefaultSpecifier = ImportDefaultSpecifierImpl().apply(block)

/**
 * subtype of ImportSpecifier
 */
public fun ImportSpecifier.importNamespaceSpecifier(block: ImportNamespaceSpecifier.() -> Unit):
    ImportNamespaceSpecifier = ImportNamespaceSpecifierImpl().apply(block)

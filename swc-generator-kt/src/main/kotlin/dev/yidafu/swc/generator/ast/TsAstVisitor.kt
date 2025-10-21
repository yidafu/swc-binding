package dev.yidafu.swc.generator.ast

import dev.yidafu.swc.generator.util.*
import dev.yidafu.swc.types.*

/**
 * TypeScript AST 访问器
 */
class TsAstVisitor(private val program: Program) {
    private val interfaces = mutableListOf<TsInterfaceDeclaration>()
    private val typeAliases = mutableListOf<TsTypeAliasDeclaration>()
    
    /**
     * 遍历 AST 提取所有 interface 和 type alias
     */
    fun visit() {
        Logger.debug("AST 类型: ${program::class.simpleName}", 4)
        when (program) {
            is Module -> visitModule(program)
            is Script -> visitScript(program)
        }
        Logger.debug("遍历完成: ${interfaces.size} 接口, ${typeAliases.size} 类型别名", 4)
    }
    
    private fun visitModule(module: Module) {
        val items = module.body.orEmpty()
        Logger.verbose("Module 包含 ${items.size} 个项", 6)
        items.forEachIndexed { index, item ->
            Logger.verbose("  项 $index: ${item::class.simpleName}", 8)
            visitModuleItem(item)
        }
    }
    
    private fun visitScript(script: Script) {
        // Script 的 body 通常不包含类型定义，跳过处理
        val stmts = script.body.orEmpty()
        Logger.verbose("Script 包含 ${stmts.size} 个语句", 6)
        stmts.forEachIndexed { index, stmt ->
            Logger.verbose("  语句 $index: ${stmt::class.simpleName}", 8)
            visitStatement(stmt)
        }
    }
    
    private fun visitModuleItem(item: ModuleItem) {
        Logger.verbose("    处理 ModuleItem: ${item::class.simpleName}", 10)
        Logger.debug("      是 ModuleDeclaration? ${item is ModuleDeclaration}")
        Logger.debug("      是 Statement? ${item is Statement}")
        Logger.debug("      是 TsInterfaceDeclaration? ${item is TsInterfaceDeclaration}")
        
        when (item) {
            is ModuleDeclaration -> {
                Logger.debug("      -> 进入 visitModuleDeclaration", 10)
                visitModuleDeclaration(item)
            }
            is Statement -> {
                Logger.debug("      -> 进入 visitStatement", 10)
                visitStatement(item)
            }
            else -> {
                Logger.warn("未处理的 ModuleItem 类型: ${item::class.simpleName}")
            }
        }
    }
    
    private fun visitModuleDeclaration(decl: ModuleDeclaration) {
        Logger.debug("        visitModuleDeclaration: ${decl::class.simpleName}", 12)
        when (decl) {
            is TsInterfaceDeclaration -> {
                interfaces.add(decl)
                Logger.verbose("发现 interface: ${decl.id.safeValue()}", 8)
            }
            is TsTypeAliasDeclaration -> {
                typeAliases.add(decl)
                Logger.verbose("发现 type alias: ${decl.id.safeValue()}", 8)
            }
            is TsModuleDeclaration -> {
                // 处理嵌套模块
                Logger.verbose("进入嵌套模块", 8)
                when (val body = decl.body) {
                    is TsModuleBlock -> {
                        val items = body.body.orEmpty()
                        Logger.verbose("  模块包含 ${items.size} 个项", 10)
                        items.forEach { visitModuleItem(it) }
                    }
                    is TsNamespaceDeclaration -> {
                        // TsNamespaceDeclaration 的 body 类型处理较复杂，暂时跳过
                        Logger.verbose("  跳过 namespace 声明", 10)
                    }
                }
            }
            else -> {
                Logger.verbose("跳过其他模块声明: ${decl::class.simpleName}", 8)
            }
        }
    }
    
    private fun visitStatement(stmt: Statement) {
        // Statement 中也可能包含类型定义
        // TsInterfaceDeclaration 和 TsTypeAliasDeclaration 在某些情况下是 Statement
        Logger.debug("        visitStatement: ${stmt::class.simpleName}", 12)
        when (stmt) {
            is TsInterfaceDeclaration -> {
                interfaces.add(stmt)
                Logger.verbose("发现 interface (from Statement): ${stmt.id.safeValue()}", 8)
            }
            is TsTypeAliasDeclaration -> {
                typeAliases.add(stmt)
                Logger.verbose("发现 type alias (from Statement): ${stmt.id.safeValue()}", 8)
            }
            else -> {
                // 其他 Statement 类型暂时跳过
                Logger.debug("        跳过其他 Statement: ${stmt::class.simpleName}", 12)
            }
        }
    }
    
    /**
     * 获取所有 interface 声明
     */
    fun getInterfaces(): List<TsInterfaceDeclaration> {
        return interfaces
    }
    
    /**
     * 获取所有 type alias 声明
     */
    fun getTypeAliases(): List<TsTypeAliasDeclaration> {
        return typeAliases
    }
    
    /**
     * 根据名称查找 interface
     */
    fun findInterface(name: String): TsInterfaceDeclaration? {
        return interfaces.find { it.id.safeValue() == name }
    }
    
    /**
     * 根据名称查找 type alias
     */
    fun findTypeAlias(name: String): TsTypeAliasDeclaration? {
        return typeAliases.find { it.id.safeValue() == name }
    }
}


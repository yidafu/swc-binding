package dev.yidafu.swc.generator.parser

import dev.yidafu.swc.generator.util.*

/**
 * TypeScript AST 访问器
 * 
 * 使用通用 AstNode 遍历 AST，提取 interface 和 type alias 声明
 */
class TsAstVisitor(jsonString: String) {
    private val program = AstNode.fromJson(jsonString)
    private val interfaces = mutableListOf<AstNode>()
    private val typeAliases = mutableListOf<AstNode>()
    
    /**
     * 遍历 AST 提取所有 interface 和 type alias
     */
    fun visit() {
        Logger.debug("AST 类型: ${program.type}", 4)
        when (program.type) {
            "Module", "Script" -> visitProgramBody()
        }
        Logger.debug("遍历完成: ${interfaces.size} 接口, ${typeAliases.size} 类型别名", 4)
    }
    
    private fun visitProgramBody() {
        val items = program.getNodes("body")
        Logger.verbose("Program 包含 ${items.size} 个项", 6)
        items.forEachIndexed { index, item ->
            Logger.verbose("  项 $index: ${item.type}", 8)
            visitModuleItem(item)
        }
    }
    
    private fun visitModuleItem(item: AstNode) {
        Logger.verbose("    处理 ModuleItem: ${item.type}", 10)
        
        when (item.type) {
            "TsInterfaceDeclaration" -> {
                interfaces.add(item)
                Logger.verbose("发现 interface: ${item.getInterfaceName()}", 8)
            }
            "TsTypeAliasDeclaration" -> {
                typeAliases.add(item)
                Logger.verbose("发现 type alias: ${item.getTypeAliasName()}", 8)
            }
            // 增强: 支持多种 export 形式
            "ExportDeclaration", "TsExportDeclaration", "ExportNamedDeclaration" -> {
                Logger.verbose("处理 Export: ${item.type}", 8)
                handleExportDeclaration(item)
            }
            "TsModuleDeclaration" -> {
                // 处理嵌套模块
                Logger.verbose("进入嵌套模块", 8)
                visitModuleDeclaration(item)
            }
            else -> {
                // 其他类型可能是 Statement，尝试处理
                Logger.debug("      -> 尝试作为 Statement 处理", 10)
                visitStatement(item)
            }
        }
    }
    
    /**
     * 处理 export 声明
     */
    private fun handleExportDeclaration(exportNode: AstNode) {
        // 尝试多种可能的字段名
        val declaration = exportNode.getNode("declaration") 
            ?: exportNode.getNode("decl")
            ?: return
        
        Logger.debug("        Export declaration 类型: ${declaration.type}", 12)
        
        when (declaration.type) {
            "TsInterfaceDeclaration" -> {
                interfaces.add(declaration)
                Logger.verbose("发现 interface (exported): ${declaration.getInterfaceName()}", 8)
            }
            "TsTypeAliasDeclaration" -> {
                typeAliases.add(declaration)
                Logger.verbose("发现 type alias (exported): ${declaration.getTypeAliasName()}", 8)
            }
            else -> {
                Logger.debug("        跳过 export: ${declaration.type}", 12)
            }
        }
    }
    
    private fun visitModuleDeclaration(decl: AstNode) {
        val body = decl.getNode("body") ?: return
        Logger.verbose("  模块声明 body 类型: ${body.type}", 10)
        
        when (body.type) {
            "TsModuleBlock" -> {
                val items = body.getNodes("body")
                Logger.verbose("  模块包含 ${items.size} 个项", 10)
                items.forEach { visitModuleItem(it) }
            }
            "TsNamespaceDeclaration" -> {
                // 递归处理 namespace
                Logger.verbose("  跳过 namespace 声明", 10)
            }
            else -> {
                Logger.verbose("  未知的模块 body 类型: ${body.type}", 10)
            }
        }
    }
    
    private fun visitStatement(stmt: AstNode) {
        // Statement 中也可能包含类型定义
        Logger.debug("        visitStatement: ${stmt.type}", 12)
        when (stmt.type) {
            "TsInterfaceDeclaration" -> {
                interfaces.add(stmt)
                Logger.verbose("发现 interface (from Statement): ${stmt.getInterfaceName()}", 8)
            }
            "TsTypeAliasDeclaration" -> {
                typeAliases.add(stmt)
                Logger.verbose("发现 type alias (from Statement): ${stmt.getTypeAliasName()}", 8)
            }
            else -> {
                // 其他 Statement 类型暂时跳过
                Logger.debug("        跳过其他 Statement: ${stmt.type}", 12)
            }
        }
    }
    
    /**
     * 获取所有 interface 声明
     */
    fun getInterfaces(): List<AstNode> {
        return interfaces
    }
    
    /**
     * 获取所有 type alias 声明
     */
    fun getTypeAliases(): List<AstNode> {
        return typeAliases
    }
    
    /**
     * 根据名称查找 interface
     */
    fun findInterface(name: String): AstNode? {
        return interfaces.find { it.getInterfaceName() == name }
    }
    
    /**
     * 根据名称查找 type alias
     */
    fun findTypeAlias(name: String): AstNode? {
        return typeAliases.find { it.getTypeAliasName() == name }
    }
}

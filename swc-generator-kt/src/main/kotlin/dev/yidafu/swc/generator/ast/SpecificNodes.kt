package dev.yidafu.swc.generator.ast

/**
 * 专用节点类
 * 
 * 为常用的 AST 节点类型提供更友好的封装
 */

/**
 * Program 节点（Module 或 Script）
 */
class ProgramNode(private val node: AstNode) {
    val type: String get() = node.type  // "Module" 或 "Script"
    val body: List<AstNode> get() = node.getNodes("body")
    val span: Span? get() = node.getSpan()
    
    fun isModule() = type == "Module"
    fun isScript() = type == "Script"
    
    /**
     * 遍历所有 body 项
     */
    fun forEachItem(block: (AstNode) -> Unit) {
        body.forEach(block)
    }
}

/**
 * Interface 声明节点
 */
class InterfaceNode(private val node: AstNode) {
    val id: String? get() = node.getNode("id")?.getIdentifierValue()
    val body: AstNode? get() = node.getNode("body")
    val extends: List<AstNode> get() = node.getNodes("extends")
    val typeParams: AstNode? get() = node.getNode("typeParams")
    val declare: Boolean get() = node.getBoolean("declare") ?: false
    val span: Span? get() = node.getSpan()
    
    /**
     * 获取所有属性
     */
    fun getProperties(): List<AstNode> {
        return body?.getNodes("body") ?: emptyList()
    }
    
    /**
     * 获取所有 extends 的类型名称
     */
    fun getExtendsNames(): List<String> {
        return extends.mapNotNull { extend ->
            extend.getNode("expression")?.let { expr ->
                when {
                    expr.isIdentifier() -> expr.getIdentifierValue()
                    expr.type == "TsQualifiedName" -> {
                        expr.getNode("right")?.getIdentifierValue()
                    }
                    else -> null
                }
            }
        }
    }
    
    /**
     * 遍历所有属性
     */
    fun forEachProperty(block: (AstNode) -> Unit) {
        getProperties().forEach(block)
    }
}

/**
 * Type Alias 节点
 */
class TypeAliasNode(private val node: AstNode) {
    val id: String? get() = node.getNode("id")?.getIdentifierValue()
    val typeAnnotation: AstNode? get() = node.getNode("typeAnnotation")
    val typeParams: AstNode? get() = node.getNode("typeParams")
    val declare: Boolean get() = node.getBoolean("declare") ?: false
    val span: Span? get() = node.getSpan()
    
    /**
     * 是否为 Union Type
     */
    fun isUnionType(): Boolean {
        return typeAnnotation?.isUnionType() == true
    }
    
    /**
     * 是否为 Intersection Type
     */
    fun isIntersectionType(): Boolean {
        return typeAnnotation?.isIntersectionType() == true
    }
    
    /**
     * 是否为 Literal Union（所有成员都是字面量）
     */
    fun isLiteralUnion(): Boolean {
        val annotation = typeAnnotation ?: return false
        if (!annotation.isUnionType()) return false
        val types = annotation.getNodes("types")
        return types.isNotEmpty() && types.all { it.isLiteralType() }
    }
    
    /**
     * 获取 Union Type 的所有类型
     */
    fun getUnionTypes(): List<AstNode> {
        val annotation = typeAnnotation ?: return emptyList()
        if (!annotation.isUnionType()) return emptyList()
        return annotation.getNodes("types")
    }
    
    /**
     * 获取 Intersection Type 的所有类型
     */
    fun getIntersectionTypes(): List<AstNode> {
        val annotation = typeAnnotation ?: return emptyList()
        if (!annotation.isIntersectionType()) return emptyList()
        return annotation.getNodes("types")
    }
}

/**
 * Property Signature 节点
 */
class PropertySignatureNode(private val node: AstNode) {
    val key: AstNode? get() = node.getNode("key")
    val typeAnnotation: AstNode? get() = node.getNode("typeAnnotation")
    val optional: Boolean get() = node.getBoolean("optional") ?: false
    val readonly: Boolean get() = node.getBoolean("readonly") ?: false
    val computed: Boolean get() = node.getBoolean("computed") ?: false
    val span: Span? get() = node.getSpan()
    
    /**
     * 获取属性名称
     */
    fun getName(): String? {
        val k = key ?: return null
        return when {
            k.isIdentifier() -> k.getIdentifierValue()
            k.isStringLiteral() -> k.getStringLiteralValue()
            else -> null
        }
    }
    
    /**
     * 获取类型注解节点（去除 TsTypeAnnotation 包装）
     */
    fun getTypeAnnotationNode(): AstNode? {
        val annotation = typeAnnotation ?: return null
        // TsTypeAnnotation 有一个 typeAnnotation 字段包含实际类型
        return annotation.getNode("typeAnnotation")
    }
}

/**
 * Export Declaration 节点
 */
class ExportDeclarationNode(private val node: AstNode) {
    val declaration: AstNode? get() = node.getNode("declaration")
    val span: Span? get() = node.getSpan()
    
    /**
     * 是否导出 Interface
     */
    fun isExportInterface(): Boolean {
        return declaration?.isInterface() == true
    }
    
    /**
     * 是否导出 Type Alias
     */
    fun isExportTypeAlias(): Boolean {
        return declaration?.isTypeAlias() == true
    }
}

/**
 * Type Annotation 节点
 */
class TypeAnnotationNode(private val node: AstNode) {
    val typeAnnotation: AstNode? get() = node.getNode("typeAnnotation")
    val span: Span? get() = node.getSpan()
    
    /**
     * 获取实际的类型节点
     */
    fun getActualType(): AstNode? = typeAnnotation
}

// ========== 工厂方法 ==========

/**
 * 转换为 ProgramNode
 */
fun AstNode.asProgramNode(): ProgramNode? {
    return if (isModule() || isScript()) ProgramNode(this) else null
}

/**
 * 转换为 InterfaceNode
 */
fun AstNode.asInterfaceNode(): InterfaceNode? {
    return if (isInterface()) InterfaceNode(this) else null
}

/**
 * 转换为 TypeAliasNode
 */
fun AstNode.asTypeAliasNode(): TypeAliasNode? {
    return if (isTypeAlias()) TypeAliasNode(this) else null
}

/**
 * 转换为 PropertySignatureNode
 */
fun AstNode.asPropertySignatureNode(): PropertySignatureNode? {
    return if (isPropertySignature()) PropertySignatureNode(this) else null
}

/**
 * 转换为 ExportDeclarationNode
 */
fun AstNode.asExportDeclarationNode(): ExportDeclarationNode? {
    return if (isExportDeclaration()) ExportDeclarationNode(this) else null
}

/**
 * 转换为 TypeAnnotationNode
 */
fun AstNode.asTypeAnnotationNode(): TypeAnnotationNode? {
    return if (type == "TsTypeAnnotation") TypeAnnotationNode(this) else null
}


package dev.yidafu.swc.generator.config

/**
 * Ctxt 字段配置
 * 管理需要独立 ctxt 字段的类
 */
object CtxtFieldsConfig {
    /**
     * 需要 ctxt 字段的类类型
     */
    enum class ClassWithCtxt(val value: String, val category: String) {
        // 语句相关
        BLOCK_STATEMENT("BlockStatement", "语句"),
        
        // 表达式相关
        CALL_EXPRESSION("CallExpression", "表达式"),
        NEW_EXPRESSION("NewExpression", "表达式"),
        ARROW_FUNCTION_EXPRESSION("ArrowFunctionExpression", "表达式"),
        TAGGED_TEMPLATE_EXPRESSION("TaggedTemplateExpression", "表达式"),
        
        // 声明相关
        FUNCTION_DECLARATION("FunctionDeclaration", "声明"),
        VARIABLE_DECLARATION("VariableDeclaration", "声明"),
        
        // 类相关
        CLASS("Class", "类"),
        PRIVATE_PROPERTY("PrivateProperty", "类"),
        CONSTRUCTOR("Constructor", "类"),
        
        // 标识符相关
        IDENTIFIER("Identifier", "标识符")
    }

    /**
     * 需要自动添加 ctxt 字段的类名列表
     * 这些类对应 Rust 端包含独立的 ctxt: SyntaxContext 字段的结构体
     * 注意：ctxt 不是 Span 的一部分，而是这些结构体的独立字段
     */
    val CLASSES_WITH_CTXT: Set<String> = ClassWithCtxt.values().map { it.value }.toSet()

    /**
     * 检查类是否需要 ctxt 字段
     */
    fun needsCtxtField(className: String): Boolean {
        return CLASSES_WITH_CTXT.contains(className)
    }

    /**
     * 按类别获取需要 ctxt 字段的类
     */
    fun getClassesByCategory(category: String): Set<String> {
        return ClassWithCtxt.values()
            .filter { it.category == category }
            .map { it.value }
            .toSet()
    }
}


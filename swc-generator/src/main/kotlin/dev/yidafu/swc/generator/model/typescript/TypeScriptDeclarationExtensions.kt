package dev.yidafu.swc.generator.model.typescript

/**
 * TypeScript 声明扩展方法
 */
fun TypeScriptDeclaration.isInterface(): Boolean = this is TypeScriptDeclaration.InterfaceDeclaration

fun TypeScriptDeclaration.isTypeAlias(): Boolean = this is TypeScriptDeclaration.TypeAliasDeclaration

/**
 * 获取声明名称
 */
fun TypeScriptDeclaration.getName(): String = when (this) {
    is TypeScriptDeclaration.InterfaceDeclaration -> name
    is TypeScriptDeclaration.TypeAliasDeclaration -> name
}

/**
 * 获取类型参数
 */
fun TypeScriptDeclaration.getTypeParameters(): List<TypeParameter> = when (this) {
    is TypeScriptDeclaration.InterfaceDeclaration -> typeParameters
    is TypeScriptDeclaration.TypeAliasDeclaration -> typeParameters
}

/**
 * 检查是否有泛型参数
 */
fun TypeScriptDeclaration.hasTypeParameters(): Boolean = getTypeParameters().isNotEmpty()

/**
 * 检查是否为空声明（无成员）
 */
fun TypeScriptDeclaration.isEmpty(): Boolean = when (this) {
    is TypeScriptDeclaration.InterfaceDeclaration -> members.isEmpty()
    is TypeScriptDeclaration.TypeAliasDeclaration -> false // 类型别名总是有类型
}

/**
 * 获取所有引用的类型名称
 */
fun TypeScriptDeclaration.getReferencedTypeNames(): Set<String> = when (this) {
    is TypeScriptDeclaration.InterfaceDeclaration -> {
        val fromExtends = extends.map { it.name }.toSet()
        val fromMembers = members.flatMap { it.type.getReferencedTypeNames() }.toSet()
        fromExtends + fromMembers
    }
    is TypeScriptDeclaration.TypeAliasDeclaration -> {
        type.getReferencedTypeNames()
    }
}

/**
 * 检查是否引用特定类型
 */
fun TypeScriptDeclaration.referencesType(typeName: String): Boolean = getReferencedTypeNames().contains(typeName)

/**
 * 获取继承链深度
 */
fun TypeScriptDeclaration.getInheritanceDepth(): Int = when (this) {
    is TypeScriptDeclaration.InterfaceDeclaration -> {
        if (extends.isEmpty()) 0 else 1
    }
    is TypeScriptDeclaration.TypeAliasDeclaration -> 0
}

/**
 * 检查是否为配置接口（基于命名模式）
 */
fun TypeScriptDeclaration.isConfigInterface(): Boolean = when (this) {
    is TypeScriptDeclaration.InterfaceDeclaration -> {
        name.endsWith("Config") || name.endsWith("Options") || name.endsWith("Settings")
    }
    is TypeScriptDeclaration.TypeAliasDeclaration -> false
}

/**
 * 检查是否为联合类型别名
 */
fun TypeScriptDeclaration.isUnionTypeAlias(): Boolean = when (this) {
    is TypeScriptDeclaration.TypeAliasDeclaration -> type is TypeScriptType.Union
    is TypeScriptDeclaration.InterfaceDeclaration -> false
}

/**
 * 检查是否为字面量联合类型别名
 */
fun TypeScriptDeclaration.isLiteralUnionTypeAlias(): Boolean = when (this) {
    is TypeScriptDeclaration.TypeAliasDeclaration -> {
        type is TypeScriptType.Union && type.types.all { it is TypeScriptType.Literal }
    }
    is TypeScriptDeclaration.InterfaceDeclaration -> false
}

/**
 * 检查是否为交叉类型别名
 */
fun TypeScriptDeclaration.isIntersectionTypeAlias(): Boolean = when (this) {
    is TypeScriptDeclaration.TypeAliasDeclaration -> type is TypeScriptType.Intersection
    is TypeScriptDeclaration.InterfaceDeclaration -> false
}

/**
 * 获取文档注释
 */
fun TypeScriptDeclaration.getKdoc(): String? = when (this) {
    is TypeScriptDeclaration.InterfaceDeclaration -> kdoc
    is TypeScriptDeclaration.TypeAliasDeclaration -> kdoc
}

/**
 * 验证声明完整性
 */
fun TypeScriptDeclaration.validate(): List<String> = when (this) {
    is TypeScriptDeclaration.InterfaceDeclaration -> {
        val errors = mutableListOf<String>()
        if (name.isBlank()) errors.add("Interface name cannot be blank")
        if (name.contains(" ")) errors.add("Interface name cannot contain spaces")
        // 可以添加更多验证规则
        errors
    }
    is TypeScriptDeclaration.TypeAliasDeclaration -> {
        val errors = mutableListOf<String>()
        if (name.isBlank()) errors.add("Type alias name cannot be blank")
        if (name.contains(" ")) errors.add("Type alias name cannot contain spaces")
        // 可以添加更多验证规则
        errors
    }
}

/**
 * 获取声明的字符串表示（用于调试）
 */
fun TypeScriptDeclaration.toDebugString(): String = when (this) {
    is TypeScriptDeclaration.InterfaceDeclaration -> {
        "interface $name${if (typeParameters.isNotEmpty()) "<${typeParameters.joinToString(", ") { it.name }}>" else ""}${if (extends.isNotEmpty()) " extends ${extends.joinToString(", ") { it.name }}" else ""}"
    }
    is TypeScriptDeclaration.TypeAliasDeclaration -> {
        "type $name${if (typeParameters.isNotEmpty()) "<${typeParameters.joinToString(", ") { it.name }}>" else ""} = ${type.getTypeName()}"
    }
}

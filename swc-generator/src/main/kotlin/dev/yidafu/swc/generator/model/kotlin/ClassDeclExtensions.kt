package dev.yidafu.swc.generator.model.kotlin

import dev.yidafu.swc.generator.util.Logger

/**
 * ClassDecl的扩展函数
 * 将KotlinClass的业务逻辑迁移到这里
 */

/**
 * 包装保留字
 */
fun wrapReservedWord(typeName: String): String {
    return if (KotlinIdentifierConstants.RESERVED_WORDS.contains(typeName.lowercase())) {
        "`$typeName`"
    } else {
        typeName
    }
}

/**
 * 验证类型名称是否有效
 */
fun isValidKotlinTypeName(typeName: String): Boolean {
    if (typeName.isBlank()) {
        Logger.debug("类型名称为空: '$typeName'")
        return false
    }

    // 检查是否包含泛型符号
    if (typeName.contains("<") || typeName.contains(">")) {
        Logger.debug("类型名称包含泛型符号: '$typeName'")
        return false
    }

    // 检查是否包含空白字符
    if (typeName.contains(" ") || typeName.contains("\n") || typeName.contains("\t")) {
        Logger.debug("类型名称包含空白字符: '$typeName'")
        return false
    }

    // 检查是否包含注释残留
    if (typeName.contains("/*") || typeName.contains("*/")) {
        Logger.debug("类型名称包含注释: '$typeName'")
        return false
    }

    // 检查是否以小写字母开头（但保留字例外）
    if (typeName.isNotEmpty() && typeName[0].isLowerCase() &&
        !KotlinIdentifierConstants.RESERVED_WORDS.contains(typeName.lowercase())
    ) {
        Logger.debug("类型名称首字母小写: '$typeName'")
        return false
    }

    // 检查是否包含连字符
    if (typeName.contains("-")) {
        Logger.debug("类型名称包含连字符: '$typeName'")
        return false
    }

    // 检查是否符合 Kotlin 标识符规范
    if (!typeName.matches(Regex("[A-Za-z][a-zA-Z0-9_]*"))) {
        Logger.debug("类型名称不符合 Kotlin 标识符规范: '$typeName'")
        return false
    }

    // 保留字可以用反引号包裹
    if (KotlinIdentifierConstants.RESERVED_WORDS.contains(typeName.lowercase())) {
        Logger.debug("类型名称是保留字，将用反引号包裹: '$typeName'")
    }

    return true
}

/**
 * 为属性添加 SerialName 注解（如果不存在）
 */
private fun KotlinDeclaration.PropertyDecl.withSerialNameAnnotation(): KotlinDeclaration.PropertyDecl {
    val serialName = getPropertySerialName()
    if (annotations.any { it.name == "SerialName" }) return this
    return copy(annotations = annotations + KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral(serialName))))
}

/**
 * 转换为 interface
 */
fun KotlinDeclaration.ClassDecl.toInterface(): KotlinDeclaration.ClassDecl {
    return copy(
        modifier = ClassModifier.Interface,
        annotations = listOf(KotlinDeclaration.Annotation("SwcDslMarker")),
        properties = properties.map { prop ->
            prop.withNullableIfNeeded()
                .withSerialNameAnnotation()
                .copy(defaultValue = null)
        }
    )
}

/**
 * 转换为 class
 */
fun KotlinDeclaration.ClassDecl.toClass(): KotlinDeclaration.ClassDecl {
    val modifier = ClassModifier.FinalClass // 所有类都使用普通 class
    return copy(
        modifier = modifier,
        properties = properties.map { prop ->
            prop.withNullableIfNeeded()
                .withSerialNameAnnotation()
                .copy(defaultValue = Expression.NullLiteral)
        }
    )
}

/**
 * 转换为实现类
 */
fun KotlinDeclaration.ClassDecl.toImplClass(): KotlinDeclaration.ClassDecl {
    val discriminator = getDiscriminator()
    val serialName = computeSerialName(discriminator)
    val interfaceName = name // 保存接口名称

    return copy(
        name = wrapReservedWord("${name}Impl"),
        modifier = ClassModifier.FinalClass,
        parents = listOf(KotlinType.Simple(interfaceName)),
        annotations = listOf(
            KotlinDeclaration.Annotation("SwcDslMarker"),
            KotlinDeclaration.Annotation("Serializable"),
            KotlinDeclaration.Annotation("JsonClassDiscriminator", listOf(Expression.StringLiteral(discriminator))),
            KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral(serialName)))
        ),
        properties = properties.map { prop ->
            val serialName = prop.getPropertySerialName()
            val existingSerialName = prop.annotations.find { it.name == "SerialName" }
            val annotations = if (existingSerialName != null) {
                prop.annotations
            } else {
                prop.annotations + KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral(serialName)))
            }
            val updatedProp = prop.withNullableIfNeeded().copy(
                modifier = PropertyModifier.OverrideVar,
                annotations = annotations
            )

            // 如果是 type 属性，设置为不可空 String 类型并设置默认值为接口名称
            if (prop.name == "type") {
                val innerType = when (updatedProp.type) {
                    is KotlinType.Nullable -> updatedProp.type.innerType
                    else -> updatedProp.type
                }
                if (innerType is KotlinType.StringType) {
                    // type 属性应该是不可空的 String 类型
                    updatedProp.copy(
                        type = KotlinType.StringType,
                        defaultValue = Expression.StringLiteral(interfaceName)
                    )
                } else {
                    updatedProp.copy(defaultValue = Expression.NullLiteral)
                }
            } else {
                // 其他属性设置默认值为 null
                updatedProp.copy(defaultValue = Expression.NullLiteral)
            }
        }
    )
}

/**
 * 获取判别器
 */
fun KotlinDeclaration.ClassDecl.getDiscriminator(): String {
    return when {
        name.endsWith("Impl") -> {
            val baseName = name.removeSuffix("Impl")
            when {
                baseName.endsWith("Expr") -> "type"
                baseName.endsWith("Stmt") -> "type"
                baseName.endsWith("Pat") -> "type"
                baseName.endsWith("Decl") -> "type"
                baseName.endsWith("ModuleItem") -> "type"
                else -> "type"
            }
        }
        name.endsWith("Expr") -> "type"
        name.endsWith("Stmt") -> "type"
        name.endsWith("Pat") -> "type"
        name.endsWith("Decl") -> "type"
        name.endsWith("ModuleItem") -> "type"
        else -> "type"
    }
}

/**
 * 计算SerialName
 */
fun KotlinDeclaration.ClassDecl.computeSerialName(discriminator: String): String {
    return when (discriminator) {
        "type" -> {
            when {
                name.endsWith("Impl") -> {
                    val baseName = name.removeSuffix("Impl")
                    when {
                        baseName.endsWith("Expr") -> baseName
                        baseName.endsWith("Stmt") -> baseName
                        baseName.endsWith("Pat") -> baseName
                        baseName.endsWith("Decl") -> baseName
                        baseName.endsWith("ModuleItem") -> baseName
                        else -> baseName
                    }
                }
                else -> name
            }
        }
        else -> name
    }
}

/**
 * 克隆ClassDecl
 */
fun KotlinDeclaration.ClassDecl.clone(): KotlinDeclaration.ClassDecl {
    return copy(
        properties = properties.map { it.copy() }
    )
}

/**
 * 获取所有属性（包括继承的）
 */
fun KotlinDeclaration.ClassDecl.getAllProperties(): List<KotlinDeclaration.PropertyDecl> {
    val allProperties = mutableListOf<KotlinDeclaration.PropertyDecl>()

    // 添加当前类的属性
    allProperties.addAll(properties)

    // 添加父类的属性
    parents.forEach { parentName ->
        // 这里需要从全局类映射中查找父类
        // 暂时返回当前属性，实际实现需要访问全局类映射
    }
    return allProperties
}

/**
 * 检查是否为接口
 */
fun KotlinDeclaration.ClassDecl.isInterface(): Boolean {
    return modifier == ClassModifier.Interface
}

/**
 * 检查是否为类
 */
fun KotlinDeclaration.ClassDecl.isClass(): Boolean {
    return modifier == ClassModifier.FinalClass
}

/**
 * 检查是否为实现类
 */
fun KotlinDeclaration.ClassDecl.isImplClass(): Boolean {
    return name.endsWith("Impl")
}

/**
 * 获取类名（不带反引号）
 */
fun KotlinDeclaration.ClassDecl.getClassName(): String {
    return if (name.startsWith("`") && name.endsWith("`")) {
        name.substring(1, name.length - 1)
    } else {
        name
    }
}

/**
 * 检查是否应该是 sealed 类型（有子类型）
 * 注意：在 ADT 架构中，这个信息应该从 TypeScript ADT 结构获取
 * 这里保留向后兼容，但建议使用 InheritanceAnalyzer
 */
fun KotlinDeclaration.ClassDecl.shouldBeSealed(allClasses: List<KotlinDeclaration.ClassDecl>): Boolean {
    if (modifier != ClassModifier.Interface) return false
    // 在 ADT 架构中，这个信息应该从 TypeScript ADT 结构分析
    // 暂时返回 false，建议使用 InheritanceAnalyzer 替代
    return false
}

/**
 * 获取继承深度（距离根节点的距离）
 * 注意：在 ADT 架构中，这个信息应该从 TypeScript ADT 结构获取
 * 这里保留向后兼容，但建议使用 InheritanceAnalyzer
 */
fun KotlinDeclaration.ClassDecl.getInheritanceDepth(): Int {
    // 在 ADT 架构中，这个信息应该从 TypeScript ADT 结构分析
    // 暂时返回 0，建议使用 InheritanceAnalyzer 替代
    return 0
}

/**
 * 获取继承树根节点
 * 注意：在 ADT 架构中，这个信息应该从 TypeScript ADT 结构获取
 * 这里保留向后兼容，但建议使用 InheritanceAnalyzer
 */
fun KotlinDeclaration.ClassDecl.getInheritanceRoot(): String {
    // 在 ADT 架构中，这个信息应该从 TypeScript ADT 结构分析
    // 暂时返回自身名称，建议使用 InheritanceAnalyzer 替代
    return name
}

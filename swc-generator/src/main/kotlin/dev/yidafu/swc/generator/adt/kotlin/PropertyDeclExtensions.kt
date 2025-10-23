package dev.yidafu.swc.generator.adt.kotlin

import dev.yidafu.swc.generator.config.HardcodedRules

/**
 * PropertyDecl的扩展函数
 * 将KotlinProperty的业务逻辑迁移到这里
 */

/**
 * 判断是否为数组
 */
fun KotlinDeclaration.PropertyDecl.isArray(): Boolean {
    return when (type) {
        is KotlinType.Generic -> type.name == "Array" || type.name == "List"
        else -> false
    }
}

/**
 * 判断是否为联合类型
 */
fun KotlinDeclaration.PropertyDecl.isUnion(): Boolean {
    return when (type) {
        is KotlinType.Union -> true
        else -> false
    }
}

/**
 * 判断是否为Booleanable
 */
fun KotlinDeclaration.PropertyDecl.isBooleanable(): Boolean {
    return when (type) {
        is KotlinType.Boolean -> true
        is KotlinType.Union -> {
            type.types.any { it is KotlinType.Boolean }
        }
        else -> false
    }
}

/**
 * 判断是否为基本类型
 */
fun KotlinDeclaration.PropertyDecl.isPrimitive(): Boolean {
    return when (type) {
        is KotlinType.StringType -> true
        is KotlinType.Int -> true
        is KotlinType.Boolean -> true
        is KotlinType.Long -> true
        is KotlinType.Double -> true
        is KotlinType.Float -> true
        is KotlinType.Char -> true
        is KotlinType.Byte -> true
        is KotlinType.Short -> true
        else -> false
    }
}

/**
 * 获取注解字符串
 */
fun KotlinDeclaration.PropertyDecl.getAnnotation(): String {
    val discriminator = getDiscriminator()
    return if (discriminator != "type") {
        "@SerialName(\"$discriminator\")"
    } else {
        ""
    }
}

/**
 * 获取类型字符串
 */
fun KotlinDeclaration.PropertyDecl.getTypeString(): String {
    return type.toTypeString()
}

/**
 * 获取实际类型字符串
 */
fun KotlinDeclaration.PropertyDecl.getActualType(): String {
    return type.toTypeString()
}

/**
 * 获取判别器
 */
fun KotlinDeclaration.PropertyDecl.getDiscriminator(): String {
    // 从注解中查找SerialName
    val serialNameAnnotation = annotations.find { it.name == "SerialName" }
    return if (serialNameAnnotation != null && serialNameAnnotation.arguments.isNotEmpty()) {
        when (val firstArg = serialNameAnnotation.arguments.first()) {
            is Expression.StringLiteral -> firstArg.value
            else -> "type"
        }
    } else {
        "type"
    }
}

/**
 * 获取属性的序列化名称
 * - 优先使用已有的 @SerialName 注解中的值
 * - 否则使用属性原始名称
 */
fun KotlinDeclaration.PropertyDecl.getPropertySerialName(): String {
    // 检查是否已有 SerialName 注解
    val existingSerialName = annotations.find { it.name == "SerialName" }
    if (existingSerialName != null && existingSerialName.arguments.isNotEmpty()) {
        when (val firstArg = existingSerialName.arguments.first()) {
            is Expression.StringLiteral -> return firstArg.value
            else -> {
                // 如果不是字符串字面量，使用属性名称
            }
        }
    }
    // 使用属性原始名称
    return name
}

/**
 * 克隆PropertyDecl
 */
fun KotlinDeclaration.PropertyDecl.clone(): KotlinDeclaration.PropertyDecl {
    return copy()
}

/**
 * 检查是否为可选属性
 */
fun KotlinDeclaration.PropertyDecl.isOptional(): Boolean {
    return when (type) {
        is KotlinType.Union -> {
            type.types.any { it is KotlinType.Nullable }
        }
        else -> false
    }
}

/**
 * 检查是否为必需属性
 */
fun KotlinDeclaration.PropertyDecl.isRequired(): Boolean {
    return !isOptional()
}

/**
 * 获取属性名（不带反引号）
 */
fun KotlinDeclaration.PropertyDecl.getPropertyName(): String {
    return if (name.startsWith("`") && name.endsWith("`")) {
        name.substring(1, name.length - 1)
    } else {
        name
    }
}

/**
 * 检查是否为数组属性
 */
fun KotlinDeclaration.PropertyDecl.isArrayProperty(): Boolean {
    return when (type) {
        is KotlinType.Generic -> type.name == "Array" || type.name == "List"
        else -> false
    }
}

/**
 * 检查是否为对象属性
 */
fun KotlinDeclaration.PropertyDecl.isObjectProperty(): Boolean {
    return when (type) {
        is KotlinType.Simple -> true
        is KotlinType.Generic -> true
        else -> false
    }
}

/**
 * 获取默认值表达式
 */
fun KotlinDeclaration.PropertyDecl.getDefaultValueExpression(): Expression? {
    return defaultValue
}

/**
 * 检查是否有默认值
 */
fun KotlinDeclaration.PropertyDecl.hasDefaultValue(): Boolean {
    return defaultValue != null
}

/**
 * 获取KDoc注释
 */
fun KotlinDeclaration.PropertyDecl.getKDoc(): String? {
    return kdoc
}

/**
 * 检查是否有注释
 */
fun KotlinDeclaration.PropertyDecl.hasComment(): Boolean {
    return !kdoc.isNullOrBlank()
}

/**
 * 将所有属性类型包装为 Nullable 类型
 */
fun KotlinDeclaration.PropertyDecl.withNullableIfNeeded(): KotlinDeclaration.PropertyDecl {
    // 如果已经是 nullable，直接返回
    if (type is KotlinType.Nullable) {
        return this
    }
    
    // 所有属性都变为可空，保持原有默认值
    return copy(
        type = KotlinType.Nullable(type)
    )
}
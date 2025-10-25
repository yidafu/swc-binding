package dev.yidafu.swc.generator.parser

/**
 * AstNode 扩展函数
 * * 提供便捷的节点访问和类型判断方法
 */

// ========== 便捷访问方法 ==========

/**
 * 获取 Identifier 节点的 value
 */
fun AstNode.getIdentifierValue(): String? = if (type == "Identifier") getString("value") else null

/**
 * 获取 StringLiteral 节点的 value
 */
fun AstNode.getStringLiteralValue(): String? = if (type == "StringLiteral") getString("value") else null

/**
 * 获取 NumericLiteral 节点的 value
 */
fun AstNode.getNumericLiteralValue(): Double? = if (type == "NumericLiteral") {
    val raw = getRaw("value")
    if (raw is kotlinx.serialization.json.JsonPrimitive) {
        raw.content.toDoubleOrNull()
    } else {
        null
    }
} else {
    null
}

/**
 * 获取 BooleanLiteral 节点的 value
 */
fun AstNode.getBooleanLiteralValue(): Boolean? = if (type == "BooleanLiteral") getBoolean("value") else null

// ========== 类型判断 ==========

/**
 * 是否为 Interface 声明
 */
fun AstNode.isInterface() = type == "TsInterfaceDeclaration"

/**
 * 是否为 Type Alias 声明
 */
fun AstNode.isTypeAlias() = type == "TsTypeAliasDeclaration"

/**
 * 是否为 Union Type
 */
fun AstNode.isUnionType() = type == "TsUnionType"

/**
 * 是否为 Intersection Type
 */
fun AstNode.isIntersectionType() = type == "TsIntersectionType"

/**
 * 是否为 Literal Type
 */
fun AstNode.isLiteralType() = type == "TsLiteralType"

/**
 * 是否为 Type Reference
 */
fun AstNode.isTypeReference() = type == "TsTypeReference"

/**
 * 是否为 Type Literal
 */
fun AstNode.isTypeLiteral() = type == "TsTypeLiteral"

/**
 * 是否为 Keyword Type
 */
fun AstNode.isKeywordType() = type == "TsKeywordType"

/**
 * 是否为 Array Type
 */
fun AstNode.isArrayType() = type == "TsArrayType"

/**
 * 是否为 Identifier
 */
fun AstNode.isIdentifier() = type == "Identifier"

/**
 * 是否为 String Literal
 */
fun AstNode.isStringLiteral() = type == "StringLiteral"

/**
 * 是否为 Numeric Literal
 */
fun AstNode.isNumericLiteral() = type == "NumericLiteral"

/**
 * 是否为 Boolean Literal
 */
fun AstNode.isBooleanLiteral() = type == "BooleanLiteral"

/**
 * 是否为 Module
 */
fun AstNode.isModule() = type == "Module"

/**
 * 是否为 Script
 */
fun AstNode.isScript() = type == "Script"

/**
 * 是否为 Export Declaration
 */
fun AstNode.isExportDeclaration() = type == "ExportDeclaration"

/**
 * 是否为 Property Signature
 */
fun AstNode.isPropertySignature() = type == "TsPropertySignature"

// ========== 节点导航 ==========

/**
 * 获取 TypeReference 的类型名称
 */
fun AstNode.getTypeReferenceName(): String? {
    if (!isTypeReference()) return null
    val typeName = getNode("typeName") ?: return null
    return when {
        typeName.isIdentifier() -> typeName.getIdentifierValue()
        typeName.type == "TsQualifiedName" -> {
            // 对于 qualified name，获取最右侧的标识符
            typeName.getNode("right")?.getIdentifierValue()
        }
        else -> null
    }
}

/**
 * 获取 Interface 的名称
 */
fun AstNode.getInterfaceName(): String? {
    if (!isInterface()) return null
    return getNode("id")?.getIdentifierValue()
}

/**
 * 获取 Type Alias 的名称
 */
fun AstNode.getTypeAliasName(): String? {
    if (!isTypeAlias()) return null
    return getNode("id")?.getIdentifierValue()
}

/**
 * 获取 Property Signature 的名称
 */
fun AstNode.getPropertyName(): String? {
    if (!isPropertySignature()) return null
    val key = getNode("key") ?: return null
    return when {
        key.isIdentifier() -> key.getIdentifierValue()
        key.isStringLiteral() -> key.getStringLiteralValue()
        else -> null
    }
}

/**
 * 获取 Keyword Type 的 kind
 */
fun AstNode.getKeywordKind(): String? {
    if (!isKeywordType()) return null
    return getString("kind")
}

/**
 * 获取 Literal Type 的字面量值（字符串形式）
 */
fun AstNode.getLiteralValue(): String? {
    if (!isLiteralType()) return null
    val literal = getNode("literal") ?: return null
    return when {
        literal.isStringLiteral() -> literal.getStringLiteralValue()  // 不添加双引号，让调用方处理
        literal.isNumericLiteral() -> literal.getNumericLiteralValue()?.toString()
        literal.isBooleanLiteral() -> literal.getBooleanLiteralValue()?.toString()
        else -> null
    }
}


package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.adt.converter.TypeConverter
import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.typescript.*
import dev.yidafu.swc.generator.config.GlobalConfig
import dev.yidafu.swc.generator.config.HardcodedRules
import dev.yidafu.swc.generator.core.model.GenericParent
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.util.*

/**
 * Interface 提取器
 * 使用 AstNode 提取 interface 的属性和关系
 */
class InterfaceExtractor(private val visitor: TsAstVisitor) {
    private val classPropertiesMap = mutableMapOf<String, List<KotlinDeclaration.PropertyDecl>>()
    private val classAllPropertiesMap = mutableMapOf<String, List<KotlinDeclaration.PropertyDecl>>()

    /**
     * 提取 interface 的属性（仅自身属性，不含继承）
     */
    fun getInterfaceOwnProperties(interfaceDecl: AstNode): List<KotlinDeclaration.PropertyDecl> {
        val interfaceName = interfaceDecl.getInterfaceName() ?: return emptyList()

        // 使用缓存避免重复计算
        return classPropertiesMap.getOrPut(interfaceName) {
            val body = interfaceDecl.getNode("body")
            val members = body?.getNodes("body") ?: emptyList()
            members.mapNotNull { member ->
                if (member.isPropertySignature()) {
                    extractProperty(member)
                } else {
                    null
                }
            }
        }
    }

    /**
     * 提取 interface 的所有属性（包含继承的）
     */
    fun getInterfaceAllProperties(interfaceDecl: AstNode): List<KotlinDeclaration.PropertyDecl> {
        val interfaceName = interfaceDecl.getInterfaceName() ?: return emptyList()

        // 使用缓存避免重复计算
        return classAllPropertiesMap.getOrPut(interfaceName) {
            val visited = mutableSetOf<String>()
            getInterfaceAllPropertiesImpl(interfaceDecl, visited)
        }
    }

    /**
     * 递归获取接口属性的实现，带循环检测
     */
    private fun getInterfaceAllPropertiesImpl(
        interfaceDecl: AstNode,
        visited: MutableSet<String>
    ): List<KotlinDeclaration.PropertyDecl> {
        val interfaceName = interfaceDecl.getInterfaceName() ?: return emptyList()

        // 检测循环继承
        if (visited.contains(interfaceName)) {
            Logger.warn("检测到循环继承: ${visited.joinToString(" -> ")} -> $interfaceName")
            return emptyList()
        }

        visited.add(interfaceName)

        try {
            val props = getInterfaceOwnProperties(interfaceDecl).map { it.clone() }.toMutableList()

            // 递归获取父类型的属性
            ExtendRelationship.findParentsByChild(interfaceName).forEach { parentName ->
                val parentInterface = visitor.findInterface(parentName)
                if (parentInterface != null) {
                    val parentProps = getInterfaceAllPropertiesImpl(parentInterface, visited)
                    mergeKotlinProperties(props, parentProps)
                } else {
                    // 可能是 type alias，使用缓存
                    val cachedProps = classPropertiesMap[parentName]
                    if (cachedProps != null) {
                        val parentProps = cachedProps
                        mergeKotlinProperties(props, parentProps)
                    }
                }
            }

            return props
        } finally {
            visited.remove(interfaceName)
        }
    }

    /**
     * 提取属性
     */
    private fun extractProperty(propSig: AstNode): KotlinDeclaration.PropertyDecl? {
        val propName = propSig.getPropertyName() ?: return null

        if (propName.isEmpty()) return null

        // 使用 ADT 流程
        val typeAnnotation = propSig.getNode("typeAnnotation")?.getNode("typeAnnotation")
        val tsType = TypeResolver.extractTypeScriptType(typeAnnotation).getOrDefault(TypeScriptType.Any)
        var kotlinType = TypeConverter.convert(tsType).getOrDefault(KotlinType.Any)

        // 应用配置重写
        val actualName = GlobalConfig.config.kotlinKeywordMap[propName] ?: propName
        kotlinType = HardcodedRules.getPropertyTypeOverride(actualName) ?: kotlinType

        // 提取默认值
        val defaultValue = extractDefaultValue(tsType)

        return KotlinDeclaration.PropertyDecl(
            name = propName,
            type = kotlinType,
            modifier = PropertyModifier.Var,
            defaultValue = Expression.StringLiteral(defaultValue),
            kdoc = extractComment(propSig)
        )
    }

    /**
     * 从 TypeScript 类型提取默认值
     */
    private fun extractDefaultValue(tsType: TypeScriptType): String {
        return when {
            tsType is TypeScriptType.Literal -> when (val value = tsType.value) {
                is LiteralValue.StringLiteral -> "\"${value.value}\""
                is LiteralValue.NumberLiteral -> value.value.toString()
                is LiteralValue.BooleanLiteral -> value.value.toString()
                else -> ""
            }
            else -> ""
        }
    }

    /**
     * 提取注释（简化版，实际需要从 span 或其他地方获取）
     */
    private fun extractComment(propSig: AstNode): String {
        // SWC AST 中注释提取比较复杂，这里暂时返回空
        // 实际实现需要解析 comments 数组并匹配位置
        return ""
    }

    /**
     * 合并属性列表（去重）- 优化版本使用 HashMap
     */
    private fun mergeKotlinProperties(
        source: MutableList<KotlinDeclaration.PropertyDecl>,
        target: List<KotlinDeclaration.PropertyDecl>
    ) {
        val nameMap = source.associateBy { it.name }.toMutableMap()
        target.forEach { prop ->
            nameMap.putIfAbsent(prop.name, prop.clone())
        }
        source.clear()
        source.addAll(nameMap.values)
    }

    /**
     * 提取继承关系（简单版本，保持向后兼容）
     */
    fun extractExtends(interfaceDecl: AstNode): List<String> {
        return extractGenericExtends(interfaceDecl).map { it.name }
    }

    /**
     * 提取泛型继承关系
     */
    fun extractGenericExtends(interfaceDecl: AstNode): List<GenericParent> {
        val extendsNodes = interfaceDecl.getNodes("extends")
        return extendsNodes.mapNotNull { extend ->
            val expr = extend.getNode("expression")
            when {
                expr?.isIdentifier() == true -> {
                    val name = expr.getIdentifierValue()
                    if (name != null && name.isNotEmpty()) {
                        GenericParent(name)
                    } else {
                        null
                    }
                }
                expr?.type == "TsQualifiedName" -> {
                    val name = expr.getNode("right")?.getIdentifierValue()
                    if (name != null && name.isNotEmpty()) {
                        GenericParent(name)
                    } else {
                        null
                    }
                }
                expr?.type == "TsTypeReference" -> {
                    // 处理泛型类型引用，如 A<T>
                    val name = expr.getNode("typeName")?.getIdentifierValue()
                    if (name != null && name.isNotEmpty()) {
                        val typeArgs = extractTypeArguments(expr)
                        GenericParent(name, typeArgs)
                    } else {
                        null
                    }
                }
                else -> null
            }
        }
    }

    /**
     * 提取类型参数
     */
    private fun extractTypeArguments(typeRef: AstNode): List<KotlinType> {
        val typeParams = typeRef.getNode("typeParameters")?.getNodes("params") ?: emptyList()
        return typeParams.mapNotNull { param ->
            val tsTypeResult = TypeResolver.extractTypeScriptType(param)
            val tsType = when (tsTypeResult) {
                is dev.yidafu.swc.generator.adt.result.GeneratorResult.Success -> tsTypeResult.value
                else -> TypeScriptType.Any
            }
            val kotlinTypeResult = TypeConverter.convert(tsType)
            when (kotlinTypeResult) {
                is dev.yidafu.swc.generator.adt.result.GeneratorResult.Success -> kotlinTypeResult.value
                else -> KotlinType.Any
            }
        }
    }

    /**
     * 提取头部注释
     */
    fun extractHeaderComment(interfaceDecl: AstNode): String {
        // 简化版，实际需要从 AST 中提取
        return ""
    }
}

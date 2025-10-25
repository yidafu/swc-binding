package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.adt.typescript.*
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.util.Logger

/**
 * 声明提取器
 * 纯 TypeScript ADT 提取，不涉及 Kotlin 相关逻辑
 * 从 InterfaceExtractor.kt 中分离出来的声明提取逻辑
 */
class DeclarationExtractor(private val visitor: TsAstVisitor) {

    /**
     * 提取接口的所有成员（包含继承的）
     */
    fun extractInterfaceAllMembers(interfaceDecl: AstNode): List<TypeMember> {
        val interfaceName = interfaceDecl.getInterfaceName() ?: return emptyList()

        Logger.debug("提取接口所有成员: $interfaceName", 6)

        val visited = mutableSetOf<String>()
        return extractInterfaceAllMembersImpl(interfaceDecl, visited)
    }

    /**
     * 提取接口自己的成员（不包含继承的）
     */
    fun extractInterfaceOwnMembers(interfaceDecl: AstNode): List<TypeMember> {
        val interfaceName = interfaceDecl.getInterfaceName() ?: return emptyList()

        Logger.debug("提取接口自有成员: $interfaceName", 6)

        return extractInterfaceOwnMembersImpl(interfaceDecl)
    }

    /**
     * 提取接口自己的成员（不包含继承的）
     */
    private fun extractInterfaceOwnMembersImpl(interfaceDecl: AstNode): List<TypeMember> {
        val body = interfaceDecl.getNode("body") ?: return emptyList()
        val members = body.getNodes("body")

        return members.mapNotNull { member ->
            extractTypeMember(member)
        }
    }

    /**
     * 递归获取接口成员的实现，带循环检测
     */
    private fun extractInterfaceAllMembersImpl(
        interfaceDecl: AstNode,
        visited: MutableSet<String>
    ): List<TypeMember> {
        val interfaceName = interfaceDecl.getInterfaceName() ?: return emptyList()

        // 检测循环继承
        if (visited.contains(interfaceName)) {
            Logger.warn("检测到循环继承: ${visited.joinToString(" -> ")} -> $interfaceName")
            return emptyList()
        }

        visited.add(interfaceName)

        try {
            val ownMembers = extractInterfaceOwnMembersImpl(interfaceDecl).toMutableList()

            // 递归获取父类型的成员
            val extends = extractExtends(interfaceDecl)
            extends.forEach { parentRef ->
                val parentInterface = visitor.findInterface(parentRef.name)
                if (parentInterface != null) {
                    val parentMembers = extractInterfaceAllMembersImpl(parentInterface, visited)
                    mergeTypeMembers(ownMembers, parentMembers)
                }
            }

            return ownMembers
        } finally {
            visited.remove(interfaceName)
        }
    }

    /**
     * 提取类型成员
     */
    private fun extractTypeMember(member: AstNode): TypeMember? {
        val name = member.getPropertyName() ?: return null
        if (name.isEmpty()) return null

        val typeAnnotation = member.getNode("typeAnnotation")?.getNode("typeAnnotation")
        val type = TypeResolver.extractTypeScriptType(typeAnnotation)
            .getOrDefault(TypeScriptType.Any)

        val optional = member.getBoolean("optional") ?: false
        val readonly = member.getBoolean("readonly") ?: false

        return TypeMember(
            name = name,
            type = type,
            optional = optional,
            readonly = readonly
        )
    }

    /**
     * 提取继承关系
     */
    fun extractExtends(interfaceDecl: AstNode): List<TypeReference> {
        val extendsNodes = interfaceDecl.getNodes("extends")
        return extendsNodes.mapNotNull { extend ->
            val expr = extend.getNode("expression")
            when {
                expr?.isIdentifier() == true -> {
                    val name = expr.getIdentifierValue()
                    if (name != null && name.isNotEmpty()) {
                        TypeReference(name)
                    } else null
                }
                expr?.type == "TsQualifiedName" -> {
                    val name = expr.getNode("right")?.getIdentifierValue()
                    if (name != null && name.isNotEmpty()) {
                        TypeReference(name)
                    } else null
                }
                expr?.type == "TsTypeReference" -> {
                    val name = expr.getNode("typeName")?.getIdentifierValue()
                    if (name != null && name.isNotEmpty()) {
                        val typeArgs = extractTypeArguments(expr)
                        TypeReference(name, typeArgs)
                    } else null
                }
                else -> null
            }
        }
    }

    /**
     * 提取类型参数
     */
    private fun extractTypeArguments(typeRef: AstNode): List<TypeScriptType> {
        val typeParams = typeRef.getNode("typeParameters")?.getNodes("params") ?: emptyList()
        return typeParams.mapNotNull { param ->
            TypeResolver.extractTypeScriptType(param).getOrNull()
        }
    }

    /**
     * 合并类型成员列表（去重）
     */
    private fun mergeTypeMembers(
        source: MutableList<TypeMember>,
        target: List<TypeMember>
    ) {
        val nameMap = source.associateBy { it.name }.toMutableMap()
        target.forEach { member ->
            nameMap.putIfAbsent(member.name, member)
        }
        source.clear()
        source.addAll(nameMap.values)
    }

    /**
     * 提取头部注释
     */
    fun extractHeaderComment(interfaceDecl: AstNode): String? {
        // 简化版，实际需要从 AST 中提取
        // SWC AST 中注释提取比较复杂，这里暂时返回空
        return null
    }

    /**
     * 检查接口是否为配置接口
     */
    fun isConfigInterface(interfaceDecl: AstNode): Boolean {
        val name = interfaceDecl.getInterfaceName() ?: return false
        return name.endsWith("Config") || name.endsWith("Options") || name.endsWith("Settings")
    }

    /**
     * 检查接口是否为空接口
     */
    fun isEmptyInterface(interfaceDecl: AstNode): Boolean {
        val members = extractInterfaceOwnMembers(interfaceDecl)
        return members.isEmpty()
    }

    /**
     * 获取接口的继承深度
     */
    fun getInheritanceDepth(interfaceDecl: AstNode): Int {
        val extends = extractExtends(interfaceDecl)
        return if (extends.isEmpty()) 0 else 1
    }

    /**
     * 获取接口引用的所有类型名称
     */
    fun getReferencedTypeNames(interfaceDecl: AstNode): Set<String> {
        val fromExtends = extractExtends(interfaceDecl).map { it.name }.toSet()
        val fromMembers = extractInterfaceAllMembers(interfaceDecl)
            .flatMap { it.type.getReferencedTypeNames() }
            .toSet()
        return fromExtends + fromMembers
    }

}

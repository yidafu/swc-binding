package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.adt.result.*
import dev.yidafu.swc.generator.adt.typescript.*
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.util.Logger

/**
 * TypeScript ADT 提取器
 * 负责将 AstNode 转换为 TypeScript ADT 声明
 */
class TypeScriptADTExtractor(private val visitor: TsAstVisitor) {

    /**
     * 提取声明（通用方法）
     */
    fun extractDeclaration(astNode: AstNode): GeneratorResult<TypeScriptDeclaration> {
        return when (astNode.type) {
            "TsInterfaceDeclaration" -> extractInterface(astNode)
            "TsTypeAliasDeclaration" -> extractTypeAlias(astNode)
            else -> GeneratorResultFactory.failure(
                code = ErrorCode.PARSE_ERROR,
                message = "Unsupported declaration type: ${astNode.type}"
            )
        }
    }

    /**
     * 提取接口声明
     */
    fun extractInterface(astNode: AstNode): GeneratorResult<TypeScriptDeclaration.InterfaceDeclaration> {
        return try {
            val name = astNode.getNode("id")?.getString("value")
                ?: return GeneratorResultFactory.failure(
                    code = ErrorCode.PARSE_ERROR,
                    message = "Interface name not found"
                )

            Logger.debug("提取接口: $name", 4)

            // 提取类型参数
            val typeParameters = extractTypeParameters(astNode)

            // 提取继承关系
            val extends = extractExtends(astNode)

            // 提取成员
            val members = extractInterfaceMembers(astNode)

            // 提取文档注释
            val kdoc = extractKdoc(astNode)

            val interfaceDecl = TypeScriptDeclaration.InterfaceDeclaration(
                name = name,
                typeParameters = typeParameters,
                extends = extends,
                members = members,
                kdoc = kdoc
            )

            Logger.debug("  类型参数: ${typeParameters.size}, 继承: ${extends.size}, 成员: ${members.size}", 6)

            GeneratorResultFactory.success(interfaceDecl)
        } catch (e: Exception) {
            Logger.error("提取接口失败: ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.PARSE_ERROR,
                message = "Failed to extract interface: ${e.message}",
                cause = e
            )
        }
    }

    /**
     * 提取类型别名声明
     */
    fun extractTypeAlias(astNode: AstNode): GeneratorResult<TypeScriptDeclaration.TypeAliasDeclaration> {
        return try {
            val name = astNode.getNode("id")?.getString("value")
                ?: return GeneratorResultFactory.failure(
                    code = ErrorCode.PARSE_ERROR,
                    message = "Type alias name not found"
                )

            Logger.debug("提取类型别名: $name", 4)

            // 提取类型参数
            val typeParameters = extractTypeParameters(astNode)

            // 提取类型定义
            val typeAnnotation = astNode.getNode("typeAnnotation")?.getNode("typeAnnotation")
            val type = TypeResolver.extractTypeScriptType(typeAnnotation)
                .getOrDefault(TypeScriptType.Any)

            // 提取文档注释
            val kdoc = extractKdoc(astNode)

            val typeAliasDecl = TypeScriptDeclaration.TypeAliasDeclaration(
                name = name,
                typeParameters = typeParameters,
                type = type,
                kdoc = kdoc
            )

            Logger.debug("  类型参数: ${typeParameters.size}, 类型: ${type.getTypeName()}", 6)

            GeneratorResultFactory.success(typeAliasDecl)
        } catch (e: Exception) {
            Logger.error("提取类型别名失败: ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.PARSE_ERROR,
                message = "Failed to extract type alias: ${e.message}",
                cause = e
            )
        }
    }

    /**
     * 提取类型参数
     */
    private fun extractTypeParameters(astNode: AstNode): List<TypeParameter> {
        val typeParams = astNode.getNode("typeParameters")?.getNodes("params") ?: emptyList()
        return typeParams.mapNotNull { param ->
            val name = param.getString("name") ?: return@mapNotNull null
            val constraint = param.getNode("constraint")?.let { constraintNode ->
                TypeResolver.extractTypeScriptType(constraintNode).getOrNull()
            }
            val default = param.getNode("default")?.let { defaultNode ->
                TypeResolver.extractTypeScriptType(defaultNode).getOrNull()
            }
            TypeParameter(
                name = name,
                constraint = constraint,
                default = default
            )
        }
    }

    /**
     * 提取继承关系
     */
    private fun extractExtends(astNode: AstNode): List<TypeReference> {
        val extendsNodes = astNode.getNodes("extends")
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
     * 提取接口成员
     */
    private fun extractInterfaceMembers(astNode: AstNode): List<TypeMember> {
        val body = astNode.getNode("body")
        val members = body?.getNodes("body") ?: emptyList()
        return members.mapNotNull { member ->
            if (member.isPropertySignature()) {
                extractTypeMember(member)
            } else {
                null
            }
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
     * 提取文档注释
     */
    private fun extractKdoc(astNode: AstNode): String? {
        // 简化版，实际需要从 AST 中提取注释
        // SWC AST 中注释提取比较复杂，这里暂时返回空
        return null
    }

    /**
     * 批量提取所有声明
     */
    fun extractAllDeclarations(): GeneratorResult<List<TypeScriptDeclaration>> {
        return try {
            val interfaces = visitor.getInterfaces()
            val typeAliases = visitor.getTypeAliases()

            Logger.info("开始提取 TypeScript ADT: ${interfaces.size} 个接口, ${typeAliases.size} 个类型别名")

            val declarations = mutableListOf<TypeScriptDeclaration>()

            // 提取接口
            interfaces.forEach { interfaceNode ->
                extractInterface(interfaceNode).onSuccess { decl ->
                    declarations.add(decl)
                }.onFailure { error ->
                    Logger.warn("跳过接口提取失败: ${error.message}")
                }
            }

            // 提取类型别名
            typeAliases.forEach { typeAliasNode ->
                extractTypeAlias(typeAliasNode).onSuccess { decl ->
                    declarations.add(decl)
                }.onFailure { error ->
                    Logger.warn("跳过类型别名提取失败: ${error.message}")
                }
            }

            Logger.info("TypeScript ADT 提取完成: ${declarations.size} 个声明")

            GeneratorResultFactory.success(declarations)
        } catch (e: Exception) {
            Logger.error("批量提取声明失败: ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.PARSE_ERROR,
                message = "Failed to extract all declarations: ${e.message}",
                cause = e
            )
        }
    }
}

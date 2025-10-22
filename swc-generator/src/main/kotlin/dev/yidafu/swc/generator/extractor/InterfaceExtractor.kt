package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.core.model.KotlinProperty
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.config.Constants
import dev.yidafu.swc.generator.parser.*
import dev.yidafu.swc.generator.util.*

/**
 * Interface 提取器
 * 
 * 使用 AstNode 提取 interface 的属性和关系
 */
class InterfaceExtractor(private val visitor: TsAstVisitor) {
    private val classPropertiesMap = mutableMapOf<String, List<KotlinProperty>>()
    private val classAllPropertiesMap = mutableMapOf<String, List<KotlinProperty>>()
    
    /**
     * 提取 interface 的属性（仅自身属性，不含继承）
     */
    fun getInterfaceOwnProperties(interfaceDecl: AstNode): List<KotlinProperty> {
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
    fun getInterfaceAllProperties(interfaceDecl: AstNode): List<KotlinProperty> {
        val interfaceName = interfaceDecl.getInterfaceName() ?: return emptyList()
        
        // 使用缓存避免重复计算
        return classAllPropertiesMap.getOrPut(interfaceName) {
            val props = getInterfaceOwnProperties(interfaceDecl).map { it.clone() }.toMutableList()
        
            // 递归获取父类型的属性
            fun recursiveGetProps(typeName: String) {
                ExtendRelationship.findParentsByChild(typeName).forEach { parentName ->
                    val parentInterface = visitor.findInterface(parentName)
                    if (parentInterface != null) {
                        val parentProps = getInterfaceOwnProperties(parentInterface).map { prop ->
                            val newProp = prop.clone()
                            newProp.isOverride = true
                            newProp
                        }
                        mergeKotlinProperties(props, parentProps)
                        recursiveGetProps(parentName)
                    } else {
                        // 可能是 type alias
                        val cachedProps = classPropertiesMap[parentName]
                        if (cachedProps != null) {
                            val parentProps = cachedProps.map { prop ->
                                val newProp = prop.clone()
                                newProp.isOverride = true
                                newProp
                            }
                            mergeKotlinProperties(props, parentProps)
                        }
                    }
                }
            }
            
            recursiveGetProps(interfaceName)
            props
        }
    }
    
    /**
     * 提取属性
     */
    private fun extractProperty(propSig: AstNode): KotlinProperty? {
        val propName = propSig.getPropertyName() ?: return null
        
        if (propName.isEmpty()) return null
        
        // 获取类型注解
        val typeAnnotation = propSig.getNode("typeAnnotation")?.getNode("typeAnnotation")
        var kotlinType = TypeResolver.resolveType(typeAnnotation)
        
        // 移除注释并应用类型重写规则
        kotlinType = kotlinType.removeComment()
        val actualName = Constants.kotlinKeywordMap[propName] ?: propName
        kotlinType = Constants.propTypeRewrite[actualName] ?: kotlinType
        
        // 提取默认值（字符串字面量）
        val defaultValue = if (typeAnnotation?.isLiteralType() == true) {
            val literal = typeAnnotation.getNode("literal")
            if (literal?.isStringLiteral() == true) {
                literal.getStringLiteralValue() ?: ""
            } else {
                ""
            }
        } else {
            ""
        }
        
        return KotlinProperty(
            name = propName,
            type = kotlinType,
            comment = extractComment(propSig),
            defaultValue = defaultValue
        )
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
     * 合并属性列表（去重）
     */
    private fun mergeKotlinProperties(
        source: MutableList<KotlinProperty>,
        target: List<KotlinProperty>
    ) {
        val nameSet = source.map { it.name }.toSet()
        target.forEach { prop ->
            if (!nameSet.contains(prop.name)) {
                source.add(prop.clone())
            }
        }
    }
    
    /**
     * 提取继承关系
     */
    fun extractExtends(interfaceDecl: AstNode): List<String> {
        val extendsNodes = interfaceDecl.getNodes("extends")
        return extendsNodes
            .mapNotNull { extend ->
                val expr = extend.getNode("expression")
                when {
                    expr?.isIdentifier() == true -> expr.getIdentifierValue()
                    expr?.type == "TsQualifiedName" -> {
                        expr.getNode("right")?.getIdentifierValue()
                    }
                    else -> null
                }
            }
            .filter { it.isNotEmpty() }
    }
    
    /**
     * 提取头部注释
     */
    fun extractHeaderComment(interfaceDecl: AstNode): String {
        // 简化版，实际需要从 AST 中提取
        return ""
    }
}

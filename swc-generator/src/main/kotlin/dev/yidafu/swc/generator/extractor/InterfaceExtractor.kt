package dev.yidafu.swc.generator.extractor

import dev.yidafu.swc.generator.adt.typescript.*
import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.converter.TypeConverter
import dev.yidafu.swc.generator.core.model.KotlinProperty
import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import dev.yidafu.swc.generator.config.ConfigLoader
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
        
        // 使用 ADT 流程
        val typeAnnotation = propSig.getNode("typeAnnotation")?.getNode("typeAnnotation")
        val tsType = TypeResolver.extractTypeScriptType(typeAnnotation).getOrDefault(TypeScriptType.Any)
        var kotlinType = TypeConverter.convert(tsType).getOrDefault(KotlinType.Any)
        
        // 应用配置重写
        val config = ConfigLoader.loadConfig()
        val actualName = config.kotlinKeywordMap[propName] ?: propName
        kotlinType = config.propTypeRewrite[actualName]?.let {
            KotlinType.Simple(it)
        } ?: kotlinType
        
        // 提取默认值
        val defaultValue = extractDefaultValue(tsType)
        
        return KotlinProperty(
            name = propName,
            kotlinType = kotlinType,
            comment = extractComment(propSig),
            defaultValue = defaultValue
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

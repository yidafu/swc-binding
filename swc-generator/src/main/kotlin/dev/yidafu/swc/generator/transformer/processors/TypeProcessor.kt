package dev.yidafu.swc.generator.transformer.processors

import dev.yidafu.swc.generator.adt.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.parser.AstNode

/**
 * 类型处理器接口
 */
interface TypeProcessor {
    /**
     * 判断是否可以处理该类型别名
     */
    fun canProcess(typeAlias: AstNode): Boolean

    /**
     * 处理类型别名（返回 ADT 声明）
     */
    fun process(typeAlias: AstNode, context: TransformContext): KotlinDeclaration?
}

/**
 * 转换上下文
 */
data class TransformContext(
    private val classDeclMap: MutableMap<String, KotlinDeclaration.ClassDecl>,
    private val propertiesMap: MutableMap<String, List<KotlinDeclaration.PropertyDecl>> = mutableMapOf(),
    private val typeAliasList: MutableList<KotlinDeclaration.TypeAliasDecl> = mutableListOf()
) {
    fun addClassDecl(classDecl: KotlinDeclaration.ClassDecl) {
        classDeclMap[classDecl.name] = classDecl
    }

    fun addProperties(className: String, properties: List<KotlinDeclaration.PropertyDecl>) {
        propertiesMap[className] = properties
    }
    
    fun addTypeAlias(typeAlias: KotlinDeclaration.TypeAliasDecl) {
        typeAliasList.add(typeAlias)
    }

    fun getAllPropertiesMap(): Map<String, List<KotlinDeclaration.PropertyDecl>> = propertiesMap

    fun getClassDecl(name: String): KotlinDeclaration.ClassDecl? = classDeclMap[name]

    fun getAllClassDecls(): List<KotlinDeclaration.ClassDecl> = classDeclMap.values.toList()
    
    fun getAllTypeAliases(): List<KotlinDeclaration.TypeAliasDecl> = typeAliasList.toList()
    
    fun updateClassDecl(classDecl: KotlinDeclaration.ClassDecl) {
        classDeclMap[classDecl.name] = classDecl
    }
    
    fun updateTypeAlias(typeAlias: KotlinDeclaration.TypeAliasDecl) {
        val index = typeAliasList.indexOfFirst { it.name == typeAlias.name }
        if (index >= 0) {
            typeAliasList[index] = typeAlias
        } else {
            typeAliasList.add(typeAlias)
        }
    }
}

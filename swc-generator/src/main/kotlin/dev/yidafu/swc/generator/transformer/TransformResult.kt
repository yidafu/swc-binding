package dev.yidafu.swc.generator.transformer

import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration

/**
 * 转换结果
 */
data class TransformResult(
    val classDecls: List<KotlinDeclaration.ClassDecl>,
    val classAllPropertiesMap: Map<String, List<KotlinDeclaration.PropertyDecl>>,
    val typeAliases: List<KotlinDeclaration.TypeAliasDecl> = emptyList()
)

package dev.yidafu.swc.generator.config

import com.squareup.kotlinpoet.ClassName

/**
 * 集合类型配置
 * PoetExtensions 使用的基础集合类型映射
 */
object CollectionsConfig {
    val MAP = ClassName("kotlin", "Map")
    val LIST = ClassName("kotlin", "List")
    val SET = ClassName("kotlin", "Set")
    val MUTABLE_MAP = ClassName("kotlin.collections", "MutableMap")
    val MUTABLE_LIST = ClassName("kotlin.collections", "MutableList")
    val MUTABLE_SET = ClassName("kotlin.collections", "MutableSet")
}

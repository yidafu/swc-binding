package dev.yidafu.swc.generator.adt.kotlin

/**
 * Kotlin 标识符相关常量
 */
object KotlinIdentifierConstants {
    /**
     * Kotlin 保留字集合
     */
    val RESERVED_WORDS = setOf(
        "class", "interface", "object", "fun", "val", "var",
        "if", "else", "when", "for", "while", "do",
        "try", "catch", "finally", "throw", "return"
    )
}

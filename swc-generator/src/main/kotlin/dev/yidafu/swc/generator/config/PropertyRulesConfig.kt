package dev.yidafu.swc.generator.config

/**
 * 属性规则配置
 * 管理属性相关的规则和常量
 */
object PropertyRulesConfig {
    /**
     * 特殊属性名称
     */
    enum class SpecialProperty(val value: String) {
        TYPE("type"),
        SYNTAX("syntax"),
        SPAN("span")
    }

    /**
     * Span 坐标属性名称
     */
    enum class SpanCoordinate(val value: String) {
        START("start"),
        END("end"),
        CTXT("ctxt")
    }

    /**
     * Kotlin 保留字
     */
    enum class ReservedWord(val value: String) {
        OBJECT("object"),
        INLINE("inline"),
        IN("in"),
        SUPER("super"),
        CLASS("class"),
        INTERFACE("interface"),
        FUN("fun"),
        VAL("val"),
        VAR("var"),
        WHEN("when"),
        IS("is"),
        AS("as"),
        IMPORT("import"),
        PACKAGE("package")
    }

    // 向后兼容的常量
    val TYPE: String = SpecialProperty.TYPE.value
    val SYNTAX: String = SpecialProperty.SYNTAX.value
    val SPAN: String = SpecialProperty.SPAN.value

    val SPAN_COORDINATES: Set<String> = SpanCoordinate.values().map { it.value }.toSet()

    private val RESERVED_WORDS: Set<String> = ReservedWord.values().map { it.value.lowercase() }.toSet()

    /**
     * 检查是否为 type 属性
     */
    fun isTypeProperty(name: String): Boolean = name.removeSurrounding("`") == TYPE

    /**
     * 检查是否为 syntax 属性
     */
    fun isSyntaxProperty(name: String): Boolean = name.removeSurrounding("`") == SYNTAX

    /**
     * 检查是否为 span 属性
     */
    fun isSpanProperty(name: String): Boolean = name.removeSurrounding("`") == SPAN

    /**
     * 检查是否为 span 坐标属性
     */
    fun isSpanCoordinate(name: String): Boolean = name.removeSurrounding("`") in SPAN_COORDINATES

    /**
     * 检查是否为 span 坐标属性（针对特定接口）
     */
    fun isSpanCoordinateProperty(interfaceName: String, propertyName: String): Boolean {
        return interfaceName == "Span" && isSpanCoordinate(propertyName)
    }

    /**
     * 包装保留字
     */
    fun wrapReservedWord(name: String): String {
        return if (RESERVED_WORDS.contains(name.lowercase())) {
            "`$name`"
        } else {
            name
        }
    }
}

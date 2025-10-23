package dev.yidafu.swc.generator.config

/**
 * 硬编码规则配置
 *
 * 包含固定的类型映射、过滤规则等，这些规则不需要通过配置文件修改
 */
object HardcodedRules {

    /**
     * JS到Kotlin基础类型映射
     */
    val jsToKotlinTypeMap = mapOf(
        "number" to "Int",
        "boolean" to "Boolean",
        "string" to "String",
        "string[]" to "Array<String>"
    )

    /**
     * 属性类型覆盖映射（字符串形式，用于向后兼容）
     */
    private val propertyTypeOverrides = mapOf(
        "global_defs" to "Map<String, String>",
        "top_retain" to "BooleanableString",
        "sequences" to "Boolean",
        "pure_getters" to "BooleanableString",
        "toplevel" to "BooleanableString",
        "targets" to "Map<String, String>"
    )

    /**
     * 重要接口列表
     */
    val importantInterfaces = listOf(
        "Options", "Config", "CallerOptions", "Plugin",
        "MatchPattern", "TerserCompressOptions", "TerserMangleOptions",
        "JsMinifyOptions", "JsFormatOptions", "EnvConfig", "JscConfig", "ModuleConfig",
        "TsParserConfig", "EsParserConfig", "ParserConfig"
    )

    /**
     * 跳过的类模式
     */
    val skipClassPatterns = listOf(
        "TsTemplateLiteralType.*",
        "TemplateLiteral.*"
    )

    /**
     * 跳过的DSL接收者
     */
    val skipDslReceivers = listOf(
        "HasSpan",
        "HasDecorator"
    )

    /**
     * 检查是否应该跳过某个类
     */
    fun shouldSkipClass(className: String): Boolean {
        return skipClassPatterns.any { pattern ->
            className.matches(pattern.replace("*", ".*").toRegex())
        }
    }

    /**
     * 检查是否应该跳过某个DSL接收者
     */
    fun shouldSkipDslReceiver(receiverName: String): Boolean {
        return skipDslReceivers.contains(receiverName)
    }

    /**
     * 检查类型是否应该默认为 nullable
     * 所有属性都默认为可空
     */
    fun shouldBeNullable(typeName: String): Boolean {
        return true
    }

    /**
     * 检查是否为重要接口
     */
    fun isImportantInterface(interfaceName: String): Boolean {
        return importantInterfaces.contains(interfaceName)
    }

    /**
     * 获取属性类型覆盖
     * 返回正确的 KotlinType 对象，支持泛型类型
     */
    fun getPropertyTypeOverride(propertyName: String): dev.yidafu.swc.generator.adt.kotlin.KotlinType? {
        return when (propertyName) {
            "global_defs", "targets" -> dev.yidafu.swc.generator.adt.kotlin.KotlinType.Generic(
                "Map",
                listOf(
                    dev.yidafu.swc.generator.adt.kotlin.KotlinType.StringType,
                    dev.yidafu.swc.generator.adt.kotlin.KotlinType.StringType
                )
            )
            "top_retain", "pure_getters", "toplevel" -> dev.yidafu.swc.generator.adt.kotlin.KotlinType.Booleanable(
                dev.yidafu.swc.generator.adt.kotlin.KotlinType.StringType
            )
            "sequences" -> dev.yidafu.swc.generator.adt.kotlin.KotlinType.Boolean
            else -> null
        }
    }
}

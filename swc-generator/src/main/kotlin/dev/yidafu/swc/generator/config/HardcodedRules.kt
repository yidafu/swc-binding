package dev.yidafu.swc.generator.config

/**
 * 硬编码规则配置
 *
 * 包含固定的过滤规则和重要接口列表，这些规则不需要通过配置文件修改
 * * 注意：基础类型转换现在完全在 ADT 转换过程中自动处理
 */
object HardcodedRules {

    // 注意：基础类型映射已移至 ADT 转换过程中自动处理

    /**
     * 跳过的DSL接收者
     */
    val skipDslReceivers = listOf(
        "HasSpan",
        "HasDecorator"
    )

    // 注意：skipClassPatterns 已移除
    // 所有类型现在通过 ADT 转换过程自动处理

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
            "sequences" -> dev.yidafu.swc.generator.adt.kotlin.KotlinType.Boolean
            "toplevel", "pureGetters", "topRetain" -> dev.yidafu.swc.generator.adt.kotlin.KotlinType.Nullable(dev.yidafu.swc.generator.adt.kotlin.KotlinType.StringType)
            else -> null
        }
    }
}

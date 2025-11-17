package dev.yidafu.swc.generator.config

import com.squareup.kotlinpoet.ClassName

/**
 * 集中管理生成器中的硬编码常量与映射
 * 
 * 此文件作为兼容层，委托给新的配置类
 * 新的配置类提供了更好的类型安全和代码组织
 * 
 * @deprecated 建议直接使用新的配置类（AnnotationConfig, PropertyRulesConfig 等）
 */
@Deprecated("建议直接使用新的配置类", ReplaceWith("AnnotationConfig", "dev.yidafu.swc.generator.config.AnnotationConfig"))
object Hardcoded {
    /**
     * 注解名称配置
     * @deprecated 使用 AnnotationConfig
     */
    @Deprecated("使用 AnnotationConfig", ReplaceWith("AnnotationConfig"))
    object AnnotationNames {
        val SERIAL_NAME = AnnotationConfig.SERIAL_NAME
        val SERIALIZABLE = AnnotationConfig.SERIALIZABLE
        val JSON_CLASS_DISCRIMINATOR = AnnotationConfig.JSON_CLASS_DISCRIMINATOR
        val TRANSIENT = AnnotationConfig.TRANSIENT
        val ENCODE_DEFAULT = AnnotationConfig.ENCODE_DEFAULT
        val OPT_IN = AnnotationConfig.OPT_IN
        val CONTEXTUAL = AnnotationConfig.CONTEXTUAL

        fun toClassName(name: String): ClassName = AnnotationConfig.toClassName(name)
    }

    /**
     * 接口启发式配置
     * @deprecated 使用 InterfaceHeuristicsConfig
     */
    @Deprecated("使用 InterfaceHeuristicsConfig", ReplaceWith("InterfaceHeuristicsConfig"))
    object InterfaceHeuristics {
        val interfaceSuffixes: Set<String> = InterfaceHeuristicsConfig.interfaceSuffixes
    }

    /**
     * 集合类型配置
     * @deprecated 使用 CollectionsConfig
     */
    @Deprecated("使用 CollectionsConfig", ReplaceWith("CollectionsConfig"))
    object Collections {
        val MAP = CollectionsConfig.MAP
        val LIST = CollectionsConfig.LIST
        val SET = CollectionsConfig.SET
        val MUTABLE_MAP = CollectionsConfig.MUTABLE_MAP
        val MUTABLE_LIST = CollectionsConfig.MUTABLE_LIST
        val MUTABLE_SET = CollectionsConfig.MUTABLE_SET
    }

    /**
     * 序列化器配置
     * @deprecated 使用 SerializerConfig
     */
    @Deprecated("使用 SerializerConfig", ReplaceWith("SerializerConfig"))
    object Serializer {
        const val DEFAULT_DISCRIMINATOR: String = "type"
        const val SYNTAX_DISCRIMINATOR: String = "syntax"

        @JvmStatic
        val additionalOpenBases: MutableSet<String> = SerializerConfig.additionalOpenBases

        @JvmStatic
        var missingSerializablePolicy: SerializerConfig.MissingSerializablePolicy
            get() = SerializerConfig.missingSerializablePolicy
            set(value) { SerializerConfig.missingSerializablePolicy = value }

        val configInterfaceNames: Set<String> = SerializerConfig.configInterfaceNames
    }

    /**
     * Union 相关配置
     * @deprecated 使用 UnionConfig
     */
    @Deprecated("使用 UnionConfig", ReplaceWith("UnionConfig"))
    object Union {
        @JvmStatic
        var includeNullabilityInToken: Boolean
            get() = UnionConfig.includeNullabilityInToken
            set(value) { UnionConfig.includeNullabilityInToken = value }

        @JvmStatic
        var includeNullabilityInKey: Boolean
            get() = UnionConfig.includeNullabilityInKey
            set(value) { UnionConfig.includeNullabilityInKey = value }

        @JvmStatic
        val hotFixedCombos: MutableSet<String> = UnionConfig.hotFixedCombos

        @JvmStatic
        var factoryArity: Set<Int>
            get() = UnionConfig.factoryArity
            set(value) { UnionConfig.factoryArity = value }
    }

    /**
     * 属性规则配置
     * @deprecated 使用 PropertyRulesConfig
     */
    @Deprecated("使用 PropertyRulesConfig", ReplaceWith("PropertyRulesConfig"))
    object PropertyRules {
        const val TYPE: String = "type"
        const val SYNTAX: String = "syntax"
        const val SPAN: String = "span"
        val SPAN_COORDINATES: Set<String> = PropertyRulesConfig.SPAN_COORDINATES

        fun isTypeProperty(name: String): Boolean = PropertyRulesConfig.isTypeProperty(name)
        fun isSyntaxProperty(name: String): Boolean = PropertyRulesConfig.isSyntaxProperty(name)
        fun isSpanProperty(name: String): Boolean = PropertyRulesConfig.isSpanProperty(name)
        fun isSpanCoordinate(name: String): Boolean = PropertyRulesConfig.isSpanCoordinate(name)
        fun isSpanCoordinateProperty(interfaceName: String, propertyName: String): Boolean =
            PropertyRulesConfig.isSpanCoordinateProperty(interfaceName, propertyName)
        fun wrapReservedWord(name: String): String = PropertyRulesConfig.wrapReservedWord(name)
    }

    /**
     * Ctxt 字段配置
     * @deprecated 使用 CtxtFieldsConfig
     */
    @Deprecated("使用 CtxtFieldsConfig", ReplaceWith("CtxtFieldsConfig"))
    object CtxtFields {
        val CLASSES_WITH_CTXT: Set<String> = CtxtFieldsConfig.CLASSES_WITH_CTXT
    }

    /**
     * 类型别名规则配置
     * @deprecated 使用 TypeAliasRulesConfig
     */
    @Deprecated("使用 TypeAliasRulesConfig", ReplaceWith("TypeAliasRulesConfig"))
    object TypeAliasRules {
        val FORCE_STRING_ALIASES: Set<String> = TypeAliasRulesConfig.FORCE_STRING_ALIASES

        fun isForceStringAlias(aliasName: String): Boolean =
            TypeAliasRulesConfig.isForceStringAlias(aliasName)

        fun forceNullableForInterface(interfaceName: String): Boolean =
            TypeAliasRulesConfig.forceNullableForInterface(interfaceName)
    }

    /**
     * 转换器规则配置
     * @deprecated 使用 ConverterRulesConfig
     */
    @Deprecated("使用 ConverterRulesConfig", ReplaceWith("ConverterRulesConfig"))
    object ConverterRules {
        val SKIPPED_TYPE_ALIASES: Set<String> = ConverterRulesConfig.SKIPPED_TYPE_ALIASES

        fun shouldSkipTypeAlias(name: String): Boolean =
            ConverterRulesConfig.shouldSkipTypeAlias(name)
    }

    /**
     * 接口规则配置
     * @deprecated 使用 InterfaceRulesConfig
     */
    @Deprecated("使用 InterfaceRulesConfig", ReplaceWith("InterfaceRulesConfig"))
    object InterfaceRules {
        val SKIPPED_INTERFACES: Map<String, String> = InterfaceRulesConfig.SKIPPED_INTERFACES
        val TYPE_REFERENCE_REPLACEMENTS: Map<String, String> = InterfaceRulesConfig.TYPE_REFERENCE_REPLACEMENTS

        fun shouldSkipInterface(interfaceName: String): Boolean =
            InterfaceRulesConfig.shouldSkipInterface(interfaceName)

        fun getSkipReason(interfaceName: String): String? =
            InterfaceRulesConfig.getSkipReason(interfaceName)

        fun replaceTypeReference(typeName: String): String =
            InterfaceRulesConfig.replaceTypeReference(typeName)

        fun getRootDiscriminator(mappedInterfaceName: String): String? =
            InterfaceRulesConfig.getRootDiscriminator(mappedInterfaceName)
    }
}

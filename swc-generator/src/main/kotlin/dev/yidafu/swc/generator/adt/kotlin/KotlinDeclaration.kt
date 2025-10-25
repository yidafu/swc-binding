package dev.yidafu.swc.generator.adt.kotlin

/**
 * Kotlin 声明 ADT（代数数据类型）
 * * 表示 Kotlin 代码中的各种声明结构
 */
sealed class KotlinDeclaration {

    /**
     * 类型参数
     */
    data class TypeParameter(
        val name: String,
        val variance: Variance = Variance.INVARIANT,
        val constraint: KotlinType? = null,
        val default: KotlinType? = null
    )

    /**
     * 类型参数变体
     */
    enum class Variance {
        INVARIANT, COVARIANT, CONTRAVARIANT
    }

    /**
     * 类声明
     */
    data class ClassDecl(
        val name: String,
        val modifier: ClassModifier,
        val properties: List<PropertyDecl>,
        val parents: List<KotlinType>,
        val typeParameters: List<TypeParameter> = emptyList(),
        val functions: List<FunctionDecl> = emptyList(),
        val nestedClasses: List<ClassDecl> = emptyList(),
        val enumEntries: List<EnumEntry> = emptyList(),
        val annotations: List<Annotation>,
        val kdoc: String? = null
    ) : KotlinDeclaration()

    /**
     * 枚举条目
     */
    data class EnumEntry(
        val name: String,
        val arguments: List<Expression> = emptyList()
    ) : KotlinDeclaration()

    /**
     * 属性声明
     */
    data class PropertyDecl(
        val name: String,
        val type: KotlinType,
        val modifier: PropertyModifier,
        val defaultValue: Expression? = null,
        val annotations: List<Annotation> = emptyList(),
        val kdoc: String? = null
    ) : KotlinDeclaration()

    /**
     * 函数声明
     */
    data class FunctionDecl(
        val name: String,
        val receiver: KotlinType? = null,
        val parameters: List<ParameterDecl>,
        val returnType: KotlinType,
        val modifier: FunctionModifier,
        val annotations: List<Annotation> = emptyList(),
        val kdoc: String? = null
    ) : KotlinDeclaration()

    /**
     * 类型别名声明
     */
    data class TypeAliasDecl(
        val name: String,
        val type: KotlinType,
        val typeParameters: List<TypeParameter> = emptyList(),
        val annotations: List<Annotation> = emptyList(),
        val kdoc: String? = null
    ) : KotlinDeclaration()

    /**
     * 参数声明
     */
    data class ParameterDecl(
        val name: String,
        val type: KotlinType,
        val defaultValue: Expression? = null,
        val annotations: List<Annotation> = emptyList()
    )

    /**
     * 注解
     */
    data class Annotation(
        val name: String,
        val arguments: List<Expression> = emptyList()
    )
}

/**
 * 类型参数变型
 */
enum class Variance {
    INVARIANT, // 不变
    COVARIANT, // 协变 (out)
    CONTRAVARIANT // 逆变 (in)
}

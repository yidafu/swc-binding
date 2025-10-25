package dev.yidafu.swc.generator.adt.converter

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.typescript.*
import dev.yidafu.swc.generator.util.Logger

/**
 * 专门处理类型参数转换的转换器
 * 从 TypeScriptDeclarationConverter 中分离出来的专门职责
 */
object TypeParameterConverter {
    
    /**
     * 转换 TypeScript 类型参数为 Kotlin 类型参数
     */
    fun convertTypeParameters(tsTypeParams: List<TypeParameter>): List<KotlinDeclaration.TypeParameter> {
        return tsTypeParams.map { tsParam ->
            val constraint = tsParam.constraint?.let { typeConverter.convertToKotlinType(it).getOrNull() }
            val default = tsParam.default?.let { typeConverter.convertToKotlinType(it).getOrNull() }

            KotlinDeclaration.TypeParameter(
                name = tsParam.name,
                variance = convertVariance(tsParam.variance),
                constraint = constraint,
                default = default
            )
        }
    }

    /**
     * 转换方差修饰符
     */
    private fun convertVariance(variance: dev.yidafu.swc.generator.adt.typescript.Variance): KotlinDeclaration.Variance {
        return when (variance) {
            dev.yidafu.swc.generator.adt.typescript.Variance.INVARIANT -> KotlinDeclaration.Variance.INVARIANT
            dev.yidafu.swc.generator.adt.typescript.Variance.COVARIANT -> KotlinDeclaration.Variance.COVARIANT
            dev.yidafu.swc.generator.adt.typescript.Variance.CONTRAVARIANT -> KotlinDeclaration.Variance.CONTRAVARIANT
        }
    }

    // 注意：这里需要访问 TypeConverter，但为了避免循环依赖，
    // 我们通过参数传递的方式来解决
    private lateinit var typeConverter: TypeConverter

    /**
     * 设置类型转换器（由外部调用者设置）
     */
    fun setTypeConverter(converter: TypeConverter) {
        this.typeConverter = converter
    }
}

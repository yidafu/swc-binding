package dev.yidafu.swc.generator.converter

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.config.ConverterRulesConfig
import dev.yidafu.swc.generator.converter.declaration.InterfaceConverter
import dev.yidafu.swc.generator.converter.declaration.TypeAliasConverter
import dev.yidafu.swc.generator.converter.type.TypeConverter
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.model.typescript.TypeScriptType
import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.util.Logger

/**
 * TypeScript 到 Kotlin 的主转换器
 *
 * 协调各个子转换器完成转换工作，包括：
 * - [InterfaceConverter] - 接口声明转换
 * - [TypeAliasConverter] - 类型别名转换
 * - [TypeConverter] - 类型转换
 *
 * 此转换器负责：
 * - 管理转换器的生命周期
 * - 构建联合类型父类注册表
 * - 处理转换错误和跳过规则
 *
 * @param config 生成器配置
 * @param inheritanceAnalyzer 继承关系分析器，如果为 null 则基于当前声明列表动态创建
 */
class TypeScriptToKotlinConverter(
    private val config: Configuration,
    private val inheritanceAnalyzer: InheritanceAnalyzer? = null
) {

    private val typeConverter = TypeConverter(config)

    /**
     * 转换 TypeScript 声明列表为 Kotlin 声明列表
     *
     * 批量转换多个声明，自动处理：
     * - 继承关系分析
     * - 联合类型父类注册
     * - 嵌套类型注册
     * - 跳过规则应用
     *
     * @param tsDeclarations TypeScript 声明列表
     * @return 转换结果，成功时包含 Kotlin 声明列表，失败时包含错误信息
     */
    fun convertDeclarations(
        tsDeclarations: List<TypeScriptDeclaration>
    ): GeneratorResult<List<KotlinDeclaration>> {
        return try {
            Logger.debug("开始转换 ${tsDeclarations.size} 个 TypeScript 声明")

            val kotlinDeclarations = mutableListOf<KotlinDeclaration>()
            val activeAnalyzer = inheritanceAnalyzer ?: InheritanceAnalyzer(tsDeclarations)
            val unionParentRegistry = buildUnionParentRegistry(tsDeclarations)
            val nestedTypeRegistry = mutableMapOf<String, String>()
            val interfaceConverter = InterfaceConverter(config, activeAnalyzer, unionParentRegistry, nestedTypeRegistry)
            val typeAliasConverter = TypeAliasConverter(config, activeAnalyzer, unionParentRegistry, nestedTypeRegistry)

            for (tsDeclaration in tsDeclarations) {
                if (shouldSkipDeclaration(tsDeclaration)) {
                    Logger.debug("跳过类型别名: ${(tsDeclaration as TypeScriptDeclaration.TypeAliasDeclaration).name}", 6)
                    continue
                }
                val result = convertDeclaration(tsDeclaration, interfaceConverter, typeAliasConverter)
                result.onSuccess { kotlinDeclaration ->
                    val name = when (tsDeclaration) {
                        is TypeScriptDeclaration.InterfaceDeclaration -> tsDeclaration.name
                        is TypeScriptDeclaration.TypeAliasDeclaration -> tsDeclaration.name
                    }
                    if (name == "ForOfStatement" || name == "ComputedPropName") {
                        Logger.debug("成功转换: $name -> ${kotlinDeclaration::class.simpleName}", 6)
                    }
                    kotlinDeclarations.add(kotlinDeclaration)
                    kotlinDeclarations.addAll(typeAliasConverter.drainExtraDeclarations())
                }.onFailure { error ->
                    val name = when (tsDeclaration) {
                        is TypeScriptDeclaration.InterfaceDeclaration -> tsDeclaration.name
                        is TypeScriptDeclaration.TypeAliasDeclaration -> tsDeclaration.name
                    }
                    if (name == "ForOfStatement" || name == "ComputedPropName") {
                        Logger.warn("转换声明失败: $name, ${error.message}")
                    } else {
                        Logger.warn("转换声明失败: ${tsDeclaration::class.simpleName}, ${error.message}")
                    }
                    // 继续处理其他声明，不中断整个流程
                }
            }

            Logger.debug("转换完成，生成了 ${kotlinDeclarations.size} 个 Kotlin 声明")
            GeneratorResultFactory.success(kotlinDeclarations)
        } catch (e: Exception) {
            Logger.error("转换声明列表失败: ${e.message}")
            GeneratorResultFactory.failure(
                code = ErrorCode.UNKNOWN,
                message = "Failed to convert declarations: ${e.message}",
                cause = e
            )
        }
    }

    /**
     * 转换单个 TypeScript 声明
     *
     * 为单个声明创建独立的转换上下文，包括：
     * - 基于单个声明的继承关系分析器
     * - 联合类型父类注册表
     * - 嵌套类型注册表
     *
     * @param tsDeclaration 要转换的 TypeScript 声明
     * @return 转换结果，成功时包含 Kotlin 声明，失败时包含错误信息
     */
    fun convertDeclaration(
        tsDeclaration: TypeScriptDeclaration
    ): GeneratorResult<KotlinDeclaration> {
        if (shouldSkipDeclaration(tsDeclaration)) {
            val aliasName = (tsDeclaration as TypeScriptDeclaration.TypeAliasDeclaration).name
            Logger.debug("单独转换时跳过类型别名: $aliasName", 6)
            return GeneratorResultFactory.failure(
                code = ErrorCode.UNSUPPORTED_TYPE,
                message = "Type alias $aliasName is intentionally skipped"
            )
        }
        val activeAnalyzer = inheritanceAnalyzer ?: InheritanceAnalyzer(listOf(tsDeclaration))
        val unionParentRegistry = buildUnionParentRegistry(listOf(tsDeclaration))
        val nestedTypeRegistry = mutableMapOf<String, String>()
        val interfaceConverter = InterfaceConverter(config, activeAnalyzer, unionParentRegistry, nestedTypeRegistry)
        val typeAliasConverter = TypeAliasConverter(config, activeAnalyzer, unionParentRegistry, nestedTypeRegistry)
        return convertDeclaration(tsDeclaration, interfaceConverter, typeAliasConverter)
    }

    private fun convertDeclaration(
        tsDeclaration: TypeScriptDeclaration,
        interfaceConverter: InterfaceConverter,
        typeAliasConverter: TypeAliasConverter
    ): GeneratorResult<KotlinDeclaration> {
        return when (tsDeclaration) {
            is TypeScriptDeclaration.InterfaceDeclaration -> interfaceConverter.convert(tsDeclaration)
            is TypeScriptDeclaration.TypeAliasDeclaration -> typeAliasConverter.convert(tsDeclaration)
        }
    }

    /**
     * 转换 TypeScript 类型为 Kotlin 类型
     *
     * 委托给 [TypeConverter] 进行实际转换。
     *
     * @param tsType 要转换的 TypeScript 类型
     * @return 转换结果，成功时包含 Kotlin 类型，失败时包含错误信息
     */
    fun convertType(tsType: TypeScriptType): GeneratorResult<KotlinType> {
        return typeConverter.convert(tsType)
    }

    private fun shouldSkipDeclaration(declaration: TypeScriptDeclaration): Boolean {
        return declaration is TypeScriptDeclaration.TypeAliasDeclaration &&
            ConverterRulesConfig.shouldSkipTypeAlias(declaration.name)
    }

    /**
     * 构建联合类型父类注册表
     *
     * 扫描所有类型别名声明，找出联合类型（如 `A | B | C`），
     * 并记录每个成员类型对应的联合类型别名。
     *
     * 例如：如果存在 `type Expr = A | B`，则注册表中会记录：
     * - `A` -> `Expr`
     * - `B` -> `Expr`
     *
     * @param tsDeclarations TypeScript 声明列表
     * @return 联合类型父类注册表，键为成员类型名，值为包含该类型的联合类型别名集合
     */
    private fun buildUnionParentRegistry(
        tsDeclarations: List<TypeScriptDeclaration>
    ): MutableMap<String, MutableSet<String>> {
        val registry = mutableMapOf<String, MutableSet<String>>()
        tsDeclarations.filterIsInstance<TypeScriptDeclaration.TypeAliasDeclaration>().forEach { alias ->
            val union = alias.type as? TypeScriptType.Union ?: return@forEach
            val references = union.types.filterIsInstance<TypeScriptType.Reference>()
            if (references.size == union.types.size) {
                references.forEach { ref ->
                    registry.getOrPut(ref.name) { mutableSetOf() }.add(alias.name)
                }
            }
        }
        return registry
    }
}

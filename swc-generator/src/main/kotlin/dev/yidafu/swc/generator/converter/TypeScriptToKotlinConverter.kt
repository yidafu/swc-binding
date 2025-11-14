package dev.yidafu.swc.generator.converter

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.Configuration
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
 * 协调各个子转换器完成转换工作
 */
class TypeScriptToKotlinConverter(
    private val config: Configuration,
    private val inheritanceAnalyzer: InheritanceAnalyzer? = null
) {
    
    private val typeConverter = TypeConverter(config)
    private val skippedTypeAliases = setOf("ToSnakeCase", "ToSnakeCaseProperties")

    /**
     * 转换 TypeScript 声明列表为 Kotlin 声明列表
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
                    kotlinDeclarations.add(kotlinDeclaration)
                }.onFailure { error ->
                    Logger.warn("转换声明失败: ${tsDeclaration::class.simpleName}, ${error.message}")
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
     * 转换 TypeScript 类型
     */
    fun convertType(tsType: TypeScriptType): GeneratorResult<KotlinType> {
        return typeConverter.convert(tsType)
    }

    private fun shouldSkipDeclaration(declaration: TypeScriptDeclaration): Boolean {
        return declaration is TypeScriptDeclaration.TypeAliasDeclaration &&
            skippedTypeAliases.contains(declaration.name)
    }

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

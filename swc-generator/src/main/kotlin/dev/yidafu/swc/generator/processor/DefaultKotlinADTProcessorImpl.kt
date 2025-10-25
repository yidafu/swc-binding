package dev.yidafu.swc.generator.processor

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.result.ErrorCode
import dev.yidafu.swc.generator.adt.result.GeneratorResult
import dev.yidafu.swc.generator.adt.result.GeneratorResultFactory
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.util.Logger

/**
 * 默认的 Kotlin ADT 处理器实现
 * * 处理特殊的业务逻辑，如：
 * - 属性类型覆盖
 * - 特殊修饰符设置
 * - 注解添加
 * - 命名规则应用
 */
class DefaultKotlinADTProcessorImpl : KotlinADTProcessor {

    override fun processDeclarations(
        declarations: List<KotlinDeclaration>,
        config: SwcGeneratorConfig
    ): GeneratorResult<List<KotlinDeclaration>> {
        return try {
            Logger.debug("开始处理 Kotlin ADT 声明，总数: ${declarations.size}", 4)

            val processedDeclarations = declarations.mapNotNull { declaration ->
                processDeclaration(declaration, config).getOrNull()
            }

            Logger.debug("Kotlin ADT 处理完成，处理后数量: ${processedDeclarations.size}", 4)
            GeneratorResultFactory.success(processedDeclarations)
        } catch (e: Exception) {
            Logger.error("处理 Kotlin ADT 声明失败: ${e.message}")
            GeneratorResultFactory.failure(ErrorCode.CODE_GENERATION_ERROR, "处理 Kotlin ADT 声明失败", e)
        }
    }

    override fun processDeclaration(
        declaration: KotlinDeclaration,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration> {
        return try {
            when (declaration) {
                is KotlinDeclaration.ClassDecl -> processClassDeclaration(declaration, config)
                is KotlinDeclaration.PropertyDecl -> processPropertyDeclaration(declaration, config)
                is KotlinDeclaration.FunctionDecl -> processFunctionDeclaration(declaration, config)
                is KotlinDeclaration.TypeAliasDecl -> processTypeAliasDeclaration(declaration, config)
                is KotlinDeclaration.EnumEntry -> GeneratorResultFactory.success(declaration)
            }
        } catch (e: Exception) {
            Logger.warn("处理声明失败: ${getDeclarationName(declaration)}, ${e.message}")
            GeneratorResultFactory.success(declaration) // 失败时返回原始声明
        }
    }

    /**
     * 处理类声明
     */
    private fun processClassDeclaration(
        declaration: KotlinDeclaration.ClassDecl,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration.ClassDecl> {
        // 处理属性
        val processedProperties = declaration.properties.mapNotNull { property ->
            processPropertyDeclaration(property, config).getOrNull()
        }

        // 应用特殊规则
        val processedDeclaration = declaration.copy(
            properties = processedProperties
            // 可以在这里添加其他特殊处理逻辑
            // 例如：特殊修饰符、注解等
        )

        return GeneratorResultFactory.success(processedDeclaration)
    }

    /**
     * 处理属性声明
     */
    private fun processPropertyDeclaration(
        declaration: KotlinDeclaration.PropertyDecl,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration.PropertyDecl> {
        // 应用属性类型覆盖规则
        val processedType = applyPropertyTypeOverrides(declaration.type, declaration.name)

        // 应用命名规则
        val processedName = applyNamingRules(declaration.name)

        // 应用注解规则
        val processedAnnotations = applyAnnotationRules(declaration.annotations, declaration.name)

        val processedDeclaration = declaration.copy(
            name = processedName,
            type = processedType,
            annotations = processedAnnotations
        )

        return GeneratorResultFactory.success(processedDeclaration)
    }

    /**
     * 处理函数声明
     */
    private fun processFunctionDeclaration(
        declaration: KotlinDeclaration.FunctionDecl,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration.FunctionDecl> {
        // 应用命名规则
        val processedName = applyNamingRules(declaration.name)

        val processedDeclaration = declaration.copy(
            name = processedName
        )

        return GeneratorResultFactory.success(processedDeclaration)
    }

    /**
     * 处理类型别名声明
     */
    private fun processTypeAliasDeclaration(
        declaration: KotlinDeclaration.TypeAliasDecl,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration.TypeAliasDecl> {
        // 应用命名规则
        val processedName = applyNamingRules(declaration.name)

        val processedDeclaration = declaration.copy(
            name = processedName
        )

        return GeneratorResultFactory.success(processedDeclaration)
    }

    /**
     * 应用属性类型覆盖规则
     */
    private fun applyPropertyTypeOverrides(type: KotlinType, propertyName: String): KotlinType {
        val overrideType = CodeGenerationRules.getPropertyTypeOverride(propertyName)
        return overrideType ?: type
    }

    /**
     * 应用命名规则
     */
    private fun applyNamingRules(name: String): String {
        // 应用 snake_case 到 camelCase 转换
        return CodeGenerationRules.snakeToCamelCase(name)
    }

    /**
     * 应用注解规则
     */
    private fun applyAnnotationRules(
        annotations: List<KotlinDeclaration.Annotation>,
        propertyName: String
    ): List<KotlinDeclaration.Annotation> {
        val newAnnotations = mutableListOf<KotlinDeclaration.Annotation>()
        newAnnotations.addAll(annotations)

        // 添加 @SerialName 注解（如果需要）
        val originalName = CodeGenerationRules.snakeToCamelCase(propertyName)
        if (originalName != propertyName) {
            val serialNameAnnotation = KotlinDeclaration.Annotation(
                name = "SerialName",
                arguments = listOf(Expression.StringLiteral(propertyName))
            )
            newAnnotations.add(serialNameAnnotation)
        }

        return newAnnotations
    }

    /**
     * 获取声明名称
     */
    private fun getDeclarationName(declaration: KotlinDeclaration): String {
        return when (declaration) {
            is KotlinDeclaration.ClassDecl -> declaration.name
            is KotlinDeclaration.PropertyDecl -> declaration.name
            is KotlinDeclaration.FunctionDecl -> declaration.name
            is KotlinDeclaration.TypeAliasDecl -> declaration.name
            is KotlinDeclaration.EnumEntry -> declaration.name
        }
    }
}

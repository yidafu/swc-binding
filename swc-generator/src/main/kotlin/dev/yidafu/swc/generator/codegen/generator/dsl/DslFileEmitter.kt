package dev.yidafu.swc.generator.codegen.generator.dsl

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName
import dev.yidafu.swc.generator.codegen.generator.PoetGenerator
import dev.yidafu.swc.generator.codegen.model.KotlinExtensionFun
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFile
import dev.yidafu.swc.generator.codegen.poet.PoetConstants
import dev.yidafu.swc.generator.codegen.poet.createDslLambdaType
import dev.yidafu.swc.generator.codegen.poet.createExtensionFun
import dev.yidafu.swc.generator.codegen.poet.createFileBuilder
import dev.yidafu.swc.generator.config.SerializerConfig
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.isInterface
import dev.yidafu.swc.generator.util.CollectionUtils
import dev.yidafu.swc.generator.util.Logger
import java.nio.file.Path

/**
 * DSL 文件生成器
 *
 * 负责生成 DSL 扩展函数文件，包括：
 * - 接收者类型的 DSL 文件（如 `Node.kt`、`Expression.kt` 等）
 * - `create.kt` 文件（包含所有创建函数）
 *
 * @param modelContext DSL 模型上下文，包含类信息和继承关系
 * @param poet KotlinPoet 生成器，用于生成代码
 */
class DslFileEmitter(
    private val modelContext: DslModelContext,
    private val poet: PoetGenerator
) {
    /**
     * 生成所有 DSL 文件
     *
     * 为每个接收者类型生成一个 DSL 文件，并生成一个 create.kt 文件。
     *
     * @param collection DSL 扩展函数集合，包含按接收者类型分组的扩展函数
     * @param outputDir 输出目录
     * @return 生成的文件列表
     */
    fun emit(collection: DslExtensionCollection, outputDir: Path): List<GeneratedFile> {
        val files = mutableListOf<GeneratedFile>()
        collection.groups.forEach { (receiver, funList) ->
            files += emitReceiverDslFile(outputDir, receiver, funList)
        }
        files += emitCreateFile(outputDir, collection.nodeCreatableClasses)
        return files
    }

    /**
     * 生成接收者类型的 DSL 文件
     *
     * 为指定的接收者类型生成一个包含所有扩展函数的文件。
     *
     * @param outputDir 输出目录
     * @param receiver 接收者类型名称（如 "Node"、"Expression"）
     * @param funList 该接收者类型的所有扩展函数列表
     * @return 生成的文件
     */
    private fun emitReceiverDslFile(
        outputDir: Path,
        receiver: String,
        funList: List<KotlinExtensionFun>
    ): GeneratedFile {
        Logger.debug("生成 DSL 文件: $receiver.kt (${funList.size} 个函数)", 6)
        val fileBuilder = createFileBuilder(
            PoetConstants.PKG_DSL,
            receiver,
            PoetConstants.PKG_TYPES to "*"
        )

        funList.forEach { extFun ->
            runCatching {
                createDslExtensionFun(extFun, receiver)
            }.onSuccess { funSpec ->
                fileBuilder.addFunction(funSpec)
            }.onFailure { e ->
                Logger.warn("生成失败: ${extFun.funName}, 跳过 (${e.message})")
            }
        }
        val fileSpec = poet.buildFile(fileBuilder)
        return GeneratedFile(outputDir.resolve("$receiver.kt"), fileSpec = fileSpec)
    }

    /**
     * 生成 create.kt 文件
     *
     * 生成包含所有创建函数的文件。创建函数用于创建可实例化的 AST 节点。
     *
     * 生成规则：
     * - 为 `nodeCreatableClasses` 中的每个类生成 `create{ClassName}` 函数
     * - 为 `interfaceToImplMap` 中的接口生成 `create{InterfaceName}` 函数
     * - 如果类名以 `Impl` 结尾，使用接口名作为函数名和返回类型
     *
     * @param outputDir 输出目录
     * @param nodeCreatableClasses 可创建的节点类列表
     * @return 生成的 create.kt 文件
     */
    private fun emitCreateFile(
        outputDir: Path,
        nodeCreatableClasses: List<KotlinDeclaration.ClassDecl>
    ): GeneratedFile {
        Logger.debug("生成 create.kt", 6)
        val fileBuilder = createFileBuilder(
            PoetConstants.PKG_DSL,
            "create",
            PoetConstants.PKG_TYPES to "*"
        )

        // 为 nodeCreatableClasses 中的类生成 create 函数
        nodeCreatableClasses.forEach { klass ->
            val funSpec = createCreateFunction(klass)
            fileBuilder.addFunction(funSpec)
        }

        // 为 interfaceToImplMap 中的接口生成 create 函数（即使实现类不在 nodeCreatableClasses 中）
        val interfaceToImplMap = dev.yidafu.swc.generator.config.SerializerConfig.interfaceToImplMap
        val processedInterfaces = CollectionUtils.newStringSet()

        interfaceToImplMap.forEach { (interfaceName, implClassName) ->
            // 检查是否已经为这个接口生成了 create 函数
            val alreadyProcessed = nodeCreatableClasses.any { klass ->
                val className = klass.name.removeSurrounding("`")
                className == implClassName || className == interfaceName
            }

            if (!alreadyProcessed && !processedInterfaces.contains(interfaceName)) {
                // 检查接口是否存在且继承自 Node
                val interfaceDecl = modelContext.classInfoByName[interfaceName]
                if (interfaceDecl != null && modelContext.inheritsNode(interfaceName)) {
                    // 创建一个临时的类声明用于生成 create 函数
                    val tempImplClass = KotlinDeclaration.ClassDecl(
                        name = implClassName,
                        modifier = dev.yidafu.swc.generator.model.kotlin.ClassModifier.FinalClass,
                        properties = emptyList(),
                        parents = listOf(dev.yidafu.swc.generator.model.kotlin.KotlinType.Simple(interfaceName)),
                        typeParameters = emptyList(),
                        nestedClasses = emptyList(),
                        annotations = emptyList(),
                        kdoc = null
                    )
                    val funSpec = createCreateFunction(tempImplClass)
                    fileBuilder.addFunction(funSpec)
                    processedInterfaces.add(interfaceName)
                    Logger.debug("  为 interfaceToImplMap 中的接口生成 create 函数: $interfaceName -> $implClassName", 6)
                }
            }
        }

        val fileSpec = poet.buildFile(fileBuilder)
        val totalFunctions = nodeCreatableClasses.size + processedInterfaces.size
        Logger.verbose("  生成了 $totalFunctions 个 create 函数 (${nodeCreatableClasses.size} 个来自 nodeCreatableClasses, ${processedInterfaces.size} 个来自 interfaceToImplMap)", 6)
        return GeneratedFile(outputDir.resolve("create.kt"), fileSpec = fileSpec)
    }

    private fun createDslExtensionFun(extFun: KotlinExtensionFun, receiver: String): FunSpec {
        return createDslExtensionFunLegacy(extFun, receiver)
    }

    private fun createDslExtensionFunLegacy(extFun: KotlinExtensionFun, receiver: String): FunSpec {
        val funName = DslNamingRules.toFunName(extFun.funName.replace("Impl", ""))
        val returnTypeName = DslNamingRules.sanitizeTypeName(DslNamingRules.removeGenerics(extFun.funName.replace("Impl", "")))
        val receiverTypeName = DslNamingRules.sanitizeTypeName(DslNamingRules.removeGenerics(receiver))
        val implTypeName = resolveImplClassName(extFun.funName)
            ?: throw IllegalStateException("No concrete implementation for ${extFun.funName}")

        return createExtensionFun(
            funName = funName,
            receiverType = ClassName(PoetConstants.PKG_TYPES, receiverTypeName),
            returnType = ClassName(PoetConstants.PKG_TYPES, returnTypeName),
            implType = ClassName(PoetConstants.PKG_TYPES, implTypeName),
            kdoc = extFun.comments.takeIf { it.isNotEmpty() }
        )
    }

    /**
     * 解析实现类名称
     *
     * 根据类型名称查找对应的具体实现类名称。
     *
     * 查找顺序：
     * 1. 检查 `interfaceToImplMap` 配置
     * 2. 如果类型本身是具体类，直接返回
     * 3. 如果是接口，查找其具体类后代，优先选择 `${name}Impl` 命名的类
     *
     * @param typeName 类型名称
     * @return 实现类名称，如果找不到则返回 null
     */
    private fun resolveImplClassName(typeName: String): String? {
        val normalized = DslNamingRules.sanitizeTypeName(DslNamingRules.removeGenerics(typeName)).removeSurrounding("`")

        // 首先检查 customType.kt 中定义的接口到实现类的映射
        SerializerConfig.interfaceToImplMap[normalized]?.let { implName ->
            return implName
        }

        val decl = modelContext.classInfoByName[normalized]
        return when {
            // 若本身是具体类，直接返回
            decl != null && !decl.modifier.isInterface() -> decl.name
            // 若是接口名，则查找其具体类后代；优先选择 `${name}Impl` 命名的具体类
            decl != null && decl.modifier.isInterface() -> {
                val descendants = modelContext.hierarchy.findDescendants(normalized)
                val concrete = descendants.firstOrNull { name ->
                    modelContext.classInfoByName[name]?.modifier?.isInterface() == false
                }
                val preferred = descendants.firstOrNull { it == "${normalized}Impl" && modelContext.classInfoByName[it]?.modifier?.isInterface() == false }
                preferred ?: concrete
            }
            else -> null
        }
    }

    /**
     * 创建 create 函数
     *
     * 为指定的类生成 `create{ClassName}` 函数。
     * 函数接受一个 lambda 参数，用于配置类的属性。
     *
     * @param klass 要生成 create 函数的类
     * @return 生成的函数规范
     */
    private fun createCreateFunction(klass: KotlinDeclaration.ClassDecl): FunSpec {
        val className = klass.name.removeSurrounding("`")

        // 如果类名以 Impl 结尾，使用接口名作为函数名和返回类型
        val (functionName, returnTypeName, implTypeName) = if (className.endsWith("Impl")) {
            val interfaceName = className.removeSuffix("Impl")
            // 检查接口是否存在
            val interfaceDecl = modelContext.classInfoByName[interfaceName]
            if (interfaceDecl != null && interfaceDecl.modifier.isInterface()) {
                Triple(interfaceName, interfaceName, className)
            } else {
                Triple(className, className, className)
            }
        } else {
            // 对于非 Impl 类，检查是否有对应的接口
            val implType = resolveImplClassName(className)
            if (implType != null && implType != className) {
                // 有对应的实现类，使用接口名
                Triple(className, className, implType)
            } else {
                Triple(className, className, className)
            }
        }

        val returnType = ClassName(PoetConstants.PKG_TYPES, returnTypeName)
        val implType = ClassName(PoetConstants.PKG_TYPES, implTypeName)

        return FunSpec.builder("create$functionName")
            .addParameter("block", createDslLambdaType(returnType as TypeName))
            .returns(returnType)
            .addStatement("return %T().apply(block)", implType)
            .build()
    }
}

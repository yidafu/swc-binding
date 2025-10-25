package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.kotlin.isValidKotlinTypeName
import dev.yidafu.swc.generator.adt.kotlin.wrapReservedWord
import dev.yidafu.swc.generator.codegen.poet.*
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.config.HardcodedRules
import dev.yidafu.swc.generator.codegen.model.KotlinExtensionFun
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzerHolder
import dev.yidafu.swc.generator.util.Logger
import java.io.File

/**
 * DSL 代码生成器
 */
class DslGenerator(
    private val classDecls: List<KotlinDeclaration.ClassDecl>,
    private val classAllPropertiesMap: Map<String, List<KotlinDeclaration.PropertyDecl>>
) {
    private val extFunMap = mutableMapOf<String, KotlinExtensionFun>()
    private val generatedClassNameList = classDecls.map { it.name }

    /**
     * 添加扩展函数
     */
    private fun addExtensionFun(extFun: KotlinExtensionFun) {
        if (extFun.receiver != extFun.funName) {
            if (HardcodedRules.shouldSkipDslReceiver(extFun.receiver)) return
            if (extFun.receiver == extFun.funName.replace("Impl", "")) return

            // 验证类型名称
            if (!isValidKotlinTypeName(extFun.funName) || !isValidKotlinTypeName(extFun.receiver)) {
                Logger.debug("跳过无效类型名称: ${extFun.receiver}.${extFun.funName}")
                return
            }

            extFunMap["${extFun.receiver} - ${extFun.funName.replace("Impl", "")}"] = extFun
        }
    }

    /**
     * 生成 DSL 扩展函数
     * 优化：批量处理，减少重复计算
     */
    fun generateExtensionFunctions() {
        val needGenerateDslClassList = classDecls
            .filter { 
                val analyzer = InheritanceAnalyzerHolder.get()
                analyzer.findAllChildrenByParent(it.name).isNotEmpty() 
            }
            .map { it.name }

        // 批量创建扩展函数列表
        needGenerateDslClassList.forEach { key ->
            createExtensionFunList(key)
        }

        // 批量处理属性相关的扩展函数
        val propertyExtensionFuns = generatePropertyExtensionFunctions(needGenerateDslClassList)
        propertyExtensionFuns.forEach { extFun ->
            addExtensionFunWrapper(extFun)
        }
    }
    
    /**
     * 生成属性相关的扩展函数
     */
    private fun generatePropertyExtensionFunctions(needGenerateDslClassList: List<String>): List<KotlinExtensionFun> {
        return needGenerateDslClassList
            .mapNotNull { klass ->
                val props = classAllPropertiesMap[klass]
                if (props != null) klass to props else null
            }
            .flatMap { (klass, props) ->
                props.flatMap { prop ->
                    generatePropertyExtensionFunctions(klass, prop)
                }
            }
    }
    
    /**
     * 为单个属性生成扩展函数
     */
    private fun generatePropertyExtensionFunctions(klass: String, prop: KotlinDeclaration.PropertyDecl): List<KotlinExtensionFun> {
        val kdoc = """
            /**
              * $klass#${prop.name}: ${prop.getTypeString()}
              * extension function for create ${prop.getTypeString()} -> {child}
              */
        """.trimIndent()
        
        val analyzer = InheritanceAnalyzerHolder.get()
        return when (val type = prop.type) {
            is KotlinType.Union -> {
                type.types.flatMap { unionType ->
                    analyzer.findAllGrandChildren(unionType.toTypeString()).map { child ->
                        KotlinExtensionFun(klass, child, kdoc.replace("{child}", child))
                    }
                }
            }
            else -> {
                analyzer.findAllGrandChildren(type.toTypeString()).map { child ->
                    KotlinExtensionFun(klass, child, kdoc.replace("{child}", child))
                }
            }
        }
    }

    /**
     * 创建扩展函数列表
     */
    private fun createExtensionFunList(key: String) {
        val analyzer = InheritanceAnalyzerHolder.get()
        analyzer.findAllGrandChildren(key).forEach { child ->
            addExtensionFunWrapper(
                KotlinExtensionFun(
                    key,
                    child,
                    """
                /**
                  * subtype of $key
                  */
                    """.trimIndent()
                )
            )
        }
    }

    /**
     * 添加扩展函数（带包装逻辑）
     */
    private fun addExtensionFunWrapper(extFun: KotlinExtensionFun) {
        if (HardcodedRules.shouldSkipDslReceiver(extFun.receiver)) return
        if (extFun.receiver == extFun.funName.replace("Impl", "")) return

        // 过滤掉包含非法字符的类型名称
        if (!isValidKotlinTypeName(extFun.funName) || !isValidKotlinTypeName(extFun.receiver)) {
            Logger.debug("跳过无效类型名称: ${extFun.receiver}.${extFun.funName}")
            return
        }

        if (extFun.funName.endsWith("Impl")) {
            if (generatedClassNameList.contains(extFun.funName)) {
                addExtensionFun(extFun)
            } else {
                addExtensionFun(KotlinExtensionFun(extFun.receiver, extFun.funName.replace("Impl", "")))
            }
        } else {
            // 如果是 interface 或 ref union type，添加
            addExtensionFun(extFun)
        }
    }

    /**
     * 创建 DSL 头部
     */
    private fun createKotlinDslHeader(): List<String> {
        return listOf(
            "package dev.yidafu.swc.dsl",
            "",
            "import dev.yidafu.swc.generated.*",
            ""
        )
    }

    /**
     * 写入 DSL 文件
     */
    fun writeToDirectory(outputDir: String) {
        val dir = File(outputDir)

        // 清空并创建目录
        if (dir.exists()) {
            dir.deleteRecursively()
        }
        dir.mkdirs()

        // 按 receiver 分组并生成文件
        val groups = extFunMap.values.groupBy { it.receiver }
        groups.forEach { (receiver, funList) ->
            writeReceiverDslFile(outputDir, receiver, funList)
        }

        // 生成 create.kt
        generateCreateFunctions(outputDir)

        Logger.success("Generated DSL files in: $outputDir (${groups.size + 1} 个文件)")
    }

    /**
     * 写入单个 receiver 的 DSL 文件（使用 KotlinPoet）
     */
    private fun writeReceiverDslFile(outputDir: String, receiver: String, funList: List<KotlinExtensionFun>) {
        Logger.debug("生成 DSL 文件: $receiver.kt (${funList.size} 个函数)", 6)

        val fileBuilder = createFileBuilder(
            PoetConstants.PKG_DSL,
            receiver,
            PoetConstants.PKG_TYPES to "*"
        )

        funList.forEach { extFun ->
            runCatching {
                createDslExtensionFun(extFun, receiver)
            }.onSuccess { funSpec: FunSpec ->
                fileBuilder.addFunction(funSpec)
            }.onFailure { e ->
                Logger.warn("生成失败: ${extFun.funName}, 跳过 (${e.message})")
                // KotlinPoet 不支持在普通文件中添加原始代码，跳过失败的函数
            }
        }

        // 手动写入文件，避免 KotlinPoet 创建包目录结构
        val fileSpec = fileBuilder.build()
        val fileName = "$receiver.kt"
        val outputFile = File(outputDir, fileName)
        outputFile.writeText(fileSpec.toString())
    }

    /**
     * 创建 DSL 扩展函数（使用 ADT）
     */
    private fun createDslExtensionFun(extFun: KotlinExtensionFun, receiver: String): FunSpec {
        val funName = toFunName(extFun.funName.replace("Impl", ""))
        val returnTypeName = sanitizeTypeName(removeGenerics(extFun.funName.replace("Impl", "")))
        val receiverTypeName = sanitizeTypeName(removeGenerics(receiver))

        // 使用 ADT 创建函数声明
        val functionDecl = KotlinDeclaration.FunctionDecl(
            name = funName,
            receiver = KotlinTypeFactory.simple(receiverTypeName),
            parameters = listOf(
                KotlinDeclaration.ParameterDecl(
                    name = "block",
                    type = KotlinTypeFactory.receiverFunction(
                        receiver = KotlinTypeFactory.simple(receiverTypeName),
                        returnType = KotlinTypeFactory.unit()
                    )
                )
            ),
            returnType = KotlinTypeFactory.simple(returnTypeName),
            modifier = FunctionModifier.Fun,
            kdoc = extFun.comments.takeIf { it.isNotEmpty() }
        )

        return try {
            KotlinPoetConverter.convertFunctionDeclaration(functionDecl)
        } catch (e: Exception) {
            Logger.warn("使用 KotlinPoetConverter 转换函数失败，回退到手动构建: ${e.message}")
            createDslExtensionFunLegacy(extFun, receiver)
        }
    }

    /**
     * 创建 DSL 扩展函数（传统方式，作为回退）
     */
    private fun createDslExtensionFunLegacy(extFun: KotlinExtensionFun, receiver: String): FunSpec {
        val funName = toFunName(extFun.funName.replace("Impl", ""))
        val returnTypeName = sanitizeTypeName(removeGenerics(extFun.funName.replace("Impl", "")))
        val receiverTypeName = sanitizeTypeName(removeGenerics(receiver))

        return createExtensionFun(
            funName = funName,
            receiverType = ClassName(PoetConstants.PKG_TYPES, receiverTypeName),
            returnType = ClassName(PoetConstants.PKG_TYPES, returnTypeName),
            implType = ClassName(PoetConstants.PKG_TYPES, sanitizeTypeName(removeGenerics(extFun.funName))),
            kdoc = extFun.comments.takeIf { it.isNotEmpty() }
        )
    }

    /**
     * 清理类型名称，确保它是有效的 Kotlin 类型名称（增强版）
     * 使用缓存优化重复清理
     */
    private val sanitizedTypeNameCache = mutableMapOf<String, String>()
    
    private fun sanitizeTypeName(typeName: String): String {
        return sanitizedTypeNameCache.getOrPut(typeName) {
            sanitizeTypeNameInternal(typeName)
        }
    }
    
    /**
     * 内部清理类型名称实现
     */
    private fun sanitizeTypeNameInternal(typeName: String): String {
        var cleaned = typeName.trim()

        // 移除注释
        cleaned = cleaned.replace(Regex("""/\*.*?\*/"""), "").trim()

        // 移除任何残留的泛型参数（处理不完整的泛型）
        cleaned = cleaned.replace(Regex("<[^>]*>?"), "")

        // 移除空格、换行等空白字符
        cleaned = cleaned.replace(Regex("\\s+"), "")

        // 移除任何非法字符
        cleaned = cleaned.replace(Regex("[^a-zA-Z0-9_]"), "")

        // 确保首字母大写
        if (cleaned.isNotEmpty() && !cleaned.first().isUpperCase()) {
            cleaned = cleaned.replaceFirstChar { it.uppercase() }
        }

        // 如果清理后为空或无效，返回默认值
        if (cleaned.isEmpty() || !cleaned.matches(Regex("[A-Z][a-zA-Z0-9_]*"))) {
            Logger.warn("类型名称清理失败: $typeName -> $cleaned，使用 'InvalidType'")
            return "InvalidType"
        }

        // 处理保留字，用反引号包裹
        return wrapReservedWord(cleaned)
    }

    /**
     * 移除泛型参数
     */
    private fun removeGenerics(str: String): String {
        return str.replace(Regex("<[^>]*>"), "")
    }

    /**
     * 转换函数名（首字母小写，避免关键字，移除泛型参数）
     */
    private fun toFunName(str: String): String {
        val withoutGenerics = removeGenerics(str)
        val name = withoutGenerics.replaceFirstChar { it.lowercase() }
        return CodeGenerationRules.getKotlinKeywordMap()[name] ?: name
    }

    /**
     * 生成 create 函数（使用 KotlinPoet）
     */
    private fun generateCreateFunctions(outputDir: String) {
        Logger.debug("生成 create.kt", 6)

        val implClasses = classDecls.filter { it.name.endsWith("Impl") }
        val fileBuilder = createFileBuilder(
            PoetConstants.PKG_DSL,
            "create",
            PoetConstants.PKG_TYPES to "*"
        )

        implClasses.forEach { klass ->
            val funSpec: FunSpec = createCreateFunction(klass)
            fileBuilder.addFunction(funSpec)
        }

        // 手动写入文件，避免 KotlinPoet 创建包目录结构
        val fileSpec = fileBuilder.build()
        val fileName = "create.kt"
        val outputFile = File(outputDir, fileName)
        outputFile.writeText(fileSpec.toString())
        Logger.verbose("  生成了 ${implClasses.size} 个 create 函数", 6)
    }

    /**
     * 创建单个 create 函数（顶层函数）
     */
    private fun createCreateFunction(klass: KotlinDeclaration.ClassDecl): FunSpec {
        val interfaceName = removeGenerics(klass.name.replace("Impl", ""))
        val klassName = removeGenerics(klass.name)
        val interfaceType = ClassName(PoetConstants.PKG_TYPES, interfaceName)
        val implType = ClassName(PoetConstants.PKG_TYPES, klassName)

        return FunSpec.builder("create$interfaceName")
            .addParameter("block", createDslLambdaType(interfaceType as TypeName))
            .returns(interfaceType)
            .addStatement("return %T().apply(block)", implType)
            .build()
    }
}

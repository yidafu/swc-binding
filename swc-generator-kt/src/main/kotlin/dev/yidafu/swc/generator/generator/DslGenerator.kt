package dev.yidafu.swc.generator.generator

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.model.KotlinClass
import dev.yidafu.swc.generator.model.KotlinExtensionFun
import dev.yidafu.swc.generator.poet.*
import dev.yidafu.swc.generator.relation.ExtendRelationship
import dev.yidafu.swc.generator.transform.Constants
import dev.yidafu.swc.generator.util.Logger
import java.io.File

/**
 * DSL 代码生成器
 */
class DslGenerator(
    private val kotlinClasses: List<KotlinClass>,
    private val classAllPropertiesMap: Map<String, List<dev.yidafu.swc.generator.model.KotlinProperty>>
) {
    private val extFunMap = mutableMapOf<String, KotlinExtensionFun>()
    private val generatedClassNameList = kotlinClasses.map { it.klassName }
    
    /**
     * 添加扩展函数
     */
    private fun addExtensionFun(extFun: KotlinExtensionFun) {
        if (extFun.receiver != extFun.funName) {
            if (listOf("HasSpan", "HasDecorator").contains(extFun.receiver)) return
            if (extFun.receiver == extFun.funName.replace("Impl", "")) return
            
            extFunMap["${extFun.receiver} - ${extFun.funName.replace("Impl", "")}"] = extFun
        }
    }
    
    /**
     * 生成 DSL 扩展函数
     */
    fun generateExtensionFunctions() {
        val needGenerateDslClassList = kotlinClasses
            .filter { ExtendRelationship.findAllChildrenByParent(it.klassName).isNotEmpty() }
            .map { it.klassName }
        
        needGenerateDslClassList.forEach { key ->
            createExtensionFunList(key)
        }
        
        needGenerateDslClassList
            .mapNotNull { klass ->
                val props = classAllPropertiesMap[klass]
                if (props != null) klass to props else null
            }
            .forEach { (klass, props) ->
                props.forEach { prop ->
                    prop.getTypeList().forEach { type ->
                        ExtendRelationship.findAllGrandChildren(type).forEach { child ->
                            addExtensionFunWrapper(KotlinExtensionFun(
                                klass,
                                child,
                                """
                                /**
                                  * $klass#${prop.name}: ${prop.type}
                                  * extension function for create ${prop.type} -> $child
                                  */
                                """.trimIndent()
                            ))
                        }
                    }
                }
            }
    }
    
    /**
     * 创建扩展函数列表
     */
    private fun createExtensionFunList(key: String) {
        ExtendRelationship.findAllGrandChildren(key).forEach { child ->
            addExtensionFunWrapper(KotlinExtensionFun(
                key,
                child,
                """
                /**
                  * subtype of $key
                  */
                """.trimIndent()
            ))
        }
    }
    
    /**
     * 添加扩展函数（带包装逻辑）
     */
    private fun addExtensionFunWrapper(extFun: KotlinExtensionFun) {
        if (listOf("HasSpan", "HasDecorator").contains(extFun.receiver)) return
        if (extFun.receiver == extFun.funName.replace("Impl", "")) return
        
        // 过滤掉包含非法字符的类型名称
        if (!isValidTypeName(extFun.funName) || !isValidTypeName(extFun.receiver)) {
            Logger.warn("跳过无效类型名称: ${extFun.receiver}.${extFun.funName}")
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
     * 验证类型名称是否有效（增强版）
     */
    private fun isValidTypeName(typeName: String): Boolean {
        if (typeName.isBlank()) return false
        
        // 检查是否包含非法字符
        if (typeName.contains("<") || typeName.contains(">")) {
            Logger.verbose("类型名称包含泛型符号: $typeName", 10)
            return false
        }
        if (typeName.contains(" ") || typeName.contains("\n") || typeName.contains("\t")) {
            Logger.verbose("类型名称包含空白字符: $typeName", 10)
            return false
        }
        
        // 检查是否包含注释残留
        if (typeName.contains("/*") || typeName.contains("*/")) {
            Logger.verbose("类型名称包含注释: $typeName", 10)
            return false
        }
        
        // 检查是否以小写字母开头（类型名应该大写）
        if (typeName.isNotEmpty() && typeName[0].isLowerCase()) {
            Logger.verbose("类型名称首字母小写: $typeName", 10)
            return false
        }
        
        // 检查是否符合 Kotlin 标识符规范
        if (!typeName.matches(Regex("[A-Z][a-zA-Z0-9_]*"))) {
            Logger.verbose("类型名称不符合 Kotlin 标识符规范: $typeName", 10)
            return false
        }
        
        // 检查是否为保留字
        val reservedWords = setOf("class", "interface", "object", "fun", "val", "var", "if", "else", "when")
        if (reservedWords.contains(typeName.lowercase())) {
            Logger.verbose("类型名称是保留字: $typeName", 10)
            return false
        }
        
        return true
    }
    
    /**
     * 创建 DSL 头部
     */
    private fun createKotlinDslHeader(): List<String> {
        return listOf(
            "package dev.yidafu.swc.dsl",
            "",
            "import dev.yidafu.swc.types.*",
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
        
        val fileBuilder = createFileBuilder(PoetConstants.PKG_DSL, receiver,
            PoetConstants.PKG_TYPES to "*"
        )
        
        funList.forEach { extFun ->
            runCatching {
                createDslExtensionFun(extFun, receiver)
            }.onSuccess { funSpec ->
                fileBuilder.addFunction(funSpec)
            }.onFailure { e ->
                Logger.warn("生成失败: ${extFun.funName}, 跳过 (${e.message})")
                // KotlinPoet 不支持在普通文件中添加原始代码，跳过失败的函数
            }
        }
        
        // 手动写入文件，避免 KotlinPoet 创建包目录结构
        val fileSpec = fileBuilder.build()
        val fileName = "${receiver}.kt"
        val outputFile = File(outputDir, fileName)
        outputFile.writeText(fileSpec.toString())
    }
    
    /**
     * 创建 DSL 扩展函数
     */
    private fun createDslExtensionFun(extFun: KotlinExtensionFun, receiver: String): FunSpec {
        val funName = toFunName(extFun.funName.replace("Impl", ""))
        // 清理泛型参数，避免 ClassName 构造器错误
        val returnTypeName = sanitizeTypeName(removeGenerics(extFun.funName.replace("Impl", "")))
        val implTypeName = sanitizeTypeName(removeGenerics(extFun.funName))
        val receiverTypeName = sanitizeTypeName(removeGenerics(receiver))
        
        return createExtensionFun(
            funName = funName,
            receiverType = ClassName(PoetConstants.PKG_TYPES, receiverTypeName),
            returnType = ClassName(PoetConstants.PKG_TYPES, returnTypeName),
            implType = ClassName(PoetConstants.PKG_TYPES, implTypeName),
            kdoc = extFun.comments.takeIf { it.isNotEmpty() }
        )
    }
    
    /**
     * 清理类型名称，确保它是有效的 Kotlin 类型名称（增强版）
     */
    private fun sanitizeTypeName(typeName: String): String {
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
        
        return cleaned
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
        return Constants.kotlinKeywordMap[name] ?: name
    }
    
    /**
     * 生成 create 函数（使用 KotlinPoet）
     */
    private fun generateCreateFunctions(outputDir: String) {
        Logger.debug("生成 create.kt", 6)
        
        val implClasses = kotlinClasses.filter { it.klassName.endsWith("Impl") }
        val fileBuilder = createFileBuilder(PoetConstants.PKG_DSL, "create",
            PoetConstants.PKG_TYPES to "*"
        )
        
        implClasses.forEach { klass ->
            val funSpec = createCreateFunction(klass)
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
    private fun createCreateFunction(klass: KotlinClass): FunSpec {
        val interfaceName = removeGenerics(klass.klassName.replace("Impl", ""))
        val klassName = removeGenerics(klass.klassName)
        val interfaceType = ClassName(PoetConstants.PKG_TYPES, interfaceName)
        val implType = ClassName(PoetConstants.PKG_TYPES, klassName)
        
        return FunSpec.builder("create$interfaceName")
            .addParameter("block", createDslLambdaType(interfaceType))
            .returns(interfaceType)
            .addStatement("return %T().apply(block)", implType)
            .build()
    }
}


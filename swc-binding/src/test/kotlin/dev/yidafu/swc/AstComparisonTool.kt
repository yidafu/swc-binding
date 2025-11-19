package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import dev.yidafu.swc.util.NodeJsHelper
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File

/**
 * 工具类：对比 SwcNative 和 @swc/core 生成的 AST JSON，找出 Kotlin AST 类定义问题
 */
object AstComparisonTool {
    
    /**
     * 对比结果数据类
     */
    data class ComparisonResult(
        val kotlinJson: String,
        val swcJson: String,
        val differences: List<Difference>,
        val areEqual: Boolean
    )
    
    /**
     * 差异信息
     */
    data class Difference(
        val path: String,
        val type: DifferenceType,
        val kotlinValue: String?,
        val swcValue: String?,
        val message: String
    )
    
    enum class DifferenceType {
        MISSING_IN_KOTLIN,      // Kotlin 中缺失字段
        MISSING_IN_SWC,         // @swc/core 中缺失字段
        VALUE_MISMATCH,         // 值不匹配
        TYPE_MISMATCH,          // 类型不匹配
        ARRAY_LENGTH_MISMATCH   // 数组长度不匹配
    }
    
    /**
     * 执行对比分析
     */
    fun compareAst(
        code: String,
        options: ParserConfig,
        swcNative: SwcNative,
        testName: String = "test"
    ): ComparisonResult {
        // 检查 Node.js 是否可用
        if (!NodeJsHelper.isNodeJsAvailable()) {
            throw IllegalStateException("Node.js is not available. Cannot compare with @swc/core.")
        }
        
        // 1. 获取 Kotlin (SwcNative) 的原始 JSON
        val optStr = configJson.encodeToString<ParserConfig>(options)
        val kotlinRawJson = try {
            swcNative.parseSync(code, optStr, "test.js")
        } catch (e: Exception) {
            throw IllegalStateException("Failed to parse code with SwcNative: ${e.message}", e)
        }
        
        if (kotlinRawJson.isBlank()) {
            throw IllegalStateException("SwcNative returned empty JSON")
        }
        
        // 2. 获取 @swc/core 的 JSON
        val swcOptionsJson = convertToSwcParseOptions(options)
        val swcRawJson = try {
            NodeJsHelper.parseCode(code, swcOptionsJson)
        } catch (e: Exception) {
            throw IllegalStateException("Failed to parse code with @swc/core: ${e.message}", e)
        }
        
        if (swcRawJson.isBlank()) {
            throw IllegalStateException("@swc/core returned empty JSON")
        }
        
        // 3. 解析 JSON 并对比
        val kotlinElement = Json.parseToJsonElement(kotlinRawJson)
        val swcElement = Json.parseToJsonElement(swcRawJson)
        
        val differences = mutableListOf<Difference>()
        compareElements(kotlinElement, swcElement, "", differences)
        
        // 4. 保存 JSON 到文件以便进一步分析
        saveJsonFiles(testName, kotlinRawJson, swcRawJson)
        
        return ComparisonResult(
            kotlinJson = kotlinRawJson,
            swcJson = swcRawJson,
            differences = differences,
            areEqual = differences.isEmpty()
        )
    }
    
    /**
     * 递归对比两个 JSON 元素
     */
    private fun compareElements(
        kotlin: JsonElement,
        swc: JsonElement,
        path: String,
        differences: MutableList<Difference>
    ) {
        when {
            kotlin is JsonNull && swc is JsonNull -> {
                // 两者都是 null，相同
            }
            kotlin is JsonNull -> {
                differences.add(
                    Difference(
                        path = path,
                        type = DifferenceType.MISSING_IN_KOTLIN,
                        kotlinValue = null,
                        swcValue = swc.toString(),
                        message = "Kotlin is null, but @swc/core has value: ${swc}"
                    )
                )
            }
            swc is JsonNull -> {
                differences.add(
                    Difference(
                        path = path,
                        type = DifferenceType.MISSING_IN_SWC,
                        kotlinValue = kotlin.toString(),
                        swcValue = null,
                        message = "@swc/core is null, but Kotlin has value: ${kotlin}"
                    )
                )
            }
            kotlin is JsonPrimitive && swc is JsonPrimitive -> {
                if (!comparePrimitives(kotlin, swc)) {
                    differences.add(
                        Difference(
                            path = path,
                            type = DifferenceType.VALUE_MISMATCH,
                            kotlinValue = kotlin.toString(),
                            swcValue = swc.toString(),
                            message = "Value mismatch: Kotlin='${kotlin}' vs @swc/core='${swc}'"
                        )
                    )
                }
            }
            kotlin is JsonArray && swc is JsonArray -> {
                if (kotlin.size != swc.size) {
                    differences.add(
                        Difference(
                            path = path,
                            type = DifferenceType.ARRAY_LENGTH_MISMATCH,
                            kotlinValue = kotlin.size.toString(),
                            swcValue = swc.size.toString(),
                            message = "Array length mismatch: Kotlin=${kotlin.size} vs @swc/core=${swc.size}"
                        )
                    )
                }
                // 对比数组元素
                val minSize = minOf(kotlin.size, swc.size)
                for (i in 0 until minSize) {
                    compareElements(kotlin[i], swc[i], "$path[$i]", differences)
                }
            }
            kotlin is JsonObject && swc is JsonObject -> {
                val allKeys = (kotlin.keys + swc.keys).distinct().sorted()
                
                for (key in allKeys) {
                    val newPath = if (path.isEmpty()) key else "$path.$key"
                    val kotlinValue = kotlin[key]
                    val swcValue = swc[key]
                    
                    when {
                        kotlinValue == null && swcValue == null -> {
                            // 两者都缺失，跳过
                        }
                        kotlinValue == null -> {
                            differences.add(
                                Difference(
                                    path = newPath,
                                    type = DifferenceType.MISSING_IN_KOTLIN,
                                    kotlinValue = null,
                                    swcValue = swcValue.toString(),
                                    message = "Field '$key' missing in Kotlin AST, present in @swc/core: ${swcValue}"
                                )
                            )
                        }
                        swcValue == null -> {
                            differences.add(
                                Difference(
                                    path = newPath,
                                    type = DifferenceType.MISSING_IN_SWC,
                                    kotlinValue = kotlinValue.toString(),
                                    swcValue = null,
                                    message = "Field '$key' missing in @swc/core AST, present in Kotlin: ${kotlinValue}"
                                )
                            )
                        }
                        else -> {
                            compareElements(kotlinValue, swcValue, newPath, differences)
                        }
                    }
                }
            }
            else -> {
                differences.add(
                    Difference(
                        path = path,
                        type = DifferenceType.TYPE_MISMATCH,
                        kotlinValue = kotlin.toString(),
                        swcValue = swc.toString(),
                        message = "Type mismatch: Kotlin=${kotlin::class.simpleName} vs @swc/core=${swc::class.simpleName}"
                    )
                )
            }
        }
    }
    
    /**
     * 对比两个原始值
     */
    private fun comparePrimitives(p1: JsonPrimitive, p2: JsonPrimitive): Boolean {
        if (p1.isString && p2.isString) {
            return p1.content == p2.content
        }
        
        if (p1.isString || p2.isString) {
            return false
        }
        
        // 数字对比
        val d1 = p1.doubleOrNull
        val d2 = p2.doubleOrNull
        if (d1 != null && d2 != null) {
            return kotlin.math.abs(d1 - d2) < 1e-10
        }
        
        // 布尔值对比
        val b1 = p1.booleanOrNull
        val b2 = p2.booleanOrNull
        if (b1 != null && b2 != null) {
            return b1 == b2
        }
        
        return false
    }
    
    /**
     * 转换 Kotlin ParserConfig 为 @swc/core parse options 格式
     */
    fun convertToSwcParseOptions(options: ParserConfig): String {
        fun getTargetName(target: JscTarget?): String {
            return target?.let {
                configJson.encodeToString(it).trim('"')
            } ?: "es5"
        }
        
        return when (options) {
            is EsParserConfig -> {
                val syntax = "ecmascript"
                val target = getTargetName(options.target)
                val isModule = true
                val comments = options.comments ?: false
                val script = options.script ?: false
                val jsx = options.jsx ?: false
                
                buildString {
                    append("""{"syntax":"$syntax","target":"$target","isModule":$isModule,"comments":$comments,"script":$script""")
                    if (jsx) append(""","jsx":true""")
                    append("}")
                }
            }
            is TsParserConfig -> {
                val syntax = "typescript"
                val target = getTargetName(options.target)
                val isModule = true
                val comments = options.comments ?: false
                val script = options.script ?: false
                val tsx = options.tsx ?: false
                val decorators = options.decorators ?: false
                
                buildString {
                    append("""{"syntax":"$syntax","target":"$target","isModule":$isModule,"comments":$comments,"script":$script""")
                    if (tsx) append(""","tsx":true""")
                    if (decorators) append(""","decorators":true""")
                    append("}")
                }
            }
            else -> """{"syntax":"ecmascript","target":"es5","isModule":true,"comments":false,"script":false}"""
        }
    }
    
    /**
     * 保存 JSON 文件到临时目录
     */
    private fun saveJsonFiles(testName: String, kotlinJson: String, swcJson: String) {
        val outputDir = File("build/ast-comparison")
        outputDir.mkdirs()
        
        val kotlinFile = File(outputDir, "$testName-kotlin.json")
        val swcFile = File(outputDir, "$testName-swc.json")
        
        kotlinFile.writeText(kotlinJson)
        swcFile.writeText(swcJson)
        
        println("Saved JSON files:")
        println("  Kotlin: ${kotlinFile.absolutePath}")
        println("  @swc/core: ${swcFile.absolutePath}")
    }
    
    /**
     * 打印对比报告
     */
    fun printReport(result: ComparisonResult) {
        println("=".repeat(80))
        println("AST JSON 对比报告")
        println("=".repeat(80))
        
        if (result.areEqual) {
            println("✅ 两个 AST JSON 完全相同！")
            return
        }
        
        println("\n❌ 发现 ${result.differences.size} 处差异：\n")
        
        // 按类型分组
        val byType = result.differences.groupBy { it.type }
        
        byType.forEach { (type, diffs) ->
            println("\n${"=".repeat(60)}")
            println("${type.name} (${diffs.size} 处)")
            println("${"=".repeat(60)}")
            
            diffs.forEachIndexed { index, diff ->
                println("\n差异 #${index + 1}: ${diff.path}")
                println("  类型: ${diff.type}")
                println("  消息: ${diff.message}")
                if (diff.kotlinValue != null) {
                    println("  Kotlin 值: ${diff.kotlinValue.take(200)}${if (diff.kotlinValue.length > 200) "..." else ""}")
                }
                if (diff.swcValue != null) {
                    println("  @swc/core 值: ${diff.swcValue.take(200)}${if (diff.swcValue.length > 200) "..." else ""}")
                }
            }
        }
        
        // 分析可能的问题
        println("\n${"=".repeat(80)}")
        println("问题分析")
        println("${"=".repeat(80)}")
        
        analyzeIssues(result.differences)
        
        println("\n${"=".repeat(80)}")
    }
    
    /**
     * 分析差异，识别可能的 Kotlin AST 类定义问题
     */
    private fun analyzeIssues(differences: List<Difference>) {
        val missingInKotlin = differences.filter { it.type == DifferenceType.MISSING_IN_KOTLIN }
        val missingInSwc = differences.filter { it.type == DifferenceType.MISSING_IN_SWC }
        val valueMismatches = differences.filter { it.type == DifferenceType.VALUE_MISMATCH }
        val typeMismatches = differences.filter { it.type == DifferenceType.TYPE_MISMATCH }
        
        if (missingInKotlin.isNotEmpty()) {
            println("\n⚠️  Kotlin AST 类定义可能缺失的字段:")
            missingInKotlin.take(20).forEach { diff ->
                val fieldPath = diff.path.split('.').lastOrNull() ?: diff.path
                println("  - $fieldPath (路径: ${diff.path})")
            }
            if (missingInKotlin.size > 20) {
                println("  ... 还有 ${missingInKotlin.size - 20} 个字段")
            }
        }
        
        if (missingInSwc.isNotEmpty()) {
            println("\n⚠️  Kotlin AST 类定义中可能有多余的字段（@swc/core 中没有）:")
            missingInSwc.take(20).forEach { diff ->
                val fieldPath = diff.path.split('.').lastOrNull() ?: diff.path
                println("  - $fieldPath (路径: ${diff.path})")
            }
            if (missingInSwc.size > 20) {
                println("  ... 还有 ${missingInSwc.size - 20} 个字段")
            }
        }
        
        if (typeMismatches.isNotEmpty()) {
            println("\n⚠️  类型不匹配（可能是序列化/反序列化问题）:")
            typeMismatches.take(10).forEach { diff ->
                println("  - ${diff.path}: ${diff.message}")
            }
        }
        
        // 分析常见的字段名模式
        val commonFields = listOf("span", "ctxt", "type", "loc", "range")
        val missingFields = missingInKotlin.map { it.path.split('.').lastOrNull() ?: "" }
            .filter { it in commonFields }
        
        if (missingFields.isNotEmpty()) {
            println("\n⚠️  常见字段缺失:")
            missingFields.forEach { field ->
                println("  - $field (这是 SWC AST 的标准字段，应该存在于所有节点)")
            }
        }
    }
}


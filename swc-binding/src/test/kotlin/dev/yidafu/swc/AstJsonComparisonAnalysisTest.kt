package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import dev.yidafu.swc.util.JsonComparator
import dev.yidafu.swc.util.NodeJsHelper
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ShouldSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 系统性地对比 SwcNative 和 @swc/core 生成的 AST JSON，找出 Kotlin AST 类定义问题
 * 
 * 这个测试会：
 * 1. 运行多个测试用例
 * 2. 生成详细的差异报告
 * 3. 保存 JSON 到文件以便进一步分析
 * 4. 统计常见的差异模式
 */
@Ignored
class AstJsonComparisonAnalysisTest : ShouldSpec({
    val swcNative = SwcNative()
    val outputDir = File("/tmp/swc-ast-comparison").apply { mkdirs() }
    val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
    val reportFile = File(outputDir, "comparison-report_$timestamp.txt")
    val report = StringBuilder()

    fun log(message: String) {
        println(message)
        report.appendLine(message)
    }

    /**
     * Convert Kotlin ParserConfig to @swc/core parse options format
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

                """{"syntax":"$syntax","target":"$target","isModule":$isModule,"comments":$comments,"script":$script,"tsx":$tsx,"decorators":$decorators}"""
            }
            else -> {
                """{"syntax":"ecmascript","target":"es5","isModule":true,"comments":false,"script":false}"""
            }
        }
    }

    fun collectSpans(element: JsonElement): List<String> {
        val spans = mutableListOf<String>()
        when (element) {
            is JsonObject -> {
                if (element.containsKey("span")) {
                    spans.add(element["span"].toString())
                }
                element.values.forEach { collectSpans(it).forEach { spans.add(it) } }
            }
            is JsonArray -> {
                element.forEach { collectSpans(it).forEach { spans.add(it) } }
            }
            is JsonPrimitive -> { /* 忽略 */ }
            is JsonNull -> { /* 忽略 */ }
        }
        return spans
    }

    fun collectCtxts(element: JsonElement): List<Int> {
        val ctxts = mutableListOf<Int>()
        when (element) {
            is JsonObject -> {
                if (element.containsKey("ctxt")) {
                    element["ctxt"]?.let {
                        if (it is JsonPrimitive) {
                            it.longOrNull?.toInt()?.let { ctxts.add(it) }
                        }
                    }
                }
                element.values.forEach { collectCtxts(it).forEach { ctxts.add(it) } }
            }
            is JsonArray -> {
                element.forEach { collectCtxts(it).forEach { ctxts.add(it) } }
            }
            is JsonPrimitive -> { /* 忽略 */ }
            is JsonNull -> { /* 忽略 */ }
        }
        return ctxts
    }

    fun collectTypes(element: JsonElement): List<String> {
        val types = mutableListOf<String>()
        when (element) {
            is JsonObject -> {
                if (element.containsKey("type")) {
                    element["type"]?.let {
                        if (it is JsonPrimitive && it.isString) {
                            types.add(it.content)
                        }
                    }
                }
                element.values.forEach { collectTypes(it).forEach { types.add(it) } }
            }
            is JsonArray -> {
                element.forEach { collectTypes(it).forEach { types.add(it) } }
            }
            is JsonPrimitive -> { /* 忽略 */ }
            is JsonNull -> { /* 忽略 */ }
        }
        return types
    }

    fun checkNullVsMissing(kotlinElement: JsonElement, swcElement: JsonElement) {
        // 检查是否有字段在 Kotlin 中是 null，但在 SWC 中缺失
        // 或者相反
        // 这个需要递归比较，暂时跳过详细实现
    }

    fun countNodes(element: JsonElement): Int {
        var count = 0
        when (element) {
            is JsonObject -> {
                if (element.containsKey("type")) {
                    count++
                }
                element.values.forEach { count += countNodes(it) }
            }
            is JsonArray -> {
                element.forEach { count += countNodes(it) }
            }
            is JsonPrimitive -> { /* 忽略 */ }
            is JsonNull -> { /* 忽略 */ }
        }
        return count
    }

    /**
     * 检查常见问题
     */
    fun checkCommonIssues(kotlinElement: JsonElement, swcElement: JsonElement) {
        log("\n常见问题检查:")

        // 检查 span 字段
        val kotlinSpans = collectSpans(kotlinElement)
        val swcSpans = collectSpans(swcElement)
        log("  Span 数量: Kotlin=${kotlinSpans.size}, SWC=${swcSpans.size}")

        // 检查 ctxt 字段
        val kotlinCtxts = collectCtxts(kotlinElement)
        val swcCtxts = collectCtxts(swcElement)
        log("  Ctxt 数量: Kotlin=${kotlinCtxts.size}, SWC=${swcCtxts.size}")

        // 检查 type 字段
        val kotlinTypes = collectTypes(kotlinElement)
        val swcTypes = collectTypes(swcElement)
        log("  Type 字段数量: Kotlin=${kotlinTypes.size}, SWC=${swcTypes.size}")

        // 检查是否有 null vs 缺失字段的问题
        checkNullVsMissing(kotlinElement, swcElement)
    }

    /**
     * 分析差异模式
     */
    fun analyzeDifferences(kotlinJson: String, swcJson: String, differences: List<String>) {
        log("\n差异模式分析:")

        val kotlinElement = Json.parseToJsonElement(kotlinJson)
        val swcElement = Json.parseToJsonElement(swcJson)

        // 统计缺失字段
        val missingInKotlin = mutableListOf<String>()
        val missingInSwc = mutableListOf<String>()
        val typeMismatches = mutableListOf<String>()
        val valueDifferences = mutableListOf<String>()

        differences.forEach { diff ->
            when {
                diff.contains("missing in first JSON") -> {
                    val field = diff.substringBefore(": missing")
                    missingInKotlin.add(field)
                }
                diff.contains("missing in second JSON") -> {
                    val field = diff.substringBefore(": missing")
                    missingInSwc.add(field)
                }
                diff.contains("type mismatch") -> {
                    typeMismatches.add(diff)
                }
                diff.contains("values differ") -> {
                    valueDifferences.add(diff)
                }
            }
        }

        if (missingInKotlin.isNotEmpty()) {
            log("\n  Kotlin 缺失的字段 (${missingInKotlin.size}):")
            missingInKotlin.take(20).forEach { log("    - $it") }
            if (missingInKotlin.size > 20) {
                log("    ... 还有 ${missingInKotlin.size - 20} 个")
            }
        }

        if (missingInSwc.isNotEmpty()) {
            log("\n  SWC 缺失的字段 (${missingInSwc.size}):")
            missingInSwc.take(20).forEach { log("    - $it") }
            if (missingInSwc.size > 20) {
                log("    ... 还有 ${missingInSwc.size - 20} 个")
            }
        }

        if (typeMismatches.isNotEmpty()) {
            log("\n  类型不匹配 (${typeMismatches.size}):")
            typeMismatches.take(10).forEach { log("    - $it") }
            if (typeMismatches.size > 10) {
                log("    ... 还有 ${typeMismatches.size - 10} 个")
            }
        }

        if (valueDifferences.isNotEmpty()) {
            log("\n  值差异 (${valueDifferences.size}):")
            valueDifferences.take(10).forEach { log("    - $it") }
            if (valueDifferences.size > 10) {
                log("    ... 还有 ${valueDifferences.size - 10} 个")
            }
        }

        // 检查常见问题
        checkCommonIssues(kotlinElement, swcElement)
    }

    /**
     * 对比两个 JSON 并生成详细报告
     */
    fun compareAndAnalyze(
        testName: String,
        code: String,
        options: ParserConfig,
        filename: String
    ) {
        if (!NodeJsHelper.isNodeJsAvailable()) {
            log("⚠️  Node.js 不可用，跳过测试: $testName")
            return
        }

        log("\n${"=".repeat(80)}")
        log("测试用例: $testName")
        log("${"=".repeat(80)}")
        log("代码:\n$code")
        log("")

        try {
            // 获取 Kotlin AST JSON
            val kotlinAst = swcNative.parseSync(code, options, filename)
            val kotlinJson = astJson.encodeToString<Program>(kotlinAst)

            // 获取 @swc/core AST JSON
            val swcOptionsJson = convertToSwcParseOptions(options)
            val swcJson = NodeJsHelper.parseCode(code, swcOptionsJson)

            // 保存 JSON 文件
            val kotlinJsonFile = File(outputDir, "${testName}_kotlin_$timestamp.json")
            val swcJsonFile = File(outputDir, "${testName}_swc_$timestamp.json")
            kotlinJsonFile.writeText(kotlinJson)
            swcJsonFile.writeText(swcJson)
            log("已保存 JSON 文件:")
            log("  Kotlin: ${kotlinJsonFile.absolutePath}")
            log("  SWC:    ${swcJsonFile.absolutePath}")

            // 对比 JSON
            val areEqual = JsonComparator.compare(kotlinJson, swcJson)

            if (areEqual) {
                log("✅ JSON 完全匹配")
            } else {
                log("❌ JSON 存在差异")

                // 获取详细差异
                val differences = JsonComparator.getDifferences(kotlinJson, swcJson)
                log("\n差异详情 (共 ${differences.size} 处):")
                differences.forEachIndexed { index, diff ->
                    log("  ${index + 1}. $diff")
                }

                // 分析差异模式
                analyzeDifferences(kotlinJson, swcJson, differences)
            }

            // 基本统计
            val kotlinElement = Json.parseToJsonElement(kotlinJson)
            val swcElement = Json.parseToJsonElement(swcJson)
            log("\n基本统计:")
            log("  Kotlin JSON 大小: ${kotlinJson.length} 字符")
            log("  SWC JSON 大小:   ${swcJson.length} 字符")
            log("  Kotlin 节点数:   ${countNodes(kotlinElement)}")
            log("  SWC 节点数:      ${countNodes(swcElement)}")

        } catch (e: Exception) {
            log("❌ 错误: ${e.message}")
            e.printStackTrace()
        }
    }


    // 测试用例
    context("AST JSON 对比分析") {
        should("简单变量声明") {
            compareAndAnalyze(
                "simple-var-decl",
                "const x = 42;",
                esParseOptions { },
                "test.js"
            )
        }

        should("函数声明") {
            compareAndAnalyze(
                "function-decl",
                """
                function add(a, b) {
                    return a + b;
                }
                """.trimIndent(),
                esParseOptions { },
                "test.js"
            )
        }

        should("JSX 元素") {
            compareAndAnalyze(
                "jsx-element",
                """
                function App() {
                    return <div>Hello</div>;
                }
                """.trimIndent(),
                esParseOptions {
                    jsx = true
                },
                "test.jsx"
            )
        }

        should("TypeScript 接口") {
            compareAndAnalyze(
                "ts-interface",
                """
                interface User {
                    name: string;
                    age: number;
                }
                """.trimIndent(),
                tsParseOptions { },
                "test.ts"
            )
        }

        should("TypeScript 类") {
            compareAndAnalyze(
                "ts-class",
                """
                class Person {
                    name: string;
                    constructor(name: string) {
                        this.name = name;
                    }
                }
                """.trimIndent(),
                tsParseOptions { },
                "test.ts"
            )
        }

        should("带注释的代码") {
            compareAndAnalyze(
                "with-comments",
                """
                // This is a comment
                const x = 42; // inline comment
                """.trimIndent(),
                esParseOptions {
                    comments = true
                },
                "test.js"
            )
        }

        should("复杂 JSX") {
            compareAndAnalyze(
                "complex-jsx",
                """
                function App() {
                    const show = true;
                    return (
                        <div>
                            {show && <p>Visible</p>}
                            {show ? <span>Yes</span> : <span>No</span>}
                        </div>
                    );
                }
                """.trimIndent(),
                esParseOptions {
                    jsx = true
                },
                "test.jsx"
            )
        }

        should("数组和对象") {
            compareAndAnalyze(
                "array-object",
                """
                const arr = [1, 2, 3];
                const obj = { a: 1, b: 2 };
                """.trimIndent(),
                esParseOptions { },
                "test.js"
            )
        }

        should("空模块") {
            compareAndAnalyze(
                "empty-module",
                "",
                esParseOptions { },
                "test.js"
            )
        }
    }

    afterSpec {
        // 保存报告
        reportFile.writeText(report.toString())
        log("\n${"=".repeat(80)}")
        log("报告已保存到: ${reportFile.absolutePath}")
        log("${"=".repeat(80)}")
    }
})


package dev.yidafu.swc.generator.util

/**
 * 日志工具
 */
object Logger {
    private var currentStep = 0
    private var totalSteps = 9

    // 调试模式开关（通过系统属性控制）
    private val debugMode = System.getProperty("DEBUG")?.toBoolean() ?: false
    private val verboseMode = System.getProperty("VERBOSE")?.toBoolean() ?: false

    // 性能统计
    private val timers = mutableMapOf<String, Long>()

    fun setTotalSteps(total: Int) {
        totalSteps = total
    }

    fun step(message: String) {
        currentStep++
        println("[$currentStep/$totalSteps] $message")
    }

    fun info(message: String, indent: Int = 2) {
        val prefix = " ".repeat(indent)
        println("$prefix$message")
    }

    fun success(message: String) {
        println("✓ $message")
    }

    fun error(message: String) {
        println("❌ $message")
    }

    fun error(e: Exception, message: String) {
        println("❌ $message")
        if (debugMode) {
            println("   异常类型: ${e.javaClass.simpleName}")
            println("   异常消息: ${e.message}")
            println("   堆栈跟踪:")
            e.stackTrace.take(10).forEach { frame ->
                println("     at $frame")
            }
            if (e.stackTrace.size > 10) {
                println("     ... (省略 ${e.stackTrace.size - 10} 行)")
            }
        } else {
            println("   异常: ${e.message}")
            println("   使用 --debug 查看详细堆栈跟踪")
        }
    }

    fun warn(message: String) {
        println("⚠️  $message")
    }

    fun header(title: String, char: String = "=") {
        val line = char.repeat(60)
        println(line)
        println(title)
        println(line)
    }

    fun separator() {
        println()
    }

    /**
     * 调试日志（仅在 DEBUG=true 时输出）
     */
    fun debug(message: String, indent: Int = 2) {
        if (debugMode) {
            val prefix = " ".repeat(indent)
            println("$prefix[DEBUG] $message")
        }
    }

    /**
     * 详细日志（仅在 VERBOSE=true 时输出）
     */
    fun verbose(message: String, indent: Int = 2) {
        if (verboseMode || debugMode) {
            val prefix = " ".repeat(indent)
            println("$prefix[VERBOSE] $message")
        }
    }

    /**
     * 开始计时
     */
    fun startTimer(name: String) {
        timers[name] = System.currentTimeMillis()
        debug("⏱️  开始计时: $name")
    }

    /**
     * 结束计时并输出
     */
    fun endTimer(name: String): Long {
        val startTime = timers[name] ?: return 0
        val elapsed = System.currentTimeMillis() - startTime
        timers.remove(name)
        debug("⏱️  完成 $name: ${elapsed}ms")
        return elapsed
    }

    /**
     * 打印断点信息
     */
    fun breakpoint(location: String, data: Map<String, Any?> = emptyMap()) {
        if (debugMode) {
            println("🔴 BREAKPOINT: $location")
            if (data.isNotEmpty()) {
                data.forEach { (key, value) ->
                    println("   ├─ $key = $value")
                }
            }
        }
    }

    /**
     * 打印集合统计信息
     */
    fun stats(name: String, collection: Collection<*>) {
        verbose("📊 $name: ${collection.size} 个元素")
    }

    /**
     * 条件日志（用于特定条件的断点）
     */
    fun logIf(condition: Boolean, message: String) {
        if (condition && debugMode) {
            println("   ⚡ $message")
        }
    }
}

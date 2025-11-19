package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import dev.yidafu.swc.util.NodeJsHelper
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import kotlin.test.Ignore

/**
 * E2E tests for minify method - comparing minified code from Kotlin and @swc/core
 * Currently skipped - uncomment @Ignore annotation when ready to run
 */
@Ignored
class AstJsonMinifyE2ETest : ShouldSpec({
    val swcNative = SwcNative()
    
    // Helper functions
    fun normalizeCode(code: String): String {
        return code
            .replace("\r\n", "\n")
            .replace("\r", "\n")
            .lines()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString("\n")
            .trim()
    }

    fun convertToSwcMinifyOptions(options: JsMinifyOptions): String {
        val compress = options.compress != null
        val mangle = when (val m = options.mangle) {
            is Union.U2<*, *> -> m.b == true
            else -> false
        }

        val minifyOptions = buildString {
            append("{")
            if (compress) {
                append("""\"compress\":{}""")
            }
            if (mangle) {
                if (compress) append(",")
                append("""\"mangle\":true""")
            }
            append("}")
        }

        return minifyOptions
    }

    should("minify simple JavaScript code") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        
        val code = """
            function add(a, b) {
                return a + b;
            }
        """.trimIndent()

        val minifyOptions = JsMinifyOptions().apply {
            compress = Union.U2<TerserCompressOptions, Boolean>(a = TerserCompressOptions())
            mangle = Union.U2<TerserMangleOptions, Boolean>(b = true)
        }

        // Get Kotlin minified code
        val kotlinOutput = swcNative.minifySync(code, minifyOptions)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        // Get @swc/core minified code
        val swcOptionsJson = convertToSwcMinifyOptions(minifyOptions)
        val swcCode = normalizeCode(NodeJsHelper.minifyCode(code, swcOptionsJson))

        kotlinCode shouldBe swcCode
    }

    should("minify JavaScript with variables") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = """
            const x = 42;
            const y = 100;
            const result = x + y;
        """.trimIndent()

        val minifyOptions = JsMinifyOptions().apply {
            compress = Union.U2<TerserCompressOptions, Boolean>(a = TerserCompressOptions())
            mangle = Union.U2<TerserMangleOptions, Boolean>(b = true)
        }

        val kotlinOutput = swcNative.minifySync(code, minifyOptions)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        val swcOptionsJson = convertToSwcMinifyOptions(minifyOptions)
        val swcCode = normalizeCode(NodeJsHelper.minifyCode(code, swcOptionsJson))

        kotlinCode shouldBe swcCode
    }

    should("minify TypeScript code") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = """
            interface User {
                name: string;
                age: number;
            }
            const user: User = { name: "John", age: 30 };
        """.trimIndent()

        val minifyOptions = JsMinifyOptions().apply {
            compress = Union.U2<TerserCompressOptions, Boolean>(a = TerserCompressOptions())
            mangle = Union.U2<TerserMangleOptions, Boolean>(b = true)
        }

        val kotlinOutput = swcNative.minifySync(code, minifyOptions)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        val swcOptionsJson = convertToSwcMinifyOptions(minifyOptions)
        val swcCode = normalizeCode(NodeJsHelper.minifyCode(code, swcOptionsJson))

        kotlinCode shouldBe swcCode
    }

    should("minify with compress options") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        val code = """
            function unused() {
                return 42;
            }
            function used() {
                return 100;
            }
            used();
        """.trimIndent()

        val minifyOptions = JsMinifyOptions().apply {
            compress = Union.U2<TerserCompressOptions, Boolean>(a = TerserCompressOptions())
            mangle = Union.U2<TerserMangleOptions, Boolean>(b = false)
        }

        val kotlinOutput = swcNative.minifySync(code, minifyOptions)
        val kotlinCode = normalizeCode(kotlinOutput.code)

        val swcOptionsJson = convertToSwcMinifyOptions(minifyOptions)
        val swcCode = normalizeCode(NodeJsHelper.minifyCode(code, swcOptionsJson))

        kotlinCode shouldBe swcCode
    }

})


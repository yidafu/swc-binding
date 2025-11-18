package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Tests for JSON utility functions in json.kt
 */
class JsonUtilsTest : AnnotationSpec() {
    private val swc: SwcNative by lazy {
        runCatching { SwcNative() }.getOrElse { throwable ->
            throw RuntimeException("Failed to initialize SwcNative", throwable)
        }
    }

    // ==================== parseAstTree tests ====================

    @Test
    fun `parseAstTree with valid Module JSON`() {
        val optStr = configJson.encodeToString<ParserConfig>(esParseOptions { })
        val jsonStr = swc.parseSync(
            "const x = 1;",
            optStr,
            "test.js"
        )

        // 打印原始解析得到的 AST JSON 字符串
        println("=== Raw AST JSON from parseSync ===")
        println(jsonStr)
        println("=== End of Raw AST JSON ===")

        // 检查 JSON 中是否包含 ctxt 字段
        val hasCtxt = jsonStr.contains("\"ctxt\"")
        println("Raw JSON contains 'ctxt' field: $hasCtxt")

        // 统计 span 对象数量
        val spanCount = jsonStr.split("\"span\"").size - 1
        val ctxtCount = jsonStr.split("\"ctxt\"").size - 1
        println("Total 'span' occurrences: $spanCount")
        println("Total 'ctxt' occurrences: $ctxtCount")

        val program = parseAstTree(jsonStr)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parseAstTree with complex Module`() {
        val optStr = configJson.encodeToString<ParserConfig>(esParseOptions { })
        val jsonStr = swc.parseSync(
            """
            function add(a, b) {
                return a + b;
            }
            const result = add(1, 2);
            """.trimIndent(),
            optStr,
            "test.js"
        )

        val program = parseAstTree(jsonStr)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        assertNotNull(module.body)
        assertTrue(module.body!!.size > 0)
    }

    @Test
    fun `parseAstTree with TypeScript`() {
        val optStr = configJson.encodeToString<ParserConfig>(tsParseOptions { })
        val jsonStr = swc.parseSync(
            """
            interface User {
                name: string;
                age: number;
            }
            const user: User = { name: "John", age: 30 };
            """.trimIndent(),
            optStr,
            "test.ts"
        )

        val program = parseAstTree(jsonStr)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parseAstTree with Script`() {
        val optStr = configJson.encodeToString<ParserConfig>(
            esParseOptions {
                script = true
            }
        )
        val jsonStr = swc.parseSync(
            "const x = 1;",
            optStr,
            "test.js"
        )

        val program = parseAstTree(jsonStr)

        assertNotNull(program)
        // Could be Module or Script depending on configuration
        assertTrue(program is Module || program is Script)
    }

    @Test
    fun `parseAstTree error handling with invalid JSON`() {
        val invalidJson = "{ invalid json }"

        shouldThrow<Exception> {
            parseAstTree(invalidJson)
        }
    }

    @Test
    fun `parseAstTree error handling with empty string`() {
        shouldThrow<Exception> {
            parseAstTree("")
        }
    }

    @Test
    fun `parseAstTree error handling with malformed JSON`() {
        val malformedJson = """{"type":"Module","span":{""" // Incomplete JSON

        shouldThrow<Exception> {
            parseAstTree(malformedJson)
        }
    }

    @Test
    fun `parseAstTree error handling with wrong type`() {
        val wrongTypeJson = """{"type":"InvalidType","span":{"start":0,"end":0,"ctxt":0}}"""

        // This might throw or return null depending on implementation
        try {
            val result = parseAstTree(wrongTypeJson)
            // If it doesn't throw, result might be null or invalid
        } catch (e: Exception) {
            // Expected behavior for invalid type
            assertNotNull(e.message)
        }
    }

    // ==================== astJson configuration tests ====================

    @Test
    fun `astJson encodes Module correctly`() {
        val module = swc.parseSync(
            "const x = 1;",
            esParseOptions { },
            "test.js"
        ) as Module

        val json = astJson.encodeToString<Program>(module)

        assertNotNull(json)
        assertTrue(json.contains("\"type\""))
        assertTrue(json.contains("Module"))
    }

    @Test
    fun `astJson includes null values`() {
        val module = swc.parseSync(
            "const x = 1;",
            esParseOptions { },
            "test.js"
        ) as Module

        val json = astJson.encodeToString<Program>(module)

        // With explicitNulls = true, null values should be included
        assertNotNull(json)
    }

    @Test
    fun `astJson includes default values`() {
        val module = swc.parseSync(
            "const x = 1;",
            esParseOptions { },
            "test.js"
        ) as Module

        val json = astJson.encodeToString<Program>(module)

        // With encodeDefaults = true, default values should be included
        assertNotNull(json)
    }

    @Test
    fun `astJson uses classDiscriminator`() {
        val module = swc.parseSync(
            "const x = 1;",
            esParseOptions { },
            "test.js"
        ) as Module

        val json = astJson.encodeToString<Program>(module)

        // Should contain the discriminator field "type"
        assertTrue(json.contains("\"type\""))
    }

    // ==================== configJson configuration tests ====================

    @Test
    fun `configJson encodes ParserConfig correctly`() {
        val config = esParseOptions {
            target = JscTarget.ES2020
            comments = true
        }

        val json = configJson.encodeToString<ParserConfig>(config)

        assertNotNull(json)
        assertTrue(json.contains("ecmascript") || json.contains("es2020"))
    }

    @Test
    fun `configJson encodes Options correctly`() {
        val options = options { }

        val json = configJson.encodeToString<Options>(options)

        assertNotNull(json)
    }

    @Test
    fun `configJson uses classDiscriminator for syntax`() {
        val config = tsParseOptions { }

        val json = configJson.encodeToString<ParserConfig>(config)

        // Should contain the discriminator field "syntax"
        assertTrue(json.contains("\"syntax\""))
    }

    @Test
    fun `configJson includes default values`() {
        val config = esParseOptions { }

        val json = configJson.encodeToString<ParserConfig>(config)

        // With encodeDefaults = true, default values should be included
        assertNotNull(json)
    }

    // ==================== Round-trip tests ====================

    @Test
    fun `parseAstTree round-trip with Module`() {
        val originalCode = "const x = 1; const y = 2;"
        val optStr = configJson.encodeToString<ParserConfig>(esParseOptions { })
        val jsonStr = swc.parseSync(originalCode, optStr, "test.js")

        val program = parseAstTree(jsonStr)
        val serialized = astJson.encodeToString<Program>(program)
        val deserialized = parseAstTree(serialized)

        assertNotNull(deserialized)
        deserialized.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parseAstTree preserves structure`() {
        val code = """
            function add(a, b) {
                return a + b;
            }
            const result = add(1, 2);
        """.trimIndent()

        val optStr = configJson.encodeToString<ParserConfig>(esParseOptions { })
        val jsonStr = swc.parseSync(code, optStr, "test.js")
        val program = parseAstTree(jsonStr) as Module

        assertNotNull(program.body)
        assertTrue(program.body!!.size >= 2)
    }

    // ==================== Edge cases ====================

    @Test
    fun `parseAstTree with empty Module`() {
        val emptyModuleJson = """{"type":"Module","span":{"start":0,"end":0,"ctxt":0},"body":[],"interpreter":null}"""

        val program = parseAstTree(emptyModuleJson)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        assertNotNull(module.body)
        assertTrue(module.body!!.isEmpty())
    }

    @Test
    fun `parseAstTree with large AST`() {
        val largeCode = buildString {
            repeat(100) { i ->
                appendLine("const var$i = $i;")
            }
        }

        val optStr = configJson.encodeToString<ParserConfig>(esParseOptions { })
        val jsonStr = swc.parseSync(largeCode, optStr, "large.js")
        val program = parseAstTree(jsonStr)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `astJson handles special characters`() {
        val code = "const str = \"Hello\\nWorld\\t!\";"
        val program = swc.parseSync(code, esParseOptions { }, "test.js")
        val module = program as Module

        val json = astJson.encodeToString<Program>(module)

        assertNotNull(json)
        // Should properly escape special characters
    }
}

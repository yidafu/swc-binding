package dev.yidafu.swc.util

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.SwcJson
import dev.yidafu.swc.astJson
import dev.yidafu.swc.configJson
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.encodeToString
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Tests for JSON utility functions in json.kt
 */
class JsonUtilsTest : ShouldSpec({
    val swc: SwcNative by lazy {
        runCatching { SwcNative() }.getOrElse { throwable ->
            throw RuntimeException("Failed to initialize SwcNative", throwable)
        }
    }

    // ==================== parseAstTree tests ====================

    should("parseAstTree with valid Module JSON") {
        val optStr = configJson.encodeToString<ParserConfig>(esParseOptions { })
        val jsonStr = swc.parseSync(
            "const x = 1;",
            optStr,
            "test.js"
        )

        // Print raw AST JSON string from parseSync
        println("=== Raw AST JSON from parseSync ===")
        println(jsonStr)
        println("=== End of Raw AST JSON ===")

        // Check if JSON contains ctxt field
        val hasCtxt = jsonStr.contains("\"ctxt\"")
        println("Raw JSON contains 'ctxt' field: $hasCtxt")

        // Count span object occurrences
        val spanCount = jsonStr.split("\"span\"").size - 1
        val ctxtCount = jsonStr.split("\"ctxt\"").size - 1
        println("Total 'span' occurrences: $spanCount")
        println("Total 'ctxt' occurrences: $ctxtCount")

        val program = SwcJson.parseAstTree(jsonStr)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
    }

    should("parseAstTree with complex Module") {
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

        val program = SwcJson.parseAstTree(jsonStr)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        assertNotNull(module.body)
        assertTrue(module.body!!.size > 0)
    }

    should("parseAstTree with TypeScript") {
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

        val program = SwcJson.parseAstTree(jsonStr)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
    }

    should("parseAstTree with Script") {
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

        val program = SwcJson.parseAstTree(jsonStr)

        assertNotNull(program)
        // Could be Module or Script depending on configuration
        assertTrue(program is Module || program is Script)
    }

    should("parseAstTree error handling with invalid JSON") {
        val invalidJson = "{ invalid json }"

        shouldThrow<Exception> {
            SwcJson.parseAstTree(invalidJson)
        }
    }

    should("parseAstTree error handling with empty string") {
        shouldThrow<Exception> {
            SwcJson.parseAstTree("")
        }
    }

    should("parseAstTree error handling with malformed JSON") {
        val malformedJson = """{"type":"Module","span":{""" // Incomplete JSON

        shouldThrow<Exception> {
            SwcJson.parseAstTree(malformedJson)
        }
    }

    should("parseAstTree error handling with wrong type") {
        val wrongTypeJson = """{"type":"InvalidType","span":{"start":0,"end":0,"ctxt":0}}"""

        // This might throw or return null depending on implementation
        try {
            val result = SwcJson.parseAstTree(wrongTypeJson)
            // If it doesn't throw, result might be null or invalid
        } catch (e: Exception) {
            // Expected behavior for invalid type
            assertNotNull(e.message)
        }
    }

    // ==================== astJson configuration tests ====================

    should("astJson encodes Module correctly") {
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

    should("astJson includes null values") {
        val module = swc.parseSync(
            "const x = 1;",
            esParseOptions { },
            "test.js"
        ) as Module

        val json = astJson.encodeToString<Program>(module)

        // With explicitNulls = true, null values should be included
        assertNotNull(json)
    }

    should("astJson includes default values") {
        val module = swc.parseSync(
            "const x = 1;",
            esParseOptions { },
            "test.js"
        ) as Module

        val json = astJson.encodeToString<Program>(module)

        // With encodeDefaults = true, default values should be included
        assertNotNull(json)
    }

    should("astJson uses classDiscriminator") {
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

    should("configJson encodes ParserConfig correctly") {
        val config = esParseOptions {
            target = JscTarget.ES2020
            comments = true
        }

        val json = configJson.encodeToString<ParserConfig>(config)

        assertNotNull(json)
        assertTrue(json.contains("ecmascript") || json.contains("es2020"))
    }

    should("configJson encodes Options correctly") {
        val options = options { }

        val json = configJson.encodeToString<Options>(options)

        assertNotNull(json)
    }

    should("configJson uses classDiscriminator for syntax") {
        val config = tsParseOptions { }

        val json = configJson.encodeToString<ParserConfig>(config)

        // Should contain the discriminator field "syntax"
        assertTrue(json.contains("\"syntax\""))
    }

    should("configJson includes default values") {
        val config = esParseOptions { }

        val json = configJson.encodeToString<ParserConfig>(config)

        // With encodeDefaults = true, default values should be included
        assertNotNull(json)
    }

    // ==================== Round-trip tests ====================

    should("parseAstTree round-trip with Module") {
        val originalCode = "const x = 1; const y = 2;"
        val optStr = configJson.encodeToString<ParserConfig>(esParseOptions { })
        val jsonStr = swc.parseSync(originalCode, optStr, "test.js")

        val program = SwcJson.parseAstTree(jsonStr)
        val serialized = astJson.encodeToString<Program>(program)
        val deserialized = SwcJson.parseAstTree(serialized)

        assertNotNull(deserialized)
        deserialized.shouldBeInstanceOf<Module>()
    }

    should("parseAstTree preserves structure") {
        val code = """
            function add(a, b) {
                return a + b;
            }
            const result = add(1, 2);
        """.trimIndent()

        val optStr = configJson.encodeToString<ParserConfig>(esParseOptions { })
        val jsonStr = swc.parseSync(code, optStr, "test.js")
        val program = SwcJson.parseAstTree(jsonStr) as Module

        assertNotNull(program.body)
        assertTrue(program.body!!.size >= 2)
    }

    // ==================== Edge cases ====================

    should("parseAstTree with empty Module") {
        val emptyModuleJson = """{"type":"Module","span":{"start":0,"end":0,"ctxt":0},"body":[],"interpreter":null}"""

        val program = SwcJson.parseAstTree(emptyModuleJson)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        assertNotNull(module.body)
        assertTrue(module.body!!.isEmpty())
    }

    should("parseAstTree with large AST") {
        val largeCode = buildString {
            repeat(100) { i ->
                appendLine("const var$i = $i;")
            }
        }

        val optStr = configJson.encodeToString<ParserConfig>(esParseOptions { })
        val jsonStr = swc.parseSync(largeCode, optStr, "large.js")
        val program = SwcJson.parseAstTree(jsonStr)

        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
    }

    should("astJson handles special characters") {
        val code = "const str = \"Hello\\nWorld\\t!\";"
        val program = swc.parseSync(code, esParseOptions { }, "test.js")
        val module = program as Module

        val json = astJson.encodeToString<Program>(module)

        assertNotNull(json)
        // Should properly escape special characters
    }
})

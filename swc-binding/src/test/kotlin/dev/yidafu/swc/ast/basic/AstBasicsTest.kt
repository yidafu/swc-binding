package dev.yidafu.swc.ast.basic

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.astJson
import dev.yidafu.swc.emptySpan
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import dev.yidafu.swc.generated.dsl.createIdentifier
import dev.yidafu.swc.generated.dsl.createModule
import dev.yidafu.swc.generated.dsl.createVariableDeclarator
import dev.yidafu.swc.span
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.encodeToString

class AstBasicsTest : ShouldSpec({
    val swc = SwcNative()

    should("create Span with span DSL") {
        val s = span()

        s.shouldNotBeNull()
        s.shouldBeInstanceOf<Span>()
    }

    should("create Span with emptySpan DSL") {
        val s = emptySpan()

        s.shouldNotBeNull()
        s.start shouldBe 0
        s.end shouldBe 0
        s.ctxt shouldBe 0
    }

    should("create Span with apply") {
        val s = span().apply {
            start = 10
            end = 20
            ctxt = 0
        }

        s.start shouldBe 10
        s.end shouldBe 20
        s.ctxt shouldBe 0
    }

    should("serialize Span") {
        val s = span().apply {
            start = 5
            end = 15
            ctxt = 2
        }

        val json = astJson.encodeToString(s)
        json.shouldNotBeNull()
    }

    should("deserialize Span") {
        val json = """{"start":10,"end":20,"ctxt":5}"""
        val s = astJson.decodeFromString<Span>(json)

        s.start shouldBe 10
        s.end shouldBe 20
        s.ctxt shouldBe 5
    }

    should("serialize Span includes ctxt field") {
        val s = span().apply {
            start = 5
            end = 15
            ctxt = 2
        }

        val json = astJson.encodeToString(s)

        // Verify JSON contains ctxt field
        json shouldBe """{"start":5,"end":15,"ctxt":2}"""
    }

    should("serialize Span with default ctxt includes ctxt field") {
        val s = span().apply {
            start = 1
            end = 10
            // ctxt uses default value 0
        }

        val json = astJson.encodeToString(s)

        // Verify JSON contains ctxt field (even with default value)
        json shouldBe """{"start":1,"end":10,"ctxt":0}"""
        json shouldContain "\"ctxt\""
        json shouldContain "\"ctxt\":0"
    }

    should("serialize Span with zero ctxt includes ctxt field") {
        val s = span().apply {
            start = 0
            end = 0
            ctxt = 0
        }

        val json = astJson.encodeToString(s)

        // Verify JSON contains ctxt field
        json shouldBe """{"start":0,"end":0,"ctxt":0}"""
        json shouldContain "\"ctxt\""
    }

    should("deserialize Span without ctxt field uses default") {
        // Test deserializing from JSON missing ctxt field
        val json = """{"start":10,"end":20}"""
        val s = astJson.decodeFromString<Span>(json)

        s.start shouldBe 10
        s.end shouldBe 20
        s.ctxt shouldBe 0 // Should use default value
    }

    should("serialize Identifier with nested Span includes ctxt field") {
        // Test if Span nested in AST node contains ctxt field
        val id = createIdentifier {
            value = "testVar"
            optional = false
            span = span().apply {
                start = 0
                end = 8
                ctxt = 0
            }
        }

        val json = astJson.encodeToString<Identifier>(id)

        // Verify nested Span in JSON contains ctxt field
        json shouldContain "\"ctxt\""
        json shouldContain "\"span\""
        // Verify span object contains ctxt
        val spanJson = json.substringAfter("\"span\":{").substringBefore("}")
        spanJson shouldContain "\"ctxt\""
    }

    should("serialize Module with nested Spans includes ctxt fields") {
        // Test complex AST node containing multiple nested Spans
        val module = swc.parseSync(
            "const x = 1;",
            createEsParserConfig { },
            "test.js"
        ) as Module

        val json = astJson.encodeToString<Program>(module)

        // Verify JSON contains ctxt field (should appear in all Span objects)
        json shouldContain "\"ctxt\""

        // Check if all "span" objects have "ctxt" field
        // Use more precise method: find all "span":{ and check corresponding objects
        var index = 0
        var spanCount = 0
        var ctxtCount = 0
        val jsonLower = json.lowercase()

        while (true) {
            val spanIndex = jsonLower.indexOf("\"span\":{", index)
            if (spanIndex == -1) break

            spanCount++
            // Find corresponding closing brace (need to handle nesting)
            var depth = 0
            var endIndex = spanIndex + 8 // Skip "span":{
            var found = false

            while (endIndex < json.length) {
                when (json[endIndex]) {
                    '{' -> depth++
                    '}' -> {
                        if (depth == 0) {
                            found = true
                            break
                        }
                        depth--
                    }
                }
                endIndex++
            }

            if (found) {
                val spanContent = json.substring(spanIndex, endIndex + 1)
                if (spanContent.contains("\"ctxt\"")) {
                    ctxtCount++
                } else {
                    // Print span object missing ctxt
                    println("Found span without ctxt: ${spanContent.take(150)}")
                }
            }

            index = spanIndex + 8
        }

        println("Total spans found: $spanCount, spans with ctxt: $ctxtCount")

        // All span objects should contain ctxt field
        if (spanCount > 0) {
            spanCount shouldBe ctxtCount
        }
    }

    should("printSync serialized JSON includes ctxt in all Spans") {
        // Test that all Spans in JSON serialized by printSync contain ctxt
        val module = swc.parseSync(
            "const x = 1; const y = 2;",
            createEsParserConfig { },
            "test.js"
        ) as Module

        // Simulate serialization process in printSync
        val programJson = astJson.encodeToString<Program>(module)
        val fixedProgram = astJson.decodeFromString<Program>(programJson)
        val finalJson = astJson.encodeToString<Program>(fixedProgram)

        // Check all span objects
        var index = 0
        var spanCount = 0
        var ctxtCount = 0
        val jsonLower = finalJson.lowercase()

        while (true) {
            val spanIndex = jsonLower.indexOf("\"span\":{", index)
            if (spanIndex == -1) break

            spanCount++
            // Find corresponding closing brace
            var depth = 0
            var endIndex = spanIndex + 8
            var found = false

            while (endIndex < finalJson.length) {
                when (finalJson[endIndex]) {
                    '{' -> depth++
                    '}' -> {
                        if (depth == 0) {
                            found = true
                            break
                        }
                        depth--
                    }
                }
                endIndex++
            }

            if (found) {
                val spanContent = finalJson.substring(spanIndex, endIndex + 1)
                if (spanContent.contains("\"ctxt\"")) {
                    ctxtCount++
                } else {
                    println("printSync: Found span without ctxt: ${spanContent.take(150)}")
                }
            }

            index = spanIndex + 8
        }

        println("printSync: Total spans: $spanCount, with ctxt: $ctxtCount")

        // All span objects should contain ctxt field
        if (spanCount > 0) {
            spanCount shouldBe ctxtCount
        }
    }

    should("create IdentifierImpl") {
        val id = createIdentifier {
            value = "testVar"
            optional = false
            span = emptySpan()
        }

        id.value shouldBe "testVar"
        id.optional shouldBe false
    }

    should("serialize Identifier") {
        val id = createIdentifier {
            value = "myVar"
            optional = false
            span = emptySpan()
        }

        val json = astJson.encodeToString<Identifier>(id)
        json.shouldNotBeNull()
    }

    should("deserialize Identifier") {
        val json = """{"type":"Identifier","value":"test","optional":false,"span":{"start":0,"end":0,"ctxt":0}}"""
        val id = astJson.decodeFromString<Identifier>(json)

        id.value shouldBe "test"
        id.optional shouldBe false
    }

    should("create StringLiteralImpl") {
        val lit = StringLiteral().apply {
            value = "hello"
            raw = "\"hello\""
            span = emptySpan()
        }

        lit.value shouldBe "hello"
        lit.raw shouldBe "\"hello\""
    }

    should("create NumericLiteralImpl") {
        val lit = NumericLiteral().apply {
            value = 42.0
            raw = "42"
            span = emptySpan()
        }

        lit.value shouldBe 42.0
        lit.raw shouldBe "42"
    }

    should("create BooleanLiteralImpl") {
        val lit = BooleanLiteral().apply {
            value = true
            span = emptySpan()
        }

        lit.value shouldBe true
    }

    should("create NullLiteralImpl") {
        val lit = NullLiteral().apply {
            span = emptySpan()
        }

        lit.shouldNotBeNull()
    }

    should("create ModuleImpl with apply") {
        val mod = Module().apply {
            span = emptySpan()
            body = arrayOf()
        }

        mod.shouldNotBeNull()
        mod.body.shouldNotBeNull()
        mod.body!!.size shouldBe 0
    }

    should("use module DSL") {
        val mod = module {
            span = emptySpan()
            body = arrayOf()
        }

        mod.shouldNotBeNull()
        mod.shouldBeInstanceOf<Module>()
    }

    should("serialize Module") {
        val mod = createModule {
            span = emptySpan()
            body = arrayOf()
        }

        val json = astJson.encodeToString<Module>(mod)
        json.shouldNotBeNull()
    }

    should("create VariableDeclaratorImpl") {
        val decl = createVariableDeclarator {
            span = emptySpan()
            id = createIdentifier {
                value = "x"
                span = emptySpan()
            }
            init = NumericLiteral().apply {
                value = 1.0
                raw = "1"
                span = emptySpan()
            }
        }

        decl.shouldNotBeNull()
        decl.id.shouldBeInstanceOf<Identifier>()
    }

    should("Span with same values") {
        val s1 = span().apply { start = 1; end = 2; ctxt = 0 }
        val s2 = span().apply { start = 1; end = 2; ctxt = 0 }

        s1.shouldNotBeNull()
        s2.shouldNotBeNull()
        s1.start shouldBe s2.start
        s1.end shouldBe s2.end
        s1.ctxt shouldBe s2.ctxt
    }
})

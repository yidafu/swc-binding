package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import dev.yidafu.swc.generated.dsl.createBindingIdentifier
import dev.yidafu.swc.generated.dsl.createIdentifier
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
/**
 * Basic AST node serialization tests
 * Split from AstSerializationTest
 */
class AstSerializationBasicTest : ShouldSpec({

    should("serialize and deserialize Identifier") {
        val identifier = createIdentifier {
            value = "test"
            optional = false
            span = span {
                start = 0
                end = 4
            }
        }

        val json = astJson.encodeToString(identifier)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"Identifier\""
        json shouldContain "\"value\":\"test\""

        val deserialized = astJson.decodeFromString<Identifier>(json)
        deserialized.value shouldBe "test"
        deserialized.optional shouldBe false
        deserialized.span.start shouldBe 0
        deserialized.span.end shouldBe 4
    }

    should("round-trip serialize Identifier") {
        val original = createIdentifier {
            value = "x"
            optional = true
            span = span {
                start = 10
                end = 11
                ctxt = 2
            }
        }

        val json1 = astJson.encodeToString(original)
        val deserialized = astJson.decodeFromString<Identifier>(json1)
        val json2 = astJson.encodeToString(deserialized)

        json1 shouldBe json2
    }

    should("serialize and deserialize StringLiteral") {
        val literal: Literal = createStringLiteral {
            value = "hello"
            raw = "\"hello\""
            span = span {
                start = 0
                end = 7
            }
        }

        val json = astJson.encodeToString(literal)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"StringLiteral\""
        json shouldContain "\"value\":\"hello\""
        json shouldContain "\"raw\":\"\\\"hello\\\"\""

        val deserialized = astJson.decodeFromString<Literal>(json)
        deserialized.shouldBeInstanceOf<StringLiteral>()
        (deserialized as StringLiteral).value shouldBe "hello"
        (deserialized as StringLiteral).raw shouldBe "\"hello\""
    }

    should("serialize and deserialize NumericLiteral") {
        val literal: Literal = createNumericLiteral {
            value = 42.0
            raw = "42"
            span = span {
                start = 0
                end = 2
            }
        }

        val json = astJson.encodeToString(literal)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"NumericLiteral\""
        
        val deserialized = astJson.decodeFromString<Literal>(json)
        deserialized.shouldBeInstanceOf<NumericLiteral>()
        (deserialized as NumericLiteral).value shouldBe 42.0
        (deserialized as NumericLiteral).raw shouldBe "42"
    }

    should("serialize and deserialize BooleanLiteral") {
        val literal: Literal = createBooleanLiteral {
            value = true
            span = emptySpan()
        }

        val json = astJson.encodeToString(literal)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"BooleanLiteral\""
        
        val deserialized = astJson.decodeFromString<Literal>(json)
        deserialized.shouldBeInstanceOf<BooleanLiteral>()
        (deserialized as BooleanLiteral).value shouldBe true
    }

    should("serialize and deserialize BindingIdentifier") {
        val bindingIdentifier = createBindingIdentifier {
            value = "param"
            optional = false
            span = span {
                start = 0
                end = 5
            }
        }

        val json = astJson.encodeToString(bindingIdentifier)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"Identifier\""
        json shouldContain "\"value\":\"param\""

        val deserialized = astJson.decodeFromString<BindingIdentifier>(json)
        deserialized.value shouldBe "param"
        deserialized.optional shouldBe false
        deserialized.span.start shouldBe 0
        deserialized.span.end shouldBe 5
    }

    should("round-trip serialize BindingIdentifier") {
        val original = createBindingIdentifier {
            value = "binding"
            optional = true
            span = span {
                start = 10
                end = 17
            }
        }

        val json1 = astJson.encodeToString(original)
        val deserialized = astJson.decodeFromString<BindingIdentifier>(json1)
        val json2 = astJson.encodeToString(deserialized)

        json1 shouldBe json2
        deserialized.value shouldBe "binding"
        deserialized.optional shouldBe true
    }

    should("deserialize from JSON string") {
        val json = """
            {
                "type": "Identifier",
                "value": "testId",
                "optional": false,
                "span": {
                    "start": 0,
                    "end": 6,
                    "ctxt": 0
                }
            }
        """.trimIndent()

        val identifier = astJson.decodeFromString<Identifier>(json)

        identifier.value shouldBe "testId"
        identifier.optional shouldBe false
        identifier.span.start shouldBe 0
        identifier.span.end shouldBe 6
    }

    should("serialize to JSON string") {
        val identifier = createIdentifier {
            value = "myVar"
            optional = true
            span = span {
                start = 10
                end = 15
                ctxt = 2
            }
        }

        val json = astJson.encodeToString(identifier)

        json.shouldNotBeNull()
        json shouldContain "\"type\":\"Identifier\""
        json shouldContain "\"value\":\"myVar\""
        json shouldContain "\"optional\":true"
        json shouldContain "\"span\""
    }

    should("compare Identifier and BindingIdentifier serialization") {
        val identifier = createIdentifier {
            value = "test"
            optional = false
            span = emptySpan()
        }

        val bindingIdentifier = createBindingIdentifier {
            value = "test"
            optional = false
            span = emptySpan()
        }

        val identifierJson = astJson.encodeToString(identifier)
        val bindingIdentifierJson = astJson.encodeToString(bindingIdentifier)

        identifierJson shouldContain "\"type\":\"Identifier\""
        bindingIdentifierJson shouldContain "\"type\":\"Identifier\""
        identifierJson shouldContain "\"value\":\"test\""
        bindingIdentifierJson shouldContain "\"value\":\"test\""

        val deserializedId = astJson.decodeFromString<Identifier>(identifierJson)
        val deserializedBinding = astJson.decodeFromString<BindingIdentifier>(bindingIdentifierJson)

        deserializedId.value shouldBe "test"
        deserializedBinding.value shouldBe "test"
    }
})

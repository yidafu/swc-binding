package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
/**
 * Declaration serialization tests
 * Split from AstSerializationTest
 */
class AstSerializationDeclarationTest : ShouldSpec({

    should("serialize and deserialize VariableDeclaration") {
        val decl: Declaration = createVariableDeclaration {
            kind = VariableDeclarationKind.VAR
            declarations = arrayOf(
                createVariableDeclarator {
                    id = createIdentifier {
                        value = "counter"
                        span = emptySpan()
                    }
                    init = createNumericLiteral {
                        value = 0.0
                        raw = "0"
                        span = emptySpan()
                    }
                }
            )
            span = emptySpan()
        }

        val json = astJson.encodeToString(decl)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"VariableDeclaration\""

        val deserialized = astJson.decodeFromString<Declaration>(json)
        deserialized.shouldBeInstanceOf<VariableDeclaration>()
        (deserialized as VariableDeclaration).kind shouldBe VariableDeclarationKind.VAR
        (deserialized as VariableDeclaration).declarations.shouldNotBeNull()
        (deserialized as VariableDeclaration).declarations!!.size shouldBe 1
    }

    should("serialize and deserialize ClassDeclaration") {
        val classDecl = createClassDeclaration {
            identifier = createIdentifier {
                value = "MyClass"
                span = emptySpan()
            }
            declare = false
            body = arrayOf()
            superClass = null
            isAbstract = false
            span = emptySpan()
        }

        val json = astJson.encodeToString(classDecl as Declaration)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"ClassDeclaration\""
        json shouldContain "\"identifier\""

        val deserialized = astJson.decodeFromString<Declaration>(json)
        deserialized.shouldBeInstanceOf<ClassDeclaration>()
        (deserialized as ClassDeclaration).identifier.shouldNotBeNull()
        (deserialized as ClassDeclaration).identifier!!.value shouldBe "MyClass"
    }

    should("serialize and deserialize FunctionDeclaration") {
        val funcDecl: Declaration = FunctionDeclaration().apply {
            identifier = createIdentifier {
                value = "myFunction"
                span = emptySpan()
            }
            declare = false
            params = arrayOf()
            body = null
            span = emptySpan()
        }

        val json = astJson.encodeToString(funcDecl)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"FunctionDeclaration\""

        val deserialized = astJson.decodeFromString<Declaration>(json)
        deserialized.shouldBeInstanceOf<FunctionDeclaration>()
        (deserialized as FunctionDeclaration).identifier.shouldNotBeNull()
        (deserialized as FunctionDeclaration).identifier!!.value shouldBe "myFunction"
    }
})

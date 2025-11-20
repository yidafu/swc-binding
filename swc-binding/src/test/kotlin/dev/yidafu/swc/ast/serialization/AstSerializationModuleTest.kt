package dev.yidafu.swc.ast.serialization

import dev.yidafu.swc.astJson
import dev.yidafu.swc.emptySpan
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
 * Module serialization tests
 * Split from AstSerializationTest
 */
class AstSerializationModuleTest : ShouldSpec({

    should("serialize and deserialize Module") {
        val module = createModule {
            body = arrayOf(
                createVariableDeclaration {
                    kind = VariableDeclarationKind.CONST
                    declarations = arrayOf(
                        createVariableDeclarator {
                            id = createIdentifier {
                                value = "x"
                                span = emptySpan()
                            }
                            init = createNumericLiteral {
                                value = 1.0
                                raw = "1"
                                span = emptySpan()
                            }
                        }
                    )
                    span = emptySpan()
                }
            )
            span = emptySpan()
        }

        val json = astJson.encodeToString(module as Program)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"Module\""

        val deserialized = astJson.decodeFromString<Program>(json) as Module
        deserialized.body.shouldNotBeNull()
        deserialized.body!!.size shouldBe 1
        deserialized.body!![0].shouldBeInstanceOf<VariableDeclaration>()
    }

    should("round-trip serialize Module") {
        val original = createModule {
            body = arrayOf(
                createVariableDeclaration {
                    kind = VariableDeclarationKind.LET
                    declarations = arrayOf(
                        createVariableDeclarator {
                            id = createIdentifier {
                                value = "y"
                                span = emptySpan()
                            }
                            init = createStringLiteral {
                                value = "test"
                                raw = "\"test\""
                                span = emptySpan()
                            }
                        }
                    )
                    span = emptySpan()
                }
            )
            span = emptySpan()
        }

        val json1 = astJson.encodeToString(original)
        val deserialized = astJson.decodeFromString<Module>(json1)
        val json2 = astJson.encodeToString(deserialized)

        json1 shouldBe json2
    }

    should("serialize and deserialize ExportDeclaration") {
        val exportDecl = ExportDeclaration().apply {
            declaration = createVariableDeclaration {
                kind = VariableDeclarationKind.CONST
                declarations = arrayOf(
                    createVariableDeclarator {
                        id = createIdentifier {
                            value = "exported"
                            span = emptySpan()
                        }
                        init = null
                    }
                )
                span = emptySpan()
            }
            span = emptySpan()
        }

        val json = astJson.encodeToString(exportDecl as ModuleDeclaration)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"ExportDeclaration\""

        val deserialized = astJson.decodeFromString<ModuleDeclaration>(json)
        deserialized.shouldBeInstanceOf<ExportDeclaration>()
        deserialized.declaration.shouldNotBeNull()
        deserialized.declaration!!.shouldBeInstanceOf<VariableDeclaration>()
    }

    should("serialize and deserialize ImportDeclaration") {
        val importDecl: ModuleDeclaration = createImportDeclaration {
            specifiers = arrayOf(
                createImportDefaultSpecifier {
                    local = createIdentifier {
                        value = "React"
                        span = emptySpan()
                    }
                    span = emptySpan()
                }
            )
            source = createStringLiteral {
                value = "react"
                raw = "\"react\""
                span = emptySpan()
            }
            typeOnly = false
            span = emptySpan()
        }

        val json = astJson.encodeToString(importDecl)
        json.shouldNotBeNull()
        json shouldContain "\"type\":\"ImportDeclaration\""

        val deserialized = astJson.decodeFromString<ModuleDeclaration>(json)
        deserialized.shouldBeInstanceOf<ImportDeclaration>()
        (deserialized as ImportDeclaration).specifiers.shouldNotBeNull()
        (deserialized as ImportDeclaration).specifiers!!.size shouldBe 1
        (deserialized as ImportDeclaration).source.shouldNotBeNull()
        ((deserialized as ImportDeclaration).source as StringLiteral).value shouldBe "react"
    }
})

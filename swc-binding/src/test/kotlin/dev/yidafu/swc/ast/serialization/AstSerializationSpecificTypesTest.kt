package dev.yidafu.swc.ast.serialization

import dev.yidafu.swc.astJson
import dev.yidafu.swc.emptySpan
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.*
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

/**
 * Serialization and deserialization tests for specific AST node types
 * Including: VariableDeclarator, Param, BlockStatement, JSXOpeningElement, JSXClosingElement
 */
class AstSerializationSpecificTypesTest : ShouldSpec({

    context("VariableDeclarator serialization and deserialization") {
        should("serialize and deserialize VariableDeclarator") {
            val declarator = createVariableDeclarator {
                id = createIdentifier {
                    value = "x"
                    span = emptySpan()
                }
                init = createNumericLiteral {
                    value = 42.0
                    raw = "42"
                    span = emptySpan()
                }
                definite = false
                span = emptySpan()
            }

            val json = astJson.encodeToString(declarator)
            json.shouldNotBeNull()
            json shouldContain "\"type\":\"VariableDeclarator\""

            val deserialized = astJson.decodeFromString<VariableDeclarator>(json)
            deserialized.id.shouldNotBeNull()
            deserialized.id!!.shouldBeInstanceOf<Identifier>()
            (deserialized.id as Identifier).value shouldBe "x"
            deserialized.init.shouldNotBeNull()
            deserialized.init!!.shouldBeInstanceOf<NumericLiteral>()
            (deserialized.init as NumericLiteral).value shouldBe 42.0
            deserialized.definite shouldBe false
        }

        should("VariableDeclarator round-trip serialization") {
            val original = createVariableDeclarator {
                id = createIdentifier {
                    value = "test"
                    span = emptySpan()
                }
                init = createStringLiteral {
                    value = "hello"
                    raw = "\"hello\""
                    span = emptySpan()
                }
                definite = true
                span = emptySpan()
            }

            val json1 = astJson.encodeToString(original)
            val deserialized = astJson.decodeFromString<VariableDeclarator>(json1)
            val json2 = astJson.encodeToString(deserialized)

            json1 shouldBe json2
            deserialized.definite shouldBe true
        }

        should("serialize and deserialize VariableDeclarator (without init)") {
            val declarator = createVariableDeclarator {
                id = createIdentifier {
                    value = "y"
                    span = emptySpan()
                }
                init = null
                definite = null
                span = emptySpan()
            }

            val json = astJson.encodeToString(declarator)
            val deserialized = astJson.decodeFromString<VariableDeclarator>(json)

            deserialized.id.shouldNotBeNull()
            (deserialized.id as Identifier).value shouldBe "y"
            deserialized.init.shouldBeNull()
        }
    }

    context("Param serialization and deserialization") {
        should("serialize and deserialize Param") {
            val param = createParam {
                pat = createIdentifier {
                    value = "param"
                    span = emptySpan()
                }
                decorators = null
                span = emptySpan()
            }

            val json = astJson.encodeToString(param)
            json.shouldNotBeNull()
            json shouldContain "\"type\":\"Parameter\""

            val deserialized = astJson.decodeFromString<Param>(json)
            deserialized.pat.shouldNotBeNull()
            deserialized.pat!!.shouldBeInstanceOf<Identifier>()
            (deserialized.pat as Identifier).value shouldBe "param"
            deserialized.decorators.shouldBeNull()
        }

        should("Param round-trip serialization") {
            val original = createParam {
                pat = createBindingIdentifier {
                    value = "bindingParam"
                    span = emptySpan()
                }
                decorators = null
                span = emptySpan()
            }

            val json1 = astJson.encodeToString(original)
            val deserialized = astJson.decodeFromString<Param>(json1)
            val json2 = astJson.encodeToString(deserialized)

            json1 shouldBe json2
            deserialized.pat.shouldNotBeNull()
            (deserialized.pat as BindingIdentifier).value shouldBe "bindingParam"
        }

        should("serialize and deserialize Param with decorators") {
            val param = createParam {
                pat = createIdentifier {
                    value = "decoratedParam"
                    span = emptySpan()
                }
                decorators = arrayOf(
                    createDecorator {
                        expression = createCallExpression {
                            callee = createIdentifier {
                                value = "decorator"
                                span = emptySpan()
                            }
                            arguments = arrayOf()
                            span = emptySpan()
                        }
                        span = emptySpan()
                    }
                )
                span = emptySpan()
            }

            val json = astJson.encodeToString(param)
            val deserialized = astJson.decodeFromString<Param>(json)

            deserialized.decorators.shouldNotBeNull()
            deserialized.decorators!!.size shouldBe 1
        }
    }

    context("BlockStatement serialization and deserialization") {
        should("serialize and deserialize BlockStatement") {
            val blockStmt = createBlockStatement {
                stmts = arrayOf(
                    createExpressionStatement {
                        expression = createStringLiteral {
                            value = "test"
                            raw = "\"test\""
                            span = emptySpan()
                        }
                        span = emptySpan()
                    }
                )
                span = emptySpan()
                ctxt = 0
            }

            val json = astJson.encodeToString(blockStmt)
            json.shouldNotBeNull()
            json shouldContain "\"type\":\"BlockStatement\""

            val deserialized = astJson.decodeFromString<BlockStatement>(json)
            deserialized.stmts.shouldNotBeNull()
            deserialized.stmts!!.size shouldBe 1
            deserialized.stmts!![0].shouldBeInstanceOf<ExpressionStatement>()
            deserialized.ctxt shouldBe 0
        }

        should("BlockStatement round-trip serialization") {
            val original = createBlockStatement {
                stmts = arrayOf(
                    createReturnStatement {
                        argument = createNumericLiteral {
                            value = 100.0
                            raw = "100"
                            span = emptySpan()
                        }
                        span = emptySpan()
                    }
                )
                span = emptySpan()
                ctxt = 1
            }

            val json1 = astJson.encodeToString(original)
            val deserialized = astJson.decodeFromString<BlockStatement>(json1)
            val json2 = astJson.encodeToString(deserialized)

            json1 shouldBe json2
            deserialized.ctxt shouldBe 1
            deserialized.stmts!![0].shouldBeInstanceOf<ReturnStatement>()
        }

        should("serialize and deserialize empty BlockStatement") {
            val blockStmt = createBlockStatement {
                stmts = arrayOf()
                span = emptySpan()
                ctxt = 0
            }

            val json = astJson.encodeToString(blockStmt)
            val deserialized = astJson.decodeFromString<BlockStatement>(json)

            deserialized.stmts.shouldNotBeNull()
            deserialized.stmts!!.size shouldBe 0
        }
    }

    context("JSXOpeningElement serialization and deserialization") {
        should("serialize and deserialize JSXOpeningElement") {
            val openingElement = createJSXOpeningElement {
                name = createIdentifier {
                    value = "div"
                    span = emptySpan()
                }
                attributes = null
                selfClosing = false
                typeArguments = null
                span = emptySpan()
            }

            val json = astJson.encodeToString(openingElement)
            json.shouldNotBeNull()
            json shouldContain "\"type\":\"JSXOpeningElement\""

            val deserialized = astJson.decodeFromString<JSXOpeningElement>(json)
            deserialized.name.shouldNotBeNull()
            deserialized.name!!.shouldBeInstanceOf<Identifier>()
            (deserialized.name as Identifier).value shouldBe "div"
            deserialized.selfClosing shouldBe false
        }

        should("JSXOpeningElement round-trip serialization") {
            val original = createJSXOpeningElement {
                name = createIdentifier {
                    value = "button"
                    span = emptySpan()
                }
                attributes = arrayOf(
                    createJSXAttribute {
                        name = identifier {
                            value = "className"
                            span = emptySpan()
                        }
                        value = stringLiteral {
                            value = "btn"
                            raw = "\"btn\""
                            span = emptySpan()
                        }
                        span = emptySpan()
                    }
                )
                selfClosing = true
                typeArguments = null
                span = emptySpan()
            }

            val json1 = astJson.encodeToString(original)
            val deserialized = astJson.decodeFromString<JSXOpeningElement>(json1)
            val json2 = astJson.encodeToString(deserialized)

            json1 shouldBe json2
            deserialized.selfClosing shouldBe true
            deserialized.attributes.shouldNotBeNull()
            deserialized.attributes!!.size shouldBe 1
        }

        should("serialize and deserialize JSXOpeningElement with attributes") {
            val openingElement = createJSXOpeningElement {
                name = createJSXMemberExpression {
                    `object` = createIdentifier {
                        value = "React"
                        span = emptySpan()
                    }
                    property = createIdentifier {
                        value = "Component"
                        span = emptySpan()
                    }
                }
                attributes = arrayOf()
                selfClosing = false
                typeArguments = null
                span = emptySpan()
            }

            val json = astJson.encodeToString(openingElement)
            val deserialized = astJson.decodeFromString<JSXOpeningElement>(json)

            deserialized.name.shouldNotBeNull()
            deserialized.name!!.shouldBeInstanceOf<JSXMemberExpression>()
        }
    }

    context("JSXClosingElement serialization and deserialization") {
        should("serialize and deserialize JSXClosingElement") {
            val closingElement = createJSXClosingElement {
                name = createIdentifier {
                    value = "div"
                    span = emptySpan()
                }
                span = emptySpan()
            }

            val json = astJson.encodeToString(closingElement)
            json.shouldNotBeNull()
            json shouldContain "\"type\":\"JSXClosingElement\""

            val deserialized = astJson.decodeFromString<JSXClosingElement>(json)
            deserialized.name.shouldNotBeNull()
            deserialized.name!!.shouldBeInstanceOf<Identifier>()
            (deserialized.name as Identifier).value shouldBe "div"
        }

        should("JSXClosingElement round-trip serialization") {
            val original = createJSXClosingElement {
                name = createIdentifier {
                    value = "section"
                    span = emptySpan()
                }
                span = emptySpan()
            }

            val json1 = astJson.encodeToString(original)
            val deserialized = astJson.decodeFromString<JSXClosingElement>(json1)
            val json2 = astJson.encodeToString(deserialized)

            json1 shouldBe json2
            (deserialized.name as Identifier).value shouldBe "section"
        }

        should("serialize and deserialize JSXClosingElement with namespace") {
            val closingElement = createJSXClosingElement {
                name = createJSXNamespacedName {
                    namespace = createIdentifier {
                        value = "ns"
                        span = emptySpan()
                    }
                    name = createIdentifier {
                        value = "element"
                        span = emptySpan()
                    }
                }
                span = emptySpan()
            }

            val json = astJson.encodeToString(closingElement)
            val deserialized = astJson.decodeFromString<JSXClosingElement>(json)

            deserialized.name.shouldNotBeNull()
            deserialized.name!!.shouldBeInstanceOf<JSXNamespacedName>()
            (deserialized.name as JSXNamespacedName).name.shouldNotBeNull()
            ((deserialized.name as JSXNamespacedName).name as Identifier).value shouldBe "element"
        }
    }
})

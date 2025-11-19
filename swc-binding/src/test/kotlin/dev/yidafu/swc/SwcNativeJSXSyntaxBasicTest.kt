package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Basic JSX syntax parsing tests
 * Split from SwcNativeJSXSyntaxTest
 */
class SwcNativeJSXSyntaxBasicTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse JSX element with attributes") {
        val output = swcNative.parseSync(
            """
            function App() {
                return <div className="container" id="main">Hello</div>;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val returnStmt = body?.stmts?.get(0)?.shouldBeInstanceOf<ReturnStatement>()
            val jsxElement = returnStmt?.argument?.shouldBeInstanceOf<JSXElement>()
            assertNotNull(jsxElement)
            assertNotNull(jsxElement?.opening)
            assertNotNull(jsxElement?.opening?.attributes)
        }
    }

    should("parse JSX element with multiple attributes") {
        val output = swcNative.parseSync(
            """
            <div 
                className="container" 
                id="main" 
                data-testid="test"
                aria-label="Main container"
            >
                Content
            </div>
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val jsxElement = exprStmt.expression.shouldBeInstanceOf<JSXElement>()
            assertNotNull(jsxElement.opening?.attributes)
            assertTrue(jsxElement.opening?.attributes!!.size >= 4)
        }
    }

    should("parse JSX element with boolean attributes") {
        val output = swcNative.parseSync(
            """
            <input type="text" disabled readonly required />
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse JSX expression container") {
        val output = swcNative.parseSync(
            """
            function App() {
                const name = "World";
                return <div>Hello, {name}!</div>;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val returnStmt = body?.stmts?.get(1)?.shouldBeInstanceOf<ReturnStatement>()
            val jsxElement = returnStmt?.argument?.shouldBeInstanceOf<JSXElement>()
            assertNotNull(jsxElement)
            assertNotNull(jsxElement.children)
            val exprContainer = jsxElement.children?.get(1)?.shouldBeInstanceOf<JSXExpressionContainer>()
            assertNotNull(exprContainer)
        }
    }

    should("parse JSX expression container with complex expression") {
        val output = swcNative.parseSync(
            """
            function App() {
                const count = 5;
                return <div>Count: {count * 2 + 1}</div>;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse JSX expression container with function call") {
        val output = swcNative.parseSync(
            """
            function App() {
                return <div>{formatDate(new Date())}</div>;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse JSX expression container with conditional") {
        val output = swcNative.parseSync(
            """
            function App() {
                const isLoggedIn = true;
                return <div>{isLoggedIn ? "Welcome" : "Please login"}</div>;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse JSX Fragment") {
        val output = swcNative.parseSync(
            """
            function App() {
                return <>Hello World</>;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val returnStmt = body?.stmts?.get(0)?.shouldBeInstanceOf<ReturnStatement>()
            val jsxFragment = returnStmt?.argument?.shouldBeInstanceOf<JSXFragment>()
            assertNotNull(jsxFragment)
        }
    }

    should("parse JSX Fragment with multiple children") {
        val output = swcNative.parseSync(
            """
            function App() {
                return (
                    <>
                        <h1>Title</h1>
                        <p>Content</p>
                        <footer>Footer</footer>
                    </>
                );
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val returnStmt = body?.stmts?.get(0)?.shouldBeInstanceOf<ReturnStatement>()
            val parenExpr = returnStmt?.argument?.shouldBeInstanceOf<ParenthesisExpression>()
            val jsxFragment = parenExpr?.expression?.shouldBeInstanceOf<JSXFragment>()
            assertNotNull(jsxFragment)
            assertNotNull(jsxFragment?.children)
            assertTrue(jsxFragment?.children!!.size >= 3)
        }
    }

    should("parse JSX with event handler attributes") {
        val output = swcNative.parseSync(
            """
            function App() {
                return (
                    <button onClick={() => console.log("clicked")}>
                        Click me
                    </button>
                );
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val returnStmt = body?.stmts?.get(0)?.shouldBeInstanceOf<ReturnStatement>()
            val parenExpr = returnStmt?.argument?.shouldBeInstanceOf<ParenthesisExpression>()
            val jsxElement = parenExpr?.expression?.shouldBeInstanceOf<JSXElement>()
            assertNotNull(jsxElement)
            assertNotNull(jsxElement?.opening?.attributes)
        }
    }

    should("parse JSX with multiple event handlers") {
        val output = swcNative.parseSync(
            """
            function App() {
                return (
                    <div
                        onClick={() => {}}
                        onMouseEnter={() => {}}
                        onMouseLeave={() => {}}
                        onSubmit={(e) => e.preventDefault()}
                    >
                        Content
                    </div>
                );
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse JSX text content") {
        val output = swcNative.parseSync(
            """
            function App() {
                return <div>Plain text content</div>;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val returnStmt = body?.stmts?.get(0)?.shouldBeInstanceOf<ReturnStatement>()
            val jsxElement = returnStmt?.argument?.shouldBeInstanceOf<JSXElement>()
            assertNotNull(jsxElement)
            assertNotNull(jsxElement.children)
            val jsxText = jsxElement.children?.get(0)?.shouldBeInstanceOf<JSXText>()
            assertNotNull(jsxText)
        }
    }

    should("parse JSX with mixed text and elements") {
        val output = swcNative.parseSync(
            """
            function App() {
                return (
                    <div>
                        Text before
                        <span>Span content</span>
                        Text after
                    </div>
                );
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
    }
})

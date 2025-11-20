package dev.yidafu.swc.swcnative.syntax.jsx

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Advanced JSX syntax parsing tests
 * Split from SwcNativeJSXSyntaxTest
 */
class SwcNativeJSXSyntaxAdvancedTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse JSX with conditional rendering") {
        val output = swcNative.parseSync(
            """
            function App() {
                const show = true;
                return (
                    <div>
                        {show && <p>Visible</p>}
                        {show ? <span>Yes</span> : <span>No</span>}
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

    should("parse JSX with complex conditional rendering") {
        val output = swcNative.parseSync(
            """
            function App() {
                const user = { name: "John", isAdmin: true };
                return (
                    <div>
                        {user && (
                            <div>
                                <p>Welcome, {user.name}</p>
                                {user.isAdmin && <button>Admin Panel</button>}
                            </div>
                        )}
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

    should("parse JSX with list rendering") {
        val output = swcNative.parseSync(
            """
            function App() {
                const items = [1, 2, 3];
                return (
                    <ul>
                        {items.map(item => <li key={item}>{item}</li>)}
                    </ul>
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

    should("parse JSX with nested list rendering") {
        val output = swcNative.parseSync(
            """
            function App() {
                const data = [
                    { id: 1, name: "Item 1", tags: ["a", "b"] },
                    { id: 2, name: "Item 2", tags: ["c", "d"] }
                ];
                return (
                    <div>
                        {data.map(item => (
                            <div key={item.id}>
                                <h3>{item.name}</h3>
                                <ul>
                                    {item.tags.map(tag => <li key={tag}>{tag}</li>)}
                                </ul>
                            </div>
                        ))}
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

    should("parse JSX with custom component") {
        val output = swcNative.parseSync(
            """
            function App() {
                return <MyComponent prop1="value1" prop2={42} />;
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
            val name = jsxElement?.opening?.name?.shouldBeInstanceOf<Identifier>()
            assertNotNull(name)
            assertEquals("MyComponent", name?.value)
        }
    }

    should("parse JSX with nested custom components") {
        val output = swcNative.parseSync(
            """
            function App() {
                return (
                    <Container>
                        <Header title="My App" />
                        <Main>
                            <Sidebar />
                            <Content />
                        </Main>
                        <Footer />
                    </Container>
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

    should("parse JSX member expression") {
        val output = swcNative.parseSync(
            """
            function App() {
                return <MyComponent.SubComponent prop="value" />;
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
            val memberExpr = jsxElement?.opening?.name?.shouldBeInstanceOf<JSXMemberExpression>()
            assertNotNull(memberExpr)
            assertNotNull(memberExpr?.`object`)
            assertNotNull(memberExpr?.`property`)
        }
    }

    should("parse JSX nested member expression") {
        val output = swcNative.parseSync(
            """
            function App() {
                return <A.B.C prop="value" />;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse JSX namespaced name") {
        val output = swcNative.parseSync(
            """
            function App() {
                return <foo:bar prop="value" />;
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
            val namespacedName = jsxElement?.opening?.name?.shouldBeInstanceOf<JSXNamespacedName>()
            assertNotNull(namespacedName)
            assertNotNull(namespacedName?.namespace)
            assertNotNull(namespacedName?.name)
        }
    }

    should("parse JSX with spread attributes") {
        val output = swcNative.parseSync(
            """
            function App() {
                const props = { className: "container", id: "main" };
                return <div {...props}>Content</div>;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse JSX with spread children") {
        val output = swcNative.parseSync(
            """
            function App() {
                const children = [<p>1</p>, <p>2</p>, <p>3</p>];
                return <div>{...children}</div>;
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

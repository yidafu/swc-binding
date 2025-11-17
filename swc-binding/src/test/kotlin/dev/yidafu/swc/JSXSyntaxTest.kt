package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import kotlin.test.assertEquals
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import kotlin.test.assertIs
import kotlin.test.assertTrue
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test

class JSXSyntaxTest : AnnotationSpec() {
    private val swcNative = SwcNative()

    // ==================== JSX Element and Attributes Tests ====================

    @Test
    fun `parse JSX element with attributes`() {
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

    @Test
    fun `parse JSX element with multiple attributes`() {
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

    @Test
    fun `parse JSX element with boolean attributes`() {
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

    // ==================== JSX Expression Container Tests ====================

    @Test
    fun `parse JSX expression container`() {
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

    @Test
    fun `parse JSX expression container with complex expression`() {
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

    @Test
    fun `parse JSX expression container with function call`() {
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

    @Test
    fun `parse JSX expression container with conditional`() {
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

    // ==================== JSX Fragment Tests ====================

    @Test
    fun `parse JSX Fragment`() {
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

    @Test
    fun `parse JSX Fragment with multiple children`() {
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
            val jsxFragment = returnStmt?.argument?.shouldBeInstanceOf<JSXFragment>()
            assertNotNull(jsxFragment)
            assertNotNull(jsxFragment.children)
            assertTrue(jsxFragment.children!!.size >= 3)
        }
    }

    // ==================== Event Handler Attributes Tests ====================

    @Test
    fun `parse JSX with event handler attributes`() {
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
            val jsxElement = returnStmt?.argument?.shouldBeInstanceOf<JSXElement>()
            assertNotNull(jsxElement)
            assertNotNull(jsxElement?.opening?.attributes)
        }
    }

    @Test
    fun `parse JSX with multiple event handlers`() {
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

    // ==================== Conditional Rendering Tests ====================

    @Test
    fun `parse JSX with conditional rendering`() {
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

    @Test
    fun `parse JSX with complex conditional rendering`() {
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

    // ==================== List Rendering Tests ====================

    @Test
    fun `parse JSX with list rendering`() {
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

    @Test
    fun `parse JSX with nested list rendering`() {
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

    // ==================== Custom Component Tests ====================

    @Test
    fun `parse JSX with custom component`() {
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

    @Test
    fun `parse JSX with nested custom components`() {
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

    // ==================== JSX Member Expression Tests ====================

    @Test
    fun `parse JSX member expression`() {
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

    @Test
    fun `parse JSX nested member expression`() {
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

    // ==================== JSX Namespaced Name Tests ====================

    @Test
    fun `parse JSX namespaced name`() {
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

    // ==================== TSX Tests (TypeScript JSX) ====================

    @Test
    fun `parse TSX with typed component`() {
        val output = swcNative.parseSync(
            """
            interface Props {
                name: string;
                age: number;
            }
            
            function Greeting(props: Props) {
                return <div>Hello, {props.name}! You are {props.age} years old.</div>;
            }
            """.trimIndent(),
            tsParseOptions {
                tsx = true
            },
            "test.tsx"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val interfaceDecl = items[0].shouldBeInstanceOf<TsInterfaceDeclaration>()
            assertNotNull(interfaceDecl.id)
            val funcDecl = items[1].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl)
        }
    }

    @Test
    fun `parse TSX with generic component`() {
        val output = swcNative.parseSync(
            """
            interface ListProps<T> {
                items: T[];
                renderItem: (item: T) => React.ReactNode;
            }
            
            function List<T>(props: ListProps<T>) {
                return (
                    <ul>
                        {props.items.map((item, index) => (
                            <li key={index}>{props.renderItem(item)}</li>
                        ))}
                    </ul>
                );
            }
            """.trimIndent(),
            tsParseOptions {
                tsx = true
            },
            "test.tsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TSX with typed JSX attributes`() {
        val output = swcNative.parseSync(
            """
            interface ButtonProps {
                onClick: (event: React.MouseEvent<HTMLButtonElement>) => void;
                disabled?: boolean;
                children: React.ReactNode;
            }
            
            function Button(props: ButtonProps) {
                return (
                    <button onClick={props.onClick} disabled={props.disabled}>
                        {props.children}
                    </button>
                );
            }
            """.trimIndent(),
            tsParseOptions {
                tsx = true
            },
            "test.tsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TSX with type annotations in JSX`() {
        val output = swcNative.parseSync(
            """
            function App() {
                const count: number = 42;
                const message: string = "Hello";
                return (
                    <div>
                        <p>{count}</p>
                        <p>{message}</p>
                    </div>
                );
            }
            """.trimIndent(),
            tsParseOptions {
                tsx = true
            },
            "test.tsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TSX with React FC type`() {
        val output = swcNative.parseSync(
            """
            interface Props {
                title: string;
            }
            
            const Component: React.FC<Props> = ({ title }) => {
                return <h1>{title}</h1>;
            };
            """.trimIndent(),
            tsParseOptions {
                tsx = true
            },
            "test.tsx"
        )
        output.shouldBeInstanceOf<Module>()
    }

    // ==================== JSX Spread Tests ====================

    @Test
    fun `parse JSX with spread attributes`() {
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

    @Test
    fun `parse JSX with spread children`() {
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

    // ==================== JSX Text Tests ====================

    @Test
    fun `parse JSX text content`() {
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

    @Test
    fun `parse JSX with mixed text and elements`() {
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

    // ==================== Transform JSX Tests ====================

    @Test
    fun `transform JSX to JavaScript`() {
        val output = swcNative.transformSync(
            """
            function App() {
                return <div>Hello World</div>;
            }
            """.trimIndent(),
            false,
            options {
                jsc = jscConfig {
                    parser = esParseOptions {
                        jsx = true
                    }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("<"), "Transformed code should not contain JSX syntax")
    }

    @Test
    fun `transform TSX to JavaScript`() {
        val output = swcNative.transformSync(
            """
            interface Props {
                name: string;
            }
            
            function Greeting(props: Props) {
                return <div>Hello, {props.name}!</div>;
            }
            """.trimIndent(),
            false,
            options {
                jsc = jscConfig {
                    parser = tsParseOptions {
                        tsx = true
                    }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("interface"), "Transformed code should not contain TypeScript syntax")
        assertTrue(!output.code.contains("<"), "Transformed code should not contain JSX syntax")
    }
}


package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Tests for TSX (TypeScript JSX) syntax parsing
 * Covers TypeScript features combined with JSX syntax
 */
class SwcNativeTSXSyntaxTest : AnnotationSpec() {
    private val swcNative = SwcNative()

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
}


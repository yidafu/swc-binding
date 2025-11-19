package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertIs
import kotlin.test.assertNotNull

/**
 * Basic TypeScript features tests
 * Split from SwcNativeTypeScriptTest
 */
class SwcNativeTypeScriptBasicTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse TypeScript type annotations") {
        val output = swcNative.parseSync(
            """
            const x: number = 42;
            const y: string = "hello";
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        assertIs<Module>(output)
    }

    should("parse TypeScript type annotation on parameters and returns") {
        val output = swcNative.parseSync(
            """
            function add(a: number, b: number): number {
                return a + b;
            }
            
            const arrow: (x: number) => string = (x) => x.toString();
            
            class MyClass {
                method(param: string): boolean {
                    return true;
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript interface") {
        val output = swcNative.parseSync(
            """
            interface User {
                name: string;
                age: number;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        assertIs<Module>(output)
    }

    should("parse TypeScript interface with extends") {
        val output = swcNative.parseSync(
            """
            interface A {
                a: number;
            }
            
            interface B extends A {
                b: string;
            }
            
            interface C extends A, B {
                c: boolean;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val interfaceDecl = items[1].shouldBeInstanceOf<TsInterfaceDeclaration>()
            assertNotNull(interfaceDecl.extends)
        }
    }

    should("parse TypeScript interface with generic parameters") {
        val output = swcNative.parseSync(
            """
            interface Container<T> {
                value: T;
                setValue(value: T): void;
                getValue(): T;
            }
            
            interface Pair<T, U> {
                first: T;
                second: U;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val interfaceDecl = items[0].shouldBeInstanceOf<TsInterfaceDeclaration>()
            assertNotNull(interfaceDecl.typeParams)
        }
    }

    should("parse TypeScript optional properties") {
        val output = swcNative.parseSync(
            """
            interface User {
                name: string;
                age?: number;
                email?: string;
            }
            
            type Optional<T> = {
                [K in keyof T]?: T[K];
            };
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript readonly modifier") {
        val output = swcNative.parseSync(
            """
            type ReadonlyArray<T> = readonly T[];
            interface ReadonlyProps {
                readonly name: string;
                readonly age: number;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript enum") {
        val output = swcNative.parseSync(
            """
            enum Color {
                Red,
                Green,
                Blue
            }
            
            enum Status {
                Active = "active",
                Inactive = "inactive",
                Pending = "pending"
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val enumDecl = items[0].shouldBeInstanceOf<TsEnumDeclaration>()
            assertNotNull(enumDecl.id)
            assertNotNull(enumDecl.members)
        }
    }
})

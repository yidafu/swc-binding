
package dev.yidafu.swc.swcnative.syntax.typescript

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull

/**
 * TypeScript advanced features tests (const assertion, satisfies, type guards, etc.)
 * Split from SwcNativeTypeScriptTest
 */
class SwcNativeTypeScriptFeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse TypeScript const assertion") {
        val output = swcNative.parseSync(
            """
            const array = [1, 2, 3] as const;
            const obj = { a: 1, b: 2 } as const;
            const tuple = [1, "hello"] as const;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            // "as const" is parsed as TsConstAssertion, not TsAsExpression
            val constAssertion = declarator.init?.shouldBeInstanceOf<TsConstAssertion>()
            assertNotNull(constAssertion)
        }
    }

    should("parse TypeScript satisfies expression") {
        val output = swcNative.parseSync(
            """
            const colors = {
                red: "#ff0000",
                green: "#00ff00",
                blue: "#0000ff"
            } satisfies Record<string, string>;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val satisfiesExpr = declarator.init?.shouldBeInstanceOf<TsSatisfiesExpression>()
            assertNotNull(satisfiesExpr)
        }
    }

    should("parse TypeScript type predicates") {
        val output = swcNative.parseSync(
            """
            function isString(value: unknown): value is string {
                return typeof value === "string";
            }
            
            function isNumber(value: any): value is number {
                return typeof value === "number";
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl)
        }
    }

    should("parse TypeScript type guards") {
        val output = swcNative.parseSync(
            """
            function isString(value: unknown): value is string {
                return typeof value === "string";
            }
            
            function isNumber(value: any): value is number {
                return typeof value === "number";
            }
            
            function process(value: unknown) {
                if (isString(value)) {
                    return value.toUpperCase();
                }
                if (isNumber(value)) {
                    return value * 2;
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript assertion functions") {
        val output = swcNative.parseSync(
            """
            function assertIsString(value: unknown): asserts value is string {
                if (typeof value !== "string") {
                    throw new Error("Not a string");
                }
            }
            
            function process(value: unknown) {
                assertIsString(value);
                return value.toUpperCase();
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript branded types") {
        val output = swcNative.parseSync(
            """
            type UserId = string & { __brand: "UserId" };
            type ProductId = string & { __brand: "ProductId" };
            
            function getUser(id: UserId) {
                return { id, name: "User" };
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript function overloads") {
        val output = swcNative.parseSync(
            """
            function process(value: string): string;
            function process(value: number): number;
            function process(value: string | number): string | number {
                return value;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript abstract class") {
        val output = swcNative.parseSync(
            """
            abstract class Animal {
                abstract makeSound(): void;
                move(): void {
                    console.log("Moving");
                }
            }
            
            class Dog extends Animal {
                makeSound(): void {
                    console.log("Woof");
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }
})

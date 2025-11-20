package dev.yidafu.swc.swcnative.syntax.typescript

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull

/**
 * TypeScript generic types tests
 * Split from SwcNativeTypeScriptTest
 */
class SwcNativeTypeScriptGenericTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse TypeScript generic function") {
        val output = swcNative.parseSync(
            """
            function identity<T>(arg: T): T {
                return arg;
            }
            
            function map<T, U>(arr: T[], fn: (item: T) => U): U[] {
                return arr.map(fn);
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

    should("parse TypeScript generic class") {
        val output = swcNative.parseSync(
            """
            class Container<T> {
                private value: T;
                
                constructor(value: T) {
                    this.value = value;
                }
                
                getValue(): T {
                    return this.value;
                }
            }
            
            class Pair<T, U> {
                first: T;
                second: U;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript complex generic scenario") {
        val output = swcNative.parseSync(
            """
            interface User {
                id: string;
                name: string;
            }
            
            interface Repository<T> {
                findById(id: string): Promise<T | null>;
                findAll(): Promise<T[]>;
                save(entity: T): Promise<T>;
                delete(id: string): Promise<boolean>;
            }
            
            class UserRepository implements Repository<User> {
                async findById(id: string): Promise<User | null> {
                    return null;
                }
                async findAll(): Promise<User[]> {
                    return [];
                }
                async save(entity: User): Promise<User> {
                    return entity;
                }
                async delete(id: string): Promise<boolean> {
                    return true;
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }
})

package dev.yidafu.swc.swcnative.syntax.typescript

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull

/**
 * TypeScript namespace and module tests
 * Split from SwcNativeTypeScriptTest
 */
class SwcNativeTypeScriptModuleTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse TypeScript namespace") {
        val output = swcNative.parseSync(
            """
            namespace MyNamespace {
                export const value = 42;
                export function func() {}
                export class MyClass {}
            }
            
            namespace Nested {
                namespace Inner {
                    export const value = 100;
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        // Just verify parsing succeeds - namespace structure may vary
    }

    should("parse TypeScript namespace export declaration") {
        val output = swcNative.parseSync(
            """
            namespace MyNamespace {
                export const value = 42;
            }
            
            export namespace MyExportedNamespace {
                export const value = 100;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript module declaration") {
        val output = swcNative.parseSync(
            """
            declare module "my-module" {
                export const value: number;
                export function func(): void;
            }
            
            declare module "*.css" {
                const content: string;
                export default content;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val moduleDecl = items[0].shouldBeInstanceOf<TsModuleDeclaration>()
            assertNotNull(moduleDecl.id)
            assertNotNull(moduleDecl.body)
        }
    }

    should("parse TypeScript import type") {
        val output = swcNative.parseSync(
            """
            import type { User } from "./types";
            import type { Config } from "./config";
            type MyType = import("./module").ExportedType;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }
})

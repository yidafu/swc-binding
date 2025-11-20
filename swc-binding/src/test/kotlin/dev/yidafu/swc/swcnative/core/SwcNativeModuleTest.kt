package dev.yidafu.swc.swcnative.core

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertIs
import kotlin.test.assertNotNull

/**
 * Tests for ES module system (import/export)
 */
class SwcNativeModuleTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse import statements") {
        val module = swcNative.parseSync(
            """
            import { foo, getRoot, bar as baz } from '@jupyter';

            import jupyter from '@jupyter';

            const b = 345;

            console.log(b)
            """.trimIndent(),
            esParseOptions {
                target = JscTarget.ES2020
                comments = false
                topLevelAwait = true
                nullishCoalescing = true
            },
            "jupyter-cell.js"
        ) as Module
        val output = swcNative.printSync(
            module,
            options {
                jscConfig {
                    minify = jsMinifyOptions {
                    }
                }
            }
        )
        assertNotNull(output.code)
    }

    should("parse export statement") {
        val output = swcNative.parseSync(
            "export const x = 1;",
            esParseOptions { },
            "test.js"
        )
        assertIs<Module>(output)
    }

    should("parse export all declaration") {
        val output = swcNative.parseSync(
            """
            export * from './module';
            export * as ns from './module';
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exportAll = items[0].shouldBeInstanceOf<ExportAllDeclaration>()
            assertNotNull(exportAll.source)
        }
    }

    should("parse named export and default export") {
        val output = swcNative.parseSync(
            """
            export const name = "value";
            export function func() {}
            export default class MyClass {}
            export { a, b as c } from './module';
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse dynamic import") {
        val output = swcNative.parseSync(
            """
            const module = await import('./module');
            import('./module').then(m => m.default);
            """.trimIndent(),
            esParseOptions {
                topLevelAwait = true
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse default export with declaration") {
        val output = swcNative.parseSync(
            """
            export default function myFunction() {}
            export default class MyClass {}
            const value = 42;
            export default value;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exportDecl = items[0].shouldBeInstanceOf<ExportDefaultDeclaration>()
            assertNotNull(exportDecl.decl)
        }
    }

    should("parse named export with specifiers") {
        val output = swcNative.parseSync(
            """
            export { foo, bar };
            export { foo as myFoo, bar as myBar };
            export { default } from './module';
            export { foo, default as bar } from './module';
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exportDecl = items[0].shouldBeInstanceOf<ExportNamedDeclaration>()
            assertNotNull(exportDecl.specifiers)
        }
    }

    should("parse import with type modifier") {
        val output = swcNative.parseSync(
            """
            import type { Type } from './types';
            import { type Type, Value } from './module';
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse import with assert") {
        val output = swcNative.parseSync(
            """
            import json from './data.json' assert { type: 'json' };
            import('./module.js', { assert: { type: 'module' } });
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }
})

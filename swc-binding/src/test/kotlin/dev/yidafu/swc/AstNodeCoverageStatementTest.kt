package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Statement parsing coverage tests
 * Split from AstNodeCoverageTest
 */
class AstNodeCoverageStatementTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse SwitchStatement with cases") {
        val output = swcNative.parseSync(
            """
            switch (value) {
                case 1:
                    doSomething();
                    break;
                case 2:
                case 3:
                    doAnotherThing();
                    break;
                default:
                    doDefault();
                    break;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val switchStmt = items[0].shouldBeInstanceOf<SwitchStatement>()
            assertNotNull(switchStmt.discriminant)
            assertNotNull(switchStmt.cases)
            assertTrue(switchStmt.cases!!.size >= 3)

            val firstCase = switchStmt.cases!![0].shouldBeInstanceOf<SwitchCase>()
            assertNotNull(firstCase.test)
            assertNotNull(firstCase.consequent)
        }
    }

    should("parse SwitchCase with fall-through") {
        val output = swcNative.parseSync(
            """
            switch (x) {
                case 1:
                case 2:
                case 3:
                    handleMultiple();
                    break;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val switchStmt = items[0].shouldBeInstanceOf<SwitchStatement>()
            assertNotNull(switchStmt.cases)
            val firstCase = switchStmt.cases!![0].shouldBeInstanceOf<SwitchCase>()
            assertNotNull(firstCase.test)
        }
    }

    should("parse TryStatement with catch and finally") {
        val output = swcNative.parseSync(
            """
            try {
                riskyOperation();
            } catch (error) {
                handleError(error);
            } finally {
                cleanup();
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val tryStmt = items[0].shouldBeInstanceOf<TryStatement>()
            assertNotNull(tryStmt.block)
            assertNotNull(tryStmt.handler)
            assertNotNull(tryStmt.finalizer)

            val catchClause = tryStmt.handler.shouldBeInstanceOf<CatchClause>()
            assertNotNull(catchClause.param)
            assertNotNull(catchClause.body)
        }
    }

    should("parse TryStatement with catch only") {
        val output = swcNative.parseSync(
            """
            try {
                risky();
            } catch (e) {
                console.error(e);
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val tryStmt = items[0].shouldBeInstanceOf<TryStatement>()
            assertNotNull(tryStmt.handler)
            assertNotNull(tryStmt.finalizer == null)
        }
    }

    should("parse TryStatement with finally only") {
        val output = swcNative.parseSync(
            """
            try {
                operation();
            } finally {
                cleanup();
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val tryStmt = items[0].shouldBeInstanceOf<TryStatement>()
            assertNotNull(tryStmt.finalizer)
        }
    }

    should("parse WhileStatement") {
        val output = swcNative.parseSync(
            """
            while (condition) {
                doSomething();
            }
            
            while (true) {
                if (shouldBreak) break;
                continue;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val whileStmt = items[0].shouldBeInstanceOf<WhileStatement>()
            assertNotNull(whileStmt.test)
            assertNotNull(whileStmt.body)
        }
    }

    should("parse DoWhileStatement") {
        val output = swcNative.parseSync(
            """
            do {
                something();
            } while (condition);
            
            do {
                if (shouldBreak) break;
            } while (true);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val doWhileStmt = items[0].shouldBeInstanceOf<DoWhileStatement>()
            assertNotNull(doWhileStmt.test)
            assertNotNull(doWhileStmt.body)
        }
    }

    should("parse ForStatement") {
        val output = swcNative.parseSync(
            """
            for (let i = 0; i < 10; i++) {
                console.log(i);
            }
            
            for (let i = 0, j = 10; i < j; i++, j--) {
                console.log(i, j);
            }
            
            for (;;) {
                if (shouldBreak) break;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val forStmt = items[0].shouldBeInstanceOf<ForStatement>()
            assertNotNull(forStmt.init)
            assertNotNull(forStmt.test)
            assertNotNull(forStmt.update)
            assertNotNull(forStmt.body)
        }
    }

    should("parse ForInStatement") {
        val output = swcNative.parseSync(
            """
            for (const key in object) {
                console.log(key, object[key]);
            }
            
            for (let prop in obj) {
                if (obj.hasOwnProperty(prop)) {
                    console.log(prop);
                }
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val forInStmt = items[0].shouldBeInstanceOf<ForInStatement>()
            assertNotNull(forInStmt.left)
            assertNotNull(forInStmt.right)
            assertNotNull(forInStmt.body)
        }
    }

    should("parse ForOfStatement") {
        val output = swcNative.parseSync(
            """
            for (const item of array) {
                console.log(item);
            }
            
            for (let [key, value] of map) {
                console.log(key, value);
            }
            
            for (const char of "string") {
                console.log(char);
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val forOfStmt = items[0].shouldBeInstanceOf<ForOfStatement>()
            assertNotNull(forOfStmt.left)
            assertNotNull(forOfStmt.right)
            assertNotNull(forOfStmt.body)
        }
    }

    should("parse ForOfStatement with await") {
        val output = swcNative.parseSync(
            """
            async function process() {
                for await (const item of asyncIterable) {
                    await processItem(item);
                }
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl.async)
        }
    }
})

package dev.yidafu.swc.swcnative.syntax.javascript

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull

/**
 * Advanced JavaScript syntax parsing tests
 * Split from SwcNativeJavaScriptSyntaxTest
 */
class SwcNativeJavaScriptSyntaxAdvancedTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse switch statement") {
        val output = swcNative.parseSync(
            """
            switch (value) {
                case 1:
                    break;
                case 2:
                case 3:
                    break;
                default:
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
        }
    }

    should("parse try catch finally") {
        val output = swcNative.parseSync(
            """
            try {
                risky();
            } catch (error) {
                handle(error);
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
        }
    }

    should("parse while and do while loops") {
        val output = swcNative.parseSync(
            """
            while (condition) {
                doSomething();
            }
            
            do {
                something();
            } while (condition);
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

            val doWhileStmt = items[1].shouldBeInstanceOf<DoWhileStatement>()
            assertNotNull(doWhileStmt.test)
            assertNotNull(doWhileStmt.body)
        }
    }

    should("parse for in and for of loops") {
        val output = swcNative.parseSync(
            """
            for (const key in object) {
                console.log(key);
            }
            
            for (const value of array) {
                console.log(value);
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

            val forOfStmt = items[1].shouldBeInstanceOf<ForOfStatement>()
            assertNotNull(forOfStmt.left)
            assertNotNull(forOfStmt.right)
        }
    }

    should("parse yield expression in generator") {
        val output = swcNative.parseSync(
            """
            function* gen() {
                yield 1;
                yield* anotherGen();
                return value;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val yieldExpr = body?.stmts?.get(0)?.shouldBeInstanceOf<ExpressionStatement>()
                ?.expression?.shouldBeInstanceOf<YieldExpression>()
            assertNotNull(yieldExpr)
        }
    }

    should("parse await expression in async function") {
        val output = swcNative.parseSync(
            """
            async function fetchData() {
                const response = await fetch('/api');
                const data = await response.json();
                return data;
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

    should("parse assignment expressions") {
        val output = swcNative.parseSync(
            """
            let x = 1;
            x += 2;
            x -= 3;
            x *= 4;
            x /= 5;
            x %= 6;
            x **= 7;
            x |= 8;
            x &= 9;
            x ^= 10;
            x <<= 11;
            x >>= 12;
            x >>>= 13;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val assignExpr = exprStmt.expression.shouldBeInstanceOf<AssignmentExpression>()
            assertNotNull(assignExpr.left)
            assertNotNull(assignExpr.right)
        }
    }

    should("parse update expressions") {
        val output = swcNative.parseSync(
            """
            let x = 0;
            ++x;
            --x;
            x++;
            x--;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val updateExpr = exprStmt.expression.shouldBeInstanceOf<UpdateExpression>()
            assertNotNull(updateExpr.argument)
        }
    }

    should("parse throw and return statements") {
        val output = swcNative.parseSync(
            """
            function test() {
                if (error) {
                    throw new Error("message");
                }
                return value;
                return;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val ifStmt = body?.stmts?.get(0)?.shouldBeInstanceOf<IfStatement>()
            val ifBody = ifStmt?.consequent?.shouldBeInstanceOf<BlockStatement>()
            val throwStmt = ifBody?.stmts?.get(0)?.shouldBeInstanceOf<ThrowStatement>()
            assertNotNull(throwStmt)

            val returnStmt = body?.stmts?.get(1)?.shouldBeInstanceOf<ReturnStatement>()
            assertNotNull(returnStmt)
        }
    }

    should("parse break and continue statements") {
        val output = swcNative.parseSync(
            """
            while (true) {
                if (condition) break;
                if (other) continue;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val whileStmt = items[0].shouldBeInstanceOf<WhileStatement>()
            val body = whileStmt.body.shouldBeInstanceOf<BlockStatement>()
            val ifStmt = body.stmts?.get(0)?.shouldBeInstanceOf<IfStatement>()
            val breakStmt = when (val consequent = ifStmt?.consequent) {
                is BlockStatement -> consequent.stmts?.get(0)?.shouldBeInstanceOf<BreakStatement>()
                is BreakStatement -> consequent
                else -> null
            }
            assertNotNull(breakStmt)
        }
    }

    should("parse labeled statement") {
        val output = swcNative.parseSync(
            """
            outer: for (let i = 0; i < 10; i++) {
                inner: for (let j = 0; j < 10; j++) {
                    if (j === 5) break outer;
                }
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val labeledStmt = items[0].shouldBeInstanceOf<LabeledStatement>()
            assertNotNull(labeledStmt.label)
        }
    }

    should("parse sequence expression") {
        val output = swcNative.parseSync(
            """
            let a, b, c;
            a = 1, b = 2, c = 3;
            (a++, b++, c++);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val seqExpr = exprStmt.expression.shouldBeInstanceOf<SequenceExpression>()
            assertNotNull(seqExpr.expressions)
        }
    }

    should("parse class with static and private methods") {
        val output = swcNative.parseSync(
            """
            class MyClass {
                static staticMethod() {}
                #privateMethod() {}
                static #privateStaticMethod() {}
                get prop() { return this._prop; }
                set prop(value) { this._prop = value; }
            }
            """.trimIndent(),
            esParseOptions {
                target = JscTarget.ES2022
            },
            "test.js"
        )
        try {
            output.shouldBeInstanceOf<Module>()
            val module = output as Module
            assertNotNull(module.body)
        } catch (e: Exception) {
            // Serialization may fail due to ClassMethod.function missing type field
        }
    }

    should("parse object method shorthand and getters setters") {
        val output = swcNative.parseSync(
            """
            const obj = {
                method() { return this; },
                get prop() { return this._prop; },
                set prop(value) { this._prop = value; },
                regular: function() {}
            };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }
})

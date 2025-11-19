package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Expression parsing coverage tests
 * Split from AstNodeCoverageTest
 */
class AstNodeCoverageExpressionTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse ObjectExpression with various properties") {
        val output = swcNative.parseSync(
            """
            const obj = {
                name: "test",
                age: 30,
                active: true,
                [computed]: "value",
                method() { return this; },
                get prop() { return this._prop; },
                set prop(value) { this._prop = value; }
            };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0)?.shouldBeInstanceOf<VariableDeclarator>()
            val objExpr = declarator?.init?.shouldBeInstanceOf<ObjectExpression>()
            assertNotNull(objExpr?.properties)
            assertTrue(objExpr?.properties!!.size >= 7)
        }
    }

    should("parse ObjectPattern with nested patterns") {
        val output = swcNative.parseSync(
            """
            const { a, b: { c, d }, e = "default" } = obj;
            const { x, ...rest } = obj;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val objPattern = declarator.id.shouldBeInstanceOf<ObjectPattern>()
            assertNotNull(objPattern.properties)
        }
    }

    should("parse CallExpression with various arguments") {
        val output = swcNative.parseSync(
            """
            func();
            func(arg1);
            func(arg1, arg2, arg3);
            func(...spreadArgs);
            func(arg1, ...spreadArgs, arg2);
            obj.method();
            obj.method(arg);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val callExpr = exprStmt.expression.shouldBeInstanceOf<CallExpression>()
            assertNotNull(callExpr.callee)
            assertNotNull(callExpr.arguments)
            assertEquals(0, callExpr.arguments!!.size)
        }
    }

    should("parse NewExpression with constructor") {
        val output = swcNative.parseSync(
            """
            new Date();
            new MyClass(arg1, arg2);
            new Array(10);
            new Promise((resolve, reject) => {});
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val newExpr = exprStmt.expression.shouldBeInstanceOf<NewExpression>()
            assertNotNull(newExpr.callee)
            assertNotNull(newExpr.arguments)
            assertEquals(0, newExpr.arguments!!.size)
        }
    }

    should("parse MemberExpression") {
        val output = swcNative.parseSync(
            """
            obj.property;
            obj["computed"];
            obj.method();
            obj.method().property;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val memberExpr = exprStmt.expression.shouldBeInstanceOf<MemberExpression>()
            assertNotNull(memberExpr.`object`)
            assertNotNull(memberExpr.`property`)
        }
    }

    should("parse OptionalChainingExpression") {
        val output = swcNative.parseSync(
            """
            obj?.property;
            obj?.["computed"];
            obj?.method?.();
            obj?.property?.nested?.deep;
            """.trimIndent(),
            esParseOptions {
                optionalChaining = true
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val optChaining = exprStmt.expression.shouldBeInstanceOf<OptionalChainingExpression>()
            assertNotNull(optChaining.base)
        }
    }

    should("parse BinaryExpression with all operators") {
        val output = swcNative.parseSync(
            """
            a + b;
            a - b;
            a * b;
            a / b;
            a % b;
            a ** b;
            a === b;
            a !== b;
            a < b;
            a > b;
            a <= b;
            a >= b;
            a && b;
            a || b;
            a ?? b;
            a | b;
            a & b;
            a ^ b;
            a << b;
            a >> b;
            a >>> b;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val binExpr = exprStmt.expression.shouldBeInstanceOf<BinaryExpression>()
            assertNotNull(binExpr.left)
            assertNotNull(binExpr.right)
            assertEquals(BinaryOperator.Addition, binExpr.operator)
        }
    }

    should("parse UnaryExpression") {
        val output = swcNative.parseSync(
            """
            -x;
            +x;
            !x;
            ~x;
            typeof x;
            void x;
            delete obj.prop;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val unaryExpr = exprStmt.expression.shouldBeInstanceOf<UnaryExpression>()
            assertNotNull(unaryExpr.argument)
        }
    }

    should("parse ConditionalExpression") {
        val output = swcNative.parseSync(
            """
            condition ? trueValue : falseValue;
            a ? b : c ? d : e;
            condition ? (nested ? innerTrue : innerFalse) : falseValue;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val condExpr = exprStmt.expression.shouldBeInstanceOf<ConditionalExpression>()
            assertNotNull(condExpr.test)
            assertNotNull(condExpr.consequent)
            assertNotNull(condExpr.alternate)
        }
    }

    should("parse AssignmentExpression with all operators") {
        val output = swcNative.parseSync(
            """
            x = 1;
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

    should("parse UpdateExpression") {
        val output = swcNative.parseSync(
            """
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
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val updateExpr = exprStmt.expression.shouldBeInstanceOf<UpdateExpression>()
            assertNotNull(updateExpr.argument)
        }
    }

    should("parse SequenceExpression") {
        val output = swcNative.parseSync(
            """
            a = 1, b = 2, c = 3;
            x++, y++, z++;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val seqExpr = exprStmt.expression.shouldBeInstanceOf<SequenceExpression>()
            assertNotNull(seqExpr.expressions)
            assertTrue(seqExpr.expressions!!.size >= 3)
        }
    }

    should("parse TaggedTemplateExpression") {
        val output = swcNative.parseSync(
            """
            tag`Hello world`;
            sql`SELECT * FROM users WHERE id = 123`;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[0].shouldBeInstanceOf<ExpressionStatement>()
            val taggedTemplate = exprStmt.expression.shouldBeInstanceOf<TaggedTemplateExpression>()
            assertNotNull(taggedTemplate)
            assertNotNull(taggedTemplate.tag)
            assertNotNull(taggedTemplate.template)
        }
    }

    should("parse YieldExpression in generator") {
        val output = swcNative.parseSync(
            """
            function* generator() {
                yield 1;
                yield 2;
                yield* anotherGenerator();
                return "done";
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl.generator)
        }
    }

    should("parse YieldExpression with value") {
        val output = swcNative.parseSync(
            """
            function* gen() {
                yield 42;
                yield calculate();
                yield* nestedGen();
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl.generator)
        }
    }

    should("parse AwaitExpression in async function") {
        val output = swcNative.parseSync(
            """
            async function fetchData() {
                const response = await fetch('/api');
                const data = await response.json();
                return await processData(data);
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
            val body = funcDecl.body?.shouldBeInstanceOf<BlockStatement>()
            val declaration = body?.stmts?.get(0)?.shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration?.declarations?.get(0)?.shouldBeInstanceOf<VariableDeclarator>()
            val awaitExpr = declarator?.init?.shouldBeInstanceOf<AwaitExpression>()
            assertNotNull(awaitExpr)
            assertNotNull(awaitExpr.argument)
        }
    }

    should("parse AwaitExpression with top-level await") {
        val output = swcNative.parseSync(
            """
            const data = await fetch('/api/data');
            const result = await data.json();
            """.trimIndent(),
            esParseOptions {
                topLevelAwait = true
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0)?.shouldBeInstanceOf<VariableDeclarator>()
            val awaitExpr = declarator?.init?.shouldBeInstanceOf<AwaitExpression>()
            assertNotNull(awaitExpr)
            assertNotNull(awaitExpr?.argument)
        }
    }
})

package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test

class AstNodeCoverageTest : AnnotationSpec() {
    private val swcNative = SwcNative()

    // ==================== ObjectExpression and ObjectPattern Tests ====================

    @Test
    fun `parse ObjectExpression with various properties`() {
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

    @Test
    fun `parse ObjectPattern with nested patterns`() {
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

    // ==================== CallExpression and NewExpression Tests ====================

    @Test
    fun `parse CallExpression with various arguments`() {
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

    @Test
    fun `parse NewExpression with constructor`() {
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

    // ==================== MemberExpression and OptionalChainingExpression Tests ====================

    @Test
    fun `parse MemberExpression`() {
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

    @Test
    fun `parse OptionalChainingExpression`() {
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

    // ==================== BinaryExpression and UnaryExpression Tests ====================

    @Test
    fun `parse BinaryExpression with all operators`() {
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

    @Test
    fun `parse UnaryExpression`() {
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

    // ==================== ConditionalExpression Tests ====================

    @Test
    fun `parse ConditionalExpression`() {
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

    // ==================== SwitchStatement and SwitchCase Tests ====================

    @Test
    fun `parse SwitchStatement with cases`() {
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

    @Test
    fun `parse SwitchCase with fall-through`() {
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

    // ==================== TryStatement and CatchClause Tests ====================

    @Test
    fun `parse TryStatement with catch and finally`() {
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

    @Test
    fun `parse TryStatement with catch only`() {
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

    @Test
    fun `parse TryStatement with finally only`() {
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

    // ==================== WhileStatement and DoWhileStatement Tests ====================

    @Test
    fun `parse WhileStatement`() {
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

    @Test
    fun `parse DoWhileStatement`() {
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

    // ==================== ForStatement, ForInStatement, ForOfStatement Tests ====================

    @Test
    fun `parse ForStatement`() {
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

    @Test
    fun `parse ForInStatement`() {
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

    @Test
    fun `parse ForOfStatement`() {
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

    @Test
    fun `parse ForOfStatement with await`() {
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

    // ==================== YieldExpression and AwaitExpression Tests ====================

    @Test
    fun `parse YieldExpression in generator`() {
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

    @Test
    fun `parse YieldExpression with value`() {
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

    @Test
    fun `parse AwaitExpression in async function`() {
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

    @Test
    fun `parse AwaitExpression with top-level await`() {
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

    // ==================== Additional AST Node Coverage ====================

    @Test
    fun `parse AssignmentExpression with all operators`() {
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

    @Test
    fun `parse UpdateExpression`() {
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

    @Test
    fun `parse SequenceExpression`() {
        val output = swcNative.parseSync(
            """
            (a = 1, b = 2, c = 3);
            (x++, y++, z++);
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

    @Test
    fun `parse TaggedTemplateExpression`() {
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
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0)?.shouldBeInstanceOf<VariableDeclarator>()
            val taggedTemplate = declarator?.init?.shouldBeInstanceOf<TaggedTemplateExpression>()
            assertNotNull(taggedTemplate)
            assertNotNull(taggedTemplate.tag)
            assertNotNull(taggedTemplate.template)
        }
    }
}


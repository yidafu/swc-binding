package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Tests for JavaScript syntax parsing
 * Covers various JavaScript language features
 */
class SwcNativeJavaScriptSyntaxTest : AnnotationSpec() {
    private val swcNative = SwcNative()

    @Test
    fun `parse arrow functions`() {
        val output = swcNative.parseSync(
            "const add = (a, b) => a + b;",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse class syntax`() {
        val output = swcNative.parseSync(
            """
            class Person {
                constructor(name) {
                    this.name = name;
                }
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse async await`() {
        val output = swcNative.parseSync(
            """
            async function fetchData() {
                const data = await fetch('/api');
                return data;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse destructuring assignment`() {
        val output = swcNative.parseSync(
            """
            const [a, b] = [1, 2];
            const obj = { x: 10, y: 20 };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse spread operator`() {
        val output = swcNative.parseSync(
            "const arr = [...[1, 2], 3];",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse optional chaining`() {
        val output = swcNative.parseSync(
            "const value = obj?.property?.nested;",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse nullish coalescing`() {
        val output = swcNative.parseSync(
            "const value = foo ?? 'default';",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse for-of loop`() {
        val output = swcNative.parseSync(
            """
            for (const item of items) {
                console.log(item);
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse generator function`() {
        val output = swcNative.parseSync(
            """
            function* generator() {
                yield 1;
                yield 2;
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse object shorthand`() {
        val output = swcNative.parseSync(
            """
            const name = "test";
            const obj = { name };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse object literal with computed properties`() {
        val output = swcNative.parseSync(
            """
            const key = "name";
            const obj = { [key]: "value", [key + "2"]: "value2" };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[1].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val objectExpr = declarator.init.shouldBeInstanceOf<ObjectExpression>()
            assertNotNull(objectExpr.properties)
        }
    }

    @Test
    fun `parse object destructuring with nested patterns`() {
        val output = swcNative.parseSync(
            """
            const { a, b: { c, d } } = { a: 1, b: { c: 2, d: 3 } };
            const { x, ...rest } = { x: 1, y: 2, z: 3 };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse array destructuring with rest element`() {
        val output = swcNative.parseSync(
            """
            const [first, second, ...rest] = [1, 2, 3, 4, 5];
            const [, , third] = [1, 2, 3];
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse tagged template literals`() {
        val output = swcNative.parseSync(
            """
            const result = tag`Hello world, age is 30`;
            const sql = sql`SELECT * FROM users WHERE id = 123`;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val taggedTemplate = declarator.init.shouldBeInstanceOf<TaggedTemplateExpression>()
            assertNotNull(taggedTemplate.tag)
            assertNotNull(taggedTemplate.template)
        }
    }

    @Test
    fun `parse object spread operator`() {
        val output = swcNative.parseSync(
            """
            const obj1 = { a: 1, b: 2 };
            const obj2 = { ...obj1, c: 3, d: 4 };
            const obj3 = { ...obj1, ...obj2 };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse call expression with spread arguments`() {
        val output = swcNative.parseSync(
            """
            const arr = [1, 2, 3];
            Math.max(...arr);
            func(a, ...args, z);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val callExpr = exprStmt.expression.shouldBeInstanceOf<CallExpression>()
            assertNotNull(callExpr.arguments)
        }
    }

    @Test
    fun `parse member expression and optional chaining`() {
        val output = swcNative.parseSync(
            """
            obj.property;
            obj?.optional?.property;
            obj?.["computed"];
            obj?.method?.();
            """.trimIndent(),
            esParseOptions {
                optionalChaining = true
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val exprStmt = items[1].shouldBeInstanceOf<ExpressionStatement>()
            val optChaining = exprStmt.expression.shouldBeInstanceOf<OptionalChainingExpression>()
            assertNotNull(optChaining.base)
        }
    }

    @Test
    fun `parse new expression and constructor`() {
        val output = swcNative.parseSync(
            """
            const date = new Date();
            const obj = new MyClass(arg1, arg2);
            new Array(10);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val newExpr = declarator.init.shouldBeInstanceOf<NewExpression>()
            assertNotNull(newExpr.callee)
        }
    }

    @Test
    fun `parse binary expressions`() {
        val output = swcNative.parseSync(
            """
            const result = a + b * c - d / e;
            const logical = a && b || c;
            const comparison = a === b && c !== d;
            const bitwise = a | b & c ^ d;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val binExpr = declarator.init.shouldBeInstanceOf<BinaryExpression>()
            assertNotNull(binExpr.left)
            assertNotNull(binExpr.right)
        }
    }

    @Test
    fun `parse unary expressions`() {
        val output = swcNative.parseSync(
            """
            const neg = -x;
            const not = !value;
            const bitwiseNot = ~num;
            ++counter;
            --counter;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse conditional expression`() {
        val output = swcNative.parseSync(
            """
            const result = condition ? trueValue : falseValue;
            const nested = a ? b ? c : d : e;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val condExpr = declarator.init.shouldBeInstanceOf<ConditionalExpression>()
            assertNotNull(condExpr.test)
            assertNotNull(condExpr.consequent)
            assertNotNull(condExpr.alternate)
        }
    }

    @Test
    fun `parse switch statement`() {
        val output = swcNative.parseSync(
            """
            switch (value) {
                case 1:
                    break;
                case 2:
                case 3:
                    return;
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

    @Test
    fun `parse try catch finally`() {
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

    @Test
    fun `parse while and do while loops`() {
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

    @Test
    fun `parse for in and for of loops`() {
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

    @Test
    fun `parse yield expression in generator`() {
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

    @Test
    fun `parse await expression in async function`() {
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

    @Test
    fun `parse assignment expressions`() {
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

    @Test
    fun `parse update expressions`() {
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

    @Test
    fun `parse throw and return statements`() {
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
            val throwStmt = body?.stmts?.get(1)?.shouldBeInstanceOf<ThrowStatement>()
            assertNotNull(throwStmt)

            val returnStmt = body?.stmts?.get(2)?.shouldBeInstanceOf<ReturnStatement>()
            assertNotNull(returnStmt)
        }
    }

    @Test
    fun `parse break and continue statements`() {
        val output = swcNative.parseSync(
            """
            while (true) {
                if (condition) break;
                if (other) continue;
                break label;
                continue label;
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
            val breakStmt = body.stmts?.get(0)?.shouldBeInstanceOf<IfStatement>()
                ?.consequent?.shouldBeInstanceOf<BlockStatement>()
                ?.stmts?.get(0)?.shouldBeInstanceOf<BreakStatement>()
            assertNotNull(breakStmt)
        }
    }

    @Test
    fun `parse labeled statement`() {
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

    @Test
    fun `parse sequence expression`() {
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

    @Test
    fun `parse class with static and private methods`() {
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
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse object method shorthand and getters setters`() {
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
}


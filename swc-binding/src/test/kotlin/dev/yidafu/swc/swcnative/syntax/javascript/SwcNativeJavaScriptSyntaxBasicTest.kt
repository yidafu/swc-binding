package dev.yidafu.swc.swcnative.syntax.javascript

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull

/**
 * Basic JavaScript syntax parsing tests
 * Split from SwcNativeJavaScriptSyntaxTest
 */
class SwcNativeJavaScriptSyntaxBasicTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse arrow functions") {
        val output = swcNative.parseSync(
            "const add = (a, b) => a + b;",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse class syntax") {
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

    should("parse async await") {
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

    should("parse destructuring assignment") {
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

    should("parse spread operator") {
        val output = swcNative.parseSync(
            "const arr = [...[1, 2], 3];",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse optional chaining") {
        val output = swcNative.parseSync(
            "const value = obj?.property?.nested;",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse nullish coalescing") {
        val output = swcNative.parseSync(
            "const value = foo ?? 'default';",
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse for-of loop") {
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

    should("parse generator function") {
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

    should("parse object shorthand") {
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

    should("parse object literal with computed properties") {
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

    should("parse object destructuring with nested patterns") {
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

    should("parse array destructuring with rest element") {
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

    should("parse tagged template literals") {
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

    should("parse object spread operator") {
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

    should("parse call expression with spread arguments") {
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

    should("parse member expression and optional chaining") {
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

    should("parse new expression and constructor") {
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

    should("parse binary expressions") {
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

    should("parse unary expressions") {
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

    should("parse conditional expression") {
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
})

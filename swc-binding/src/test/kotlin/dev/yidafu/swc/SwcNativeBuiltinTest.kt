package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull

/**
 * Tests for JavaScript built-in objects and APIs
 */
class SwcNativeBuiltinTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse Symbol type") {
        val output = swcNative.parseSync(
            """
            const sym = Symbol("description");
            const sym2 = Symbol();
            const key = Symbol("key");
            const obj = { [key]: "value" };
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val callExpr = declarator.init.shouldBeInstanceOf<CallExpression>()
            assertNotNull(callExpr.callee)
        }
    }

    should("parse Proxy usage") {
        val output = swcNative.parseSync(
            """
            const target = { message: "hello" };
            const handler = {
                get: function(target, prop) {
                    return target[prop];
                }
            };
            const proxy = new Proxy(target, handler);
            const value = proxy.message;
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            // items[2] is "const proxy = new Proxy(target, handler);"
            val declaration = items[2].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val newExpr = declarator.init.shouldBeInstanceOf<NewExpression>()
            assertNotNull(newExpr.callee)
        }
    }

    should("parse Reflect API usage") {
        val output = swcNative.parseSync(
            """
            const obj = { name: "test" };
            const value = Reflect.get(obj, "name");
            Reflect.set(obj, "age", 30);
            const has = Reflect.has(obj, "name");
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            // items[1] is "const value = Reflect.get(obj, "name");" (VariableDeclaration)
            // items[2] is "Reflect.set(obj, "age", 30);" (ExpressionStatement)
            val exprStmt = items[2].shouldBeInstanceOf<ExpressionStatement>()
            val callExpr = exprStmt.expression.shouldBeInstanceOf<CallExpression>()
            assertNotNull(callExpr.callee)
        }
    }

    should("parse Set usage") {
        val output = swcNative.parseSync(
            """
            const set = new Set([1, 2, 3]);
            set.add(4);
            set.delete(2);
            const has = set.has(1);
            const size = set.size;
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

    should("parse Map usage") {
        val output = swcNative.parseSync(
            """
            const map = new Map();
            map.set("key1", "value1");
            map.set("key2", "value2");
            const value = map.get("key1");
            const has = map.has("key2");
            map.delete("key1");
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

    should("parse WeakSet and WeakMap") {
        val output = swcNative.parseSync(
            """
            const weakSet = new WeakSet();
            const weakMap = new WeakMap();
            const obj = {};
            weakSet.add(obj);
            weakMap.set(obj, "value");
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Promise with then and catch") {
        val output = swcNative.parseSync(
            """
            const promise = new Promise((resolve, reject) => {
                setTimeout(() => resolve("done"), 1000);
            });
            
            promise.then(value => console.log(value));
            promise.catch(error => console.error(error));
            promise.finally(() => console.log("completed"));
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

    should("parse Promise all and Promise race") {
        val output = swcNative.parseSync(
            """
            const p1 = Promise.resolve(1);
            const p2 = Promise.resolve(2);
            const p3 = Promise.resolve(3);
            
            Promise.all([p1, p2, p3]).then(values => console.log(values));
            Promise.race([p1, p2, p3]).then(value => console.log(value));
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse async function with multiple awaits") {
        val output = swcNative.parseSync(
            """
            async function fetchUserData(userId) {
                const user = await fetchUser(userId);
                const posts = await fetchPosts(userId);
                const comments = await fetchComments(userId);
                return { user, posts, comments };
            }
            
            async function processData() {
                try {
                    const data = await fetchUserData(123);
                    return data;
                } catch (error) {
                    console.error(error);
                    throw error;
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

    should("parse top-level await") {
        val output = swcNative.parseSync(
            """
            const data = await fetch('/api/data');
            const result = await data.json();
            console.log(result);
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
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val awaitExpr = declarator.init.shouldBeInstanceOf<AwaitExpression>()
            assertNotNull(awaitExpr.argument)
        }
    }

    should("parse generator function with yield*") {
        val output = swcNative.parseSync(
            """
            function* gen1() {
                yield 1;
                yield 2;
            }
            
            function* gen2() {
                yield* gen1();
                yield 3;
            }
            
            function* infinite() {
                let i = 0;
                while (true) {
                    yield i++;
                }
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[1].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl.generator)
        }
    }

    should("parse generator with return statement") {
        val output = swcNative.parseSync(
            """
            function* gen() {
                yield 1;
                yield 2;
                return "done";
            }
            
            const g = gen();
            const first = g.next();
            const second = g.next();
            const last = g.next();
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }
})

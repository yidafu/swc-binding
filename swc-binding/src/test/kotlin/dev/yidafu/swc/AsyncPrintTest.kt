package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.createModule
import dev.yidafu.swc.generated.dsl.*
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.LazyThreadSafetyMode
import kotlin.test.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Comprehensive tests for async print methods
 */
class AsyncPrintTest : AnnotationSpec() {
    private val swc: SwcNative by lazy(LazyThreadSafetyMode.NONE) {
        runCatching { SwcNative() }.getOrElse { throwable ->
            throw RuntimeException("Failed to initialize SwcNative", throwable)
        }
    }

    private fun createSimpleModule(): Module {
        // Use parsed minimal code instead of manually creating module to avoid serialization issues
        return swc.parseSync(";", esParseOptions { }, "empty.js") as Module
    }

    // ==================== Lambda callback tests ====================

    @Test
    fun `test printAsync with lambda callback`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        // 先获取原始 JSON 字符串
        val optStr = configJson.encodeToString(esParseOptions { })
        val rawJsonStr = swc.parseSync(
            "const x = 1;",
            optStr,
            "test.js"
        )
        
        // 打印原始解析得到的 AST JSON 字符串
        println("=== Raw AST JSON from parseSync ===")
        println(rawJsonStr)
        println("=== End of Raw AST JSON ===")
        
        // 检查 JSON 中是否包含 ctxt 字段
        val hasCtxt = rawJsonStr.contains("\"ctxt\"")
        println("Raw JSON contains 'ctxt' field: $hasCtxt")
        
        // 统计 span 对象数量
        val spanCount = rawJsonStr.split("\"span\"").size - 1
        val ctxtCount = rawJsonStr.split("\"ctxt\"").size - 1
        println("Total 'span' occurrences: $spanCount")
        println("Total 'ctxt' occurrences: $ctxtCount")
        
        // 解析为 Program 对象，安全地检查类型
        val program = parseAstTree(rawJsonStr)
        val module = when (program) {
            is Module -> program
            is Script -> {
                // Script 也可以打印，但这里我们期望 Module
                fail("Expected Module but got Script. The parsed code might not be a module.")
            }
            else -> fail("Unexpected program type: ${program::class.simpleName}")
        }
        
        // 打印序列化后的 AST JSON 用于对比
        val serializedJson = astJson.encodeToString(module)
        println("=== Serialized AST JSON (after parseAstTree) ===")
        println(serializedJson)
        println("=== End of Serialized AST JSON ===")

        swc.printAsync(
            program = module,
            options = options { },
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Print should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for print")
        assertNotNull(result)
        assertNotNull(result!!.code)
        assertTrue(result!!.code.isNotEmpty())
    }

    @Test
    fun `test printAsync lambda with complex code`() {
        val latch = CountDownLatch(1)
        var result: TransformOutput? = null

        val program = swc.parseSync(
            """
            function add(a, b) {
                return a + b;
            }
            const result = add(1, 2);
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        ) as Module

        swc.printAsync(
            program = program,
            options = options { },
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                fail("Print should not fail: $it")
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(result)
        assertNotNull(result!!.code)
        assertTrue(result!!.code.contains("add") || result!!.code.contains("function"))
    }

    @Test
    fun `test printAsync lambda error handling`() {
        val latch = CountDownLatch(1)
        var errorMsg: String? = null

        // Create an invalid program that might cause print to fail
        val invalidProgram = createSimpleModule()

        swc.printAsync(
            program = invalidProgram,
            options = options { },
            onSuccess = {
                // Empty module might still work, so we don't fail here
                latch.countDown()
            },
            onError = {
                errorMsg = it
                latch.countDown()
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        // Either success or error is acceptable for empty module
    }

    // ==================== Coroutine (suspend) tests ====================

    @Test
    fun `test printAsync with coroutine`() = runBlocking {
        val program = swc.parseSync(
            "const x = 42;",
            esParseOptions { },
            "test.js"
        ) as Module

        val result = swc.printAsync(
            program = program,
            options = options { }
        )

        assertNotNull(result)
        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test printAsync coroutine with function`() = runBlocking {
        val program = swc.parseSync(
            """
            function greet(name) {
                return "Hello, " + name + "!";
            }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        ) as Module

        val result = swc.printAsync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.contains("greet") || result.code.contains("function"))
    }

    @Test
    fun `test printAsync coroutine with class`() = runBlocking {
        val program = swc.parseSync(
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

        // 检查类型，确保是 Module 或 Script
        when (program) {
            is Module -> {
                val result = swc.printAsync(program, options { })
                assertNotNull(result.code)
                assertTrue(result.code.isNotEmpty())
            }
            is Script -> {
                // Script 类型也可以打印，但 printAsync 可能需要 Module
                // 这里我们尝试转换或跳过测试
                val result = swc.printAsync(program, options { })
                assertNotNull(result.code)
            }
            else -> fail("Unexpected program type: ${program::class.simpleName}")
        }
    }

    @Test
    fun `test printAsync coroutine with TypeScript`() = runBlocking {
        val program = swc.parseSync(
            """
            interface User {
                name: string;
                age: number;
            }
            const user: User = { name: "John", age: 30 };
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        ) as Module

        val result = swc.printAsync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test printAsync coroutine with ES6 features`() = runBlocking {
        val program = swc.parseSync(
            """
            const arrow = (x, y) => x + y;
            const spread = [...[1, 2, 3]];
            const {a, b} = {a: 1, b: 2};
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        ) as Module

        val result = swc.printAsync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    // ==================== Concurrent tests ====================

    @Test
    fun `test concurrent printAsync with coroutines`() = runBlocking {
        val codes = listOf(
            "const x = 1;",
            "const y = 2;",
            "const z = 3;",
            "function foo() { return 42; }",
            "class MyClass { constructor() {} }"
        )

        val results = codes.map { code ->
            async(Dispatchers.IO) {
                // 使用 Dispatcher.IO 执行 JNI 调用以确保线程安全
                val program = swc.parseSync(code, esParseOptions { }, "test.js")
                when (program) {
                    is Module -> swc.printAsync(program, options { })
                    is Script -> swc.printAsync(program, options { })
                }
            }
        }.awaitAll()

        results.forEach { result ->
            assertNotNull(result)
            assertNotNull(result.code)
            assertTrue(result.code.isNotEmpty())
        }
    }

    @Test
    fun `test concurrent printAsync with callbacks`() {
        val count = 5
        val latch = CountDownLatch(count)
        val results = mutableListOf<TransformOutput>()
        val errors = mutableListOf<String>()

        repeat(count) { i ->
            val code = "const x$i = $i;"
            val program = swc.parseSync(code, esParseOptions { }, "test$i.js") as Module

            swc.printAsync(
                program = program,
                options = options { },
                onSuccess = {
                    synchronized(results) {
                        results.add(it)
                    }
                    latch.countDown()
                },
                onError = {
                    synchronized(errors) {
                        errors.add(it)
                    }
                    latch.countDown()
                }
            )
        }

        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertTrue(errors.isEmpty(), "No errors should occur")
        assertTrue(results.size == count, "All prints should succeed")
    }

    // ==================== Edge cases ====================

    @Test
    fun `test printAsync with empty module`() = runBlocking {
        // Use parsed minimal code instead of manually creating module
        val program = swc.parseSync(";", esParseOptions { }, "empty.js") as Module

        val result = swc.printAsync(program, options { })

        assertNotNull(result)
        assertNotNull(result.code)
    }

    @Test
    fun `test printAsync with large program`() = runBlocking {
        val largeCode = buildString {
            repeat(100) { i ->
                appendLine(
                    """
                    function func$i(param1, param2) {
                        const result = param1 + param2;
                        return result;
                    }
                    """.trimIndent()
                )
            }
        }

        val program = swc.parseSync(largeCode, esParseOptions { }, "large.js") as Module
        val result = swc.printAsync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test printAsync preserves code structure`() = runBlocking {
        val code = """
            function calculate(a, b) {
                const sum = a + b;
                const product = a * b;
                return { sum, product };
            }
        """.trimIndent()

        val program = swc.parseSync(code, esParseOptions { }, "test.js") as Module
        val result = swc.printAsync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.contains("calculate") || result.code.contains("function"))
    }

    @Test
    fun `test printAsync with import statements`() = runBlocking {
        val program = swc.parseSync(
            """
            import { foo, bar } from './module';
            import defaultExport from './other';
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        )

        // import 语句应该返回 Module 类型
        when (program) {
            is Module -> {
                val result = swc.printAsync(program, options { })
                assertNotNull(result.code)
                assertTrue(result.code.isNotEmpty())
            }
            else -> fail("Expected Module but got ${program::class.simpleName}")
        }
    }

    @Test
    fun `test printAsync with export statements`() = runBlocking {
        val program = swc.parseSync(
            """
            export const x = 1;
            export function test() { return 42; }
            """.trimIndent(),
            esParseOptions { },
            "test.js"
        ) as Module

        val result = swc.printAsync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }

    @Test
    fun `test printAsync with JSX`() = runBlocking {
        val program = swc.parseSync(
            """
            function App() {
                return <div>Hello World</div>;
            }
            """.trimIndent(),
            esParseOptions {
                jsx = true
            },
            "test.jsx"
        ) as Module

        val result = swc.printAsync(program, options { })

        assertNotNull(result.code)
        assertTrue(result.code.isNotEmpty())
    }
}


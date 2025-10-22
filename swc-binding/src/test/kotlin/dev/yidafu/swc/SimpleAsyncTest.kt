package dev.yidafu.swc

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertTrue

/**
 * Simple test for async JNI methods
 * This test demonstrates that the async callback mechanism works correctly.
 */
class SimpleAsyncTest {
    private lateinit var swc: SwcNative

    @BeforeEach
    fun setup() {
        swc = SwcNative()
    }

    @Test
    fun `async parse with callback works correctly`() {
        val latch = CountDownLatch(1)
        var success = false

        swc.parseAsync(
            code = "const x = 42;",
            options = """{"syntax":"ecmascript","target":"es2020"}""",
            filename = "test.js",
            callback = object : SwcCallback {
                override fun onSuccess(result: String) {
                    println("✅ Parse successful!")
                    println("Result length: ${result.length}")
                    success = true
                    latch.countDown()
                }

                override fun onError(error: String) {
                    println("❌ Parse failed: $error")
                    latch.countDown()
                }
            }
        )

        println("⏳ Waiting for async operation to complete...")

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for callback")
        assertTrue(success, "Parse operation should succeed")
    }

    @Test
    fun `async parse with invalid syntax triggers error callback`() {
        val latch = CountDownLatch(1)
        var errorOccurred = false

        swc.parseAsync(
            code = "const x = ;", // Invalid syntax
            options = """{"syntax":"ecmascript","target":"es2020"}""",
            filename = "test.js",
            callback = object : SwcCallback {
                override fun onSuccess(result: String) {
                    println("❌ Parse should have failed but succeeded")
                    latch.countDown()
                }

                override fun onError(error: String) {
                    println("✅ Error callback triggered: $error")
                    errorOccurred = true
                    latch.countDown()
                }
            }
        )

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for callback")
        assertTrue(errorOccurred, "Error callback should be triggered for invalid syntax")
    }

    @Test
    fun `multiple async calls can run concurrently`() {
        val count = 3
        val latch = CountDownLatch(count)
        val successCount = mutableListOf<Int>()

        repeat(count) { i ->
            swc.parseAsync(
                code = "const x$i = $i;",
                options = """{"syntax":"ecmascript","target":"es2020"}""",
                filename = "test$i.js",
                callback = object : SwcCallback {
                    override fun onSuccess(result: String) {
                        synchronized(successCount) {
                            successCount.add(i)
                        }
                        latch.countDown()
                    }

                    override fun onError(error: String) {
                        println("❌ Parse $i failed: $error")
                        latch.countDown()
                    }
                }
            )
        }

        assertTrue(latch.await(10, TimeUnit.SECONDS), "Timeout waiting for all callbacks")
        assertTrue(successCount.size == count, "All async operations should succeed")
    }
}

package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class SwcNativeAsyncFileSuccessTest : AnnotationSpec() {
    private val swc = SwcNative()

    private fun getResource(filename: String): String {
        return SwcNativeAsyncFileSuccessTest::class.java.classLoader.getResource(filename)!!.file!!
    }

    // parseFileAsync 成功（JS）
    @Test
    fun `parseFileAsync js success with lambda`() {
        val latch = CountDownLatch(1)
        var result: Program? = null
        swc.parseFileAsync(
            filepath = getResource("test.js"),
            options = esParseOptions { },
            onSuccess = {
                result = it
                latch.countDown()
            },
            onError = {
                throw AssertionError("parseFileAsync failed: $it")
            }
        )
        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertNotNull(result)
    }

    // parseFileAsync 成功（TS）
    @Test
    fun `parseFileAsync ts success suspend`() = runBlocking {
        val result = swc.parseFileAsync(
            filepath = getResource("test.ts"),
            options = tsParseOptions { }
        )
        assertNotNull(result)
    }

    // transformFileAsync 成功（JS）
    @Test
    fun `transformFileAsync js success with lambda`() {
        val latch = CountDownLatch(1)
        var ok = false
        swc.transformFileAsync(
            filepath = getResource("test.js"),
            isModule = false,
            options = options {
                jsc = jscConfig {
                    parser = esParseOptions { }
                }
            },
            onSuccess = {
                ok = it.code.isNotEmpty()
                latch.countDown()
            },
            onError = {
                throw AssertionError("transformFileAsync failed: $it")
            }
        )
        assertTrue(latch.await(10, TimeUnit.SECONDS))
        assertTrue(ok)
    }

    // transformFileAsync 成功（TS）
    @Test
    fun `transformFileAsync ts success suspend`() = runBlocking {
        val output = swc.transformFileAsync(
            filepath = getResource("test.ts"),
            isModule = false,
            options = options {
                jsc = jscConfig {
                    parser = tsParseOptions { }
                }
            }
        )
        assertNotNull(output.code)
    }
}



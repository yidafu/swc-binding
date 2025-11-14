package dev.yidafu.swc.generator.result

import dev.yidafu.swc.generator.test.assertEquals
import dev.yidafu.swc.generator.test.assertFalse
import dev.yidafu.swc.generator.test.assertNull
import dev.yidafu.swc.generator.test.assertTrue
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test

class GeneratorResultTest : AnnotationSpec() {

    @Test
    fun `test success result`() {
        val result = GeneratorResultFactory.success("test")

        assertTrue(result.isSuccess())
        assertFalse(result.isFailure())
        assertEquals("test", result.getOrThrow())
    }

    @Test
    fun `test failure result`() {
        val result = GeneratorResultFactory.failure<String>(
            ErrorCode.UNKNOWN,
            "Test error"
        )

        assertTrue(result.isFailure())
        assertFalse(result.isSuccess())

        result.onFailure { error ->
            assertEquals(ErrorCode.UNKNOWN, error.code)
            assertEquals("Test error", error.message)
        }
    }

    @Test
    fun `test onSuccess callback`() {
        var callbackExecuted = false
        val result = GeneratorResultFactory.success("test")

        result.onSuccess {
            callbackExecuted = true
            assertEquals("test", it)
        }

        assertTrue(callbackExecuted)
    }

    @Test
    fun `test onFailure callback`() {
        var callbackExecuted = false
        val result = GeneratorResultFactory.failure<String>(
            ErrorCode.UNKNOWN,
            "Test error"
        )

        result.onFailure { error ->
            callbackExecuted = true
            assertEquals("Test error", error.message)
        }

        assertTrue(callbackExecuted)
    }

    @Test
    fun `test getOrNull with success`() {
        val result = GeneratorResultFactory.success("test")
        assertEquals("test", result.getOrNull())
    }

    @Test
    fun `test getOrNull with failure`() {
        val result = GeneratorResultFactory.failure<String>(
            ErrorCode.UNKNOWN,
            "Test error"
        )
        assertNull(result.getOrNull())
    }

    @Test
    fun `test getOrDefault with success`() {
        val result = GeneratorResultFactory.success("test")
        assertEquals("test", result.getOrDefault("default"))
    }

    @Test
    fun `test getOrDefault with failure`() {
        val result = GeneratorResultFactory.failure<String>(
            ErrorCode.UNKNOWN,
            "Test error"
        )
        assertEquals("default", result.getOrDefault("default"))
    }

    @Test
    fun `test map success`() {
        val result = GeneratorResultFactory.success(5)
        val mapped = result.map { it * 2 }

        assertTrue(mapped.isSuccess())
        assertEquals(10, mapped.getOrThrow())
    }

    @Test
    fun `test map failure`() {
        val result = GeneratorResultFactory.failure<Int>(
            ErrorCode.UNKNOWN,
            "Test error"
        )
        val mapped = result.map { it * 2 }

        assertTrue(mapped.isFailure())
    }

    @Test
    fun `test flatMap success`() {
        val result = GeneratorResultFactory.success(5)
        val flatMapped = result.flatMap { GeneratorResultFactory.success(it * 2) }

        assertTrue(flatMapped.isSuccess())
        assertEquals(10, flatMapped.getOrThrow())
    }

    @Test
    fun `test flatMap failure`() {
        val result = GeneratorResultFactory.failure<Int>(
            ErrorCode.UNKNOWN,
            "Test error"
        )
        val flatMapped = result.flatMap { GeneratorResultFactory.success(it * 2) }

        assertTrue(flatMapped.isFailure())
    }
}

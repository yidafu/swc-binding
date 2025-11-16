package dev.yidafu.swc

import kotlin.test.assertEquals
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test

class SwcExceptionTest : AnnotationSpec() {

    @Test
    fun `create SwcException with message`() {
        val exception = SwcException("Test error message")

        assertEquals("Test error message", exception.message)
    }

    @Test
    fun `SwcException is RuntimeException`() {
        val exception = SwcException("Test error")

        exception.shouldBeInstanceOf<RuntimeException>()
    }

    @Test
    fun `throw and catch SwcException`() {
        try {
            throw SwcException("Test exception")
        } catch (e: SwcException) {
            assertEquals("Test exception", e.message)
        }
    }

    @Test
    fun `SwcException with empty message`() {
        val exception = SwcException("")

        assertNotNull(exception)
        assertEquals("", exception.message)
    }

    @Test
    fun `SwcException with long message`() {
        val longMessage = "This is a very long error message that contains " +
            "multiple pieces of information about what went wrong " +
            "during the parsing or transformation process"

        val exception = SwcException(longMessage)

        assertEquals(longMessage, exception.message)
    }

    @Test
    fun `SwcException with special characters`() {
        val message = "Error: Parse failed at line 10, column 5\nUnexpected token: '}'"
        val exception = SwcException(message)

        assertEquals(message, exception.message)
    }

    @Test
    fun `SwcException can be caught as RuntimeException`() {
        try {
            throw SwcException("Test")
        } catch (e: RuntimeException) {
            e.shouldBeInstanceOf<SwcException>()
        }
    }

    @Test
    fun `SwcException stack trace`() {
        val exception = SwcException("Test error")

        assertNotNull(exception.stackTrace)
        assert(exception.stackTrace.isNotEmpty())
    }

    @Test
    fun `SwcException with unicode message`() {
        val message = "解析错误：意外的标记 '}'  at 第 10 行"
        val exception = SwcException(message)

        assertEquals(message, exception.message)
    }

    @Test
    fun `multiple SwcExceptions with different messages`() {
        val exception1 = SwcException("Error 1")
        val exception2 = SwcException("Error 2")

        assertEquals("Error 1", exception1.message)
        assertEquals("Error 2", exception2.message)
        assert(exception1.message != exception2.message)
    }
}

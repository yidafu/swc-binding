package dev.yidafu.swc

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SwcExceptionTest : ShouldSpec({

    should("create SwcException with message") {
        val exception = SwcException("Test error message")

        assertEquals("Test error message", exception.message)
    }

    should("SwcException is RuntimeException") {
        val exception = SwcException("Test error")

        exception.shouldBeInstanceOf<RuntimeException>()
    }

    should("throw and catch SwcException") {
        try {
            throw SwcException("Test exception")
        } catch (e: SwcException) {
            assertEquals("Test exception", e.message)
        }
    }

    should("SwcException with empty message") {
        val exception = SwcException("")

        assertNotNull(exception)
        assertEquals("", exception.message)
    }

    should("SwcException with long message") {
        val longMessage = "This is a very long error message that contains " +
            "multiple pieces of information about what went wrong " +
            "during the parsing or transformation process"

        val exception = SwcException(longMessage)

        assertEquals(longMessage, exception.message)
    }

    should("SwcException with special characters") {
        val message = "Error: Parse failed at line 10, column 5\nUnexpected token: '}'"
        val exception = SwcException(message)

        assertEquals(message, exception.message)
    }

    should("SwcException can be caught as RuntimeException") {
        try {
            throw SwcException("Test")
        } catch (e: RuntimeException) {
            e.shouldBeInstanceOf<SwcException>()
        }
    }

    should("SwcException stack trace") {
        val exception = SwcException("Test error")

        assertNotNull(exception.stackTrace)
        assert(exception.stackTrace.isNotEmpty())
    }

    should("SwcException with unicode message") {
        val message = "解析错误：意外的标记 '}'  at 第 10 行"
        val exception = SwcException(message)

        assertEquals(message, exception.message)
    }

    should("multiple SwcExceptions with different messages") {
        val exception1 = SwcException("Error 1")
        val exception2 = SwcException("Error 2")

        assertEquals("Error 1", exception1.message)
        assertEquals("Error 2", exception2.message)
        assert(exception1.message != exception2.message)
    }
})

package dev.yidafu.swc.generator.test

import io.kotest.assertions.fail
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

fun assertTrue(condition: Boolean, message: String? = null) {
    if (!condition) fail(message ?: "Expected condition to be true")
}

fun assertFalse(condition: Boolean, message: String? = null) {
    if (condition) fail(message ?: "Expected condition to be false")
}

fun <T> assertEquals(expected: T, actual: T, message: String? = null) {
    actual shouldBe expected
}

fun <T> assertNotEquals(illegal: T, actual: T, message: String? = null) {
    actual shouldNotBe illegal
}

fun <T : Any> assertNotNull(actual: T?, message: String? = null): T {
    actual.shouldNotBeNull()
    return actual
}

fun <T : Any> assertNull(actual: T?, message: String? = null) {
    actual.shouldBeNull()
}

inline fun <reified T : Throwable> assertFailsWith(noinline block: () -> Unit): T {
    return shouldThrow<T> { block() }
}


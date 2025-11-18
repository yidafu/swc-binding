package dev.yidafu.swc

import dev.yidafu.swc.generated.Span
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Utility object for creating Span objects.
 *
 * This object provides static methods that are Java-friendly.
 * Java code can call these methods directly without needing Kotlin extension functions.
 */
object Spans {
    /**
     * Create a Span object with specified start, end, and context values.
     *
     * A Span represents a location in the source code. It's used throughout
     * the AST to track where each node appears in the original source.
     *
     * This method is available as a static method in Java.
     *
     * @param start Starting byte position in the source code (default: 0)
     * @param end Ending byte position in the source code (default: 0)
     * @param ctxt Syntax context, used for macro expansion tracking (default: 0)
     * @return Configured Span object
     *
     * @example Kotlin usage:
     * ```kotlin
     * val span1 = span(start = 10, end = 20, ctxt = 0)
     * ```
     *
     * @example Java usage:
     * ```java
     * Span span = Spans.create(10, 20, 0);
     * ```
     */
    @JvmStatic
    @JvmOverloads
    fun create(
        start: Int = 0,
        end: Int = 0,
        ctxt: Int = 0
    ): Span = Span().apply {
        this.start = start
        this.end = end
        this.ctxt = ctxt
    }

    /**
     * Create an empty span with all values set to 0.
     *
     * This is useful when creating AST nodes programmatically where
     * the exact source positions are not important.
     *
     * This method is available as a static method in Java.
     *
     * @return Span with start=0, end=0, ctxt=0
     *
     * @example Kotlin usage:
     * ```kotlin
     * val span = emptySpan()
     * ```
     *
     * @example Java usage:
     * ```java
     * Span span = Spans.empty();
     * ```
     */
    @JvmStatic
    fun empty(): Span = create(0, 0, 0)
}

/**
 * Create a Span object with specified start, end, and context values.
 *
 * A Span represents a location in the source code. It's used throughout
 * the AST to track where each node appears in the original source.
 *
 * This is a Kotlin extension function. For Java, use [Spans.create] instead.
 *
 * @param start Starting byte position in the source code
 * @param end Ending byte position in the source code
 * @param ctxt Syntax context (used for macro expansion tracking)
 * @param block Optional lambda to further configure the span
 * @return Configured Span object
 *
 * @example
 * ```kotlin
 * // Create a span for code at positions 10-20
 * val span1 = span(start = 10, end = 20, ctxt = 0)
 *
 * // Create a span with additional configuration
 * val span2 = span(start = 0, end = 5) {
 *     // Additional properties can be set here if needed
 * }
 * ```
 */
@JvmOverloads
fun span(
    start: Int = 0,
    end: Int = 0,
    ctxt: Int = 0,
    block: Span.() -> Unit = {}
): Span = Span().apply {
    this.start = start
    this.end = end
    this.ctxt = ctxt
    block()
}

/**
 * Create an empty span with all values set to 0.
 *
 * This is useful when creating AST nodes programmatically where
 * the exact source positions are not important.
 *
 * This is a Kotlin extension function. For Java, use [Spans.empty] instead.
 *
 * @return Span with start=0, end=0, ctxt=0
 *
 * @example
 * ```kotlin
 * val identifier = identifier {
 *     value = "x"
 *     span = emptySpan() // Use empty span when position doesn't matter
 * }
 * ```
 */
fun emptySpan(): Span = span(start = 0, end = 0, ctxt = 0)

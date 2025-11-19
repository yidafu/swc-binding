package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2016 (ES7) features
 */
class ES2016FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse Array Includes") {
        val code = """
            const array = [1,2,3,4,5,6];
            if(array.includes(5)){
              console.log("Found an element");
            }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Array Includes - with NaN") {
        val code = """
            let numbers = [1, 2, 3, 4, NaN, ,];
            console.log(numbers.includes(NaN));
            console.log(numbers.includes(undefined));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Exponentiation Operator") {
        val code = """
            const cube1 = x => x ** 3;
            console.log(cube1(3));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})

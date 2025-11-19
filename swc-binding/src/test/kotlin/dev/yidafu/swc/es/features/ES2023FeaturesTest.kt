package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2023 (ES14) features
 */
class ES2023FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse Find array from last - findLast") {
        val code = """
            const isOdd = (number) => number % 2 === 1;
            const numbers = [1, 2, 3, 4, 5];
            console.log(numbers.findLast(isOdd));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Find array from last - findLastIndex") {
        val code = """
            const isOdd = (number) => number % 2 === 1;
            const numbers = [1, 2, 3, 4, 5];
            console.log(numbers.findLastIndex(isOdd));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Hashbang syntax") {
        val code = """
            #!/usr/bin/env node
            'use strict';
            console.log("Hello world from hashbang syntax");
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Symbols as weakmap keys") {
        val code = """
            const weak = new WeakMap();
            const key = Symbol("ref");
            weak.set(key, "ES2023");
            console.log(weak.get(key));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Change array by copy - toReversed") {
        val code = """
            const numbers = [1, 3, 2, 4, 5];
            const reversedArray = numbers.toReversed();
            console.log(reversedArray);
            console.log(numbers);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Change array by copy - toSorted") {
        val code = """
            const numbers = [1, 3, 2, 4, 5];
            const sortedArray = numbers.toSorted();
            console.log(sortedArray);
            console.log(numbers);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Change array by copy - toSpliced") {
        val code = """
            const numbers = [1, 3, 2, 4, 5];
            const splicedArray = numbers.toSpliced(1, 3);
            console.log(splicedArray);
            console.log(numbers);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Change array by copy - with") {
        val code = """
            const numbers = [1, 3, 2, 4, 5];
            const replaceWithArray = numbers.with(2, 10);
            console.log(replaceWithArray);
            console.log(numbers);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})


package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2019 (ES10) features
 */
class ES2019FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse Array flat") {
        val code = """
            const numberArray = [[1, 2], [[3], 4], [5, 6]];
            const flattenedArrOneLevel = numberArray.flat(1);
            const flattenedArrTwoLevel = numberArray.flat(2);
            console.log(flattenedArrOneLevel);
            console.log(flattenedArrTwoLevel);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Array flatMap") {
        val code = """
            const numberArray1 = [[1], [2], [3], [4], [5]];
            console.log(numberArray1.flatMap(value => [value * 10]));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Object fromEntries") {
        val code = """
            const arr = [ ['a', '1'], ['b', '2'], ['c', '3'] ];
            const obj = Object.fromEntries(arr);
            console.log(obj);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse String trimStart and trimEnd") {
        val code = """
            let messageTwo = "   Hello World!!    ";
            console.log(messageTwo.trimStart());
            console.log(messageTwo.trimEnd());
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Symbol description") {
        val code = """
            console.log(Symbol('one').description);
            console.log(Symbol.for('one').description);
            console.log(Symbol('').description);
            console.log(Symbol().description);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Optional catch binding") {
        val code = """
            let isTheFeatureImplemented = false;
            try {
              if(isFeatureSupported()) {
                isTheFeatureImplemented = true;
              }
            } catch {}
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse JSON Improvements - parse with U+2028") {
        val code = """
            console.log(JSON.parse('"\\u2028"'));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Array Stable Sort") {
        val code = """
            const users = [
                { name: "Albert",  age: 30 },
                { name: "Bravo",   age: 30 },
                { name: "Colin",   age: 30 },
                { name: "Rock",    age: 50 },
                { name: "Sunny",   age: 50 }
            ]
            users.sort((a, b) => a.age - b.age);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Function.toString()") {
        val code = """
            function sayHello(message) {
                let msg = message;
                console.log(`Hello, ${'$'}{msg}`);
            }
            console.log(sayHello.toString());
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Private Class Variables") {
        val code = """
            class User {
              #message = "Welcome to ES2020"
              login() { console.log(this.#message) }
            }
            const user = new User()
            user.login()
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})


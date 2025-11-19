package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2017 (ES8) features
 */
class ES2017FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse Async functions") {
        val code = """
            async function logger() {
              let data = await fetch('http://someapi.com/users');
              console.log(data)
            }
            logger();
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Object values") {
        val code = """
            const countries = {
              IN: 'India',
              SG: 'Singapore',
            }
            Object.values(countries);
            console.log(Object.values(['India', 'Singapore']));
            console.log(Object.values('India'));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Object entries") {
        val code = """
            const countries = {
              IN: 'India',
              SG: 'Singapore',
            }
            Object.entries(countries);
            const countriesArr = ['India', 'Singapore'];
            console.log(Object.entries(countriesArr));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Object property descriptors") {
        val code = """
            const profile = {
              age: 42
            };
            const descriptors = Object.getOwnPropertyDescriptors(profile);
            console.log(descriptors);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse String padding - padStart") {
        val code = """
            const cardNumber = '01234567891234';
            const lastFourDigits = cardNumber.slice(-4);
            const maskedCardNumber = lastFourDigits.padStart(cardNumber.length, '*');
            console.log(maskedCardNumber);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse String padding - padEnd") {
        val code = """
            const label1 = "Name";
            const value1 = "John"
            console.log((label1 + ': ').padEnd(20, ' ') + value1);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Shared memory and atomics") {
        val code = """
            const sharedMemory = new SharedArrayBuffer(1024);
            const sharedArray = new Uint8Array(sharedMemory);
            sharedArray[0] = 10;
            Atomics.add(sharedArray, 0, 20);
            console.log(Atomics.load(sharedArray, 0));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Trailing commas") {
        val code = """
            function func(a,b,) {
              console.log(a, b);
            }
            func(1,2,);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})


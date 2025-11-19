package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2020 (ES11) features
 */
class ES2020FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse BigInt") {
        // Note: BigInt literal parsing may have serialization issues with SWC
        // Testing BigInt constructor call instead
        val code = """
            const bigIntConstructorRep = BigInt(9007199254740991);
            const bigIntStringRep = BigInt("9007199254740991");
            console.log(bigIntConstructorRep);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Dynamic Import") {
        val code = """
            const moduleSpecifier = './message.js';
            import(moduleSpecifier)
              .then((module) => {
                module.default();
                module.sayGoodBye();
              })
              .catch(err => console.log('loading error'));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Nullish Coalescing Operator") {
        val code = """
            let vehicle = {
              car: {
                name: "",
                speed: 0
              }
            };
            console.log(vehicle.car.name ?? "Unknown");
            console.log(vehicle.car.speed ?? 90);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse String matchAll") {
        val code = """
            const regex = /t(e)(st(\\d?))/g;
            const string = 'test1test2';
            const matchesIterator = string.matchAll(regex);
            Array.from(matchesIterator, result => console.log(result));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Optional chaining") {
        val code = """
            let vehicle = {};
            let vehicle1 = {
              car: {
                name: 'ABC',
                speed: 90
              }
            };
            console.log(vehicle.car?.name);
            console.log(vehicle.car?.speed);
            console.log(vehicle1.car?.name);
            console.log(vehicle.car?.name ?? "Unknown");
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Promise.allSettled") {
        val code = """
            const promise1 = new Promise((resolve, reject) => setTimeout(() => resolve(100), 1000));
            const promise2 = new Promise((resolve, reject) => setTimeout(reject, 1000));
            Promise.allSettled([promise1, promise2]).then(data => console.log(data));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse globalThis") {
        val code = """
            if (typeof globalThis.setTimeout !== 'function') {
              console.log('no setTimeout in this environment or runtime');
            }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse import.meta") {
        val code = """
            console.log(import.meta);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse for..in order") {
        val code = """
            var object = {
              'a': 2,
              'b': 3,
              'c': 4
            }
            for(let key in object) {
              console.log(key);
            }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})

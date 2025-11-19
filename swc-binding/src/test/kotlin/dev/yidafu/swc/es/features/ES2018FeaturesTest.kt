package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2018 (ES9) features
 */
class ES2018FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse Async iterators") {
        val code = """
            const arr = ['a', 'b', 'c', 'd'];
            const syncIterator = arr[Symbol.iterator]();
            console.log(syncIterator.next());
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Object rest and spread operators - rest") {
        val code = """
            function myfunc1({ a, ...x }) {
              console.log(a, x);
            }
            myfunc1({
              a: 1,
              b: 2,
              c: 3,
              d: 4
            });
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Object rest and spread operators - spread") {
        val code = """
            const myObject = { a: 1, b: 2, c: 3, d:4 };
            const myNewObject = { ...myObject, e: 5 };
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Promise finally") {
        val code = """
            let isLoading = true;
            fetch('http://somesite.com/users')
               .then(data => data.json())
               .catch(err => console.error(err))
               .finally(() => {
                 isLoading = false;
                 console.log('Finished loading!!');
               })
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})

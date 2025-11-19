package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2021 (ES12) features
 */
class ES2021FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse replaceAll") {
        val code = """
            console.log('10101010'.replaceAll('0', '1'));
            console.log('01010101'.replaceAll('0', '1'));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse promise.any") {
        val code = """
            let promise1 = new Promise((resolve) => setTimeout(resolve, 100, 'Resolves after 100ms'));
            let promise2 = new Promise((resolve) => setTimeout(resolve, 200, 'Resolves after 200ms'));
            let promise3 = new Promise((resolve, reject) => setTimeout(reject, 0) );
            let promises = [promise1, promise2, promise3];
            Promise.any(promises)
                .then( value => console.log(value));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse WeakRef") {
        val code = """
            const myObject = new WeakRef({
              name: 'Sudheer',
              age: 34
            });
            console.log(myObject.deref());
            console.log(myObject.deref().name);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Numeric Separators") {
        val code = """
            const billion = 1000_000_000;
            console.log(billion);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Logical Operators - &&=") {
        val code = """
            let x = 10;
            let y = 20;
            x &&= y;
            console.log(x);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Logical Operators - ||=") {
        val code = """
            let x = 0;
            let y = 20;
            x ||= y;
            console.log(x);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Logical Operators - ??=") {
        val code = """
            let x;
            let y = 1;
            x ??= y;
            console.log(x);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})


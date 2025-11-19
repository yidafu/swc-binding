package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2025 (ES16) features
 */
class ES2025FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse Set Methods - union") {
        val code = """
            const set1 = new Set([1, 2, 3, 4, 5]);
            const set2 = new Set([3, 4, 5, 6, 7]);
            const union = set1.union(set2);
            console.log([...union]);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Set Methods - intersection") {
        val code = """
            const set1 = new Set([1, 2, 3, 4, 5]);
            const set2 = new Set([3, 4, 5, 6, 7]);
            const intersection = set1.intersection(set2);
            console.log([...intersection]);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Set Methods - difference") {
        val code = """
            const set1 = new Set([1, 2, 3, 4, 5]);
            const set2 = new Set([3, 4, 5, 6, 7]);
            const difference = set1.difference(set2);
            console.log([...difference]);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Set Methods - symmetricDifference") {
        val code = """
            const set1 = new Set([1, 2, 3, 4, 5]);
            const set2 = new Set([3, 4, 5, 6, 7]);
            const symDifference = set1.symmetricDifference(set2);
            console.log([...symDifference]);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Iterator Helpers - filter and map") {
        val code = """
            function* generateNumbers() {
              yield 1;
              yield 2;
              yield 3;
              yield 4;
              yield 5;
            }
            const result = generateNumbers()[Symbol.iterator]()
              .filter(x => x > 1)
              .map(x => x * 10)
              .take(3)
              .toArray();
            console.log(result);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Temporal API ES2025") {
        val code = """
            const today = Temporal.Now.plainDate();
            console.log(today.toString());
            const date = Temporal.PlainDate.from("2025-06-01");
            console.log("Year:", date.year);
            const futureDate = date.add({ months: 1, days: 15 });
            console.log(futureDate.toString());
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Decorator Metadata") {
        val code = """
            function apiEndpoint(path, method = "GET") {
              return function(target, context) {
                context.metadata = { 
                  ...context.metadata,
                  api: { path, method }
                };
                return target;
              };
            }
            class UserService {
              @apiEndpoint("/users", "GET")
              getAllUsers() {
              }
            }
        """.trimIndent()

        val output = swcNative.parseSync(
            code,
            tsParseOptions {
                decorators = true
            },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }
})


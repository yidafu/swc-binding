package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2022 (ES13) features
 */
class ES2022FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse Top-level await") {
        val code = """
            let posts = await posts();
        """.trimIndent()

        val output = swcNative.parseSync(
            code,
            esParseOptions {
                topLevelAwait = true
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Top-level await - dynamic import") {
        val code = """
            const messages = await import(`./messages-${'$'}{language}.js`);
        """.trimIndent()

        val output = swcNative.parseSync(
            code,
            esParseOptions {
                topLevelAwait = true
            },
            "test.js"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Class fields - public and private") {
        val code = """
            class Employee  {
              name = "John";
              #age=35;
              constructor() {
              }
              #getAge() {
                return this.#age
              }
            }
            const employee = new Employee();
            employee.name = "Jack";
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Array .at() method") {
        val code = """
            const array = [1, 2, 3, 4, 5];
            console.log(array.at(-2));
            const string = '12345';
            console.log(string.at(-2));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Error Cause") {
        val code = """
            function processUserData(arrayData) {
               return arrayData.map(data => {
                   try {
                     const json = JSON.parse(data);
                     return json;
                   } catch (err) {
                     throw new Error(
                       `Data processing failed`,
                       {cause: err}
                     );
                   }
                 });
             }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse hasOwn") {
        val code = """
            const user = {
              age: 35, 
              hasOwnProperty: ()=> {
                return false;
              }
            };
            Object.hasOwn(user, 'age');
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Regex match indices") {
        val code = """
            const regexPatter = /(Jack)/gd;
            const input = 'Authos: Jack, Alexander and Jacky';
            const result = [...input.matchAll(regexPatter)];
            console.log(result[0]);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})


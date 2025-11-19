package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2024 (ES15) features
 */
class ES2024FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse GroupBy into objects - Object.groupBy") {
        val code = """
            const persons = [
              {name:"John", age:70},
              {name:"Kane", age:5},
              {name:"Jack", age:50},
              {name:"Rambo", age:15}
            ];
            function callbackFunc({ age }) {
              if(age >= 60) {
                  return "senior";
              } else if(age > 17 && age < 60) {
                  return "adult";
              }
              else {
                  return "kid";
              }
            }
            const result = Object.groupBy(persons, callbackFunc);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse GroupBy into objects - Map.groupBy") {
        val code = """
            const persons = [
              {name:"John", age:70},
              {name:"Kane", age:5},
              {name:"Jack", age:50}
            ];
            function callbackFunc({ age }) {
              if(age >= 60) {
                  return "senior";
              } else if(age > 17 && age < 60) {
                  return "adult";
              }
              else {
                  return "kid";
              }
            }
            const result = Map.groupBy(persons, callbackFunc);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Temporal API") {
        val code = """
            const today = Temporal.Now.plainDate();
            console.log(today.toString());
            const date = Temporal.PlainDate.from("2025-06-01");
            console.log("Year:", date.year);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Well formed unicode strings - isWellFormed") {
        val code = """
            const str1 = "Hello World \\uD815";
            const str2 = "Welcome to ES2024";
            console.log(str1.isWellFormed());
            console.log(str2.isWellFormed());
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Well formed unicode strings - toWellFormed") {
        val code = """
            const str1 = "Hello World \\uD815";
            const str2 = "Welcome to ES2024";
            console.log(str1.toWellFormed());
            console.log(str2.toWellFormed());
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Atomic waitSync") {
        val code = """
            const arrayBuffer = new SharedArrayBuffer(1024);
            const arr = new Int32Array(arrayBuffer);
            Atomics.waitAsync(arr, 0 , 0 , 500);
            Atomics.notify(arr, 0);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Promise withResolvers") {
        val code = """
            const { promise, resolve, reject} = Promise.withResolvers();
            setTimeout(() =>  { Math.random() > 0.5 ? resolve("Success") : reject("Error")},1000);
            promise.then(result => console.log(result)).catch(error => console.error(error));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})

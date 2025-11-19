package dev.yidafu.swc.es.features

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * Tests for ES2015 (ES6) features
 */
class ES2015FeaturesTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse Variable Scoping - let") {
        val code = """
            let a = 1;
            if (a === 1) {
              let a = 2;
              console.log(a);
            }
            console.log(a);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Variable Scoping - const") {
        val code = """
            const x = 1;
            if (x === 1) {
                const y = 2;
                console.log(y);
            }
            console.log(x);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Arrow functions - basic") {
        val code = """
            var multiplyArrowFunc = (a, b) => a * b;
            console.log(multiplyArrowFunc(2, 5));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Arrow functions - single parameter") {
        val code = """
            const message = name => console.log("Hello, " + name + "!");
            message("Sudheer");
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Arrow functions - multiple parameters") {
        val code = """
            const multiply = (x, y) => x * y;
            console.log(multiply(2, 5));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Arrow functions - with body") {
        val code = """
            const even = number => {
              if(number%2) {
                console.log("Even");
              } else {
                console.log("Odd");
              }
            }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Arrow functions - no parameters") {
        val code = """
            const greet = () => console.log('Hello World!');
            greet();
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Classes - class declaration") {
        val code = """
            class Square {
              constructor(length) {
                this.length = length;
              }
              get area() {
                return this.length * this.length;
              }
              set length(value) {
                this.length = value;
              }
            }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Classes - class expression") {
        val code = """
            const square = class Square {
              constructor(length) {
                this.length = length;
              }
              get area() {
                return this.length * this.length;
              }
            }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Classes - inheritance") {
        val code = """
            class Vehicle {
              constructor(name) {
                this.name = name;
              }
              start() {
                console.log(`${'$'}{this.name} vehicle started`);
              }
            }
            class Car extends Vehicle {
              start() {
                console.log(`${'$'}{this.name} car started`);
              }
            }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Enhanced object literals - property shorthand") {
        val code = """
            const a = 1, b = 2, c = 3;
            const obj = {
              a,
              b,
              c
            };
            console.log(obj);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Enhanced object literals - method shorthand") {
        val code = """
            const calculation = {
              sum(a, b) { return a + b; },
              multiply(a, b) { return a * b; }
            };
            console.log(calculation.sum(5, 3));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Enhanced object literals - computed property names") {
        val code = """
            const key = 'three';
            const computedObj = {
              one: 1,
              two: 2,
              [key]: 3
            };
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Template literals") {
        val code = """
            const firstName = 'John';
            console.log(`Hello ${'$'}{firstName}!
            Good morning!`);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Template literals - tagged") {
        val code = """
            const Button = styled.a`
              display: inline-block;
              border-radius: 3px;
            `
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Destructuring - object") {
        val code = """
            const user = { firstName: 'John', lastName: 'Kary' };
            const {firstName, lastName} = user;
            console.log(firstName, lastName);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Destructuring - array") {
        val code = """
            const [one, two, three] = ['one', 'two', 'three'];
            console.log(one, two, three);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Default parameters") {
        val code = """
            function add(a = 10, b = 20) {
              return a + b;
            }
            add(20);
            add();
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Rest parameter") {
        val code = """
            function sum(...args) {
              return args.reduce((previous, current) => {
                return previous + current;
              });
            }
            console.log(sum(1, 2, 3));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Spread Operator - function calls") {
        val code = """
            console.log(Math.max(...[-10, 30, 10, 20]));
            console.log(Math.max(-10, ...[-50, 10], 30));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Spread Operator - array literals") {
        val code = """
            console.log([1, ...[2,3], 4, ...[5, 6, 7]]);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Iterators & For..of") {
        val code = """
            const collection = {
              one: 1,
              two: 2,
              three: 3,
              [Symbol.iterator]() {
                const values = Object.keys(this);
                let i = 0;
                return {
                  next: () => {
                    return {
                      value: this[values[i++]],
                      done: i > values.length
                    }
                  }
                };
              }
            };
            for (const value of collection) {
              console.log(value);
            }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Generators") {
        val code = """
            function* myGenerator(i) {
              yield i + 10;
              yield i + 20;
              return i + 30;
            }
            const myGenObj = myGenerator(10);
            console.log(myGenObj.next().value);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Modules - named exports") {
        val code = """
            const PI = Math.PI;
            function add(...args) {
              return args.reduce((num, tot) => tot + num);
            }
            export { PI, add };
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Modules - default export") {
        val code = """
            export default function add(...args) {
              return args.reduce((num, tot) => tot + num);
            }
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Modules - import statements") {
        val code = """
            import * as name from "my-module";
            import { export1 } from "my-module";
            import { export1 , export2 } from "my-module";
            import defaultExport from "my-module";
            import { export1 as alias1 } from "my-module";
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Set") {
        val code = """
            let mySet = new Set()
            mySet.add(1);
            mySet.add(2);
            mySet.add(2);
            mySet.add('some text here');
            console.log(mySet.size);
            console.log(mySet.has(2));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse WeakSet") {
        val code = """
            let myUserSet = new WeakSet();
            let john = { name: "John" };
            let rocky = { name: "Rocky" };
            myUserSet.add(john);
            myUserSet.add(rocky);
            console.log(myUserSet.has(john));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Map") {
        val code = """
            let typeMap = new Map();
            var keyObj = {'one': 1}
            typeMap.set('10', 'string');
            typeMap.set(10, 'number');
            typeMap.set(true, 'boolean');
            typeMap.set(keyObj, 'object');
            console.log(typeMap.get(10));
            console.log(typeMap.size);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse WeakMap") {
        val code = """
            var weakMap = new WeakMap();
            var obj1 = {}
            var obj2 = {}
            weakMap.set(obj1, 1);
            weakMap.set(obj2, 2);
            console.log(weakMap.get(obj2));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Unicode - new string form") {
        val code = """
            let str = '𠮷';
            console.log('\\u{20BB7}');
            console.log(new RegExp('\\u{20BB7}', 'u'));
            console.log(/^.$/u.test(str));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Symbols") {
        val code = """
            let id = Symbol("id");
            let user = {
              name: "John",
              age: 40,
              [id]: 111
            };
            console.log("User Id: " + user[id]);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Proxies") {
        val code = """
            const target = {
              name: "John",
              age: 3
            };
            const handler = {
              get: function(target, prop) {
                return prop in target ?
                    target[prop] :
                    `${'$'}{prop} does not exist`;
              }
            };
            const user = new Proxy(target, handler);
            console.log(user.name);
            console.log(user.gender);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Promises") {
        val code = """
            const promise = new Promise(function(resolve, reject) {
              setTimeout(() => resolve(1), 1000);
            });
            promise.then(function(result) {
                console.log(result);
                return result * 2;
              }).then(function(result) {
                console.log(result);
                return result * 3;
              }).catch(function(error){
                console.log(error);
              });
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Reflect - construct") {
        val code = """
            class User {
              constructor(firstName, lastName) {
                this.firstName = firstName;
                this.lastName = lastName;
              }
              get fullName() {
                return `${'$'}{this.firstName} ${'$'}{this.lastName}`;
              }
            };
            let args = ['John', 'Emma'];
            let john = Reflect.construct(User, args);
            console.log(john instanceof User);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Reflect - apply") {
        val code = """
            const max = Reflect.apply(Math.max, Math, [100, 200, 300]);
            console.log(max);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Binary and Octal - binary literals") {
        val code = """
            const num = 0b110;
            console.log(num);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Binary and Octal - octal literals") {
        val code = """
            const num = 0o55;
            console.log(num);
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Proper Tail Calls") {
        val code = """
            function factorial(n, acc = 1) {
              if (n === 0) {
                return acc
              }
              return factorial(n - 1, n * acc)
            }
            console.log(factorial(5));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Array find methods - find") {
        val code = """
            let arr = [2, 4, 5, 6, 8, 10];
            function isOdd(i) {
              return i % 2 !== 0;
            }
            console.log(arr.find(isOdd));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }

    should("parse Array find methods - findIndex") {
        val code = """
            let arr = [2, 4, 5, 6, 8, 10];
            function isOdd(i) {
              return i % 2 !== 0;
            }
            console.log(arr.findIndex(isOdd));
        """.trimIndent()

        val output = swcNative.parseSync(code, esParseOptions { }, "test.js")
        output.shouldBeInstanceOf<Module>()
    }
})

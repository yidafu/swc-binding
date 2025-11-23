const global_Li9sb2NhbC5qcw = (function(exports = {}) {
    const inline_Li9sb2NhbC0yLmpz = global_Li9sb2NhbC0yLmpz;
    const bar = inline_Li9sb2NhbC0yLmpz.bar;
    console.log('local.js', bar);
    const foo = "foo";
    exports.bar = bar;
    exports.foo = foo;
    return exports;
})();
const global_Li9sb2NhbC0yLmpz = (function(exports = {}) {
    const bar = "bar";
    exports.bar = bar;
    return exports;
})();
const inline_Li9sb2NhbC5qcw = global_Li9sb2NhbC5qcw;
const bar = inline_Li9sb2NhbC5qcw.bar;
console.log('main.js', bar);

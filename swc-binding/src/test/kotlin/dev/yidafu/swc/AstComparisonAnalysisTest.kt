package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import dev.yidafu.swc.util.NodeJsHelper
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ShouldSpec

/**
 * AST comparison analysis test - Find issues in Kotlin AST class definitions
 */
@Ignored
class AstComparisonAnalysisTest : ShouldSpec({

    beforeSpec {
        if (!NodeJsHelper.isNodeJsAvailable()) {
            println("⚠️  Node.js is not available, skipping comparison tests")
        }
    }

    should("Compare simple JavaScript variable declaration") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should

        val swcNative = SwcNative()
        val code = "const x = 42;"
        val options = esParseOptions { }

        val result = AstComparisonTool.compareAst(code, options, swcNative, "simple-var")
        AstComparisonTool.printReport(result)
    }

    should("Compare JavaScript function") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should

        val swcNative = SwcNative()
        val code = """
            function add(a, b) {
                return a + b;
            }
        """.trimIndent()
        val options = esParseOptions { }

        val result = AstComparisonTool.compareAst(code, options, swcNative, "function")
        AstComparisonTool.printReport(result)
    }

    should("Compare JSX syntax") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should

        val swcNative = SwcNative()
        val code = """
            function App() {
                return <div>Hello</div>;
            }
        """.trimIndent()
        val options = esParseOptions {
            jsx = true
        }

        val result = AstComparisonTool.compareAst(code, options, swcNative, "jsx")
        AstComparisonTool.printReport(result)
    }

    should("Compare TypeScript interface") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should

        val swcNative = SwcNative()
        val code = """
            interface User {
                name: string;
                age: number;
            }
        """.trimIndent()
        val options = tsParseOptions { }

        val result = AstComparisonTool.compareAst(code, options, swcNative, "typescript-interface")
        AstComparisonTool.printReport(result)
    }

    should("Compare complex JavaScript code") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should

        val swcNative = SwcNative()
        val code = """
            const obj = {
                a: 1,
                b: [2, 3],
                c: {
                    d: 4
                }
            };
            
            function test() {
                if (obj.a > 0) {
                    return obj.b.map(x => x * 2);
                }
                return [];
            }
        """.trimIndent()
        val options = esParseOptions { }

        val result = AstComparisonTool.compareAst(code, options, swcNative, "complex-js")
        AstComparisonTool.printReport(result)
    }

    should("Compare code with comments") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should

        val swcNative = SwcNative()
        val code = """
            // This is a comment
            const x = 42; // inline comment
        """.trimIndent()
        val options = esParseOptions {
            comments = true
        }

        val result = AstComparisonTool.compareAst(code, options, swcNative, "with-comments")
        AstComparisonTool.printReport(result)
    }

    should("Compare complex JSX syntax") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should

        val swcNative = SwcNative()
        val code = """
            function App() {
                const show = true;
                return (
                    <div>
                        {show && <p>Visible</p>}
                        <MyComponent prop1="value1" prop2={42} />
                    </div>
                );
            }
        """.trimIndent()
        val options = esParseOptions {
            jsx = true
        }

        val result = AstComparisonTool.compareAst(code, options, swcNative, "jsx-complex")
        AstComparisonTool.printReport(result)
    }
})

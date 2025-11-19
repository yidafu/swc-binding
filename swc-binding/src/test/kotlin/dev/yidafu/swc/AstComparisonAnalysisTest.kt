package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import dev.yidafu.swc.util.NodeJsHelper
import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.Ignore

/**
 * AST 对比分析测试 - 找出 Kotlin AST 类定义问题
 */
@Ignored
class AstComparisonAnalysisTest : ShouldSpec({
    
    beforeSpec {
        if (!NodeJsHelper.isNodeJsAvailable()) {
            println("⚠️  Node.js 不可用，跳过对比测试")
        }
    }
    
    should("对比简单 JavaScript 变量声明") {
        if (!NodeJsHelper.isNodeJsAvailable()) return@should
        
        val swcNative = SwcNative()
        val code = "const x = 42;"
        val options = esParseOptions { }
        
        val result = AstComparisonTool.compareAst(code, options, swcNative, "simple-var")
        AstComparisonTool.printReport(result)
    }
    
    should("对比 JavaScript 函数") {
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
    
    should("对比 JSX 语法") {
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
    
    should("对比 TypeScript 接口") {
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
    
    should("对比复杂 JavaScript 代码") {
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
    
    should("对比带注释的代码") {
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
    
    should("对比 JSX 复杂语法") {
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


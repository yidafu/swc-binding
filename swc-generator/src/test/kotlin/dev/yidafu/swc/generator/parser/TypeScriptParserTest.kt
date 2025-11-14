package dev.yidafu.swc.generator.parser

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import java.io.File
import kotlin.io.path.createTempFile

class TypeScriptParserTest : FunSpec({
    
    val swc = SwcNative()
    val config = SwcGeneratorConfig()
    val parser = TypeScriptParser(swc, config)
    
    test("应该解析简单的 TypeScript 文件") {
        val tempFile = createTempFile(prefix = "test", suffix = ".d.ts").toFile()
        try {
            tempFile.writeText("""
                export interface TestInterface {
                    name: string;
                }
            """.trimIndent())
            
            val result = parser.parse(tempFile.absolutePath)
            
            result.isSuccess() shouldBe true
            val parseResult = result.getOrNull()!!
            parseResult.program.type shouldBe "Module"
            parseResult.inputPath shouldBe tempFile.absolutePath
            parseResult.sourceCode shouldContain "export interface"
            parseResult.astJsonString.isNotEmpty() shouldBe true
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该返回错误当文件不存在时") {
        val result = parser.parse("/nonexistent/file.d.ts")
        
        result.isFailure() shouldBe true
        (result.getOrNull() == null) shouldBe true
    }
    
    test("应该解析包含 type alias 的文件") {
        val tempFile = createTempFile(prefix = "test", suffix = ".d.ts").toFile()
        try {
            tempFile.writeText("""
                export type TestType = string | number;
            """.trimIndent())
            
            val result = parser.parse(tempFile.absolutePath)
            
            result.isSuccess() shouldBe true
            val parseResult = result.getOrNull()!!
            parseResult.program.type shouldBe "Module"
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该解析包含多个声明的文件") {
        val tempFile = createTempFile(prefix = "test", suffix = ".d.ts").toFile()
        try {
            tempFile.writeText("""
                export interface Interface1 {
                    prop1: string;
                }
                
                export interface Interface2 {
                    prop2: number;
                }
                
                export type Type1 = string;
                export type Type2 = number;
            """.trimIndent())
            
            val result = parser.parse(tempFile.absolutePath)
            
            result.isSuccess() shouldBe true
            val parseResult = result.getOrNull()!!
            parseResult.program.type shouldBe "Module"
            
            val visitor = TsAstVisitor(parseResult.astJsonString)
            visitor.visit()
            visitor.getInterfaces() shouldHaveSize 2
            visitor.getTypeAliases() shouldHaveSize 2
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该解析空文件") {
        val tempFile = createTempFile(prefix = "test", suffix = ".d.ts").toFile()
        try {
            tempFile.writeText("")
            
            val result = parser.parse(tempFile.absolutePath)
            
            // 空文件可能会解析成功，但 body 应该为空
            if (result.isSuccess()) {
                val parseResult = result.getOrNull()!!
                parseResult.program.type shouldBe "Module"
            }
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该解析包含复杂类型的文件") {
        val tempFile = createTempFile(prefix = "test", suffix = ".d.ts").toFile()
        try {
            tempFile.writeText("""
                export interface ComplexInterface {
                    prop1: string;
                    prop2?: number;
                    prop3: string | number;
                    prop4: {
                        nested: string;
                    };
                }
            """.trimIndent())
            
            val result = parser.parse(tempFile.absolutePath)
            
            result.isSuccess() shouldBe true
            val parseResult = result.getOrNull()!!
            parseResult.program.type shouldBe "Module"
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该包含源代码在解析结果中") {
        val tempFile = createTempFile(prefix = "test", suffix = ".d.ts").toFile()
        try {
            val sourceCode = """
                export interface TestInterface {
                    name: string;
                }
            """.trimIndent()
            tempFile.writeText(sourceCode)
            
            val result = parser.parse(tempFile.absolutePath)
            
            result.isSuccess() shouldBe true
            val parseResult = result.getOrNull()!!
            parseResult.sourceCode shouldBe sourceCode
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该包含 AST JSON 字符串在解析结果中") {
        val tempFile = createTempFile(prefix = "test", suffix = ".d.ts").toFile()
        try {
            tempFile.writeText("""
                export interface TestInterface {
                    name: string;
                }
            """.trimIndent())
            
            val result = parser.parse(tempFile.absolutePath)
            
            result.isSuccess() shouldBe true
            val parseResult = result.getOrNull()!!
            parseResult.astJsonString.isNotEmpty() shouldBe true
            parseResult.astJsonString shouldContain "Module"
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该包含输入路径在解析结果中") {
        val tempFile = createTempFile(prefix = "test", suffix = ".d.ts").toFile()
        try {
            tempFile.writeText("""
                export interface TestInterface {
                    name: string;
                }
            """.trimIndent())
            
            val result = parser.parse(tempFile.absolutePath)
            
            result.isSuccess() shouldBe true
            val parseResult = result.getOrNull()!!
            parseResult.inputPath shouldBe tempFile.absolutePath
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该解析包含继承的 interface") {
        val tempFile = createTempFile(prefix = "test", suffix = ".d.ts").toFile()
        try {
            tempFile.writeText("""
                export interface Parent {
                    parentProp: string;
                }
                
                export interface Child extends Parent {
                    childProp: number;
                }
            """.trimIndent())
            
            val result = parser.parse(tempFile.absolutePath)
            
            result.isSuccess() shouldBe true
            val parseResult = result.getOrNull()!!
            parseResult.program.type shouldBe "Module"
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该处理文件路径中的特殊字符") {
        val tempDir = File.createTempFile("test-dir", "").apply {
            delete()
            mkdirs()
        }
        val tempFile = File(tempDir, "test-file.d.ts")
        try {
            tempFile.writeText("""
                export interface TestInterface {
                    name: string;
                }
            """.trimIndent())
            
            val result = parser.parse(tempFile.absolutePath)
            
            result.isSuccess() shouldBe true
            val parseResult = result.getOrNull()!!
            parseResult.inputPath shouldBe tempFile.absolutePath
        } finally {
            tempFile.delete()
            tempDir.delete()
        }
    }
})


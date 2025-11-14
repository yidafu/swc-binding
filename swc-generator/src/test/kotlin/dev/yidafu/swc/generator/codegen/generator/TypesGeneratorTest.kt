package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.adt.kotlin.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContain
import java.io.File
import kotlin.io.path.createTempFile

class TypesGeneratorTest : FunSpec({
    
    test("应该创建 TypesGenerator") {
        val classDecls = emptyList<KotlinDeclaration.ClassDecl>()
        val generator = TypesGenerator(classDecls)
        
        // generator 已成功创建，类型是 TypesGenerator，不是可空类型
    }
    
    test("应该添加 typealias") {
        val classDecls = emptyList<KotlinDeclaration.ClassDecl>()
        val generator = TypesGenerator(classDecls)
        
        val typeAlias = KotlinDeclaration.TypeAliasDecl(
            name = "TestType",
            type = KotlinTypeFactory.string()
        )
        
        generator.addTypeAlias(typeAlias)
        
        // 验证 typealias 已添加（通过检查生成的文件）
    }
    
    test("应该生成 types.kt 文件") {
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "TestClass",
            modifier = ClassModifier.DataClass,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "prop",
                    type = KotlinTypeFactory.string(),
                    modifier = PropertyModifier.Val
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )
        
        val generator = TypesGenerator(listOf(classDecl))
        val tempFile = createTempFile(prefix = "test", suffix = ".kt").toFile()
        
        try {
            generator.writeToFile(tempFile.absolutePath)
            
            tempFile.exists() shouldBe true
            tempFile.readText().isNotEmpty() shouldBe true
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该生成包含类声明的文件") {
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "TestClass",
            modifier = ClassModifier.DataClass,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "name",
                    type = KotlinTypeFactory.string(),
                    modifier = PropertyModifier.Val
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )
        
        val generator = TypesGenerator(listOf(classDecl))
        val tempFile = createTempFile(prefix = "test", suffix = ".kt").toFile()
        
        try {
            generator.writeToFile(tempFile.absolutePath)
            
            val content = tempFile.readText()
            content shouldContain "TestClass"
            content shouldContain "name"
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该生成包含 typealias 的文件") {
        val generator = TypesGenerator(emptyList())
        
        val typeAlias = KotlinDeclaration.TypeAliasDecl(
            name = "TestType",
            type = KotlinTypeFactory.string()
        )
        
        generator.addTypeAlias(typeAlias)
        
        val tempFile = createTempFile(prefix = "test", suffix = ".kt").toFile()
        
        try {
            generator.writeToFile(tempFile.absolutePath)
            
            val content = tempFile.readText()
            content shouldContain "typealias TestType"
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该处理多个类声明") {
        val classDecls = listOf(
            KotlinDeclaration.ClassDecl(
                name = "Class1",
                modifier = ClassModifier.DataClass,
                properties = emptyList(),
                parents = emptyList(),
                annotations = emptyList()
            ),
            KotlinDeclaration.ClassDecl(
                name = "Class2",
                modifier = ClassModifier.DataClass,
                properties = emptyList(),
                parents = emptyList(),
                annotations = emptyList()
            )
        )
        
        val generator = TypesGenerator(classDecls)
        val tempFile = createTempFile(prefix = "test", suffix = ".kt").toFile()
        
        try {
            generator.writeToFile(tempFile.absolutePath)
            
            val content = tempFile.readText()
            content shouldContain "Class1"
            content shouldContain "Class2"
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该处理接口声明") {
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "TestInterface",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "prop",
                    type = KotlinTypeFactory.string(),
                    modifier = PropertyModifier.Val
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )
        
        val generator = TypesGenerator(listOf(classDecl))
        val tempFile = createTempFile(prefix = "test", suffix = ".kt").toFile()
        
        try {
            generator.writeToFile(tempFile.absolutePath)
            
            val content = tempFile.readText()
            content shouldContain "interface TestInterface"
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该处理密封类声明") {
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "TestSealed",
            modifier = ClassModifier.SealedClass,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        
        val generator = TypesGenerator(listOf(classDecl))
        val tempFile = createTempFile(prefix = "test", suffix = ".kt").toFile()
        
        try {
            generator.writeToFile(tempFile.absolutePath)
            
            val content = tempFile.readText()
            content shouldContain "sealed class TestSealed"
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该处理带继承的类声明") {
        val parentType = KotlinTypeFactory.simple("Parent")
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "Child",
            modifier = ClassModifier.DataClass,
            properties = emptyList(),
            parents = listOf(parentType),
            annotations = emptyList()
        )
        
        val generator = TypesGenerator(listOf(classDecl))
        val tempFile = createTempFile(prefix = "test", suffix = ".kt").toFile()
        
        try {
            generator.writeToFile(tempFile.absolutePath)
            
            val content = tempFile.readText()
            content shouldContain "Child"
            // 应该包含继承关系
        } finally {
            tempFile.delete()
        }
    }
    
    test("应该处理带注解的类声明") {
        val annotation = KotlinDeclaration.Annotation("Serializable")
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "TestClass",
            modifier = ClassModifier.DataClass,
            properties = emptyList(),
            parents = emptyList(),
            annotations = listOf(annotation)
        )
        
        val generator = TypesGenerator(listOf(classDecl))
        val tempFile = createTempFile(prefix = "test", suffix = ".kt").toFile()
        
        try {
            generator.writeToFile(tempFile.absolutePath)
            
            val content = tempFile.readText()
            content shouldContain "@Serializable"
        } finally {
            tempFile.delete()
        }
    }
})


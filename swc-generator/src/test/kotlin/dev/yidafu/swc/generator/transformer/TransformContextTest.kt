package dev.yidafu.swc.generator.transformer

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.transformer.processors.TransformContext
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldContain

class TransformContextTest : FunSpec({
    
    test("应该能够添加类声明") {
        val context = TransformContext(mutableMapOf())
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "TestClass",
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        
        context.addClassDecl(classDecl)
        
        val retrieved = context.getClassDecl("TestClass")!!
        retrieved.name shouldBe "TestClass"
        retrieved.modifier shouldBe ClassModifier.FinalClass
    }
    
    test("应该能够添加多个类声明") {
        val context = TransformContext(mutableMapOf())
        val classDecl1 = KotlinDeclaration.ClassDecl(
            name = "Class1",
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        val classDecl2 = KotlinDeclaration.ClassDecl(
            name = "Class2",
            modifier = ClassModifier.Interface,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        
        context.addClassDecl(classDecl1)
        context.addClassDecl(classDecl2)
        
        context.getAllClassDecls() shouldHaveSize 2
        context.getClassDecl("Class1")!!
        context.getClassDecl("Class2")!!
    }
    
    test("应该能够添加属性") {
        val context = TransformContext(mutableMapOf())
        val properties = listOf(
            KotlinDeclaration.PropertyDecl(
                name = "name",
                type = KotlinType.StringType,
                modifier = PropertyModifier.Val
            ),
            KotlinDeclaration.PropertyDecl(
                name = "age",
                type = KotlinType.Int,
                modifier = PropertyModifier.Val
            )
        )
        
        context.addProperties("TestClass", properties)
        
        val allProperties = context.getAllPropertiesMap()
        allProperties.size shouldBe 1
        allProperties["TestClass"]!!.size shouldBe 2
    }
    
    test("应该能够为多个类添加属性") {
        val context = TransformContext(mutableMapOf())
        val props1 = listOf(
            KotlinDeclaration.PropertyDecl(
                name = "prop1",
                type = KotlinType.StringType,
                modifier = PropertyModifier.Val
            )
        )
        val props2 = listOf(
            KotlinDeclaration.PropertyDecl(
                name = "prop2",
                type = KotlinType.Int,
                modifier = PropertyModifier.Val
            )
        )
        
        context.addProperties("Class1", props1)
        context.addProperties("Class2", props2)
        
        val allProperties = context.getAllPropertiesMap()
        allProperties.size shouldBe 2
        allProperties["Class1"]!!
        allProperties["Class2"]!!
    }
    
    test("应该能够获取所有属性映射") {
        val context = TransformContext(mutableMapOf())
        val properties = listOf(
            KotlinDeclaration.PropertyDecl(
                name = "test",
                type = KotlinType.StringType,
                modifier = PropertyModifier.Val
            )
        )
        
        context.addProperties("TestClass", properties)
        
        val allProperties = context.getAllPropertiesMap()
        allProperties.size shouldBe 1
        allProperties["TestClass"]!!.contains(properties[0]) shouldBe true
    }
    
    test("应该能够获取类声明") {
        val context = TransformContext(mutableMapOf())
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "MyClass",
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        
        context.addClassDecl(classDecl)
        
        val retrieved = context.getClassDecl("MyClass")!!
        retrieved.name shouldBe "MyClass"
    }
    
    test("应该返回 null 当类声明不存在时") {
        val context = TransformContext(mutableMapOf())
        
        val retrieved = context.getClassDecl("NonExistent")
        (retrieved == null) shouldBe true
    }
    
    test("应该能够获取所有类声明") {
        val context = TransformContext(mutableMapOf())
        val classDecl1 = KotlinDeclaration.ClassDecl(
            name = "A",
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        val classDecl2 = KotlinDeclaration.ClassDecl(
            name = "B",
            modifier = ClassModifier.Interface,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        val classDecl3 = KotlinDeclaration.ClassDecl(
            name = "C",
            modifier = ClassModifier.SealedInterface,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        
        context.addClassDecl(classDecl1)
        context.addClassDecl(classDecl2)
        context.addClassDecl(classDecl3)
        
        val allDecls = context.getAllClassDecls()
        allDecls shouldHaveSize 3
        val names = allDecls.map { it.name }
        names shouldContain "A"
        names shouldContain "B"
        names shouldContain "C"
    }
    
    test("应该返回空列表当没有类声明时") {
        val context = TransformContext(mutableMapOf())
        
        context.getAllClassDecls().size shouldBe 0
    }
    
    test("应该返回空映射当没有属性时") {
        val context = TransformContext(mutableMapOf())
        
        context.getAllPropertiesMap().size shouldBe 0
    }
    
    test("应该能够覆盖已存在的类声明") {
        val context = TransformContext(mutableMapOf())
        val classDecl1 = KotlinDeclaration.ClassDecl(
            name = "TestClass",
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        val classDecl2 = KotlinDeclaration.ClassDecl(
            name = "TestClass",
            modifier = ClassModifier.Interface,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        
        context.addClassDecl(classDecl1)
        context.addClassDecl(classDecl2)
        
        val retrieved = context.getClassDecl("TestClass")!!
        retrieved.modifier shouldBe ClassModifier.Interface
        context.getAllClassDecls() shouldHaveSize 1
    }
    
    test("应该能够覆盖已存在的属性") {
        val context = TransformContext(mutableMapOf())
        val props1 = listOf(
            KotlinDeclaration.PropertyDecl(
                name = "old",
                type = KotlinType.StringType,
                modifier = PropertyModifier.Val
            )
        )
        val props2 = listOf(
            KotlinDeclaration.PropertyDecl(
                name = "new",
                type = KotlinType.Int,
                modifier = PropertyModifier.Val
            )
        )
        
        context.addProperties("TestClass", props1)
        context.addProperties("TestClass", props2)
        
        val allProperties = context.getAllPropertiesMap()
        allProperties["TestClass"]!! shouldHaveSize 1
        allProperties["TestClass"]!![0].name shouldBe "new"
    }
    
    test("应该能够处理复杂的类声明") {
        val context = TransformContext(mutableMapOf())
        val properties = listOf(
            KotlinDeclaration.PropertyDecl(
                name = "name",
                type = KotlinType.StringType,
                modifier = PropertyModifier.Val,
                annotations = listOf(
                    KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral("name")))
                )
            ),
            KotlinDeclaration.PropertyDecl(
                name = "value",
                type = KotlinType.Generic("List", listOf(KotlinType.StringType)),
                modifier = PropertyModifier.Val
            )
        )
        val classDecl = KotlinDeclaration.ClassDecl(
            name = "ComplexClass",
            modifier = ClassModifier.DataClass,
            properties = properties,
            parents = listOf(KotlinType.Simple("BaseClass")),
            annotations = listOf(
                KotlinDeclaration.Annotation("Serializable")
            ),
            kdoc = "这是一个复杂的类"
        )
        
        context.addClassDecl(classDecl)
        context.addProperties("ComplexClass", properties)
        
        val retrieved = context.getClassDecl("ComplexClass")!!
        retrieved.properties shouldHaveSize 2
        retrieved.parents shouldHaveSize 1
        retrieved.annotations shouldHaveSize 1
        
        val allProperties = context.getAllPropertiesMap()
        allProperties["ComplexClass"]!! shouldHaveSize 2
    }
})


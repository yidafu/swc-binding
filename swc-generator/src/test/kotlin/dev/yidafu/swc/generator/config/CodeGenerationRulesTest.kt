package dev.yidafu.swc.generator.config

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.Expression
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class CodeGenerationRulesTest : ShouldSpec({

    should("reserved words are wrapped") {
        CodeGenerationRules.wrapReservedWord("class") shouldBe "`class`"
        CodeGenerationRules.wrapReservedWord("Custom") shouldBe "Custom"
    }

    should("snake case converts to camel case") {
        CodeGenerationRules.snakeToCamelCase("foo_bar_baz") shouldBe "fooBarBaz"
        CodeGenerationRules.snakeToCamelCase("single") shouldBe "single"
    }

    should("kotlin type name validation rejects invalid inputs") {
        CodeGenerationRules.isValidKotlinTypeName("ValidName").shouldBeTrue()
        CodeGenerationRules.isValidKotlinTypeName("invalidName").shouldBeFalse()
        CodeGenerationRules.isValidKotlinTypeName("List<String>").shouldBeFalse()
        CodeGenerationRules.isValidKotlinTypeName("").shouldBeFalse()
    }

    should("property type overrides return configured nullable strings") {
        val override = CodeGenerationRules.getPropertyTypeOverride("global_defs")
        override.shouldBeInstanceOf<KotlinType.Nullable>()
        override.innerType shouldBe KotlinType.StringType
    }

    should("createPropertyDecl adds serialization annotations when renaming") {
        val property = CodeGenerationRules.createPropertyDecl(
            name = "class",
            type = KotlinType.StringType,
            isOptional = false,
            originalName = "class"
        )

        property.name shouldBe "`class`"
        property.type shouldBe KotlinType.StringType
        property.annotations.map { it.name }.shouldContainExactly(listOf("Serializable", "SerialName"))
    }

    should("createClassDecl wraps parents and names") {
        val klass = CodeGenerationRules.createClassDecl(
            name = "object",
            modifier = ClassModifier.Interface,
            properties = emptyList(),
            parents = listOf("Node"),
            annotations = listOf(KotlinDeclaration.Annotation("Serializable"))
        )

        klass.name shouldBe "`object`"
        klass.parents.single().toTypeString() shouldBe "Node"
        klass.annotations.single().name shouldBe "Serializable"
    }

    should("skip dsl receiver respects configuration") {
        CodeGenerationRules.shouldSkipDslReceiver("HasSpan").shouldBeTrue()
        CodeGenerationRules.shouldSkipDslReceiver("Expression").shouldBeFalse()
    }

    should("processImplementationProperty sets span default value to emptySpan function call") {
        // 创建一个带有 span 属性的属性声明
        val spanProperty = KotlinDeclaration.PropertyDecl(
            name = "span",
            type = KotlinType.Simple("Span"),
            modifier = PropertyModifier.Var,
            defaultValue = null,
            annotations = emptyList()
        )

        val rule = TypesImplementationRules.InterfaceRule(
            interfaceCleanName = "TestNode",
            syntaxLiteral = null,
            discriminator = "type"
        )

        val processedProperty = TypesImplementationRules.processImplementationProperty(spanProperty, rule)

        // 验证 span 属性的类型是 Span
        processedProperty.type.shouldBeInstanceOf<KotlinType.Simple>()
        (processedProperty.type as KotlinType.Simple).name shouldBe "Span"

        // 验证 span 属性的默认值是 emptySpan() 函数调用
        processedProperty.defaultValue.shouldNotBeNull()
        processedProperty.defaultValue.shouldBeInstanceOf<Expression.FunctionCall>()
        val functionCall = processedProperty.defaultValue as Expression.FunctionCall
        functionCall.name shouldBe "emptySpan"
        functionCall.arguments shouldBe emptyList()

        // 验证添加了 @EncodeDefault 注解
        processedProperty.annotations.map { it.name }.shouldContainExactly(listOf("EncodeDefault"))
    }

    should("processImplementationProperty preserves existing span default value if set") {
        // 创建一个已经设置了默认值的 span 属性
        val spanProperty = KotlinDeclaration.PropertyDecl(
            name = "span",
            type = KotlinType.Simple("Span"),
            modifier = PropertyModifier.Var,
            defaultValue = Expression.FunctionCall("emptySpan"),
            annotations = emptyList()
        )

        val rule = TypesImplementationRules.InterfaceRule(
            interfaceCleanName = "TestNode",
            syntaxLiteral = null,
            discriminator = "type"
        )

        val processedProperty = TypesImplementationRules.processImplementationProperty(spanProperty, rule)

        // 验证默认值仍然是 emptySpan()
        processedProperty.defaultValue.shouldNotBeNull()
        processedProperty.defaultValue.shouldBeInstanceOf<Expression.FunctionCall>()
        val functionCall = processedProperty.defaultValue as Expression.FunctionCall
        functionCall.name shouldBe "emptySpan"
    }
})

package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.model.kotlin.*
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class TypesGeneratorTest : AnnotationSpec() {
    @Test
    fun `type property in implementation is non nullable with default value`() {
        val generator = TypesGenerator(mutableListOf())
        val propertyDecl = KotlinDeclaration.PropertyDecl(
            name = "type",
            type = KotlinType.Nullable(KotlinType.StringType),
            modifier = PropertyModifier.Var,
            annotations = emptyList(),
            kdoc = null
        )

        val method = TypesGenerator::class.java.getDeclaredMethod(
            "processImplementationProperty",
            KotlinDeclaration.PropertyDecl::class.java,
            String::class.java
        ).apply { isAccessible = true }

        val processed = method.invoke(generator, propertyDecl, "BinaryExpression") as KotlinDeclaration.PropertyDecl

        processed.type.shouldBeInstanceOf<KotlinType.StringType>()
        val defaultValue = processed.defaultValue as Expression.StringLiteral
        defaultValue.value shouldBe "BinaryExpression"
    }

    @Test
    fun `implementation class includes inherited properties`() {
        val nodeInterface = KotlinDeclaration.ClassDecl(
            name = "Node",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "type",
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.Val,
                    annotations = emptyList(),
                    kdoc = null
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )

        val hasSpanInterface = KotlinDeclaration.ClassDecl(
            name = "HasSpan",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "span",
                    type = KotlinType.Simple("Span"),
                    modifier = PropertyModifier.Var,
                    annotations = emptyList(),
                    kdoc = null
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )

        val metaPropertyInterface = KotlinDeclaration.ClassDecl(
            name = "MetaProperty",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "kind",
                    type = KotlinType.Simple("Union.U2<String, String>"),
                    modifier = PropertyModifier.Var,
                    annotations = emptyList(),
                    kdoc = null
                )
            ),
            parents = listOf(
                KotlinType.Simple("Node"),
                KotlinType.Simple("HasSpan")
            ),
            annotations = emptyList()
        )

        val generator = TypesGenerator(
            mutableListOf(nodeInterface, hasSpanInterface, metaPropertyInterface)
        )

        val method = TypesGenerator::class.java.getDeclaredMethod(
            "createImplementationClass",
            KotlinDeclaration.ClassDecl::class.java,
            InheritanceAnalyzer::class.java
        ).apply { isAccessible = true }

        val analyzer = InheritanceAnalyzer()
        val implClass = method.invoke(generator, metaPropertyInterface, analyzer) as KotlinDeclaration.ClassDecl

        val propertyNames = implClass.properties.map { it.name.removeSurrounding("`") }
        propertyNames.shouldContainAll(listOf("type", "span", "kind"))
    }

    @Test
    fun `implementation class deduplicates overridden properties`() {
        val nodeInterface = KotlinDeclaration.ClassDecl(
            name = "Node",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "type",
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.Var
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )

        val expressionBaseInterface = KotlinDeclaration.ClassDecl(
            name = "ExpressionBase",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "type",
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.OverrideVar
                )
            ),
            parents = listOf(KotlinType.Simple("Node")),
            annotations = emptyList()
        )

        val awaitExpressionInterface = KotlinDeclaration.ClassDecl(
            name = "AwaitExpression",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "type",
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.OverrideVar
                )
            ),
            parents = listOf(KotlinType.Simple("ExpressionBase")),
            annotations = emptyList()
        )

        val generator = TypesGenerator(
            mutableListOf(nodeInterface, expressionBaseInterface, awaitExpressionInterface)
        )

        val method = TypesGenerator::class.java.getDeclaredMethod(
            "createImplementationClass",
            KotlinDeclaration.ClassDecl::class.java,
            InheritanceAnalyzer::class.java
        ).apply { isAccessible = true }

        val analyzer = InheritanceAnalyzer()
        val implClass = method.invoke(generator, awaitExpressionInterface, analyzer) as KotlinDeclaration.ClassDecl

        val typeCount = implClass.properties.count { it.name.removeSurrounding("`") == "type" }
        typeCount shouldBe 1
    }

    @Test
    fun `parser config implementation uses syntax discriminator and defaults`() {
        val tsParserInterface = KotlinDeclaration.ClassDecl(
            name = "TsParserConfig",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "syntax",
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.Var
                ),
                KotlinDeclaration.PropertyDecl(
                    name = "tsx",
                    type = KotlinType.Nullable(KotlinType.Boolean),
                    modifier = PropertyModifier.Var
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )

        val generator = TypesGenerator(mutableListOf(tsParserInterface))
        val method = TypesGenerator::class.java.getDeclaredMethod(
            "createImplementationClass",
            KotlinDeclaration.ClassDecl::class.java,
            InheritanceAnalyzer::class.java
        ).apply { isAccessible = true }

        val analyzer = InheritanceAnalyzer()
        val implClass = method.invoke(generator, tsParserInterface, analyzer) as KotlinDeclaration.ClassDecl

        val syntaxProp = implClass.properties.first { it.name == "syntax" }
        syntaxProp.type.shouldBeInstanceOf<KotlinType.StringType>()
        val defaultValue = syntaxProp.defaultValue as Expression.StringLiteral
        defaultValue.value shouldBe "typescript"

        val discriminator = implClass.annotations.first { it.name == "JsonClassDiscriminator" }
        val discriminatorValue = discriminator.arguments.first() as Expression.StringLiteral
        discriminatorValue.value shouldBe "syntax"

        val serialName = implClass.annotations.first { it.name == "SerialName" }
        val serialValue = serialName.arguments.first() as Expression.StringLiteral
        serialValue.value shouldBe "typescript"
    }
}

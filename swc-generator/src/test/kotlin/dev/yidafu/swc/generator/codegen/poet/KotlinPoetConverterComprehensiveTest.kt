package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.FileSpec
import dev.yidafu.swc.generator.model.kotlin.*
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf

/**
 * KotlinPoetConverter 的全面测试
 * 在重构前添加，确保重构后逻辑正确
 */
class KotlinPoetConverterComprehensiveTest : AnnotationSpec() {

    @Test
    fun `convertType should handle all basic types`() {
        val types = listOf(
            KotlinType.StringType,
            KotlinType.Int,
            KotlinType.Boolean,
            KotlinType.Double,
            KotlinType.Long,
            KotlinType.Float,
            KotlinType.Char,
            KotlinType.Byte,
            KotlinType.Short,
            KotlinType.Any,
            KotlinType.Unit,
            KotlinType.Nothing
        )

        types.forEach { type ->
            val typeName = KotlinPoetConverter.convertType(type)
            typeName.shouldNotBeNull()
        }
    }

    @Test
    fun `convertType should handle nullable types`() {
        val nullableString = KotlinType.Nullable(KotlinType.StringType)
        val typeName = KotlinPoetConverter.convertType(nullableString)
        typeName.shouldNotBeNull()
        typeName.isNullable shouldBe true
    }

    @Test
    fun `convertType should handle generic types`() {
        val listType = KotlinType.Generic("List", listOf(KotlinType.StringType))
        val typeName = KotlinPoetConverter.convertType(listType)
        typeName.shouldNotBeNull()
    }

    @Test
    fun `convertType should handle nested types`() {
        val nestedType = KotlinType.Nested("Parent", "Child")
        val typeName = KotlinPoetConverter.convertType(nestedType)
        typeName.shouldNotBeNull()
    }

    @Test
    fun `convertProperty should handle all property modifiers`() {
        val modifiers = listOf(
            PropertyModifier.Val,
            PropertyModifier.Var,
            PropertyModifier.ConstVal,
            PropertyModifier.LateinitVar,
            PropertyModifier.OverrideVal,
            PropertyModifier.OverrideVar
        )

        modifiers.forEach { modifier ->
            val prop = KotlinDeclaration.PropertyDecl(
                name = "test",
                type = KotlinType.StringType,
                modifier = modifier,
                annotations = emptyList()
            )
            val propertySpec = KotlinPoetConverter.convertProperty(prop)
            propertySpec.shouldNotBeNull()
        }
    }

    @Test
    fun `convertProperty should handle default values`() {
        val prop = KotlinDeclaration.PropertyDecl(
            name = "value",
            type = KotlinType.StringType,
            modifier = PropertyModifier.Val,
            defaultValue = Expression.StringLiteral("default"),
            annotations = emptyList()
        )
        val propertySpec = KotlinPoetConverter.convertProperty(prop)
        propertySpec.shouldNotBeNull()
    }

    @Test
    fun `convertProperty should handle annotations`() {
        val prop = KotlinDeclaration.PropertyDecl(
            name = "value",
            type = KotlinType.StringType,
            modifier = PropertyModifier.Val,
            annotations = listOf(
                KotlinDeclaration.Annotation("SerialName", listOf(Expression.StringLiteral("custom")))
            )
        )
        val propertySpec = KotlinPoetConverter.convertProperty(prop)
        propertySpec.shouldNotBeNull()
        propertySpec.annotations.size shouldBe 1
    }

    @Test
    fun `convertClassDeclaration should handle data class`() {
        val klass = KotlinDeclaration.ClassDecl(
            name = "TestData",
            modifier = ClassModifier.DataClass,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "name",
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.Val,
                    annotations = emptyList()
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )
        val typeSpec = KotlinPoetConverter.convertClassDeclaration(klass)
        typeSpec.shouldNotBeNull()
        typeSpec.modifiers.contains(com.squareup.kotlinpoet.KModifier.DATA) shouldBe true
    }

    @Test
    fun `convertClassDeclaration should handle interface`() {
        val klass = KotlinDeclaration.ClassDecl(
            name = "TestInterface",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "value",
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.Val,
                    annotations = emptyList()
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )
        val typeSpec = KotlinPoetConverter.convertClassDeclaration(klass)
        typeSpec.shouldNotBeNull()
        typeSpec.kind shouldBe com.squareup.kotlinpoet.TypeSpec.Kind.INTERFACE
    }

    @Test
    fun `convertClassDeclaration should handle sealed interface`() {
        val klass = KotlinDeclaration.ClassDecl(
            name = "TestSealed",
            modifier = ClassModifier.SealedInterface,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList()
        )
        val typeSpec = KotlinPoetConverter.convertClassDeclaration(klass)
        typeSpec.shouldNotBeNull()
        typeSpec.kind shouldBe com.squareup.kotlinpoet.TypeSpec.Kind.INTERFACE
        typeSpec.modifiers.contains(com.squareup.kotlinpoet.KModifier.SEALED) shouldBe true
    }

    @Test
    fun `convertClassDeclaration should handle enum class`() {
        val klass = KotlinDeclaration.ClassDecl(
            name = "TestEnum",
            modifier = ClassModifier.EnumClass,
            properties = emptyList(),
            parents = emptyList(),
            annotations = emptyList(),
            enumEntries = listOf(
                KotlinDeclaration.EnumEntry("VALUE1", emptyList(), emptyList()),
                KotlinDeclaration.EnumEntry("VALUE2", emptyList(), emptyList())
            )
        )
        val typeSpec = KotlinPoetConverter.convertClassDeclaration(klass)
        typeSpec.shouldNotBeNull()
        // 检查是否为枚举类型（通过检查是否有枚举常量）
        typeSpec.enumConstants.size shouldBe 2
    }

    @Test
    fun `convertClassDeclaration should handle inheritance`() {
        val klass = KotlinDeclaration.ClassDecl(
            name = "Child",
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = listOf(KotlinType.Simple("Parent")),
            annotations = emptyList()
        )
        val typeSpec = KotlinPoetConverter.convertClassDeclaration(klass)
        typeSpec.shouldNotBeNull()
    }

    @Test
    fun `convertClassDeclaration should add SerialName for Node-derived classes`() {
        val nodeClass = KotlinDeclaration.ClassDecl(
            name = "Expression",
            modifier = ClassModifier.FinalClass,
            properties = emptyList(),
            parents = listOf(KotlinType.Simple("Node")),
            annotations = emptyList()
        )
        val declLookup = mapOf(
            "Node" to KotlinDeclaration.ClassDecl(
                name = "Node",
                modifier = ClassModifier.Interface,
                properties = emptyList(),
                parents = emptyList(),
                annotations = emptyList()
            )
        )
        val typeSpec = KotlinPoetConverter.convertClassDeclaration(nodeClass, emptySet(), declLookup)
        typeSpec.shouldNotBeNull()
        
        val file = FileSpec.builder("test", "Test")
            .addType(typeSpec)
            .build()
        val content = buildString { file.writeTo(this) }
        content.shouldContain("@SerialName")
        content.shouldContain("Expression")
    }

    @Test
    fun `convertClassDeclaration should add Transient for Node type property`() {
        val nodeInterface = KotlinDeclaration.ClassDecl(
            name = "Node",
            modifier = ClassModifier.Interface,
            properties = listOf(
                KotlinDeclaration.PropertyDecl(
                    name = "type",
                    type = KotlinType.StringType,
                    modifier = PropertyModifier.Val,
                    annotations = emptyList()
                )
            ),
            parents = emptyList(),
            annotations = emptyList()
        )
        val typeSpec = KotlinPoetConverter.convertClassDeclaration(nodeInterface)
        typeSpec.shouldNotBeNull()
        
        val file = FileSpec.builder("test", "Test")
            .addType(typeSpec)
            .build()
        val content = buildString { file.writeTo(this) }
        content.shouldContain("@Transient")
    }

    @Test
    fun `convertAnnotation should handle SerialName annotation`() {
        val annotation = KotlinDeclaration.Annotation(
            name = "SerialName",
            arguments = listOf(Expression.StringLiteral("custom"))
        )
        val annotationSpec = KotlinPoetConverter.convertAnnotation(annotation)
        annotationSpec.shouldNotBeNull()
    }

    @Test
    fun `convertAnnotation should handle Serializable annotation`() {
        val annotation = KotlinDeclaration.Annotation(
            name = "Serializable",
            arguments = emptyList()
        )
        val annotationSpec = KotlinPoetConverter.convertAnnotation(annotation)
        annotationSpec.shouldNotBeNull()
    }

    @Test
    fun `convertTypeAliasDeclaration should handle simple type alias`() {
        val alias = KotlinDeclaration.TypeAliasDecl(
            name = "StringAlias",
            type = KotlinType.StringType,
            typeParameters = emptyList(),
            annotations = emptyList()
        )
        val typeAliasSpec = KotlinPoetConverter.convertTypeAliasDeclaration(alias)
        typeAliasSpec.shouldNotBeNull()
    }

    @Test
    fun `convertTypeAliasDeclaration should handle generic type alias`() {
        val alias = KotlinDeclaration.TypeAliasDecl(
            name = "ListAlias",
            type = KotlinType.Generic("List", listOf(KotlinType.StringType)),
            typeParameters = listOf(
                KotlinDeclaration.TypeParameter("T", KotlinDeclaration.Variance.INVARIANT)
            ),
            annotations = emptyList()
        )
        val typeAliasSpec = KotlinPoetConverter.convertTypeAliasDeclaration(alias)
        typeAliasSpec.shouldNotBeNull()
    }

    @Test
    fun `convertFunctionDeclaration should handle simple function`() {
        val func = KotlinDeclaration.FunctionDecl(
            name = "test",
            returnType = KotlinType.StringType,
            parameters = emptyList(),
            modifier = FunctionModifier.Fun,
            annotations = emptyList()
        )
        val funSpec = KotlinPoetConverter.convertFunctionDeclaration(func)
        funSpec.shouldNotBeNull()
    }

    @Test
    fun `convertFunctionDeclaration should handle function with parameters`() {
        val func = KotlinDeclaration.FunctionDecl(
            name = "test",
            returnType = KotlinType.StringType,
            parameters = listOf(
                KotlinDeclaration.ParameterDecl(
                    name = "value",
                    type = KotlinType.StringType,
                    annotations = emptyList()
                )
            ),
            modifier = FunctionModifier.Fun,
            annotations = emptyList()
        )
        val funSpec = KotlinPoetConverter.convertFunctionDeclaration(func)
        funSpec.shouldNotBeNull()
        funSpec.parameters.size shouldBe 1
    }

    @Test
    fun `convertFunctionDeclaration should handle override function`() {
        val func = KotlinDeclaration.FunctionDecl(
            name = "test",
            returnType = KotlinType.StringType,
            parameters = emptyList(),
            modifier = FunctionModifier.OverrideFun,
            annotations = emptyList()
        )
        val funSpec = KotlinPoetConverter.convertFunctionDeclaration(func)
        funSpec.shouldNotBeNull()
        funSpec.modifiers.contains(com.squareup.kotlinpoet.KModifier.OVERRIDE) shouldBe true
    }
}


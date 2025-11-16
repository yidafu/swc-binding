package dev.yidafu.swc.generator.converter.declaration

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.model.kotlin.Expression
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import dev.yidafu.swc.generator.model.kotlin.toImplClass
import dev.yidafu.swc.generator.model.typescript.*
import dev.yidafu.swc.generator.model.typescript.TypeMember
import dev.yidafu.swc.generator.config.TypesImplementationRules
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.string.shouldContain as shouldContainString

class InterfaceConverterTest : AnnotationSpec() {

    private val config = Configuration.default()
    private val converter = InterfaceConverter(config)

    @Test
    fun `test converter creation`() {
        converter.shouldNotBeNull()
    }

    @Test
    fun `test convert simple interface`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "SimpleInterface",
            members = emptyList(),
            typeParameters = emptyList(),
            extends = emptyList()
        )

        val result = converter.convert(tsInterface)

        result.shouldNotBeNull()
        if (result.isSuccess()) {
            val kotlinClass = result.getOrThrow()
            kotlinClass.name.shouldContainString("SimpleInterface")
        }
    }

    @Test
    fun `test convert interface with properties`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "InterfaceWithProps",
            members = listOf(
                TypeMember(
                    name = "name",
                    type = TypeScriptType.Keyword(KeywordKind.STRING),
                    optional = false,
                    readonly = false
                )
            ),
            typeParameters = emptyList(),
            extends = emptyList()
        )

        val result = converter.convert(tsInterface)

        result.shouldNotBeNull()
    }

    @Test
    fun `test convert interface with inheritance`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "DerivedInterface",
            members = emptyList(),
            typeParameters = emptyList(),
            extends = listOf(
                TypeReference("BaseInterface", emptyList())
            )
        )

        val result = converter.convert(tsInterface)

        result.shouldNotBeNull()
    }

    @Test
    fun `type property is forced to string`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "HasType",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Keyword(KeywordKind.NUMBER)
                )
            )
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        val typeProperty = kotlinClass.properties.first { it.name.removeSurrounding("`") == "type" }
        typeProperty.type shouldBe KotlinType.StringType
    }

    @Test
    fun `non type properties are nullable`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "HasValue",
            members = listOf(
                TypeMember(
                    name = "value",
                    type = TypeScriptType.Keyword(KeywordKind.STRING),
                    optional = false
                )
            )
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        val valueProperty = kotlinClass.properties.first { it.name == "value" }
        valueProperty.type.shouldBeInstanceOf<KotlinType.Nullable>()
    }

    @Test
    fun `type literal members generate nested interfaces`() {
        val childLiteral = TypeScriptType.TypeLiteral(
            members = listOf(
                TypeMember(
                    name = "enabled",
                    type = TypeScriptType.Keyword(KeywordKind.BOOLEAN)
                )
            )
        )

        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Parent",
            members = listOf(
                TypeMember(
                    name = "config",
                    type = childLiteral
                )
            )
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        kotlinClass.nestedClasses.size shouldBe 1
        kotlinClass.nestedClasses.first().name shouldBe "ParentConfig"
        val propertyType = kotlinClass.properties.first().type.shouldBeInstanceOf<KotlinType.Nullable>()
        val nestedType = propertyType.innerType.shouldBeInstanceOf<KotlinType.Nested>()
        nestedType.parent shouldBe "Parent"
        nestedType.name shouldBe "ParentConfig"
    }

    @Test
    fun `span coordinates are mapped to Int`() {
        val spanInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Span",
            members = listOf(
                TypeMember(name = "start", type = TypeScriptType.Keyword(KeywordKind.NUMBER)),
                TypeMember(name = "end", type = TypeScriptType.Keyword(KeywordKind.NUMBER)),
                TypeMember(name = "ctxt", type = TypeScriptType.Keyword(KeywordKind.NUMBER))
            )
        )

        val kotlinClass = converter.convert(spanInterface).getOrThrow()
        listOf("start", "end", "ctxt").forEach { propName ->
            kotlinClass.properties.first { it.name == propName }.type shouldBe KotlinType.Int
        }
    }

    @Test
    fun `type literal within union generates nested reference`() {
        val literalMember = TypeMember(
            name = "flag",
            type = TypeScriptType.Keyword(KeywordKind.BOOLEAN)
        )
        val unionLiteral = TypeScriptType.TypeLiteral(
            members = listOf(literalMember)
        )
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "UnionLiteral",
            members = listOf(
                TypeMember(
                    name = "payload",
                    type = TypeScriptType.Union(
                        listOf(
                            unionLiteral,
                            TypeScriptType.Reference("OtherNode")
                        )
                    )
                )
            )
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        kotlinClass.nestedClasses.map { it.name } shouldContain "UnionLiteralPayload"

        val payloadProperty = kotlinClass.properties.first { it.name == "payload" }
        val nullableType = payloadProperty.type.shouldBeInstanceOf<KotlinType.Nullable>()
        val unionType = nullableType.innerType.shouldBeInstanceOf<KotlinType.Generic>()
        unionType.name shouldBe "Union.U2"

        val firstParam = unionType.params.first().shouldBeInstanceOf<KotlinType.Nested>()
        firstParam.parent shouldBe "UnionLiteral"
        firstParam.name shouldBe "UnionLiteralPayload"

        val secondParam = unionType.params[1].shouldBeInstanceOf<KotlinType.Simple>()
        secondParam.name shouldBe "OtherNode"
    }

    @Test
    fun `child type property overrides parent and duplicates filtered`() {
        val parentInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "BaseInterface",
            members = listOf(
                TypeMember(name = "type", type = TypeScriptType.Keyword(KeywordKind.STRING)),
                TypeMember(name = "value", type = TypeScriptType.Keyword(KeywordKind.STRING))
            ),
            extends = emptyList()
        )
        val childInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "ChildInterface",
            members = listOf(
                TypeMember(name = "type", type = TypeScriptType.Keyword(KeywordKind.STRING)),
                TypeMember(name = "value", type = TypeScriptType.Keyword(KeywordKind.STRING))
            ),
            extends = listOf(TypeReference("BaseInterface"))
        )
        val inheritanceAnalyzer = InheritanceAnalyzer(listOf(parentInterface, childInterface))
        val unionParentRegistry = mutableMapOf(
            "ChildInterface" to mutableSetOf("UnionInjectedParent")
        )
        val converterWithAnalyzer = InterfaceConverter(
            config = config,
            inheritanceAnalyzer = inheritanceAnalyzer,
            unionParentRegistry = unionParentRegistry
        )

        val kotlinClass = converterWithAnalyzer.convert(childInterface).getOrThrow()

        kotlinClass.parents.map { it.toTypeString() }.shouldContainAll(
            listOf("BaseInterface", "UnionInjectedParent")
        )
        kotlinClass.properties.size shouldBe 1

        val typeProperty = kotlinClass.properties.first()
        typeProperty.name shouldBe "type"
        typeProperty.modifier.shouldBeInstanceOf<PropertyModifier.OverrideVar>()
    }

    @Test
    fun `syntax property is nullable without literal (no hardcoded map)`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "TsParserConfig",
            members = listOf(
                TypeMember(
                    name = "syntax",
                    type = TypeScriptType.Keyword(KeywordKind.STRING),
                    optional = true
                ),
                TypeMember(
                    name = "tsx",
                    type = TypeScriptType.Keyword(KeywordKind.BOOLEAN),
                    optional = true
                )
            ),
            extends = emptyList()
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        val syntaxProperty = kotlinClass.properties.first { it.name == "syntax" }
        // 无字面量时，不再强制非空，遵循可空规则
        syntaxProperty.type.shouldBeInstanceOf<KotlinType.Nullable>()
        val tsxProperty = kotlinClass.properties.first { it.name == "tsx" }
        tsxProperty.type.shouldBeInstanceOf<KotlinType.Nullable>()
    }

    @Test
    fun `syntax literal enforces non null String without hardcoded map`() {
        // 名称不在任何硬编码白名单中，纯靠 TS ADT 的字面量信息驱动
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "CustomConfig",
            members = listOf(
                TypeMember(
                    name = "syntax",
                    type = TypeScriptType.Literal(LiteralValue.StringLiteral("custom"))
                ),
                TypeMember(
                    name = "flag",
                    type = TypeScriptType.Keyword(KeywordKind.BOOLEAN),
                    optional = true
                )
            ),
            extends = emptyList()
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        val syntaxProperty = kotlinClass.properties.first { it.name == "syntax" }
        // 强制为非空 String（不依赖接口名硬编码）
        syntaxProperty.type shouldBe KotlinType.StringType
        // 其他属性仍按规则可空
        val flagProperty = kotlinClass.properties.first { it.name == "flag" }
        flagProperty.type.shouldBeInstanceOf<KotlinType.Nullable>()
    }

    @Test
    fun `type property with literal value extracts and stores literal`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Param",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Literal(LiteralValue.StringLiteral("Parameter"))
                ),
                TypeMember(
                    name = "pat",
                    type = TypeScriptType.Reference("Pattern")
                )
            ),
            extends = emptyList()
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        val typeProperty = kotlinClass.properties.first { it.name.removeSurrounding("`") == "type" }
        
        // 验证 type 属性是 String 类型
        typeProperty.type shouldBe KotlinType.StringType
        
        // 验证字面量值被存储在 defaultValue 中
        typeProperty.defaultValue.shouldNotBeNull()
        typeProperty.defaultValue.shouldBeInstanceOf<Expression.StringLiteral>()
        val literalValue = (typeProperty.defaultValue as Expression.StringLiteral).value
        literalValue shouldBe "Parameter"
    }

    @Test
    fun `computeSerialName uses type field literal value from TypeScript`() {
        // 创建一个带有 type 字段字面量值的接口
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Param",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Literal(LiteralValue.StringLiteral("Parameter"))
                )
            ),
            extends = emptyList()
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        
        // 转换为实现类
        val implClass = kotlinClass.toImplClass()
        
        // 验证 @SerialName 注解使用了从 TypeScript 提取的字面量值
        val serialNameAnnotation = implClass.annotations.find { it.name == "SerialName" }
        serialNameAnnotation.shouldNotBeNull()
        
        val serialNameArg = serialNameAnnotation!!.arguments.firstOrNull()
        serialNameArg.shouldNotBeNull()
        serialNameArg.shouldBeInstanceOf<Expression.StringLiteral>()
        
        val serialNameValue = (serialNameArg as Expression.StringLiteral).value
        // 应该使用 "Parameter" 而不是 "Param"
        serialNameValue shouldBe "Parameter"
    }

    @Test
    fun `computeSerialName falls back to interface name when no literal value`() {
        // 创建一个没有 type 字段字面量值的接口
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "SimpleNode",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Keyword(KeywordKind.STRING)
                )
            ),
            extends = emptyList()
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        val implClass = kotlinClass.toImplClass()
        
        // 验证 @SerialName 注解使用了接口名称（因为没有字面量值）
        val serialNameAnnotation = implClass.annotations.find { it.name == "SerialName" }
        serialNameAnnotation.shouldNotBeNull()
        
        val serialNameArg = serialNameAnnotation!!.arguments.firstOrNull()
        serialNameArg.shouldNotBeNull()
        serialNameArg.shouldBeInstanceOf<Expression.StringLiteral>()
        
        val serialNameValue = (serialNameArg as Expression.StringLiteral).value
        serialNameValue shouldBe "SimpleNode"
    }

    @Test
    fun `does not filter child properties when parent declaration missing (no hardcoded fallback)`() {
        // 子接口声明，声明继承一个未声明的父接口 HasDecorator
        // 目标：确保在父接口缺失的情况下，不会根据硬编码字典过滤 'decorators' 属性
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "ChildWithoutParentDecl",
            members = listOf(
                TypeMember(
                    name = "decorators",
                    type = TypeScriptType.Array(TypeScriptType.Keyword(KeywordKind.STRING))
                ),
                TypeMember(
                    name = "other",
                    type = TypeScriptType.Keyword(KeywordKind.STRING)
                )
            ),
            extends = listOf(TypeReference("HasDecorator"))
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        kotlinClass.properties.map { it.name.removeSurrounding("`") } shouldContain "decorators"
        kotlinClass.properties.map { it.name.removeSurrounding("`") } shouldContain "other"
    }

    @Test
    fun `toImplClass preserves type field literal value from TypeScript`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Param",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Literal(LiteralValue.StringLiteral("Parameter"))
                )
            ),
            extends = emptyList()
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        val implClass = kotlinClass.toImplClass()
        
        // 验证实现类中的 type 属性保留了从 TypeScript 提取的字面量值
        val typeProperty = implClass.properties.first { it.name.removeSurrounding("`") == "type" }
        typeProperty.defaultValue.shouldNotBeNull()
        typeProperty.defaultValue.shouldBeInstanceOf<Expression.StringLiteral>()
        
        val literalValue = (typeProperty.defaultValue as Expression.StringLiteral).value
        // 应该保留 "Parameter" 而不是被覆盖为 "Param"
        literalValue shouldBe "Parameter"
    }

    @Test
    fun `processFinalClassProperty preserves type field literal value`() {
        // 创建一个带有 type 字段字面量值的属性
        val typeProperty = KotlinDeclaration.PropertyDecl(
            name = "type",
            type = KotlinType.StringType,
            modifier = PropertyModifier.Var,
            defaultValue = Expression.StringLiteral("Parameter"), // 从 TypeScript 提取的字面量值
            annotations = emptyList()
        )

        val rule = TypesImplementationRules.InterfaceRule(
            interfaceCleanName = "Param",
            syntaxLiteral = null,
            discriminator = "type"
        )

        // 使用反射或创建一个测试用的 converter 来测试 processFinalClassProperty
        // 由于 processFinalClassProperty 是私有方法，我们通过 convert 接口来间接测试
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Param",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Literal(LiteralValue.StringLiteral("Parameter"))
                )
            ),
            extends = emptyList()
        )

        val kotlinClass = converter.convert(tsInterface).getOrThrow()
        
        // 验证在转换为 FinalClass 后，type 属性的默认值仍然是 "Parameter"
        val processedTypeProperty = kotlinClass.properties.first { it.name.removeSurrounding("`") == "type" }
        processedTypeProperty.defaultValue.shouldNotBeNull()
        processedTypeProperty.defaultValue.shouldBeInstanceOf<Expression.StringLiteral>()
        
        val literalValue = (processedTypeProperty.defaultValue as Expression.StringLiteral).value
        // processFinalClassProperty 应该保留从 TypeScript 提取的字面量值
        literalValue shouldBe "Parameter"
    }
}

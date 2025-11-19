package dev.yidafu.swc.generator.converter.declaration

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.typescript.*
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class TypeAliasConverterTest : ShouldSpec({

    val config = Configuration.default()
    val converter = TypeAliasConverter(config)

    should("test converter creation") {
        converter.shouldNotBeNull()
    }

    should("test convert simple type alias") {
        val tsTypeAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "SimpleAlias",
            type = TypeScriptType.Keyword(KeywordKind.STRING),
            typeParameters = emptyList()
        )

        val result = converter.convert(tsTypeAlias)

        result.shouldNotBeNull()
        if (result.isSuccess()) {
            val kotlinTypeAlias = result.getOrThrow() as KotlinDeclaration.TypeAliasDecl
            kotlinTypeAlias.name shouldBe "SimpleAlias"
        }
    }

    should("test convert type alias with generic") {
        val tsTypeAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "GenericAlias",
            type = TypeScriptType.Reference(
                name = "Array",
                typeParams = listOf(TypeScriptType.Keyword(KeywordKind.STRING))
            ),
            typeParameters = emptyList()
        )

        val result = converter.convert(tsTypeAlias)

        result.shouldNotBeNull()
    }

    should("test convert union type alias") {
        val tsTypeAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "UnionAlias",
            type = TypeScriptType.Union(
                types = listOf(
                    TypeScriptType.Keyword(KeywordKind.STRING),
                    TypeScriptType.Keyword(KeywordKind.NUMBER)
                )
            ),
            typeParameters = emptyList()
        )

        val result = converter.convert(tsTypeAlias)

        result.shouldNotBeNull()
    }

    should("literal unions generate enum classes") {
        val tsTypeAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "JsMode",
            type = TypeScriptType.Union(
                types = listOf(
                    TypeScriptType.Literal(LiteralValue.StringLiteral("esm")),
                    TypeScriptType.Literal(LiteralValue.StringLiteral("cjs"))
                )
            )
        )

        val result = converter.convert(tsTypeAlias)

        val enumDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        enumDecl.modifier shouldBe ClassModifier.EnumClass
        enumDecl.enumEntries.map { it.name }.shouldContainAll(listOf("ESM", "CJS"))
    }

    should("interface unions become sealed interfaces and register parents") {
        val registry = mutableMapOf<String, MutableSet<String>>()
        val customConverter = TypeAliasConverter(
            config = config,
            unionParentRegistry = registry
        )

        val tsTypeAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "NodeLike",
            type = TypeScriptType.Union(
                listOf(
                    TypeScriptType.Reference("Foo"),
                    TypeScriptType.Reference("Bar")
                )
            )
        )

        val result = customConverter.convert(tsTypeAlias)
        val sealedDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        sealedDecl.modifier shouldBe ClassModifier.SealedInterface
        registry["Foo"]?.shouldContainAll(listOf("NodeLike"))
        registry["Bar"]?.shouldContainAll(listOf("NodeLike"))
    }

    should("type literal aliases become interfaces") {
        val tsTypeAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "InlineOptions",
            type = TypeScriptType.TypeLiteral(
                members = listOf(
                    TypeMember(
                        name = "enabled",
                        type = TypeScriptType.Keyword(KeywordKind.BOOLEAN),
                        optional = true
                    )
                )
            )
        )

        val result = converter.convert(tsTypeAlias)
        val interfaceDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        interfaceDecl.modifier shouldBe ClassModifier.Interface
        val property = interfaceDecl.properties.first { it.name == "enabled" }
        property.type.shouldNotBeNull()
        property.type shouldBe KotlinType.Nullable(KotlinType.Boolean)
    }

    should("parse options intersection produces base interface and alias") {
        val registry = mutableMapOf<String, MutableSet<String>>()
        val customConverter = TypeAliasConverter(
            config = config,
            unionParentRegistry = registry
        )
        val parseOptions = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "ParseOptions",
            type = TypeScriptType.Intersection(
                listOf(
                    TypeScriptType.Reference("ParserConfig"),
                    TypeScriptType.TypeLiteral(
                        members = listOf(
                            TypeMember("comments", TypeScriptType.Keyword(KeywordKind.BOOLEAN), optional = true),
                            TypeMember("script", TypeScriptType.Keyword(KeywordKind.BOOLEAN), optional = true),
                            TypeMember("target", TypeScriptType.Reference("JscTarget"), optional = true)
                        )
                    )
                )
            )
        )

        val aliasDecl = customConverter.convert(parseOptions).getOrThrow() as KotlinDeclaration.TypeAliasDecl
        aliasDecl.type shouldBe KotlinType.Simple("ParserConfig")

        val extras = customConverter.drainExtraDeclarations()
        extras.size shouldBe 1
        val baseInterface = extras.first() as KotlinDeclaration.ClassDecl
        baseInterface.name shouldBe "BaseParseOptions"
        baseInterface.modifier shouldBe ClassModifier.SealedInterface
        baseInterface.properties.map { it.name }.shouldContainAll(listOf("comments", "script", "target"))
        registry["ParserConfig"]?.shouldContainAll(listOf("BaseParseOptions"))
    }
})

package dev.yidafu.swc.generator.converter

import dev.yidafu.swc.generator.analyzer.InheritanceAnalyzer
import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.Expression
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import dev.yidafu.swc.generator.model.typescript.*
import dev.yidafu.swc.generator.test.assertEquals
import dev.yidafu.swc.generator.test.assertNotNull
import dev.yidafu.swc.generator.test.assertTrue
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll

class TypeScriptToKotlinConverterTest : AnnotationSpec() {

    private val config = Configuration.default()
    private val converter = TypeScriptToKotlinConverter(config)

    @Test
    fun `test converter creation`() {
        assertNotNull(converter)
    }

    @Test
    fun `test convert empty declaration list`() {
        val result = converter.convertDeclarations(emptyList())

        assertTrue(result.isSuccess())
        val declarations = result.getOrThrow()
        assertTrue(declarations.isEmpty())
    }

    @Test
    fun `test convert interface declaration`() {
        val tsInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "TestInterface",
            members = emptyList(),
            typeParameters = emptyList(),
            extends = emptyList()
        )

        val result = converter.convertDeclaration(tsInterface)

        // 转换应该成功或失败，取决于具体实现
        assertNotNull(result)
    }

    @Test
    fun `test convert type alias declaration`() {
        val tsTypeAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "TestAlias",
            type = TypeScriptType.Keyword(KeywordKind.STRING),
            typeParameters = emptyList()
        )

        val result = converter.convertDeclaration(tsTypeAlias)

        assertNotNull(result)
    }

    @Test
    fun `test convert multiple declarations`() {
        val declarations = listOf(
            TypeScriptDeclaration.InterfaceDeclaration(
                name = "Interface1",
                members = emptyList(),
                typeParameters = emptyList(),
                extends = emptyList()
            ),
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "Alias1",
                type = TypeScriptType.Keyword(KeywordKind.STRING),
                typeParameters = emptyList()
            )
        )

        val result = converter.convertDeclarations(declarations)

        assertNotNull(result)
        if (result.isSuccess()) {
            val converted = result.getOrThrow()
            assertTrue(converted.size <= declarations.size) // 可能有些转换失败
        }
    }

    @Test
    fun `interface named Class should be renamed to JsClass`() {
        val classInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Class",
            members = emptyList(),
            typeParameters = emptyList(),
            extends = emptyList()
        )
        val wrapperInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Wrapper",
            members = listOf(
                TypeMember(
                    name = "clazz",
                    type = TypeScriptType.Reference("Class")
                )
            ),
            typeParameters = emptyList(),
            extends = emptyList()
        )

        val declarations = listOf(classInterface, wrapperInterface)
        val analyzer = InheritanceAnalyzer(declarations)
        val converterWithAnalyzer = TypeScriptToKotlinConverter(config, analyzer)
        val result = converterWithAnalyzer.convertDeclarations(declarations)

        assertTrue(result.isSuccess())
        val kotlinDecls = result.getOrThrow().filterIsInstance<KotlinDeclaration.ClassDecl>()

        val jsClassDecl = assertNotNull(
            kotlinDecls.find { it.name == "JsClass" },
            "Class interface should be renamed to JsClass"
        )

        val wrapperDecl = assertNotNull(kotlinDecls.find { it.name == "Wrapper" })
        val propertyType = wrapperDecl.properties.firstOrNull()?.type
        assertTrue(
            propertyType is KotlinType.Nullable &&
                propertyType.innerType is KotlinType.Simple &&
                (propertyType.innerType as KotlinType.Simple).name == "JsClass",
            "Wrapper.clazz should reference nullable JsClass"
        )
    }

    @Test
    fun `interface named Import should be renamed to JsImport`() {
        val importInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Import",
            members = listOf(
                TypeMember("source", TypeScriptType.Keyword(KeywordKind.STRING))
            )
        )
        val refInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "ImportUser",
            members = listOf(TypeMember("value", TypeScriptType.Reference("Import")))
        )
        val declarations = listOf(importInterface, refInterface)
        val analyzer = InheritanceAnalyzer(declarations)
        val converterWithAnalyzer = TypeScriptToKotlinConverter(config, analyzer)

        val result = converterWithAnalyzer.convertDeclarations(declarations)
        assertTrue(result.isSuccess())

        val kotlinDecls = result.getOrThrow().filterIsInstance<KotlinDeclaration.ClassDecl>()
        assertTrue(kotlinDecls.any { it.name == "JsImport" })
        val refDecl = kotlinDecls.first { it.name == "ImportUser" }
        val propertyType = refDecl.properties.first().type
        assertTrue(
            propertyType is KotlinType.Nullable &&
                propertyType.innerType is KotlinType.Simple &&
                (propertyType.innerType as KotlinType.Simple).name == "JsImport",
            "ImportUser.value should reference JsImport"
        )
    }

    @Test
    fun `type property remains non nullable`() {
        val interfaceDecl = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Foo",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Keyword(KeywordKind.STRING),
                    optional = true // 即使标记为可选，也应强制为非空
                )
            )
        )

        val result = converter.convertDeclaration(interfaceDecl)
        assertTrue(result.isSuccess())
        val kotlinDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        val typeProperty = kotlinDecl.properties.first { it.name.removeSurrounding("`") == "type" }
        assertTrue(typeProperty.type is KotlinType.StringType, "type property must stay non-null")
    }

    @Test
    fun `union alias adds parent interface`() {
        val unionAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "ImportSpecifier",
            type = TypeScriptType.Union(
                listOf(
                    TypeScriptType.Reference("NamedImportSpecifier"),
                    TypeScriptType.Reference("ImportDefaultSpecifier")
                )
            )
        )
        val namedInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "NamedImportSpecifier",
            members = emptyList(),
            typeParameters = emptyList(),
            extends = emptyList()
        )
        val defaultInterface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "ImportDefaultSpecifier",
            members = emptyList(),
            typeParameters = emptyList(),
            extends = emptyList()
        )

        val declarations = listOf(unionAlias, namedInterface, defaultInterface)
        val analyzer = InheritanceAnalyzer(declarations)
        val converterWithAnalyzer = TypeScriptToKotlinConverter(config, analyzer)
        val result = converterWithAnalyzer.convertDeclarations(declarations)

        assertTrue(result.isSuccess())
        val kotlinDecls = result.getOrThrow().filterIsInstance<KotlinDeclaration.ClassDecl>()

        val sealedParent = assertNotNull(kotlinDecls.find { it.name == "ImportSpecifier" })

        val namedDecl = assertNotNull(kotlinDecls.find { it.name == "NamedImportSpecifier" })
        assertTrue(
            namedDecl!!.parents.any { it is KotlinType.Simple && it.name == "ImportSpecifier" }
        )

        val defaultDecl = assertNotNull(kotlinDecls.find { it.name == "ImportDefaultSpecifier" })
        assertTrue(
            defaultDecl!!.parents.any { it is KotlinType.Simple && it.name == "ImportSpecifier" }
        )
    }

    @Test
    fun `primitive union maxLineLen`() {
        val interfaceDecl = TypeScriptDeclaration.InterfaceDeclaration(
            name = "JsFormatOptions",
            members = listOf(
                TypeMember(
                    name = "maxLineLen",
                    type = TypeScriptType.Union(
                        listOf(
                            TypeScriptType.Keyword(KeywordKind.NUMBER),
                            TypeScriptType.Literal(LiteralValue.BooleanLiteral(false))
                        )
                    )
                )
            )
        )

        val result = converter.convertDeclaration(interfaceDecl)
        assertTrue(result.isSuccess())
        val kotlinDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        val property = kotlinDecl.properties.first { it.name == "maxLineLen" }
        val type = property.type
        assertTrue(type is KotlinType.Nullable)
        val unionType = (type as KotlinType.Nullable).innerType
        assertTrue(unionType is KotlinType.Generic && unionType.name == "Union.U2")
        val unionGeneric = unionType as KotlinType.Generic
        assertEquals(listOf(KotlinType.Double, KotlinType.Boolean), unionGeneric.params)
    }

    @Test
    fun `nested interface references use nested type`() {
        val interfaceDecl = TypeScriptDeclaration.InterfaceDeclaration(
            name = "JsFormatOptions",
            members = listOf(
                TypeMember(
                    name = "comments",
                    type = TypeScriptType.Union(
                        listOf(
                            TypeScriptType.Keyword(KeywordKind.BOOLEAN),
                            TypeScriptType.Literal(LiteralValue.StringLiteral("all")),
                            TypeScriptType.Literal(LiteralValue.StringLiteral("some")),
                            TypeScriptType.TypeLiteral(
                                listOf(
                                    TypeMember(
                                        name = "regex",
                                        type = TypeScriptType.Keyword(KeywordKind.STRING)
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )

        val result = converter.convertDeclaration(interfaceDecl)
        assertTrue(result.isSuccess())
        val kotlinDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        val property = kotlinDecl.properties.first { it.name == "comments" }
        val type = property.type as KotlinType.Nullable
        val union = type.innerType as KotlinType.Generic
        val nestedType = union.params.last() as KotlinType.Nested
        assertEquals("JsFormatOptions", nestedType.parent)
        assertEquals("JsFormatOptionsComments", nestedType.name)
    }

    @Test
    fun `type literal property becomes nested interface`() {
        val interfaceDecl = TypeScriptDeclaration.InterfaceDeclaration(
            name = "OptimizerConfig",
            members = listOf(
                TypeMember(
                    name = "jsonify",
                    type = TypeScriptType.TypeLiteral(
                        listOf(
                            TypeMember(
                                name = "minCost",
                                type = TypeScriptType.Keyword(KeywordKind.NUMBER)
                            )
                        )
                    ),
                    optional = true
                )
            )
        )

        val result = converter.convertDeclaration(interfaceDecl)
        assertTrue(result.isSuccess())
        val kotlinDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        val property = kotlinDecl.properties.first { it.name == "jsonify" }
        val nullableType = property.type as KotlinType.Nullable
        val nestedType = nullableType.innerType as KotlinType.Nested
        assertEquals("OptimizerConfig", nestedType.parent)
        assertEquals("OptimizerConfigJsonify", nestedType.name)
    }

    @Test
    fun `numeric literal union collapses to int`() {
        val interfaceDecl = TypeScriptDeclaration.InterfaceDeclaration(
            name = "JsMinifyOptions",
            members = listOf(
                TypeMember(
                    name = "inline",
                    type = TypeScriptType.Union(
                        listOf(
                            TypeScriptType.Literal(LiteralValue.NumberLiteral(0.0)),
                            TypeScriptType.Literal(LiteralValue.NumberLiteral(1.0)),
                            TypeScriptType.Literal(LiteralValue.NumberLiteral(2.0)),
                            TypeScriptType.Literal(LiteralValue.NumberLiteral(3.0))
                        )
                    ),
                    optional = true
                )
            )
        )

        val result = converter.convertDeclaration(interfaceDecl)
        assertTrue(result.isSuccess())
        val kotlinDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        val property = kotlinDecl.properties.first { it.name == "`inline`" }
        assertTrue(property.type is KotlinType.Nullable)
        val nullableType = property.type as KotlinType.Nullable
        assertEquals(KotlinType.Int, nullableType.innerType)
    }

    @Test
    fun `ts parser config syntax is non nullable string`() {
        val tsParserConfig = TypeScriptDeclaration.InterfaceDeclaration(
            name = "TsParserConfig",
            members = listOf(
                TypeMember(
                    name = "syntax",
                    type = TypeScriptType.Literal(LiteralValue.StringLiteral("typescript"))
                ),
                TypeMember(
                    name = "tsx",
                    type = TypeScriptType.Keyword(KeywordKind.BOOLEAN),
                    optional = true
                )
            ),
            extends = listOf(TypeReference("ParserConfig"))
        )
        val declarations = listOf(tsParserConfig)
        val analyzer = InheritanceAnalyzer(declarations)
        val converterWithAnalyzer = TypeScriptToKotlinConverter(config, analyzer)

        val result = converterWithAnalyzer.convertDeclarations(declarations)
        assertTrue(result.isSuccess())

        val classDecl = result.getOrThrow().filterIsInstance<KotlinDeclaration.ClassDecl>()
            .first { it.name == "TsParserConfig" }
        val syntaxProperty = classDecl.properties.first { it.name == "syntax" }
        assertEquals(KotlinType.StringType, syntaxProperty.type)
    }

    @Test
    fun `type property overrides parent`() {
        val expressionBase = TypeScriptDeclaration.InterfaceDeclaration(
            name = "ExpressionBase",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Keyword(KeywordKind.STRING)
                )
            ),
            extends = emptyList()
        )

        val arrowFunction = TypeScriptDeclaration.InterfaceDeclaration(
            name = "ArrowFunctionExpression",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Literal(LiteralValue.StringLiteral("ArrowFunctionExpression"))
                )
            ),
            extends = listOf(TypeReference("ExpressionBase"))
        )

        val declarations = listOf(expressionBase, arrowFunction)
        val analyzer = InheritanceAnalyzer(declarations)
        val converterWithAnalyzer = TypeScriptToKotlinConverter(config, analyzer)

        val result = converterWithAnalyzer.convertDeclarations(declarations)
        assertTrue(result.isSuccess())
        val kotlinDecls = result.getOrThrow().filterIsInstance<KotlinDeclaration.ClassDecl>()
        val arrowDecl = kotlinDecls.first { it.name == "ArrowFunctionExpression" }
        val typeProperty = arrowDecl.properties.first { it.name.removeSurrounding("`") == "type" }
        assertTrue(typeProperty.modifier is PropertyModifier.OverrideVar)
    }

    @Test
    fun `parent interfaces use mapped names`() {
        val classIface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "Class",
            members = emptyList(),
            extends = emptyList()
        )

        val classDeclIface = TypeScriptDeclaration.InterfaceDeclaration(
            name = "ClassDeclaration",
            members = emptyList(),
            extends = listOf(TypeReference("Class"))
        )

        val declarations = listOf(classIface, classDeclIface)
        val analyzer = InheritanceAnalyzer(declarations)
        val converterWithAnalyzer = TypeScriptToKotlinConverter(config, analyzer)

        val result = converterWithAnalyzer.convertDeclarations(declarations)
        assertTrue(result.isSuccess())
        val kotlinDecls = result.getOrThrow().filterIsInstance<KotlinDeclaration.ClassDecl>()
        val classDecl = kotlinDecls.first { it.name == "ClassDeclaration" }
        val parentNames = classDecl.parents.map { it.toTypeString() }
        assertTrue(parentNames.contains("JsClass"), "ClassDeclaration should extend JsClass instead of Class")
    }

    @Test
    fun `skip complex snake case type aliases`() {
        val toSnakeCaseAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "ToSnakeCase",
            type = TypeScriptType.Keyword(KeywordKind.STRING),
            typeParameters = listOf(
                TypeParameter(
                    name = "T",
                    variance = Variance.INVARIANT,
                    constraint = TypeScriptType.Keyword(KeywordKind.STRING)
                )
            )
        )

        val anotherAlias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "RegularAlias",
            type = TypeScriptType.Keyword(KeywordKind.NUMBER),
            typeParameters = emptyList()
        )

        val declarations = listOf(toSnakeCaseAlias, anotherAlias)
        val result = converter.convertDeclarations(declarations)

        assertTrue(result.isSuccess())
        val kotlinDecls = result.getOrThrow()
        assertEquals(1, kotlinDecls.size, "Only non-skipped aliases should be converted")
        val aliasDecl = kotlinDecls.first() as KotlinDeclaration.TypeAliasDecl
        assertEquals("RegularAlias", aliasDecl.name)
    }

    @Test
    fun `terser ecma version alias becomes string`() {
        val alias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "TerserEcmaVersion",
            type = TypeScriptType.Union(
                listOf(
                    TypeScriptType.Literal(LiteralValue.NumberLiteral(5.0)),
                    TypeScriptType.Keyword(KeywordKind.STRING)
                )
            ),
            typeParameters = emptyList()
        )

        val result = converter.convertDeclaration(alias)
        assertTrue(result.isSuccess())
        val kotlinAlias = result.getOrThrow() as KotlinDeclaration.TypeAliasDecl
        assertEquals("TerserEcmaVersion", kotlinAlias.name)
        assertEquals(KotlinType.StringType, kotlinAlias.type)
    }

    @Test
    fun `string literal union becomes enum`() {
        val alias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "JscTarget",
            type = TypeScriptType.Union(
                listOf(
                    TypeScriptType.Literal(LiteralValue.StringLiteral("es3")),
                    TypeScriptType.Literal(LiteralValue.StringLiteral("es5")),
                    TypeScriptType.Literal(LiteralValue.StringLiteral("esnext"))
                )
            ),
            typeParameters = emptyList()
        )

        val result = converter.convertDeclaration(alias)
        assertTrue(result.isSuccess())
        val enumDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        assertEquals(ClassModifier.EnumClass, enumDecl.modifier)
        assertEquals(3, enumDecl.enumEntries.size)
        assertEnumSerialNames(enumDecl, listOf("es3", "es5", "esnext"))
    }

    @Test
    fun `import interop literal union becomes enum`() {
        val alias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "ImportInterop",
            type = TypeScriptType.Union(
                listOf(
                    TypeScriptType.Literal(LiteralValue.StringLiteral("swc")),
                    TypeScriptType.Literal(LiteralValue.StringLiteral("babel")),
                    TypeScriptType.Literal(LiteralValue.StringLiteral("node")),
                    TypeScriptType.Literal(LiteralValue.StringLiteral("none"))
                )
            ),
            typeParameters = emptyList()
        )

        val result = converter.convertDeclaration(alias)
        assertTrue(result.isSuccess())
        val enumDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        assertEquals(ClassModifier.EnumClass, enumDecl.modifier)
        assertEnumSerialNames(enumDecl, listOf("swc", "babel", "node", "none"))
    }

    @Test
    fun `binary operator literal union becomes enum`() {
        val alias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "BinaryOperator",
            type = TypeScriptType.Union(
                listOf(
                    "==", "!=", "===", "!==", "<", "<=", ">", ">=", "<<", ">>", ">>>",
                    "+", "-", "*", "/", "%", "|", "^", "&", "||", "&&", "in", "instanceof",
                    "**", "??"
                ).map { TypeScriptType.Literal(LiteralValue.StringLiteral(it)) }
            ),
            typeParameters = emptyList()
        )

        val result = converter.convertDeclaration(alias)
        assertTrue(result.isSuccess())
        val enumDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        assertEquals(ClassModifier.EnumClass, enumDecl.modifier)
        assertEquals(25, enumDecl.enumEntries.size)
        assertEnumSerialNames(
            enumDecl,
            listOf(
                "==", "!=", "===", "!==", "<", "<=", ">", ">=", "<<", ">>", ">>>",
                "+", "-", "*", "/", "%", "|", "^", "&", "||", "&&", "in", "instanceof",
                "**", "??"
            )
        )
        assertEnumNames(
            enumDecl,
            listOf(
                "Equality", "Inequality", "StrictEquality", "StrictInequality",
                "LessThan", "LessThanOrEqual", "GreaterThan", "GreaterThanOrEqual",
                "LeftShift", "RightShift", "UnsignedRightShift",
                "Addition", "Subtraction", "Multiplication", "Division", "Remainder",
                "BitwiseOR", "BitwiseXOR", "BitwiseAND", "LogicalOR", "LogicalAND",
                "IN", "INSTANCEOF", "Exponentiation", "NullishCoalescingOperator"
            )
        )
    }

    @Test
    fun `wasm analysis options properties are nullable`() {
        val typeLiteral = TypeScriptType.TypeLiteral(
            listOf(
                TypeMember("parser", TypeScriptType.Keyword(KeywordKind.STRING)),
                TypeMember("plugins", TypeScriptType.Keyword(KeywordKind.STRING))
            )
        )
        val alias = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "WasmAnalysisOptions",
            type = typeLiteral,
            typeParameters = emptyList()
        )

        val result = converter.convertDeclaration(alias)
        assertTrue(result.isSuccess())
        val interfaceDecl = result.getOrThrow() as KotlinDeclaration.ClassDecl
        val parserProp = interfaceDecl.properties.first { it.name == "parser" }
        val pluginsProp = interfaceDecl.properties.first { it.name == "plugins" }
        assertTrue(parserProp.type is KotlinType.Nullable)
        assertTrue(pluginsProp.type is KotlinType.Nullable)
    }

    @Test
    fun `class member adds override when parent present`() {
        val classMember = TypeScriptDeclaration.InterfaceDeclaration(
            name = "ClassMember",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Keyword(KeywordKind.STRING)
                )
            ),
            typeParameters = emptyList(),
            extends = emptyList()
        )
        val classProperty = TypeScriptDeclaration.InterfaceDeclaration(
            name = "ClassProperty",
            members = listOf(
                TypeMember(
                    name = "type",
                    type = TypeScriptType.Keyword(KeywordKind.STRING)
                )
            ),
            typeParameters = emptyList(),
            extends = listOf(TypeReference("ClassMember"))
        )

        val declarations = listOf(classMember, classProperty)
        val analyzer = InheritanceAnalyzer(declarations)
        val converterWithAnalyzer = TypeScriptToKotlinConverter(config, analyzer)
        val result = converterWithAnalyzer.convertDeclarations(declarations)
        assertTrue(result.isSuccess())
        val kotlinDecls = result.getOrThrow().filterIsInstance<KotlinDeclaration.ClassDecl>()
        val classPropertyDecl = kotlinDecls.first { it.name == "ClassProperty" }
        val typeProperty = classPropertyDecl.properties.first { it.name == "type" }
        assertTrue(typeProperty.modifier is PropertyModifier.OverrideVar)
    }

    @Test
    fun `parse options alias injects base interface and parser config parent`() {
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
        val parserConfig = TypeScriptDeclaration.TypeAliasDeclaration(
            name = "ParserConfig",
            type = TypeScriptType.Union(listOf(TypeScriptType.Reference("TsParserConfig")))
        )
        val tsParserConfig = TypeScriptDeclaration.InterfaceDeclaration(
            name = "TsParserConfig",
            members = emptyList()
        )

        val result = converter.convertDeclarations(listOf(parseOptions, parserConfig, tsParserConfig)).getOrThrow()
        val baseInterface = result.filterIsInstance<KotlinDeclaration.ClassDecl>().first { it.name == "BaseParseOptions" }
        baseInterface.properties.map { it.name }.shouldContainAll(listOf("comments", "script", "target"))

        val parserConfigDecl = result.filterIsInstance<KotlinDeclaration.ClassDecl>().first { it.name == "ParserConfig" }
        parserConfigDecl.parents.map { it.toTypeString() } shouldContain "BaseParseOptions"

        val tsParserInterface = result.filterIsInstance<KotlinDeclaration.ClassDecl>().first { it.name == "TsParserConfig" }
        tsParserInterface.parents.map { it.toTypeString() } shouldContain "ParserConfig"
    }

    private fun assertEnumSerialNames(enumDecl: KotlinDeclaration.ClassDecl, expected: List<String>) {
        val serialNames = enumDecl.enumEntries.map { entry ->
            val annotation = entry.annotations.find { it.name == "SerialName" }
                ?: error("Enum entry ${entry.name} missing SerialName")
            val firstArg = annotation.arguments.firstOrNull()
                ?: error("Enum entry ${entry.name} SerialName missing argument")
            (firstArg as? Expression.StringLiteral)?.value
                ?: error("Enum entry ${entry.name} SerialName argument is not StringLiteral")
        }
        assertEquals(expected, serialNames)
    }

    private fun assertEnumNames(enumDecl: KotlinDeclaration.ClassDecl, expected: List<String>) {
        val names = enumDecl.enumEntries.map { it.name }
        assertEquals(expected, names)
    }
}

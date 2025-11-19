package dev.yidafu.swc.generator.analyzer

import dev.yidafu.swc.generator.model.typescript.TypeReference
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.model.typescript.TypeScriptType
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class InheritanceAnalyzerTest : ShouldSpec({

    fun declaration(name: String, vararg parents: String) =
        TypeScriptDeclaration.InterfaceDeclaration(
            name = name,
            extends = parents.map { TypeReference(it) }
        )

    should("analyzer resolves descendants and roots") {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node"),
            declaration("Literal", "Expression"),
            declaration("Identifier", "Literal"),
            declaration("Standalone")
        )

        val analyzer = InheritanceAnalyzer(declarations)

        analyzer.findAllChildrenByParent("Expression") shouldBe listOf("Literal")
        analyzer.findAllGrandChildren("Node").shouldContainExactly(listOf("Expression", "Literal", "Identifier"))
        analyzer.findRootTypes().shouldContainExactly(listOf("Node", "Standalone"))
        analyzer.isDescendantOf("Identifier", "Node") shouldBe true
        analyzer.isDescendantOf("Standalone", "Node") shouldBe false
    }

    should("analyzer detects cycles") {
        val declarations = listOf(
            declaration("A", "B"),
            declaration("B", "A")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        analyzer.detectCycles().isNotEmpty() shouldBe true
    }

    should("expandTypeAlias expands simple union type alias") {
        val declarations = listOf(
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "SimpleUnion",
                type = TypeScriptType.Union(
                    listOf(
                        TypeScriptType.Reference("TypeA"),
                        TypeScriptType.Reference("TypeB"),
                        TypeScriptType.Reference("TypeC")
                    )
                )
            )
        )
        val analyzer = InheritanceAnalyzer(declarations)

        val expanded = analyzer.expandTypeAlias("SimpleUnion")
        expanded.shouldContainExactly(listOf("TypeA", "TypeB", "TypeC"))
    }

    should("expandTypeAlias returns null for non-type-alias") {
        val declarations = listOf(
            declaration("SomeInterface")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        analyzer.expandTypeAlias("SomeInterface").shouldBeNull()
        analyzer.expandTypeAlias("NonExistent").shouldBeNull()
    }

    should("expandTypeAlias returns null for non-union type alias") {
        val declarations = listOf(
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "SimpleAlias",
                type = TypeScriptType.Reference("SomeType")
            )
        )
        val analyzer = InheritanceAnalyzer(declarations)

        analyzer.expandTypeAlias("SimpleAlias").shouldBeNull()
    }

    should("expandTypeAlias expands nested type aliases recursively") {
        val declarations = listOf(
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "InnerUnion",
                type = TypeScriptType.Union(
                    listOf(
                        TypeScriptType.Reference("TypeX"),
                        TypeScriptType.Reference("TypeY")
                    )
                )
            ),
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "OuterUnion",
                type = TypeScriptType.Union(
                    listOf(
                        TypeScriptType.Reference("InnerUnion"),
                        TypeScriptType.Reference("TypeZ")
                    )
                )
            )
        )
        val analyzer = InheritanceAnalyzer(declarations)

        val expanded = analyzer.expandTypeAlias("OuterUnion")
        expanded.shouldContainExactly(listOf("TypeX", "TypeY", "TypeZ"))
    }

    should("expandTypeAlias prevents circular references") {
        val declarations = listOf(
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "CircularA",
                type = TypeScriptType.Union(
                    listOf(
                        TypeScriptType.Reference("CircularB"),
                        TypeScriptType.Reference("TypeA")
                    )
                )
            ),
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "CircularB",
                type = TypeScriptType.Union(
                    listOf(
                        TypeScriptType.Reference("CircularA"),
                        TypeScriptType.Reference("TypeB")
                    )
                )
            )
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // 展开 CircularA:
        // 1. CircularA 开始展开，visited = {CircularA}
        // 2. 遇到 CircularB，尝试展开 CircularB，visited = {CircularA, CircularB}
        // 3. 在 CircularB 中遇到 CircularA，expandTypeAlias("CircularA", visited) 返回 null（循环引用）
        // 4. 由于 memberExpanded 为 null，将 "CircularA" 作为类型名称添加到 expandedTypes
        // 5. 遇到 TypeB，expandTypeAlias("TypeB", visited) 返回 null，将 "TypeB" 添加到 expandedTypes
        // 6. CircularB 展开返回 ["CircularA", "TypeB"]
        // 7. 在 CircularA 中，将 CircularB 的展开结果 ["CircularA", "TypeB"] 添加到 expandedTypes
        // 8. 遇到 TypeA，expandTypeAlias("TypeA", visited) 返回 null，将 "TypeA" 添加到 expandedTypes
        // 9. CircularA 展开返回 ["CircularA", "TypeB", "TypeA"]（去重后）
        val expanded = analyzer.expandTypeAlias("CircularA")
        // 由于循环引用检测，CircularA 会返回部分展开的结果，不会无限循环
        expanded.shouldContainExactly(listOf("CircularA", "TypeB", "TypeA"))
    }

    should("expandTypeAlias deduplicates expanded types") {
        val declarations = listOf(
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "DuplicatedUnion",
                type = TypeScriptType.Union(
                    listOf(
                        TypeScriptType.Reference("TypeA"),
                        TypeScriptType.Reference("TypeB"),
                        TypeScriptType.Reference("TypeA") // 重复
                    )
                )
            )
        )
        val analyzer = InheritanceAnalyzer(declarations)

        val expanded = analyzer.expandTypeAlias("DuplicatedUnion")
        expanded.shouldContainExactly(listOf("TypeA", "TypeB"))
    }

    should("expandTypeAlias handles mixed type alias and interface references") {
        val declarations = listOf(
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "MixedUnion",
                type = TypeScriptType.Union(
                    listOf(
                        TypeScriptType.Reference("TypeA"),
                        TypeScriptType.Reference("TypeB")
                    )
                )
            ),
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "ComplexUnion",
                type = TypeScriptType.Union(
                    listOf(
                        TypeScriptType.Reference("MixedUnion"),
                        TypeScriptType.Reference("TypeC"),
                        TypeScriptType.Reference("TypeD")
                    )
                )
            )
        )
        val analyzer = InheritanceAnalyzer(declarations)

        val expanded = analyzer.expandTypeAlias("ComplexUnion")
        expanded.shouldContainExactly(listOf("TypeA", "TypeB", "TypeC", "TypeD"))
    }

    should("expandTypeAlias handles non-reference types in union") {
        val declarations = listOf(
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "UnionWithLiteral",
                type = TypeScriptType.Union(
                    listOf(
                        TypeScriptType.Reference("TypeA"),
                        TypeScriptType.Keyword(dev.yidafu.swc.generator.model.typescript.KeywordKind.STRING),
                        TypeScriptType.Reference("TypeB")
                    )
                )
            )
        )
        val analyzer = InheritanceAnalyzer(declarations)

        val expanded = analyzer.expandTypeAlias("UnionWithLiteral")
        // 应该只包含引用类型，跳过字面量和关键字
        expanded.shouldContainExactly(listOf("TypeA", "TypeB"))
    }

    should("findLowestCommonAncestor finds direct common parent") {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node"),
            declaration("Identifier", "Expression"),
            declaration("StringLiteral", "Expression")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // Identifier 和 StringLiteral 的直接公共父接口是 Expression
        val lca = analyzer.findLowestCommonAncestor(listOf("Identifier", "StringLiteral"))
        lca shouldBe "Expression"
    }

    should("findLowestCommonAncestor finds indirect common parent") {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node"),
            declaration("Pattern", "Node"),
            declaration("Identifier", "Expression"),
            declaration("ArrayPattern", "Pattern"),
            declaration("ObjectPattern", "Pattern")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // Identifier 和 ArrayPattern 的公共祖先是 Node
        val lca = analyzer.findLowestCommonAncestor(listOf("Identifier", "ArrayPattern"))
        lca shouldBe "Node"
    }

    should("findLowestCommonAncestor returns Node when Node is the common ancestor") {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node"),
            declaration("Statement", "Node"),
            declaration("Identifier", "Expression"),
            declaration("BlockStatement", "Statement")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // Identifier 和 BlockStatement 的公共祖先是 Node
        val lca = analyzer.findLowestCommonAncestor(listOf("Identifier", "BlockStatement"))
        lca shouldBe "Node"
    }

    should("findLowestCommonAncestor finds common ancestor for three types") {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node"),
            declaration("Identifier", "Expression"),
            declaration("StringLiteral", "Expression"),
            declaration("NumericLiteral", "Expression")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // Identifier, StringLiteral, NumericLiteral 的公共祖先是 Expression
        val lca = analyzer.findLowestCommonAncestor(listOf("Identifier", "StringLiteral", "NumericLiteral"))
        lca shouldBe "Expression"
    }

    should("findLowestCommonAncestor returns null for unrelated types") {
        val declarations = listOf(
            declaration("TypeA"),
            declaration("TypeB")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // TypeA 和 TypeB 没有公共祖先
        val lca = analyzer.findLowestCommonAncestor(listOf("TypeA", "TypeB"))
        lca.shouldBeNull()
    }

    should("findLowestCommonAncestor returns self for single type") {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // 单个类型应该返回自身
        val lca = analyzer.findLowestCommonAncestor(listOf("Expression"))
        lca shouldBe "Expression"
    }

    should("findLowestCommonAncestor finds deepest common ancestor") {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node"),
            declaration("Literal", "Expression"),
            declaration("Pattern", "Node"),
            declaration("BindingPattern", "Pattern"),
            declaration("Identifier", "Literal"),
            declaration("ArrayPattern", "BindingPattern")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // Identifier 的祖先链: Identifier -> Literal -> Expression -> Node
        // ArrayPattern 的祖先链: ArrayPattern -> BindingPattern -> Pattern -> Node
        // 公共祖先: Node（最深的是 Node）
        val lca = analyzer.findLowestCommonAncestor(listOf("Identifier", "ArrayPattern"))
        lca shouldBe "Node"
    }

    should("findLowestCommonAncestor handles type and its ancestor") {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node"),
            declaration("Identifier", "Expression")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // Identifier 是 Expression 的后代，它们的公共祖先是 Expression（最深的公共祖先）
        val lca1 = analyzer.findLowestCommonAncestor(listOf("Identifier", "Expression"))
        lca1 shouldBe "Expression"

        // Identifier 和 Node 的公共祖先是 Node
        val lca2 = analyzer.findLowestCommonAncestor(listOf("Identifier", "Node"))
        lca2 shouldBe "Node"
    }

    should("findLowestCommonAncestor handles same type") {
        val declarations = listOf(
            declaration("Node"),
            declaration("Expression", "Node")
        )
        val analyzer = InheritanceAnalyzer(declarations)

        // 相同类型的公共祖先就是自身
        val lca = analyzer.findLowestCommonAncestor(listOf("Expression", "Expression"))
        lca shouldBe "Expression"
    }
})

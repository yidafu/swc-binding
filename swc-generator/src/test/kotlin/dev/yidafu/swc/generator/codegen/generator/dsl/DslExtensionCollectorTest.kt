package dev.yidafu.swc.generator.codegen.generator.dsl

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.nulls.shouldBeNull

class DslExtensionCollectorTest : AnnotationSpec() {

    private val nodeInterface = classDecl("Node", ClassModifier.Interface)
    private val exprInterface = classDecl(
        "Expr",
        ClassModifier.Interface,
        parents = listOf(KotlinType.Simple("Node"))
    )
    private val literalInterface = classDecl(
        "Literal",
        ClassModifier.Interface,
        parents = listOf(KotlinType.Simple("Expr"))
    )
    private val literalImplWithBackticks = classDecl(
        "`LiteralImpl`",
        ClassModifier.FinalClass,
        parents = listOf(KotlinType.Simple("Literal"))
    )
    private val nodeKindEnum = classDecl("NodeKind", ClassModifier.EnumClass)
    private val outsideInterface = classDecl("Outside", ClassModifier.Interface)
    private val enumHostInterface = classDecl("EnumHost", ClassModifier.Interface)

    private val modelContext = DslModelContext(
        classDecls = listOf(
            nodeInterface,
            exprInterface,
            literalInterface,
            literalImplWithBackticks,
            nodeKindEnum,
            outsideInterface,
            enumHostInterface
        ),
        classAllPropertiesMap = mapOf(
            "Node" to listOf(
                prop("child", KotlinType.Simple("Expr")),
                prop("kind", KotlinType.Simple("NodeKind"))
            ),
            "Expr" to listOf(
                prop("impl", KotlinType.Simple("LiteralImpl"))
            ),
            "EnumHost" to listOf(
                prop("state", KotlinType.Simple("NodeKind"))
            )
        )
    )

    @Test
    fun `collector groups instantiable property extensions`() {
        val collector = DslExtensionCollector(modelContext)
        val result = collector.collect()

        result.groups.keys.shouldContainAll("Node", "Expr")
        result.groups.keys.shouldNotContain("EnumHost")

        val nodeFunNames = result.groups["Node"].orEmpty().map { it.funName }
        nodeFunNames.shouldContainExactly("Literal")

        val exprFunNames = result.groups["Expr"].orEmpty().map { it.funName }
        exprFunNames.shouldContainExactly("Literal")
    }

    @Test
    fun `node leaf interfaces include only node descendants without children`() {
        val collector = DslExtensionCollector(modelContext)
        val result = collector.collect()

        val creatableNames = result.nodeCreatableClasses.map { it.name }
        // 只有具体类（实现类）会被认为是可实例化的 Node 类
        creatableNames.shouldContainExactly("`LiteralImpl`")
        creatableNames.shouldNotContain("Expr")
        creatableNames.shouldNotContain("Outside")
    }

    @Test
    fun `enum only properties do not produce extension functions`() {
        val collector = DslExtensionCollector(modelContext)
        val result = collector.collect()

        result.groups["EnumHost"].shouldBeNull()

        val nodeFunNames = result.groups["Node"].orEmpty().map { it.funName }
        nodeFunNames.shouldNotContain("NodeKind")
    }

    private fun classDecl(
        name: String,
        modifier: ClassModifier,
        properties: List<KotlinDeclaration.PropertyDecl> = emptyList(),
        parents: List<KotlinType> = emptyList(),
        enumEntries: List<KotlinDeclaration.EnumEntry> = emptyList()
    ): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = modifier,
            properties = properties,
            parents = parents,
            enumEntries = enumEntries,
            annotations = emptyList()
        )
    }

    private fun prop(
        name: String,
        type: KotlinType = KotlinType.StringType
    ): KotlinDeclaration.PropertyDecl {
        return KotlinDeclaration.PropertyDecl(
            name = name,
            type = type,
            modifier = PropertyModifier.Var,
            annotations = emptyList()
        )
    }
}

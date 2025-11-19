package dev.yidafu.swc.generator.codegen.generator.dsl

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.nulls.shouldBeNull

class DslExtensionCollectorTest : ShouldSpec({

    fun classDecl(
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

    fun prop(
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

    val nodeInterface = classDecl("Node", ClassModifier.Interface)
    val exprInterface = classDecl(
        "Expr",
        ClassModifier.Interface,
        parents = listOf(KotlinType.Simple("Node"))
    )
    val literalInterface = classDecl(
        "Literal",
        ClassModifier.Interface,
        parents = listOf(KotlinType.Simple("Expr"))
    )
    val literalImplWithBackticks = classDecl(
        "`LiteralImpl`",
        ClassModifier.FinalClass,
        parents = listOf(KotlinType.Simple("Literal"))
    )
    val nodeKindEnum = classDecl("NodeKind", ClassModifier.EnumClass)
    val outsideInterface = classDecl("Outside", ClassModifier.Interface)
    val enumHostInterface = classDecl("EnumHost", ClassModifier.Interface)

    val modelContext = DslModelContext(
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

    
    should("collector groups instantiable property extensions") {
        val collector = DslExtensionCollector(modelContext)
        val result = collector.collect()

        result.groups.keys.shouldContainAll("Node", "Expr")
        result.groups.keys.shouldNotContain("EnumHost")

        val nodeFunNames = result.groups["Node"].orEmpty().map { it.funName }
        nodeFunNames.shouldContainExactly("Literal")

        val exprFunNames = result.groups["Expr"].orEmpty().map { it.funName }
        exprFunNames.shouldContainExactly("Literal")
    }

    
    should("node leaf interfaces include only node descendants without children") {
        val collector = DslExtensionCollector(modelContext)
        val result = collector.collect()

        val creatableNames = result.nodeCreatableClasses.map { it.name }
        // 只有具体类（实现类）会被认为是可实例化的 Node 类
        creatableNames.shouldContainExactly("`LiteralImpl`")
        creatableNames.shouldNotContain("Expr")
        creatableNames.shouldNotContain("Outside")
    }

    
    should("enum only properties do not produce extension functions") {
        val collector = DslExtensionCollector(modelContext)
        val result = collector.collect()

        result.groups["EnumHost"].shouldBeNull()

        val nodeFunNames = result.groups["Node"].orEmpty().map { it.funName }
        nodeFunNames.shouldNotContain("NodeKind")
    }
})

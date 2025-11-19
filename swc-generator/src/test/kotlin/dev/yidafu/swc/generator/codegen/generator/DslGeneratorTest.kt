package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import java.io.File

class DslGeneratorTest : ShouldSpec({

    fun property(name: String, type: KotlinType): KotlinDeclaration.PropertyDecl {
        return KotlinDeclaration.PropertyDecl(
            name = name,
            type = type,
            modifier = PropertyModifier.Var,
            annotations = emptyList()
        )
    }

    fun classDecl(
        name: String,
        modifier: ClassModifier,
        properties: List<KotlinDeclaration.PropertyDecl> = emptyList(),
        parents: List<KotlinType> = emptyList()
    ): KotlinDeclaration.ClassDecl {
        return KotlinDeclaration.ClassDecl(
            name = name,
            modifier = modifier,
            properties = properties,
            parents = parents,
            annotations = emptyList()
        )
    }

    should("generate DSL files for property references") {
        val argument = classDecl(
            name = "Argument",
            modifier = ClassModifier.Interface,
            properties = listOf(
                property("expression", KotlinType.Simple("Expression")),
                property("literalValue", KotlinType.Simple("Literal"))
            )
        )
        val expression = classDecl(
            name = "Expression",
            modifier = ClassModifier.Interface,
            parents = listOf(KotlinType.Simple("Node"))
        )
        val identifier = classDecl(
            name = "Identifier",
            modifier = ClassModifier.Interface,
            parents = listOf(KotlinType.Simple("Expression"))
        )
        val identifierImpl = classDecl(
            name = "IdentifierImpl",
            modifier = ClassModifier.FinalClass,
            parents = listOf(KotlinType.Simple("Identifier"))
        )
        val node = classDecl(
            name = "Node",
            modifier = ClassModifier.Interface
        )
        val literal = classDecl(
            name = "Literal",
            modifier = ClassModifier.Interface
        )
        val booleanLiteral = classDecl(
            name = "BooleanLiteral",
            modifier = ClassModifier.Interface,
            parents = listOf(KotlinType.Simple("Literal"))
        )

        val classDecls = listOf(
            argument,
            expression,
            identifier,
            identifierImpl,
            node,
            literal,
            booleanLiteral
        )
        val propsMap = mapOf("Argument" to argument.properties)

        val generator = DslGenerator(classDecls, propsMap)
        val outputDir = createTempDir(prefix = "dsl-generator-test")

        try {
            generator.writeToDirectory(outputDir.absolutePath)

            val argumentDsl = File(outputDir, "Argument.kt")
            argumentDsl.exists().shouldBeTrue()
            val argumentContent = argumentDsl.readText()
            argumentContent.shouldContain("fun Argument.identifier")
            argumentContent.shouldNotContain("fun Argument.literal(")

            val createFile = File(outputDir, "create.kt")
            createFile.exists().shouldBeTrue()
            createFile.readText().shouldContain("fun createIdentifier")
        } finally {
            outputDir.deleteRecursively()
        }
    }
})

package dev.yidafu.swc.generator.core.stages

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.core.PipelineContext
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.extractor.TypeScriptADTExtractor
import dev.yidafu.swc.generator.model.typescript.TypeScriptDeclaration
import dev.yidafu.swc.generator.model.typescript.TypeScriptType
import dev.yidafu.swc.generator.parser.AstNode
import dev.yidafu.swc.generator.parser.ParseResult
import dev.yidafu.swc.generator.parser.TsAstVisitor
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import dev.yidafu.swc.generator.test.assertNotNull
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ExtractorStageTest : AnnotationSpec() {

    private val config = Configuration.default()
    private val container = mockk<DependencyContainer>()
    private val visitor = mockk<TsAstVisitor>()
    private val stage = ExtractorStage(config, container) { visitor }

    @Test
    fun `extractor stage collects declarations and stores metadata`() {
        val parseResult = ParseResult(
            astJsonString = """{"type":"Module","body":[]}""",
            program = AstNode.fromJson("""{"type":"Module","body":[]}"""),
            inputPath = "test.d.ts",
            sourceCode = ""
        )
        val extractor = mockk<TypeScriptADTExtractor>()
        val interfaceNode = mockk<AstNode>()
        val aliasNode = mockk<AstNode>()

        every { visitor.visit() } returns Unit
        every { visitor.getInterfaces() } returns listOf(interfaceNode)
        every { visitor.getTypeAliases() } returns listOf(aliasNode)
        every { visitor.getImports() } returns emptyList()
        every { container.createTypeScriptADTExtractor(any()) } returns extractor
        every { extractor.extractInterface(interfaceNode) } returns GeneratorResultFactory.success(
            TypeScriptDeclaration.InterfaceDeclaration("Foo")
        )
        every { extractor.extractTypeAlias(aliasNode) } returns GeneratorResultFactory.success(
            TypeScriptDeclaration.TypeAliasDeclaration(
                name = "Bar",
                type = TypeScriptType.Any
            )
        )

        val context = PipelineContext(config)
        val result = stage.execute(parseResult, context)

        result.isSuccess() shouldBe true
        val declarations = assertNotNull(context.getMetadata<List<TypeScriptDeclaration>>("typeScriptDeclarations"))
        declarations.shouldHaveSize(2)
        declarations.map {
            when (it) {
                is TypeScriptDeclaration.InterfaceDeclaration -> it.name
                is TypeScriptDeclaration.TypeAliasDeclaration -> it.name
            }
        } shouldBe listOf("Foo", "Bar")
    }
}

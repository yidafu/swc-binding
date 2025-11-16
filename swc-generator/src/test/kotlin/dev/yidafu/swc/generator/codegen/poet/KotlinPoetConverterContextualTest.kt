package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.FileSpec
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.PropertyModifier
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.annotation.Test
import io.kotest.matchers.string.shouldContain

class KotlinPoetConverterContextualTest : AnnotationSpec() {

    @Test
    fun `union property should be annotated with Serializable with custom serializer`() {
        val prop = KotlinDeclaration.PropertyDecl(
            name = "value",
            type = KotlinType.Generic("Union.U2", listOf(KotlinType.Simple("String"), KotlinType.Simple("Int"))),
            modifier = PropertyModifier.Val,
            annotations = emptyList()
        )
        val klass = KotlinDeclaration.ClassDecl(
            name = "Holder",
            modifier = dev.yidafu.swc.generator.model.kotlin.ClassModifier.FinalClass,
            properties = listOf(prop),
            parents = emptyList(),
            annotations = emptyList()
        )
        val typeSpec = KotlinPoetConverter.convertClassDeclaration(klass)
        val file = FileSpec.builder("dev.yidafu.test", "Dummy")
            .addType(typeSpec)
            .build()
        val content = buildString { file.writeTo(this) }
        content.shouldContain("@Serializable(with =")
    }
}


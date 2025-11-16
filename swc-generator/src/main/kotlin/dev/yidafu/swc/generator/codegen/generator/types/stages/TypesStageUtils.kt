package dev.yidafu.swc.generator.codegen.generator.types.stages

import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.getInheritanceDepth
import dev.yidafu.swc.generator.model.kotlin.getInheritanceRoot

internal object TypesStageUtils {
    fun sortByInheritance(classes: List<KotlinDeclaration.ClassDecl>): List<KotlinDeclaration.ClassDecl> {
        val groupedByRoot = classes.groupBy { it.getInheritanceRoot() }
        val result = mutableListOf<KotlinDeclaration.ClassDecl>()
        groupedByRoot.forEach { (_, classesInTree) ->
            val sortedTree = classesInTree.sortedBy { it.getInheritanceDepth() }
            result.addAll(sortedTree)
        }
        return result
    }
}


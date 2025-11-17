package dev.yidafu.swc.generator.codegen.generator.dsl

import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.isInterface
import java.util.ArrayDeque
import java.util.LinkedHashMap
import java.util.LinkedHashSet

class DslModelContext(
    val classDecls: List<KotlinDeclaration.ClassDecl>,
    val classAllPropertiesMap: Map<String, List<KotlinDeclaration.PropertyDecl>>
) {
    val generatedClassNameList: List<String> = classDecls.map { it.name }
    val classInfoByName: Map<String, KotlinDeclaration.ClassDecl> =
        classDecls.associateBy { it.name.removeSurrounding("`") }
    val hierarchy = DslHierarchy(classDecls)
    val leafInterfaceNames: Set<String> = classDecls
        .filter { it.modifier.isInterface() }
        .map { it.name.removeSurrounding("`") }
        .filter { interfaceName ->
            hierarchy.findChildren(interfaceName).none { child ->
                classInfoByName[child]?.modifier?.isInterface() == true
            }
        }
        .toSet()

    fun isNodeLeafInterface(interfaceName: String): Boolean {
        val normalized = interfaceName.removeSurrounding("`")
        if (!leafInterfaceNames.contains(normalized)) return false
        return inheritsNode(normalized)
    }

    fun inheritsNode(interfaceName: String): Boolean {
        val visited = LinkedHashSet<String>()
        val queue = ArrayDeque<String>()
        queue.add(interfaceName)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (!visited.add(current)) continue
            val decl = classInfoByName[current] ?: continue
            val parentNames = decl.parents.mapNotNull { it.extractSimpleName() }
            if (parentNames.any { it == "Node" }) {
                return true
            }
            parentNames.forEach { queue.add(it) }
        }
        return false
    }

    fun inheritsTarget(startName: String, targetInterface: String): Boolean {
        val visited = LinkedHashSet<String>()
        val queue = ArrayDeque<String>()
        queue.add(startName.removeSurrounding("`"))
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (!visited.add(current)) continue
            val decl = classInfoByName[current] ?: continue
            val parentNames = decl.parents.mapNotNull { it.extractSimpleName() }
            if (parentNames.any { it == targetInterface }) {
                return true
            }
            parentNames.forEach { queue.add(it) }
        }
        return false
    }
}

class DslHierarchy(
    classDecls: List<KotlinDeclaration.ClassDecl>
) {
    private val parentToChildren = LinkedHashMap<String, LinkedHashSet<String>>()

    init {
        classDecls.forEach { decl ->
            val childName = decl.name.removeSurrounding("`")
            decl.parents.mapNotNull { it.extractSimpleName() }.forEach { parent ->
                parentToChildren.getOrPut(parent) { LinkedHashSet() }.add(childName)
            }
        }
    }

    fun findDescendants(typeName: String): List<String> {
        val normalized = typeName.removeSurrounding("`")
        val visited = LinkedHashSet<String>()
        val queue = ArrayDeque<String>()
        parentToChildren[normalized]?.forEach { queue.add(it) }
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (visited.add(current)) {
                parentToChildren[current]?.forEach { queue.add(it) }
            }
        }
        return visited.toList()
    }

    fun findChildren(typeName: String): List<String> {
        val normalized = typeName.removeSurrounding("`")
        return parentToChildren[normalized]?.toList() ?: emptyList()
    }
}

private fun KotlinType.extractSimpleName(): String? {
    return when (this) {
        is KotlinType.Simple -> this.name.removeSurrounding("`")
        is KotlinType.Nullable -> this.innerType.extractSimpleName()
        is KotlinType.Nested -> "${this.parent.removeSurrounding("`")}.${this.name.removeSurrounding("`")}"
        else -> null
    }
}

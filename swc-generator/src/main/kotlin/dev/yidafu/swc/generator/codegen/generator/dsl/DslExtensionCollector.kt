package dev.yidafu.swc.generator.codegen.generator.dsl

import dev.yidafu.swc.generator.codegen.model.KotlinExtensionFun
import dev.yidafu.swc.generator.config.CodeGenerationRules
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.isInterface
import dev.yidafu.swc.generator.util.Logger

data class DslExtensionCollection(
    val groups: Map<String, List<KotlinExtensionFun>>,
    val nodeLeafInterfaces: List<KotlinDeclaration.ClassDecl>
)

class DslExtensionCollector(
    private val modelContext: DslModelContext
) {
    fun collect(): DslExtensionCollection {
        val extFunMap = linkedMapOf<String, KotlinExtensionFun>()
        val receivers = LinkedHashSet<String>()
        receivers.addAll(modelContext.classAllPropertiesMap.keys)
        modelContext.classDecls.filter { it.modifier.isInterface() }.forEach { receivers.add(it.name) }

        receivers.forEach { receiver ->
            if (CodeGenerationRules.shouldSkipDslReceiver(receiver)) return@forEach
            val props = getProperties(receiver)
            if (props.isEmpty()) return@forEach

            props.flatMap { prop ->
                generatePropertyExtensionFunctions(receiver, prop)
            }.forEach { extFun ->
                addExtensionFun(extFunMap, extFun)
            }
        }

        val nodeLeafInterfaces = modelContext.classDecls.filter { klass ->
            klass.modifier.isInterface() && modelContext.isNodeLeafInterface(klass.name)
        }
        Logger.debug("DSL create 函数候选: ${nodeLeafInterfaces.size}", 5)

        return DslExtensionCollection(
            groups = extFunMap.values.groupBy { it.receiver },
            nodeLeafInterfaces = nodeLeafInterfaces
        )
    }

    private fun addExtensionFun(
        extFunMap: MutableMap<String, KotlinExtensionFun>,
        extFun: KotlinExtensionFun
    ) {
        val sanitizedFunName = extFun.funName.replace("Impl", "")
        if (!CodeGenerationRules.canRegisterDslExtension(extFun.receiver, sanitizedFunName)) return
        if (!hasConcreteImplementation(extFun.funName)) return

        val finalFun = if (extFun.funName.endsWith("Impl") && !modelContext.generatedClassNameList.contains(extFun.funName)) {
            KotlinExtensionFun(extFun.receiver, sanitizedFunName, extFun.comments)
        } else {
            extFun
        }
        val key = "${finalFun.receiver} - ${finalFun.funName}"
        extFunMap[key] = finalFun
    }

    private fun generatePropertyExtensionFunctions(
        klass: String,
        prop: KotlinDeclaration.PropertyDecl
    ): List<KotlinExtensionFun> {
        if (propertyReferencesOnlyEnums(prop)) {
            Logger.debug("跳过 $klass#${prop.name} 的 DSL 扩展：属性类型为枚举", 6)
            return emptyList()
        }

        val kdoc = """
            /**
              * $klass#${prop.name}: ${prop.type.toTypeString()}
              * extension function for create ${prop.type.toTypeString()} -> {child}
              */
        """.trimIndent()

        val candidateTypes = collectReferenceTypes(prop.type)
        val instantiableTypes = candidateTypes.flatMap { resolveInstantiableTypes(it) }.distinct()
        return instantiableTypes.map { child ->
            KotlinExtensionFun(klass, child, kdoc.replace("{child}", child))
        }
    }

    private fun collectReferenceTypes(type: KotlinType): List<String> {
        return type.collectRawTypeNames()
            .map { normalizeTypeName(it) }
            .filter { it.isNotEmpty() && modelContext.classInfoByName.containsKey(it) }
            .distinct()
    }

    private fun KotlinType.collectRawTypeNames(): List<String> {
        return when (this) {
            is KotlinType.Simple -> listOf(this.name)
            is KotlinType.Nullable -> this.innerType.collectRawTypeNames()
            is KotlinType.Generic -> when (this.name) {
                "Array", "List", "MutableList", "MutableSet", "Set" -> this.params.flatMap { it.collectRawTypeNames() }
                else -> if (this.name.startsWith("Union.U")) {
                    this.params.flatMap { it.collectRawTypeNames() }
                } else {
                    listOf(this.name)
                }
            }
            is KotlinType.Union -> this.types.flatMap { it.collectRawTypeNames() }
            is KotlinType.Nested -> listOf("${this.parent}.${this.name}")
            is KotlinType.ReceiverFunction,
            is KotlinType.Function,
            is KotlinType.Any,
            is KotlinType.Unit,
            is KotlinType.Nothing,
            is KotlinType.StringType,
            is KotlinType.Int,
            is KotlinType.Boolean,
            is KotlinType.Long,
            is KotlinType.Double,
            is KotlinType.Float,
            is KotlinType.Char,
            is KotlinType.Byte,
            is KotlinType.Short -> emptyList()
        }
    }

    private fun resolveInstantiableTypes(typeName: String): List<String> {
        val normalized = normalizeTypeName(typeName)
        if (normalized.isEmpty()) return emptyList()
        val descendants = modelContext.hierarchy.findDescendants(normalized)
        val interfaceDescendants = descendants.filter { modelContext.classInfoByName[it]?.modifier?.isInterface() == true }
        val leafDescendants = interfaceDescendants.filter { modelContext.leafInterfaceNames.contains(it) }
        if (leafDescendants.isNotEmpty()) {
            return leafDescendants
        }
        val baseDecl = modelContext.classInfoByName[normalized]
        return if (baseDecl != null && (!baseDecl.modifier.isInterface() || modelContext.leafInterfaceNames.contains(normalized))) {
            listOf(baseDecl.name.removeSurrounding("`"))
        } else {
            emptyList()
        }
    }

    private fun normalizeTypeName(typeName: String): String {
        return typeName.removeSurrounding("`").trim()
    }

    private fun propertyReferencesOnlyEnums(prop: KotlinDeclaration.PropertyDecl): Boolean {
        val referencedTypes = collectReferenceTypes(prop.type)
        if (referencedTypes.isEmpty()) return false
        return referencedTypes.all { isEnumDeclaration(it) }
    }

    private fun isEnumDeclaration(typeName: String): Boolean {
        val normalized = normalizeTypeName(typeName)
        val decl = modelContext.classInfoByName[normalized]
        return decl?.modifier is ClassModifier.EnumClass
    }

    private fun getProperties(receiver: String): List<KotlinDeclaration.PropertyDecl> {
        modelContext.classAllPropertiesMap[receiver]?.let { return it }
        val sanitized = receiver.removeSurrounding("`")
        return modelContext.classInfoByName[sanitized]?.properties.orEmpty()
    }

    private fun hasConcreteImplementation(typeName: String): Boolean {
        val normalized = DslNamingRules.sanitizeTypeName(DslNamingRules.removeGenerics(typeName)).removeSurrounding("`")
        val decl = modelContext.classInfoByName[normalized]
        return when {
            decl == null -> modelContext.leafInterfaceNames.contains(normalized)
            decl.modifier.isInterface() -> modelContext.leafInterfaceNames.contains(normalized)
            else -> true
        }
    }
}

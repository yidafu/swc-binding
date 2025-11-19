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
    val nodeCreatableClasses: List<KotlinDeclaration.ClassDecl>
)

class DslExtensionCollector(
    private val modelContext: DslModelContext
) {
    fun collect(): DslExtensionCollection {
        val extFunMap = linkedMapOf<String, KotlinExtensionFun>()
        val receivers = LinkedHashSet<String>()
        // 作为接收者的候选集合应尽可能全面：所有声明 + 聚合的属性表 + 接口
        receivers.addAll(modelContext.classAllPropertiesMap.keys)
        modelContext.classDecls.forEach { receivers.add(it.name) }

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

        // 仅对可实例化的 Node 实现类生成 create 函数（避免对接口实例化）
        val nodeCreatableClasses = run {
            fun extractSimpleName(type: dev.yidafu.swc.generator.model.kotlin.KotlinType): String? {
                return when (type) {
                    is dev.yidafu.swc.generator.model.kotlin.KotlinType.Simple -> dev.yidafu.swc.generator.util.NameUtils.clean(type.name)
                    is dev.yidafu.swc.generator.model.kotlin.KotlinType.Nullable -> extractSimpleName(type.innerType)
                    else -> null
                }
            }

            // 获取 interfaceToImplMap 中定义的所有实现类名
            val interfaceToImplMap = dev.yidafu.swc.generator.config.SerializerConfig.interfaceToImplMap
            val knownImplClasses = interfaceToImplMap.values.toSet()

            // 调试：检查所有实现类
            val allImplClasses = modelContext.classDecls.filter { !it.modifier.isInterface() && it.name.removeSurrounding("`").endsWith("Impl") }
            Logger.debug("  所有 Impl 类: ${allImplClasses.map { it.name.removeSurrounding("`") }.joinToString(", ")}", 6)
            Logger.debug("  interfaceToImplMap 中的实现类: ${knownImplClasses.joinToString(", ")}", 6)

            val nodeImpls = modelContext.classDecls.filter { klass ->
                val className = klass.name.removeSurrounding("`")
                if (klass.modifier.isInterface()) return@filter false

                // 如果是在 interfaceToImplMap 中定义的实现类，直接包含（这些实现类都是为 Node 系谱的接口生成的）
                if (knownImplClasses.contains(className)) {
                    Logger.debug("  包含实现类（interfaceToImplMap）: $className", 6)
                    return@filter true
                }

                // 检查类本身是否继承自 Node
                if (modelContext.inheritsNode(className)) {
                    Logger.debug("  包含实现类（直接继承Node）: $className", 6)
                    return@filter true
                }

                // 对于实现类，检查其实现的接口是否继承自 Node
                val hasNodeParent = klass.parents.any { parent ->
                    val parentName = extractSimpleName(parent)
                    if (parentName != null) {
                        val inherits = modelContext.inheritsNode(parentName)
                        if (inherits) {
                            Logger.debug("  包含实现类（通过接口继承Node）: $className -> $parentName", 6)
                        }
                        inherits
                    } else {
                        false
                    }
                }
                hasNodeParent
            }
            val parserConfigImpls = modelContext.classDecls.filter { klass ->
                !klass.modifier.isInterface() && modelContext.inheritsTarget(klass.name.removeSurrounding("`"), "ParserConfig")
            }
            val merged = (nodeImpls + parserConfigImpls)
                .distinctBy { it.name.removeSurrounding("`") }
            Logger.debug("DSL create 函数候选(实现类，含 ParserConfig 子类): ${merged.size}", 5)
            merged
        }

        return DslExtensionCollection(
            groups = extFunMap.values.groupBy { it.receiver },
            nodeCreatableClasses = nodeCreatableClasses
        )
    }

    private fun addExtensionFun(
        extFunMap: MutableMap<String, KotlinExtensionFun>,
        extFun: KotlinExtensionFun
    ) {
        val sanitizedFunName = extFun.funName.replace("Impl", "")
        if (!CodeGenerationRules.canRegisterDslExtension(extFun.receiver, sanitizedFunName)) return
        if (!hasConcreteImplementation(extFun.funName)) return

        // 统一对外暴露去掉 Impl 的函数名；实现类型在发射阶段解析
        val finalFun = KotlinExtensionFun(extFun.receiver, sanitizedFunName, extFun.comments)
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

        // 检查属性类型是否为联合类型（Union.U2/U3）
        val unionTypeName = getUnionTypeName(prop.type)
        if (unionTypeName != null) {
            // 对于联合类型，只为联合类型本身生成一个 DSL 扩展函数
            val kdoc = """
                /**
                  * $klass#${prop.name}: ${prop.type.toTypeString()}
                  * extension function for union type: ${prop.type.toTypeString()}
                  */
            """.trimIndent()

            Logger.debug("为联合类型属性 $klass#${prop.name}: ${prop.type.toTypeString()} 生成 DSL 扩展函数", 6)
            return listOf(KotlinExtensionFun(klass, unionTypeName, kdoc))
        }

        // 对于非联合类型，保持原有逻辑：为每个可实例化的类型生成 DSL 扩展函数
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

    /**
     * 检查类型是否为联合类型（Union.U2/U3），如果是则返回联合类型的名称
     * 用于确定是否为联合类型，以便只为联合类型本身生成 DSL 扩展函数
     */
    private fun getUnionTypeName(type: KotlinType): String? {
        return when (type) {
            is KotlinType.Generic -> {
                if (type.name.startsWith("Union.U")) {
                    // 返回联合类型名称（如 "Union.U3"）
                    type.name
                } else {
                    null
                }
            }
            is KotlinType.Nullable -> {
                // 递归检查内部类型
                getUnionTypeName(type.innerType)
            }
            else -> null
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
        val baseDecl = modelContext.classInfoByName[normalized]

        // 如果是 sealed interface，返回所有可实例化的子类型（包括具体类和接口）
        val isSealedInterface = baseDecl?.modifier is ClassModifier.SealedInterface
        if (isSealedInterface) {
            val descendants = modelContext.hierarchy.findDescendants(normalized).distinct()
            val interfaceToImplMap = dev.yidafu.swc.generator.config.SerializerConfig.interfaceToImplMap

            // 返回所有可实例化的具体类后代
            val classDescendants = descendants.filter { name ->
                modelContext.classInfoByName[name]?.modifier?.isInterface() == false
            }

            // 对于接口子类型，如果它们在 interfaceToImplMap 中有对应的实现类，也返回它们
            val interfaceDescendants = descendants.filter { name ->
                val decl = modelContext.classInfoByName[name]
                decl?.modifier?.isInterface() == true && interfaceToImplMap.containsKey(name)
            }

            // 合并具体类和接口，返回所有可实例化的类型
            val allInstantiable = (classDescendants + interfaceDescendants).distinct()
            return allInstantiable
        }

        val descendants = modelContext.hierarchy.findDescendants(normalized).distinct()
        val interfaceToImplMap = dev.yidafu.swc.generator.config.SerializerConfig.interfaceToImplMap

        // 返回可实例化的具体类后代
        val classDescendants = descendants.filter { name ->
            modelContext.classInfoByName[name]?.modifier?.isInterface() == false
        }

        // 对于接口类型，如果它们在 interfaceToImplMap 中有对应的实现类，也返回它们
        val interfaceDescendants = descendants.filter { name ->
            val decl = modelContext.classInfoByName[name]
            decl?.modifier?.isInterface() == true && interfaceToImplMap.containsKey(name)
        }

        // 合并具体类和接口，返回所有可实例化的类型
        val allInstantiable = (classDescendants + interfaceDescendants).distinct()
        if (allInstantiable.isNotEmpty()) return allInstantiable

        // 如果自身是接口且在 interfaceToImplMap 中，返回自身
        if (baseDecl != null && baseDecl.modifier.isInterface() && interfaceToImplMap.containsKey(normalized)) {
            return listOf(normalized)
        }

        // 若自身就是具体类，则返回自身；否则不返回接口，避免生成对接口的构造
        return if (baseDecl != null && !baseDecl.modifier.isInterface()) {
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

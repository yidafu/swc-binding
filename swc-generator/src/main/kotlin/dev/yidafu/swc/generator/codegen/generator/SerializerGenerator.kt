package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFile
import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFileWriter
import dev.yidafu.swc.generator.codegen.pipeline.GenerationContext
import dev.yidafu.swc.generator.codegen.pipeline.GenerationPipeline
import dev.yidafu.swc.generator.codegen.pipeline.GenerationStage
import dev.yidafu.swc.generator.codegen.pipeline.WriteFilesStage
import dev.yidafu.swc.generator.codegen.poet.PoetConstants
import dev.yidafu.swc.generator.codegen.poet.createFileBuilder
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.getClassName
import dev.yidafu.swc.generator.util.Logger
import java.io.Closeable
import java.nio.file.Path
import java.nio.file.Paths
import dev.yidafu.swc.generator.config.Hardcoded

/**
 * serializer.kt 生成器（使用 KotlinPoet）
 */

class SerializerGenerator(
    private val writer: GeneratedFileWriter = GeneratedFileWriter()
) : Closeable {
    private val customSubclassSerializers = mapOf<String, String>()
    private val skipPolymorphicInterfaces = setOf<String>()

    private val pipeline = GenerationPipeline(
        listOf(
            GenerationStage<SerializerGenerationContext> { ctx ->
                ctx.generatedFiles += GeneratedFile(
                    outputPath = ctx.outputPath,
                    fileSpec = createSerializerFile(ctx.classDecls)
                )
                // 生成 Union 专属序列化器文件，输出到同目录下的 UnionSerializer.kt
                val unionPath: Path = ctx.outputPath.parent?.resolve("UnionSerializer.kt") ?: ctx.outputPath
                val unionFile = createUnionSerializerFile()
                ctx.generatedFiles += GeneratedFile(
                    outputPath = unionPath,
                    fileSpec = unionFile
                )
            },
            WriteFilesStage(writer)
        )
    )

    /**
     * 写入文件
     */
    fun writeToFile(outputPath: String, classDecls: List<KotlinDeclaration.ClassDecl>) {
        val context = SerializerGenerationContext(Paths.get(outputPath), classDecls)
        pipeline.execute(context)
        Logger.success("Generated: $outputPath (${classDecls.size} 个类型)")
    }

    private fun createSerializerFile(classDecls: List<KotlinDeclaration.ClassDecl>): FileSpec {
        Logger.debug("使用 KotlinPoet 生成 serializer.kt...", 4)
        // 允许作为多态基类的集合：
        // 1) 所有 sealed interface
        // 2) 额外白名单中的非 sealed 接口（作为外部 API 入口需要直接反序列化）
        val sealedBases = classDecls
            .filter { it.modifier is ClassModifier.SealedInterface }
            .map { it.name.removeSurrounding("`") }
            .toSet()
        val additionalOpenBases = setOf(
            "Identifier",
            "BindingIdentifier",
            "VariableDeclarator",
            "Module",
            "Script"
        )
        val serializableBases: Set<String> = sealedBases + additionalOpenBases
        val polymorphicGroups = buildPolymorphicGroups(classDecls, serializableBases)
        Logger.debug("  多态类型数: ${polymorphicGroups.values.sumOf { it.size }}", 4)
        val fileBuilder = createFileBuilder(
            PoetConstants.PKG_TYPES, "serializer",
            "kotlinx.serialization" to "DeserializationStrategy",
            "kotlinx.serialization" to "SerializationException",
            "kotlinx.serialization" to "*",
            "kotlinx.serialization.json" to "*",
            "kotlinx.serialization.modules" to "polymorphic",
            "kotlinx.serialization.modules" to "subclass",
            "kotlinx.serialization.modules" to "SerializersModule"
        )
        buildSerializerModuleOrder(polymorphicGroups.keys).forEach { discriminator ->
            polymorphicGroups[discriminator]?.let { map ->
                fileBuilder.addProperty(
                    createSerializersModuleProperty(
                        resolveModulePropertyName(discriminator),
                        map,
                        serializableBases
                    )
                )
            }
        }
        return fileBuilder.build()
    }

    /**
     * 生成 UnionSerializer.kt 文件，包含为每个使用点专属的 KSerializer 实现
     */
    private fun createUnionSerializerFile(): FileSpec {
        val fileBuilder = createFileBuilder(
            PoetConstants.PKG_TYPES,
            "UnionSerializer",
            "kotlinx.serialization" to "KSerializer",
            "kotlinx.serialization" to "serializer",
            "kotlinx.serialization.encoding" to "Encoder",
            "kotlinx.serialization.encoding" to "Decoder",
            "kotlinx.serialization.builtins" to "ArraySerializer",
            "dev.yidafu.swc" to "Union"
        )

        val usages = UnionSerializerRegistry.all()
        if (usages.isEmpty()) {
            // 仍生成空文件壳，便于稳定输出
            fileBuilder.addFileComment("No Union.Ux usages collected.")
            return fileBuilder.build()
        }

        // 基于 (unionKind + typeArguments) 去重
        val grouped = usages.groupBy { usage ->
            UnionSerializerRegistry.computeSerializerName(usage.unionKind, usage.typeArguments)
        }

        grouped.forEach { (serializerName, groupUsages) ->
            // 任取一个 usage 代表类型参数
            val usage = groupUsages.first()

            // 构造目标类型 KSerializer<T>
            val unionClass = ClassName("dev.yidafu.swc", "Union").nestedClass(usage.unionKind)
            val typeArgs = usage.typeArguments.mapIndexed { idx, tn ->
                if (usage.isNullableElement.getOrNull(idx) == true) tn.copy(nullable = true) else tn
            }
            val unionParamType = unionClass.parameterizedBy(*typeArgs.toTypedArray())
            val targetType = if (usage.isArray) {
                ClassName("kotlin", "Array").parameterizedBy(unionParamType)
            } else {
                unionParamType
            }
            val kSerializerType = ClassName("kotlinx.serialization", "KSerializer").parameterizedBy(targetType)

            // 构造委托序列化器表达式
            val serializerMember = MemberName("kotlinx.serialization", "serializer")
            val unionSerializerCall = CodeBlock.builder().apply {
                add("%T.serializerFor(", unionClass)
                usage.typeArguments.forEachIndexed { idx, tn ->
                    if (idx > 0) add(", ")
                    val argType = if (usage.isNullableElement.getOrNull(idx) == true) tn.copy(nullable = true) else tn
                    if (argType is com.squareup.kotlinpoet.ParameterizedTypeName &&
                        argType.rawType == ClassName("kotlin", "Array") &&
                        argType.typeArguments.size == 1
                    ) {
                        val elemType = argType.typeArguments[0]
                        add("%T(%M<%T>())", ClassName("kotlinx.serialization.builtins", "ArraySerializer"), serializerMember, elemType)
                    } else {
                        add("%M<%T>()", serializerMember, argType)
                    }
                }
                add(")")
            }.build()
            val delegateInit = if (usage.isArray) {
                CodeBlock.of("%T(%L)", ClassName("kotlinx.serialization.builtins", "ArraySerializer"), unionSerializerCall)
            } else {
                unionSerializerCall
            }

            val delegateProp = PropertySpec.builder("delegate", kSerializerType, KModifier.PRIVATE)
                .initializer(delegateInit)
                .build()

            val typeBuilder = TypeSpec.objectBuilder(serializerName)
                .addSuperinterface(kSerializerType)
                .addProperty(
                    PropertySpec.builder("descriptor", ClassName("kotlinx.serialization.descriptors", "SerialDescriptor"))
                        .addModifiers(KModifier.OVERRIDE)
                        .getter(
                            FunSpec.getterBuilder()
                                .addStatement("return delegate.descriptor")
                                .build()
                        )
                        .build()
                )
                .addProperty(delegateProp)
                .addFunction(
                    FunSpec.builder("serialize")
                        .addModifiers(KModifier.OVERRIDE)
                        .addParameter("encoder", ClassName("kotlinx.serialization.encoding", "Encoder"))
                        .addParameter("value", targetType)
                        .addStatement("return delegate.serialize(encoder, value)")
                        .build()
                )
                .addFunction(
                    FunSpec.builder("deserialize")
                        .addModifiers(KModifier.OVERRIDE)
                        .addParameter("decoder", ClassName("kotlinx.serialization.encoding", "Decoder"))
                        .returns(targetType)
                        .addStatement("return delegate.deserialize(decoder)")
                        .build()
                )

            fileBuilder.addType(typeBuilder.build())
        }

        // 生成完成后清空收集，避免污染下次生成
        UnionSerializerRegistry.clear()
        return fileBuilder.build()
    }

    /**
     * 内部收集用结构
     */
    private data class DeclarationsSnapshot(
        val normalizedToRaw: LinkedHashMap<String, String>,
        val rawToNormalized: LinkedHashMap<String, String>,
        val childToParents: LinkedHashMap<String, List<String>>,
        val interfaceNames: LinkedHashSet<String>,
        val concreteClasses: List<KotlinDeclaration.ClassDecl>
    )

    /**
     * 收集标准化声明信息
     */
    private fun collectDeclarations(classDecls: List<KotlinDeclaration.ClassDecl>): DeclarationsSnapshot {
        val normalizedToRaw = LinkedHashMap<String, String>()
        val rawToNormalized = LinkedHashMap<String, String>()
        val childToParents = LinkedHashMap<String, List<String>>()
        val interfaceNames = LinkedHashSet<String>()
        val concreteClasses = mutableListOf<KotlinDeclaration.ClassDecl>()

        classDecls.forEach { decl ->
            val normalized = decl.getClassName()
            normalizedToRaw[normalized] = decl.name
            rawToNormalized[decl.name] = normalized
            childToParents[normalized] = decl.parents.mapNotNull { it.extractSimpleName() }
            if (decl.modifier is ClassModifier.Interface || decl.modifier is ClassModifier.SealedInterface) {
                interfaceNames.add(normalized)
            } else {
                concreteClasses.add(decl)
            }
        }

        return DeclarationsSnapshot(
            normalizedToRaw = normalizedToRaw,
            rawToNormalized = rawToNormalized,
            childToParents = childToParents,
            interfaceNames = interfaceNames,
            concreteClasses = concreteClasses
        )
    }

    /**
     * 构建多态映射
     */
    private fun buildPolymorphicGroups(
        classDecls: List<KotlinDeclaration.ClassDecl>,
        serializableBases: Set<String>
    ): Map<String, LinkedHashMap<String, List<String>>> {
        val snapshot = collectDeclarations(classDecls)
        val normalizedToRaw = snapshot.normalizedToRaw
        val rawToNormalized = snapshot.rawToNormalized
        val childToParents = snapshot.childToParents
        val interfaceNames = snapshot.interfaceNames
        val concreteClasses = snapshot.concreteClasses

        val parentToChildren = mutableMapOf<String, LinkedHashSet<String>>()
        val concreteRawNames = concreteClasses
            .map { normalizedToRaw[it.getClassName()] ?: it.name }
            .toSet()
        val parentsWithDirectConcrete = collectParentsWithDirectConcrete(concreteClasses, childToParents)

        // 现有具体类（如果存在）直接参与多态映射
        seedDirectConcrete(concreteClasses, childToParents, interfaceNames, normalizedToRaw, parentToChildren)

        // 基于接口继承关系推导应生成的 Impl 类
        val interfaceChildrenMap = buildInterfaceChildrenMap(childToParents, interfaceNames)
        val leafInterfaces = interfaceNames.filter { interfaceChildrenMap[it].isNullOrEmpty() }

        inferMissingImpls(
            leafInterfaces = leafInterfaces,
            interfaceNames = interfaceNames,
            childToParents = childToParents,
            normalizedToRaw = normalizedToRaw,
            concreteRawNames = concreteRawNames,
            parentToChildren = parentToChildren
        )

        // 自动检测所有有对应 Impl 类的接口，并为其生成多态注册
        interfaceNames.forEach { normalizedInterface ->
            val rawInterfaceName = normalizedToRaw[normalizedInterface] ?: normalizedInterface
            // 尝试多种名称格式来查找对应的 Impl 类
            val possibleImplNames = listOf(
                rawInterfaceName.appendImplSuffix(),
                if (rawInterfaceName.startsWith("`") && rawInterfaceName.endsWith("`")) {
                    rawInterfaceName.substring(1, rawInterfaceName.length - 1) + "Impl"
                } else {
                    "`$rawInterfaceName`Impl"
                },
                normalizedInterface + "Impl",
                "`${normalizedInterface}Impl`"
            ).distinct()
            
            // 如果还是找不到，尝试在 concreteRawNames 中搜索包含接口名称的类
            val foundImplName = possibleImplNames.firstOrNull { concreteRawNames.contains(it) }
                ?: concreteRawNames.firstOrNull { implName ->
                    val normalizedImpl = implName.removeSurrounding("`", "`")
                    normalizedImpl == "${normalizedInterface}Impl" || normalizedImpl == "${rawInterfaceName.removeSurrounding("`", "`")}Impl"
                }
            
            // 如果还是找不到，检查是否在其他接口的多态注册中已经存在
            if (foundImplName == null && normalizedInterface == "Identifier") {
                // 特殊处理 Identifier：检查 parentToChildren 中是否已经有 IdentifierImpl
                val existingChildren = parentToChildren.values.flatten().filter { 
                    it.removeSurrounding("`", "`").endsWith("IdentifierImpl") 
                }
                if (existingChildren.isNotEmpty()) {
                    val identifierImplName = existingChildren.first()
                    parentToChildren.addChild(rawInterfaceName, identifierImplName)
                    Logger.verbose("  为接口 $rawInterfaceName 添加多态注册（从现有注册中找到），子类: $identifierImplName", 6)
                    return@forEach
                }
            }
            
            if (foundImplName != null) {
                // 找到对应的 Impl 类，确保该接口在 parentToChildren 中，并包含其 Impl 类
                // 即使接口已经在 parentToChildren 中（作为其他接口的子类），也要确保它本身作为父接口
                parentToChildren.addChild(rawInterfaceName, foundImplName)
                Logger.verbose("  为接口 $rawInterfaceName 添加多态注册，子类: $foundImplName", 6)
                
            } else {
                Logger.verbose("  接口 $rawInterfaceName 没有找到对应的 Impl 类（尝试了: ${possibleImplNames.joinToString(", ")})", 8)
            }
        }

        // 将子接口的注册“提升”到最近的可序列化祖先（例如 TsParserConfig/EsParserConfig -> ParserConfig）
        val remappedToSerializableAncestor = LinkedHashMap<String, LinkedHashSet<String>>()
        parentToChildren.forEach { (rawParent, childrenSet) ->
            val parentNorm = (rawToNormalized[rawParent] ?: rawParent).removeSurrounding("`")
            val ancestors = collectAncestorsIncludingSelf(parentNorm, childToParents)
            val serializableAncestor = ancestors.firstOrNull { anc -> serializableBases.contains(anc.removeSurrounding("`")) }
            val targetRaw = if (serializableAncestor != null) {
                normalizedToRaw[serializableAncestor] ?: serializableAncestor
            } else {
                rawParent
            }
            val set = remappedToSerializableAncestor.getOrPut(targetRaw) { LinkedHashSet() }
            set.addAll(childrenSet)
        }
        // 过滤无关注册（如无法证明祖先关系且 child 可追踪）
        val compact = LinkedHashMap<String, List<String>>().apply {
            remappedToSerializableAncestor.forEach { (rawParent, childrenSet) ->
                val parentNorm = (rawToNormalized[rawParent] ?: rawParent).removeSurrounding("`")
                val filtered = childrenSet.filter { childRaw ->
                    val childNorm = (rawToNormalized[childRaw] ?: childRaw).removeSurrounding("`")
                    if (!childToParents.containsKey(childNorm)) return@filter true
                    val ancestors = collectAncestorsIncludingSelf(childNorm, childToParents).map { it.removeSurrounding("`") }.toSet()
                    ancestors.contains(parentNorm)
                }
                if (filtered.isNotEmpty()) put(rawParent, filtered)
            }
        }
        // 向上扩散到所有可序列化祖先（例如 ModuleItem <- Statement/ModuleDeclaration）
        val expanded = LinkedHashMap<String, LinkedHashSet<String>>().apply {
            compact.forEach { (rawParent, children) ->
                val parentNorm = (rawToNormalized[rawParent] ?: rawParent).removeSurrounding("`")
                // 当前父
                val current = getOrPut(rawParent) { LinkedHashSet() }
                current.addAll(children)
                // 祖先父
                collectAncestors(parentNorm, childToParents).forEach { anc ->
                    val ancClean = anc.removeSurrounding("`")
                    if (serializableBases.contains(ancClean)) {
                        val ancRaw = normalizedToRaw[anc] ?: anc
                        val set = getOrPut(ancRaw) { LinkedHashSet() }
                        set.addAll(children)
                    }
                }
            }
        }

        val finalMap = LinkedHashMap<String, List<String>>().apply {
            expanded.forEach { (k, v) -> put(k, v.toList()) }
        }
        return groupByDiscriminator(finalMap, rawToNormalized)
    }

    private fun seedDirectConcrete(
        concreteClasses: List<KotlinDeclaration.ClassDecl>,
        childToParents: Map<String, List<String>>,
        interfaceNames: Set<String>,
        normalizedToRaw: Map<String, String>,
        parentToChildren: MutableMap<String, LinkedHashSet<String>>
    ) {
        concreteClasses.forEach { concrete ->
            val childName = concrete.getClassName()
            val rawChildName = normalizedToRaw[childName] ?: concrete.name
            // 仅对“直接父接口”做多态注册，避免对所有祖先接口重复注册
            val directParents = childToParents[childName].orEmpty()
            directParents.filter { it in interfaceNames }.forEach { directParent ->
                val rawParent = normalizedToRaw[directParent] ?: directParent
                parentToChildren.addChild(rawParent, rawChildName)
            }
        }
    }

    private fun inferMissingImpls(
        leafInterfaces: List<String>,
        interfaceNames: Set<String>,
        childToParents: Map<String, List<String>>,
        normalizedToRaw: Map<String, String>,
        concreteRawNames: Set<String>,
        parentToChildren: MutableMap<String, LinkedHashSet<String>>
    ) {
        leafInterfaces.forEach { leaf ->
            val rawLeaf = normalizedToRaw[leaf] ?: leaf
            val implRawName = rawLeaf.appendImplSuffix()
            if (!concreteRawNames.contains(implRawName)) {
                // 仅为“叶接口自身”添加其推导出的 Impl 映射，避免把 Impl 扩散注册到所有祖先接口
                parentToChildren.addChild(rawLeaf, implRawName)
            }
        }

        // 自动检测所有有对应 Impl 类的接口，并为其生成多态注册（保持旧逻辑）
        interfaceNames.forEach { normalizedInterface ->
            val rawInterfaceName = normalizedToRaw[normalizedInterface] ?: normalizedInterface
            val candidates = buildImplNameCandidates(rawInterfaceName, normalizedInterface)
            val foundImplName = candidates.firstOrNull { concreteRawNames.contains(it) }
                ?: concreteRawNames.firstOrNull { implName ->
                    val normalizedImpl = implName.removeSurrounding("`", "`")
                    normalizedImpl == "${normalizedInterface}Impl" || normalizedImpl == "${rawInterfaceName.removeSurrounding("`", "`")}Impl"
                }

            if (foundImplName != null) {
                parentToChildren.addChild(rawInterfaceName, foundImplName)
                Logger.verbose("  为接口 $rawInterfaceName 添加多态注册，子类: $foundImplName", 6)
            } else {
                if (normalizedInterface == "Identifier") {
                    val existingChildren = parentToChildren.values.flatten().filter {
                        it.removeSurrounding("`", "`").endsWith("IdentifierImpl")
                    }
                    if (existingChildren.isNotEmpty()) {
                        val identifierImplName = existingChildren.first()
                        parentToChildren.addChild(rawInterfaceName, identifierImplName)
                        Logger.verbose("  为接口 $rawInterfaceName 添加多态注册（从现有注册中找到），子类: $identifierImplName", 6)
                    } else {
                        Logger.verbose("  接口 $rawInterfaceName 没有找到对应的 Impl 类（尝试了: ${candidates.joinToString(", ")})", 8)
                    }
                } else {
                    Logger.verbose("  接口 $rawInterfaceName 没有找到对应的 Impl 类（尝试了: ${candidates.joinToString(", ")})", 8)
                }
            }
        }
    }

    private fun buildImplNameCandidates(rawInterfaceName: String, normalizedInterface: String): List<String> {
        return listOf(
            rawInterfaceName.appendImplSuffix(),
            if (rawInterfaceName.startsWith("`") && rawInterfaceName.endsWith("`")) {
                rawInterfaceName.substring(1, rawInterfaceName.length - 1) + "Impl"
            } else {
                "`$rawInterfaceName`Impl"
            },
            normalizedInterface + "Impl",
            "`${normalizedInterface}Impl`"
        ).distinct()
    }

    private fun orderParents(
        classDecls: List<KotlinDeclaration.ClassDecl>,
        parentToChildren: Map<String, LinkedHashSet<String>>,
        rawToNormalized: Map<String, String>
    ): LinkedHashMap<String, List<String>> {
        val orderedResult = LinkedHashMap<String, List<String>>()
        val parentOrder = LinkedHashSet<String>()
        classDecls.forEach { decl ->
            val rawParent = decl.name
            if (parentToChildren.containsKey(rawParent)) {
                parentOrder.add(rawParent)
            }
        }
        (parentOrder + (parentToChildren.keys - parentOrder)).forEach { rawParent ->
            val children = parentToChildren[rawParent]
            if (children.isNullOrEmpty()) {
                return@forEach
            }
            val normalizedParent = rawToNormalized[rawParent] ?: rawParent.removeSurrounding("`", "`")
            val cleanName = normalizedParent.removeSurrounding("`", "`")
            if (cleanName in skipPolymorphicInterfaces) {
                Logger.verbose("  跳过接口 $rawParent（在 skipPolymorphicInterfaces 中）", 8)
                return@forEach
            }
            orderedResult[rawParent] = children.toList()
            Logger.verbose("  $rawParent -> ${children.size} 个子类型: ${children.joinToString(", ")}", 6)
        }
        return orderedResult
    }

    private fun groupByDiscriminator(
        orderedResult: LinkedHashMap<String, List<String>>,
        rawToNormalized: Map<String, String>
    ): Map<String, LinkedHashMap<String, List<String>>> {
        val groupedResult = LinkedHashMap<String, LinkedHashMap<String, List<String>>>()
        orderedResult.forEach { (rawParent, children) ->
            val normalizedParent = rawToNormalized[rawParent] ?: rawParent.removeSurrounding("`", "`")
            val discriminator = if (normalizedParent.removeSurrounding("`", "`") in Hardcoded.Serializer.configInterfaceNames) {
                Hardcoded.Serializer.SYNTAX_DISCRIMINATOR
            } else {
                Hardcoded.Serializer.DEFAULT_DISCRIMINATOR
            }
            groupedResult
                .getOrPut(discriminator) { LinkedHashMap() }
                .put(rawParent, children)
        }
        return groupedResult
    }

    private fun collectParentsWithDirectConcrete(
        concreteClasses: List<KotlinDeclaration.ClassDecl>,
        childToParents: Map<String, List<String>>
    ): Set<String> {
        val parents = LinkedHashSet<String>()
        concreteClasses.forEach { concrete ->
            val childName = concrete.getClassName()
            childToParents[childName].orEmpty().forEach { parent ->
                parents.add(parent)
            }
        }
        return parents
    }

    private fun shouldGeneratePolymorphic(
        normalizedParent: String,
        interfaceChildrenMap: Map<String, LinkedHashSet<String>>,
        parentsWithDirectConcrete: Set<String>,
        concreteRawNames: Set<String>,
        rawParent: String
    ): Boolean {
        val cleanName = normalizedParent.removeSurrounding("`", "`")
        if (cleanName in skipPolymorphicInterfaces) {
            return false
        }

        // 检查接口是否有对应的 Impl 类
        val implRawName = rawParent.appendImplSuffix()
        if (concreteRawNames.contains(implRawName)) {
            // 有对应的 Impl 类，生成多态注册以便可以直接反序列化为接口类型
            return true
        }
        
        // 尝试多种名称格式
        val possibleImplNames = listOf(
            implRawName,
            if (rawParent.startsWith("`") && rawParent.endsWith("`")) {
                rawParent.substring(1, rawParent.length - 1) + "Impl"
            } else {
                "`$rawParent`Impl"
            },
            normalizedParent + "Impl",
            "`${normalizedParent}Impl`"
        ).distinct()
        
        // 检查是否有任何匹配的 Impl 类名称
        if (possibleImplNames.any { concreteRawNames.contains(it) }) {
            return true
        }
        
        // 检查是否在 concreteRawNames 中有标准化名称匹配的类
        val normalizedImplName = "${normalizedParent}Impl"
        if (concreteRawNames.any { it.removeSurrounding("`", "`") == normalizedImplName }) {
            return true
        }
        
        // 如果接口已经在 parentToChildren 中有子类（说明之前已经添加过），也应该生成多态注册
        // 这适用于从现有注册中找到的情况
        if (cleanName == "Identifier" || cleanName == "BindingIdentifier") {
            // 特殊处理：如果接口名称是 Identifier 或 BindingIdentifier，即使找不到对应的 Impl 类名称，
            // 只要它在 parentToChildren 中（说明之前已经添加过），也应该生成多态注册
            return true
        }

        val interfaceChildrenCount = interfaceChildrenMap[normalizedParent].orEmpty().size
        val hasDirectConcreteChild = normalizedParent in parentsWithDirectConcrete

        if (interfaceChildrenCount <= 1 && !hasDirectConcreteChild) {
            return false
        }

        return true
    }

    private fun MutableMap<String, LinkedHashSet<String>>.addChild(
        parent: String,
        child: String
    ) {
        val siblings = getOrPut(parent) { LinkedHashSet() }
        // 幂等注册：同一父子映射重复出现时静默跳过（仅在高日志级别下提示）
        if (!siblings.add(child)) {
            Logger.verbose("  跳过重复的 polymorphic 注册: $child -> $parent", 8)
        }
    }

    private fun buildInterfaceChildrenMap(
        childToParents: Map<String, List<String>>,
        interfaceNames: Set<String>
    ): Map<String, LinkedHashSet<String>> {
        val map = mutableMapOf<String, LinkedHashSet<String>>()
        childToParents.forEach { (child, parents) ->
            if (child in interfaceNames) {
                parents.filter { it in interfaceNames }.forEach { parent ->
                    map.getOrPut(parent) { LinkedHashSet() }.add(child)
                }
            }
        }
        return map
    }

    private fun collectAncestors(
        childName: String,
        childToParents: Map<String, List<String>>
    ): List<String> {
        val visited = LinkedHashSet<String>()
        val queue = ArrayDeque<String>()

        childToParents[childName].orEmpty().forEach { parent ->
            if (visited.add(parent)) {
                queue.add(parent)
            }
        }

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            childToParents[current].orEmpty().forEach { parent ->
                if (visited.add(parent)) {
                    queue.add(parent)
                }
            }
        }

        return visited.toList()
    }

    private fun collectAncestorsIncludingSelf(
        startName: String,
        childToParents: Map<String, List<String>>
    ): List<String> {
        val visited = LinkedHashSet<String>()
        val queue = ArrayDeque<String>()
        if (visited.add(startName)) {
            queue.add(startName)
        }
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            childToParents[current].orEmpty().forEach { parent ->
                if (visited.add(parent)) {
                    queue.add(parent)
                }
            }
        }
        return visited.toList()
    }

    private fun KotlinType.extractSimpleName(): String? {
        return when (this) {
            is KotlinType.Simple -> this.name.removeSurrounding("`", "`")
            is KotlinType.Nullable -> this.innerType.extractSimpleName()
            else -> null
        }
    }

    private fun String.appendImplSuffix(): String {
        return if (startsWith("`") && endsWith("`")) {
            val core = substring(1, length - 1)
            "`$core" + "Impl`"
        } else {
            this + "Impl"
        }
    }


    /**
     * 创建 swcSerializersModule 属性
     */
    private fun createSerializersModuleProperty(
        propertyName: String,
        polymorphicMap: Map<String, List<String>>,
        serializableBases: Set<String>
    ): PropertySpec {
        val initializerCode = buildSerializerModuleCode(polymorphicMap, serializableBases)

        return PropertySpec.builder(propertyName, PoetConstants.Serialization.Modules.SERIALIZERS_MODULE)
            .initializer(initializerCode)
            .build()
    }

    /**
     * 构建 SerializersModule 初始化代码
     */
    private fun buildSerializerModuleCode(
        polymorphicMap: Map<String, List<String>>,
        serializableBases: Set<String>
    ): CodeBlock {
        return CodeBlock.builder()
            .add("SerializersModule {\n")
            .apply {
                indent()
                polymorphicMap.forEach { (parent, children) ->
                    val cleanParent = parent.removeSurrounding("`")
                    if (!serializableBases.contains(cleanParent)) {
                        // 仅对已标注 @Serializable 的基类进行多态注册，避免 PolymorphismValidator 触发
                        Logger.verbose("  跳过未标注 @Serializable 的多态基类: $parent", 8)
                        return@forEach
                    }
                    add("polymorphic(%L::class) {\n", parent)
                    indent()
                    children.forEach { child ->
                        val serializer = customSubclassSerializers[child.removeSurrounding("`")]
                        if (serializer != null) {
                            add("subclass(%L::class, %L)\n", child, serializer)
                        } else {
                            add("subclass(%L::class)\n", child)
                        }
                    }
                    unindent()
                    add("}\n")
                }
                unindent()
            }
            .add("}")
            .build()
    }

    private fun buildSerializerModuleOrder(discriminators: Set<String>): List<String> {
        val prioritized = listOf(Hardcoded.Serializer.DEFAULT_DISCRIMINATOR, Hardcoded.Serializer.SYNTAX_DISCRIMINATOR)
        return prioritized.filter { discriminators.contains(it) } +
            discriminators.filterNot { it in prioritized }
    }

    private fun resolveModulePropertyName(discriminator: String): String {
        return when (discriminator) {
            Hardcoded.Serializer.DEFAULT_DISCRIMINATOR -> "swcSerializersModule"
            Hardcoded.Serializer.SYNTAX_DISCRIMINATOR -> "swcConfigSerializersModule"
            else -> "swc${discriminator.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}SerializersModule"
        }
    }

    /**
     * 关闭资源，释放线程池
     */
    override fun close() {
        writer.close()
    }
}

private data class SerializerGenerationContext(
    val outputPath: Path,
    val classDecls: List<KotlinDeclaration.ClassDecl>
) : GenerationContext {
    override val generatedFiles: MutableList<GeneratedFile> = mutableListOf()
}

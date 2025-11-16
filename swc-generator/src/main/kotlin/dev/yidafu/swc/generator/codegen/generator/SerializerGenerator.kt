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
import dev.yidafu.swc.generator.util.NameUtils
import dev.yidafu.swc.generator.util.NameUtils.appendImplSuffix
import dev.yidafu.swc.generator.util.NameUtils.buildImplNameCandidates
import dev.yidafu.swc.generator.util.NameUtils.normalized
import dev.yidafu.swc.generator.util.NameUtils.removeBackticks
import dev.yidafu.swc.generator.util.NameUtils.simpleNameOf
import dev.yidafu.swc.generator.util.NameUtils.sortMapKeysByNormalized
import dev.yidafu.swc.generator.util.NameUtils.sortNamesByNormalized
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
        val serializableBases: Set<String> = sealedBases + Hardcoded.Serializer.additionalOpenBases
        val polymorphicGroups = buildPolymorphicGroups(classDecls, serializableBases)
        // 语义校验：所有将作为 polymorphic 父的类型必须已标注 @Serializable
        validateSerializableParents(classDecls, polymorphicGroups)
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
                        sortMapKeysAndValues(map)
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
            "kotlinx.serialization.descriptors" to "SerialDescriptor",
            "kotlinx.serialization" to "serializer",
            "kotlinx.serialization.encoding" to "Encoder",
            "kotlinx.serialization.encoding" to "Decoder",
            "kotlinx.serialization.builtins" to "ArraySerializer",
            "dev.yidafu.swc" to "Union"
        )

        // 生成通用工厂（带缓存）
        emitUnionFactory(fileBuilder)

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

        fun buildArgSerializerExpr(argType: TypeName, isNullable: Boolean): CodeBlock {
            // 递归构造 ArraySerializer(serializer<Elem>()) 结构；再根据 isNullable 选择 serializer<T?>()
            val serializerMember = MemberName("kotlinx.serialization", "serializer")
            fun innerBuild(type: TypeName): CodeBlock {
                return if (type is com.squareup.kotlinpoet.ParameterizedTypeName &&
                    type.rawType == ClassName("kotlin", "Array") &&
                    type.typeArguments.size == 1
                ) {
                    val elem = type.typeArguments[0]
                    CodeBlock.of("%T(%L)", ClassName("kotlinx.serialization.builtins", "ArraySerializer"), innerBuild(elem))
                } else {
                    CodeBlock.of("%M<%T>()", serializerMember, type)
                }
            }
            val base = innerBuild(argType)
            return if (isNullable) {
                // 将最外层类型改为可空：通过直接请求 serializer<T?>()
                // 由于 base 可能已经是 ArraySerializer(...)，我们需要在类型位添加 '?'，重新生成
                CodeBlock.of("%M<%T?>()", serializerMember, argType)
            } else {
                base
            }
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

            // 通过工厂获取委托序列化器（复用 & 缓存）
            val delegateInit = CodeBlock.builder().apply {
                add("%T.get(", ClassName(PoetConstants.PKG_TYPES, "UnionFactory"))
                add("%S, ", usage.unionKind)
                add("%L, ", usage.isArray)
                add("listOf(")
                usage.typeArguments.forEachIndexed { idx, tn ->
                    if (idx > 0) add(", ")
                    val argType = if (usage.isNullableElement.getOrNull(idx) == true) tn.copy(nullable = true) else tn
                    val nullable = usage.isNullableElement.getOrNull(idx) == true
                    add("%L", buildArgSerializerExpr(argType, nullable))
                }
                add(")) as %T", kSerializerType)
            }.build()

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
     * 发出 UnionFactory（缓存 + 工厂）
     */
    private fun emitUnionFactory(fileBuilder: FileSpec.Builder) {
        val mapType = ClassName("java.util.concurrent", "ConcurrentHashMap")
            .parameterizedBy(ClassName("kotlin", "String"), ClassName("kotlinx.serialization", "KSerializer").parameterizedBy(STAR))

        val factoryType = TypeSpec.objectBuilder("UnionFactory")
            .addProperty(
                PropertySpec.builder("cache", mapType, KModifier.PRIVATE)
                    .initializer("%T()", ClassName("java.util.concurrent", "ConcurrentHashMap"))
                    .build()
            )
            .addFunction(
                FunSpec.builder("keyOf")
                    .addModifiers(KModifier.PRIVATE)
                    .returns(ClassName("kotlin", "String"))
                    .addParameter("kind", String::class)
                    .addParameter("isArray", Boolean::class)
                    .addParameter("argSerializers", ClassName("kotlin.collections", "List").parameterizedBy(ClassName("kotlinx.serialization", "KSerializer").parameterizedBy(STAR)))
                    .addCode(
                        CodeBlock.of(
                            "return buildString {\n" +
                                "  append(kind).append('|')\n" +
                                "  append(\"arr=\").append(isArray).append('|')\n" +
                                "  argSerializers.forEachIndexed { i, ks ->\n" +
                                "    if (i > 0) append(',')\n" +
                                "    val sn = ks.descriptor.serialName\n" +
                                "    append(sn)\n" +
                                "    if (%T.Union.includeNullabilityInKey) {\n" +
                                "      val isNull = sn.endsWith('?')\n" +
                                "      append(\":null=\").append(isNull)\n" +
                                "    }\n" +
                                "  }\n" +
                                "}\n",
                            ClassName("dev.yidafu.swc.generator.config", "Hardcoded")
                        )
                    )
                    .build()
            )
            .addFunction(
                FunSpec.builder("get")
                    .addKdoc("根据 Union 元数与参数序列化器获取（或缓存）对应的 KSerializer；必要时包装为 ArraySerializer。")
                    .returns(ClassName("kotlinx.serialization", "KSerializer").parameterizedBy(STAR))
                    .addParameter("kind", String::class)
                    .addParameter("isArray", Boolean::class)
                    .addParameter("argSerializers", ClassName("kotlin.collections", "List").parameterizedBy(ClassName("kotlinx.serialization", "KSerializer").parameterizedBy(STAR)))
                    .addCode(
                        CodeBlock.builder()
                            .addStatement("val key = keyOf(kind, isArray, argSerializers)")
                            .addStatement("val cached = cache[key]")
                            .addStatement("if (cached != null) return cached")
                            .add("val created: %T = when (kind) {\n", ClassName("kotlinx.serialization", "KSerializer").parameterizedBy(STAR))
                            .indent()
                            .addStatement("\"U2\" -> %T.U2.serializerFor(argSerializers[0], argSerializers[1])", ClassName("dev.yidafu.swc", "Union"))
                            .addStatement("\"U3\" -> %T.U3.serializerFor(argSerializers[0], argSerializers[1], argSerializers[2])", ClassName("dev.yidafu.swc", "Union"))
                            .addStatement("\"U4\" -> %T.U4.serializerFor(argSerializers[0], argSerializers[1], argSerializers[2], argSerializers[3])", ClassName("dev.yidafu.swc", "Union"))
                            .addStatement("\"U5\" -> %T.U5.serializerFor(argSerializers[0], argSerializers[1], argSerializers[2], argSerializers[3], argSerializers[4])", ClassName("dev.yidafu.swc", "Union"))
                            .addStatement("else -> throw IllegalArgumentException(\"Unsupported Union kind: $\" + kind)")
                            .unindent()
                            .addStatement("}")
                            .addStatement("val result = if (isArray) %T(created) else created", ClassName("kotlinx.serialization.builtins", "ArraySerializer"))
                            .addStatement("cache[key] = result")
                            .addStatement("return result")
                            .build()
                    )
                    .build()
            )
            .build()

        fileBuilder.addType(factoryType)
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
            childToParents[normalized] = decl.parents.mapNotNull { simpleNameOf(it) }
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
            val possibleImplNames = buildImplNameCandidates(rawInterfaceName, normalizedInterface)
            
            // 如果还是找不到，尝试在 concreteRawNames 中搜索包含接口名称的类
            val foundImplName = possibleImplNames.firstOrNull { concreteRawNames.contains(it) }
                ?: concreteRawNames.firstOrNull { implName ->
                    val normalizedImpl = implName.removeSurrounding("`", "`")
                    normalizedImpl == "${normalizedInterface}Impl" || normalizedImpl == "${rawInterfaceName.removeSurrounding("`", "`")}Impl"
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

        // 1) 提升到最近的可序列化祖先
        val remappedToSerializableAncestor = promoteToNearestSerializableAncestor(
            parentToChildren,
            serializableBases,
            rawToNormalized,
            normalizedToRaw,
            childToParents
        )
        // 2) 过滤并压缩
        val compact = compactAndFilterMappings(
            remappedToSerializableAncestor,
            rawToNormalized,
            childToParents
        )
        // 3) 向所有可序列化祖先扩散
        val expanded = expandToAllSerializableAncestors(
            compact,
            serializableBases,
            rawToNormalized,
            normalizedToRaw,
            childToParents
        )

        val finalMap = LinkedHashMap<String, List<String>>().apply {
            expanded.forEach { (k, v) -> put(k, v.toList()) }
        }
        return groupByDiscriminator(finalMap, rawToNormalized)
    }

    private fun promoteToNearestSerializableAncestor(
        parentToChildren: Map<String, LinkedHashSet<String>>,
        serializableBases: Set<String>,
        rawToNormalized: Map<String, String>,
        normalizedToRaw: Map<String, String>,
        childToParents: Map<String, List<String>>
    ): LinkedHashMap<String, LinkedHashSet<String>> {
        val result = LinkedHashMap<String, LinkedHashSet<String>>()
        parentToChildren.forEach { (rawParent, childrenSet) ->
            val parentNorm = clean(rawToNormalized[rawParent] ?: rawParent)
            val ancestors = ancestorsWithSelfOf(parentNorm, childToParents)
            val serializableAncestor = ancestors.firstOrNull { anc -> serializableBases.contains(clean(anc)) }
            val targetRaw = if (serializableAncestor != null) {
                normalizedToRaw[serializableAncestor] ?: serializableAncestor
            } else {
                rawParent
            }
            val set = result.getOrPut(targetRaw) { LinkedHashSet() }
            set.addAll(childrenSet)
        }
        return result
    }

    private fun compactAndFilterMappings(
        remapped: Map<String, LinkedHashSet<String>>,
        rawToNormalized: Map<String, String>,
        childToParents: Map<String, List<String>>
    ): LinkedHashMap<String, List<String>> {
        val compact = LinkedHashMap<String, List<String>>()
        remapped.forEach { (rawParent, childrenSet) ->
            val parentNorm = clean(rawToNormalized[rawParent] ?: rawParent)
            val filtered = childrenSet.filter { childRaw ->
                val childNorm = clean(rawToNormalized[childRaw] ?: childRaw)
                if (!childToParents.containsKey(childNorm)) return@filter true
                val ancestors = ancestorsWithSelfOf(childNorm, childToParents).map { clean(it) }.toSet()
                ancestors.contains(parentNorm)
            }
            if (filtered.isNotEmpty()) compact[rawParent] = filtered
        }
        return compact
    }

    private fun expandToAllSerializableAncestors(
        compact: Map<String, List<String>>,
        serializableBases: Set<String>,
        rawToNormalized: Map<String, String>,
        normalizedToRaw: Map<String, String>,
        childToParents: Map<String, List<String>>
    ): LinkedHashMap<String, LinkedHashSet<String>> {
        val expanded = LinkedHashMap<String, LinkedHashSet<String>>()
        compact.forEach { (rawParent, children) ->
            val parentNorm = clean(rawToNormalized[rawParent] ?: rawParent)
            val current = expanded.getOrPut(rawParent) { LinkedHashSet() }
            current.addAll(children)
            ancestorsOf(parentNorm, childToParents).forEach { anc ->
                val ancClean = clean(anc)
                if (serializableBases.contains(ancClean)) {
                    val ancRaw = normalizedToRaw[anc] ?: anc
                    val set = expanded.getOrPut(ancRaw) { LinkedHashSet() }
                    set.addAll(children)
                }
            }
        }
        return expanded
    }

    private fun ancestorsOf(name: String, rel: Map<String, List<String>>): List<String> =
        ancestorsCache.getOrPut(clean(name)) { collectAncestors(clean(name), rel) }

    private fun ancestorsWithSelfOf(name: String, rel: Map<String, List<String>>): List<String> =
        ancestorsWithSelfCache.getOrPut(clean(name)) { collectAncestorsIncludingSelf(clean(name), rel) }

    // 简单的名称清洗缓存，减少重复 removeSurrounding("`") 带来的分配
    private val cleanNameCache = mutableMapOf<String, String>()
    private fun clean(name: String): String = cleanNameCache.getOrPut(name) { name.removeSurrounding("`") }

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
            val implRawName = appendImplSuffix(rawLeaf)
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
                Logger.verbose("  接口 $rawInterfaceName 没有找到对应的 Impl 类（尝试了: ${candidates.joinToString(", ")})", 8)
            }
        }
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
            val discriminator = if (clean(normalizedParent) in Hardcoded.Serializer.configInterfaceNames) {
                Hardcoded.Serializer.SYNTAX_DISCRIMINATOR
            } else {
                Hardcoded.Serializer.DEFAULT_DISCRIMINATOR
            }
            groupedResult
                .getOrPut(discriminator) { LinkedHashMap() }
                .put(rawParent, children)
        }
        // 对每个分组内的 key 及其子类进行排序，提升稳定性
        val sortedGrouped = LinkedHashMap<String, LinkedHashMap<String, List<String>>>()
        groupedResult.forEach { (disc, map) ->
            val orderedMap = LinkedHashMap<String, List<String>>()
            map.keys.sortedBy { clean(it) }.forEach { parent ->
                val children = map.getValue(parent)
                orderedMap[parent] = children.sortedBy { clean(it) }
            }
            sortedGrouped[disc] = orderedMap
        }
        return sortedGrouped
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
        val implRawName = appendImplSuffix(rawParent)
        if (concreteRawNames.contains(implRawName)) {
            // 有对应的 Impl 类，生成多态注册以便可以直接反序列化为接口类型
            return true
        }
        
        // 尝试多种名称格式
        val possibleImplNames = buildImplNameCandidates(rawParent, normalizedParent)
        
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
        val norm = clean(childName)
        return ancestorsCache.getOrPut(norm) {
            val visited = LinkedHashSet<String>()
            val queue = ArrayDeque<String>()
            childToParents[norm].orEmpty().forEach { parent ->
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
            visited.toList()
        }
    }

    private fun collectAncestorsIncludingSelf(
        startName: String,
        childToParents: Map<String, List<String>>
    ): List<String> {
        val norm = clean(startName)
        return ancestorsWithSelfCache.getOrPut(norm) {
            val visited = LinkedHashSet<String>()
            val queue = ArrayDeque<String>()
            if (visited.add(norm)) {
                queue.add(norm)
            }
            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                childToParents[current].orEmpty().forEach { parent ->
                    if (visited.add(parent)) {
                        queue.add(parent)
                    }
                }
            }
            visited.toList()
        }
    }

    // 祖先链缓存
    private val ancestorsCache = mutableMapOf<String, List<String>>()
    private val ancestorsWithSelfCache = mutableMapOf<String, List<String>>()

    

    /**
     * 创建 swcSerializersModule 属性
     */
    private fun createSerializersModuleProperty(
        propertyName: String,
        polymorphicMap: Map<String, List<String>>
    ): PropertySpec {
        val initializerCode = buildSerializerModuleCode(polymorphicMap)

        return PropertySpec.builder(propertyName, PoetConstants.Serialization.Modules.SERIALIZERS_MODULE)
            .initializer(initializerCode)
            .build()
    }

    /**
     * 构建 SerializersModule 初始化代码
     */
    private fun buildSerializerModuleCode(
        polymorphicMap: Map<String, List<String>>
    ): CodeBlock {
        return CodeBlock.builder()
            .add("SerializersModule {\n")
            .apply {
                indent()
                // 防御性排序：父 key 与子类名基于去反引号后的字典序
                val orderedParents = polymorphicMap.keys.sortedBy { clean(it) }
                orderedParents.forEach { parent ->
                    val children = polymorphicMap.getValue(parent).sortedBy { clean(it) }
                    add("polymorphic(%L::class) {\n", parent)
                    indent()
                    children.forEach { child ->
                        val serializer = customSubclassSerializers[clean(child)]
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

    private fun sortMapKeysAndValues(map: Map<String, List<String>>): Map<String, List<String>> {
        val ordered = LinkedHashMap<String, List<String>>()
        map.keys.sortedBy { clean(it) }.forEach { k ->
            ordered[k] = map.getValue(k).sortedBy { clean(it) }
        }
        return ordered
    }

    /**
     * 校验所有作为 polymorphic 父的类型均已标注 @Serializable（策略可配置）
     */
    private fun validateSerializableParents(
        classDecls: List<KotlinDeclaration.ClassDecl>,
        groupedByDiscriminator: Map<String, LinkedHashMap<String, List<String>>>
    ) {
        val byNormalized = classDecls.associateBy { clean(it.getClassName()) }
        val missingDetails = mutableListOf<String>()
        val policy = Hardcoded.Serializer.missingSerializablePolicy
        val openBases = Hardcoded.Serializer.additionalOpenBases

        groupedByDiscriminator.forEach { (discriminator, parentMap) ->
            val moduleName = resolveModulePropertyName(discriminator)
            parentMap.keys.forEach { rawParent ->
                val norm = clean(rawParent)
                val decl = byNormalized[norm]
                val isSealed = decl?.modifier is ClassModifier.SealedInterface
                val annotated = decl?.annotations?.any { it.name == "Serializable" } == true
                if (!annotated) {
                    val msg = "多态父类型缺失 @Serializable: raw=$rawParent, norm=$norm, discriminator=$discriminator, module=$moduleName"
                    when (policy) {
                        Hardcoded.Serializer.MissingSerializablePolicy.ERROR -> {
                            missingDetails.add(msg)
                        }
                        Hardcoded.Serializer.MissingSerializablePolicy.WARN_OPEN_BASES -> {
                            // 非 sealed 接口默认仅告警（开放父接口更可能是外部入口或推断生成）
                            if (!isSealed || openBases.contains(norm)) {
                                Logger.warn(msg)
                            } else {
                                missingDetails.add(msg)
                            }
                        }
                        Hardcoded.Serializer.MissingSerializablePolicy.WARN_ALL -> {
                            Logger.warn(msg)
                        }
                    }
                }
            }
        }
        if (missingDetails.isNotEmpty()) {
            Logger.error("以下多态基类未标注 @Serializable：")
            missingDetails.forEach { Logger.error(" - $it") }
            throw IllegalStateException("Polymorphic parents must be annotated with @Serializable")
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

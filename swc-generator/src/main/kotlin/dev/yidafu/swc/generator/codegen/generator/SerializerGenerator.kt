package dev.yidafu.swc.generator.codegen.generator

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.codegen.poet.PoetConstants
import dev.yidafu.swc.generator.codegen.poet.createFileBuilder
import dev.yidafu.swc.generator.model.kotlin.ClassModifier
import dev.yidafu.swc.generator.model.kotlin.KotlinDeclaration
import dev.yidafu.swc.generator.model.kotlin.KotlinType
import dev.yidafu.swc.generator.model.kotlin.getClassName
import dev.yidafu.swc.generator.util.Logger
import java.io.File

/**
 * serializer.kt 生成器（使用 KotlinPoet）
 */
class SerializerGenerator {

    /**
     * 写入文件
     */
    fun writeToFile(outputPath: String, classDecls: List<KotlinDeclaration.ClassDecl>) {
        Logger.debug("使用 KotlinPoet 生成 serializer.kt...", 4)

        val polymorphicMap = buildPolymorphicMap(classDecls)
        Logger.debug("  多态类型数: ${polymorphicMap.size}", 4)

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

        fileBuilder.addProperty(createSerializersModuleProperty(polymorphicMap))

        val file = File(outputPath)
        file.parentFile?.mkdirs()

        val fileSpec = fileBuilder.build()

        // 创建临时目录来避免 KotlinPoet 创建包目录结构
        val tempDir = File.createTempFile("swc-generator", "").apply {
            delete()
            mkdirs()
        }

        try {
            // 写入到临时目录
            fileSpec.writeTo(tempDir)

            // 找到生成的文件（KotlinPoet 会根据包名创建目录结构）
            val generatedFile = File(tempDir, "dev/yidafu/swc/generated/${fileSpec.name}.kt")
            if (generatedFile.exists()) {
                // 复制到目标位置
                generatedFile.copyTo(file, overwrite = true)
            } else {
                // 如果没找到，尝试直接查找
                val foundFile = tempDir.walkTopDown().filter { it.name == "${fileSpec.name}.kt" }.firstOrNull()
                if (foundFile != null) {
                    foundFile.copyTo(file, overwrite = true)
                } else {
                    throw IllegalStateException("无法找到生成的文件: ${fileSpec.name}.kt")
                }
            }
        } finally {
            // 清理临时目录
            tempDir.deleteRecursively()
        }

        Logger.success("Generated: $outputPath (${classDecls.size} 个类型)")
    }

    /**
     * 构建多态映射
     */
    private fun buildPolymorphicMap(classDecls: List<KotlinDeclaration.ClassDecl>): Map<String, List<String>> {
        val normalizedToRaw = LinkedHashMap<String, String>()
        val childToParents = LinkedHashMap<String, List<String>>()
        val interfaceNames = LinkedHashSet<String>()
        val concreteClasses = mutableListOf<KotlinDeclaration.ClassDecl>()

        classDecls.forEach { decl ->
            val normalized = decl.getClassName()
            normalizedToRaw[normalized] = decl.name
            childToParents[normalized] = decl.parents.mapNotNull { it.extractSimpleName() }
            if (decl.modifier is ClassModifier.Interface || decl.modifier is ClassModifier.SealedInterface) {
                interfaceNames.add(normalized)
            } else {
                concreteClasses.add(decl)
            }
        }

        val parentToChildren = mutableMapOf<String, LinkedHashSet<String>>()

        // 现有具体类（如果存在）直接参与多态映射
        concreteClasses.forEach { concrete ->
            val childName = concrete.getClassName()
            val rawChildName = normalizedToRaw[childName] ?: concrete.name
            val ancestors = collectAncestors(childName, childToParents)
            ancestors.filter { it in interfaceNames }.forEach { ancestor ->
                val rawParent = normalizedToRaw[ancestor] ?: ancestor
                parentToChildren.getOrPut(rawParent) { LinkedHashSet() }.add(rawChildName)
            }
        }

        // 基于接口继承关系推导应生成的 Impl 类
        val interfaceChildrenMap = buildInterfaceChildrenMap(childToParents, interfaceNames)
        val leafInterfaces = interfaceNames.filter { interfaceChildrenMap[it].isNullOrEmpty() }

        leafInterfaces.forEach { leaf ->
            val rawLeaf = normalizedToRaw[leaf] ?: leaf
            val implRawName = rawLeaf.appendImplSuffix()
            collectAncestorsIncludingSelf(leaf, childToParents)
                .filter { it in interfaceNames }
                .forEach { ancestor ->
                    val rawParent = normalizedToRaw[ancestor] ?: ancestor
                    parentToChildren.getOrPut(rawParent) { LinkedHashSet() }.add(implRawName)
                }
        }

        val orderedResult = LinkedHashMap<String, List<String>>()
        classDecls.forEach { decl ->
            val rawParent = decl.name
            parentToChildren[rawParent]?.takeIf { it.isNotEmpty() }?.let { children ->
                orderedResult[rawParent] = children.toList()
                Logger.verbose("  $rawParent -> ${children.size} 个子类型", 6)
            }
        }

        return orderedResult
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
    private fun createSerializersModuleProperty(polymorphicMap: Map<String, List<String>>): PropertySpec {
        val initializerCode = buildSerializerModuleCode(polymorphicMap)

        return PropertySpec.builder("swcSerializersModule", PoetConstants.Serialization.Modules.SERIALIZERS_MODULE)
            .initializer(initializerCode)
            .build()
    }

    /**
     * 构建 SerializersModule 初始化代码
     */
    private fun buildSerializerModuleCode(polymorphicMap: Map<String, List<String>>): CodeBlock {
        return CodeBlock.builder()
            .add("SerializersModule {\n")
            .apply {
                indent()
                polymorphicMap.forEach { (parent, children) ->
                    add("polymorphic(%L::class) {\n", parent)
                    indent()
                    children.forEach { child -> add("subclass(%L::class)\n", child) }
                    unindent()
                    add("}\n")
                }
                unindent()
            }
            .add("}")
            .build()
    }
}

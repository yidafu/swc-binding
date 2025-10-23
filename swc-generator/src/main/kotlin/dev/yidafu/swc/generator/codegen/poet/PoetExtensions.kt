package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

/**
 * KotlinPoet 扩展函数
 */

/**
 * 创建 Lambda 类型：ReceiverType.() -> Unit
 */
fun createDslLambdaType(receiver: TypeName): LambdaTypeName {
    return LambdaTypeName.get(receiver = receiver, returnType = UNIT)
}

/**
 * 创建 Lambda 类型：ReceiverType.() -> Unit
 */
fun createDslLambdaType(receiverClassName: String): LambdaTypeName {
    return createDslLambdaType(ClassName(PoetConstants.PKG_TYPES, receiverClassName))
}

/**
 * 解析 Kotlin 类型字符串为 TypeName（增强版）
 */
fun String.parseAsTypeName(): TypeName {
    val cleanType = this.trim().replace(Regex("""/\*.*?\*/"""), "").trim()

    // 验证类型名称
    if (!cleanType.isValidKotlinTypeName()) {
        dev.yidafu.swc.generator.util.Logger.warn("无效的类型名称: '$cleanType'，使用 Any 替代")
        return ANY
    }

    // 处理数组
    if (cleanType.startsWith("Array<")) {
        val innerType = cleanType.substringAfter("Array<").substringBeforeLast(">")
        return try {
            ClassName("kotlin", "Array").parameterizedBy(innerType.parseAsTypeName())
        } catch (e: Exception) {
            dev.yidafu.swc.generator.util.Logger.warn("数组类型解析失败: $cleanType, ${e.message}")
            ANY
        }
    }

    // 处理泛型
    if (cleanType.contains("<")) {
        val baseName = cleanType.substringBefore("<")
        val typeParams = try {
            cleanType.extractGenericParams()
        } catch (e: Exception) {
            dev.yidafu.swc.generator.util.Logger.warn("泛型参数提取失败: $cleanType, ${e.message}")
            return ClassName("", baseName.sanitizeForClassName())
        }

        val baseClassName = when (baseName) {
            "Map" -> MAP
            "List" -> LIST
            "Set" -> SET
            "MutableMap" -> MUTABLE_MAP
            "MutableList" -> MUTABLE_LIST
            "MutableSet" -> MUTABLE_SET
            else -> ClassName("", baseName.sanitizeForClassName())
        }

        if (typeParams.isNotEmpty()) {
            return try {
                baseClassName.parameterizedBy(typeParams.map { it.parseAsTypeName() })
            } catch (e: Exception) {
                dev.yidafu.swc.generator.util.Logger.warn("泛型类型构造失败: $cleanType, ${e.message}")
                baseClassName
            }
        }
    }

    // 基础类型
    return when (cleanType) {
        "String" -> STRING
        "Int" -> INT
        "Long" -> LONG
        "Boolean" -> BOOLEAN
        "Double" -> DOUBLE
        "Float" -> FLOAT
        "Any" -> ANY
        "Unit" -> UNIT
        "Nothing" -> NOTHING
        else -> ClassName("", cleanType.sanitizeForClassName())
    }
}

/**
 * 提取泛型参数（支持嵌套）
 */
fun String.extractGenericParams(): List<String> {
    val params = this.substringAfter("<").substringBeforeLast(">")
    if (params.isEmpty()) return emptyList()

    return params.extractNestedGenericParams()
}

/**
 * 提取嵌套泛型参数
 * 例如: "Map<String, List<Int>>" -> ["Map<String, List<Int>>"]
 */
fun String.extractNestedGenericParams(): List<String> {
    if (isEmpty()) return emptyList()

    val result = mutableListOf<String>()
    var current = StringBuilder()
    var depth = 0

    for (char in this) {
        when (char) {
            '<' -> {
                depth++
                current.append(char)
            }
            '>' -> {
                depth--
                current.append(char)
            }
            ',' -> {
                if (depth == 0) {
                    result.add(current.toString().trim())
                    current = StringBuilder()
                } else {
                    current.append(char)
                }
            }
            else -> current.append(char)
        }
    }

    if (current.isNotEmpty()) {
        result.add(current.toString().trim())
    }

    return result
}

/**
 * 验证是否为有效的 Kotlin 类型名称
 */
fun String.isValidKotlinTypeName(): Boolean {
    if (isEmpty()) return false

    // 移除泛型参数后检查
    val baseName = this.substringBefore("<").trim()

    // 检查是否以大写字母或小写字母开头（允许基本类型）
    if (!baseName.first().isLetter()) return false

    // 检查是否只包含字母、数字、下划线
    if (!baseName.matches(Regex("[a-zA-Z][a-zA-Z0-9_]*"))) return false

    // 检查是否包含非法字符
    if (this.contains(" ") || this.contains("\n") || this.contains("\t")) return false

    // 检查泛型括号是否匹配
    val openCount = this.count { it == '<' }
    val closeCount = this.count { it == '>' }
    if (openCount != closeCount) return false

    return true
}

/**
 * 清理类型名称，使其适合用作 ClassName
 */
fun String.sanitizeForClassName(): String {
    var cleaned = this.trim()

    // 移除注释
    cleaned = cleaned.replace(Regex("""/\*.*?\*/"""), "").trim()

    // 移除泛型参数（ClassName 不接受泛型）
    cleaned = cleaned.substringBefore("<")

    // 移除空格和换行
    cleaned = cleaned.replace(Regex("\\s+"), "")

    // 确保首字母大写（除非是基本类型）
    if (cleaned.isNotEmpty() && !cleaned.first().isUpperCase()) {
        val basicTypes = setOf("string", "number", "boolean", "any", "unit", "nothing")
        if (!basicTypes.contains(cleaned.lowercase())) {
            cleaned = cleaned.replaceFirstChar { it.uppercase() }
        }
    }

    // 移除任何残留的非法字符
    cleaned = cleaned.replace(Regex("[^a-zA-Z0-9_]"), "")

    // 如果为空或不合法，返回 "Any"
    if (cleaned.isEmpty() || !cleaned.matches(Regex("[a-zA-Z][a-zA-Z0-9_]*"))) {
        return "Any"
    }

    return cleaned
}

/**
 * 清理注释文本
 */
fun String.cleanKdoc(): String {
    return this.replace("/**", "")
        .replace("*/", "")
        .replace("  * ", "")
        .replace("*", "")
        .replace("%", "%%") // 转义 % 字符，防止 KotlinPoet 将其解释为格式化占位符
        .trim()
}

/**
 * 为 TypeSpec.Builder 添加父类型（智能判断 superclass vs superinterface）
 */
fun TypeSpec.Builder.addParents(
    parents: List<String>,
    isInterface: Boolean
): TypeSpec.Builder {
    if (parents.isEmpty()) return this

    if (isInterface) {
        parents.forEach { parent ->
            addSuperinterface(parent.parseAsTypeName())
        }
    } else {
        // 第一个作为 superclass，其他作为 superinterface
        superclass(parents.first().parseAsTypeName())
        parents.drop(1).forEach { parent ->
            addSuperinterface(parent.parseAsTypeName())
        }
    }

    return this
}

/**
 * 为 TypeSpec.Builder 批量添加属性
 */
fun TypeSpec.Builder.addProperties(properties: List<PropertySpec>): TypeSpec.Builder {
    properties.forEach { addProperty(it) }
    return this
}

/**
 * 创建简单的扩展函数
 */
fun createExtensionFun(
    funName: String,
    receiverType: TypeName,
    returnType: TypeName,
    implType: TypeName,
    kdoc: String? = null
): FunSpec {
    return FunSpec.builder(funName)
        .receiver(receiverType)
        .addParameter("block", createDslLambdaType(returnType))
        .returns(returnType)
        .addStatement("return %T().apply(block)", implType)
        .apply {
            if (kdoc != null && kdoc.isNotEmpty()) {
                addKdoc(kdoc.cleanKdoc())
            }
        }
        .build()
}

/**
 * 创建文件级别的模板
 */
fun createFileBuilder(
    packageName: String,
    fileName: String,
    vararg imports: Pair<String, String>
): FileSpec.Builder {
    val builder = FileSpec.builder(packageName, fileName)

    imports.forEach { (pkg, name) ->
        // KotlinPoet 不支持 wildcard imports (*)
        // 跳过 wildcard，让 KotlinPoet 自动处理需要的 imports
        if (name != "*") {
            builder.addImport(pkg, name)
        }
    }

    return builder
}

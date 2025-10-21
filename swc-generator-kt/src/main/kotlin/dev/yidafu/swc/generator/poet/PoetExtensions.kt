package dev.yidafu.swc.generator.poet

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
 * 解析 Kotlin 类型字符串为 TypeName
 */
fun String.parseAsTypeName(): TypeName {
    val cleanType = this.trim().replace(Regex("""/\*.*?\*/"""), "").trim()
    
    // 处理数组
    if (cleanType.startsWith("Array<")) {
        val innerType = cleanType.substringAfter("Array<").substringBeforeLast(">")
        return ClassName("kotlin", "Array").parameterizedBy(innerType.parseAsTypeName())
    }
    
    // 处理泛型
    if (cleanType.contains("<")) {
        val baseName = cleanType.substringBefore("<")
        val typeParams = cleanType.extractGenericParams()
        
        val baseClassName = when (baseName) {
            "Map" -> MAP
            "List" -> LIST
            "Set" -> SET
            "MutableMap" -> MUTABLE_MAP
            "MutableList" -> MUTABLE_LIST
            "MutableSet" -> MUTABLE_SET
            else -> ClassName("", baseName)
        }
        
        if (typeParams.isNotEmpty()) {
            return baseClassName.parameterizedBy(typeParams.map { it.parseAsTypeName() })
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
        else -> ClassName("", cleanType)
    }
}

/**
 * 提取泛型参数
 */
fun String.extractGenericParams(): List<String> {
    val params = this.substringAfter("<").substringBeforeLast(">")
    if (params.isEmpty()) return emptyList()
    
    // TODO: 处理嵌套泛型，目前简化处理
    return params.split(",").map { it.trim() }
}

/**
 * 清理注释文本
 */
fun String.cleanKdoc(): String {
    return this.replace("/**", "")
        .replace("*/", "")
        .replace("  * ", "")
        .replace("*", "")
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


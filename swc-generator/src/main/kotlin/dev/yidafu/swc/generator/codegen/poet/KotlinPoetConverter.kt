package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.model.kotlin.*
import dev.yidafu.swc.generator.util.Logger
import dev.yidafu.swc.generator.util.PerformanceOptimizer

/**
 * ADT 到 KotlinPoet 的核心转换器
 * * 负责将 KotlinDeclaration ADT 转换为 KotlinPoet 的 TypeSpec、PropertySpec 等
 */
object KotlinPoetConverter {

    /**
     * 转换 KotlinType 为 TypeName
     * 使用性能优化的缓存
     */
    private val typeCache = mutableMapOf<String, TypeName>()

    fun convertType(kotlinType: KotlinType): TypeName {
        val typeString = kotlinType.toTypeString()

        // 使用缓存避免重复转换
        return typeCache.getOrPut(typeString) {
            PerformanceOptimizer.measureTime("类型转换: $typeString") {
                try {
                    kotlinType.toTypeName()
                } catch (e: Exception) {
                    Logger.warn("类型转换失败: $typeString, ${e.message}")
                    Logger.verbose("错误详情: ${e.stackTraceToString()}", 8)
                    throw e
                }
            }
        }
    }

    /**
     * 转换 KotlinDeclaration 为 TypeSpec
     */
    fun convertDeclaration(decl: KotlinDeclaration): TypeSpec {
        return convertDeclaration(decl, emptySet())
    }

    /**
     * 转换 KotlinDeclaration 为 TypeSpec（带接口名称参数）
     */
    fun convertDeclaration(decl: KotlinDeclaration, interfaceNames: Set<String>): TypeSpec {
        return when (decl) {
            is KotlinDeclaration.ClassDecl -> convertClassDeclaration(decl, interfaceNames)
            is KotlinDeclaration.PropertyDecl -> {
                // 属性声明不能直接转换为 TypeSpec，应该通过 convertProperty 处理
                throw IllegalArgumentException("PropertyDecl cannot be converted to TypeSpec directly")
            }
            is KotlinDeclaration.FunctionDecl -> {
                // 函数声明不能直接转换为 TypeSpec，应该通过 convertFunctionDeclaration 处理
                throw IllegalArgumentException("FunctionDecl cannot be converted to TypeSpec directly")
            }
            is KotlinDeclaration.TypeAliasDecl -> {
                // 类型别名声明不能直接转换为 TypeSpec，应该通过 convertTypeAliasDeclaration 处理
                throw IllegalArgumentException("TypeAliasDecl cannot be converted to TypeSpec directly")
            }
            is KotlinDeclaration.EnumEntry -> {
                // 枚举条目不能直接转换为 TypeSpec，应该通过枚举类处理
                throw IllegalArgumentException("EnumEntry cannot be converted to TypeSpec directly")
            }
        }
    }

    /**
     * 转换类声明为 TypeSpec
     */
    fun convertClassDeclaration(decl: KotlinDeclaration.ClassDecl, interfaceNames: Set<String> = emptySet()): TypeSpec {
        val builder = createTypeBuilder(decl.name, decl.modifier)

        // 添加修饰符
        addClassModifiers(builder, decl.modifier)

        // 添加注解
        decl.annotations.forEach { annotation ->
            convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
        }

        // 添加类型参数
        if (decl.typeParameters.isNotEmpty()) {
            decl.typeParameters.forEach { typeParam ->
                val typeVariable = TypeVariableName(typeParam.name)
                builder.addTypeVariable(typeVariable)
            }
        }

        // 添加父类型
        if (decl.parents.isNotEmpty()) {
            addParents(builder, decl.parents, decl.modifier is ClassModifier.Interface, interfaceNames)
        }

        // 添加属性或构造函数参数
        when (decl.modifier) {
            is ClassModifier.DataClass -> {
                // Data class: 属性作为构造函数参数
                val constructorParams = decl.properties.map { prop ->
                    val typeName = convertType(prop.type)
                    val paramBuilder = ParameterSpec.builder(prop.name, typeName)

                    // 添加注解
                    prop.annotations.forEach { annotation ->
                        convertAnnotation(annotation)?.let { paramBuilder.addAnnotation(it) }
                    }

                    // 添加默认值
                    prop.defaultValue?.let { defaultValue ->
                        val defaultValueStr = defaultValue.toCodeString()
                        if (defaultValueStr.isNotBlank()) {
                            paramBuilder.defaultValue(defaultValueStr)
                        }
                    }

                    paramBuilder.build()
                }
                builder.primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addParameters(constructorParams)
                        .build()
                )
            }
            is ClassModifier.EnumClass -> {
                // 为枚举类添加构造函数参数
                if (decl.properties.isNotEmpty()) {
                    val constructorParams = decl.properties.map { prop ->
                        val typeName = convertType(prop.type)
                        ParameterSpec.builder(prop.name, typeName).build()
                    }

                    builder.primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameters(constructorParams)
                            .build()
                    )
                }

                // 添加枚举条目
                decl.enumEntries.forEach { entry ->
                    if (entry.arguments.isNotEmpty()) {
                        // 有参数的枚举条目，使用匿名类
                        val enumBuilder = TypeSpec.anonymousClassBuilder()

                        // 添加枚举条目的参数
                        entry.arguments.forEach { arg ->
                            enumBuilder.addSuperclassConstructorParameter("%L", arg.toCodeString())
                        }

                        builder.addEnumConstant(entry.name, enumBuilder.build())
                    } else {
                        // 无参数的枚举条目
                        builder.addEnumConstant(entry.name)
                    }
                }
            }
            else -> {
                // 普通类: 属性作为类属性
                decl.properties.forEach { prop ->
                    convertProperty(prop)?.let { builder.addProperty(it) }
                }
            }
        }

        // 添加嵌套类
        decl.nestedClasses.forEach { nestedClass ->
            val nestedTypeSpec = convertClassDeclaration(nestedClass, interfaceNames)
            builder.addType(nestedTypeSpec)
        }

        // 添加文档
        decl.kdoc?.let { builder.addKdoc(it.cleanKdoc()) }

        return builder.build()
    }

    /**
     * 转换属性声明为 PropertySpec
     */
    fun convertProperty(prop: KotlinDeclaration.PropertyDecl): PropertySpec? {
        return try {
            val typeName = convertType(prop.type)
            val builder = PropertySpec.builder(prop.name, typeName)

            // 添加修饰符
            addPropertyModifiers(builder, prop.modifier)

            // 添加注解
            prop.annotations.forEach { annotation ->
                convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
            }

            // 添加默认值
            prop.defaultValue?.let { defaultValue ->
                val defaultValueStr = defaultValue.toCodeString()
                // 只有当默认值不为空时才添加 initializer
                if (defaultValueStr.isNotBlank()) {
                    builder.initializer(defaultValueStr)
                }
            }

            // 添加文档
            prop.kdoc?.let { builder.addKdoc(it.cleanKdoc()) }

            builder.build()
        } catch (e: Exception) {
            Logger.warn("转换属性失败: ${prop.name}, ${e.message}")
            Logger.warn("错误详情: ${e.stackTraceToString()}")
            null
        }
    }

    /**
     * 转换函数声明为 FunSpec
     */
    fun convertFunctionDeclaration(func: KotlinDeclaration.FunctionDecl): FunSpec {
        val builder = FunSpec.builder(func.name)

        // 添加接收者
        func.receiver?.let { receiver ->
            builder.receiver(convertType(receiver))
        }

        // 添加参数
        func.parameters.forEach { param ->
            val paramSpec = ParameterSpec.builder(
                param.name,
                convertType(param.type)
            )

            // 添加参数注解
            param.annotations.forEach { annotation ->
                convertAnnotation(annotation)?.let { paramSpec.addAnnotation(it) }
            }

            // 添加默认值
            param.defaultValue?.let { defaultValue ->
                paramSpec.defaultValue(defaultValue.toCodeString())
            }

            builder.addParameter(paramSpec.build())
        }

        // 设置返回类型
        builder.returns(convertType(func.returnType))

        // 添加修饰符
        addFunctionModifiers(builder, func.modifier)

        // 添加注解
        func.annotations.forEach { annotation ->
            convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
        }

        // 添加文档
        func.kdoc?.let { builder.addKdoc(it.cleanKdoc()) }

        return builder.build()
    }

    /**
     * 转换类型别名声明为 TypeAliasSpec
     */
    fun convertTypeAliasDeclaration(alias: KotlinDeclaration.TypeAliasDecl): TypeAliasSpec {
        val builder = TypeAliasSpec.builder(alias.name, convertType(alias.type))

        // 添加类型参数
        alias.typeParameters.forEach { typeParam ->
            val typeVar = when (typeParam.variance) {
                KotlinDeclaration.Variance.COVARIANT -> TypeVariableName(typeParam.name, KModifier.OUT)
                KotlinDeclaration.Variance.CONTRAVARIANT -> TypeVariableName(typeParam.name, KModifier.IN)
                else -> TypeVariableName(typeParam.name)
            }
            builder.addTypeVariable(typeVar)
        }

        // 添加注解
        alias.annotations.forEach { annotation ->
            convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
        }

        // 添加文档
        alias.kdoc?.let { builder.addKdoc(it.cleanKdoc()) }

        return builder.build()
    }

    /**
     * 转换注解为 AnnotationSpec
     * 使用缓存优化重复转换
     */
    private val annotationCache = mutableMapOf<String, AnnotationSpec?>()

    fun convertAnnotation(annotation: KotlinDeclaration.Annotation): AnnotationSpec? {
        val cacheKey = "${annotation.name}:${annotation.arguments.joinToString(",") { it.toCodeString() }}"

        return annotationCache.getOrPut(cacheKey) {
            try {
                val className = getAnnotationClassName(annotation.name)
                val builder = AnnotationSpec.builder(className)

                // 添加参数
                annotation.arguments.forEach { arg ->
                    addAnnotationArgument(builder, arg)
                }

                builder.build()
            } catch (e: Exception) {
                Logger.warn("转换注解失败: ${annotation.name}, ${e.message}")
                null
            }
        }
    }

    /**
     * 获取注解类名（提取公共逻辑）
     */
    private fun getAnnotationClassName(annotationName: String): ClassName {
        return when (annotationName) {
            "SerialName" -> ClassName("kotlinx.serialization", "SerialName")
            "Serializable" -> ClassName("kotlinx.serialization", "Serializable")
            "JsonClassDiscriminator" -> ClassName("kotlinx.serialization.json", "JsonClassDiscriminator")
            "OptIn" -> ClassName("kotlin", "OptIn")
            "SwcDslMarker" -> ClassName("dev.yidafu.swc.generated", "SwcDslMarker")
            else -> ClassName("", annotationName)
        }
    }

    /**
     * 添加注解参数（提取公共逻辑）
     */
    private fun addAnnotationArgument(builder: AnnotationSpec.Builder, arg: Expression) {
        when (arg) {
            is Expression.StringLiteral -> builder.addMember("%S", arg.value)
            is Expression.ClassReference -> {
                val className = when (arg.className) {
                    "ExperimentalSerializationApi" -> ClassName("kotlinx.serialization", "ExperimentalSerializationApi")
                    else -> ClassName("", arg.className)
                }
                builder.addMember("%T::class", className)
            }
            else -> builder.addMember("%L", arg.toCodeString())
        }
    }

    /**
     * 创建 TypeSpec.Builder
     */
    private fun createTypeBuilder(name: String, modifier: ClassModifier): TypeSpec.Builder {
        return when (modifier) {
            is ClassModifier.Interface -> TypeSpec.interfaceBuilder(name)
            is ClassModifier.SealedInterface -> TypeSpec.interfaceBuilder(name)
            is ClassModifier.Object -> TypeSpec.objectBuilder(name)
            is ClassModifier.EnumClass -> TypeSpec.enumBuilder(name)
            else -> TypeSpec.classBuilder(name)
        }
    }

    /**
     * 添加类修饰符
     */
    private fun addClassModifiers(builder: TypeSpec.Builder, modifier: ClassModifier) {
        // 所有顶级声明都应该是 public
        builder.addModifiers(KModifier.PUBLIC)

        when (modifier) {
            is ClassModifier.SealedInterface -> builder.addModifiers(KModifier.SEALED)
            is ClassModifier.SealedClass -> builder.addModifiers(KModifier.SEALED)
            is ClassModifier.OpenClass -> builder.addModifiers(KModifier.OPEN)
            is ClassModifier.AbstractClass -> builder.addModifiers(KModifier.ABSTRACT)
            is ClassModifier.DataClass -> builder.addModifiers(KModifier.DATA)
            else -> { /* 默认修饰符 */ }
        }
    }

    /**
     * 添加属性修饰符
     */
    private fun addPropertyModifiers(builder: PropertySpec.Builder, modifier: PropertyModifier) {
        // 所有属性都应该是 public
        builder.addModifiers(KModifier.PUBLIC)

        when (modifier) {
            is PropertyModifier.ConstVal -> {
                builder.addModifiers(KModifier.CONST)
                builder.mutable(false)
            }
            is PropertyModifier.Val -> builder.mutable(false)
            is PropertyModifier.Var -> builder.mutable(true)
            is PropertyModifier.LateinitVar -> {
                builder.addModifiers(KModifier.LATEINIT)
                builder.mutable(true)
            }
            is PropertyModifier.OverrideVal -> {
                builder.addModifiers(KModifier.OVERRIDE)
                builder.mutable(false)
            }
            is PropertyModifier.OverrideVar -> {
                builder.addModifiers(KModifier.OVERRIDE)
                builder.mutable(true)
            }
        }
    }

    /**
     * 添加函数修饰符
     */
    private fun addFunctionModifiers(builder: FunSpec.Builder, modifier: FunctionModifier) {
        when (modifier) {
            is FunctionModifier.OverrideFun -> builder.addModifiers(KModifier.OVERRIDE)
            is FunctionModifier.AbstractFun -> builder.addModifiers(KModifier.ABSTRACT)
            is FunctionModifier.OpenFun -> builder.addModifiers(KModifier.OPEN)
            is FunctionModifier.FinalFun -> builder.addModifiers(KModifier.FINAL)
            is FunctionModifier.InfixFun -> builder.addModifiers(KModifier.INFIX)
            is FunctionModifier.OperatorFun -> builder.addModifiers(KModifier.OPERATOR)
            is FunctionModifier.SuspendFun -> builder.addModifiers(KModifier.SUSPEND)
            else -> { /* 默认修饰符 */ }
        }
    }

    /**
     * 添加父类型
     */
    private fun addParents(
        builder: TypeSpec.Builder,
        parents: List<KotlinType>,
        isInterface: Boolean,
        interfaceNames: Set<String>
    ) {
        if (parents.isEmpty()) return

        if (isInterface) {
            parents.forEach { parent ->
                builder.addSuperinterface(convertType(parent))
            }
        } else {
            // 根据父类型本身的性质决定是 superclass 还是 superinterface
            parents.forEach { parent ->
                val parentTypeName = convertType(parent)
                // 检查父类型是否是接口（通过类型名称判断）
                if (isInterfaceType(parent, interfaceNames)) {
                    builder.addSuperinterface(parentTypeName)
                } else {
                    builder.superclass(parentTypeName)
                }
            }
        }
    }

    /**
     * 判断类型是否是接口
     * 使用动态生成的接口名称集合
     */
    private val interfaceSuffixes = setOf("Interface", "Options", "Config")

    private fun isInterfaceType(kotlinType: KotlinType, interfaceNames: Set<String>): Boolean {
        return when (kotlinType) {
            is KotlinType.Simple -> {
                val name = kotlinType.name
                interfaceNames.contains(name) || interfaceSuffixes.any { name.endsWith(it) }
            }
            else -> false
        }
    }
}

package dev.yidafu.swc.generator.codegen.poet

import com.squareup.kotlinpoet.*
import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.util.Logger

/**
 * ADT 到 KotlinPoet 的核心转换器
 * 
 * 负责将 KotlinDeclaration ADT 转换为 KotlinPoet 的 TypeSpec、PropertySpec 等
 */
object KotlinPoetConverter {
    
    /**
     * 转换 KotlinType 为 TypeName
     */
    fun convertType(kotlinType: KotlinType): TypeName {
        return try {
            kotlinType.toTypeName()
        } catch (e: Exception) {
            Logger.warn("类型转换失败: ${kotlinType.toTypeString()}, ${e.message}")
            Logger.warn("错误详情: ${e.stackTraceToString()}")
            throw e
        }
    }
    
    /**
     * 转换 KotlinDeclaration 为 TypeSpec
     */
    fun convertDeclaration(decl: KotlinDeclaration): TypeSpec {
        return when (decl) {
            is KotlinDeclaration.ClassDecl -> convertClassDeclaration(decl)
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
        }
    }
    
    /**
     * 转换类声明为 TypeSpec
     */
    fun convertClassDeclaration(decl: KotlinDeclaration.ClassDecl): TypeSpec {
        val builder = createTypeBuilder(decl.name, decl.modifier)
        
        // 添加修饰符
        addClassModifiers(builder, decl.modifier)
        
        // 添加注解
        decl.annotations.forEach { annotation ->
            convertAnnotation(annotation)?.let { builder.addAnnotation(it) }
        }
        
        // 添加父类型
        if (decl.parents.isNotEmpty()) {
            addParents(builder, decl.parents, decl.modifier is ClassModifier.Interface)
        }
        
        // 添加属性
        decl.properties.forEach { prop ->
            convertProperty(prop)?.let { builder.addProperty(it) }
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
                builder.initializer(defaultValue.toCodeString())
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
                Variance.COVARIANT -> TypeVariableName(typeParam.name, KModifier.OUT)
                Variance.CONTRAVARIANT -> TypeVariableName(typeParam.name, KModifier.IN)
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
     */
    fun convertAnnotation(annotation: KotlinDeclaration.Annotation): AnnotationSpec? {
        return try {
            val builder = AnnotationSpec.builder(ClassName("", annotation.name))
            
            // 添加参数
            annotation.arguments.forEach { arg ->
                builder.addMember("%L", arg.toCodeString())
            }
            
            builder.build()
        } catch (e: Exception) {
            Logger.warn("转换注解失败: ${annotation.name}, ${e.message}")
            null
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
        isInterface: Boolean
    ) {
        if (parents.isEmpty()) return
        
        if (isInterface) {
            parents.forEach { parent ->
                builder.addSuperinterface(convertType(parent))
            }
        } else {
            // 第一个作为 superclass，其他作为 superinterface
            builder.superclass(convertType(parents.first()))
            parents.drop(1).forEach { parent ->
                builder.addSuperinterface(convertType(parent))
            }
        }
    }
}

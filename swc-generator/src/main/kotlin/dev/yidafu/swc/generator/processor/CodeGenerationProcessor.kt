package dev.yidafu.swc.generator.processor

import dev.yidafu.swc.generator.adt.kotlin.*
import dev.yidafu.swc.generator.adt.result.GeneratorResult
import dev.yidafu.swc.generator.adt.result.GeneratorResultFactory
import dev.yidafu.swc.generator.adt.result.ErrorCode
import dev.yidafu.swc.generator.config.SwcGeneratorConfig
import dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzerHolder
import dev.yidafu.swc.generator.util.Logger

/**
 * 实现类生成处理器
 * 
 * 负责为接口生成对应的实现类（如 AbcImpl）
 * 修改 Kotlin ADT，添加实现类声明
 */
class CodeGenerationProcessor : KotlinADTProcessor {
    
    override fun processDeclarations(
        declarations: List<KotlinDeclaration>,
        config: SwcGeneratorConfig
    ): GeneratorResult<List<KotlinDeclaration>> {
        return try {
            Logger.debug("开始生成实现类...")
            
            // 分离类声明和类型别名
            val classDecls = declarations.filterIsInstance<KotlinDeclaration.ClassDecl>()
            val typeAliases = declarations.filterIsInstance<KotlinDeclaration.TypeAliasDecl>()
            
            Logger.debug("准备为接口生成实现类，接口数量: ${classDecls.size}")
            
            // 为接口生成实现类
            val implementationClasses = generateImplementationClasses(classDecls)
            
            // 合并原始声明和新的实现类
            val allDeclarations = mutableListOf<KotlinDeclaration>()
            allDeclarations.addAll(declarations)
            allDeclarations.addAll(implementationClasses)
            
            Logger.success("实现类生成完成，新增 ${implementationClasses.size} 个实现类")
            GeneratorResultFactory.success(allDeclarations)
        } catch (e: Exception) {
            Logger.error("实现类生成失败: ${e.message}")
            GeneratorResultFactory.failure(ErrorCode.CODE_GENERATION_ERROR, "实现类生成失败", e)
        }
    }
    
    override fun processDeclaration(
        declaration: KotlinDeclaration,
        config: SwcGeneratorConfig
    ): GeneratorResult<KotlinDeclaration> {
        // 实现类生成处理器不需要处理单个声明
        return GeneratorResultFactory.success(declaration)
    }
    
    /**
     * 为接口生成实现类
     */
    private fun generateImplementationClasses(classDecls: List<KotlinDeclaration.ClassDecl>): List<KotlinDeclaration.ClassDecl> {
        val analyzer = InheritanceAnalyzerHolder.get()
        val implementationClasses = mutableListOf<KotlinDeclaration.ClassDecl>()
        
        // 只处理接口
        val interfaces = classDecls.filter { it.modifier == ClassModifier.Interface }
        
        interfaces.forEach { interfaceDecl ->
            try {
                val implClass = createImplementationClass(interfaceDecl, analyzer, classDecls)
                implementationClasses.add(implClass)
                Logger.verbose("  ✓ 生成实现类: ${implClass.name}", 4)
            } catch (e: Exception) {
                Logger.warn("  生成实现类失败: ${interfaceDecl.name}Impl, ${e.message}")
            }
        }
        
        return implementationClasses
    }
    
    /**
     * 创建实现类
     */
    private fun createImplementationClass(
        interfaceDecl: KotlinDeclaration.ClassDecl,
        analyzer: dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzer,
        allClassDecls: List<KotlinDeclaration.ClassDecl>
    ): KotlinDeclaration.ClassDecl {
        // 收集所有继承的属性（包括父接口的属性）
        val allProperties = collectAllProperties(interfaceDecl, analyzer, allClassDecls)
        
        return interfaceDecl.copy(
            name = "${interfaceDecl.name}Impl",
            modifier = ClassModifier.DataClass,
            properties = allProperties
        )
    }
    
    /**
     * 收集所有属性（包括继承的属性）
     */
    private fun collectAllProperties(
        interfaceDecl: KotlinDeclaration.ClassDecl,
        analyzer: dev.yidafu.swc.generator.adt.typescript.InheritanceAnalyzer,
        allClassDecls: List<KotlinDeclaration.ClassDecl>
    ): List<KotlinDeclaration.PropertyDecl> {
        val allProperties = mutableSetOf<KotlinDeclaration.PropertyDecl>()
        
        // 添加当前接口的属性
        allProperties.addAll(interfaceDecl.properties)
        
        // 递归添加父接口的属性
        val parentInterfaces = analyzer.findAllParentsByChild(interfaceDecl.name)
        parentInterfaces.forEach { parentName ->
            val parentInterface = allClassDecls.find { it.name == parentName }
            if (parentInterface != null) {
                allProperties.addAll(collectAllProperties(parentInterface, analyzer, allClassDecls))
            }
        }
        
        return allProperties.toList()
    }
    
}

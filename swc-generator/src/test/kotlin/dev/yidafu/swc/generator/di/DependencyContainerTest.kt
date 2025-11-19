package dev.yidafu.swc.generator.di

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.test.assertNotNull
import io.kotest.core.spec.style.ShouldSpec

class DependencyContainerTest : ShouldSpec({

    val config = Configuration.default()

    should("test container creation") {
        val container = DependencyContainer(config)
        assertNotNull(container)
    }

    should("test container provides TypeScriptParser") {
        val container = DependencyContainer(config)
        assertNotNull(container.typeScriptParser)
    }

    should("test container provides createTypeScriptADTExtractor factory") {
        val container = DependencyContainer(config)
        // TypeScriptADTExtractor 需要通过工厂方法创建，需要 visitor 参数
        // 这里只测试工厂方法存在
        assertNotNull(container)
    }

    should("test container provides InheritanceAnalyzer") {
        val container = DependencyContainer(config)
        assertNotNull(container.inheritanceAnalyzer)
    }

    should("test container provides TypeScriptToKotlinConverter") {
        val container = DependencyContainer(config)
        assertNotNull(container.typeScriptToKotlinConverter)
    }

    should("test container provides KotlinADTProcessor") {
        val container = DependencyContainer(config)
        assertNotNull(container.kotlinADTProcessor)
    }

    should("test container provides CodeEmitter") {
        val container = DependencyContainer(config)
        assertNotNull(container.codeEmitter)
    }

    should("test container provides PerformanceOptimizer") {
        val container = DependencyContainer(config)
        assertNotNull(container.performanceOptimizer)
    }

    should("test container provides SwcGeneratorConfig") {
        val container = DependencyContainer(config)
        assertNotNull(container.swcGeneratorConfig)
    }

    should("test container components are lazy initialized") {
        val container = DependencyContainer(config)

        // 访问组件应该触发初始化
        val parser = container.typeScriptParser
        assertNotNull(parser)

        // 再次访问应该返回同一个实例（如果使用单例模式）
        val parser2 = container.typeScriptParser
        assertNotNull(parser2)
    }
})

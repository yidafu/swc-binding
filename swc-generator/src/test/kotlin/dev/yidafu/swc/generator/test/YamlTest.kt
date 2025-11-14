package dev.yidafu.swc.generator.test

import dev.yidafu.swc.generator.config.ConfigurationLoader
import java.io.File

/**
 * 测试 kaml 库的 YAML 解析功能
 */
fun main() {
    println("=== 测试 kaml 库 YAML 解析功能 ===")

    val loader = ConfigurationLoader()

    // 测试1: 生成示例配置文件
    println("\n1. 生成示例配置文件...")
    val sampleConfigPath = "test-config.yaml"
    val generateResult = loader.generateSampleConfig(sampleConfigPath)

    if (generateResult.isSuccess()) {
        println("✓ 示例配置文件生成成功")

        // 测试2: 解析生成的配置文件
        println("\n2. 解析生成的配置文件...")
        val loadResult = loader.loadFromFile(sampleConfigPath)

        if (loadResult.isSuccess()) {
            val config = loadResult.getOrThrow()
            println("✓ 配置文件解析成功")
            println("  - 输入路径: ${config.input.inputPath}")
            println("  - 输出路径: ${config.output.outputTypesPath}")
            println("  - 启用缓存: ${config.behavior.enableCaching}")
            println("  - 并行处理: ${config.behavior.enableParallelProcessing}")

            // 测试3: 验证配置内容
            println("\n3. 验证配置内容...")
            val validationResult = config.validate()
            if (validationResult.isSuccess()) {
                println("✓ 配置验证通过")
            } else {
                validationResult.onFailure { error ->
                    println("✗ 配置验证失败: ${error.message}")
                }
            }
        } else {
            loadResult.onFailure { error ->
                println("✗ 配置文件解析失败: ${error.message}")
            }
        }

        // 清理测试文件
        File(sampleConfigPath).delete()
        println("\n✓ 测试文件已清理")
    } else {
        generateResult.onFailure { error ->
            println("✗ 示例配置文件生成失败: ${error.message}")
        }
    }

    // 测试4: 测试不存在的配置文件（应该使用默认配置）
    println("\n4. 测试不存在的配置文件...")
    val defaultResult = loader.loadFromFile("non-existent-config.yaml")
    if (defaultResult.isSuccess()) {
        println("✓ 默认配置加载成功")
    } else {
        defaultResult.onFailure { error ->
            println("✗ 默认配置加载失败: ${error.message}")
        }
    }

    println("\n=== kaml 库 YAML 解析功能测试完成 ===")
}

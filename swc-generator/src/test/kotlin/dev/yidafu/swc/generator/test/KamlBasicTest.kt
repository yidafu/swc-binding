package dev.yidafu.swc.generator.test

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.Serializable

@Serializable
data class TestConfig(
    val name: String,
    val value: Int,
    val enabled: Boolean
)

fun main() {
    println("=== 测试 kaml 库基本功能 ===")
    
    try {
        // 测试1: 序列化
        println("\n1. 测试序列化...")
        val testConfig = TestConfig("test", 42, true)
        val yaml = Yaml.default
        val yamlString = yaml.encodeToString(TestConfig.serializer(), testConfig)
        println("✓ 序列化成功:")
        println(yamlString)
        
        // 测试2: 反序列化
        println("\n2. 测试反序列化...")
        val parsedConfig = yaml.decodeFromString(TestConfig.serializer(), yamlString)
        println("✓ 反序列化成功:")
        println("  - name: ${parsedConfig.name}")
        println("  - value: ${parsedConfig.value}")
        println("  - enabled: ${parsedConfig.enabled}")
        
        // 测试3: 验证数据一致性
        println("\n3. 验证数据一致性...")
        if (testConfig == parsedConfig) {
            println("✓ 数据一致性验证通过")
        } else {
            println("✗ 数据一致性验证失败")
        }
        
        println("\n=== kaml 库基本功能测试完成 ===")
        
    } catch (e: Exception) {
        println("✗ 测试失败: ${e.message}")
        e.printStackTrace()
    }
}

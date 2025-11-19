import dev.yidafu.swc.generator.config.ConfigurationLoader
import java.io.File

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
            
        } else {
            println("✗ 配置文件解析失败")
        }
        
        // 清理测试文件
        File(sampleConfigPath).delete()
        println("\n✓ 测试文件已清理")
        
    } else {
        println("✗ 示例配置文件生成失败")
    }
    
    println("\n=== kaml 库 YAML 解析功能测试完成 ===")
}

package dev.yidafu.swc.generator.core.model

import dev.yidafu.swc.generator.config.Constants

/**
 * Kotlin 扩展函数模型
 */
data class KotlinExtensionFun(
    val receiver: String = "",
    val funName: String = "",
    val comments: String = ""
) {
    /**
     * 转换函数名（首字母小写，处理关键字）
     */
    private fun toFunName(str: String): String {
        val name = str[0].lowercase() + str.substring(1)
        return Constants.kotlinKeywordMap[name] ?: name
    }
    
    /**
     * 转换为字符串
     */
    override fun toString(): String {
        var actualFunName = funName
        val noImpl = funName.replace("Impl", "")
        
        // 如果在 noImplClassList 中，则不需要 Impl 后缀
        // 这里需要传入 noImplClassList，暂时简化处理
        
        val lines = mutableListOf<String>()
        
        if (comments.isNotEmpty()) {
            lines.add(comments)
        }
        
        lines.add("fun $receiver.${toFunName(noImpl)}(block: $noImpl.() -> Unit): ${noImpl} {")
        lines.add("  return ${actualFunName}().apply(block)")
        lines.add("}")
        
        return lines.joinToString("\n")
    }
}


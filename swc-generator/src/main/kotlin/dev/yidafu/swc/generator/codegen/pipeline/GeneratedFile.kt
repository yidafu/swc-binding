package dev.yidafu.swc.generator.codegen.pipeline

import com.squareup.kotlinpoet.FileSpec
import java.nio.file.Path

/**
 * 待写入的文件。
 *
 * @param fileSpec KotlinPoet 生成的文件（可选）
 * @param contentProducer 直接提供内容的函数（可选）
 * @param outputPath 目标输出路径
 * @param formatter 针对单个文件的格式化函数
 */
data class GeneratedFile(
    val outputPath: Path,
    val fileSpec: FileSpec? = null,
    val contentProducer: (() -> String)? = null,
    val formatter: ((String, Path) -> String)? = null
) {
    init {
        require(fileSpec != null || contentProducer != null) {
            "GeneratedFile 必须提供 fileSpec 或 contentProducer"
        }
    }

    fun buildContent(): String = fileSpec?.toString() ?: contentProducer!!.invoke()
}

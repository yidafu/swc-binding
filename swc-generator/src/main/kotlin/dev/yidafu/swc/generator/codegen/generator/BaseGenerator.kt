package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFileWriter
import java.io.Closeable

/**
 * Generator 基类，提供公共的资源管理逻辑
 */
abstract class BaseGenerator(
    protected val writer: GeneratedFileWriter = GeneratedFileWriter()
) : Closeable {

    /**
     * 关闭资源，释放线程池
     */
    override fun close() {
        writer.close()
    }
}


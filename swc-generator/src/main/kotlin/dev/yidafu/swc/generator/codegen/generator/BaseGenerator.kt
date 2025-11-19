package dev.yidafu.swc.generator.codegen.generator

import dev.yidafu.swc.generator.codegen.pipeline.GeneratedFileWriter
import java.io.Closeable

/**
 * Generator 基类
 *
 * 提供公共的资源管理逻辑，所有生成器都应继承此类。
 * 实现了 [Closeable] 接口，确保资源能够正确释放。
 *
 * @param writer 文件写入器，用于写入生成的文件
 */
abstract class BaseGenerator(
    protected val writer: GeneratedFileWriter = GeneratedFileWriter()
) : Closeable {

    /**
     * 关闭资源
     *
     * 释放文件写入器占用的资源（如线程池等）。
     * 应在使用完生成器后调用此方法。
     */
    override fun close() {
        writer.close()
    }
}

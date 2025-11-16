package dev.yidafu.swc

import dev.yidafu.swc.generated.Program
import dev.yidafu.swc.generated.swcConfigSerializersModule
import dev.yidafu.swc.generated.swcSerializersModule
import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.Param
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@OptIn(ExperimentalSerializationApi::class)
private val extendedSwcSerializersModule: SerializersModule = SerializersModule {
    include(swcSerializersModule)
    // 不再注册 *Impl 实现类，保持由生成的多态模块处理
}

@OptIn(ExperimentalSerializationApi::class)
private val extendedSwcConfigSerializersModule: SerializersModule = SerializersModule {
    include(swcConfigSerializersModule)
    // 不再注册 OptionsImpl，保持由生成的多态模块处理
}

@OptIn(ExperimentalSerializationApi::class)
val astJson = Json {
    classDiscriminator = "type"
    serializersModule = extendedSwcSerializersModule
    // TODO: @swc/types seems incomplete
    ignoreUnknownKeys = true
    explicitNulls = true // Include null values in JSON to prevent Rust deserialization errors
    encodeDefaults = true // Include default values (e.g., Span.ctxt = 0) in JSON
    coerceInputValues = true // Coerce missing values to defaults during deserialization (e.g., missing ctxt -> 0)
}

@OptIn(ExperimentalSerializationApi::class)
val configJson = Json {
    classDiscriminator = "syntax"
    serializersModule = extendedSwcConfigSerializersModule
    explicitNulls = false // Don't include null values - Rust expects missing fields to use defaults
    encodeDefaults = true // Include default values (e.g., Span.ctxt = 0) in JSON
}

/**
 * 用于 TransformOutput 的简单 JSON 解码器
 * TransformOutput 是简单的数据类，不需要复杂的序列化配置
 */
val outputJson = Json {
    ignoreUnknownKeys = true
    explicitNulls = false // TransformOutput 的 msg 字段是可选的
    encodeDefaults = false
}

fun parseAstTree(jsonStr: String): Program {
    return astJson.decodeFromString<Program>(jsonStr)
}
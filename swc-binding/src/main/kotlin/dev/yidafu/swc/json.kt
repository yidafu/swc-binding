package dev.yidafu.swc

import dev.yidafu.swc.types.Program
import dev.yidafu.swc.types.configSerializer
import dev.yidafu.swc.types.swcSerializersModule
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val astJson = Json {
    classDiscriminator = "type"
    serializersModule = swcSerializersModule
    // TODO: @swc/types seems incomplete
    ignoreUnknownKeys = true
    explicitNulls = true // Include null values in JSON to prevent Rust deserialization errors
}

@OptIn(ExperimentalSerializationApi::class)
val configJson = Json {
    classDiscriminator = "syntax"
    serializersModule = configSerializer
    explicitNulls = true // Include null values in JSON to prevent Rust deserialization errors
}

fun parseAstTree(jsonStr: String): Program {
    return astJson.decodeFromString<Program>(jsonStr)
}
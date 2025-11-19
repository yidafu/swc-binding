package dev.yidafu.swc

import dev.yidafu.swc.generated.Program
import dev.yidafu.swc.generated.TruePlusMinus
import dev.yidafu.swc.generated.swcConfigSerializersModule
import dev.yidafu.swc.generated.swcSerializersModule
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlin.jvm.JvmStatic

/**
 * Extended serializers module for SWC AST nodes.
 * Includes the generated serializers module and handles polymorphic types.
 * * Implementation classes (*Impl) from customType.kt are handled by the generated polymorphic module.
 * * Note: 所有使用 @JsonClassDiscriminator("type") 的类都应该有显式的 type 字段（使用 @EncodeDefault），
 * 以确保即使它们不是多态类型，也能在序列化时输出 type 字段。
 */
@OptIn(ExperimentalSerializationApi::class)
private val extendedSwcSerializersModule: SerializersModule = SerializersModule {
    include(swcSerializersModule)
    // Register custom serializer for TruePlusMinus to handle both boolean and string values
    contextual(TruePlusMinus::class, TruePlusMinusSerializer)
    // Implementation classes (*Impl) are handled by the generated polymorphic module
    // IdentifierImpl, BindingIdentifierImpl, TemplateLiteralImpl, TsTemplateLiteralTypeImpl
    // are registered in swcSerializersModule via polymorphic subclass registration
}

/**
 * Extended serializers module for SWC configuration objects.
 * Includes the generated config serializers module.
 */
@OptIn(ExperimentalSerializationApi::class)
private val extendedSwcConfigSerializersModule: SerializersModule = SerializersModule {
    include(swcConfigSerializersModule)
    // OptionsImpl is handled by the generated polymorphic module
}

/**
 * JSON serializer/deserializer for AST (Abstract Syntax Tree) nodes.
 *
 * This JSON instance is configured specifically for SWC AST serialization:
 * - Uses "type" as the class discriminator for polymorphic types
 * - Ignores unknown keys (for forward compatibility)
 * - Includes default values in serialization (e.g., Span.ctxt = 0)
 * - Coerces missing values to defaults during deserialization
 *
 * @example
 * ```kotlin
 * val ast: Program = // ... some AST
 * val json = astJson.encodeToString(ast)
 * val deserialized = astJson.decodeFromString<Program>(json)
 * ```
 */
@OptIn(ExperimentalSerializationApi::class)
val astJson = Json {
    // 需要 classDiscriminator 配置，与 @JsonClassDiscriminator("type") 配合使用
    // @JsonClassDiscriminator 指定使用 type 属性作为 discriminator
    // classDiscriminator 指定 JSON 中的字段名为 "type"
    //
    // 注意：@JsonClassDiscriminator 只在多态序列化时才会自动添加 discriminator 字段。
    // 对于非多态类型的序列化（如 Array<VariableDeclarator>），这些类已经有显式的 type 字段
    // （使用 @EncodeDefault），配合 encodeDefaults = true 配置，确保 type 字段会被序列化。
    classDiscriminator = "type"
    serializersModule = extendedSwcSerializersModule
    // @swc/types may be incomplete, so ignore unknown keys
    ignoreUnknownKeys = true
    // Don't include null values - fields with @EncodeDefault will use their default values
    explicitNulls = false
    // Include default values (e.g., Boolean? = false, Span.ctxt = 0) in JSON
    // 这个配置确保所有使用 @EncodeDefault 的字段（包括 type 字段）都会被序列化
    encodeDefaults = true
    // Coerce missing values to defaults during deserialization (e.g., missing ctxt -> 0)
    coerceInputValues = true
}

/**
 * JSON serializer/deserializer for SWC configuration objects.
 *
 * This JSON instance is configured for parser and transform options:
 * - Uses "syntax" as the class discriminator
 * - Includes default values (Rust expects missing fields to use defaults)
 *
 * @example
 * ```kotlin
 * val options = esParseOptions { }
 * val json = configJson.encodeToString(options)
 * val deserialized = configJson.decodeFromString<ParserConfig>(json)
 * ```
 */
@OptIn(ExperimentalSerializationApi::class)
val configJson = Json {
    // 需要 classDiscriminator 配置，与 @JsonClassDiscriminator("syntax") 配合使用
    // @JsonClassDiscriminator 指定使用 syntax 属性作为 discriminator
    // classDiscriminator 指定 JSON 中的字段名为 "syntax"
    classDiscriminator = "syntax"
    serializersModule = extendedSwcConfigSerializersModule
    // Don't include null values - Rust expects missing fields to use defaults
    explicitNulls = false
    // Include default values (e.g., Span.ctxt = 0) in JSON
    encodeDefaults = true
}

/**
 * Simple JSON decoder for TransformOutput.
 *
 * TransformOutput is a simple data class that doesn't require complex
 * serialization configuration. The `msg` field is optional.
 *
 * @example
 * ```kotlin
 * val outputJson = """{"code": "const x = 42;", "msg": null}"""
 * val output = outputJson.decodeFromString<TransformOutput>(outputJson)
 * ```
 */
val outputJson = Json {
    ignoreUnknownKeys = true
    // TransformOutput's msg field is optional
    explicitNulls = false
    encodeDefaults = false
}

/**
 * Utility object for JSON parsing operations.
 *
 * This object provides static methods that are Java-friendly.
 */
object SwcJson {
    /**
     * Parse a JSON string into a Program AST node.
     *
     * This method is available as a static method in Java.
     *
     * @param jsonStr JSON string representation of the AST
     * @return Parsed Program AST node
     * @throws SerializationException if the JSON is invalid or doesn't match the AST structure
     *
     * @example Java usage:
     * ```java
     * String json = "{\"type\":\"Module\",\"body\":[],\"span\":{\"start\":0,\"end\":0,\"ctxt\":0}}";
     * Program program = SwcJson.parseAstTree(json);
     * ```
     */
    @JvmStatic
    fun parseAstTree(jsonStr: String): Program {
        return astJson.decodeFromString<Program>(jsonStr)
    }
}

/**
 * Parse a JSON string into a Program AST node.
 *
 * This is a convenience function that uses [astJson] to deserialize
 * a JSON string representation of an AST into a [Program] object.
 *
 * This is a Kotlin extension function. For Java, use [SwcJson.parseAstTree] instead.
 *
 * @param jsonStr JSON string representation of the AST
 * @return Parsed Program AST node
 * @throws SerializationException if the JSON is invalid or doesn't match the AST structure
 *
 * @example
 * ```kotlin
 * val json = """{"type":"Module","body":[],"span":{"start":0,"end":0,"ctxt":0}}"""
 * val program = parseAstTree(json)
 * ```
 */
fun parseAstTree(jsonStr: String): Program {
    return SwcJson.parseAstTree(jsonStr)
}
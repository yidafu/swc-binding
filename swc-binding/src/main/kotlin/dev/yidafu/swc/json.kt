package dev.yidafu.swc

import dev.yidafu.swc.generated.Node
import dev.yidafu.swc.generated.Program
import dev.yidafu.swc.generated.TruePlusMinus
import dev.yidafu.swc.generated.dsl.module
import dev.yidafu.swc.generated.swcConfigSerializersModule
import dev.yidafu.swc.generated.swcSerializersModule
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlin.jvm.JvmStatic

/**
 * Extended serializers module for SWC AST nodes.
 *
 * This module includes the generated serializers module and handles polymorphic types
 * and custom serialization logic. It is used by [astJson] for serializing/deserializing
 * AST nodes.
 *
 * **Key Features:**
 * - Includes all generated serializers from [swcSerializersModule]
 * - Registers custom serializer for [TruePlusMinus] to handle both boolean and string values
 * - Implementation classes (*Impl) from customType.kt are handled by the generated polymorphic module
 *
 * **Important Note**: All classes using `@JsonClassDiscriminator("type")` should have
 * an explicit `type` field with `@EncodeDefault` to ensure the type field is serialized
 * even when they are not polymorphic types.
 *
 * @see astJson for the JSON instance using this module
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
 *
 * This module includes the generated config serializers module and is used by [configJson]
 * for serializing/deserializing parser and transform configuration objects.
 *
 * **Key Features:**
 * - Includes all generated config serializers from [swcConfigSerializersModule]
 * - OptionsImpl is handled by the generated polymorphic module
 *
 * @see configJson for the JSON instance using this module
 */
@OptIn(ExperimentalSerializationApi::class)
private val extendedSwcConfigSerializersModule: SerializersModule = SerializersModule {
    include(swcConfigSerializersModule)
    // OptionsImpl is handled by the generated polymorphic module
}

/**
 * JSON serializer/deserializer for AST (Abstract Syntax Tree) nodes.
 *
 * This JSON instance is configured specifically for SWC AST serialization and deserialization.
 * It is used internally by [SwcNative] methods to convert between AST objects and JSON strings
 * when communicating with the native SWC library.
 *
 * **Configuration Details:**
 * - Uses "type" as the class discriminator for polymorphic types (required for AST node types)
 * - Ignores unknown keys (for forward compatibility with SWC version updates)
 * - Includes default values in serialization (e.g., `Span.ctxt = 0`, `Boolean? = false`)
 * - Coerces missing values to defaults during deserialization (handles incomplete JSON)
 * - Uses [extendedSwcSerializersModule] for custom serialization logic
 *
 * **Usage:**
 * This instance is primarily used internally. For parsing JSON strings manually, use
 * [SwcJson.parseAstTree] instead.
 *
 * @example Internal usage:
 * ```kotlin
 * val ast: Program = // ... some AST
 * val json = astJson.encodeToString(ast) // Convert AST to JSON for native library
 * val deserialized = astJson.decodeFromString<Program>(json) // Convert JSON back to AST
 * ```
 *
 * @see SwcJson.parseAstTree for public API to parse JSON strings
 * @hide
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
 * This JSON instance is configured for parser and transform options serialization.
 * It is used internally by [SwcNative] methods to convert between configuration objects
 * and JSON strings when communicating with the native SWC library.
 *
 * **Configuration Details:**
 * - Uses "syntax" as the class discriminator (distinguishes between ES and TS parser configs)
 * - Includes default values in serialization (Rust expects missing fields to use defaults)
 * - Uses [extendedSwcConfigSerializersModule] for custom serialization logic
 *
 * **Usage:**
 * This instance is primarily used internally. Configuration objects are typically created
 * using DSL functions like [esParseOptions] and [tsParseOptions].
 *
 * @example Internal usage:
 * ```kotlin
 * val options = esParseOptions { }
 * val json = configJson.encodeToString(options) // Convert config to JSON for native library
 * val deserialized = configJson.decodeFromString<ParserConfig>(json) // Convert JSON back to config
 * ```
 *
 * @see esParseOptions for creating ECMAScript parser options
 * @see tsParseOptions for creating TypeScript parser options
 */
@OptIn(ExperimentalSerializationApi::class)
internal val configJson = Json {
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
 * Simple JSON decoder for [TransformOutput].
 *
 * This JSON instance is configured specifically for deserializing [TransformOutput] objects
 * returned from SWC native operations (transform, print, minify). It is used internally
 * by [SwcNative] methods to parse the JSON response from the native library.
 *
 * **Configuration Details:**
 * - Ignores unknown keys (for forward compatibility)
 * - Allows null values (the `msg` field is optional)
 * - Does not encode defaults (TransformOutput is a simple data class)
 *
 * **Usage:**
 * This instance is primarily used internally. The [TransformOutput] class is returned
 * directly from [SwcNative] methods, so manual deserialization is rarely needed.
 *
 * @example Internal usage:
 * ```kotlin
 * val outputJson = """{"code": "const x = 42;", "msg": null}"""
 * val output = outputJson.decodeFromString<TransformOutput>(outputJson)
 * ```
 *
 * @see TransformOutput for the data class structure
 */
internal val outputJson = Json {
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
     * String json = "{\"type\":\"Module\",\"body\":[],\"span\":{\"start\":0,\"end\":0}}";
     * Program program = SwcJson.parseAstTree(json);
     * ```
     */
    @JvmStatic
    fun parseAstTree(jsonStr: String): Program {
        return astJson.decodeFromString<Program>(jsonStr)
    }
    
    /**
     * Fix missing `ctxt` fields in span objects and Node subclasses within JsonElement.
     * 
     * This function addresses the issue where Rust's serde skips serializing default values (ctxt = 0),
     * which causes deserialization failures in polymorphic scenarios where `coerceInputValues` doesn't work.
     * 
     * The function recursively traverses the JsonElement tree and:
     * 1. Finds all span objects that have "start" and "end" fields but are missing the "ctxt" field
     * 2. Finds all Node subclasses (objects with "type" and "span" fields) that are missing the "ctxt" field
     * 
     * @param element The JsonElement to fix
     * @return The fixed JsonElement with all span objects and Node subclasses containing the ctxt field
     */
    internal fun fixMissingCtxtFieldsInJsonElement(element: JsonElement): JsonElement {
        return when (element) {
            is JsonObject -> {
                val fixedEntries = element.entries.map { (key, value) ->
                    if (key == "span" && value is JsonObject) {
                        // This is a span object, check if it needs fixing
                        val spanObj = value
                        val hasStart = spanObj.containsKey("start")
                        val hasEnd = spanObj.containsKey("end")
                        val hasCtxt = spanObj.containsKey("ctxt")
                        
                        if (hasStart && hasEnd && !hasCtxt) {
                            // Add ctxt field with value 0
                            key to buildJsonObject {
                                spanObj.forEach { (k, v) ->
                                    put(k, v)
                                }
                                put("ctxt", kotlinx.serialization.json.JsonPrimitive(0))
                            }
                        } else {
                            // Recursively fix nested elements
                            key to fixMissingCtxtFieldsInJsonElement(value)
                        }
                    } else {
                        // Recursively fix nested elements first
                        key to fixMissingCtxtFieldsInJsonElement(value)
                    }
                }
                
                // After fixing nested elements, check if this object itself is a Node subclass
                // that needs ctxt field (has "type" and "span" but no "ctxt")
                val hasType = element.containsKey("type")
                val hasSpan = element.containsKey("span")
                val hasCtxt = element.containsKey("ctxt")
                
                if (hasType && hasSpan && !hasCtxt) {
                    // This is a Node subclass missing ctxt, add it
                    buildJsonObject {
                        fixedEntries.forEach { (k, v) ->
                            put(k, v)
                        }
                        put("ctxt", kotlinx.serialization.json.JsonPrimitive(0))
                    }
                } else {
                    JsonObject(fixedEntries.toMap())
                }
            }
            is JsonArray -> {
                JsonArray(
                    element.map { fixMissingCtxtFieldsInJsonElement(it) }
                )
            }
            else -> element
        }
    }
    
     inline fun <reified T : Node> astTreeToString(program: T) : String {
        return astJson.encodeToString(program)
    }
}

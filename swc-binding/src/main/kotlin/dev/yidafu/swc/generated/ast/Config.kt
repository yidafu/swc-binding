// Auto-generated file. Do not edit. Generated at: 2025-11-18T20:22:48.526409

package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.String
@JsonClassDiscriminator("syntax")
public interface Config {
    @Serializable(with = U2_String_ArrayString__Serializer::class)
    public var test: Union.U2<String, Array<String>>?
    @Serializable(with = U2_String_ArrayString__Serializer::class)
    public var exclude: Union.U2<String, Array<String>>?

    public var env: EnvConfig?

    public var jsc: JscConfig?

    public var module: ModuleConfig?

    public var minify: Boolean?
    @Serializable(with = U2_Boolean_String__Serializer::class)
    public var sourceMaps: Union.U2<Boolean, String>?

    public var inlineSourcesContent: Boolean?
}

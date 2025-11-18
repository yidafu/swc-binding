// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.032968

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

public interface WasmAnalysisOptions {
    public var parser: ParserConfig?
    @Serializable(with = U2_Boolean_String__Serializer::class)
    public var module: Union.U2<Boolean, String>?

    public var filename: String?

    public var errorFormat: String?

    public var cacheRoot: String?

    public var plugins: Array<WasmPlugin>?
}

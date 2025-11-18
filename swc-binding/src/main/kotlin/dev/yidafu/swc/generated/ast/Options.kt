// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.046539

package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.String
@Serializable
public class Options : Config {
    @EncodeDefault
    public var script: Boolean? = null
    @EncodeDefault
    public var cwd: String? = null
    @EncodeDefault
    public var caller: CallerOptions? = null
    @EncodeDefault
    public var filename: String? = null
    @EncodeDefault
    public var root: String? = null
    @EncodeDefault
    public var rootMode: String? = null
    @EncodeDefault
    public var envName: String? = null
    @EncodeDefault
    @Serializable(with = U2_String_Boolean__Serializer::class)
    public var configFile: Union.U2<String, Boolean>? = null
    @EncodeDefault
    public var swcrc: Boolean? = null
    @EncodeDefault
    @Serializable(with = U3_Boolean_MatchPattern_ArrayMatchPattern__Serializer::class)
    public var swcrcRoots: Union.U3<Boolean, MatchPattern, Array<MatchPattern>>? = null
    @EncodeDefault
    @Serializable(with = U2_Boolean_String__Serializer::class)
    public var inputSourceMap: Union.U2<Boolean, String>? = null
    @EncodeDefault
    public var sourceFileName: String? = null
    @EncodeDefault
    public var sourceRoot: String? = null
    @EncodeDefault
    public var plugin: Plugin? = null
    @EncodeDefault
    @Serializable(with = U2_Boolean_String__Serializer::class)
    public var isModule: Union.U2<Boolean, String>? = null
    @EncodeDefault
    public var outputPath: String? = null

    public override var test: Union.U2<String, Array<String>>? = null

    public override var exclude: Union.U2<String, Array<String>>? = null

    public override var env: EnvConfig? = null

    public override var jsc: JscConfig? = null

    public override var module: ModuleConfig? = null

    public override var minify: Boolean? = null

    public override var sourceMaps: Union.U2<Boolean, String>? = null

    public override var inlineSourcesContent: Boolean? = null
}

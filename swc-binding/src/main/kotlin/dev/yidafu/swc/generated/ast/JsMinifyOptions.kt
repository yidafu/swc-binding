// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.038859

package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.String
@Serializable
public class JsMinifyOptions {
    @EncodeDefault
    @Serializable(with = U2_TerserCompressOptions_Boolean__Serializer::class)
    public var compress: Union.U2<TerserCompressOptions, Boolean>? = null
    @EncodeDefault
    public var format: JsFormatOptions? = null
    @EncodeDefault
    @Serializable(with = U2_TerserMangleOptions_Boolean__Serializer::class)
    public var mangle: Union.U2<TerserMangleOptions, Boolean>? = null
    @EncodeDefault
    public var ecma: TerserEcmaVersion? = null
    @SerialName("keep_classnames")
    @EncodeDefault
    public var keepClassnames: Boolean? = null
    @SerialName("keep_fnames")
    @EncodeDefault
    public var keepFnames: Boolean? = null
    @EncodeDefault
    @Serializable(with = U2_Boolean_String__Serializer::class)
    public var module: Union.U2<Boolean, String>? = null
    @EncodeDefault
    public var safari10: Boolean? = null
    @EncodeDefault
    public var toplevel: String? = null
    @EncodeDefault
    public var sourceMap: Boolean? = null
    @EncodeDefault
    public var outputPath: String? = null
    @EncodeDefault
    public var inlineSourcesContent: Boolean? = null
}

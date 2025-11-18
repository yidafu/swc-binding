// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.039766

package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Nothing
import kotlin.String
@Serializable
public class JsFormatOptions {
    @EncodeDefault
    public var asciiOnly: Boolean? = null
    @EncodeDefault
    public var beautify: Boolean? = null
    @EncodeDefault
    public var braces: Boolean? = null
    @EncodeDefault
    @Serializable(with = U3_Boolean_String_JsFormatOptionsComments__Serializer::class)
    public var comments: Union.U3<Boolean, String, JsFormatOptionsComments>? = null
    @EncodeDefault
    public var ecma: TerserEcmaVersion? = null
    @EncodeDefault
    public var indentLevel: Double? = null
    @EncodeDefault
    public var indentStart: Double? = null
    @EncodeDefault
    public var inlineScript: Boolean? = null
    @EncodeDefault
    public var keepNumbers: Double? = null
    @EncodeDefault
    public var keepQuotedProps: Boolean? = null
    @EncodeDefault
    @Serializable(with = U2_Double_Boolean__Serializer::class)
    public var maxLineLen: Union.U2<Double, Boolean>? = null
    @EncodeDefault
    public var preamble: String? = null
    @EncodeDefault
    public var quoteKeys: Boolean? = null
    @EncodeDefault
    public var quoteStyle: Boolean? = null
    @EncodeDefault
    public var preserveAnnotations: Boolean? = null
    @EncodeDefault
    public var safari10: Boolean? = null
    @EncodeDefault
    public var semicolons: Boolean? = null
    @EncodeDefault
    public var shebang: Boolean? = null
    @EncodeDefault
    public var webkit: Boolean? = null
    @EncodeDefault
    public var wrapIife: Boolean? = null
    @EncodeDefault
    public var wrapFuncArgs: Boolean? = null

    public interface JsFormatOptionsComments {
        public var regex: String?
    }
}

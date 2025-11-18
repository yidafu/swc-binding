// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.039292

package dev.yidafu.swc.generated

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
import kotlin.collections.Map
@Serializable
public class JscConfig {
    @EncodeDefault
    public var assumptions: Assumptions? = null
    @EncodeDefault
    public var loose: Boolean? = null
    @EncodeDefault
    public var parser: ParserConfig? = null
    @EncodeDefault
    public var transform: TransformConfig? = null
    @EncodeDefault
    public var externalHelpers: Boolean? = null
    @EncodeDefault
    public var target: JscTarget? = null
    @EncodeDefault
    public var keepClassNames: Boolean? = null
    @EncodeDefault
    public var experimental: JscConfigExperimental? = null
    @EncodeDefault
    public var baseUrl: String? = null
    @EncodeDefault
    public var paths: Map<String, Array<String>>? = null
    @EncodeDefault
    public var minify: JsMinifyOptions? = null
    @EncodeDefault
    public var preserveAllComments: Boolean? = null
    @EncodeDefault
    public var output: JscConfigOutput? = null

    public interface JscConfigExperimental {
        public var optimizeHygiene: Boolean?

        public var keepImportAttributes: Boolean?

        public var emitAssertForImportAttributes: Boolean?

        public var cacheRoot: String?

        public var plugins: Array<WasmPlugin>?

        public var runPluginFirst: Boolean?

        public var disableBuiltinTransformsForInternalTesting: Boolean?

        public var emitIsolatedDts: Boolean?

        public var disableAllLints: Boolean?

        public var keepImportAssertions: Boolean?
    }

    public interface JscConfigOutput {
        public var charset: String?
    }
}

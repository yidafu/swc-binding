// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.187912

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn

/**
 * conflict with @SerialName
 * remove class property `override var syntax : String? = "ecmascript"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("syntax")
@SerialName("ecmascript")
public class EsParserConfig : ParserConfig {
    @EncodeDefault
    public var jsx: Boolean? = null
    @EncodeDefault
    public var numericSeparator: Boolean? = null
    @EncodeDefault
    public var classPrivateProperty: Boolean? = null
    @EncodeDefault
    public var privateMethod: Boolean? = null
    @EncodeDefault
    public var classProperty: Boolean? = null
    @EncodeDefault
    public var functionBind: Boolean? = null
    @EncodeDefault
    public var decorators: Boolean? = null
    @EncodeDefault
    public var decoratorsBeforeExport: Boolean? = null
    @EncodeDefault
    public var exportDefaultFrom: Boolean? = null
    @EncodeDefault
    public var exportNamespaceFrom: Boolean? = null
    @EncodeDefault
    public var dynamicImport: Boolean? = null
    @EncodeDefault
    public var nullishCoalescing: Boolean? = null
    @EncodeDefault
    public var optionalChaining: Boolean? = null
    @EncodeDefault
    public var importMeta: Boolean? = null
    @EncodeDefault
    public var topLevelAwait: Boolean? = null
    @EncodeDefault
    public var importAssertions: Boolean? = null
    @EncodeDefault
    public var importAttributes: Boolean? = null
    @EncodeDefault
    public var allowSuperOutsideMethod: Boolean? = null
    @EncodeDefault
    public var allowReturnOutsideFunction: Boolean? = null
    @EncodeDefault
    public var autoAccessors: Boolean? = null
    @EncodeDefault
    public var explicitResourceManagement: Boolean? = null

    public override var comments: Boolean? = null

    public override var script: Boolean? = null

    public override var target: JscTarget? = null
}

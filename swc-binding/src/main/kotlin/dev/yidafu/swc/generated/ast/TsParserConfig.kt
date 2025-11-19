// Auto-generated file. Do not edit. Generated at: 2025-11-19T20:51:47.056499

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
 * remove class property `override var syntax : String? = "typescript"`
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("syntax")
@SerialName("typescript")
public class TsParserConfig : ParserConfig {
    @EncodeDefault
    public var tsx: Boolean? = null
    @EncodeDefault
    public var decorators: Boolean? = null
    @EncodeDefault
    public var dynamicImport: Boolean? = null

    public override var comments: Boolean? = null

    public override var script: Boolean? = null

    public override var target: JscTarget? = null
}

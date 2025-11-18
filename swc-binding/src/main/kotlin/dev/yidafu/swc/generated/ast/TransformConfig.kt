// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.037008

package dev.yidafu.swc.generated

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
public class TransformConfig {
    @EncodeDefault
    public var react: ReactConfig? = null
    @EncodeDefault
    public var constModules: ConstModulesConfig? = null
    @EncodeDefault
    public var optimizer: OptimizerConfig? = null
    @EncodeDefault
    public var legacyDecorator: Boolean? = null
    @EncodeDefault
    public var decoratorMetadata: Boolean? = null
    @EncodeDefault
    public var decoratorVersion: String? = null
    @EncodeDefault
    public var treatConstEnumAsEnum: Boolean? = null
    @EncodeDefault
    public var useDefineForClassFields: Boolean? = null
    @EncodeDefault
    public var verbatimModuleSyntax: Boolean? = null
    @EncodeDefault
    public var tsEnumIsMutable: Boolean? = null
}

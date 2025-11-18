// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.036152

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
@Serializable
public class EnvConfig {
    @EncodeDefault
    public var mode: String? = null
    @EncodeDefault
    public var debug: Boolean? = null
    @EncodeDefault
    public var dynamicImport: Boolean? = null
    @EncodeDefault
    public var loose: Boolean? = null
    @EncodeDefault
    public var bugfixes: Boolean? = null
    @EncodeDefault
    public var skip: Array<String>? = null
    @EncodeDefault
    public var include: Array<String>? = null
    @EncodeDefault
    public var exclude: Array<String>? = null
    @EncodeDefault
    public var coreJs: String? = null
    @EncodeDefault
    public var targets: String? = null
    @EncodeDefault
    public var path: String? = null
    @EncodeDefault
    public var shippedProposals: Boolean? = null
    @EncodeDefault
    public var forceAllTransforms: Boolean? = null
}

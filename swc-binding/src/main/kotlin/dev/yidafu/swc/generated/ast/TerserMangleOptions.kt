// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.033936

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
public class TerserMangleOptions {
    @EncodeDefault
    public var props: TerserManglePropertiesOptions? = null
    @EncodeDefault
    public var topLevel: Boolean? = null
    @EncodeDefault
    public var keepClassNames: Boolean? = null
    @EncodeDefault
    public var keepFnNames: Boolean? = null
    @SerialName("keep_fnames")
    @EncodeDefault
    public var keepFnames: Boolean? = null
    @EncodeDefault
    public var keepPrivateProps: Boolean? = null
    @EncodeDefault
    public var ie8: Boolean? = null
    @EncodeDefault
    public var safari10: Boolean? = null
    @EncodeDefault
    public var reserved: Array<String>? = null
}

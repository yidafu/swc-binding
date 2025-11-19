// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.191034

package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Int
import kotlin.Nothing
import kotlin.String
@Serializable
public class GlobalPassOption {
    @EncodeDefault
    public var vars: Record<String, String>? = null
    @EncodeDefault
    @Serializable(with = U2_ArrayString_RecordStringString__Serializer::class)
    public var envs: Union.U2<Array<String>, Record<String, String>>? = null
    @EncodeDefault
    public var typeofs: Record<String, String>? = null
}

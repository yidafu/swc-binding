// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.038795

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
public class ReactConfig {
    @EncodeDefault
    public var pragma: String? = null
    @EncodeDefault
    public var pragmaFrag: String? = null
    @EncodeDefault
    public var throwIfNamespace: Boolean? = null
    @EncodeDefault
    public var development: Boolean? = null
    @EncodeDefault
    public var useBuiltins: Boolean? = null
    @EncodeDefault
    @Serializable(with = U2_Boolean_ReactConfigRefresh__Serializer::class)
    public var refresh: Union.U2<Boolean, ReactConfigRefresh>? = null
    @EncodeDefault
    public var runtime: String? = null
    @EncodeDefault
    public var importSource: String? = null

    public interface ReactConfigRefresh {
        public var refreshReg: String?

        public var refreshSig: String?

        public var emitFullSignatures: Boolean?
    }
}

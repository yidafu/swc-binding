// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.037199

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Nothing
@Serializable
public class OptimizerConfig {
    @EncodeDefault
    public var simplify: Boolean? = null
    @EncodeDefault
    public var globals: GlobalPassOption? = null
    @EncodeDefault
    public var jsonify: OptimizerConfigJsonify? = null

    public interface OptimizerConfigJsonify {
        public var minCost: Double?
    }
}

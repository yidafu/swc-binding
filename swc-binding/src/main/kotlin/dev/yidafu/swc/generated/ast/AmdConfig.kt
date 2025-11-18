// Auto-generated file. Do not edit. Generated at: 2025-11-19T00:57:02.731218

package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.OptIn
import kotlin.String
@Serializable
@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@SerialName("amd")
public class AmdConfig : BaseModuleConfig, ModuleConfig {
    @EncodeDefault
    public var moduleId: String? = null

    public override var strict: Boolean? = null

    public override var strictMode: Boolean? = null

    public override var lazy: Union.U2<Boolean, Array<String>>? = null

    public override var noInterop: Boolean? = null

    public override var importInterop: String? = null

    public override var outFileExtension: String? = null

    public override var exportInteropAnnotation: Boolean? = null

    public override var ignoreDynamic: Boolean? = null

    public override var allowTopLevelThis: Boolean? = null

    public override var preserveImportMeta: Boolean? = null

    public override var resolveFully: Boolean? = null
}

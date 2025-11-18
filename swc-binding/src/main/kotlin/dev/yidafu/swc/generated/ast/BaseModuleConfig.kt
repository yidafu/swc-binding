// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.027674

package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.Nothing
import kotlin.String

public interface BaseModuleConfig {
    public var strict: Boolean?

    public var strictMode: Boolean?
    @Serializable(with = U2_Boolean_ArrayString__Serializer::class)
    public var lazy: Union.U2<Boolean, Array<String>>?

    public var noInterop: Boolean?

    public var importInterop: String?

    public var outFileExtension: String?

    public var exportInteropAnnotation: Boolean?

    public var ignoreDynamic: Boolean?

    public var allowTopLevelThis: Boolean?

    public var preserveImportMeta: Boolean?

    public var resolveFully: Boolean?
}

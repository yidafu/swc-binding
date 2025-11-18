// Auto-generated file. Do not edit. Generated at: 2025-11-18T19:24:47.053076

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Array
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Nothing
import kotlin.String
@Serializable
public class TerserCompressOptions {
    @EncodeDefault
    public var arguments: Boolean? = null
    @EncodeDefault
    public var arrows: Boolean? = null
    @EncodeDefault
    public var booleans: Boolean? = null
    @SerialName("booleans_as_integers")
    @EncodeDefault
    public var booleansAsIntegers: Boolean? = null
    @SerialName("collapse_vars")
    @EncodeDefault
    public var collapseVars: Boolean? = null
    @EncodeDefault
    public var comparisons: Boolean? = null
    @SerialName("computed_props")
    @EncodeDefault
    public var computedProps: Boolean? = null
    @EncodeDefault
    public var conditionals: Boolean? = null
    @SerialName("dead_code")
    @EncodeDefault
    public var deadCode: Boolean? = null
    @EncodeDefault
    public var defaults: Boolean? = null
    @EncodeDefault
    public var directives: Boolean? = null
    @SerialName("drop_console")
    @EncodeDefault
    public var dropConsole: Boolean? = null
    @SerialName("drop_debugger")
    @EncodeDefault
    public var dropDebugger: Boolean? = null
    @EncodeDefault
    public var ecma: TerserEcmaVersion? = null
    @EncodeDefault
    public var evaluate: Boolean? = null
    @EncodeDefault
    public var expression: Boolean? = null
    @SerialName("global_defs")
    @EncodeDefault
    public var globalDefs: String? = null
    @SerialName("hoist_funs")
    @EncodeDefault
    public var hoistFuns: Boolean? = null
    @SerialName("hoist_props")
    @EncodeDefault
    public var hoistProps: Boolean? = null
    @SerialName("hoist_vars")
    @EncodeDefault
    public var hoistVars: Boolean? = null
    @EncodeDefault
    public var ie8: Boolean? = null
    @SerialName("if_return")
    @EncodeDefault
    public var ifReturn: Boolean? = null
    @EncodeDefault
    public var `inline`: Int? = null
    @SerialName("join_vars")
    @EncodeDefault
    public var joinVars: Boolean? = null
    @SerialName("keep_classnames")
    @EncodeDefault
    public var keepClassnames: Boolean? = null
    @SerialName("keep_fargs")
    @EncodeDefault
    public var keepFargs: Boolean? = null
    @SerialName("keep_fnames")
    @EncodeDefault
    public var keepFnames: Boolean? = null
    @SerialName("keep_infinity")
    @EncodeDefault
    public var keepInfinity: Boolean? = null
    @EncodeDefault
    public var loops: Boolean? = null
    @SerialName("negate_iife")
    @EncodeDefault
    public var negateIife: Boolean? = null
    @EncodeDefault
    public var passes: Double? = null
    @EncodeDefault
    public var properties: Boolean? = null
    @SerialName("pure_getters")
    @EncodeDefault
    public var pureGetters: String? = null
    @SerialName("pure_funcs")
    @EncodeDefault
    public var pureFuncs: Array<String>? = null
    @SerialName("reduce_funcs")
    @EncodeDefault
    public var reduceFuncs: Boolean? = null
    @SerialName("reduce_vars")
    @EncodeDefault
    public var reduceVars: Boolean? = null
    @EncodeDefault
    public var sequences: String? = null
    @SerialName("side_effects")
    @EncodeDefault
    public var sideEffects: Boolean? = null
    @EncodeDefault
    public var switches: Boolean? = null
    @SerialName("top_retain")
    @EncodeDefault
    public var topRetain: String? = null
    @EncodeDefault
    public var toplevel: String? = null
    @EncodeDefault
    public var typeofs: Boolean? = null
    @EncodeDefault
    public var unsafe: Boolean? = null
    @SerialName("unsafe_passes")
    @EncodeDefault
    public var unsafePasses: Boolean? = null
    @SerialName("unsafe_arrows")
    @EncodeDefault
    public var unsafeArrows: Boolean? = null
    @SerialName("unsafe_comps")
    @EncodeDefault
    public var unsafeComps: Boolean? = null
    @SerialName("unsafe_function")
    @EncodeDefault
    public var unsafeFunction: Boolean? = null
    @SerialName("unsafe_math")
    @EncodeDefault
    public var unsafeMath: Boolean? = null
    @SerialName("unsafe_symbols")
    @EncodeDefault
    public var unsafeSymbols: Boolean? = null
    @SerialName("unsafe_methods")
    @EncodeDefault
    public var unsafeMethods: Boolean? = null
    @SerialName("unsafe_proto")
    @EncodeDefault
    public var unsafeProto: Boolean? = null
    @SerialName("unsafe_regexp")
    @EncodeDefault
    public var unsafeRegexp: Boolean? = null
    @SerialName("unsafe_undefined")
    @EncodeDefault
    public var unsafeUndefined: Boolean? = null
    @EncodeDefault
    public var unused: Boolean? = null
    @SerialName("const_to_let")
    @EncodeDefault
    public var constToLet: Boolean? = null
    @EncodeDefault
    public var module: Boolean? = null
}

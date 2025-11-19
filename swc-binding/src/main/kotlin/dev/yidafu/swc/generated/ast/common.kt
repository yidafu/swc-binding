// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.186402

package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.emptySpan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.Any
import kotlin.Array
import kotlin.Boolean
import kotlin.DslMarker
import kotlin.Int
import kotlin.Nothing
import kotlin.String
import kotlin.collections.Map
@DslMarker
public annotation class SwcDslMarker

public typealias Record<T, S> = Map<T, String>

public typealias ParseOptions = ParserConfig

public typealias TerserEcmaVersion = String

public typealias Swcrc = Union.U2<Config, Array<Config>>

public typealias WasmPlugin = Union.U2<String, Record<String, Any>>
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("syntax")
public sealed interface BaseParseOptions {
    public var comments: Boolean?

    public var script: Boolean?

    public var target: JscTarget?
}
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("syntax")
public sealed interface ParserConfig : BaseParseOptions
@SwcDslMarker
@Serializable
public sealed interface ModuleConfig
@SwcDslMarker
@Serializable
public sealed interface ClassMember
@SwcDslMarker
@Serializable
public sealed interface Declaration : Statement
@SwcDslMarker
@Serializable
public sealed interface Expression : JSXExpression, Pattern
@SwcDslMarker
@Serializable
public sealed interface JSXObject
@SwcDslMarker
@Serializable
public sealed interface JSXExpression
@SwcDslMarker
@Serializable
public sealed interface JSXElementName
@SwcDslMarker
@Serializable
public sealed interface JSXAttributeOrSpread
@SwcDslMarker
@Serializable
public sealed interface JSXAttributeName
@SwcDslMarker
@Serializable
public sealed interface JSXAttrValue
@SwcDslMarker
@Serializable
public sealed interface JSXElementChild
@SwcDslMarker
@Serializable
public sealed interface Literal : Expression, JSXAttrValue
@SwcDslMarker
@Serializable
public sealed interface ModuleDeclaration : ModuleItem
@SwcDslMarker
@Serializable
public sealed interface DefaultDecl
@SwcDslMarker
@Serializable
public sealed interface ImportSpecifier
@SwcDslMarker
@Serializable
public sealed interface ModuleExportName
@SwcDslMarker
@Serializable
public sealed interface ExportSpecifier
@SwcDslMarker
@Serializable
public sealed interface Program
@SwcDslMarker
@Serializable
public sealed interface ModuleItem
@SwcDslMarker
@Serializable
public sealed interface Pattern
@SwcDslMarker
@Serializable
public sealed interface ObjectPatternProperty
@SwcDslMarker
@Serializable
public sealed interface Property
@SwcDslMarker
@Serializable
public sealed interface PropertyName
@SwcDslMarker
@Serializable
public sealed interface Statement : ModuleItem
@SwcDslMarker
@Serializable
public sealed interface TsParameterPropertyParameter
@SwcDslMarker
@Serializable
public sealed interface TsEntityName : TsTypeQueryExpr, TsModuleReference
@SwcDslMarker
@Serializable
public sealed interface TsTypeElement
@SwcDslMarker
@Serializable
public sealed interface TsType
@SwcDslMarker
@Serializable
public sealed interface TsFnOrConstructorType : TsType
@SwcDslMarker
@Serializable
public sealed interface TsFnParameter
@SwcDslMarker
@Serializable
public sealed interface TsThisTypeOrIdent
@SwcDslMarker
@Serializable
public sealed interface TsTypeQueryExpr
@SwcDslMarker
@Serializable
public sealed interface TsUnionOrIntersectionType : TsType
@SwcDslMarker
@Serializable
public sealed interface TsLiteral
@SwcDslMarker
@Serializable
public sealed interface TsEnumMemberId
@SwcDslMarker
@Serializable
public sealed interface TsNamespaceBody
@SwcDslMarker
@Serializable
public sealed interface TsModuleName
@SwcDslMarker
@Serializable
public sealed interface TsModuleReference

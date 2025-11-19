// Custom types that are manually defined to avoid serialization conflicts
// These types are skipped by the code generator

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Identifier AST node interface.
 *
 * An identifier represents a variable name, function name, property name, etc.
 * in JavaScript/TypeScript code. This interface is used in many contexts throughout
 * the AST, including expressions, JSX elements, module exports, and TypeScript types.
 *
 * **Note**: The `type` field is omitted here due to conflicts with `@SerialName`.
 * The actual type is determined by the serialization discriminator.
 *
 * @property value The identifier name (e.g., "x", "myFunction", "MyClass")
 * @property optional Whether this identifier is optional (used in TypeScript)
 * @property span Source code location information
 * @property ctxt Syntax context (used for macro expansion tracking)
 *
 * @example
 * ```kotlin
 * val id = identifier {
 *     value = "myVariable"
 *     optional = false
 *     span = emptySpan()
 *     ctxt = 0
 * }
 * ```
 */
@SwcDslMarker
interface Identifier :
    ExpressionBase,
    Expression,
    JSXObject,
    JSXElementName,
    JSXAttributeName,
    ModuleExportName,
    Property,
    PropertyName,
    TsEntityName,
    TsThisTypeOrIdent,
    TsEnumMemberId,
    TsModuleName {
    // conflict with @SerialName
    //  var type: String?
    var `value`: String?
    var optional: Boolean?
    override var span: Span
    var ctxt: Int
}

/**
 * Binding identifier AST node interface.
 *
 * A binding identifier is used in patterns (e.g., variable declarations, function parameters)
 * where a value is being bound to a name. This is similar to [Identifier] but used specifically
 * in binding contexts like destructuring patterns and function parameters.
 *
 * **Note**: The `type` field is omitted here due to conflicts with `@SerialName`.
 * The actual type is determined by the serialization discriminator.
 *
 * @property value The identifier name (e.g., "x", "param", "destructuredVar")
 * @property optional Whether this identifier is optional (used in TypeScript)
 * @property typeAnnotation Optional TypeScript type annotation
 * @property span Source code location information
 *
 * @example
 * ```kotlin
 * val bindingId = bindingIdentifier {
 *     value = "myParam"
 *     optional = false
 *     typeAnnotation = null
 *     span = emptySpan()
 * }
 * ```
 */
@SwcDslMarker
interface BindingIdentifier : PatternBase, Pattern, TsParameterPropertyParameter, TsFnParameter {
    // conflict with @SerialName
    //  var type: String?
    var `value`: String?
    var optional: Boolean?
    override var typeAnnotation: TsTypeAnnotation?
    override var span: Span
}

/**
 * Implementation of [Identifier] and [BindingIdentifier] interfaces.
 *
 * This class provides the concrete implementation for identifier AST nodes.
 * It implements both [Identifier] and [BindingIdentifier] because they share
 * the same structure in the SWC AST.
 *
 * **Note**: This is a generated/manually maintained class that handles serialization
 * conflicts. The `type` field is handled by the `@JsonClassDiscriminator` annotation.
 *
 * @property value The identifier name (default: null)
 * @property optional Whether this identifier is optional (default: null)
 * @property span Source code location information (default: empty span)
 * @property ctxt Syntax context (default: 0)
 * @property typeAnnotation Optional TypeScript type annotation (default: null)
 *
 * @see Identifier for the interface definition
 * @see BindingIdentifier for the binding variant
 */
@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("Identifier")
class IdentifierImpl() : Identifier, BindingIdentifier {
    // conflict with @SerialName
    //  override var type : String? = "Identifier"
    @EncodeDefault
    override var `value`: String? = null

    @EncodeDefault
    override var optional: Boolean? = null
    override var span: Span = emptySpan()

    @EncodeDefault
    override var ctxt: Int = 0
    override var typeAnnotation: TsTypeAnnotation? = null
}

/**
 * Type alias for [IdentifierImpl] when used as a binding identifier.
 *
 * This allows the same implementation class to be used for both identifier
 * and binding identifier contexts, as they share the same structure.
 */
typealias BindingIdentifierImpl = IdentifierImpl

/**
 * Template literal AST node interface.
 *
 * A template literal represents a JavaScript template string (e.g., `` `Hello ${name}` ``).
 * It consists of an array of string parts (quasis) and an array of expressions
 * that are interpolated into the template.
 *
 * **Note**: The `type` field is omitted here due to conflicts with `@SerialName`.
 * The actual type is determined by the serialization discriminator.
 *
 * @property expressions Array of expressions interpolated into the template
 * @property quasis Array of template string parts (the static text segments)
 * @property span Source code location information
 *
 * @example
 * ```kotlin
 * val template = templateLiteral {
 *     expressions = arrayOf(identifier { value = "name"; span = emptySpan() })
 *     quasis = arrayOf(
 *         templateElement { raw = "Hello "; cooked = "Hello "; span = emptySpan() },
 *         templateElement { raw = ""; cooked = ""; span = emptySpan() }
 *     )
 *     span = emptySpan()
 * }
 * ```
 */
@SwcDslMarker
interface TemplateLiteral : ExpressionBase, Expression {
    // conflict with @SerialName
    //  var type: String?
    var expressions: Array<Expression>?
    var quasis: Array<TemplateElement>?
    override var span: Span
}

/**
 * TypeScript template literal type AST node interface.
 *
 * A TypeScript template literal type represents a template literal type
 * (e.g., `` `Hello ${string}` ``) used in TypeScript's type system.
 * It consists of an array of TypeScript types and an array of template string parts.
 *
 * **Note**: The `type` field is omitted here due to conflicts with `@SerialName`.
 * The actual type is determined by the serialization discriminator.
 *
 * @property types Array of TypeScript types interpolated into the template
 * @property quasis Array of template string parts (the static text segments)
 * @property span Source code location information
 */
@SwcDslMarker
interface TsTemplateLiteralType : Node, HasSpan, TsLiteral {
    // conflict with @SerialName
    //  var type: String?
    var types: Array<TsType>?
    var quasis: Array<TemplateElement>?
    override var span: Span
}

/**
 * Implementation of [TemplateLiteral] and [TsTemplateLiteralType] interfaces.
 *
 * This class provides the concrete implementation for template literal AST nodes.
 * It implements both [TemplateLiteral] (for JavaScript expressions) and
 * [TsTemplateLiteralType] (for TypeScript types) because they share the same structure.
 *
 * **Note**: This is a generated/manually maintained class that handles serialization
 * conflicts. The `type` field is handled by the `@JsonClassDiscriminator` annotation.
 *
 * @property types Array of TypeScript types (default: null, used for TypeScript template literal types)
 * @property expressions Array of expressions (default: null, used for JavaScript template literals)
 * @property quasis Array of template string parts (default: null)
 * @property span Source code location information (default: empty span)
 *
 * @see TemplateLiteral for the JavaScript expression interface
 * @see TsTemplateLiteralType for the TypeScript type interface
 */
@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("TemplateLiteral")
class TemplateLiteralImpl : TemplateLiteral, TsTemplateLiteralType {
    // conflict with @SerialName
    //  override var type : String? = "TemplateLiteral"
    @EncodeDefault
    override var types: Array<TsType>? = null

    @EncodeDefault
    override var expressions: Array<Expression>? = null

    @EncodeDefault
    override var quasis: Array<TemplateElement>? = null
    override var span: Span = emptySpan()
}

/**
 * Type alias for [TemplateLiteralImpl] when used as a TypeScript template literal type.
 *
 * This allows the same implementation class to be used for both JavaScript template literals
 * and TypeScript template literal types, as they share the same structure.
 */
typealias TsTemplateLiteralTypeImpl = TemplateLiteralImpl

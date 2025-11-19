// Custom types that are manually defined to avoid serialization conflicts
// These types are skipped by the code generator

package dev.yidafu.swc.generated

import dev.yidafu.swc.emptySpan
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

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

@SwcDslMarker
interface BindingIdentifier : PatternBase, Pattern, TsParameterPropertyParameter, TsFnParameter {
    // conflict with @SerialName
    //  var type: String?
    var `value`: String?
    var optional: Boolean?
    override var typeAnnotation: TsTypeAnnotation?
    override var span: Span
}

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

typealias BindingIdentifierImpl = IdentifierImpl

@SwcDslMarker
interface TemplateLiteral : ExpressionBase, Expression {
    // conflict with @SerialName
    //  var type: String?
    var expressions: Array<Expression>?
    var quasis: Array<TemplateElement>?
    override var span: Span
}

@SwcDslMarker
interface TsTemplateLiteralType : Node, HasSpan, TsLiteral {
    // conflict with @SerialName
    //  var type: String?
    var types: Array<TsType>?
    var quasis: Array<TemplateElement>?
    override var span: Span
}

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

typealias TsTemplateLiteralTypeImpl = TemplateLiteralImpl

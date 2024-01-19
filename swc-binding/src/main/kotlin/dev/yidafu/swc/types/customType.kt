package dev.yidafu.swc.types

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@SwcDslMarker
interface TemplateLiteral : ExpressionBase, Expression {
    // conflict with @SerialName
    //  var type: String?
    var expressions: Array<Expression>?
    var quasis: Array<TemplateElement>?
    override var span: Span?
}

@SwcDslMarker
interface TsTemplateLiteralType : Node, HasSpan, TsLiteral {
    // conflict with @SerialName
    //  var type: String?
    var types: Array<TsType>?
    var quasis: Array<TemplateElement>?
    override var span: Span?
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("TemplateLiteral")
class TemplateLiteralImpl : TemplateLiteral, TsTemplateLiteralType {
    // conflict with @SerialName
    //  override var type : String? = "TemplateLiteral"
    override var types: Array<TsType>? = null
    override var expressions: Array<Expression>? = null
    override var quasis: Array<TemplateElement>? = null
    override var span: Span? = null
}

typealias TsTemplateLiteralTypeImpl = TemplateLiteralImpl
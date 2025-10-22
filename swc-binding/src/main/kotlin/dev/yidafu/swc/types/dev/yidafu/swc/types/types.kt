package dev.yidafu.swc.types

import Argument
import ArrayExpressionImpl
import ArrayPatternImpl
import ArrowFunctionExpressionImpl
import AssignmentExpressionImpl
import AssignmentPatternImpl
import AssignmentPatternPropertyImpl
import AssignmentPropertyImpl
import AwaitExpressionImpl
import BaseParseOptions
import BigIntLiteralImpl
import BinaryExpressionImpl
import BindingIdentifierImpl
import BlockStatement
import BlockStatementImpl
import BooleanLiteralImpl
import BooleanableArrayString
import BooleanableString
import BreakStatementImpl
import CallExpressionImpl
import CatchClause
import CatchClauseImpl
import Class
import ClassDeclarationImpl
import ClassExpressionImpl
import ClassImpl
import ClassMember
import ClassMethodBase
import ClassMethodImpl
import ClassPropertyBase
import ClassPropertyImpl
import ComputedPropNameImpl
import ConditionalExpressionImpl
import ConfigImpl
import ConstructorImpl
import ContinueStatementImpl
import DebuggerStatementImpl
import Declaration
import Decorator
import DecoratorImpl
import DefaultDecl
import DoWhileStatementImpl
import EmptyStatementImpl
import EnvConfig
import ExportAllDeclarationImpl
import ExportDeclarationImpl
import ExportDefaultDeclarationImpl
import ExportDefaultExpressionImpl
import ExportDefaultSpecifierImpl
import ExportNamedDeclarationImpl
import ExportNamespaceSpecifierImpl
import ExportSpecifier
import ExprOrSpread
import Expression
import ExpressionBase
import ExpressionStatementImpl
import Fn
import ForInStatementImpl
import ForOfStatementImpl
import ForStatementImpl
import FunctionDeclarationImpl
import FunctionExpressionImpl
import GetterPropertyImpl
import HasDecorator
import HasInterpreter
import HasInterpreterImpl
import HasSpan
import Identifier
import IdentifierImpl
import IfStatementImpl
import ImportDeclarationImpl
import ImportDefaultSpecifierImpl
import ImportImpl
import ImportNamespaceSpecifierImpl
import ImportSpecifier
import InvalidImpl
import JSXAttrValue
import JSXAttributeImpl
import JSXAttributeName
import JSXAttributeOrSpread
import JSXClosingElement
import JSXClosingElementImpl
import JSXClosingFragment
import JSXClosingFragmentImpl
import JSXElementChild
import JSXElementImpl
import JSXElementName
import JSXEmptyExpressionImpl
import JSXExpression
import JSXExpressionContainerImpl
import JSXFragmentImpl
import JSXMemberExpressionImpl
import JSXNamespacedNameImpl
import JSXObject
import JSXOpeningElement
import JSXOpeningElementImpl
import JSXOpeningFragment
import JSXOpeningFragmentImpl
import JSXSpreadChildImpl
import JSXTextImpl
import JscConfig
import KeyValuePatternPropertyImpl
import KeyValuePropertyImpl
import LabeledStatementImpl
import MemberExpressionImpl
import MetaPropertyImpl
import MethodPropertyImpl
import ModuleConfig
import ModuleExportName
import ModuleImpl
import ModuleItem
import NamedExportSpecifierImpl
import NamedImportSpecifierImpl
import NewExpressionImpl
import Node
import NullLiteralImpl
import NumericLiteralImpl
import ObjectExpression
import ObjectExpressionImpl
import ObjectPatternImpl
import ObjectPatternProperty
import OptionalChainingCallImpl
import OptionalChainingExpressionImpl
import Param
import ParamImpl
import ParenthesisExpressionImpl
import ParserConfig
import Pattern
import PatternBase
import PrivateMethodImpl
import PrivateName
import PrivateNameImpl
import PrivatePropertyImpl
import PropBase
import PropertyName
import RegExpLiteralImpl
import RestElementImpl
import ReturnStatementImpl
import ScriptImpl
import SequenceExpressionImpl
import SetterPropertyImpl
import Span
import SpanImpl
import SpreadElementImpl
import Statement
import StaticBlockImpl
import StringLiteral
import StringLiteralImpl
import Super
import SuperImpl
import SuperPropExpressionImpl
import SwitchCase
import SwitchCaseImpl
import SwitchStatementImpl
import TaggedTemplateExpressionImpl
import TemplateElementImpl
import TemplateLiteral
import ThisExpressionImpl
import ThrowStatementImpl
import TruePlusMinus
import TryStatementImpl
import TsArrayTypeImpl
import TsAsExpressionImpl
import TsCallSignatureDeclarationImpl
import TsConditionalTypeImpl
import TsConstAssertionImpl
import TsConstructSignatureDeclarationImpl
import TsConstructorTypeImpl
import TsEntityName
import TsEnumDeclarationImpl
import TsEnumMember
import TsEnumMemberId
import TsEnumMemberImpl
import TsExportAssignmentImpl
import TsExpressionWithTypeArguments
import TsExpressionWithTypeArgumentsImpl
import TsExternalModuleReferenceImpl
import TsFnParameter
import TsFunctionTypeImpl
import TsGetterSignatureImpl
import TsImportEqualsDeclarationImpl
import TsImportTypeImpl
import TsIndexSignatureImpl
import TsIndexedAccessTypeImpl
import TsInferTypeImpl
import TsInstantiationImpl
import TsInterfaceBody
import TsInterfaceBodyImpl
import TsInterfaceDeclarationImpl
import TsIntersectionTypeImpl
import TsKeywordTypeImpl
import TsLiteral
import TsLiteralTypeImpl
import TsMappedTypeImpl
import TsMethodSignatureImpl
import TsModuleBlockImpl
import TsModuleDeclarationImpl
import TsModuleName
import TsModuleReference
import TsNamespaceBody
import TsNamespaceDeclarationImpl
import TsNamespaceExportDeclarationImpl
import TsNonNullExpressionImpl
import TsOptionalTypeImpl
import TsParameterPropertyImpl
import TsParameterPropertyParameter
import TsParenthesizedTypeImpl
import TsPropertySignatureImpl
import TsQualifiedNameImpl
import TsRestTypeImpl
import TsSatisfiesExpressionImpl
import TsSetterSignatureImpl
import TsThisTypeImpl
import TsThisTypeOrIdent
import TsTupleElement
import TsTupleElementImpl
import TsTupleTypeImpl
import TsType
import TsTypeAliasDeclarationImpl
import TsTypeAnnotation
import TsTypeAnnotationImpl
import TsTypeAssertionImpl
import TsTypeElement
import TsTypeLiteralImpl
import TsTypeOperatorImpl
import TsTypeParameter
import TsTypeParameterDeclaration
import TsTypeParameterDeclarationImpl
import TsTypeParameterImpl
import TsTypeParameterInstantiation
import TsTypeParameterInstantiationImpl
import TsTypePredicateImpl
import TsTypeQueryExpr
import TsTypeQueryImpl
import TsTypeReferenceImpl
import TsUnionTypeImpl
import UnaryExpressionImpl
import UpdateExpressionImpl
import VariableDeclarationImpl
import VariableDeclarator
import VariableDeclaratorImpl
import WhileStatementImpl
import WithStatementImpl
import YieldExpressionImpl
import kotlin.Any
import kotlin.Array
import kotlin.Boolean
import kotlin.DslMarker
import kotlin.Int
import kotlin.Long
import kotlin.OptIn
import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@DslMarker
public annotation class SwcDslMarker

public typealias Record<T, S> = Map<T, String>

/**
 * /literalis:String
 */
public object JscTarget {
  public var ES3: String? = "es3"

  public var ES5: String? = "es5"

  public var ES2015: String? = "es2015"

  public var ES2016: String? = "es2016"

  public var ES2017: String? = "es2017"

  public var ES2018: String? = "es2018"

  public var ES2019: String? = "es2019"

  public var ES2020: String? = "es2020"

  public var ES2021: String? = "es2021"

  public var ES2022: String? = "es2022"

  public var ESNEXT: String? = "esnext"
}

/**
 * /literalis:String
 */
public object MethodKind {
  public var METHOD: String? = "method"

  public var GETTER: String? = "getter"

  public var SETTER: String? = "setter"
}

/**
 * /literalis:String
 */
public object VariableDeclarationKind {
  public var VAR: String? = "var"

  public var LET: String? = "let"

  public var CONST: String? = "const"
}

/**
 * /literalis:String
 */
public object BinaryOperator {
  public var Equality: String? = "=="

  public var Inequality: String? = "!="

  public var StrictEquality: String? = "==="

  public var StrictInequality: String? = "!=="

  public var LessThan: String? = "<"

  public var LessThanOrEqual: String? = "<="

  public var GreaterThan: String? = ">"

  public var GreaterThanOrEqual: String? = ">="

  public var LeftShift: String? = "<<"

  public var RightShift: String? = ">>"

  public var UnsignedRightShift: String? = ">>>"

  public var Addition: String? = "+"

  public var Subtraction: String? = "-"

  public var Multiplication: String? = "*"

  public var Division: String? = "/"

  public var BitwiseOR: String? = "|"

  public var BitwiseXOR: String? = "^"

  public var BitwiseAND: String? = "&"

  public var LogicalOR: String? = "||"

  public var LogicalAND: String? = "&&"

  public var JSIN: String? = "in"

  public var INSTANCEOF: String? = "instanceof"

  public var Exponentiation: String? = "**"

  public var NullishCoalescingOperator: String? = "??"
}

/**
 * /literalis:String
 */
public object AssignmentOperator {
  public var Assignment: String? = "="

  public var AdditionAssignment: String? = "+="

  public var SubtractionAssignment: String? = "-="

  public var MultiplicationAssignment: String? = "*="

  public var DivisionAssignment: String? = "/="

  public var LeftShiftAssignment: String? = "<<="

  public var RightShiftAssignment: String? = ">>="

  public var UnsignedRightShiftAssignment: String? = ">>>="

  public var BitwiseORAssignment: String? = "|="

  public var BitwiseXORAssignment: String? = "^="

  public var BitwiseANDAssignment: String? = "&="

  public var ExponentiationAssignment: String? = "**="

  public var LogicalANDAssignment: String? = "&&="

  public var LogicalORAssignment: String? = "||="

  public var NullishCoalescingAssignment: String? = "??="
}

/**
 * /literalis:String
 */
public object UpdateOperator {
  public var Increment: String? = "++"

  public var Decrement: String? = "--"
}

/**
 * /literalis:String
 */
public object UnaryOperator {
  public var Subtraction: String? = "-"

  public var Addition: String? = "+"

  public var LogicalNOT: String? = "!"

  public var BitwiseNOT: String? = "~"

  public var TYPEOF: String? = "typeof"

  public var VOID: String? = "void"

  public var DELETE: String? = "delete"
}

/**
 * /literalis:String
 */
public object TsKeywordTypeKind {
  public var ANY: String? = "any"

  public var UNKNOWN: String? = "unknown"

  public var NUMBER: String? = "number"

  public var JSOBJECT: String? = "object"

  public var BOOLEAN: String? = "boolean"

  public var BIGINT: String? = "bigint"

  public var STRING: String? = "string"

  public var SYMBOL: String? = "symbol"

  public var VOID: String? = "void"

  public var UNDEFINED: String? = "undefined"

  public var NULL: String? = "null"

  public var NEVER: String? = "never"

  public var INTRINSIC: String? = "intrinsic"
}

/**
 * /literalis:String
 */
public object TsTypeOperatorOp {
  public var KEYOF: String? = "keyof"

  public var UNIQUE: String? = "unique"

  public var READONLY: String? = "readonly"
}

/**
 * /literalis:String
 */
public object Accessibility {
  public var PUBLIC: String? = "public"

  public var PROTECTED: String? = "protected"

  public var PRIVATE: String? = "private"
}

@SwcDslMarker
@Serializable
public sealed interface BaseParseOptions {
  public var comments: Boolean? = null

  public var script: Boolean? = null

  public var target: String? = null
}

@SwcDslMarker
@Serializable
public open class ParseOptions : ParserConfig(), BaseParseOptions {
  override var comments: Boolean? = null

  override var script: Boolean? = null

  override var target: String? = null
}

@SwcDslMarker
@Serializable
public sealed interface ParserConfig

@SwcDslMarker
@Serializable
public sealed interface ModuleConfig

@SwcDslMarker
@Serializable
public sealed interface ClassMember

@SwcDslMarker
@Serializable
public sealed interface Declaration

@SwcDslMarker
@Serializable
public sealed interface Expression

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
public sealed interface Literal

@SwcDslMarker
@Serializable
public sealed interface ModuleDeclaration

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
public sealed interface Statement

@SwcDslMarker
@Serializable
public sealed interface TsParameterPropertyParameter

@SwcDslMarker
@Serializable
public sealed interface TsEntityName

@SwcDslMarker
@Serializable
public sealed interface TsTypeElement

@SwcDslMarker
@Serializable
public sealed interface TsType

@SwcDslMarker
@Serializable
public sealed interface TsFnOrConstructorType

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
public sealed interface TsUnionOrIntersectionType

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

@SwcDslMarker
public interface Config {
  public var test: Array<String>? = null

  public var exclude: Array<String>? = null

  public var env: EnvConfig? = null

  public var jsc: JscConfig? = null

  public var module: ModuleConfig? = null

  public var minify: Boolean? = null

  @Serializable(BooleanableStringSerializer::class)
  public var sourceMaps: BooleanableString? = null

  public var inlineSourcesContent: Boolean? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("Config")
public class ConfigImpl : ConfigImpl() {
  override var test: Array<String>? = null

  override var exclude: Array<String>? = null

  override var env: EnvConfig? = null

  override var jsc: JscConfig? = null

  override var module: ModuleConfig? = null

  override var minify: Boolean? = null

  @Serializable(BooleanableStringSerializer::class)
  override var sourceMaps: BooleanableString? = null

  override var inlineSourcesContent: Boolean? = null
}

@SwcDslMarker
public interface BaseModuleConfig {
  public var strict: Boolean? = null

  public var strictMode: Boolean? = null

  @Serializable(BooleanableArrayStringSerializer::class)
  public var lazy: BooleanableArrayString? = null

  public var noInterop: Boolean? = null

  public var importInterop: String? = null

  public var exportInteropAnnotation: Boolean? = null

  public var ignoreDynamic: Boolean? = null

  public var allowTopLevelThis: Boolean? = null

  public var preserveImportMeta: Boolean? = null
}

@SwcDslMarker
@Serializable
@SerialName("Span")
public class Span {
  public var start: Int? = null

  public var end: Int? = null

  public var ctxt: Int? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("Span")
public class SpanImpl : SpanImpl() {
  override var start: Int? = null

  override var end: Int? = null

  override var ctxt: Int? = null
}

@SwcDslMarker
public sealed interface Node {
  public var type: String? = null
}

@SwcDslMarker
public interface HasSpan {
  public var span: Span? = null
}

@SwcDslMarker
public interface HasDecorator {
  public var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface Class : HasSpan, HasDecorator {
  public var body: Array<ClassMember>? = null

  public var superClass: Expression? = null

  public var isAbstract: Boolean? = null

  public var typeParams: TsTypeParameterDeclaration? = null

  public var superTypeParams: TsTypeParameterInstantiation? = null

  public var implements: Array<TsExpressionWithTypeArguments>? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("Class")
public class ClassImpl : ClassImpl() {
  override var body: Array<ClassMember>? = null

  override var superClass: Expression? = null

  override var isAbstract: Boolean? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var superTypeParams: TsTypeParameterInstantiation? = null

  override var implements: Array<TsExpressionWithTypeArguments>? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface ClassPropertyBase : Node, HasSpan, HasDecorator {
  public var `value`: Expression? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  public var isStatic: Boolean? = null

  public var accessibility: String? = null

  public var isOptional: Boolean? = null

  public var isOverride: Boolean? = null

  public var readonly: Boolean? = null

  public var definite: Boolean? = null

  override var type: String? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface ClassProperty : ClassPropertyBase {
  public var type: String? = null

  public var key: PropertyName? = null

  public var isAbstract: Boolean? = null

  public var declare: Boolean? = null

  override var `value`: Expression? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var isStatic: Boolean? = null

  override var accessibility: String? = null

  override var isOptional: Boolean? = null

  override var isOverride: Boolean? = null

  override var readonly: Boolean? = null

  override var definite: Boolean? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ClassProperty)
public class ClassPropertyImpl : ClassPropertyImpl() {
  override var type: String? = ClassProperty

  override var key: PropertyName? = null

  override var isAbstract: Boolean? = null

  override var declare: Boolean? = null

  override var `value`: Expression? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var isStatic: Boolean? = null

  override var accessibility: String? = null

  override var isOptional: Boolean? = null

  override var isOverride: Boolean? = null

  override var readonly: Boolean? = null

  override var definite: Boolean? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface PrivateProperty : ClassPropertyBase {
  public var type: String? = null

  public var key: PrivateName? = null

  override var `value`: Expression? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var isStatic: Boolean? = null

  override var accessibility: String? = null

  override var isOptional: Boolean? = null

  override var isOverride: Boolean? = null

  override var readonly: Boolean? = null

  override var definite: Boolean? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(PrivateProperty)
public class PrivatePropertyImpl : PrivatePropertyImpl() {
  override var type: String? = PrivateProperty

  override var key: PrivateName? = null

  override var `value`: Expression? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var isStatic: Boolean? = null

  override var accessibility: String? = null

  override var isOptional: Boolean? = null

  override var isOverride: Boolean? = null

  override var readonly: Boolean? = null

  override var definite: Boolean? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface Param : Node, HasSpan, HasDecorator {
  public var type: String? = null

  public var pat: Pattern? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(Parameter)
public class ParamImpl : ParamImpl() {
  override var type: String? = Parameter

  override var pat: Pattern? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface Constructor : Node, HasSpan {
  public var type: String? = null

  public var key: PropertyName? = null

  public var params: Any? = null

  public var body: BlockStatement? = null

  public var accessibility: String? = null

  public var isOptional: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(Constructor)
public class ConstructorImpl : ConstructorImpl() {
  override var type: String? = Constructor

  override var key: PropertyName? = null

  override var params: Any? = null

  override var body: BlockStatement? = null

  override var accessibility: String? = null

  override var isOptional: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ClassMethodBase : Node, HasSpan {
  public var function: Fn? = null

  public var kind: String? = null

  public var isStatic: Boolean? = null

  public var accessibility: String? = null

  public var isAbstract: Boolean? = null

  public var isOptional: Boolean? = null

  public var isOverride: Boolean? = null

  override var type: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ClassMethod : ClassMethodBase {
  public var type: String? = null

  public var key: PropertyName? = null

  override var function: Fn? = null

  override var kind: String? = null

  override var isStatic: Boolean? = null

  override var accessibility: String? = null

  override var isAbstract: Boolean? = null

  override var isOptional: Boolean? = null

  override var isOverride: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ClassMethod)
public class ClassMethodImpl : ClassMethodImpl() {
  override var type: String? = ClassMethod

  override var key: PropertyName? = null

  override var function: Fn? = null

  override var kind: String? = null

  override var isStatic: Boolean? = null

  override var accessibility: String? = null

  override var isAbstract: Boolean? = null

  override var isOptional: Boolean? = null

  override var isOverride: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface PrivateMethod : ClassMethodBase {
  public var type: String? = null

  public var key: PrivateName? = null

  override var function: Fn? = null

  override var kind: String? = null

  override var isStatic: Boolean? = null

  override var accessibility: String? = null

  override var isAbstract: Boolean? = null

  override var isOptional: Boolean? = null

  override var isOverride: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(PrivateMethod)
public class PrivateMethodImpl : PrivateMethodImpl() {
  override var type: String? = PrivateMethod

  override var key: PrivateName? = null

  override var function: Fn? = null

  override var kind: String? = null

  override var isStatic: Boolean? = null

  override var accessibility: String? = null

  override var isAbstract: Boolean? = null

  override var isOptional: Boolean? = null

  override var isOverride: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface StaticBlock : Node, HasSpan {
  public var type: String? = null

  public var body: BlockStatement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(StaticBlock)
public class StaticBlockImpl : StaticBlockImpl() {
  override var type: String? = StaticBlock

  override var body: BlockStatement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface Decorator : Node, HasSpan {
  public var type: String? = null

  public var expression: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(Decorator)
public class DecoratorImpl : DecoratorImpl() {
  override var type: String? = Decorator

  override var expression: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface FunctionDeclaration : Fn {
  public var type: String? = null

  public var identifier: Identifier? = null

  public var declare: Boolean? = null

  override var params: Array<Param>? = null

  override var body: BlockStatement? = null

  override var generator: Boolean? = null

  override var async: Boolean? = null

  override var typeParameters: TsTypeParameterDeclaration? = null

  override var returnType: TsTypeAnnotation? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(FunctionDeclaration)
public class FunctionDeclarationImpl : FunctionDeclarationImpl() {
  override var type: String? = FunctionDeclaration

  override var identifier: Identifier? = null

  override var declare: Boolean? = null

  override var params: Array<Param>? = null

  override var body: BlockStatement? = null

  override var generator: Boolean? = null

  override var async: Boolean? = null

  override var typeParameters: TsTypeParameterDeclaration? = null

  override var returnType: TsTypeAnnotation? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface ClassDeclaration : Class, Node {
  public var type: String? = null

  public var identifier: Identifier? = null

  public var declare: Boolean? = null

  override var body: Array<ClassMember>? = null

  override var superClass: Expression? = null

  override var isAbstract: Boolean? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var superTypeParams: TsTypeParameterInstantiation? = null

  override var implements: Array<TsExpressionWithTypeArguments>? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ClassDeclaration)
public class ClassDeclarationImpl : ClassDeclarationImpl() {
  override var type: String? = ClassDeclaration

  override var identifier: Identifier? = null

  override var declare: Boolean? = null

  override var body: Array<ClassMember>? = null

  override var superClass: Expression? = null

  override var isAbstract: Boolean? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var superTypeParams: TsTypeParameterInstantiation? = null

  override var implements: Array<TsExpressionWithTypeArguments>? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface VariableDeclaration : Node, HasSpan {
  public var type: String? = null

  public var kind: String? = null

  public var declare: Boolean? = null

  public var declarations: Array<VariableDeclarator>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(VariableDeclaration)
public class VariableDeclarationImpl : VariableDeclarationImpl() {
  override var type: String? = VariableDeclaration

  override var kind: String? = null

  override var declare: Boolean? = null

  override var declarations: Array<VariableDeclarator>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface VariableDeclarator : Node, HasSpan {
  public var type: String? = null

  public var id: Pattern? = null

  public var `init`: Expression? = null

  public var definite: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(VariableDeclarator)
public class VariableDeclaratorImpl : VariableDeclaratorImpl() {
  override var type: String? = VariableDeclarator

  override var id: Pattern? = null

  override var `init`: Expression? = null

  override var definite: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ExpressionBase : Node, HasSpan {
  override var type: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface Identifier : ExpressionBase {
  public var type: String? = null

  public var `value`: String? = null

  public var optional: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("Identifier")
public class IdentifierImpl : IdentifierImpl() {
  override var type: String? = Identifier

  override var `value`: String? = null

  override var optional: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface OptionalChainingExpression : ExpressionBase {
  public var type: String? = null

  public var questionDotToken: Span? = null

  public var base: Any? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(OptionalChainingExpression)
public class OptionalChainingExpressionImpl : OptionalChainingExpressionImpl() {
  override var type: String? = OptionalChainingExpression

  override var questionDotToken: Span? = null

  override var base: Any? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface OptionalChainingCall : ExpressionBase {
  public var type: String? = null

  public var callee: Expression? = null

  public var arguments: Array<ExprOrSpread>? = null

  public var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("OptionalChainingCall")
public class OptionalChainingCallImpl : OptionalChainingCallImpl() {
  override var type: String? = CallExpression

  override var callee: Expression? = null

  override var arguments: Array<ExprOrSpread>? = null

  override var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ThisExpression : ExpressionBase {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ThisExpression)
public class ThisExpressionImpl : ThisExpressionImpl() {
  override var type: String? = ThisExpression

  override var span: Span? = null
}

@SwcDslMarker
public interface ArrayExpression : ExpressionBase {
  public var type: String? = null

  public var elements: Array<ExprOrSpread>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ArrayExpression)
public class ArrayExpressionImpl : ArrayExpressionImpl() {
  override var type: String? = ArrayExpression

  override var elements: Array<ExprOrSpread>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ObjectExpression : ExpressionBase {
  public var type: String? = null

  public var properties: Any? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ObjectExpression)
public class ObjectExpressionImpl : ObjectExpressionImpl() {
  override var type: String? = ObjectExpression

  override var properties: Any? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface SpreadElement : Node {
  public var type: String? = null

  public var spread: Span? = null

  public var arguments: Expression? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(SpreadElement)
public class SpreadElementImpl : SpreadElementImpl() {
  override var type: String? = SpreadElement

  override var spread: Span? = null

  override var arguments: Expression? = null
}

@SwcDslMarker
public interface UnaryExpression : ExpressionBase {
  public var type: String? = null

  public var `operator`: String? = null

  public var argument: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(UnaryExpression)
public class UnaryExpressionImpl : UnaryExpressionImpl() {
  override var type: String? = UnaryExpression

  override var `operator`: String? = null

  override var argument: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface UpdateExpression : ExpressionBase {
  public var type: String? = null

  public var `operator`: String? = null

  public var prefix: Boolean? = null

  public var argument: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(UpdateExpression)
public class UpdateExpressionImpl : UpdateExpressionImpl() {
  override var type: String? = UpdateExpression

  override var `operator`: String? = null

  override var prefix: Boolean? = null

  override var argument: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface BinaryExpression : ExpressionBase {
  public var type: String? = null

  public var `operator`: String? = null

  public var left: Expression? = null

  public var right: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(BinaryExpression)
public class BinaryExpressionImpl : BinaryExpressionImpl() {
  override var type: String? = BinaryExpression

  override var `operator`: String? = null

  override var left: Expression? = null

  override var right: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface FunctionExpression : Fn, ExpressionBase {
  public var type: String? = null

  public var identifier: Identifier? = null

  override var params: Array<Param>? = null

  override var body: BlockStatement? = null

  override var generator: Boolean? = null

  override var async: Boolean? = null

  override var typeParameters: TsTypeParameterDeclaration? = null

  override var returnType: TsTypeAnnotation? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(FunctionExpression)
public class FunctionExpressionImpl : FunctionExpressionImpl() {
  override var type: String? = FunctionExpression

  override var identifier: Identifier? = null

  override var params: Array<Param>? = null

  override var body: BlockStatement? = null

  override var generator: Boolean? = null

  override var async: Boolean? = null

  override var typeParameters: TsTypeParameterDeclaration? = null

  override var returnType: TsTypeAnnotation? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface ClassExpression : Class, ExpressionBase {
  public var type: String? = null

  public var identifier: Identifier? = null

  override var body: Array<ClassMember>? = null

  override var superClass: Expression? = null

  override var isAbstract: Boolean? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var superTypeParams: TsTypeParameterInstantiation? = null

  override var implements: Array<TsExpressionWithTypeArguments>? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ClassExpression)
public class ClassExpressionImpl : ClassExpressionImpl() {
  override var type: String? = ClassExpression

  override var identifier: Identifier? = null

  override var body: Array<ClassMember>? = null

  override var superClass: Expression? = null

  override var isAbstract: Boolean? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var superTypeParams: TsTypeParameterInstantiation? = null

  override var implements: Array<TsExpressionWithTypeArguments>? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface AssignmentExpression : ExpressionBase {
  public var type: String? = null

  public var `operator`: String? = null

  public var left: Any? = null

  public var right: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(AssignmentExpression)
public class AssignmentExpressionImpl : AssignmentExpressionImpl() {
  override var type: String? = AssignmentExpression

  override var `operator`: String? = null

  override var left: Any? = null

  override var right: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface MemberExpression : ExpressionBase {
  public var type: String? = null

  @SerialName("object")
  public var jsObject: Expression? = null

  public var `property`: Any? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(MemberExpression)
public class MemberExpressionImpl : MemberExpressionImpl() {
  override var type: String? = MemberExpression

  @SerialName("object")
  override var jsObject: Expression? = null

  override var `property`: Any? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface SuperPropExpression : ExpressionBase {
  public var type: String? = null

  public var obj: Super? = null

  public var `property`: Any? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(SuperPropExpression)
public class SuperPropExpressionImpl : SuperPropExpressionImpl() {
  override var type: String? = SuperPropExpression

  override var obj: Super? = null

  override var `property`: Any? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ConditionalExpression : ExpressionBase {
  public var type: String? = null

  public var test: Expression? = null

  public var consequent: Expression? = null

  public var alternate: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ConditionalExpression)
public class ConditionalExpressionImpl : ConditionalExpressionImpl() {
  override var type: String? = ConditionalExpression

  override var test: Expression? = null

  override var consequent: Expression? = null

  override var alternate: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface Super : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(Super)
public class SuperImpl : SuperImpl() {
  override var type: String? = Super

  override var span: Span? = null
}

@SwcDslMarker
public interface Import : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(Import)
public class ImportImpl : ImportImpl() {
  override var type: String? = Import

  override var span: Span? = null
}

@SwcDslMarker
public interface CallExpression : ExpressionBase {
  public var type: String? = null

  public var callee: Any? = null

  public var arguments: Array<Argument>? = null

  public var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("CallExpression")
public class CallExpressionImpl : CallExpressionImpl() {
  override var type: String? = CallExpression

  override var callee: Any? = null

  override var arguments: Array<Argument>? = null

  override var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface NewExpression : ExpressionBase {
  public var type: String? = null

  public var callee: Expression? = null

  public var arguments: Array<Argument>? = null

  public var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(NewExpression)
public class NewExpressionImpl : NewExpressionImpl() {
  override var type: String? = NewExpression

  override var callee: Expression? = null

  override var arguments: Array<Argument>? = null

  override var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface SequenceExpression : ExpressionBase {
  public var type: String? = null

  public var expressions: Array<Expression>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(SequenceExpression)
public class SequenceExpressionImpl : SequenceExpressionImpl() {
  override var type: String? = SequenceExpression

  override var expressions: Array<Expression>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ArrowFunctionExpression : ExpressionBase {
  public var type: String? = null

  public var params: Array<Pattern>? = null

  public var body: Any? = null

  public var async: Boolean? = null

  public var generator: Boolean? = null

  public var typeParameters: TsTypeParameterDeclaration? = null

  public var returnType: TsTypeAnnotation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ArrowFunctionExpression)
public class ArrowFunctionExpressionImpl : ArrowFunctionExpressionImpl() {
  override var type: String? = ArrowFunctionExpression

  override var params: Array<Pattern>? = null

  override var body: Any? = null

  override var async: Boolean? = null

  override var generator: Boolean? = null

  override var typeParameters: TsTypeParameterDeclaration? = null

  override var returnType: TsTypeAnnotation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface YieldExpression : ExpressionBase {
  public var type: String? = null

  public var argument: Expression? = null

  public var `delegate`: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(YieldExpression)
public class YieldExpressionImpl : YieldExpressionImpl() {
  override var type: String? = YieldExpression

  override var argument: Expression? = null

  override var `delegate`: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface MetaProperty : Node, HasSpan {
  public var type: String? = null

  public var kind: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(MetaProperty)
public class MetaPropertyImpl : MetaPropertyImpl() {
  override var type: String? = MetaProperty

  override var kind: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface AwaitExpression : ExpressionBase {
  public var type: String? = null

  public var argument: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(AwaitExpression)
public class AwaitExpressionImpl : AwaitExpressionImpl() {
  override var type: String? = AwaitExpression

  override var argument: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TaggedTemplateExpression : ExpressionBase {
  public var type: String? = null

  public var tag: Expression? = null

  public var typeParameters: TsTypeParameterInstantiation? = null

  public var template: TemplateLiteral? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TaggedTemplateExpression)
public class TaggedTemplateExpressionImpl : TaggedTemplateExpressionImpl() {
  override var type: String? = TaggedTemplateExpression

  override var tag: Expression? = null

  override var typeParameters: TsTypeParameterInstantiation? = null

  override var template: TemplateLiteral? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TemplateElement : ExpressionBase {
  public var type: String? = null

  public var tail: Boolean? = null

  public var cooked: String? = null

  public var raw: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TemplateElement)
public class TemplateElementImpl : TemplateElementImpl() {
  override var type: String? = TemplateElement

  override var tail: Boolean? = null

  override var cooked: String? = null

  override var raw: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ParenthesisExpression : ExpressionBase {
  public var type: String? = null

  public var expression: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ParenthesisExpression)
public class ParenthesisExpressionImpl : ParenthesisExpressionImpl() {
  override var type: String? = ParenthesisExpression

  override var expression: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface Fn : HasSpan, HasDecorator {
  public var params: Array<Param>? = null

  public var body: BlockStatement? = null

  public var generator: Boolean? = null

  public var async: Boolean? = null

  public var typeParameters: TsTypeParameterDeclaration? = null

  public var returnType: TsTypeAnnotation? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface PatternBase : Node, HasSpan {
  public var typeAnnotation: TsTypeAnnotation? = null

  override var type: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface PrivateName : ExpressionBase {
  public var type: String? = null

  public var id: Identifier? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(PrivateName)
public class PrivateNameImpl : PrivateNameImpl() {
  override var type: String? = PrivateName

  override var id: Identifier? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXMemberExpression : Node {
  public var type: String? = null

  @SerialName("object")
  public var jsObject: JSXObject? = null

  public var `property`: Identifier? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXMemberExpression)
public class JSXMemberExpressionImpl : JSXMemberExpressionImpl() {
  override var type: String? = JSXMemberExpression

  @SerialName("object")
  override var jsObject: JSXObject? = null

  override var `property`: Identifier? = null
}

@SwcDslMarker
public interface JSXNamespacedName : Node {
  public var type: String? = null

  public var namespace: Identifier? = null

  public var name: Identifier? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXNamespacedName)
public class JSXNamespacedNameImpl : JSXNamespacedNameImpl() {
  override var type: String? = JSXNamespacedName

  override var namespace: Identifier? = null

  override var name: Identifier? = null
}

@SwcDslMarker
public interface JSXEmptyExpression : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXEmptyExpression)
public class JSXEmptyExpressionImpl : JSXEmptyExpressionImpl() {
  override var type: String? = JSXEmptyExpression

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXExpressionContainer : Node, HasSpan {
  public var type: String? = null

  public var expression: JSXExpression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXExpressionContainer)
public class JSXExpressionContainerImpl : JSXExpressionContainerImpl() {
  override var type: String? = JSXExpressionContainer

  override var expression: JSXExpression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXSpreadChild : Node, HasSpan {
  public var type: String? = null

  public var expression: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXSpreadChild)
public class JSXSpreadChildImpl : JSXSpreadChildImpl() {
  override var type: String? = JSXSpreadChild

  override var expression: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXOpeningElement : Node, HasSpan {
  public var type: String? = null

  public var name: JSXElementName? = null

  public var attributes: Array<JSXAttributeOrSpread>? = null

  public var selfClosing: Boolean? = null

  public var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXOpeningElement)
public class JSXOpeningElementImpl : JSXOpeningElementImpl() {
  override var type: String? = JSXOpeningElement

  override var name: JSXElementName? = null

  override var attributes: Array<JSXAttributeOrSpread>? = null

  override var selfClosing: Boolean? = null

  override var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXClosingElement : Node, HasSpan {
  public var type: String? = null

  public var name: JSXElementName? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXClosingElement)
public class JSXClosingElementImpl : JSXClosingElementImpl() {
  override var type: String? = JSXClosingElement

  override var name: JSXElementName? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXAttribute : Node, HasSpan {
  public var type: String? = null

  public var name: JSXAttributeName? = null

  public var `value`: JSXAttrValue? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXAttribute)
public class JSXAttributeImpl : JSXAttributeImpl() {
  override var type: String? = JSXAttribute

  override var name: JSXAttributeName? = null

  override var `value`: JSXAttrValue? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXText : Node, HasSpan {
  public var type: String? = null

  public var `value`: String? = null

  public var raw: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXText)
public class JSXTextImpl : JSXTextImpl() {
  override var type: String? = JSXText

  override var `value`: String? = null

  override var raw: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXElement : Node, HasSpan {
  public var type: String? = null

  public var opening: JSXOpeningElement? = null

  public var children: Array<JSXElementChild>? = null

  public var closing: JSXClosingElement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXElement)
public class JSXElementImpl : JSXElementImpl() {
  override var type: String? = JSXElement

  override var opening: JSXOpeningElement? = null

  override var children: Array<JSXElementChild>? = null

  override var closing: JSXClosingElement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXFragment : Node, HasSpan {
  public var type: String? = null

  public var opening: JSXOpeningFragment? = null

  public var children: Array<JSXElementChild>? = null

  public var closing: JSXClosingFragment? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXFragment)
public class JSXFragmentImpl : JSXFragmentImpl() {
  override var type: String? = JSXFragment

  override var opening: JSXOpeningFragment? = null

  override var children: Array<JSXElementChild>? = null

  override var closing: JSXClosingFragment? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXOpeningFragment : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXOpeningFragment)
public class JSXOpeningFragmentImpl : JSXOpeningFragmentImpl() {
  override var type: String? = JSXOpeningFragment

  override var span: Span? = null
}

@SwcDslMarker
public interface JSXClosingFragment : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(JSXClosingFragment)
public class JSXClosingFragmentImpl : JSXClosingFragmentImpl() {
  override var type: String? = JSXClosingFragment

  override var span: Span? = null
}

@SwcDslMarker
public interface StringLiteral : Node, HasSpan {
  public var type: String? = null

  public var `value`: String? = null

  public var raw: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(StringLiteral)
public class StringLiteralImpl : StringLiteralImpl() {
  override var type: String? = StringLiteral

  override var `value`: String? = null

  override var raw: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface BooleanLiteral : Node, HasSpan {
  public var type: String? = null

  public var `value`: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(BooleanLiteral)
public class BooleanLiteralImpl : BooleanLiteralImpl() {
  override var type: String? = BooleanLiteral

  override var `value`: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface NullLiteral : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(NullLiteral)
public class NullLiteralImpl : NullLiteralImpl() {
  override var type: String? = NullLiteral

  override var span: Span? = null
}

@SwcDslMarker
public interface RegExpLiteral : Node, HasSpan {
  public var type: String? = null

  public var pattern: String? = null

  public var flags: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(RegExpLiteral)
public class RegExpLiteralImpl : RegExpLiteralImpl() {
  override var type: String? = RegExpLiteral

  override var pattern: String? = null

  override var flags: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface NumericLiteral : Node, HasSpan {
  public var type: String? = null

  public var `value`: Int? = null

  public var raw: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(NumericLiteral)
public class NumericLiteralImpl : NumericLiteralImpl() {
  override var type: String? = NumericLiteral

  override var `value`: Int? = null

  override var raw: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface BigIntLiteral : Node, HasSpan {
  public var type: String? = null

  public var `value`: Long? = null

  public var raw: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(BigIntLiteral)
public class BigIntLiteralImpl : BigIntLiteralImpl() {
  override var type: String? = BigIntLiteral

  override var `value`: Long? = null

  override var raw: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ExportDefaultExpression : Node, HasSpan {
  public var type: String? = null

  public var expression: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ExportDefaultExpression)
public class ExportDefaultExpressionImpl : ExportDefaultExpressionImpl() {
  override var type: String? = ExportDefaultExpression

  override var expression: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ExportDeclaration : Node, HasSpan {
  public var type: String? = null

  public var declaration: Declaration? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ExportDeclaration)
public class ExportDeclarationImpl : ExportDeclarationImpl() {
  override var type: String? = ExportDeclaration

  override var declaration: Declaration? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ImportDeclaration : Node, HasSpan {
  public var type: String? = null

  public var specifiers: Array<ImportSpecifier>? = null

  public var source: StringLiteral? = null

  public var typeOnly: Boolean? = null

  public var asserts: ObjectExpression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ImportDeclaration)
public class ImportDeclarationImpl : ImportDeclarationImpl() {
  override var type: String? = ImportDeclaration

  override var specifiers: Array<ImportSpecifier>? = null

  override var source: StringLiteral? = null

  override var typeOnly: Boolean? = null

  override var asserts: ObjectExpression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ExportAllDeclaration : Node, HasSpan {
  public var type: String? = null

  public var source: StringLiteral? = null

  public var asserts: ObjectExpression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ExportAllDeclaration)
public class ExportAllDeclarationImpl : ExportAllDeclarationImpl() {
  override var type: String? = ExportAllDeclaration

  override var source: StringLiteral? = null

  override var asserts: ObjectExpression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ExportNamedDeclaration : Node, HasSpan {
  public var type: String? = null

  public var specifiers: Array<ExportSpecifier>? = null

  public var source: StringLiteral? = null

  public var typeOnly: Boolean? = null

  public var asserts: ObjectExpression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ExportNamedDeclaration)
public class ExportNamedDeclarationImpl : ExportNamedDeclarationImpl() {
  override var type: String? = ExportNamedDeclaration

  override var specifiers: Array<ExportSpecifier>? = null

  override var source: StringLiteral? = null

  override var typeOnly: Boolean? = null

  override var asserts: ObjectExpression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ExportDefaultDeclaration : Node, HasSpan {
  public var type: String? = null

  public var decl: DefaultDecl? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ExportDefaultDeclaration)
public class ExportDefaultDeclarationImpl : ExportDefaultDeclarationImpl() {
  override var type: String? = ExportDefaultDeclaration

  override var decl: DefaultDecl? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ImportDefaultSpecifier : Node, HasSpan {
  public var type: String? = null

  public var local: Identifier? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ImportDefaultSpecifier)
public class ImportDefaultSpecifierImpl : ImportDefaultSpecifierImpl() {
  override var type: String? = ImportDefaultSpecifier

  override var local: Identifier? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ImportNamespaceSpecifier : Node, HasSpan {
  public var type: String? = null

  public var local: Identifier? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ImportNamespaceSpecifier)
public class ImportNamespaceSpecifierImpl : ImportNamespaceSpecifierImpl() {
  override var type: String? = ImportNamespaceSpecifier

  override var local: Identifier? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface NamedImportSpecifier : Node, HasSpan {
  public var type: String? = null

  public var local: Identifier? = null

  public var imported: ModuleExportName? = null

  public var isTypeOnly: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ImportSpecifier)
public class NamedImportSpecifierImpl : NamedImportSpecifierImpl() {
  override var type: String? = ImportSpecifier

  override var local: Identifier? = null

  override var imported: ModuleExportName? = null

  override var isTypeOnly: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ExportNamespaceSpecifier : Node, HasSpan {
  public var type: String? = null

  public var name: ModuleExportName? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ExportNamespaceSpecifier)
public class ExportNamespaceSpecifierImpl : ExportNamespaceSpecifierImpl() {
  override var type: String? = ExportNamespaceSpecifier

  override var name: ModuleExportName? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ExportDefaultSpecifier : Node, HasSpan {
  public var type: String? = null

  public var exported: Identifier? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ExportDefaultSpecifier)
public class ExportDefaultSpecifierImpl : ExportDefaultSpecifierImpl() {
  override var type: String? = ExportDefaultSpecifier

  override var exported: Identifier? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface NamedExportSpecifier : Node, HasSpan {
  public var type: String? = null

  public var orig: ModuleExportName? = null

  public var exported: ModuleExportName? = null

  public var isTypeOnly: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ExportSpecifier)
public class NamedExportSpecifierImpl : NamedExportSpecifierImpl() {
  override var type: String? = ExportSpecifier

  override var orig: ModuleExportName? = null

  override var exported: ModuleExportName? = null

  override var isTypeOnly: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface HasInterpreter {
  public var interpreter: String? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("HasInterpreter")
public class HasInterpreterImpl : HasInterpreterImpl() {
  override var interpreter: String? = null
}

@SwcDslMarker
public interface Module : Node, HasSpan, HasInterpreter {
  public var type: String? = null

  public var body: Array<ModuleItem>? = null

  override var span: Span? = null

  override var interpreter: String? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(Module)
public class ModuleImpl : ModuleImpl() {
  override var type: String? = Module

  override var body: Array<ModuleItem>? = null

  override var span: Span? = null

  override var interpreter: String? = null
}

@SwcDslMarker
public interface Script : Node, HasSpan, HasInterpreter {
  public var type: String? = null

  public var body: Array<Statement>? = null

  override var span: Span? = null

  override var interpreter: String? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(Script)
public class ScriptImpl : ScriptImpl() {
  override var type: String? = Script

  override var body: Array<Statement>? = null

  override var span: Span? = null

  override var interpreter: String? = null
}

@SwcDslMarker
public interface BindingIdentifier : PatternBase {
  public var type: String? = null

  public var `value`: String? = null

  public var optional: Boolean? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("BindingIdentifier")
public class BindingIdentifierImpl : BindingIdentifierImpl() {
  override var type: String? = Identifier

  override var `value`: String? = null

  override var optional: Boolean? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ArrayPattern : PatternBase {
  public var type: String? = null

  public var elements: Array<Pattern>? = null

  public var optional: Boolean? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ArrayPattern)
public class ArrayPatternImpl : ArrayPatternImpl() {
  override var type: String? = ArrayPattern

  override var elements: Array<Pattern>? = null

  override var optional: Boolean? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ObjectPattern : PatternBase {
  public var type: String? = null

  public var properties: Array<ObjectPatternProperty>? = null

  public var optional: Boolean? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ObjectPattern)
public class ObjectPatternImpl : ObjectPatternImpl() {
  override var type: String? = ObjectPattern

  override var properties: Array<ObjectPatternProperty>? = null

  override var optional: Boolean? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface AssignmentPattern : PatternBase {
  public var type: String? = null

  public var left: Pattern? = null

  public var right: Expression? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(AssignmentPattern)
public class AssignmentPatternImpl : AssignmentPatternImpl() {
  override var type: String? = AssignmentPattern

  override var left: Pattern? = null

  override var right: Expression? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface RestElement : PatternBase {
  public var type: String? = null

  public var rest: Span? = null

  public var argument: Pattern? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(RestElement)
public class RestElementImpl : RestElementImpl() {
  override var type: String? = RestElement

  override var rest: Span? = null

  override var argument: Pattern? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface KeyValuePatternProperty : Node {
  public var type: String? = null

  public var key: PropertyName? = null

  public var `value`: Pattern? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(KeyValuePatternProperty)
public class KeyValuePatternPropertyImpl : KeyValuePatternPropertyImpl() {
  override var type: String? = KeyValuePatternProperty

  override var key: PropertyName? = null

  override var `value`: Pattern? = null
}

@SwcDslMarker
public interface AssignmentPatternProperty : Node, HasSpan {
  public var type: String? = null

  public var key: Identifier? = null

  public var `value`: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(AssignmentPatternProperty)
public class AssignmentPatternPropertyImpl : AssignmentPatternPropertyImpl() {
  override var type: String? = AssignmentPatternProperty

  override var key: Identifier? = null

  override var `value`: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface PropBase : Node {
  public var key: PropertyName? = null

  override var type: String? = null
}

@SwcDslMarker
public interface KeyValueProperty : PropBase {
  public var type: String? = null

  public var `value`: Expression? = null

  override var key: PropertyName? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(KeyValueProperty)
public class KeyValuePropertyImpl : KeyValuePropertyImpl() {
  override var type: String? = KeyValueProperty

  override var `value`: Expression? = null

  override var key: PropertyName? = null
}

@SwcDslMarker
public interface AssignmentProperty : Node {
  public var type: String? = null

  public var key: Identifier? = null

  public var `value`: Expression? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(AssignmentProperty)
public class AssignmentPropertyImpl : AssignmentPropertyImpl() {
  override var type: String? = AssignmentProperty

  override var key: Identifier? = null

  override var `value`: Expression? = null
}

@SwcDslMarker
public interface GetterProperty : PropBase, HasSpan {
  public var type: String? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  public var body: BlockStatement? = null

  override var key: PropertyName? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(GetterProperty)
public class GetterPropertyImpl : GetterPropertyImpl() {
  override var type: String? = GetterProperty

  override var typeAnnotation: TsTypeAnnotation? = null

  override var body: BlockStatement? = null

  override var key: PropertyName? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface SetterProperty : PropBase, HasSpan {
  public var type: String? = null

  public var `param`: Pattern? = null

  public var body: BlockStatement? = null

  override var key: PropertyName? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(SetterProperty)
public class SetterPropertyImpl : SetterPropertyImpl() {
  override var type: String? = SetterProperty

  override var `param`: Pattern? = null

  override var body: BlockStatement? = null

  override var key: PropertyName? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface MethodProperty : PropBase, Fn {
  public var type: String? = null

  override var key: PropertyName? = null

  override var params: Array<Param>? = null

  override var body: BlockStatement? = null

  override var generator: Boolean? = null

  override var async: Boolean? = null

  override var typeParameters: TsTypeParameterDeclaration? = null

  override var returnType: TsTypeAnnotation? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(MethodProperty)
public class MethodPropertyImpl : MethodPropertyImpl() {
  override var type: String? = MethodProperty

  override var key: PropertyName? = null

  override var params: Array<Param>? = null

  override var body: BlockStatement? = null

  override var generator: Boolean? = null

  override var async: Boolean? = null

  override var typeParameters: TsTypeParameterDeclaration? = null

  override var returnType: TsTypeAnnotation? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface ComputedPropName : Node, HasSpan {
  public var type: String? = null

  public var expression: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(Computed)
public class ComputedPropNameImpl : ComputedPropNameImpl() {
  override var type: String? = Computed

  override var expression: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface BlockStatement : Node, HasSpan {
  public var type: String? = null

  public var stmts: Array<Statement>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(BlockStatement)
public class BlockStatementImpl : BlockStatementImpl() {
  override var type: String? = BlockStatement

  override var stmts: Array<Statement>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ExpressionStatement : Node, HasSpan {
  public var type: String? = null

  public var expression: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ExpressionStatement)
public class ExpressionStatementImpl : ExpressionStatementImpl() {
  override var type: String? = ExpressionStatement

  override var expression: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface EmptyStatement : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(EmptyStatement)
public class EmptyStatementImpl : EmptyStatementImpl() {
  override var type: String? = EmptyStatement

  override var span: Span? = null
}

@SwcDslMarker
public interface DebuggerStatement : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(DebuggerStatement)
public class DebuggerStatementImpl : DebuggerStatementImpl() {
  override var type: String? = DebuggerStatement

  override var span: Span? = null
}

@SwcDslMarker
public interface WithStatement : Node, HasSpan {
  public var type: String? = null

  @SerialName("object")
  public var jsObject: Expression? = null

  public var body: Statement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(WithStatement)
public class WithStatementImpl : WithStatementImpl() {
  override var type: String? = WithStatement

  @SerialName("object")
  override var jsObject: Expression? = null

  override var body: Statement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ReturnStatement : Node, HasSpan {
  public var type: String? = null

  public var argument: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ReturnStatement)
public class ReturnStatementImpl : ReturnStatementImpl() {
  override var type: String? = ReturnStatement

  override var argument: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface LabeledStatement : Node, HasSpan {
  public var type: String? = null

  public var label: Identifier? = null

  public var body: Statement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(LabeledStatement)
public class LabeledStatementImpl : LabeledStatementImpl() {
  override var type: String? = LabeledStatement

  override var label: Identifier? = null

  override var body: Statement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface BreakStatement : Node, HasSpan {
  public var type: String? = null

  public var label: Identifier? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(BreakStatement)
public class BreakStatementImpl : BreakStatementImpl() {
  override var type: String? = BreakStatement

  override var label: Identifier? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ContinueStatement : Node, HasSpan {
  public var type: String? = null

  public var label: Identifier? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ContinueStatement)
public class ContinueStatementImpl : ContinueStatementImpl() {
  override var type: String? = ContinueStatement

  override var label: Identifier? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface IfStatement : Node, HasSpan {
  public var type: String? = null

  public var test: Expression? = null

  public var consequent: Statement? = null

  public var alternate: Statement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(IfStatement)
public class IfStatementImpl : IfStatementImpl() {
  override var type: String? = IfStatement

  override var test: Expression? = null

  override var consequent: Statement? = null

  override var alternate: Statement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface SwitchStatement : Node, HasSpan {
  public var type: String? = null

  public var discriminant: Expression? = null

  public var cases: Array<SwitchCase>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(SwitchStatement)
public class SwitchStatementImpl : SwitchStatementImpl() {
  override var type: String? = SwitchStatement

  override var discriminant: Expression? = null

  override var cases: Array<SwitchCase>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ThrowStatement : Node, HasSpan {
  public var type: String? = null

  public var argument: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ThrowStatement)
public class ThrowStatementImpl : ThrowStatementImpl() {
  override var type: String? = ThrowStatement

  override var argument: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TryStatement : Node, HasSpan {
  public var type: String? = null

  public var block: BlockStatement? = null

  public var handler: CatchClause? = null

  public var finalizer: BlockStatement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TryStatement)
public class TryStatementImpl : TryStatementImpl() {
  override var type: String? = TryStatement

  override var block: BlockStatement? = null

  override var handler: CatchClause? = null

  override var finalizer: BlockStatement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface WhileStatement : Node, HasSpan {
  public var type: String? = null

  public var test: Expression? = null

  public var body: Statement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(WhileStatement)
public class WhileStatementImpl : WhileStatementImpl() {
  override var type: String? = WhileStatement

  override var test: Expression? = null

  override var body: Statement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface DoWhileStatement : Node, HasSpan {
  public var type: String? = null

  public var test: Expression? = null

  public var body: Statement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(DoWhileStatement)
public class DoWhileStatementImpl : DoWhileStatementImpl() {
  override var type: String? = DoWhileStatement

  override var test: Expression? = null

  override var body: Statement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ForStatement : Node, HasSpan {
  public var type: String? = null

  public var `init`: Any? = null

  public var test: Expression? = null

  public var update: Expression? = null

  public var body: Statement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ForStatement)
public class ForStatementImpl : ForStatementImpl() {
  override var type: String? = ForStatement

  override var `init`: Any? = null

  override var test: Expression? = null

  override var update: Expression? = null

  override var body: Statement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ForInStatement : Node, HasSpan {
  public var type: String? = null

  public var left: Any? = null

  public var right: Expression? = null

  public var body: Statement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ForInStatement)
public class ForInStatementImpl : ForInStatementImpl() {
  override var type: String? = ForInStatement

  override var left: Any? = null

  override var right: Expression? = null

  override var body: Statement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface ForOfStatement : Node, HasSpan {
  public var type: String? = null

  public var await: Span? = null

  public var left: Any? = null

  public var right: Expression? = null

  public var body: Statement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(ForOfStatement)
public class ForOfStatementImpl : ForOfStatementImpl() {
  override var type: String? = ForOfStatement

  override var await: Span? = null

  override var left: Any? = null

  override var right: Expression? = null

  override var body: Statement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface SwitchCase : Node, HasSpan {
  public var type: String? = null

  public var test: Expression? = null

  public var consequent: Array<Statement>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(SwitchCase)
public class SwitchCaseImpl : SwitchCaseImpl() {
  override var type: String? = SwitchCase

  override var test: Expression? = null

  override var consequent: Array<Statement>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface CatchClause : Node, HasSpan {
  public var type: String? = null

  public var `param`: Pattern? = null

  public var body: BlockStatement? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(CatchClause)
public class CatchClauseImpl : CatchClauseImpl() {
  override var type: String? = CatchClause

  override var `param`: Pattern? = null

  override var body: BlockStatement? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeAnnotation : Node, HasSpan {
  public var type: String? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeAnnotation)
public class TsTypeAnnotationImpl : TsTypeAnnotationImpl() {
  override var type: String? = TsTypeAnnotation

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeParameterDeclaration : Node, HasSpan {
  public var type: String? = null

  public var parameters: Array<TsTypeParameter>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeParameterDeclaration)
public class TsTypeParameterDeclarationImpl : TsTypeParameterDeclarationImpl() {
  override var type: String? = TsTypeParameterDeclaration

  override var parameters: Array<TsTypeParameter>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeParameter : Node, HasSpan {
  public var type: String? = null

  public var name: Identifier? = null

  @SerialName("in")
  public var jsIn: Boolean? = null

  public var `out`: Boolean? = null

  public var constraint: TsType? = null

  public var default: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeParameter)
public class TsTypeParameterImpl : TsTypeParameterImpl() {
  override var type: String? = TsTypeParameter

  override var name: Identifier? = null

  @SerialName("in")
  override var jsIn: Boolean? = null

  override var `out`: Boolean? = null

  override var constraint: TsType? = null

  override var default: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeParameterInstantiation : Node, HasSpan {
  public var type: String? = null

  public var params: Array<TsType>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeParameterInstantiation)
public class TsTypeParameterInstantiationImpl : TsTypeParameterInstantiationImpl() {
  override var type: String? = TsTypeParameterInstantiation

  override var params: Array<TsType>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsParameterProperty : Node, HasSpan, HasDecorator {
  public var type: String? = null

  public var accessibility: String? = null

  public var `override`: Boolean? = null

  public var readonly: Boolean? = null

  public var `param`: TsParameterPropertyParameter? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsParameterProperty)
public class TsParameterPropertyImpl : TsParameterPropertyImpl() {
  override var type: String? = TsParameterProperty

  override var accessibility: String? = null

  override var `override`: Boolean? = null

  override var readonly: Boolean? = null

  override var `param`: TsParameterPropertyParameter? = null

  override var span: Span? = null

  override var decorators: Array<Decorator>? = null
}

@SwcDslMarker
public interface TsQualifiedName : Node {
  public var type: String? = null

  public var left: TsEntityName? = null

  public var right: Identifier? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsQualifiedName)
public class TsQualifiedNameImpl : TsQualifiedNameImpl() {
  override var type: String? = TsQualifiedName

  override var left: TsEntityName? = null

  override var right: Identifier? = null
}

@SwcDslMarker
public interface TsCallSignatureDeclaration : Node, HasSpan {
  public var type: String? = null

  public var params: Array<TsFnParameter>? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  public var typeParams: TsTypeParameterDeclaration? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsCallSignatureDeclaration)
public class TsCallSignatureDeclarationImpl : TsCallSignatureDeclarationImpl() {
  override var type: String? = TsCallSignatureDeclaration

  override var params: Array<TsFnParameter>? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsConstructSignatureDeclaration : Node, HasSpan {
  public var type: String? = null

  public var params: Array<TsFnParameter>? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  public var typeParams: TsTypeParameterDeclaration? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsConstructSignatureDeclaration)
public class TsConstructSignatureDeclarationImpl : TsConstructSignatureDeclarationImpl() {
  override var type: String? = TsConstructSignatureDeclaration

  override var params: Array<TsFnParameter>? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsPropertySignature : Node, HasSpan {
  public var type: String? = null

  public var readonly: Boolean? = null

  public var key: Expression? = null

  public var computed: Boolean? = null

  public var optional: Boolean? = null

  public var `init`: Expression? = null

  public var params: Array<TsFnParameter>? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  public var typeParams: TsTypeParameterDeclaration? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsPropertySignature)
public class TsPropertySignatureImpl : TsPropertySignatureImpl() {
  override var type: String? = TsPropertySignature

  override var readonly: Boolean? = null

  override var key: Expression? = null

  override var computed: Boolean? = null

  override var optional: Boolean? = null

  override var `init`: Expression? = null

  override var params: Array<TsFnParameter>? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsGetterSignature : Node, HasSpan {
  public var type: String? = null

  public var readonly: Boolean? = null

  public var key: Expression? = null

  public var computed: Boolean? = null

  public var optional: Boolean? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsGetterSignature)
public class TsGetterSignatureImpl : TsGetterSignatureImpl() {
  override var type: String? = TsGetterSignature

  override var readonly: Boolean? = null

  override var key: Expression? = null

  override var computed: Boolean? = null

  override var optional: Boolean? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsSetterSignature : Node, HasSpan {
  public var type: String? = null

  public var readonly: Boolean? = null

  public var key: Expression? = null

  public var computed: Boolean? = null

  public var optional: Boolean? = null

  public var `param`: TsFnParameter? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsSetterSignature)
public class TsSetterSignatureImpl : TsSetterSignatureImpl() {
  override var type: String? = TsSetterSignature

  override var readonly: Boolean? = null

  override var key: Expression? = null

  override var computed: Boolean? = null

  override var optional: Boolean? = null

  override var `param`: TsFnParameter? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsMethodSignature : Node, HasSpan {
  public var type: String? = null

  public var readonly: Boolean? = null

  public var key: Expression? = null

  public var computed: Boolean? = null

  public var optional: Boolean? = null

  public var params: Array<TsFnParameter>? = null

  public var typeAnn: TsTypeAnnotation? = null

  public var typeParams: TsTypeParameterDeclaration? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsMethodSignature)
public class TsMethodSignatureImpl : TsMethodSignatureImpl() {
  override var type: String? = TsMethodSignature

  override var readonly: Boolean? = null

  override var key: Expression? = null

  override var computed: Boolean? = null

  override var optional: Boolean? = null

  override var params: Array<TsFnParameter>? = null

  override var typeAnn: TsTypeAnnotation? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsIndexSignature : Node, HasSpan {
  public var type: String? = null

  public var params: Array<TsFnParameter>? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  public var readonly: Boolean? = null

  public var static: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsIndexSignature)
public class TsIndexSignatureImpl : TsIndexSignatureImpl() {
  override var type: String? = TsIndexSignature

  override var params: Array<TsFnParameter>? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var readonly: Boolean? = null

  override var static: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsKeywordType : Node, HasSpan {
  public var type: String? = null

  public var kind: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsKeywordType)
public class TsKeywordTypeImpl : TsKeywordTypeImpl() {
  override var type: String? = TsKeywordType

  override var kind: String? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsThisType : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsThisType)
public class TsThisTypeImpl : TsThisTypeImpl() {
  override var type: String? = TsThisType

  override var span: Span? = null
}

@SwcDslMarker
public interface TsFunctionType : Node, HasSpan {
  public var type: String? = null

  public var params: Array<TsFnParameter>? = null

  public var typeParams: TsTypeParameterDeclaration? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsFunctionType)
public class TsFunctionTypeImpl : TsFunctionTypeImpl() {
  override var type: String? = TsFunctionType

  override var params: Array<TsFnParameter>? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsConstructorType : Node, HasSpan {
  public var type: String? = null

  public var params: Array<TsFnParameter>? = null

  public var typeParams: TsTypeParameterDeclaration? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  public var isAbstract: Boolean? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsConstructorType)
public class TsConstructorTypeImpl : TsConstructorTypeImpl() {
  override var type: String? = TsConstructorType

  override var params: Array<TsFnParameter>? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var isAbstract: Boolean? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeReference : Node, HasSpan {
  public var type: String? = null

  public var typeName: TsEntityName? = null

  public var typeParams: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeReference)
public class TsTypeReferenceImpl : TsTypeReferenceImpl() {
  override var type: String? = TsTypeReference

  override var typeName: TsEntityName? = null

  override var typeParams: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypePredicate : Node, HasSpan {
  public var type: String? = null

  public var asserts: Boolean? = null

  public var paramName: TsThisTypeOrIdent? = null

  public var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypePredicate)
public class TsTypePredicateImpl : TsTypePredicateImpl() {
  override var type: String? = TsTypePredicate

  override var asserts: Boolean? = null

  override var paramName: TsThisTypeOrIdent? = null

  override var typeAnnotation: TsTypeAnnotation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsImportType : Node, HasSpan {
  public var type: String? = null

  public var argument: StringLiteral? = null

  public var qualifier: TsEntityName? = null

  public var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsImportType)
public class TsImportTypeImpl : TsImportTypeImpl() {
  override var type: String? = TsImportType

  override var argument: StringLiteral? = null

  override var qualifier: TsEntityName? = null

  override var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeQuery : Node, HasSpan {
  public var type: String? = null

  public var exprName: TsTypeQueryExpr? = null

  public var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeQuery)
public class TsTypeQueryImpl : TsTypeQueryImpl() {
  override var type: String? = TsTypeQuery

  override var exprName: TsTypeQueryExpr? = null

  override var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeLiteral : Node, HasSpan {
  public var type: String? = null

  public var members: Array<TsTypeElement>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeLiteral)
public class TsTypeLiteralImpl : TsTypeLiteralImpl() {
  override var type: String? = TsTypeLiteral

  override var members: Array<TsTypeElement>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsArrayType : Node, HasSpan {
  public var type: String? = null

  public var elemType: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsArrayType)
public class TsArrayTypeImpl : TsArrayTypeImpl() {
  override var type: String? = TsArrayType

  override var elemType: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTupleType : Node, HasSpan {
  public var type: String? = null

  public var elemTypes: Array<TsTupleElement>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTupleType)
public class TsTupleTypeImpl : TsTupleTypeImpl() {
  override var type: String? = TsTupleType

  override var elemTypes: Array<TsTupleElement>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTupleElement : Node, HasSpan {
  public var type: String? = null

  public var label: Pattern? = null

  public var ty: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTupleElement)
public class TsTupleElementImpl : TsTupleElementImpl() {
  override var type: String? = TsTupleElement

  override var label: Pattern? = null

  override var ty: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsOptionalType : Node, HasSpan {
  public var type: String? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsOptionalType)
public class TsOptionalTypeImpl : TsOptionalTypeImpl() {
  override var type: String? = TsOptionalType

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsRestType : Node, HasSpan {
  public var type: String? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsRestType)
public class TsRestTypeImpl : TsRestTypeImpl() {
  override var type: String? = TsRestType

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsUnionType : Node, HasSpan {
  public var type: String? = null

  public var types: Array<TsType>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsUnionType)
public class TsUnionTypeImpl : TsUnionTypeImpl() {
  override var type: String? = TsUnionType

  override var types: Array<TsType>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsIntersectionType : Node, HasSpan {
  public var type: String? = null

  public var types: Array<TsType>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsIntersectionType)
public class TsIntersectionTypeImpl : TsIntersectionTypeImpl() {
  override var type: String? = TsIntersectionType

  override var types: Array<TsType>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsConditionalType : Node, HasSpan {
  public var type: String? = null

  public var checkType: TsType? = null

  public var extendsType: TsType? = null

  public var trueType: TsType? = null

  public var falseType: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsConditionalType)
public class TsConditionalTypeImpl : TsConditionalTypeImpl() {
  override var type: String? = TsConditionalType

  override var checkType: TsType? = null

  override var extendsType: TsType? = null

  override var trueType: TsType? = null

  override var falseType: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsInferType : Node, HasSpan {
  public var type: String? = null

  public var typeParam: TsTypeParameter? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsInferType)
public class TsInferTypeImpl : TsInferTypeImpl() {
  override var type: String? = TsInferType

  override var typeParam: TsTypeParameter? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsParenthesizedType : Node, HasSpan {
  public var type: String? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsParenthesizedType)
public class TsParenthesizedTypeImpl : TsParenthesizedTypeImpl() {
  override var type: String? = TsParenthesizedType

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeOperator : Node, HasSpan {
  public var type: String? = null

  public var op: String? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeOperator)
public class TsTypeOperatorImpl : TsTypeOperatorImpl() {
  override var type: String? = TsTypeOperator

  override var op: String? = null

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsIndexedAccessType : Node, HasSpan {
  public var type: String? = null

  public var readonly: Boolean? = null

  public var objectType: TsType? = null

  public var indexType: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsIndexedAccessType)
public class TsIndexedAccessTypeImpl : TsIndexedAccessTypeImpl() {
  override var type: String? = TsIndexedAccessType

  override var readonly: Boolean? = null

  override var objectType: TsType? = null

  override var indexType: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsMappedType : Node, HasSpan {
  public var type: String? = null

  public var readonly: TruePlusMinus? = null

  public var typeParam: TsTypeParameter? = null

  public var nameType: TsType? = null

  public var optional: TruePlusMinus? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsMappedType)
public class TsMappedTypeImpl : TsMappedTypeImpl() {
  override var type: String? = TsMappedType

  override var readonly: TruePlusMinus? = null

  override var typeParam: TsTypeParameter? = null

  override var nameType: TsType? = null

  override var optional: TruePlusMinus? = null

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsLiteralType : Node, HasSpan {
  public var type: String? = null

  public var literal: TsLiteral? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsLiteralType)
public class TsLiteralTypeImpl : TsLiteralTypeImpl() {
  override var type: String? = TsLiteralType

  override var literal: TsLiteral? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsInterfaceDeclaration : Node, HasSpan {
  public var type: String? = null

  public var id: Identifier? = null

  public var declare: Boolean? = null

  public var typeParams: TsTypeParameterDeclaration? = null

  public var extends: Array<TsExpressionWithTypeArguments>? = null

  public var body: TsInterfaceBody? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsInterfaceDeclaration)
public class TsInterfaceDeclarationImpl : TsInterfaceDeclarationImpl() {
  override var type: String? = TsInterfaceDeclaration

  override var id: Identifier? = null

  override var declare: Boolean? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var extends: Array<TsExpressionWithTypeArguments>? = null

  override var body: TsInterfaceBody? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsInterfaceBody : Node, HasSpan {
  public var type: String? = null

  public var body: Array<TsTypeElement>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsInterfaceBody)
public class TsInterfaceBodyImpl : TsInterfaceBodyImpl() {
  override var type: String? = TsInterfaceBody

  override var body: Array<TsTypeElement>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsExpressionWithTypeArguments : Node, HasSpan {
  public var type: String? = null

  public var expression: Expression? = null

  public var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsExpressionWithTypeArguments)
public class TsExpressionWithTypeArgumentsImpl : TsExpressionWithTypeArgumentsImpl() {
  override var type: String? = TsExpressionWithTypeArguments

  override var expression: Expression? = null

  override var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeAliasDeclaration : Node, HasSpan {
  public var type: String? = null

  public var declare: Boolean? = null

  public var id: Identifier? = null

  public var typeParams: TsTypeParameterDeclaration? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeAliasDeclaration)
public class TsTypeAliasDeclarationImpl : TsTypeAliasDeclarationImpl() {
  override var type: String? = TsTypeAliasDeclaration

  override var declare: Boolean? = null

  override var id: Identifier? = null

  override var typeParams: TsTypeParameterDeclaration? = null

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsEnumDeclaration : Node, HasSpan {
  public var type: String? = null

  public var declare: Boolean? = null

  public var isConst: Boolean? = null

  public var id: Identifier? = null

  public var members: Array<TsEnumMember>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsEnumDeclaration)
public class TsEnumDeclarationImpl : TsEnumDeclarationImpl() {
  override var type: String? = TsEnumDeclaration

  override var declare: Boolean? = null

  override var isConst: Boolean? = null

  override var id: Identifier? = null

  override var members: Array<TsEnumMember>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsEnumMember : Node, HasSpan {
  public var type: String? = null

  public var id: TsEnumMemberId? = null

  public var `init`: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsEnumMember)
public class TsEnumMemberImpl : TsEnumMemberImpl() {
  override var type: String? = TsEnumMember

  override var id: TsEnumMemberId? = null

  override var `init`: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsModuleDeclaration : Node, HasSpan {
  public var type: String? = null

  public var declare: Boolean? = null

  public var global: Boolean? = null

  public var id: TsModuleName? = null

  public var body: TsNamespaceBody? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsModuleDeclaration)
public class TsModuleDeclarationImpl : TsModuleDeclarationImpl() {
  override var type: String? = TsModuleDeclaration

  override var declare: Boolean? = null

  override var global: Boolean? = null

  override var id: TsModuleName? = null

  override var body: TsNamespaceBody? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsModuleBlock : Node, HasSpan {
  public var type: String? = null

  public var body: Array<ModuleItem>? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsModuleBlock)
public class TsModuleBlockImpl : TsModuleBlockImpl() {
  override var type: String? = TsModuleBlock

  override var body: Array<ModuleItem>? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsNamespaceDeclaration : Node, HasSpan {
  public var type: String? = null

  public var declare: Boolean? = null

  public var global: Boolean? = null

  public var id: Identifier? = null

  public var body: TsNamespaceBody? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsNamespaceDeclaration)
public class TsNamespaceDeclarationImpl : TsNamespaceDeclarationImpl() {
  override var type: String? = TsNamespaceDeclaration

  override var declare: Boolean? = null

  override var global: Boolean? = null

  override var id: Identifier? = null

  override var body: TsNamespaceBody? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsImportEqualsDeclaration : Node, HasSpan {
  public var type: String? = null

  public var declare: Boolean? = null

  public var isExport: Boolean? = null

  public var isTypeOnly: Boolean? = null

  public var id: Identifier? = null

  public var moduleRef: TsModuleReference? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsImportEqualsDeclaration)
public class TsImportEqualsDeclarationImpl : TsImportEqualsDeclarationImpl() {
  override var type: String? = TsImportEqualsDeclaration

  override var declare: Boolean? = null

  override var isExport: Boolean? = null

  override var isTypeOnly: Boolean? = null

  override var id: Identifier? = null

  override var moduleRef: TsModuleReference? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsExternalModuleReference : Node, HasSpan {
  public var type: String? = null

  public var expression: StringLiteral? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsExternalModuleReference)
public class TsExternalModuleReferenceImpl : TsExternalModuleReferenceImpl() {
  override var type: String? = TsExternalModuleReference

  override var expression: StringLiteral? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsExportAssignment : Node, HasSpan {
  public var type: String? = null

  public var expression: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsExportAssignment)
public class TsExportAssignmentImpl : TsExportAssignmentImpl() {
  override var type: String? = TsExportAssignment

  override var expression: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsNamespaceExportDeclaration : Node, HasSpan {
  public var type: String? = null

  public var id: Identifier? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsNamespaceExportDeclaration)
public class TsNamespaceExportDeclarationImpl : TsNamespaceExportDeclarationImpl() {
  override var type: String? = TsNamespaceExportDeclaration

  override var id: Identifier? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsAsExpression : ExpressionBase {
  public var type: String? = null

  public var expression: Expression? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsAsExpression)
public class TsAsExpressionImpl : TsAsExpressionImpl() {
  override var type: String? = TsAsExpression

  override var expression: Expression? = null

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsSatisfiesExpression : ExpressionBase {
  public var type: String? = null

  public var expression: Expression? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsSatisfiesExpression)
public class TsSatisfiesExpressionImpl : TsSatisfiesExpressionImpl() {
  override var type: String? = TsSatisfiesExpression

  override var expression: Expression? = null

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsInstantiation : Node, HasSpan {
  public var type: String? = null

  public var expression: Expression? = null

  public var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsInstantiation)
public class TsInstantiationImpl : TsInstantiationImpl() {
  override var type: String? = TsInstantiation

  override var expression: Expression? = null

  override var typeArguments: TsTypeParameterInstantiation? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsTypeAssertion : ExpressionBase {
  public var type: String? = null

  public var expression: Expression? = null

  public var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsTypeAssertion)
public class TsTypeAssertionImpl : TsTypeAssertionImpl() {
  override var type: String? = TsTypeAssertion

  override var expression: Expression? = null

  override var typeAnnotation: TsType? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsConstAssertion : ExpressionBase {
  public var type: String? = null

  public var expression: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsConstAssertion)
public class TsConstAssertionImpl : TsConstAssertionImpl() {
  override var type: String? = TsConstAssertion

  override var expression: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface TsNonNullExpression : ExpressionBase {
  public var type: String? = null

  public var expression: Expression? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(TsNonNullExpression)
public class TsNonNullExpressionImpl : TsNonNullExpressionImpl() {
  override var type: String? = TsNonNullExpression

  override var expression: Expression? = null

  override var span: Span? = null
}

@SwcDslMarker
public interface Invalid : Node, HasSpan {
  public var type: String? = null

  override var span: Span? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName(Invalid)
public class InvalidImpl : InvalidImpl() {
  override var type: String? = Invalid

  override var span: Span? = null
}

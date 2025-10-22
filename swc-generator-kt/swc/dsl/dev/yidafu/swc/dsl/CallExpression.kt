package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.Argument
import dev.yidafu.swc.types.ArgumentImpl
import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrowFunctionExpression
import dev.yidafu.swc.types.ArrowFunctionExpressionImpl
import dev.yidafu.swc.types.AssignmentExpression
import dev.yidafu.swc.types.AssignmentExpressionImpl
import dev.yidafu.swc.types.AwaitExpression
import dev.yidafu.swc.types.AwaitExpressionImpl
import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BinaryExpression
import dev.yidafu.swc.types.BinaryExpressionImpl
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.CallExpression
import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
import dev.yidafu.swc.types.ConditionalExpression
import dev.yidafu.swc.types.ConditionalExpressionImpl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.Import
import dev.yidafu.swc.types.ImportImpl
import dev.yidafu.swc.types.Invalid
import dev.yidafu.swc.types.InvalidImpl
import dev.yidafu.swc.types.JSXElement
import dev.yidafu.swc.types.JSXElementImpl
import dev.yidafu.swc.types.JSXEmptyExpression
import dev.yidafu.swc.types.JSXEmptyExpressionImpl
import dev.yidafu.swc.types.JSXFragment
import dev.yidafu.swc.types.JSXFragmentImpl
import dev.yidafu.swc.types.JSXMemberExpression
import dev.yidafu.swc.types.JSXMemberExpressionImpl
import dev.yidafu.swc.types.JSXNamespacedName
import dev.yidafu.swc.types.JSXNamespacedNameImpl
import dev.yidafu.swc.types.JSXText
import dev.yidafu.swc.types.JSXTextImpl
import dev.yidafu.swc.types.MemberExpression
import dev.yidafu.swc.types.MemberExpressionImpl
import dev.yidafu.swc.types.MetaProperty
import dev.yidafu.swc.types.MetaPropertyImpl
import dev.yidafu.swc.types.NewExpression
import dev.yidafu.swc.types.NewExpressionImpl
import dev.yidafu.swc.types.NullLiteral
import dev.yidafu.swc.types.NullLiteralImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.ObjectExpression
import dev.yidafu.swc.types.ObjectExpressionImpl
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.OptionalChainingExpressionImpl
import dev.yidafu.swc.types.ParenthesisExpression
import dev.yidafu.swc.types.ParenthesisExpressionImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
import dev.yidafu.swc.types.SequenceExpression
import dev.yidafu.swc.types.SequenceExpressionImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.Super
import dev.yidafu.swc.types.SuperImpl
import dev.yidafu.swc.types.SuperPropExpression
import dev.yidafu.swc.types.SuperPropExpressionImpl
import dev.yidafu.swc.types.TaggedTemplateExpression
import dev.yidafu.swc.types.TaggedTemplateExpressionImpl
import dev.yidafu.swc.types.TemplateLiteral
import dev.yidafu.swc.types.TemplateLiteralImpl
import dev.yidafu.swc.types.ThisExpression
import dev.yidafu.swc.types.ThisExpressionImpl
import dev.yidafu.swc.types.TsAsExpression
import dev.yidafu.swc.types.TsAsExpressionImpl
import dev.yidafu.swc.types.TsConstAssertion
import dev.yidafu.swc.types.TsConstAssertionImpl
import dev.yidafu.swc.types.TsInstantiation
import dev.yidafu.swc.types.TsInstantiationImpl
import dev.yidafu.swc.types.TsNonNullExpression
import dev.yidafu.swc.types.TsNonNullExpressionImpl
import dev.yidafu.swc.types.TsSatisfiesExpression
import dev.yidafu.swc.types.TsSatisfiesExpressionImpl
import dev.yidafu.swc.types.TsTypeAssertion
import dev.yidafu.swc.types.TsTypeAssertionImpl
import dev.yidafu.swc.types.TsTypeParameterInstantiation
import dev.yidafu.swc.types.TsTypeParameterInstantiationImpl
import dev.yidafu.swc.types.UnaryExpression
import dev.yidafu.swc.types.UnaryExpressionImpl
import dev.yidafu.swc.types.UpdateExpression
import dev.yidafu.swc.types.UpdateExpressionImpl
import dev.yidafu.swc.types.YieldExpression
import dev.yidafu.swc.types.YieldExpressionImpl
import kotlin.Unit

/**
 * CallExpression#type: String
 * extension function for create String -> String
 */
public fun CallExpression.string(block: String.() -> Unit): String = String().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> SuperImpl
 */
public fun CallExpression.jsSuper(block: Super.() -> Unit): Super = SuperImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> ImportImpl
 */
public fun CallExpression.`import`(block: Import.() -> Unit): Import = ImportImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> ThisExpressionImpl
 */
public fun CallExpression.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> ArrayExpressionImpl
 */
public fun CallExpression.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> ObjectExpressionImpl
 */
public fun CallExpression.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> FunctionExpressionImpl
 */
public fun CallExpression.functionExpression(block: FunctionExpression.() -> Unit):
    FunctionExpression = FunctionExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> UnaryExpressionImpl
 */
public fun CallExpression.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> UpdateExpressionImpl
 */
public fun CallExpression.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> BinaryExpressionImpl
 */
public fun CallExpression.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> AssignmentExpressionImpl
 */
public fun CallExpression.assignmentExpression(block: AssignmentExpression.() -> Unit):
    AssignmentExpression = AssignmentExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> MemberExpressionImpl
 */
public fun CallExpression.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> SuperPropExpressionImpl
 */
public fun CallExpression.superPropExpression(block: SuperPropExpression.() -> Unit):
    SuperPropExpression = SuperPropExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> ConditionalExpressionImpl
 */
public fun CallExpression.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> NewExpressionImpl
 */
public fun CallExpression.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> SequenceExpressionImpl
 */
public fun CallExpression.sequenceExpression(block: SequenceExpression.() -> Unit):
    SequenceExpression = SequenceExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> IdentifierImpl
 */
public fun CallExpression.identifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> StringLiteralImpl
 */
public fun CallExpression.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> BooleanLiteralImpl
 */
public fun CallExpression.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> NullLiteralImpl
 */
public fun CallExpression.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> NumericLiteralImpl
 */
public fun CallExpression.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> BigIntLiteralImpl
 */
public fun CallExpression.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> RegExpLiteralImpl
 */
public fun CallExpression.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> JSXTextImpl
 */
public fun CallExpression.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> TemplateLiteralImpl
 */
public fun CallExpression.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> TaggedTemplateExpressionImpl
 */
public fun CallExpression.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> ArrowFunctionExpressionImpl
 */
public fun CallExpression.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> ClassExpressionImpl
 */
public fun CallExpression.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> YieldExpressionImpl
 */
public fun CallExpression.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> MetaPropertyImpl
 */
public fun CallExpression.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> AwaitExpressionImpl
 */
public fun CallExpression.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> ParenthesisExpressionImpl
 */
public fun CallExpression.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> JSXMemberExpressionImpl
 */
public fun CallExpression.jSXMemberExpression(block: JSXMemberExpression.() -> Unit):
    JSXMemberExpression = JSXMemberExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> JSXNamespacedNameImpl
 */
public fun CallExpression.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName
    = JSXNamespacedNameImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> JSXEmptyExpressionImpl
 */
public fun CallExpression.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit):
    JSXEmptyExpression = JSXEmptyExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> JSXElementImpl
 */
public fun CallExpression.jSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> JSXFragmentImpl
 */
public fun CallExpression.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> TsTypeAssertionImpl
 */
public fun CallExpression.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> TsConstAssertionImpl
 */
public fun CallExpression.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> TsNonNullExpressionImpl
 */
public fun CallExpression.tsNonNullExpression(block: TsNonNullExpression.() -> Unit):
    TsNonNullExpression = TsNonNullExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> TsAsExpressionImpl
 */
public fun CallExpression.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> TsSatisfiesExpressionImpl
 */
public fun CallExpression.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> TsInstantiationImpl
 */
public fun CallExpression.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> PrivateNameImpl
 */
public fun CallExpression.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> ->
 * OptionalChainingExpressionImpl
 */
public fun CallExpression.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * CallExpression#callee: Union.U3<Super, Import, Expression>
 * extension function for create Union.U3<Super, Import, Expression> -> InvalidImpl
 */
public fun CallExpression.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * CallExpression#arguments: Array<Argument>
 * extension function for create Array<Argument> -> ArgumentImpl
 */
public fun CallExpression.argument(block: Argument.() -> Unit): Argument =
    ArgumentImpl().apply(block)

/**
 * CallExpression#typeArguments: TsTypeParameterInstantiation
 * extension function for create TsTypeParameterInstantiation -> TsTypeParameterInstantiationImpl
 */
public
    fun CallExpression.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * CallExpression#span: Span
 * extension function for create Span -> SpanImpl
 */
public fun CallExpression.span(block: Span.() -> Unit): Span = SpanImpl().apply(block)

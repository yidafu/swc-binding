package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayExpression
import dev.yidafu.swc.types.ArrayExpressionImpl
import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
import dev.yidafu.swc.types.ArrowFunctionExpression
import dev.yidafu.swc.types.ArrowFunctionExpressionImpl
import dev.yidafu.swc.types.AssignmentExpression
import dev.yidafu.swc.types.AssignmentExpressionImpl
import dev.yidafu.swc.types.AssignmentPattern
import dev.yidafu.swc.types.AssignmentPatternImpl
import dev.yidafu.swc.types.AssignmentPatternProperty
import dev.yidafu.swc.types.AssignmentPatternPropertyImpl
import dev.yidafu.swc.types.AssignmentProperty
import dev.yidafu.swc.types.AssignmentPropertyImpl
import dev.yidafu.swc.types.AwaitExpression
import dev.yidafu.swc.types.AwaitExpressionImpl
import dev.yidafu.swc.types.BigIntLiteral
import dev.yidafu.swc.types.BigIntLiteralImpl
import dev.yidafu.swc.types.BinaryExpression
import dev.yidafu.swc.types.BinaryExpressionImpl
import dev.yidafu.swc.types.BindingIdentifier
import dev.yidafu.swc.types.BindingIdentifierImpl
import dev.yidafu.swc.types.BlockStatement
import dev.yidafu.swc.types.BlockStatementImpl
import dev.yidafu.swc.types.BooleanLiteral
import dev.yidafu.swc.types.BooleanLiteralImpl
import dev.yidafu.swc.types.BreakStatement
import dev.yidafu.swc.types.BreakStatementImpl
import dev.yidafu.swc.types.CallExpression
import dev.yidafu.swc.types.CallExpressionImpl
import dev.yidafu.swc.types.CatchClause
import dev.yidafu.swc.types.CatchClauseImpl
import dev.yidafu.swc.types.ClassDeclaration
import dev.yidafu.swc.types.ClassDeclarationImpl
import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
import dev.yidafu.swc.types.ClassMethod
import dev.yidafu.swc.types.ClassMethodImpl
import dev.yidafu.swc.types.ClassProperty
import dev.yidafu.swc.types.ClassPropertyImpl
import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
import dev.yidafu.swc.types.ConditionalExpression
import dev.yidafu.swc.types.ConditionalExpressionImpl
import dev.yidafu.swc.types.Constructor
import dev.yidafu.swc.types.ConstructorImpl
import dev.yidafu.swc.types.ContinueStatement
import dev.yidafu.swc.types.ContinueStatementImpl
import dev.yidafu.swc.types.DebuggerStatement
import dev.yidafu.swc.types.DebuggerStatementImpl
import dev.yidafu.swc.types.Decorator
import dev.yidafu.swc.types.DecoratorImpl
import dev.yidafu.swc.types.DoWhileStatement
import dev.yidafu.swc.types.DoWhileStatementImpl
import dev.yidafu.swc.types.EmptyStatement
import dev.yidafu.swc.types.EmptyStatementImpl
import dev.yidafu.swc.types.ExportAllDeclaration
import dev.yidafu.swc.types.ExportAllDeclarationImpl
import dev.yidafu.swc.types.ExportDeclaration
import dev.yidafu.swc.types.ExportDeclarationImpl
import dev.yidafu.swc.types.ExportDefaultDeclaration
import dev.yidafu.swc.types.ExportDefaultDeclarationImpl
import dev.yidafu.swc.types.ExportDefaultExpression
import dev.yidafu.swc.types.ExportDefaultExpressionImpl
import dev.yidafu.swc.types.ExportDefaultSpecifier
import dev.yidafu.swc.types.ExportDefaultSpecifierImpl
import dev.yidafu.swc.types.ExportNamedDeclaration
import dev.yidafu.swc.types.ExportNamedDeclarationImpl
import dev.yidafu.swc.types.ExportNamespaceSpecifier
import dev.yidafu.swc.types.ExportNamespaceSpecifierImpl
import dev.yidafu.swc.types.ExpressionStatement
import dev.yidafu.swc.types.ExpressionStatementImpl
import dev.yidafu.swc.types.ForInStatement
import dev.yidafu.swc.types.ForInStatementImpl
import dev.yidafu.swc.types.ForOfStatement
import dev.yidafu.swc.types.ForOfStatementImpl
import dev.yidafu.swc.types.ForStatement
import dev.yidafu.swc.types.ForStatementImpl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.GetterProperty
import dev.yidafu.swc.types.GetterPropertyImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.IfStatement
import dev.yidafu.swc.types.IfStatementImpl
import dev.yidafu.swc.types.Import
import dev.yidafu.swc.types.ImportDeclaration
import dev.yidafu.swc.types.ImportDeclarationImpl
import dev.yidafu.swc.types.ImportDefaultSpecifier
import dev.yidafu.swc.types.ImportDefaultSpecifierImpl
import dev.yidafu.swc.types.ImportImpl
import dev.yidafu.swc.types.ImportNamespaceSpecifier
import dev.yidafu.swc.types.ImportNamespaceSpecifierImpl
import dev.yidafu.swc.types.Invalid
import dev.yidafu.swc.types.InvalidImpl
import dev.yidafu.swc.types.JSXAttribute
import dev.yidafu.swc.types.JSXAttributeImpl
import dev.yidafu.swc.types.JSXClosingElement
import dev.yidafu.swc.types.JSXClosingElementImpl
import dev.yidafu.swc.types.JSXClosingFragment
import dev.yidafu.swc.types.JSXClosingFragmentImpl
import dev.yidafu.swc.types.JSXElement
import dev.yidafu.swc.types.JSXElementImpl
import dev.yidafu.swc.types.JSXEmptyExpression
import dev.yidafu.swc.types.JSXEmptyExpressionImpl
import dev.yidafu.swc.types.JSXExpressionContainer
import dev.yidafu.swc.types.JSXExpressionContainerImpl
import dev.yidafu.swc.types.JSXFragment
import dev.yidafu.swc.types.JSXFragmentImpl
import dev.yidafu.swc.types.JSXMemberExpression
import dev.yidafu.swc.types.JSXMemberExpressionImpl
import dev.yidafu.swc.types.JSXNamespacedName
import dev.yidafu.swc.types.JSXNamespacedNameImpl
import dev.yidafu.swc.types.JSXOpeningElement
import dev.yidafu.swc.types.JSXOpeningElementImpl
import dev.yidafu.swc.types.JSXOpeningFragment
import dev.yidafu.swc.types.JSXOpeningFragmentImpl
import dev.yidafu.swc.types.JSXSpreadChild
import dev.yidafu.swc.types.JSXSpreadChildImpl
import dev.yidafu.swc.types.JSXText
import dev.yidafu.swc.types.JSXTextImpl
import dev.yidafu.swc.types.KeyValuePatternProperty
import dev.yidafu.swc.types.KeyValuePatternPropertyImpl
import dev.yidafu.swc.types.KeyValueProperty
import dev.yidafu.swc.types.KeyValuePropertyImpl
import dev.yidafu.swc.types.LabeledStatement
import dev.yidafu.swc.types.LabeledStatementImpl
import dev.yidafu.swc.types.MemberExpression
import dev.yidafu.swc.types.MemberExpressionImpl
import dev.yidafu.swc.types.MetaProperty
import dev.yidafu.swc.types.MetaPropertyImpl
import dev.yidafu.swc.types.MethodProperty
import dev.yidafu.swc.types.MethodPropertyImpl
import dev.yidafu.swc.types.Module
import dev.yidafu.swc.types.ModuleImpl
import dev.yidafu.swc.types.NamedExportSpecifier
import dev.yidafu.swc.types.NamedExportSpecifierImpl
import dev.yidafu.swc.types.NamedImportSpecifier
import dev.yidafu.swc.types.NamedImportSpecifierImpl
import dev.yidafu.swc.types.NewExpression
import dev.yidafu.swc.types.NewExpressionImpl
import dev.yidafu.swc.types.Node
import dev.yidafu.swc.types.NullLiteral
import dev.yidafu.swc.types.NullLiteralImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.ObjectExpression
import dev.yidafu.swc.types.ObjectExpressionImpl
import dev.yidafu.swc.types.ObjectPattern
import dev.yidafu.swc.types.ObjectPatternImpl
import dev.yidafu.swc.types.OptionalChainingCall
import dev.yidafu.swc.types.OptionalChainingCallImpl
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.OptionalChainingExpressionImpl
import dev.yidafu.swc.types.Param
import dev.yidafu.swc.types.ParamImpl
import dev.yidafu.swc.types.ParenthesisExpression
import dev.yidafu.swc.types.ParenthesisExpressionImpl
import dev.yidafu.swc.types.PrivateMethod
import dev.yidafu.swc.types.PrivateMethodImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.PrivateProperty
import dev.yidafu.swc.types.PrivatePropertyImpl
import dev.yidafu.swc.types.RegExpLiteral
import dev.yidafu.swc.types.RegExpLiteralImpl
import dev.yidafu.swc.types.RestElement
import dev.yidafu.swc.types.RestElementImpl
import dev.yidafu.swc.types.ReturnStatement
import dev.yidafu.swc.types.ReturnStatementImpl
import dev.yidafu.swc.types.Script
import dev.yidafu.swc.types.ScriptImpl
import dev.yidafu.swc.types.SequenceExpression
import dev.yidafu.swc.types.SequenceExpressionImpl
import dev.yidafu.swc.types.SetterProperty
import dev.yidafu.swc.types.SetterPropertyImpl
import dev.yidafu.swc.types.SpreadElement
import dev.yidafu.swc.types.SpreadElementImpl
import dev.yidafu.swc.types.StaticBlock
import dev.yidafu.swc.types.StaticBlockImpl
import dev.yidafu.swc.types.String
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import dev.yidafu.swc.types.Super
import dev.yidafu.swc.types.SuperImpl
import dev.yidafu.swc.types.SuperPropExpression
import dev.yidafu.swc.types.SuperPropExpressionImpl
import dev.yidafu.swc.types.SwitchCase
import dev.yidafu.swc.types.SwitchCaseImpl
import dev.yidafu.swc.types.SwitchStatement
import dev.yidafu.swc.types.SwitchStatementImpl
import dev.yidafu.swc.types.TaggedTemplateExpression
import dev.yidafu.swc.types.TaggedTemplateExpressionImpl
import dev.yidafu.swc.types.TemplateElement
import dev.yidafu.swc.types.TemplateElementImpl
import dev.yidafu.swc.types.TemplateLiteral
import dev.yidafu.swc.types.TemplateLiteralImpl
import dev.yidafu.swc.types.ThisExpression
import dev.yidafu.swc.types.ThisExpressionImpl
import dev.yidafu.swc.types.ThrowStatement
import dev.yidafu.swc.types.ThrowStatementImpl
import dev.yidafu.swc.types.TryStatement
import dev.yidafu.swc.types.TryStatementImpl
import dev.yidafu.swc.types.TsArrayType
import dev.yidafu.swc.types.TsArrayTypeImpl
import dev.yidafu.swc.types.TsAsExpression
import dev.yidafu.swc.types.TsAsExpressionImpl
import dev.yidafu.swc.types.TsCallSignatureDeclaration
import dev.yidafu.swc.types.TsCallSignatureDeclarationImpl
import dev.yidafu.swc.types.TsConditionalType
import dev.yidafu.swc.types.TsConditionalTypeImpl
import dev.yidafu.swc.types.TsConstAssertion
import dev.yidafu.swc.types.TsConstAssertionImpl
import dev.yidafu.swc.types.TsConstructSignatureDeclaration
import dev.yidafu.swc.types.TsConstructSignatureDeclarationImpl
import dev.yidafu.swc.types.TsConstructorType
import dev.yidafu.swc.types.TsConstructorTypeImpl
import dev.yidafu.swc.types.TsEnumDeclaration
import dev.yidafu.swc.types.TsEnumDeclarationImpl
import dev.yidafu.swc.types.TsEnumMember
import dev.yidafu.swc.types.TsEnumMemberImpl
import dev.yidafu.swc.types.TsExportAssignment
import dev.yidafu.swc.types.TsExportAssignmentImpl
import dev.yidafu.swc.types.TsExpressionWithTypeArguments
import dev.yidafu.swc.types.TsExpressionWithTypeArgumentsImpl
import dev.yidafu.swc.types.TsExternalModuleReference
import dev.yidafu.swc.types.TsExternalModuleReferenceImpl
import dev.yidafu.swc.types.TsFunctionType
import dev.yidafu.swc.types.TsFunctionTypeImpl
import dev.yidafu.swc.types.TsGetterSignature
import dev.yidafu.swc.types.TsGetterSignatureImpl
import dev.yidafu.swc.types.TsImportEqualsDeclaration
import dev.yidafu.swc.types.TsImportEqualsDeclarationImpl
import dev.yidafu.swc.types.TsImportType
import dev.yidafu.swc.types.TsImportTypeImpl
import dev.yidafu.swc.types.TsIndexSignature
import dev.yidafu.swc.types.TsIndexSignatureImpl
import dev.yidafu.swc.types.TsIndexedAccessType
import dev.yidafu.swc.types.TsIndexedAccessTypeImpl
import dev.yidafu.swc.types.TsInferType
import dev.yidafu.swc.types.TsInferTypeImpl
import dev.yidafu.swc.types.TsInstantiation
import dev.yidafu.swc.types.TsInstantiationImpl
import dev.yidafu.swc.types.TsInterfaceBody
import dev.yidafu.swc.types.TsInterfaceBodyImpl
import dev.yidafu.swc.types.TsInterfaceDeclaration
import dev.yidafu.swc.types.TsInterfaceDeclarationImpl
import dev.yidafu.swc.types.TsIntersectionType
import dev.yidafu.swc.types.TsIntersectionTypeImpl
import dev.yidafu.swc.types.TsKeywordType
import dev.yidafu.swc.types.TsKeywordTypeImpl
import dev.yidafu.swc.types.TsLiteralType
import dev.yidafu.swc.types.TsLiteralTypeImpl
import dev.yidafu.swc.types.TsMappedType
import dev.yidafu.swc.types.TsMappedTypeImpl
import dev.yidafu.swc.types.TsMethodSignature
import dev.yidafu.swc.types.TsMethodSignatureImpl
import dev.yidafu.swc.types.TsModuleBlock
import dev.yidafu.swc.types.TsModuleBlockImpl
import dev.yidafu.swc.types.TsModuleDeclaration
import dev.yidafu.swc.types.TsModuleDeclarationImpl
import dev.yidafu.swc.types.TsNamespaceDeclaration
import dev.yidafu.swc.types.TsNamespaceDeclarationImpl
import dev.yidafu.swc.types.TsNamespaceExportDeclaration
import dev.yidafu.swc.types.TsNamespaceExportDeclarationImpl
import dev.yidafu.swc.types.TsNonNullExpression
import dev.yidafu.swc.types.TsNonNullExpressionImpl
import dev.yidafu.swc.types.TsOptionalType
import dev.yidafu.swc.types.TsOptionalTypeImpl
import dev.yidafu.swc.types.TsParameterProperty
import dev.yidafu.swc.types.TsParameterPropertyImpl
import dev.yidafu.swc.types.TsParenthesizedType
import dev.yidafu.swc.types.TsParenthesizedTypeImpl
import dev.yidafu.swc.types.TsPropertySignature
import dev.yidafu.swc.types.TsPropertySignatureImpl
import dev.yidafu.swc.types.TsQualifiedName
import dev.yidafu.swc.types.TsQualifiedNameImpl
import dev.yidafu.swc.types.TsRestType
import dev.yidafu.swc.types.TsRestTypeImpl
import dev.yidafu.swc.types.TsSatisfiesExpression
import dev.yidafu.swc.types.TsSatisfiesExpressionImpl
import dev.yidafu.swc.types.TsSetterSignature
import dev.yidafu.swc.types.TsSetterSignatureImpl
import dev.yidafu.swc.types.TsTemplateLiteralType
import dev.yidafu.swc.types.TsTemplateLiteralTypeImpl
import dev.yidafu.swc.types.TsThisType
import dev.yidafu.swc.types.TsThisTypeImpl
import dev.yidafu.swc.types.TsTupleElement
import dev.yidafu.swc.types.TsTupleElementImpl
import dev.yidafu.swc.types.TsTupleType
import dev.yidafu.swc.types.TsTupleTypeImpl
import dev.yidafu.swc.types.TsTypeAliasDeclaration
import dev.yidafu.swc.types.TsTypeAliasDeclarationImpl
import dev.yidafu.swc.types.TsTypeAnnotation
import dev.yidafu.swc.types.TsTypeAnnotationImpl
import dev.yidafu.swc.types.TsTypeAssertion
import dev.yidafu.swc.types.TsTypeAssertionImpl
import dev.yidafu.swc.types.TsTypeLiteral
import dev.yidafu.swc.types.TsTypeLiteralImpl
import dev.yidafu.swc.types.TsTypeOperator
import dev.yidafu.swc.types.TsTypeOperatorImpl
import dev.yidafu.swc.types.TsTypeParameter
import dev.yidafu.swc.types.TsTypeParameterDeclaration
import dev.yidafu.swc.types.TsTypeParameterDeclarationImpl
import dev.yidafu.swc.types.TsTypeParameterImpl
import dev.yidafu.swc.types.TsTypeParameterInstantiation
import dev.yidafu.swc.types.TsTypeParameterInstantiationImpl
import dev.yidafu.swc.types.TsTypePredicate
import dev.yidafu.swc.types.TsTypePredicateImpl
import dev.yidafu.swc.types.TsTypeQuery
import dev.yidafu.swc.types.TsTypeQueryImpl
import dev.yidafu.swc.types.TsTypeReference
import dev.yidafu.swc.types.TsTypeReferenceImpl
import dev.yidafu.swc.types.TsUnionType
import dev.yidafu.swc.types.TsUnionTypeImpl
import dev.yidafu.swc.types.UnaryExpression
import dev.yidafu.swc.types.UnaryExpressionImpl
import dev.yidafu.swc.types.UpdateExpression
import dev.yidafu.swc.types.UpdateExpressionImpl
import dev.yidafu.swc.types.VariableDeclaration
import dev.yidafu.swc.types.VariableDeclarationImpl
import dev.yidafu.swc.types.VariableDeclarator
import dev.yidafu.swc.types.VariableDeclaratorImpl
import dev.yidafu.swc.types.WhileStatement
import dev.yidafu.swc.types.WhileStatementImpl
import dev.yidafu.swc.types.WithStatement
import dev.yidafu.swc.types.WithStatementImpl
import dev.yidafu.swc.types.YieldExpression
import dev.yidafu.swc.types.YieldExpressionImpl
import kotlin.Unit

/**
 * subtype of Node
 */
public fun Node.classProperty(block: ClassProperty.() -> Unit): ClassProperty =
    ClassPropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.privateProperty(block: PrivateProperty.() -> Unit): PrivateProperty =
    PrivatePropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.`param`(block: Param.() -> Unit): Param = ParamImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.`constructor`(block: Constructor.() -> Unit): Constructor =
    ConstructorImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.classMethod(block: ClassMethod.() -> Unit): ClassMethod =
    ClassMethodImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.privateMethod(block: PrivateMethod.() -> Unit): PrivateMethod =
    PrivateMethodImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.staticBlock(block: StaticBlock.() -> Unit): StaticBlock =
    StaticBlockImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.decorator(block: Decorator.() -> Unit): Decorator = DecoratorImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.classDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.variableDeclaration(block: VariableDeclaration.() -> Unit): VariableDeclaration =
    VariableDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.variableDeclarator(block: VariableDeclarator.() -> Unit): VariableDeclarator =
    VariableDeclaratorImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.identifier(block: Identifier.() -> Unit): Identifier = IdentifierImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.optionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.optionalChainingCall(block: OptionalChainingCall.() -> Unit): OptionalChainingCall =
    OptionalChainingCallImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.thisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.arrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.objectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.unaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.updateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.binaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.functionExpression(block: FunctionExpression.() -> Unit): FunctionExpression =
    FunctionExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.classExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.assignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression =
    AssignmentExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.memberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.superPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression =
    SuperPropExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.conditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.callExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.newExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.sequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression =
    SequenceExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.arrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.yieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.awaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.templateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.taggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.templateElement(block: TemplateElement.() -> Unit): TemplateElement =
    TemplateElementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.parenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.privateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression =
    TsNonNullExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.spreadElement(block: SpreadElement.() -> Unit): SpreadElement =
    SpreadElementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jsSuper(block: Super.() -> Unit): Super = SuperImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.`import`(block: Import.() -> Unit): Import = ImportImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.metaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.bindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier =
    BindingIdentifierImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.arrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.objectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.assignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern =
    AssignmentPatternImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.restElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression =
    JSXMemberExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression =
    JSXEmptyExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainerImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXSpreadChild(block: JSXSpreadChild.() -> Unit): JSXSpreadChild =
    JSXSpreadChildImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXOpeningElement(block: JSXOpeningElement.() -> Unit): JSXOpeningElement =
    JSXOpeningElementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXClosingElement(block: JSXClosingElement.() -> Unit): JSXClosingElement =
    JSXClosingElementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXAttribute(block: JSXAttribute.() -> Unit): JSXAttribute =
    JSXAttributeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXElement(block: JSXElement.() -> Unit): JSXElement = JSXElementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXOpeningFragment(block: JSXOpeningFragment.() -> Unit): JSXOpeningFragment =
    JSXOpeningFragmentImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.jSXClosingFragment(block: JSXClosingFragment.() -> Unit): JSXClosingFragment =
    JSXClosingFragmentImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.stringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.booleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.nullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.regExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.numericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.bigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.exportDefaultExpression(block: ExportDefaultExpression.() -> Unit):
    ExportDefaultExpression = ExportDefaultExpressionImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.exportDeclaration(block: ExportDeclaration.() -> Unit): ExportDeclaration =
    ExportDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.importDeclaration(block: ImportDeclaration.() -> Unit): ImportDeclaration =
    ImportDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.exportAllDeclaration(block: ExportAllDeclaration.() -> Unit): ExportAllDeclaration =
    ExportAllDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.exportNamedDeclaration(block: ExportNamedDeclaration.() -> Unit):
    ExportNamedDeclaration = ExportNamedDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.exportDefaultDeclaration(block: ExportDefaultDeclaration.() -> Unit):
    ExportDefaultDeclaration = ExportDefaultDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.importDefaultSpecifier(block: ImportDefaultSpecifier.() -> Unit):
    ImportDefaultSpecifier = ImportDefaultSpecifierImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.importNamespaceSpecifier(block: ImportNamespaceSpecifier.() -> Unit):
    ImportNamespaceSpecifier = ImportNamespaceSpecifierImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.namedImportSpecifier(block: NamedImportSpecifier.() -> Unit): NamedImportSpecifier =
    NamedImportSpecifierImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.exportNamespaceSpecifier(block: ExportNamespaceSpecifier.() -> Unit):
    ExportNamespaceSpecifier = ExportNamespaceSpecifierImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.exportDefaultSpecifier(block: ExportDefaultSpecifier.() -> Unit):
    ExportDefaultSpecifier = ExportDefaultSpecifierImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.namedExportSpecifier(block: NamedExportSpecifier.() -> Unit): NamedExportSpecifier =
    NamedExportSpecifierImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.module(block: Module.() -> Unit): Module = ModuleImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.script(block: Script.() -> Unit): Script = ScriptImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.keyValuePatternProperty(block: KeyValuePatternProperty.() -> Unit):
    KeyValuePatternProperty = KeyValuePatternPropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.assignmentPatternProperty(block: AssignmentPatternProperty.() -> Unit):
    AssignmentPatternProperty = AssignmentPatternPropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.keyValueProperty(block: KeyValueProperty.() -> Unit): KeyValueProperty =
    KeyValuePropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.getterProperty(block: GetterProperty.() -> Unit): GetterProperty =
    GetterPropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.setterProperty(block: SetterProperty.() -> Unit): SetterProperty =
    SetterPropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.methodProperty(block: MethodProperty.() -> Unit): MethodProperty =
    MethodPropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.assignmentProperty(block: AssignmentProperty.() -> Unit): AssignmentProperty =
    AssignmentPropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.computedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropNameImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.blockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.expressionStatement(block: ExpressionStatement.() -> Unit): ExpressionStatement =
    ExpressionStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.debuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement =
    DebuggerStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.withStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.returnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.labeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.breakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.continueStatement(block: ContinueStatement.() -> Unit): ContinueStatement =
    ContinueStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.ifStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.switchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.throwStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.whileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.doWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.forStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.forInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.forOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.switchCase(block: SwitchCase.() -> Unit): SwitchCase = SwitchCaseImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.catchClause(block: CatchClause.() -> Unit): CatchClause =
    CatchClauseImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeParameter(block: TsTypeParameter.() -> Unit): TsTypeParameter =
    TsTypeParameterImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsParameterProperty(block: TsParameterProperty.() -> Unit): TsParameterProperty =
    TsParameterPropertyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedNameImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsCallSignatureDeclaration(block: TsCallSignatureDeclaration.() -> Unit):
    TsCallSignatureDeclaration = TsCallSignatureDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsConstructSignatureDeclaration(block: TsConstructSignatureDeclaration.() -> Unit):
    TsConstructSignatureDeclaration = TsConstructSignatureDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsPropertySignature(block: TsPropertySignature.() -> Unit): TsPropertySignature =
    TsPropertySignatureImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsGetterSignature(block: TsGetterSignature.() -> Unit): TsGetterSignature =
    TsGetterSignatureImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsSetterSignature(block: TsSetterSignature.() -> Unit): TsSetterSignature =
    TsSetterSignatureImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsMethodSignature(block: TsMethodSignature.() -> Unit): TsMethodSignature =
    TsMethodSignatureImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignatureImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsThisType(block: TsThisType.() -> Unit): TsThisType = TsThisTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsConstructorType(block: TsConstructorType.() -> Unit): TsConstructorType =
    TsConstructorTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTupleElement(block: TsTupleElement.() -> Unit): TsTupleElement =
    TsTupleElementImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsRestType(block: TsRestType.() -> Unit): TsRestType = TsRestTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsIntersectionType(block: TsIntersectionType.() -> Unit): TsIntersectionType =
    TsIntersectionTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsConditionalType(block: TsConditionalType.() -> Unit): TsConditionalType =
    TsConditionalTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsParenthesizedType(block: TsParenthesizedType.() -> Unit): TsParenthesizedType =
    TsParenthesizedTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsIndexedAccessType(block: TsIndexedAccessType.() -> Unit): TsIndexedAccessType =
    TsIndexedAccessTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTemplateLiteralType(block: TsTemplateLiteralType.() -> Unit):
    TsTemplateLiteralType = TsTemplateLiteralTypeImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsInterfaceBody(block: TsInterfaceBody.() -> Unit): TsInterfaceBody =
    TsInterfaceBodyImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsExpressionWithTypeArguments(block: TsExpressionWithTypeArguments.() -> Unit):
    TsExpressionWithTypeArguments = TsExpressionWithTypeArgumentsImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration =
    TsEnumDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsEnumMember(block: TsEnumMember.() -> Unit): TsEnumMember =
    TsEnumMemberImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsModuleDeclaration(block: TsModuleDeclaration.() -> Unit): TsModuleDeclaration =
    TsModuleDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsModuleBlock(block: TsModuleBlock.() -> Unit): TsModuleBlock =
    TsModuleBlockImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsNamespaceDeclaration(block: TsNamespaceDeclaration.() -> Unit):
    TsNamespaceDeclaration = TsNamespaceDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsImportEqualsDeclaration(block: TsImportEqualsDeclaration.() -> Unit):
    TsImportEqualsDeclaration = TsImportEqualsDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsExternalModuleReference(block: TsExternalModuleReference.() -> Unit):
    TsExternalModuleReference = TsExternalModuleReferenceImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsExportAssignment(block: TsExportAssignment.() -> Unit): TsExportAssignment =
    TsExportAssignmentImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsNamespaceExportDeclaration(block: TsNamespaceExportDeclaration.() -> Unit):
    TsNamespaceExportDeclaration = TsNamespaceExportDeclarationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.tsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

/**
 * subtype of Node
 */
public fun Node.invalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

/**
 * Node#type: String
 * extension function for create String -> String
 */
public fun Node.string(block: String.() -> Unit): String = String().apply(block)

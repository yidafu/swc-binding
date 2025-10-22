package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.AmdConfig
import dev.yidafu.swc.types.AmdConfigImpl
import dev.yidafu.swc.types.Argument
import dev.yidafu.swc.types.ArgumentImpl
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
import dev.yidafu.swc.types.CallerOptions
import dev.yidafu.swc.types.CallerOptionsImpl
import dev.yidafu.swc.types.CatchClause
import dev.yidafu.swc.types.CatchClauseImpl
import dev.yidafu.swc.types.Class
import dev.yidafu.swc.types.ClassDeclaration
import dev.yidafu.swc.types.ClassDeclarationImpl
import dev.yidafu.swc.types.ClassExpression
import dev.yidafu.swc.types.ClassExpressionImpl
import dev.yidafu.swc.types.ClassImpl
import dev.yidafu.swc.types.ClassMethod
import dev.yidafu.swc.types.ClassMethodImpl
import dev.yidafu.swc.types.ClassProperty
import dev.yidafu.swc.types.ClassPropertyImpl
import dev.yidafu.swc.types.CommonJsConfig
import dev.yidafu.swc.types.CommonJsConfigImpl
import dev.yidafu.swc.types.ComputedPropName
import dev.yidafu.swc.types.ComputedPropNameImpl
import dev.yidafu.swc.types.ConditionalExpression
import dev.yidafu.swc.types.ConditionalExpressionImpl
import dev.yidafu.swc.types.Config
import dev.yidafu.swc.types.ConfigImpl
import dev.yidafu.swc.types.ConstModulesConfig
import dev.yidafu.swc.types.ConstModulesConfigImpl
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
import dev.yidafu.swc.types.EnvConfig
import dev.yidafu.swc.types.EnvConfigImpl
import dev.yidafu.swc.types.Es6Config
import dev.yidafu.swc.types.Es6ConfigImpl
import dev.yidafu.swc.types.EsParserConfig
import dev.yidafu.swc.types.EsParserConfigImpl
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
import dev.yidafu.swc.types.ExprOrSpread
import dev.yidafu.swc.types.ExprOrSpreadImpl
import dev.yidafu.swc.types.ExpressionStatement
import dev.yidafu.swc.types.ExpressionStatementImpl
import dev.yidafu.swc.types.ForInStatement
import dev.yidafu.swc.types.ForInStatementImpl
import dev.yidafu.swc.types.ForOfStatement
import dev.yidafu.swc.types.ForOfStatementImpl
import dev.yidafu.swc.types.ForStatement
import dev.yidafu.swc.types.ForStatementImpl
import dev.yidafu.swc.types.FunctionDeclaration
import dev.yidafu.swc.types.FunctionDeclarationImpl
import dev.yidafu.swc.types.FunctionExpression
import dev.yidafu.swc.types.FunctionExpressionImpl
import dev.yidafu.swc.types.GetterProperty
import dev.yidafu.swc.types.GetterPropertyImpl
import dev.yidafu.swc.types.GlobalPassOption
import dev.yidafu.swc.types.GlobalPassOptionImpl
import dev.yidafu.swc.types.HasInterpreter
import dev.yidafu.swc.types.HasInterpreterImpl
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
import dev.yidafu.swc.types.JsFormatOptions
import dev.yidafu.swc.types.JsFormatOptionsImpl
import dev.yidafu.swc.types.JsMinifyOptions
import dev.yidafu.swc.types.JsMinifyOptionsImpl
import dev.yidafu.swc.types.JscConfig
import dev.yidafu.swc.types.JscConfigImpl
import dev.yidafu.swc.types.KeyValuePatternProperty
import dev.yidafu.swc.types.KeyValuePatternPropertyImpl
import dev.yidafu.swc.types.KeyValueProperty
import dev.yidafu.swc.types.KeyValuePropertyImpl
import dev.yidafu.swc.types.LabeledStatement
import dev.yidafu.swc.types.LabeledStatementImpl
import dev.yidafu.swc.types.MatchPattern
import dev.yidafu.swc.types.MatchPatternImpl
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
import dev.yidafu.swc.types.NodeNextConfig
import dev.yidafu.swc.types.NodeNextConfigImpl
import dev.yidafu.swc.types.NullLiteral
import dev.yidafu.swc.types.NullLiteralImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.ObjectExpression
import dev.yidafu.swc.types.ObjectExpressionImpl
import dev.yidafu.swc.types.ObjectPattern
import dev.yidafu.swc.types.ObjectPatternImpl
import dev.yidafu.swc.types.OptimizerConfig
import dev.yidafu.swc.types.OptimizerConfigImpl
import dev.yidafu.swc.types.OptionalChainingCall
import dev.yidafu.swc.types.OptionalChainingCallImpl
import dev.yidafu.swc.types.OptionalChainingExpression
import dev.yidafu.swc.types.OptionalChainingExpressionImpl
import dev.yidafu.swc.types.Options
import dev.yidafu.swc.types.OptionsImpl
import dev.yidafu.swc.types.Output
import dev.yidafu.swc.types.OutputImpl
import dev.yidafu.swc.types.Param
import dev.yidafu.swc.types.ParamImpl
import dev.yidafu.swc.types.ParenthesisExpression
import dev.yidafu.swc.types.ParenthesisExpressionImpl
import dev.yidafu.swc.types.Plugin
import dev.yidafu.swc.types.PluginImpl
import dev.yidafu.swc.types.PrivateMethod
import dev.yidafu.swc.types.PrivateMethodImpl
import dev.yidafu.swc.types.PrivateName
import dev.yidafu.swc.types.PrivateNameImpl
import dev.yidafu.swc.types.PrivateProperty
import dev.yidafu.swc.types.PrivatePropertyImpl
import dev.yidafu.swc.types.ReactConfig
import dev.yidafu.swc.types.ReactConfigImpl
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
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.SpreadElement
import dev.yidafu.swc.types.SpreadElementImpl
import dev.yidafu.swc.types.StaticBlock
import dev.yidafu.swc.types.StaticBlockImpl
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
import dev.yidafu.swc.types.SystemjsConfig
import dev.yidafu.swc.types.SystemjsConfigImpl
import dev.yidafu.swc.types.TaggedTemplateExpression
import dev.yidafu.swc.types.TaggedTemplateExpressionImpl
import dev.yidafu.swc.types.TemplateElement
import dev.yidafu.swc.types.TemplateElementImpl
import dev.yidafu.swc.types.TemplateLiteral
import dev.yidafu.swc.types.TemplateLiteralImpl
import dev.yidafu.swc.types.TerserCompressOptions
import dev.yidafu.swc.types.TerserCompressOptionsImpl
import dev.yidafu.swc.types.TerserMangleOptions
import dev.yidafu.swc.types.TerserMangleOptionsImpl
import dev.yidafu.swc.types.TerserManglePropertiesOptions
import dev.yidafu.swc.types.TerserManglePropertiesOptionsImpl
import dev.yidafu.swc.types.ThisExpression
import dev.yidafu.swc.types.ThisExpressionImpl
import dev.yidafu.swc.types.ThrowStatement
import dev.yidafu.swc.types.ThrowStatementImpl
import dev.yidafu.swc.types.TransformConfig
import dev.yidafu.swc.types.TransformConfigImpl
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
import dev.yidafu.swc.types.TsParserConfig
import dev.yidafu.swc.types.TsParserConfigImpl
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
import dev.yidafu.swc.types.UmdConfig
import dev.yidafu.swc.types.UmdConfigImpl
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

public fun createPlugin(block: Plugin.() -> Unit): Plugin = PluginImpl().apply(block)

public fun createJsMinifyOptions(block: JsMinifyOptions.() -> Unit): JsMinifyOptions =
    JsMinifyOptionsImpl().apply(block)

public fun createJsFormatOptions(block: JsFormatOptions.() -> Unit): JsFormatOptions =
    JsFormatOptionsImpl().apply(block)

public fun createTerserCompressOptions(block: TerserCompressOptions.() -> Unit):
    TerserCompressOptions = TerserCompressOptionsImpl().apply(block)

public fun createTerserMangleOptions(block: TerserMangleOptions.() -> Unit): TerserMangleOptions =
    TerserMangleOptionsImpl().apply(block)

public fun createTerserManglePropertiesOptions(block: TerserManglePropertiesOptions.() -> Unit):
    TerserManglePropertiesOptions = TerserManglePropertiesOptionsImpl().apply(block)

public fun createOptions(block: Options.() -> Unit): Options = OptionsImpl().apply(block)

public fun createCallerOptions(block: CallerOptions.() -> Unit): CallerOptions =
    CallerOptionsImpl().apply(block)

public fun createConfig(block: Config.() -> Unit): Config = ConfigImpl().apply(block)

public fun createEnvConfig(block: EnvConfig.() -> Unit): EnvConfig = EnvConfigImpl().apply(block)

public fun createJscConfig(block: JscConfig.() -> Unit): JscConfig = JscConfigImpl().apply(block)

public fun createTsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig =
    TsParserConfigImpl().apply(block)

public fun createEsParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig =
    EsParserConfigImpl().apply(block)

public fun createTransformConfig(block: TransformConfig.() -> Unit): TransformConfig =
    TransformConfigImpl().apply(block)

public fun createReactConfig(block: ReactConfig.() -> Unit): ReactConfig =
    ReactConfigImpl().apply(block)

public fun createConstModulesConfig(block: ConstModulesConfig.() -> Unit): ConstModulesConfig =
    ConstModulesConfigImpl().apply(block)

public fun createOptimizerConfig(block: OptimizerConfig.() -> Unit): OptimizerConfig =
    OptimizerConfigImpl().apply(block)

public fun createGlobalPassOption(block: GlobalPassOption.() -> Unit): GlobalPassOption =
    GlobalPassOptionImpl().apply(block)

public fun createEs6Config(block: Es6Config.() -> Unit): Es6Config = Es6ConfigImpl().apply(block)

public fun createNodeNextConfig(block: NodeNextConfig.() -> Unit): NodeNextConfig =
    NodeNextConfigImpl().apply(block)

public fun createCommonJsConfig(block: CommonJsConfig.() -> Unit): CommonJsConfig =
    CommonJsConfigImpl().apply(block)

public fun createUmdConfig(block: UmdConfig.() -> Unit): UmdConfig = UmdConfigImpl().apply(block)

public fun createAmdConfig(block: AmdConfig.() -> Unit): AmdConfig = AmdConfigImpl().apply(block)

public fun createSystemjsConfig(block: SystemjsConfig.() -> Unit): SystemjsConfig =
    SystemjsConfigImpl().apply(block)

public fun createOutput(block: Output.() -> Unit): Output = OutputImpl().apply(block)

public fun createMatchPattern(block: MatchPattern.() -> Unit): MatchPattern =
    MatchPatternImpl().apply(block)

public fun createSpan(block: Span.() -> Unit): Span = SpanImpl().apply(block)

public fun createClass(block: Class.() -> Unit): Class = ClassImpl().apply(block)

public fun createClassProperty(block: ClassProperty.() -> Unit): ClassProperty =
    ClassPropertyImpl().apply(block)

public fun createPrivateProperty(block: PrivateProperty.() -> Unit): PrivateProperty =
    PrivatePropertyImpl().apply(block)

public fun createParam(block: Param.() -> Unit): Param = ParamImpl().apply(block)

public fun createConstructor(block: Constructor.() -> Unit): Constructor =
    ConstructorImpl().apply(block)

public fun createClassMethod(block: ClassMethod.() -> Unit): ClassMethod =
    ClassMethodImpl().apply(block)

public fun createPrivateMethod(block: PrivateMethod.() -> Unit): PrivateMethod =
    PrivateMethodImpl().apply(block)

public fun createStaticBlock(block: StaticBlock.() -> Unit): StaticBlock =
    StaticBlockImpl().apply(block)

public fun createDecorator(block: Decorator.() -> Unit): Decorator = DecoratorImpl().apply(block)

public fun createFunctionDeclaration(block: FunctionDeclaration.() -> Unit): FunctionDeclaration =
    FunctionDeclarationImpl().apply(block)

public fun createClassDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration =
    ClassDeclarationImpl().apply(block)

public fun createVariableDeclaration(block: VariableDeclaration.() -> Unit): VariableDeclaration =
    VariableDeclarationImpl().apply(block)

public fun createVariableDeclarator(block: VariableDeclarator.() -> Unit): VariableDeclarator =
    VariableDeclaratorImpl().apply(block)

public fun createIdentifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

public fun createOptionalChainingExpression(block: OptionalChainingExpression.() -> Unit):
    OptionalChainingExpression = OptionalChainingExpressionImpl().apply(block)

public fun createOptionalChainingCall(block: OptionalChainingCall.() -> Unit): OptionalChainingCall
    = OptionalChainingCallImpl().apply(block)

public fun createThisExpression(block: ThisExpression.() -> Unit): ThisExpression =
    ThisExpressionImpl().apply(block)

public fun createArrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression =
    ArrayExpressionImpl().apply(block)

public fun createExprOrSpread(block: ExprOrSpread.() -> Unit): ExprOrSpread =
    ExprOrSpreadImpl().apply(block)

public fun createObjectExpression(block: ObjectExpression.() -> Unit): ObjectExpression =
    ObjectExpressionImpl().apply(block)

public fun createArgument(block: Argument.() -> Unit): Argument = ArgumentImpl().apply(block)

public fun createSpreadElement(block: SpreadElement.() -> Unit): SpreadElement =
    SpreadElementImpl().apply(block)

public fun createUnaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression =
    UnaryExpressionImpl().apply(block)

public fun createUpdateExpression(block: UpdateExpression.() -> Unit): UpdateExpression =
    UpdateExpressionImpl().apply(block)

public fun createBinaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

public fun createFunctionExpression(block: FunctionExpression.() -> Unit): FunctionExpression =
    FunctionExpressionImpl().apply(block)

public fun createClassExpression(block: ClassExpression.() -> Unit): ClassExpression =
    ClassExpressionImpl().apply(block)

public fun createAssignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression
    = AssignmentExpressionImpl().apply(block)

public fun createMemberExpression(block: MemberExpression.() -> Unit): MemberExpression =
    MemberExpressionImpl().apply(block)

public fun createSuperPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression =
    SuperPropExpressionImpl().apply(block)

public fun createConditionalExpression(block: ConditionalExpression.() -> Unit):
    ConditionalExpression = ConditionalExpressionImpl().apply(block)

public fun createSuper(block: Super.() -> Unit): Super = SuperImpl().apply(block)

public fun createImport(block: Import.() -> Unit): Import = ImportImpl().apply(block)

public fun createCallExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

public fun createNewExpression(block: NewExpression.() -> Unit): NewExpression =
    NewExpressionImpl().apply(block)

public fun createSequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression =
    SequenceExpressionImpl().apply(block)

public fun createArrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit):
    ArrowFunctionExpression = ArrowFunctionExpressionImpl().apply(block)

public fun createYieldExpression(block: YieldExpression.() -> Unit): YieldExpression =
    YieldExpressionImpl().apply(block)

public fun createMetaProperty(block: MetaProperty.() -> Unit): MetaProperty =
    MetaPropertyImpl().apply(block)

public fun createAwaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression =
    AwaitExpressionImpl().apply(block)

public fun createTemplateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral =
    TemplateLiteralImpl().apply(block)

public fun createTaggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit):
    TaggedTemplateExpression = TaggedTemplateExpressionImpl().apply(block)

public fun createTemplateElement(block: TemplateElement.() -> Unit): TemplateElement =
    TemplateElementImpl().apply(block)

public fun createParenthesisExpression(block: ParenthesisExpression.() -> Unit):
    ParenthesisExpression = ParenthesisExpressionImpl().apply(block)

public fun createPrivateName(block: PrivateName.() -> Unit): PrivateName =
    PrivateNameImpl().apply(block)

public fun createJSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression =
    JSXMemberExpressionImpl().apply(block)

public fun createJSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName =
    JSXNamespacedNameImpl().apply(block)

public fun createJSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression =
    JSXEmptyExpressionImpl().apply(block)

public fun createJSXExpressionContainer(block: JSXExpressionContainer.() -> Unit):
    JSXExpressionContainer = JSXExpressionContainerImpl().apply(block)

public fun createJSXSpreadChild(block: JSXSpreadChild.() -> Unit): JSXSpreadChild =
    JSXSpreadChildImpl().apply(block)

public fun createJSXOpeningElement(block: JSXOpeningElement.() -> Unit): JSXOpeningElement =
    JSXOpeningElementImpl().apply(block)

public fun createJSXClosingElement(block: JSXClosingElement.() -> Unit): JSXClosingElement =
    JSXClosingElementImpl().apply(block)

public fun createJSXAttribute(block: JSXAttribute.() -> Unit): JSXAttribute =
    JSXAttributeImpl().apply(block)

public fun createJSXText(block: JSXText.() -> Unit): JSXText = JSXTextImpl().apply(block)

public fun createJSXElement(block: JSXElement.() -> Unit): JSXElement =
    JSXElementImpl().apply(block)

public fun createJSXFragment(block: JSXFragment.() -> Unit): JSXFragment =
    JSXFragmentImpl().apply(block)

public fun createJSXOpeningFragment(block: JSXOpeningFragment.() -> Unit): JSXOpeningFragment =
    JSXOpeningFragmentImpl().apply(block)

public fun createJSXClosingFragment(block: JSXClosingFragment.() -> Unit): JSXClosingFragment =
    JSXClosingFragmentImpl().apply(block)

public fun createStringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

public fun createBooleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral =
    BooleanLiteralImpl().apply(block)

public fun createNullLiteral(block: NullLiteral.() -> Unit): NullLiteral =
    NullLiteralImpl().apply(block)

public fun createRegExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral =
    RegExpLiteralImpl().apply(block)

public fun createNumericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

public fun createBigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral =
    BigIntLiteralImpl().apply(block)

public fun createExportDefaultExpression(block: ExportDefaultExpression.() -> Unit):
    ExportDefaultExpression = ExportDefaultExpressionImpl().apply(block)

public fun createExportDeclaration(block: ExportDeclaration.() -> Unit): ExportDeclaration =
    ExportDeclarationImpl().apply(block)

public fun createImportDeclaration(block: ImportDeclaration.() -> Unit): ImportDeclaration =
    ImportDeclarationImpl().apply(block)

public fun createExportAllDeclaration(block: ExportAllDeclaration.() -> Unit): ExportAllDeclaration
    = ExportAllDeclarationImpl().apply(block)

public fun createExportNamedDeclaration(block: ExportNamedDeclaration.() -> Unit):
    ExportNamedDeclaration = ExportNamedDeclarationImpl().apply(block)

public fun createExportDefaultDeclaration(block: ExportDefaultDeclaration.() -> Unit):
    ExportDefaultDeclaration = ExportDefaultDeclarationImpl().apply(block)

public fun createImportDefaultSpecifier(block: ImportDefaultSpecifier.() -> Unit):
    ImportDefaultSpecifier = ImportDefaultSpecifierImpl().apply(block)

public fun createImportNamespaceSpecifier(block: ImportNamespaceSpecifier.() -> Unit):
    ImportNamespaceSpecifier = ImportNamespaceSpecifierImpl().apply(block)

public fun createNamedImportSpecifier(block: NamedImportSpecifier.() -> Unit): NamedImportSpecifier
    = NamedImportSpecifierImpl().apply(block)

public fun createExportNamespaceSpecifier(block: ExportNamespaceSpecifier.() -> Unit):
    ExportNamespaceSpecifier = ExportNamespaceSpecifierImpl().apply(block)

public fun createExportDefaultSpecifier(block: ExportDefaultSpecifier.() -> Unit):
    ExportDefaultSpecifier = ExportDefaultSpecifierImpl().apply(block)

public fun createNamedExportSpecifier(block: NamedExportSpecifier.() -> Unit): NamedExportSpecifier
    = NamedExportSpecifierImpl().apply(block)

public fun createHasInterpreter(block: HasInterpreter.() -> Unit): HasInterpreter =
    HasInterpreterImpl().apply(block)

public fun createModule(block: Module.() -> Unit): Module = ModuleImpl().apply(block)

public fun createScript(block: Script.() -> Unit): Script = ScriptImpl().apply(block)

public fun createBindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier =
    BindingIdentifierImpl().apply(block)

public fun createArrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

public fun createObjectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

public fun createAssignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern =
    AssignmentPatternImpl().apply(block)

public fun createRestElement(block: RestElement.() -> Unit): RestElement =
    RestElementImpl().apply(block)

public fun createKeyValuePatternProperty(block: KeyValuePatternProperty.() -> Unit):
    KeyValuePatternProperty = KeyValuePatternPropertyImpl().apply(block)

public fun createAssignmentPatternProperty(block: AssignmentPatternProperty.() -> Unit):
    AssignmentPatternProperty = AssignmentPatternPropertyImpl().apply(block)

public fun createKeyValueProperty(block: KeyValueProperty.() -> Unit): KeyValueProperty =
    KeyValuePropertyImpl().apply(block)

public fun createAssignmentProperty(block: AssignmentProperty.() -> Unit): AssignmentProperty =
    AssignmentPropertyImpl().apply(block)

public fun createGetterProperty(block: GetterProperty.() -> Unit): GetterProperty =
    GetterPropertyImpl().apply(block)

public fun createSetterProperty(block: SetterProperty.() -> Unit): SetterProperty =
    SetterPropertyImpl().apply(block)

public fun createMethodProperty(block: MethodProperty.() -> Unit): MethodProperty =
    MethodPropertyImpl().apply(block)

public fun createComputedPropName(block: ComputedPropName.() -> Unit): ComputedPropName =
    ComputedPropNameImpl().apply(block)

public fun createBlockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

public fun createExpressionStatement(block: ExpressionStatement.() -> Unit): ExpressionStatement =
    ExpressionStatementImpl().apply(block)

public fun createEmptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

public fun createDebuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement =
    DebuggerStatementImpl().apply(block)

public fun createWithStatement(block: WithStatement.() -> Unit): WithStatement =
    WithStatementImpl().apply(block)

public fun createReturnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

public fun createLabeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement =
    LabeledStatementImpl().apply(block)

public fun createBreakStatement(block: BreakStatement.() -> Unit): BreakStatement =
    BreakStatementImpl().apply(block)

public fun createContinueStatement(block: ContinueStatement.() -> Unit): ContinueStatement =
    ContinueStatementImpl().apply(block)

public fun createIfStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

public fun createSwitchStatement(block: SwitchStatement.() -> Unit): SwitchStatement =
    SwitchStatementImpl().apply(block)

public fun createThrowStatement(block: ThrowStatement.() -> Unit): ThrowStatement =
    ThrowStatementImpl().apply(block)

public fun createTryStatement(block: TryStatement.() -> Unit): TryStatement =
    TryStatementImpl().apply(block)

public fun createWhileStatement(block: WhileStatement.() -> Unit): WhileStatement =
    WhileStatementImpl().apply(block)

public fun createDoWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement =
    DoWhileStatementImpl().apply(block)

public fun createForStatement(block: ForStatement.() -> Unit): ForStatement =
    ForStatementImpl().apply(block)

public fun createForInStatement(block: ForInStatement.() -> Unit): ForInStatement =
    ForInStatementImpl().apply(block)

public fun createForOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement =
    ForOfStatementImpl().apply(block)

public fun createSwitchCase(block: SwitchCase.() -> Unit): SwitchCase =
    SwitchCaseImpl().apply(block)

public fun createCatchClause(block: CatchClause.() -> Unit): CatchClause =
    CatchClauseImpl().apply(block)

public fun createTsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation =
    TsTypeAnnotationImpl().apply(block)

public fun createTsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

public fun createTsTypeParameter(block: TsTypeParameter.() -> Unit): TsTypeParameter =
    TsTypeParameterImpl().apply(block)

public fun createTsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

public fun createTsParameterProperty(block: TsParameterProperty.() -> Unit): TsParameterProperty =
    TsParameterPropertyImpl().apply(block)

public fun createTsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName =
    TsQualifiedNameImpl().apply(block)

public fun createTsCallSignatureDeclaration(block: TsCallSignatureDeclaration.() -> Unit):
    TsCallSignatureDeclaration = TsCallSignatureDeclarationImpl().apply(block)

public fun createTsConstructSignatureDeclaration(block: TsConstructSignatureDeclaration.() -> Unit):
    TsConstructSignatureDeclaration = TsConstructSignatureDeclarationImpl().apply(block)

public fun createTsPropertySignature(block: TsPropertySignature.() -> Unit): TsPropertySignature =
    TsPropertySignatureImpl().apply(block)

public fun createTsGetterSignature(block: TsGetterSignature.() -> Unit): TsGetterSignature =
    TsGetterSignatureImpl().apply(block)

public fun createTsSetterSignature(block: TsSetterSignature.() -> Unit): TsSetterSignature =
    TsSetterSignatureImpl().apply(block)

public fun createTsMethodSignature(block: TsMethodSignature.() -> Unit): TsMethodSignature =
    TsMethodSignatureImpl().apply(block)

public fun createTsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignatureImpl().apply(block)

public fun createTsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType =
    TsKeywordTypeImpl().apply(block)

public fun createTsThisType(block: TsThisType.() -> Unit): TsThisType =
    TsThisTypeImpl().apply(block)

public fun createTsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType =
    TsFunctionTypeImpl().apply(block)

public fun createTsConstructorType(block: TsConstructorType.() -> Unit): TsConstructorType =
    TsConstructorTypeImpl().apply(block)

public fun createTsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

public fun createTsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate =
    TsTypePredicateImpl().apply(block)

public fun createTsImportType(block: TsImportType.() -> Unit): TsImportType =
    TsImportTypeImpl().apply(block)

public fun createTsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery =
    TsTypeQueryImpl().apply(block)

public fun createTsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral =
    TsTypeLiteralImpl().apply(block)

public fun createTsArrayType(block: TsArrayType.() -> Unit): TsArrayType =
    TsArrayTypeImpl().apply(block)

public fun createTsTupleType(block: TsTupleType.() -> Unit): TsTupleType =
    TsTupleTypeImpl().apply(block)

public fun createTsTupleElement(block: TsTupleElement.() -> Unit): TsTupleElement =
    TsTupleElementImpl().apply(block)

public fun createTsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType =
    TsOptionalTypeImpl().apply(block)

public fun createTsRestType(block: TsRestType.() -> Unit): TsRestType =
    TsRestTypeImpl().apply(block)

public fun createTsUnionType(block: TsUnionType.() -> Unit): TsUnionType =
    TsUnionTypeImpl().apply(block)

public fun createTsIntersectionType(block: TsIntersectionType.() -> Unit): TsIntersectionType =
    TsIntersectionTypeImpl().apply(block)

public fun createTsConditionalType(block: TsConditionalType.() -> Unit): TsConditionalType =
    TsConditionalTypeImpl().apply(block)

public fun createTsInferType(block: TsInferType.() -> Unit): TsInferType =
    TsInferTypeImpl().apply(block)

public fun createTsParenthesizedType(block: TsParenthesizedType.() -> Unit): TsParenthesizedType =
    TsParenthesizedTypeImpl().apply(block)

public fun createTsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator =
    TsTypeOperatorImpl().apply(block)

public fun createTsIndexedAccessType(block: TsIndexedAccessType.() -> Unit): TsIndexedAccessType =
    TsIndexedAccessTypeImpl().apply(block)

public fun createTsMappedType(block: TsMappedType.() -> Unit): TsMappedType =
    TsMappedTypeImpl().apply(block)

public fun createTsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType =
    TsLiteralTypeImpl().apply(block)

public fun createTsTemplateLiteralType(block: TsTemplateLiteralType.() -> Unit):
    TsTemplateLiteralType = TsTemplateLiteralTypeImpl().apply(block)

public fun createTsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit):
    TsInterfaceDeclaration = TsInterfaceDeclarationImpl().apply(block)

public fun createTsInterfaceBody(block: TsInterfaceBody.() -> Unit): TsInterfaceBody =
    TsInterfaceBodyImpl().apply(block)

public fun createTsExpressionWithTypeArguments(block: TsExpressionWithTypeArguments.() -> Unit):
    TsExpressionWithTypeArguments = TsExpressionWithTypeArgumentsImpl().apply(block)

public fun createTsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit):
    TsTypeAliasDeclaration = TsTypeAliasDeclarationImpl().apply(block)

public fun createTsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration =
    TsEnumDeclarationImpl().apply(block)

public fun createTsEnumMember(block: TsEnumMember.() -> Unit): TsEnumMember =
    TsEnumMemberImpl().apply(block)

public fun createTsModuleDeclaration(block: TsModuleDeclaration.() -> Unit): TsModuleDeclaration =
    TsModuleDeclarationImpl().apply(block)

public fun createTsModuleBlock(block: TsModuleBlock.() -> Unit): TsModuleBlock =
    TsModuleBlockImpl().apply(block)

public fun createTsNamespaceDeclaration(block: TsNamespaceDeclaration.() -> Unit):
    TsNamespaceDeclaration = TsNamespaceDeclarationImpl().apply(block)

public fun createTsImportEqualsDeclaration(block: TsImportEqualsDeclaration.() -> Unit):
    TsImportEqualsDeclaration = TsImportEqualsDeclarationImpl().apply(block)

public fun createTsExternalModuleReference(block: TsExternalModuleReference.() -> Unit):
    TsExternalModuleReference = TsExternalModuleReferenceImpl().apply(block)

public fun createTsExportAssignment(block: TsExportAssignment.() -> Unit): TsExportAssignment =
    TsExportAssignmentImpl().apply(block)

public fun createTsNamespaceExportDeclaration(block: TsNamespaceExportDeclaration.() -> Unit):
    TsNamespaceExportDeclaration = TsNamespaceExportDeclarationImpl().apply(block)

public fun createTsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression =
    TsAsExpressionImpl().apply(block)

public fun createTsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit):
    TsSatisfiesExpression = TsSatisfiesExpressionImpl().apply(block)

public fun createTsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation =
    TsInstantiationImpl().apply(block)

public fun createTsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion =
    TsTypeAssertionImpl().apply(block)

public fun createTsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion =
    TsConstAssertionImpl().apply(block)

public fun createTsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression =
    TsNonNullExpressionImpl().apply(block)

public fun createInvalid(block: Invalid.() -> Unit): Invalid = InvalidImpl().apply(block)

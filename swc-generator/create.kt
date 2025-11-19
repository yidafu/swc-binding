package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.*

fun createConfig(block: Config.() -> Unit): Config {
    return ConfigImpl().apply(block)
}

fun createClass(block: Class.() -> Unit): Class {
    return ClassImpl().apply(block)
}

fun createClassProperty(block: ClassProperty.() -> Unit): ClassProperty {
    return ClassPropertyImpl().apply(block)
}

fun createPrivateProperty(block: PrivateProperty.() -> Unit): PrivateProperty {
    return PrivatePropertyImpl().apply(block)
}

fun createParam(block: Param.() -> Unit): Param {
    return ParamImpl().apply(block)
}

fun createConstructor(block: Constructor.() -> Unit): Constructor {
    return ConstructorImpl().apply(block)
}

fun createClassMethod(block: ClassMethod.() -> Unit): ClassMethod {
    return ClassMethodImpl().apply(block)
}

fun createPrivateMethod(block: PrivateMethod.() -> Unit): PrivateMethod {
    return PrivateMethodImpl().apply(block)
}

fun createStaticBlock(block: StaticBlock.() -> Unit): StaticBlock {
    return StaticBlockImpl().apply(block)
}

fun createDecorator(block: Decorator.() -> Unit): Decorator {
    return DecoratorImpl().apply(block)
}

fun createFunctionDeclaration(block: FunctionDeclaration.() -> Unit): FunctionDeclaration {
    return FunctionDeclarationImpl().apply(block)
}

fun createClassDeclaration(block: ClassDeclaration.() -> Unit): ClassDeclaration {
    return ClassDeclarationImpl().apply(block)
}

fun createVariableDeclaration(block: VariableDeclaration.() -> Unit): VariableDeclaration {
    return VariableDeclarationImpl().apply(block)
}

fun createVariableDeclarator(block: VariableDeclarator.() -> Unit): VariableDeclarator {
    return VariableDeclaratorImpl().apply(block)
}

fun createIdentifier(block: Identifier.() -> Unit): Identifier {
    return IdentifierImpl().apply(block)
}

fun createOptionalChainingExpression(block: OptionalChainingExpression.() -> Unit): OptionalChainingExpression {
    return OptionalChainingExpressionImpl().apply(block)
}

fun createOptionalChainingCall(block: OptionalChainingCall.() -> Unit): OptionalChainingCall {
    return OptionalChainingCallImpl().apply(block)
}

fun createThisExpression(block: ThisExpression.() -> Unit): ThisExpression {
    return ThisExpressionImpl().apply(block)
}

fun createArrayExpression(block: ArrayExpression.() -> Unit): ArrayExpression {
    return ArrayExpressionImpl().apply(block)
}

fun createObjectExpression(block: ObjectExpression.() -> Unit): ObjectExpression {
    return ObjectExpressionImpl().apply(block)
}

fun createSpreadElement(block: SpreadElement.() -> Unit): SpreadElement {
    return SpreadElementImpl().apply(block)
}

fun createUnaryExpression(block: UnaryExpression.() -> Unit): UnaryExpression {
    return UnaryExpressionImpl().apply(block)
}

fun createUpdateExpression(block: UpdateExpression.() -> Unit): UpdateExpression {
    return UpdateExpressionImpl().apply(block)
}

fun createBinaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression {
    return BinaryExpressionImpl().apply(block)
}

fun createFunctionExpression(block: FunctionExpression.() -> Unit): FunctionExpression {
    return FunctionExpressionImpl().apply(block)
}

fun createClassExpression(block: ClassExpression.() -> Unit): ClassExpression {
    return ClassExpressionImpl().apply(block)
}

fun createAssignmentExpression(block: AssignmentExpression.() -> Unit): AssignmentExpression {
    return AssignmentExpressionImpl().apply(block)
}

fun createMemberExpression(block: MemberExpression.() -> Unit): MemberExpression {
    return MemberExpressionImpl().apply(block)
}

fun createSuperPropExpression(block: SuperPropExpression.() -> Unit): SuperPropExpression {
    return SuperPropExpressionImpl().apply(block)
}

fun createConditionalExpression(block: ConditionalExpression.() -> Unit): ConditionalExpression {
    return ConditionalExpressionImpl().apply(block)
}

fun createSuper(block: Super.() -> Unit): Super {
    return SuperImpl().apply(block)
}

fun createImport(block: Import.() -> Unit): Import {
    return ImportImpl().apply(block)
}

fun createCallExpression(block: CallExpression.() -> Unit): CallExpression {
    return CallExpressionImpl().apply(block)
}

fun createNewExpression(block: NewExpression.() -> Unit): NewExpression {
    return NewExpressionImpl().apply(block)
}

fun createSequenceExpression(block: SequenceExpression.() -> Unit): SequenceExpression {
    return SequenceExpressionImpl().apply(block)
}

fun createArrowFunctionExpression(block: ArrowFunctionExpression.() -> Unit): ArrowFunctionExpression {
    return ArrowFunctionExpressionImpl().apply(block)
}

fun createYieldExpression(block: YieldExpression.() -> Unit): YieldExpression {
    return YieldExpressionImpl().apply(block)
}

fun createMetaProperty(block: MetaProperty.() -> Unit): MetaProperty {
    return MetaPropertyImpl().apply(block)
}

fun createAwaitExpression(block: AwaitExpression.() -> Unit): AwaitExpression {
    return AwaitExpressionImpl().apply(block)
}

fun createTemplateLiteral(block: TemplateLiteral.() -> Unit): TemplateLiteral {
    return TemplateLiteralImpl().apply(block)
}

fun createTaggedTemplateExpression(block: TaggedTemplateExpression.() -> Unit): TaggedTemplateExpression {
    return TaggedTemplateExpressionImpl().apply(block)
}

fun createTemplateElement(block: TemplateElement.() -> Unit): TemplateElement {
    return TemplateElementImpl().apply(block)
}

fun createParenthesisExpression(block: ParenthesisExpression.() -> Unit): ParenthesisExpression {
    return ParenthesisExpressionImpl().apply(block)
}

fun createPrivateName(block: PrivateName.() -> Unit): PrivateName {
    return PrivateNameImpl().apply(block)
}

fun createJSXMemberExpression(block: JSXMemberExpression.() -> Unit): JSXMemberExpression {
    return JSXMemberExpressionImpl().apply(block)
}

fun createJSXNamespacedName(block: JSXNamespacedName.() -> Unit): JSXNamespacedName {
    return JSXNamespacedNameImpl().apply(block)
}

fun createJSXEmptyExpression(block: JSXEmptyExpression.() -> Unit): JSXEmptyExpression {
    return JSXEmptyExpressionImpl().apply(block)
}

fun createJSXExpressionContainer(block: JSXExpressionContainer.() -> Unit): JSXExpressionContainer {
    return JSXExpressionContainerImpl().apply(block)
}

fun createJSXSpreadChild(block: JSXSpreadChild.() -> Unit): JSXSpreadChild {
    return JSXSpreadChildImpl().apply(block)
}

fun createJSXOpeningElement(block: JSXOpeningElement.() -> Unit): JSXOpeningElement {
    return JSXOpeningElementImpl().apply(block)
}

fun createJSXClosingElement(block: JSXClosingElement.() -> Unit): JSXClosingElement {
    return JSXClosingElementImpl().apply(block)
}

fun createJSXAttribute(block: JSXAttribute.() -> Unit): JSXAttribute {
    return JSXAttributeImpl().apply(block)
}

fun createJSXText(block: JSXText.() -> Unit): JSXText {
    return JSXTextImpl().apply(block)
}

fun createJSXElement(block: JSXElement.() -> Unit): JSXElement {
    return JSXElementImpl().apply(block)
}

fun createJSXFragment(block: JSXFragment.() -> Unit): JSXFragment {
    return JSXFragmentImpl().apply(block)
}

fun createJSXOpeningFragment(block: JSXOpeningFragment.() -> Unit): JSXOpeningFragment {
    return JSXOpeningFragmentImpl().apply(block)
}

fun createJSXClosingFragment(block: JSXClosingFragment.() -> Unit): JSXClosingFragment {
    return JSXClosingFragmentImpl().apply(block)
}

fun createStringLiteral(block: StringLiteral.() -> Unit): StringLiteral {
    return StringLiteralImpl().apply(block)
}

fun createBooleanLiteral(block: BooleanLiteral.() -> Unit): BooleanLiteral {
    return BooleanLiteralImpl().apply(block)
}

fun createNullLiteral(block: NullLiteral.() -> Unit): NullLiteral {
    return NullLiteralImpl().apply(block)
}

fun createRegExpLiteral(block: RegExpLiteral.() -> Unit): RegExpLiteral {
    return RegExpLiteralImpl().apply(block)
}

fun createNumericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral {
    return NumericLiteralImpl().apply(block)
}

fun createBigIntLiteral(block: BigIntLiteral.() -> Unit): BigIntLiteral {
    return BigIntLiteralImpl().apply(block)
}

fun createExportDefaultExpression(block: ExportDefaultExpression.() -> Unit): ExportDefaultExpression {
    return ExportDefaultExpressionImpl().apply(block)
}

fun createExportDeclaration(block: ExportDeclaration.() -> Unit): ExportDeclaration {
    return ExportDeclarationImpl().apply(block)
}

fun createImportDeclaration(block: ImportDeclaration.() -> Unit): ImportDeclaration {
    return ImportDeclarationImpl().apply(block)
}

fun createExportAllDeclaration(block: ExportAllDeclaration.() -> Unit): ExportAllDeclaration {
    return ExportAllDeclarationImpl().apply(block)
}

fun createExportNamedDeclaration(block: ExportNamedDeclaration.() -> Unit): ExportNamedDeclaration {
    return ExportNamedDeclarationImpl().apply(block)
}

fun createExportDefaultDeclaration(block: ExportDefaultDeclaration.() -> Unit): ExportDefaultDeclaration {
    return ExportDefaultDeclarationImpl().apply(block)
}

fun createImportDefaultSpecifier(block: ImportDefaultSpecifier.() -> Unit): ImportDefaultSpecifier {
    return ImportDefaultSpecifierImpl().apply(block)
}

fun createImportNamespaceSpecifier(block: ImportNamespaceSpecifier.() -> Unit): ImportNamespaceSpecifier {
    return ImportNamespaceSpecifierImpl().apply(block)
}

fun createNamedImportSpecifier(block: NamedImportSpecifier.() -> Unit): NamedImportSpecifier {
    return NamedImportSpecifierImpl().apply(block)
}

fun createExportNamespaceSpecifier(block: ExportNamespaceSpecifier.() -> Unit): ExportNamespaceSpecifier {
    return ExportNamespaceSpecifierImpl().apply(block)
}

fun createExportDefaultSpecifier(block: ExportDefaultSpecifier.() -> Unit): ExportDefaultSpecifier {
    return ExportDefaultSpecifierImpl().apply(block)
}

fun createNamedExportSpecifier(block: NamedExportSpecifier.() -> Unit): NamedExportSpecifier {
    return NamedExportSpecifierImpl().apply(block)
}

fun createHasInterpreter(block: HasInterpreter.() -> Unit): HasInterpreter {
    return HasInterpreterImpl().apply(block)
}

fun createModule(block: Module.() -> Unit): Module {
    return ModuleImpl().apply(block)
}

fun createScript(block: Script.() -> Unit): Script {
    return ScriptImpl().apply(block)
}

fun createBindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier {
    return BindingIdentifierImpl().apply(block)
}

fun createArrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern {
    return ArrayPatternImpl().apply(block)
}

fun createObjectPattern(block: ObjectPattern.() -> Unit): ObjectPattern {
    return ObjectPatternImpl().apply(block)
}

fun createAssignmentPattern(block: AssignmentPattern.() -> Unit): AssignmentPattern {
    return AssignmentPatternImpl().apply(block)
}

fun createRestElement(block: RestElement.() -> Unit): RestElement {
    return RestElementImpl().apply(block)
}

fun createKeyValuePatternProperty(block: KeyValuePatternProperty.() -> Unit): KeyValuePatternProperty {
    return KeyValuePatternPropertyImpl().apply(block)
}

fun createAssignmentPatternProperty(block: AssignmentPatternProperty.() -> Unit): AssignmentPatternProperty {
    return AssignmentPatternPropertyImpl().apply(block)
}

fun createKeyValueProperty(block: KeyValueProperty.() -> Unit): KeyValueProperty {
    return KeyValuePropertyImpl().apply(block)
}

fun createAssignmentProperty(block: AssignmentProperty.() -> Unit): AssignmentProperty {
    return AssignmentPropertyImpl().apply(block)
}

fun createGetterProperty(block: GetterProperty.() -> Unit): GetterProperty {
    return GetterPropertyImpl().apply(block)
}

fun createSetterProperty(block: SetterProperty.() -> Unit): SetterProperty {
    return SetterPropertyImpl().apply(block)
}

fun createMethodProperty(block: MethodProperty.() -> Unit): MethodProperty {
    return MethodPropertyImpl().apply(block)
}

fun createComputedPropName(block: ComputedPropName.() -> Unit): ComputedPropName {
    return ComputedPropNameImpl().apply(block)
}

fun createBlockStatement(block: BlockStatement.() -> Unit): BlockStatement {
    return BlockStatementImpl().apply(block)
}

fun createExpressionStatement(block: ExpressionStatement.() -> Unit): ExpressionStatement {
    return ExpressionStatementImpl().apply(block)
}

fun createEmptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement {
    return EmptyStatementImpl().apply(block)
}

fun createDebuggerStatement(block: DebuggerStatement.() -> Unit): DebuggerStatement {
    return DebuggerStatementImpl().apply(block)
}

fun createWithStatement(block: WithStatement.() -> Unit): WithStatement {
    return WithStatementImpl().apply(block)
}

fun createReturnStatement(block: ReturnStatement.() -> Unit): ReturnStatement {
    return ReturnStatementImpl().apply(block)
}

fun createLabeledStatement(block: LabeledStatement.() -> Unit): LabeledStatement {
    return LabeledStatementImpl().apply(block)
}

fun createBreakStatement(block: BreakStatement.() -> Unit): BreakStatement {
    return BreakStatementImpl().apply(block)
}

fun createContinueStatement(block: ContinueStatement.() -> Unit): ContinueStatement {
    return ContinueStatementImpl().apply(block)
}

fun createIfStatement(block: IfStatement.() -> Unit): IfStatement {
    return IfStatementImpl().apply(block)
}

fun createSwitchStatement(block: SwitchStatement.() -> Unit): SwitchStatement {
    return SwitchStatementImpl().apply(block)
}

fun createThrowStatement(block: ThrowStatement.() -> Unit): ThrowStatement {
    return ThrowStatementImpl().apply(block)
}

fun createTryStatement(block: TryStatement.() -> Unit): TryStatement {
    return TryStatementImpl().apply(block)
}

fun createWhileStatement(block: WhileStatement.() -> Unit): WhileStatement {
    return WhileStatementImpl().apply(block)
}

fun createDoWhileStatement(block: DoWhileStatement.() -> Unit): DoWhileStatement {
    return DoWhileStatementImpl().apply(block)
}

fun createForStatement(block: ForStatement.() -> Unit): ForStatement {
    return ForStatementImpl().apply(block)
}

fun createForInStatement(block: ForInStatement.() -> Unit): ForInStatement {
    return ForInStatementImpl().apply(block)
}

fun createForOfStatement(block: ForOfStatement.() -> Unit): ForOfStatement {
    return ForOfStatementImpl().apply(block)
}

fun createSwitchCase(block: SwitchCase.() -> Unit): SwitchCase {
    return SwitchCaseImpl().apply(block)
}

fun createCatchClause(block: CatchClause.() -> Unit): CatchClause {
    return CatchClauseImpl().apply(block)
}

fun createTsTypeAnnotation(block: TsTypeAnnotation.() -> Unit): TsTypeAnnotation {
    return TsTypeAnnotationImpl().apply(block)
}

fun createTsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit): TsTypeParameterDeclaration {
    return TsTypeParameterDeclarationImpl().apply(block)
}

fun createTsTypeParameter(block: TsTypeParameter.() -> Unit): TsTypeParameter {
    return TsTypeParameterImpl().apply(block)
}

fun createTsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit): TsTypeParameterInstantiation {
    return TsTypeParameterInstantiationImpl().apply(block)
}

fun createTsParameterProperty(block: TsParameterProperty.() -> Unit): TsParameterProperty {
    return TsParameterPropertyImpl().apply(block)
}

fun createTsQualifiedName(block: TsQualifiedName.() -> Unit): TsQualifiedName {
    return TsQualifiedNameImpl().apply(block)
}

fun createTsCallSignatureDeclaration(block: TsCallSignatureDeclaration.() -> Unit): TsCallSignatureDeclaration {
    return TsCallSignatureDeclarationImpl().apply(block)
}

fun createTsConstructSignatureDeclaration(block: TsConstructSignatureDeclaration.() -> Unit): TsConstructSignatureDeclaration {
    return TsConstructSignatureDeclarationImpl().apply(block)
}

fun createTsPropertySignature(block: TsPropertySignature.() -> Unit): TsPropertySignature {
    return TsPropertySignatureImpl().apply(block)
}

fun createTsGetterSignature(block: TsGetterSignature.() -> Unit): TsGetterSignature {
    return TsGetterSignatureImpl().apply(block)
}

fun createTsSetterSignature(block: TsSetterSignature.() -> Unit): TsSetterSignature {
    return TsSetterSignatureImpl().apply(block)
}

fun createTsMethodSignature(block: TsMethodSignature.() -> Unit): TsMethodSignature {
    return TsMethodSignatureImpl().apply(block)
}

fun createTsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature {
    return TsIndexSignatureImpl().apply(block)
}

fun createTsKeywordType(block: TsKeywordType.() -> Unit): TsKeywordType {
    return TsKeywordTypeImpl().apply(block)
}

fun createTsThisType(block: TsThisType.() -> Unit): TsThisType {
    return TsThisTypeImpl().apply(block)
}

fun createTsFunctionType(block: TsFunctionType.() -> Unit): TsFunctionType {
    return TsFunctionTypeImpl().apply(block)
}

fun createTsConstructorType(block: TsConstructorType.() -> Unit): TsConstructorType {
    return TsConstructorTypeImpl().apply(block)
}

fun createTsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference {
    return TsTypeReferenceImpl().apply(block)
}

fun createTsTypePredicate(block: TsTypePredicate.() -> Unit): TsTypePredicate {
    return TsTypePredicateImpl().apply(block)
}

fun createTsImportType(block: TsImportType.() -> Unit): TsImportType {
    return TsImportTypeImpl().apply(block)
}

fun createTsTypeQuery(block: TsTypeQuery.() -> Unit): TsTypeQuery {
    return TsTypeQueryImpl().apply(block)
}

fun createTsTypeLiteral(block: TsTypeLiteral.() -> Unit): TsTypeLiteral {
    return TsTypeLiteralImpl().apply(block)
}

fun createTsArrayType(block: TsArrayType.() -> Unit): TsArrayType {
    return TsArrayTypeImpl().apply(block)
}

fun createTsTupleType(block: TsTupleType.() -> Unit): TsTupleType {
    return TsTupleTypeImpl().apply(block)
}

fun createTsTupleElement(block: TsTupleElement.() -> Unit): TsTupleElement {
    return TsTupleElementImpl().apply(block)
}

fun createTsOptionalType(block: TsOptionalType.() -> Unit): TsOptionalType {
    return TsOptionalTypeImpl().apply(block)
}

fun createTsRestType(block: TsRestType.() -> Unit): TsRestType {
    return TsRestTypeImpl().apply(block)
}

fun createTsUnionType(block: TsUnionType.() -> Unit): TsUnionType {
    return TsUnionTypeImpl().apply(block)
}

fun createTsIntersectionType(block: TsIntersectionType.() -> Unit): TsIntersectionType {
    return TsIntersectionTypeImpl().apply(block)
}

fun createTsConditionalType(block: TsConditionalType.() -> Unit): TsConditionalType {
    return TsConditionalTypeImpl().apply(block)
}

fun createTsInferType(block: TsInferType.() -> Unit): TsInferType {
    return TsInferTypeImpl().apply(block)
}

fun createTsParenthesizedType(block: TsParenthesizedType.() -> Unit): TsParenthesizedType {
    return TsParenthesizedTypeImpl().apply(block)
}

fun createTsTypeOperator(block: TsTypeOperator.() -> Unit): TsTypeOperator {
    return TsTypeOperatorImpl().apply(block)
}

fun createTsIndexedAccessType(block: TsIndexedAccessType.() -> Unit): TsIndexedAccessType {
    return TsIndexedAccessTypeImpl().apply(block)
}

fun createTsMappedType(block: TsMappedType.() -> Unit): TsMappedType {
    return TsMappedTypeImpl().apply(block)
}

fun createTsLiteralType(block: TsLiteralType.() -> Unit): TsLiteralType {
    return TsLiteralTypeImpl().apply(block)
}

fun createTsTemplateLiteralType(block: TsTemplateLiteralType.() -> Unit): TsTemplateLiteralType {
    return TsTemplateLiteralTypeImpl().apply(block)
}

fun createTsInterfaceDeclaration(block: TsInterfaceDeclaration.() -> Unit): TsInterfaceDeclaration {
    return TsInterfaceDeclarationImpl().apply(block)
}

fun createTsInterfaceBody(block: TsInterfaceBody.() -> Unit): TsInterfaceBody {
    return TsInterfaceBodyImpl().apply(block)
}

fun createTsExpressionWithTypeArguments(block: TsExpressionWithTypeArguments.() -> Unit): TsExpressionWithTypeArguments {
    return TsExpressionWithTypeArgumentsImpl().apply(block)
}

fun createTsTypeAliasDeclaration(block: TsTypeAliasDeclaration.() -> Unit): TsTypeAliasDeclaration {
    return TsTypeAliasDeclarationImpl().apply(block)
}

fun createTsEnumDeclaration(block: TsEnumDeclaration.() -> Unit): TsEnumDeclaration {
    return TsEnumDeclarationImpl().apply(block)
}

fun createTsEnumMember(block: TsEnumMember.() -> Unit): TsEnumMember {
    return TsEnumMemberImpl().apply(block)
}

fun createTsModuleDeclaration(block: TsModuleDeclaration.() -> Unit): TsModuleDeclaration {
    return TsModuleDeclarationImpl().apply(block)
}

fun createTsModuleBlock(block: TsModuleBlock.() -> Unit): TsModuleBlock {
    return TsModuleBlockImpl().apply(block)
}

fun createTsNamespaceDeclaration(block: TsNamespaceDeclaration.() -> Unit): TsNamespaceDeclaration {
    return TsNamespaceDeclarationImpl().apply(block)
}

fun createTsImportEqualsDeclaration(block: TsImportEqualsDeclaration.() -> Unit): TsImportEqualsDeclaration {
    return TsImportEqualsDeclarationImpl().apply(block)
}

fun createTsExternalModuleReference(block: TsExternalModuleReference.() -> Unit): TsExternalModuleReference {
    return TsExternalModuleReferenceImpl().apply(block)
}

fun createTsExportAssignment(block: TsExportAssignment.() -> Unit): TsExportAssignment {
    return TsExportAssignmentImpl().apply(block)
}

fun createTsNamespaceExportDeclaration(block: TsNamespaceExportDeclaration.() -> Unit): TsNamespaceExportDeclaration {
    return TsNamespaceExportDeclarationImpl().apply(block)
}

fun createTsAsExpression(block: TsAsExpression.() -> Unit): TsAsExpression {
    return TsAsExpressionImpl().apply(block)
}

fun createTsSatisfiesExpression(block: TsSatisfiesExpression.() -> Unit): TsSatisfiesExpression {
    return TsSatisfiesExpressionImpl().apply(block)
}

fun createTsInstantiation(block: TsInstantiation.() -> Unit): TsInstantiation {
    return TsInstantiationImpl().apply(block)
}

fun createTsTypeAssertion(block: TsTypeAssertion.() -> Unit): TsTypeAssertion {
    return TsTypeAssertionImpl().apply(block)
}

fun createTsConstAssertion(block: TsConstAssertion.() -> Unit): TsConstAssertion {
    return TsConstAssertionImpl().apply(block)
}

fun createTsNonNullExpression(block: TsNonNullExpression.() -> Unit): TsNonNullExpression {
    return TsNonNullExpressionImpl().apply(block)
}

fun createInvalid(block: Invalid.() -> Unit): Invalid {
    return InvalidImpl().apply(block)
}
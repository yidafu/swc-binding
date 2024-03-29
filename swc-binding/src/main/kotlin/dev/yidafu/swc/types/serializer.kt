
package dev.yidafu.swc.types

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val swcSerializersModule = SerializersModule {

    polymorphic(ClassProperty::class) {
        subclass(ClassPropertyImpl::class)
    }

    polymorphic(ClassMember::class) {
        subclass(ConstructorImpl::class)
        subclass(ClassMethodImpl::class)
        subclass(PrivateMethodImpl::class)
        subclass(ClassPropertyImpl::class)
        subclass(PrivatePropertyImpl::class)
        subclass(TsIndexSignatureImpl::class)
        subclass(EmptyStatementImpl::class)
        subclass(StaticBlockImpl::class)
    }

    polymorphic(ClassPropertyBase::class) {
        subclass(ClassPropertyImpl::class)
        subclass(PrivatePropertyImpl::class)
    }

    polymorphic(Node::class) {
        subclass(ClassPropertyImpl::class)
        subclass(PrivatePropertyImpl::class)
        subclass(ParamImpl::class)
        subclass(ConstructorImpl::class)
        subclass(ClassMethodImpl::class)
        subclass(PrivateMethodImpl::class)
        subclass(StaticBlockImpl::class)
        subclass(DecoratorImpl::class)
        subclass(ClassDeclarationImpl::class)
        subclass(VariableDeclarationImpl::class)
        subclass(VariableDeclaratorImpl::class)
        subclass(IdentifierImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
        subclass(OptionalChainingCallImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(TemplateElementImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(SpreadElementImpl::class)
        subclass(SuperImpl::class)
        subclass(ImportImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(BindingIdentifierImpl::class)
        subclass(ArrayPatternImpl::class)
        subclass(ObjectPatternImpl::class)
        subclass(AssignmentPatternImpl::class)
        subclass(RestElementImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXExpressionContainerImpl::class)
        subclass(JSXSpreadChildImpl::class)
        subclass(JSXOpeningElementImpl::class)
        subclass(JSXClosingElementImpl::class)
        subclass(JSXAttributeImpl::class)
        subclass(JSXTextImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(JSXOpeningFragmentImpl::class)
        subclass(JSXClosingFragmentImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(ExportDefaultExpressionImpl::class)
        subclass(ExportDeclarationImpl::class)
        subclass(ImportDeclarationImpl::class)
        subclass(ExportAllDeclarationImpl::class)
        subclass(ExportNamedDeclarationImpl::class)
        subclass(ExportDefaultDeclarationImpl::class)
        subclass(ImportDefaultSpecifierImpl::class)
        subclass(ImportNamespaceSpecifierImpl::class)
        subclass(NamedImportSpecifierImpl::class)
        subclass(ExportNamespaceSpecifierImpl::class)
        subclass(ExportDefaultSpecifierImpl::class)
        subclass(NamedExportSpecifierImpl::class)
        subclass(ModuleImpl::class)
        subclass(ScriptImpl::class)
        subclass(KeyValuePatternPropertyImpl::class)
        subclass(AssignmentPatternPropertyImpl::class)
        subclass(KeyValuePropertyImpl::class)
        subclass(GetterPropertyImpl::class)
        subclass(SetterPropertyImpl::class)
        subclass(MethodPropertyImpl::class)
        subclass(AssignmentPropertyImpl::class)
        subclass(ComputedPropNameImpl::class)
        subclass(BlockStatementImpl::class)
        subclass(ExpressionStatementImpl::class)
        subclass(EmptyStatementImpl::class)
        subclass(DebuggerStatementImpl::class)
        subclass(WithStatementImpl::class)
        subclass(ReturnStatementImpl::class)
        subclass(LabeledStatementImpl::class)
        subclass(BreakStatementImpl::class)
        subclass(ContinueStatementImpl::class)
        subclass(IfStatementImpl::class)
        subclass(SwitchStatementImpl::class)
        subclass(ThrowStatementImpl::class)
        subclass(TryStatementImpl::class)
        subclass(WhileStatementImpl::class)
        subclass(DoWhileStatementImpl::class)
        subclass(ForStatementImpl::class)
        subclass(ForInStatementImpl::class)
        subclass(ForOfStatementImpl::class)
        subclass(SwitchCaseImpl::class)
        subclass(CatchClauseImpl::class)
        subclass(TsTypeAnnotationImpl::class)
        subclass(TsTypeParameterDeclarationImpl::class)
        subclass(TsTypeParameterImpl::class)
        subclass(TsTypeParameterInstantiationImpl::class)
        subclass(TsParameterPropertyImpl::class)
        subclass(TsQualifiedNameImpl::class)
        subclass(TsCallSignatureDeclarationImpl::class)
        subclass(TsConstructSignatureDeclarationImpl::class)
        subclass(TsPropertySignatureImpl::class)
        subclass(TsGetterSignatureImpl::class)
        subclass(TsSetterSignatureImpl::class)
        subclass(TsMethodSignatureImpl::class)
        subclass(TsIndexSignatureImpl::class)
        subclass(TsKeywordTypeImpl::class)
        subclass(TsThisTypeImpl::class)
        subclass(TsFunctionTypeImpl::class)
        subclass(TsConstructorTypeImpl::class)
        subclass(TsTypeReferenceImpl::class)
        subclass(TsTypePredicateImpl::class)
        subclass(TsImportTypeImpl::class)
        subclass(TsTypeQueryImpl::class)
        subclass(TsTypeLiteralImpl::class)
        subclass(TsArrayTypeImpl::class)
        subclass(TsTupleTypeImpl::class)
        subclass(TsTupleElementImpl::class)
        subclass(TsOptionalTypeImpl::class)
        subclass(TsRestTypeImpl::class)
        subclass(TsUnionTypeImpl::class)
        subclass(TsIntersectionTypeImpl::class)
        subclass(TsConditionalTypeImpl::class)
        subclass(TsInferTypeImpl::class)
        subclass(TsParenthesizedTypeImpl::class)
        subclass(TsTypeOperatorImpl::class)
        subclass(TsIndexedAccessTypeImpl::class)
        subclass(TsMappedTypeImpl::class)
        subclass(TsLiteralTypeImpl::class)
        subclass(TsTemplateLiteralTypeImpl::class)
        subclass(TsInterfaceDeclarationImpl::class)
        subclass(TsInterfaceBodyImpl::class)
        subclass(TsExpressionWithTypeArgumentsImpl::class)
        subclass(TsTypeAliasDeclarationImpl::class)
        subclass(TsEnumDeclarationImpl::class)
        subclass(TsEnumMemberImpl::class)
        subclass(TsModuleDeclarationImpl::class)
        subclass(TsModuleBlockImpl::class)
        subclass(TsNamespaceDeclarationImpl::class)
        subclass(TsImportEqualsDeclarationImpl::class)
        subclass(TsExternalModuleReferenceImpl::class)
        subclass(TsExportAssignmentImpl::class)
        subclass(TsNamespaceExportDeclarationImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(InvalidImpl::class)
    }

    polymorphic(HasSpan::class) {
        subclass(ClassDeclarationImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(ClassImpl::class)
        subclass(ClassPropertyImpl::class)
        subclass(PrivatePropertyImpl::class)
        subclass(ParamImpl::class)
        subclass(ConstructorImpl::class)
        subclass(ClassMethodImpl::class)
        subclass(PrivateMethodImpl::class)
        subclass(StaticBlockImpl::class)
        subclass(DecoratorImpl::class)
        subclass(VariableDeclarationImpl::class)
        subclass(VariableDeclaratorImpl::class)
        subclass(IdentifierImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
        subclass(OptionalChainingCallImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(TemplateElementImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(SuperImpl::class)
        subclass(ImportImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(FunctionDeclarationImpl::class)
        subclass(MethodPropertyImpl::class)
        subclass(BindingIdentifierImpl::class)
        subclass(ArrayPatternImpl::class)
        subclass(ObjectPatternImpl::class)
        subclass(AssignmentPatternImpl::class)
        subclass(RestElementImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXExpressionContainerImpl::class)
        subclass(JSXSpreadChildImpl::class)
        subclass(JSXOpeningElementImpl::class)
        subclass(JSXClosingElementImpl::class)
        subclass(JSXAttributeImpl::class)
        subclass(JSXTextImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(JSXOpeningFragmentImpl::class)
        subclass(JSXClosingFragmentImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(ExportDefaultExpressionImpl::class)
        subclass(ExportDeclarationImpl::class)
        subclass(ImportDeclarationImpl::class)
        subclass(ExportAllDeclarationImpl::class)
        subclass(ExportNamedDeclarationImpl::class)
        subclass(ExportDefaultDeclarationImpl::class)
        subclass(ImportDefaultSpecifierImpl::class)
        subclass(ImportNamespaceSpecifierImpl::class)
        subclass(NamedImportSpecifierImpl::class)
        subclass(ExportNamespaceSpecifierImpl::class)
        subclass(ExportDefaultSpecifierImpl::class)
        subclass(NamedExportSpecifierImpl::class)
        subclass(ModuleImpl::class)
        subclass(ScriptImpl::class)
        subclass(AssignmentPatternPropertyImpl::class)
        subclass(GetterPropertyImpl::class)
        subclass(SetterPropertyImpl::class)
        subclass(ComputedPropNameImpl::class)
        subclass(BlockStatementImpl::class)
        subclass(ExpressionStatementImpl::class)
        subclass(EmptyStatementImpl::class)
        subclass(DebuggerStatementImpl::class)
        subclass(WithStatementImpl::class)
        subclass(ReturnStatementImpl::class)
        subclass(LabeledStatementImpl::class)
        subclass(BreakStatementImpl::class)
        subclass(ContinueStatementImpl::class)
        subclass(IfStatementImpl::class)
        subclass(SwitchStatementImpl::class)
        subclass(ThrowStatementImpl::class)
        subclass(TryStatementImpl::class)
        subclass(WhileStatementImpl::class)
        subclass(DoWhileStatementImpl::class)
        subclass(ForStatementImpl::class)
        subclass(ForInStatementImpl::class)
        subclass(ForOfStatementImpl::class)
        subclass(SwitchCaseImpl::class)
        subclass(CatchClauseImpl::class)
        subclass(TsTypeAnnotationImpl::class)
        subclass(TsTypeParameterDeclarationImpl::class)
        subclass(TsTypeParameterImpl::class)
        subclass(TsTypeParameterInstantiationImpl::class)
        subclass(TsParameterPropertyImpl::class)
        subclass(TsCallSignatureDeclarationImpl::class)
        subclass(TsConstructSignatureDeclarationImpl::class)
        subclass(TsPropertySignatureImpl::class)
        subclass(TsGetterSignatureImpl::class)
        subclass(TsSetterSignatureImpl::class)
        subclass(TsMethodSignatureImpl::class)
        subclass(TsIndexSignatureImpl::class)
        subclass(TsKeywordTypeImpl::class)
        subclass(TsThisTypeImpl::class)
        subclass(TsFunctionTypeImpl::class)
        subclass(TsConstructorTypeImpl::class)
        subclass(TsTypeReferenceImpl::class)
        subclass(TsTypePredicateImpl::class)
        subclass(TsImportTypeImpl::class)
        subclass(TsTypeQueryImpl::class)
        subclass(TsTypeLiteralImpl::class)
        subclass(TsArrayTypeImpl::class)
        subclass(TsTupleTypeImpl::class)
        subclass(TsTupleElementImpl::class)
        subclass(TsOptionalTypeImpl::class)
        subclass(TsRestTypeImpl::class)
        subclass(TsUnionTypeImpl::class)
        subclass(TsIntersectionTypeImpl::class)
        subclass(TsConditionalTypeImpl::class)
        subclass(TsInferTypeImpl::class)
        subclass(TsParenthesizedTypeImpl::class)
        subclass(TsTypeOperatorImpl::class)
        subclass(TsIndexedAccessTypeImpl::class)
        subclass(TsMappedTypeImpl::class)
        subclass(TsLiteralTypeImpl::class)
        subclass(TsTemplateLiteralTypeImpl::class)
        subclass(TsInterfaceDeclarationImpl::class)
        subclass(TsInterfaceBodyImpl::class)
        subclass(TsExpressionWithTypeArgumentsImpl::class)
        subclass(TsTypeAliasDeclarationImpl::class)
        subclass(TsEnumDeclarationImpl::class)
        subclass(TsEnumMemberImpl::class)
        subclass(TsModuleDeclarationImpl::class)
        subclass(TsModuleBlockImpl::class)
        subclass(TsNamespaceDeclarationImpl::class)
        subclass(TsImportEqualsDeclarationImpl::class)
        subclass(TsExternalModuleReferenceImpl::class)
        subclass(TsExportAssignmentImpl::class)
        subclass(TsNamespaceExportDeclarationImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(InvalidImpl::class)
    }

    polymorphic(HasDecorator::class) {
        subclass(ClassDeclarationImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(ClassImpl::class)
        subclass(ClassPropertyImpl::class)
        subclass(PrivatePropertyImpl::class)
        subclass(ParamImpl::class)
        subclass(FunctionDeclarationImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(MethodPropertyImpl::class)
        subclass(TsParameterPropertyImpl::class)
    }

    polymorphic(PrivateProperty::class) {
        subclass(PrivatePropertyImpl::class)
    }

    polymorphic(Param::class) {
        subclass(ParamImpl::class)
    }

    polymorphic(Constructor::class) {
        subclass(ConstructorImpl::class)
    }

    polymorphic(ClassMethod::class) {
        subclass(ClassMethodImpl::class)
    }

    polymorphic(ClassMethodBase::class) {
        subclass(ClassMethodImpl::class)
        subclass(PrivateMethodImpl::class)
    }

    polymorphic(PrivateMethod::class) {
        subclass(PrivateMethodImpl::class)
    }

    polymorphic(StaticBlock::class) {
        subclass(StaticBlockImpl::class)
    }

    polymorphic(Decorator::class) {
        subclass(DecoratorImpl::class)
    }

    polymorphic(ClassDeclaration::class) {
        subclass(ClassDeclarationImpl::class)
    }

    polymorphic(Declaration::class) {
        subclass(ClassDeclarationImpl::class)
        subclass(FunctionDeclarationImpl::class)
        subclass(VariableDeclarationImpl::class)
        subclass(TsInterfaceDeclarationImpl::class)
        subclass(TsTypeAliasDeclarationImpl::class)
        subclass(TsEnumDeclarationImpl::class)
        subclass(TsModuleDeclarationImpl::class)
    }

    polymorphic(Statement::class) {
        subclass(BlockStatementImpl::class)
        subclass(EmptyStatementImpl::class)
        subclass(DebuggerStatementImpl::class)
        subclass(WithStatementImpl::class)
        subclass(ReturnStatementImpl::class)
        subclass(LabeledStatementImpl::class)
        subclass(BreakStatementImpl::class)
        subclass(ContinueStatementImpl::class)
        subclass(IfStatementImpl::class)
        subclass(SwitchStatementImpl::class)
        subclass(ThrowStatementImpl::class)
        subclass(TryStatementImpl::class)
        subclass(WhileStatementImpl::class)
        subclass(DoWhileStatementImpl::class)
        subclass(ForStatementImpl::class)
        subclass(ForInStatementImpl::class)
        subclass(ForOfStatementImpl::class)
        subclass(ClassDeclarationImpl::class)
        subclass(FunctionDeclarationImpl::class)
        subclass(VariableDeclarationImpl::class)
        subclass(TsInterfaceDeclarationImpl::class)
        subclass(TsTypeAliasDeclarationImpl::class)
        subclass(TsEnumDeclarationImpl::class)
        subclass(TsModuleDeclarationImpl::class)
        subclass(ExpressionStatementImpl::class)
    }

    polymorphic(ModuleItem::class) {
        subclass(ImportDeclarationImpl::class)
        subclass(ExportDeclarationImpl::class)
        subclass(ExportNamedDeclarationImpl::class)
        subclass(ExportDefaultDeclarationImpl::class)
        subclass(ExportDefaultExpressionImpl::class)
        subclass(ExportAllDeclarationImpl::class)
        subclass(TsImportEqualsDeclarationImpl::class)
        subclass(TsExportAssignmentImpl::class)
        subclass(TsNamespaceExportDeclarationImpl::class)
        subclass(BlockStatementImpl::class)
        subclass(EmptyStatementImpl::class)
        subclass(DebuggerStatementImpl::class)
        subclass(WithStatementImpl::class)
        subclass(ReturnStatementImpl::class)
        subclass(LabeledStatementImpl::class)
        subclass(BreakStatementImpl::class)
        subclass(ContinueStatementImpl::class)
        subclass(IfStatementImpl::class)
        subclass(SwitchStatementImpl::class)
        subclass(ThrowStatementImpl::class)
        subclass(TryStatementImpl::class)
        subclass(WhileStatementImpl::class)
        subclass(DoWhileStatementImpl::class)
        subclass(ForStatementImpl::class)
        subclass(ForInStatementImpl::class)
        subclass(ForOfStatementImpl::class)
        subclass(ClassDeclarationImpl::class)
        subclass(FunctionDeclarationImpl::class)
        subclass(VariableDeclarationImpl::class)
        subclass(TsInterfaceDeclarationImpl::class)
        subclass(TsTypeAliasDeclarationImpl::class)
        subclass(TsEnumDeclarationImpl::class)
        subclass(TsModuleDeclarationImpl::class)
        subclass(ExpressionStatementImpl::class)
    }

    polymorphic(Class::class) {
        subclass(ClassDeclarationImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(ClassImpl::class)
    }

    polymorphic(VariableDeclaration::class) {
        subclass(VariableDeclarationImpl::class)
    }

    polymorphic(ForStatementInit::class) {
        subclass(VariableDeclarationImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
        subclass(InvalidImpl::class)
    }

    polymorphic(ForInStatementLeft::class) {
        subclass(VariableDeclarationImpl::class)
        subclass(BindingIdentifierImpl::class)
        subclass(ArrayPatternImpl::class)
        subclass(RestElementImpl::class)
        subclass(ObjectPatternImpl::class)
        subclass(AssignmentPatternImpl::class)
        subclass(InvalidImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
    }

    polymorphic(ForOfStatementLeft::class) {
        subclass(VariableDeclarationImpl::class)
        subclass(BindingIdentifierImpl::class)
        subclass(ArrayPatternImpl::class)
        subclass(RestElementImpl::class)
        subclass(ObjectPatternImpl::class)
        subclass(AssignmentPatternImpl::class)
        subclass(InvalidImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
    }

    polymorphic(VariableDeclarator::class) {
        subclass(VariableDeclaratorImpl::class)
    }

    polymorphic(Identifier::class) {
        subclass(IdentifierImpl::class)
    }

    polymorphic(Expression::class) {
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
        subclass(InvalidImpl::class)
    }

    polymorphic(JSXExpression::class) {
        subclass(JSXEmptyExpressionImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
        subclass(InvalidImpl::class)
    }

    polymorphic(Pattern::class) {
        subclass(BindingIdentifierImpl::class)
        subclass(ArrayPatternImpl::class)
        subclass(RestElementImpl::class)
        subclass(ObjectPatternImpl::class)
        subclass(AssignmentPatternImpl::class)
        subclass(InvalidImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
    }

    polymorphic(AssignmentExpressionLeft::class) {
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
        subclass(InvalidImpl::class)
        subclass(BindingIdentifierImpl::class)
        subclass(ArrayPatternImpl::class)
        subclass(RestElementImpl::class)
        subclass(ObjectPatternImpl::class)
        subclass(AssignmentPatternImpl::class)
    }

    polymorphic(CallExpressionCallee::class) {
        subclass(SuperImpl::class)
        subclass(ImportImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
        subclass(InvalidImpl::class)
    }

    polymorphic(ArrowFunctionExpressionBody::class) {
        subclass(BlockStatementImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(MetaPropertyImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
        subclass(JSXEmptyExpressionImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsInstantiationImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
        subclass(InvalidImpl::class)
    }

    polymorphic(JSXObject::class) {
        subclass(JSXMemberExpressionImpl::class)
        subclass(IdentifierImpl::class)
    }

    polymorphic(JSXElementName::class) {
        subclass(IdentifierImpl::class)
        subclass(JSXMemberExpressionImpl::class)
        subclass(JSXNamespacedNameImpl::class)
    }

    polymorphic(JSXAttributeName::class) {
        subclass(IdentifierImpl::class)
        subclass(JSXNamespacedNameImpl::class)
    }

    polymorphic(ModuleExportName::class) {
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
    }

    polymorphic(Property::class) {
        subclass(IdentifierImpl::class)
        subclass(KeyValuePropertyImpl::class)
        subclass(AssignmentPropertyImpl::class)
        subclass(GetterPropertyImpl::class)
        subclass(SetterPropertyImpl::class)
        subclass(MethodPropertyImpl::class)
    }

    polymorphic(PropertyName::class) {
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(ComputedPropNameImpl::class)
        subclass(BigIntLiteralImpl::class)
    }

    polymorphic(TsEntityName::class) {
        subclass(TsQualifiedNameImpl::class)
        subclass(IdentifierImpl::class)
    }

    polymorphic(TsTypeQueryExpr::class) {
        subclass(TsQualifiedNameImpl::class)
        subclass(IdentifierImpl::class)
        subclass(TsImportTypeImpl::class)
    }

    polymorphic(TsModuleReference::class) {
        subclass(TsQualifiedNameImpl::class)
        subclass(IdentifierImpl::class)
        subclass(TsExternalModuleReferenceImpl::class)
    }

    polymorphic(TsThisTypeOrIdent::class) {
        subclass(TsThisTypeImpl::class)
        subclass(IdentifierImpl::class)
    }

    polymorphic(TsEnumMemberId::class) {
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
    }

    polymorphic(TsModuleName::class) {
        subclass(IdentifierImpl::class)
        subclass(StringLiteralImpl::class)
    }

    polymorphic(MemberExpressionProperty::class) {
        subclass(IdentifierImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(ComputedPropNameImpl::class)
    }

    polymorphic(SuperPropExpressionProperty::class) {
        subclass(IdentifierImpl::class)
        subclass(ComputedPropNameImpl::class)
    }

    polymorphic(ExpressionBase::class) {
        subclass(IdentifierImpl::class)
        subclass(OptionalChainingExpressionImpl::class)
        subclass(OptionalChainingCallImpl::class)
        subclass(ThisExpressionImpl::class)
        subclass(ArrayExpressionImpl::class)
        subclass(ObjectExpressionImpl::class)
        subclass(UnaryExpressionImpl::class)
        subclass(UpdateExpressionImpl::class)
        subclass(BinaryExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(ClassExpressionImpl::class)
        subclass(AssignmentExpressionImpl::class)
        subclass(MemberExpressionImpl::class)
        subclass(SuperPropExpressionImpl::class)
        subclass(ConditionalExpressionImpl::class)
        subclass(CallExpressionImpl::class)
        subclass(NewExpressionImpl::class)
        subclass(SequenceExpressionImpl::class)
        subclass(ArrowFunctionExpressionImpl::class)
        subclass(YieldExpressionImpl::class)
        subclass(AwaitExpressionImpl::class)
        subclass(TemplateLiteralImpl::class)
        subclass(TaggedTemplateExpressionImpl::class)
        subclass(TemplateElementImpl::class)
        subclass(ParenthesisExpressionImpl::class)
        subclass(PrivateNameImpl::class)
        subclass(TsAsExpressionImpl::class)
        subclass(TsSatisfiesExpressionImpl::class)
        subclass(TsTypeAssertionImpl::class)
        subclass(TsConstAssertionImpl::class)
        subclass(TsNonNullExpressionImpl::class)
    }

    polymorphic(OptionalChainingExpression::class) {
        subclass(OptionalChainingExpressionImpl::class)
    }

    polymorphic(OptionalChainingCall::class) {
        subclass(OptionalChainingCallImpl::class)
    }

    polymorphic(OptionalChainingExpressionBase::class) {
        subclass(MemberExpressionImpl::class)
        subclass(OptionalChainingCallImpl::class)
    }

    polymorphic(ThisExpression::class) {
        subclass(ThisExpressionImpl::class)
    }

    polymorphic(ArrayExpression::class) {
        subclass(ArrayExpressionImpl::class)
    }

    polymorphic(ObjectExpression::class) {
        subclass(ObjectExpressionImpl::class)
    }

    polymorphic(UnaryExpression::class) {
        subclass(UnaryExpressionImpl::class)
    }

    polymorphic(UpdateExpression::class) {
        subclass(UpdateExpressionImpl::class)
    }

    polymorphic(BinaryExpression::class) {
        subclass(BinaryExpressionImpl::class)
    }

    polymorphic(FunctionExpression::class) {
        subclass(FunctionExpressionImpl::class)
    }

    polymorphic(DefaultDecl::class) {
        subclass(ClassExpressionImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(TsInterfaceDeclarationImpl::class)
    }

    polymorphic(Fn::class) {
        subclass(FunctionDeclarationImpl::class)
        subclass(FunctionExpressionImpl::class)
        subclass(MethodPropertyImpl::class)
    }

    polymorphic(ClassExpression::class) {
        subclass(ClassExpressionImpl::class)
    }

    polymorphic(AssignmentExpression::class) {
        subclass(AssignmentExpressionImpl::class)
    }

    polymorphic(MemberExpression::class) {
        subclass(MemberExpressionImpl::class)
    }

    polymorphic(SuperPropExpression::class) {
        subclass(SuperPropExpressionImpl::class)
    }

    polymorphic(ConditionalExpression::class) {
        subclass(ConditionalExpressionImpl::class)
    }

    polymorphic(CallExpression::class) {
        subclass(CallExpressionImpl::class)
    }

    polymorphic(NewExpression::class) {
        subclass(NewExpressionImpl::class)
    }

    polymorphic(SequenceExpression::class) {
        subclass(SequenceExpressionImpl::class)
    }

    polymorphic(ArrowFunctionExpression::class) {
        subclass(ArrowFunctionExpressionImpl::class)
    }

    polymorphic(YieldExpression::class) {
        subclass(YieldExpressionImpl::class)
    }

    polymorphic(AwaitExpression::class) {
        subclass(AwaitExpressionImpl::class)
    }

    polymorphic(TemplateLiteral::class) {
        subclass(TemplateLiteralImpl::class)
    }

    polymorphic(TaggedTemplateExpression::class) {
        subclass(TaggedTemplateExpressionImpl::class)
    }

    polymorphic(TemplateElement::class) {
        subclass(TemplateElementImpl::class)
    }

    polymorphic(ParenthesisExpression::class) {
        subclass(ParenthesisExpressionImpl::class)
    }

    polymorphic(PrivateName::class) {
        subclass(PrivateNameImpl::class)
    }

    polymorphic(TsAsExpression::class) {
        subclass(TsAsExpressionImpl::class)
    }

    polymorphic(TsSatisfiesExpression::class) {
        subclass(TsSatisfiesExpressionImpl::class)
    }

    polymorphic(TsTypeAssertion::class) {
        subclass(TsTypeAssertionImpl::class)
    }

    polymorphic(TsConstAssertion::class) {
        subclass(TsConstAssertionImpl::class)
    }

    polymorphic(TsNonNullExpression::class) {
        subclass(TsNonNullExpressionImpl::class)
    }

    polymorphic(SpreadElement::class) {
        subclass(SpreadElementImpl::class)
    }

    polymorphic(JSXAttributeOrSpread::class) {
        subclass(JSXAttributeImpl::class)
        subclass(SpreadElementImpl::class)
    }

    polymorphic(Super::class) {
        subclass(SuperImpl::class)
    }

    polymorphic(Import::class) {
        subclass(ImportImpl::class)
    }

    polymorphic(MetaProperty::class) {
        subclass(MetaPropertyImpl::class)
    }

    polymorphic(BindingIdentifier::class) {
        subclass(BindingIdentifierImpl::class)
    }

    polymorphic(TsParameterPropertyParameter::class) {
        subclass(BindingIdentifierImpl::class)
        subclass(AssignmentPatternImpl::class)
    }

    polymorphic(TsFnParameter::class) {
        subclass(BindingIdentifierImpl::class)
        subclass(ArrayPatternImpl::class)
        subclass(RestElementImpl::class)
        subclass(ObjectPatternImpl::class)
    }

    polymorphic(PatternBase::class) {
        subclass(BindingIdentifierImpl::class)
        subclass(ArrayPatternImpl::class)
        subclass(ObjectPatternImpl::class)
        subclass(AssignmentPatternImpl::class)
        subclass(RestElementImpl::class)
    }

    polymorphic(ArrayPattern::class) {
        subclass(ArrayPatternImpl::class)
    }

    polymorphic(ObjectPattern::class) {
        subclass(ObjectPatternImpl::class)
    }

    polymorphic(AssignmentPattern::class) {
        subclass(AssignmentPatternImpl::class)
    }

    polymorphic(RestElement::class) {
        subclass(RestElementImpl::class)
    }

    polymorphic(ObjectPatternProperty::class) {
        subclass(KeyValuePatternPropertyImpl::class)
        subclass(AssignmentPatternPropertyImpl::class)
        subclass(RestElementImpl::class)
    }

    polymorphic(JSXMemberExpression::class) {
        subclass(JSXMemberExpressionImpl::class)
    }

    polymorphic(JSXNamespacedName::class) {
        subclass(JSXNamespacedNameImpl::class)
    }

    polymorphic(JSXEmptyExpression::class) {
        subclass(JSXEmptyExpressionImpl::class)
    }

    polymorphic(JSXExpressionContainer::class) {
        subclass(JSXExpressionContainerImpl::class)
    }

    polymorphic(JSXAttrValue::class) {
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
        subclass(JSXExpressionContainerImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
    }

    polymorphic(JSXElementChild::class) {
        subclass(JSXTextImpl::class)
        subclass(JSXExpressionContainerImpl::class)
        subclass(JSXSpreadChildImpl::class)
        subclass(JSXElementImpl::class)
        subclass(JSXFragmentImpl::class)
    }

    polymorphic(JSXSpreadChild::class) {
        subclass(JSXSpreadChildImpl::class)
    }

    polymorphic(JSXOpeningElement::class) {
        subclass(JSXOpeningElementImpl::class)
    }

    polymorphic(JSXClosingElement::class) {
        subclass(JSXClosingElementImpl::class)
    }

    polymorphic(JSXAttribute::class) {
        subclass(JSXAttributeImpl::class)
    }

    polymorphic(JSXText::class) {
        subclass(JSXTextImpl::class)
    }

    polymorphic(Literal::class) {
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(NullLiteralImpl::class)
        subclass(NumericLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(RegExpLiteralImpl::class)
        subclass(JSXTextImpl::class)
    }

    polymorphic(JSXElement::class) {
        subclass(JSXElementImpl::class)
    }

    polymorphic(JSXFragment::class) {
        subclass(JSXFragmentImpl::class)
    }

    polymorphic(JSXOpeningFragment::class) {
        subclass(JSXOpeningFragmentImpl::class)
    }

    polymorphic(JSXClosingFragment::class) {
        subclass(JSXClosingFragmentImpl::class)
    }

    polymorphic(StringLiteral::class) {
        subclass(StringLiteralImpl::class)
    }

    polymorphic(TsLiteral::class) {
        subclass(NumericLiteralImpl::class)
        subclass(StringLiteralImpl::class)
        subclass(BooleanLiteralImpl::class)
        subclass(BigIntLiteralImpl::class)
        subclass(TsTemplateLiteralTypeImpl::class)
    }

    polymorphic(BooleanLiteral::class) {
        subclass(BooleanLiteralImpl::class)
    }

    polymorphic(NullLiteral::class) {
        subclass(NullLiteralImpl::class)
    }

    polymorphic(RegExpLiteral::class) {
        subclass(RegExpLiteralImpl::class)
    }

    polymorphic(NumericLiteral::class) {
        subclass(NumericLiteralImpl::class)
    }

    polymorphic(BigIntLiteral::class) {
        subclass(BigIntLiteralImpl::class)
    }

    polymorphic(ExportDefaultExpression::class) {
        subclass(ExportDefaultExpressionImpl::class)
    }

    polymorphic(ModuleDeclaration::class) {
        subclass(ImportDeclarationImpl::class)
        subclass(ExportDeclarationImpl::class)
        subclass(ExportNamedDeclarationImpl::class)
        subclass(ExportDefaultDeclarationImpl::class)
        subclass(ExportDefaultExpressionImpl::class)
        subclass(ExportAllDeclarationImpl::class)
        subclass(TsImportEqualsDeclarationImpl::class)
        subclass(TsExportAssignmentImpl::class)
        subclass(TsNamespaceExportDeclarationImpl::class)
    }

    polymorphic(ExportDeclaration::class) {
        subclass(ExportDeclarationImpl::class)
    }

    polymorphic(ImportDeclaration::class) {
        subclass(ImportDeclarationImpl::class)
    }

    polymorphic(ExportAllDeclaration::class) {
        subclass(ExportAllDeclarationImpl::class)
    }

    polymorphic(ExportNamedDeclaration::class) {
        subclass(ExportNamedDeclarationImpl::class)
    }

    polymorphic(ExportDefaultDeclaration::class) {
        subclass(ExportDefaultDeclarationImpl::class)
    }

    polymorphic(ImportDefaultSpecifier::class) {
        subclass(ImportDefaultSpecifierImpl::class)
    }

    polymorphic(ImportSpecifier::class) {
        subclass(NamedImportSpecifierImpl::class)
        subclass(ImportDefaultSpecifierImpl::class)
        subclass(ImportNamespaceSpecifierImpl::class)
    }

    polymorphic(ImportNamespaceSpecifier::class) {
        subclass(ImportNamespaceSpecifierImpl::class)
    }

    polymorphic(NamedImportSpecifier::class) {
        subclass(NamedImportSpecifierImpl::class)
    }

    polymorphic(ExportNamespaceSpecifier::class) {
        subclass(ExportNamespaceSpecifierImpl::class)
    }

    polymorphic(ExportSpecifier::class) {
        subclass(ExportNamespaceSpecifierImpl::class)
        subclass(ExportDefaultSpecifierImpl::class)
        subclass(NamedExportSpecifierImpl::class)
    }

    polymorphic(ExportDefaultSpecifier::class) {
        subclass(ExportDefaultSpecifierImpl::class)
    }

    polymorphic(NamedExportSpecifier::class) {
        subclass(NamedExportSpecifierImpl::class)
    }

    polymorphic(Module::class) {
        subclass(ModuleImpl::class)
    }

    polymorphic(Program::class) {
        subclass(ModuleImpl::class)
        subclass(ScriptImpl::class)
    }

    polymorphic(HasInterpreter::class) {
        subclass(ModuleImpl::class)
        subclass(ScriptImpl::class)
        subclass(HasInterpreterImpl::class)
    }

    polymorphic(Script::class) {
        subclass(ScriptImpl::class)
    }

    polymorphic(KeyValuePatternProperty::class) {
        subclass(KeyValuePatternPropertyImpl::class)
    }

    polymorphic(AssignmentPatternProperty::class) {
        subclass(AssignmentPatternPropertyImpl::class)
    }

    polymorphic(KeyValueProperty::class) {
        subclass(KeyValuePropertyImpl::class)
    }

    polymorphic(PropBase::class) {
        subclass(KeyValuePropertyImpl::class)
        subclass(GetterPropertyImpl::class)
        subclass(SetterPropertyImpl::class)
        subclass(MethodPropertyImpl::class)
    }

    polymorphic(GetterProperty::class) {
        subclass(GetterPropertyImpl::class)
    }

    polymorphic(SetterProperty::class) {
        subclass(SetterPropertyImpl::class)
    }

    polymorphic(MethodProperty::class) {
        subclass(MethodPropertyImpl::class)
    }

    polymorphic(AssignmentProperty::class) {
        subclass(AssignmentPropertyImpl::class)
    }

    polymorphic(ComputedPropName::class) {
        subclass(ComputedPropNameImpl::class)
    }

    polymorphic(BlockStatement::class) {
        subclass(BlockStatementImpl::class)
    }

    polymorphic(ExpressionStatement::class) {
        subclass(ExpressionStatementImpl::class)
    }

    polymorphic(EmptyStatement::class) {
        subclass(EmptyStatementImpl::class)
    }

    polymorphic(DebuggerStatement::class) {
        subclass(DebuggerStatementImpl::class)
    }

    polymorphic(WithStatement::class) {
        subclass(WithStatementImpl::class)
    }

    polymorphic(ReturnStatement::class) {
        subclass(ReturnStatementImpl::class)
    }

    polymorphic(LabeledStatement::class) {
        subclass(LabeledStatementImpl::class)
    }

    polymorphic(BreakStatement::class) {
        subclass(BreakStatementImpl::class)
    }

    polymorphic(ContinueStatement::class) {
        subclass(ContinueStatementImpl::class)
    }

    polymorphic(IfStatement::class) {
        subclass(IfStatementImpl::class)
    }

    polymorphic(SwitchStatement::class) {
        subclass(SwitchStatementImpl::class)
    }

    polymorphic(ThrowStatement::class) {
        subclass(ThrowStatementImpl::class)
    }

    polymorphic(TryStatement::class) {
        subclass(TryStatementImpl::class)
    }

    polymorphic(WhileStatement::class) {
        subclass(WhileStatementImpl::class)
    }

    polymorphic(DoWhileStatement::class) {
        subclass(DoWhileStatementImpl::class)
    }

    polymorphic(ForStatement::class) {
        subclass(ForStatementImpl::class)
    }

    polymorphic(ForInStatement::class) {
        subclass(ForInStatementImpl::class)
    }

    polymorphic(ForOfStatement::class) {
        subclass(ForOfStatementImpl::class)
    }

    polymorphic(SwitchCase::class) {
        subclass(SwitchCaseImpl::class)
    }

    polymorphic(CatchClause::class) {
        subclass(CatchClauseImpl::class)
    }

    polymorphic(TsTypeAnnotation::class) {
        subclass(TsTypeAnnotationImpl::class)
    }

    polymorphic(TsTypeParameterDeclaration::class) {
        subclass(TsTypeParameterDeclarationImpl::class)
    }

    polymorphic(TsTypeParameter::class) {
        subclass(TsTypeParameterImpl::class)
    }

    polymorphic(TsTypeParameterInstantiation::class) {
        subclass(TsTypeParameterInstantiationImpl::class)
    }

    polymorphic(TsParameterProperty::class) {
        subclass(TsParameterPropertyImpl::class)
    }

    polymorphic(TsQualifiedName::class) {
        subclass(TsQualifiedNameImpl::class)
    }

    polymorphic(TsCallSignatureDeclaration::class) {
        subclass(TsCallSignatureDeclarationImpl::class)
    }

    polymorphic(TsTypeElement::class) {
        subclass(TsCallSignatureDeclarationImpl::class)
        subclass(TsConstructSignatureDeclarationImpl::class)
        subclass(TsPropertySignatureImpl::class)
        subclass(TsGetterSignatureImpl::class)
        subclass(TsSetterSignatureImpl::class)
        subclass(TsMethodSignatureImpl::class)
        subclass(TsIndexSignatureImpl::class)
    }

    polymorphic(TsConstructSignatureDeclaration::class) {
        subclass(TsConstructSignatureDeclarationImpl::class)
    }

    polymorphic(TsPropertySignature::class) {
        subclass(TsPropertySignatureImpl::class)
    }

    polymorphic(TsGetterSignature::class) {
        subclass(TsGetterSignatureImpl::class)
    }

    polymorphic(TsSetterSignature::class) {
        subclass(TsSetterSignatureImpl::class)
    }

    polymorphic(TsMethodSignature::class) {
        subclass(TsMethodSignatureImpl::class)
    }

    polymorphic(TsIndexSignature::class) {
        subclass(TsIndexSignatureImpl::class)
    }

    polymorphic(TsKeywordType::class) {
        subclass(TsKeywordTypeImpl::class)
    }

    polymorphic(TsType::class) {
        subclass(TsKeywordTypeImpl::class)
        subclass(TsThisTypeImpl::class)
        subclass(TsFunctionTypeImpl::class)
        subclass(TsConstructorTypeImpl::class)
        subclass(TsTypeReferenceImpl::class)
        subclass(TsTypeQueryImpl::class)
        subclass(TsTypeLiteralImpl::class)
        subclass(TsArrayTypeImpl::class)
        subclass(TsTupleTypeImpl::class)
        subclass(TsOptionalTypeImpl::class)
        subclass(TsRestTypeImpl::class)
        subclass(TsUnionTypeImpl::class)
        subclass(TsIntersectionTypeImpl::class)
        subclass(TsConditionalTypeImpl::class)
        subclass(TsInferTypeImpl::class)
        subclass(TsParenthesizedTypeImpl::class)
        subclass(TsTypeOperatorImpl::class)
        subclass(TsIndexedAccessTypeImpl::class)
        subclass(TsMappedTypeImpl::class)
        subclass(TsLiteralTypeImpl::class)
        subclass(TsTypePredicateImpl::class)
        subclass(TsImportTypeImpl::class)
    }

    polymorphic(TsThisType::class) {
        subclass(TsThisTypeImpl::class)
    }

    polymorphic(TsFunctionType::class) {
        subclass(TsFunctionTypeImpl::class)
    }

    polymorphic(TsFnOrConstructorType::class) {
        subclass(TsFunctionTypeImpl::class)
        subclass(TsConstructorTypeImpl::class)
    }

    polymorphic(TsConstructorType::class) {
        subclass(TsConstructorTypeImpl::class)
    }

    polymorphic(TsTypeReference::class) {
        subclass(TsTypeReferenceImpl::class)
    }

    polymorphic(TsTypePredicate::class) {
        subclass(TsTypePredicateImpl::class)
    }

    polymorphic(TsImportType::class) {
        subclass(TsImportTypeImpl::class)
    }

    polymorphic(TsTypeQuery::class) {
        subclass(TsTypeQueryImpl::class)
    }

    polymorphic(TsTypeLiteral::class) {
        subclass(TsTypeLiteralImpl::class)
    }

    polymorphic(TsArrayType::class) {
        subclass(TsArrayTypeImpl::class)
    }

    polymorphic(TsTupleType::class) {
        subclass(TsTupleTypeImpl::class)
    }

    polymorphic(TsTupleElement::class) {
        subclass(TsTupleElementImpl::class)
    }

    polymorphic(TsOptionalType::class) {
        subclass(TsOptionalTypeImpl::class)
    }

    polymorphic(TsRestType::class) {
        subclass(TsRestTypeImpl::class)
    }

    polymorphic(TsUnionType::class) {
        subclass(TsUnionTypeImpl::class)
    }

    polymorphic(TsUnionOrIntersectionType::class) {
        subclass(TsUnionTypeImpl::class)
        subclass(TsIntersectionTypeImpl::class)
    }

    polymorphic(TsIntersectionType::class) {
        subclass(TsIntersectionTypeImpl::class)
    }

    polymorphic(TsConditionalType::class) {
        subclass(TsConditionalTypeImpl::class)
    }

    polymorphic(TsInferType::class) {
        subclass(TsInferTypeImpl::class)
    }

    polymorphic(TsParenthesizedType::class) {
        subclass(TsParenthesizedTypeImpl::class)
    }

    polymorphic(TsTypeOperator::class) {
        subclass(TsTypeOperatorImpl::class)
    }

    polymorphic(TsIndexedAccessType::class) {
        subclass(TsIndexedAccessTypeImpl::class)
    }

    polymorphic(TsMappedType::class) {
        subclass(TsMappedTypeImpl::class)
    }

    polymorphic(TsLiteralType::class) {
        subclass(TsLiteralTypeImpl::class)
    }

    polymorphic(TsTemplateLiteralType::class) {
        subclass(TsTemplateLiteralTypeImpl::class)
    }

    polymorphic(TsInterfaceDeclaration::class) {
        subclass(TsInterfaceDeclarationImpl::class)
    }

    polymorphic(TsInterfaceBody::class) {
        subclass(TsInterfaceBodyImpl::class)
    }

    polymorphic(TsExpressionWithTypeArguments::class) {
        subclass(TsExpressionWithTypeArgumentsImpl::class)
    }

    polymorphic(TsTypeAliasDeclaration::class) {
        subclass(TsTypeAliasDeclarationImpl::class)
    }

    polymorphic(TsEnumDeclaration::class) {
        subclass(TsEnumDeclarationImpl::class)
    }

    polymorphic(TsEnumMember::class) {
        subclass(TsEnumMemberImpl::class)
    }

    polymorphic(TsModuleDeclaration::class) {
        subclass(TsModuleDeclarationImpl::class)
    }

    polymorphic(TsModuleBlock::class) {
        subclass(TsModuleBlockImpl::class)
    }

    polymorphic(TsNamespaceBody::class) {
        subclass(TsModuleBlockImpl::class)
        subclass(TsNamespaceDeclarationImpl::class)
    }

    polymorphic(TsNamespaceDeclaration::class) {
        subclass(TsNamespaceDeclarationImpl::class)
    }

    polymorphic(TsImportEqualsDeclaration::class) {
        subclass(TsImportEqualsDeclarationImpl::class)
    }

    polymorphic(TsExternalModuleReference::class) {
        subclass(TsExternalModuleReferenceImpl::class)
    }

    polymorphic(TsExportAssignment::class) {
        subclass(TsExportAssignmentImpl::class)
    }

    polymorphic(TsNamespaceExportDeclaration::class) {
        subclass(TsNamespaceExportDeclarationImpl::class)
    }

    polymorphic(TsInstantiation::class) {
        subclass(TsInstantiationImpl::class)
    }

    polymorphic(Invalid::class) {
        subclass(InvalidImpl::class)
    }

    polymorphic(FunctionDeclaration::class) {
        subclass(FunctionDeclarationImpl::class)
    }
}
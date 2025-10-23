package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ArrayContainer
import dev.yidafu.swc.types.ArrayContainerImpl
import dev.yidafu.swc.types.ArrayPattern
import dev.yidafu.swc.types.ArrayPatternImpl
import dev.yidafu.swc.types.FunctionTypes
import dev.yidafu.swc.types.FunctionTypesImpl
import dev.yidafu.swc.types.GenericInterface
import dev.yidafu.swc.types.GenericInterfaceImpl
import dev.yidafu.swc.types.Identifier
import dev.yidafu.swc.types.IdentifierImpl
import dev.yidafu.swc.types.IndexSignature
import dev.yidafu.swc.types.IndexSignatureImpl
import dev.yidafu.swc.types.NumericLiteral
import dev.yidafu.swc.types.NumericLiteralImpl
import dev.yidafu.swc.types.OptionalFields
import dev.yidafu.swc.types.OptionalFieldsImpl
import dev.yidafu.swc.types.Span
import dev.yidafu.swc.types.SpanImpl
import dev.yidafu.swc.types.StringLiteral
import dev.yidafu.swc.types.StringLiteralImpl
import kotlin.Unit

public fun createSpan(block: Span.() -> Unit): Span = SpanImpl().apply(block)

public fun createIdentifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

public fun createStringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

public fun createNumericLiteral(block: NumericLiteral.() -> Unit): NumericLiteral =
    NumericLiteralImpl().apply(block)

public fun createArrayContainer(block: ArrayContainer.() -> Unit): ArrayContainer =
    ArrayContainerImpl().apply(block)

public fun createOptionalFields(block: OptionalFields.() -> Unit): OptionalFields =
    OptionalFieldsImpl().apply(block)

public fun createIndexSignature(block: IndexSignature.() -> Unit): IndexSignature =
    IndexSignatureImpl().apply(block)

public fun createGenericInterface(block: GenericInterface.() -> Unit): GenericInterface =
    GenericInterfaceImpl().apply(block)

public fun createFunctionTypes(block: FunctionTypes.() -> Unit): FunctionTypes =
    FunctionTypesImpl().apply(block)

public fun createArrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

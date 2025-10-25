package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.ArrayPattern
import dev.yidafu.swc.generated.ArrayPatternImpl
import dev.yidafu.swc.generated.BinaryExpression
import dev.yidafu.swc.generated.BinaryExpressionImpl
import dev.yidafu.swc.generated.BindingIdentifier
import dev.yidafu.swc.generated.BindingIdentifierImpl
import dev.yidafu.swc.generated.BlockStatement
import dev.yidafu.swc.generated.BlockStatementImpl
import dev.yidafu.swc.generated.CallExpression
import dev.yidafu.swc.generated.CallExpressionImpl
import dev.yidafu.swc.generated.ExpressionStatement
import dev.yidafu.swc.generated.ExpressionStatementImpl
import dev.yidafu.swc.generated.Identifier
import dev.yidafu.swc.generated.IdentifierImpl
import dev.yidafu.swc.generated.IfStatement
import dev.yidafu.swc.generated.IfStatementImpl
import dev.yidafu.swc.generated.ObjectPattern
import dev.yidafu.swc.generated.ObjectPatternImpl
import dev.yidafu.swc.generated.ReturnStatement
import dev.yidafu.swc.generated.ReturnStatementImpl
import dev.yidafu.swc.generated.StringLiteral
import dev.yidafu.swc.generated.StringLiteralImpl
import kotlin.Unit

public fun createIdentifier(block: Identifier.() -> Unit): Identifier =
    IdentifierImpl().apply(block)

public fun createStringLiteral(block: StringLiteral.() -> Unit): StringLiteral =
    StringLiteralImpl().apply(block)

public fun createBinaryExpression(block: BinaryExpression.() -> Unit): BinaryExpression =
    BinaryExpressionImpl().apply(block)

public fun createCallExpression(block: CallExpression.() -> Unit): CallExpression =
    CallExpressionImpl().apply(block)

public fun createBlockStatement(block: BlockStatement.() -> Unit): BlockStatement =
    BlockStatementImpl().apply(block)

public fun createExpressionStatement(block: ExpressionStatement.() -> Unit): ExpressionStatement =
    ExpressionStatementImpl().apply(block)

public fun createIfStatement(block: IfStatement.() -> Unit): IfStatement =
    IfStatementImpl().apply(block)

public fun createReturnStatement(block: ReturnStatement.() -> Unit): ReturnStatement =
    ReturnStatementImpl().apply(block)

public fun createBindingIdentifier(block: BindingIdentifier.() -> Unit): BindingIdentifier =
    BindingIdentifierImpl().apply(block)

public fun createArrayPattern(block: ArrayPattern.() -> Unit): ArrayPattern =
    ArrayPatternImpl().apply(block)

public fun createObjectPattern(block: ObjectPattern.() -> Unit): ObjectPattern =
    ObjectPatternImpl().apply(block)

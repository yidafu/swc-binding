package dev.yidafu.swc.dsl

import dev.yidafu.swc.types.ClassMember
import dev.yidafu.swc.types.ClassMethod
import dev.yidafu.swc.types.ClassMethodImpl
import dev.yidafu.swc.types.ClassProperty
import dev.yidafu.swc.types.ClassPropertyImpl
import dev.yidafu.swc.types.Constructor
import dev.yidafu.swc.types.ConstructorImpl
import dev.yidafu.swc.types.EmptyStatement
import dev.yidafu.swc.types.EmptyStatementImpl
import dev.yidafu.swc.types.PrivateMethod
import dev.yidafu.swc.types.PrivateMethodImpl
import dev.yidafu.swc.types.PrivateProperty
import dev.yidafu.swc.types.PrivatePropertyImpl
import dev.yidafu.swc.types.StaticBlock
import dev.yidafu.swc.types.StaticBlockImpl
import dev.yidafu.swc.types.TsIndexSignature
import dev.yidafu.swc.types.TsIndexSignatureImpl
import kotlin.Unit

/**
 * subtype of ClassMember
 */
public fun ClassMember.`constructor`(block: Constructor.() -> Unit): Constructor =
    ConstructorImpl().apply(block)

/**
 * subtype of ClassMember
 */
public fun ClassMember.classMethod(block: ClassMethod.() -> Unit): ClassMethod =
    ClassMethodImpl().apply(block)

/**
 * subtype of ClassMember
 */
public fun ClassMember.privateMethod(block: PrivateMethod.() -> Unit): PrivateMethod =
    PrivateMethodImpl().apply(block)

/**
 * subtype of ClassMember
 */
public fun ClassMember.classProperty(block: ClassProperty.() -> Unit): ClassProperty =
    ClassPropertyImpl().apply(block)

/**
 * subtype of ClassMember
 */
public fun ClassMember.privateProperty(block: PrivateProperty.() -> Unit): PrivateProperty =
    PrivatePropertyImpl().apply(block)

/**
 * subtype of ClassMember
 */
public fun ClassMember.tsIndexSignature(block: TsIndexSignature.() -> Unit): TsIndexSignature =
    TsIndexSignatureImpl().apply(block)

/**
 * subtype of ClassMember
 */
public fun ClassMember.emptyStatement(block: EmptyStatement.() -> Unit): EmptyStatement =
    EmptyStatementImpl().apply(block)

/**
 * subtype of ClassMember
 */
public fun ClassMember.staticBlock(block: StaticBlock.() -> Unit): StaticBlock =
    StaticBlockImpl().apply(block)

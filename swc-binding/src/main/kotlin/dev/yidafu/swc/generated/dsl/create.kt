package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.Decorator
import dev.yidafu.swc.generated.DecoratorImpl
import dev.yidafu.swc.generated.TsTypeParameter
import dev.yidafu.swc.generated.TsTypeParameterDeclaration
import dev.yidafu.swc.generated.TsTypeParameterDeclarationImpl
import dev.yidafu.swc.generated.TsTypeParameterImpl
import dev.yidafu.swc.generated.TsTypeParameterInstantiation
import dev.yidafu.swc.generated.TsTypeParameterInstantiationImpl
import dev.yidafu.swc.generated.TsTypeReference
import dev.yidafu.swc.generated.TsTypeReferenceImpl
import kotlin.Unit

public fun createTsTypeParameterDeclaration(block: TsTypeParameterDeclaration.() -> Unit):
    TsTypeParameterDeclaration = TsTypeParameterDeclarationImpl().apply(block)

public fun createTsTypeParameter(block: TsTypeParameter.() -> Unit): TsTypeParameter =
    TsTypeParameterImpl().apply(block)

public fun createTsTypeParameterInstantiation(block: TsTypeParameterInstantiation.() -> Unit):
    TsTypeParameterInstantiation = TsTypeParameterInstantiationImpl().apply(block)

public fun createTsTypeReference(block: TsTypeReference.() -> Unit): TsTypeReference =
    TsTypeReferenceImpl().apply(block)

public fun createDecorator(block: Decorator.() -> Unit): Decorator = DecoratorImpl().apply(block)

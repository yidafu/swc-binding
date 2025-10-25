package dev.yidafu.swc.generated

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

public val swcSerializersModule: SerializersModule = SerializersModule {
      polymorphic(Node::class) {
        subclass(TsTypeParameterDeclaration::class)
        subclass(TsTypeParameter::class)
        subclass(TsTypeParameterInstantiation::class)
        subclass(TsTypeReference::class)
        subclass(Decorator::class)
      }
      polymorphic(HasSpan::class) {
        subclass(TsTypeParameterDeclaration::class)
        subclass(TsTypeParameter::class)
        subclass(TsTypeParameterInstantiation::class)
        subclass(TsTypeReference::class)
        subclass(Fn::class)
        subclass(Decorator::class)
      }
    }

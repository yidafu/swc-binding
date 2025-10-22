package dev.yidafu.swc.types

import ChildInterfaceImpl
import MixinAImpl
import MixinBImpl
import ParentInterface
import ParentInterfaceImpl
import kotlin.DslMarker
import kotlin.Int
import kotlin.OptIn
import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@DslMarker
public annotation class SwcDslMarker

public typealias Record<T, S> = Map<T, String>

@SwcDslMarker
public interface ParentInterface {
  public var parentProp: String? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("ParentInterface")
public class ParentInterfaceImpl : ParentInterfaceImpl() {
  override var parentProp: String? = null
}

@SwcDslMarker
public interface ChildInterface : ParentInterface {
  public var childProp: Int? = null

  override var parentProp: String? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("ChildInterface")
public class ChildInterfaceImpl : ChildInterfaceImpl() {
  override var childProp: Int? = null

  override var parentProp: String? = null
}

@SwcDslMarker
public interface MixinA {
  public var methodA: String? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("MixinA")
public class MixinAImpl : MixinAImpl() {
  override var methodA: String? = null
}

@SwcDslMarker
public interface MixinB {
  public var methodB: Int? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("MixinB")
public class MixinBImpl : MixinBImpl() {
  override var methodB: Int? = null
}

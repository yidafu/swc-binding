package dev.yidafu.swc.types

import BaseNodeImpl
import BaseSpanImpl
import TypeLiteral
import kotlin.DslMarker
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
public interface BaseNode {
  public var type: String? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName()
public class BaseNodeImpl : BaseNodeImpl() {
  override var type: String? = null
}

@SwcDslMarker
public interface BaseSpan {
  public var span: TypeLiteral? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("BaseSpan")
public class BaseSpanImpl : BaseSpanImpl() {
  override var span: TypeLiteral? = null
}

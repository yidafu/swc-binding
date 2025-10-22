package dev.yidafu.swc.types

import BaseTypeImpl
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
@Serializable
public sealed interface UnionType

@SwcDslMarker
@Serializable
public sealed interface Item

@SwcDslMarker
public interface BaseType {
  public var kind: String? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("BaseType")
public class BaseTypeImpl : BaseTypeImpl() {
  override var kind: String? = null
}

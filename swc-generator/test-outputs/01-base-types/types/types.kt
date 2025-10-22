package dev.yidafu.swc.types

import Span
import SpanImpl
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
@Serializable
@SerialName("Span")
public class Span {
  public var start: Int? = null

  public var end: Int? = null

  public var ctxt: Int? = null
}

@OptIn(ExperimentalSerializationApi::class)
@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("Span")
public class SpanImpl : SpanImpl() {
  override var start: Int? = null

  override var end: Int? = null

  override var ctxt: Int? = null
}

@SwcDslMarker
public sealed interface Node {
  public var type: String? = null
}

@SwcDslMarker
public interface HasSpan {
  public var span: Span? = null
}

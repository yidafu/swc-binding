package dev.yidafu.swc.generated

import Container
import dev.yidafu.swc.Union
import kotlin.Array
import kotlin.Boolean
import kotlin.Double
import kotlin.DslMarker
import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@DslMarker
public annotation class SwcDslMarker

public typealias Record<T, S> = Map<T, String>

public typealias Identifier = Map<String, String>

public typealias TsType = Map<String, String>

public typealias TsEntityName = Map<String, String>

public typealias Param = Map<String, String>

public typealias BlockStatement = Map<String, String>

public typealias TsTypeAnnotation = Map<String, String>

public interface Container {
  public var `value`: T?
}

public interface Pair {
  public var key: K?

  public var `value`: V?
}

public interface Constrained {
  public var `data`: T?
}

public interface GenericArray {
  public var items: Array<T>?
}

public interface Nested {
  public var wrapper: Container<T>?
}

public interface TsTypeParameterDeclaration : Node, HasSpan {
  public var type: String

  public var parameters: Array<TsTypeParameter>?
}

public interface TsTypeParameter : Node, HasSpan {
  public var type: String

  public var name: Identifier?

  public var jsIn: Boolean?

  public var `out`: Boolean?

  public var constraint: TsType?

  public var default: TsType?
}

public interface TsTypeParameterInstantiation : Node, HasSpan {
  public var type: String

  public var params: Array<TsType>?
}

public interface TsTypeReference : Node, HasSpan {
  public var type: String

  public var typeName: TsEntityName?

  public var typeParams: TsTypeParameterInstantiation?
}

public interface Fn : HasSpan, HasDecorator {
  public var params: Array<Param>?

  public var body: BlockStatement?

  public var generator: Boolean?

  public var async: Boolean?

  public var typeParams: TsTypeParameterDeclaration?

  public var returnType: TsTypeAnnotation?
}

@SwcDslMarker
public sealed interface Node {
  public var type: String
}

@SwcDslMarker
public sealed interface HasSpan {
  public var span: Span?
}

@SwcDslMarker
public sealed interface HasDecorator {
  public var decorators: Array<Decorator>?
}

public interface Decorator : Node, HasSpan {
  public var type: String

  public var expression: Map<String, String>?
}

@Serializable
public class Span {
  public var start: Double? = null

  public var end: Double? = null

  public var ctxt: Double? = null
}

@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("TsTypeParameterDeclaration")
public class TsTypeParameterDeclarationImpl : TsTypeParameterDeclaration {
  public var type: String

  public var parameters: Array<TsTypeParameter>?

  public var span: Span?
}

@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("TsTypeParameter")
public class TsTypeParameterImpl : TsTypeParameter {
  public var type: String

  public var name: Identifier?

  public var jsIn: Boolean?

  public var `out`: Boolean?

  public var constraint: TsType?

  public var default: TsType?

  public var span: Span?
}

@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("TsTypeParameterInstantiation")
public class TsTypeParameterInstantiationImpl : TsTypeParameterInstantiation {
  public var type: String

  public var params: Array<TsType>?

  public var span: Span?
}

@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("TsTypeReference")
public class TsTypeReferenceImpl : TsTypeReference {
  public var type: String

  public var typeName: TsEntityName?

  public var typeParams: TsTypeParameterInstantiation?

  public var span: Span?
}

@SwcDslMarker
@Serializable
@JsonClassDiscriminator("type")
@SerialName("Decorator")
public class DecoratorImpl : Decorator {
  public var type: String

  public var expression: Map<String, String>?

  public var span: Span?
}

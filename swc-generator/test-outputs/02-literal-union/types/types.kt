package dev.yidafu.swc.types

import kotlin.DslMarker
import kotlin.Int
import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.json.JsonClassDiscriminator

@DslMarker
public annotation class SwcDslMarker

public typealias Record<T, S> = Map<T, String>

/**
 * /literalis:String
 */
public object MethodKind {
  public var METHOD: String? = "method"

  public var GETTER: String? = "getter"

  public var SETTER: String? = "setter"
}

/**
 * /literalis:String
 */
public object BinaryOperator {
  public var Addition: String? = "+"

  public var Subtraction: String? = "-"

  public var Multiplication: String? = "*"

  public var Division: String? = "/"
}

/**
 * /literalis:String
 */
public object ComparisonOperator {
  public var Equality: String? = "=="

  public var Inequality: String? = "!="

  public var StrictEquality: String? = "==="

  public var StrictInequality: String? = "!=="

  public var LessThan: String? = "<"

  public var GreaterThan: String? = ">"

  public var LessThanOrEqual: String? = "<="

  public var GreaterThanOrEqual: String? = ">="
}

/**
 * /literalis:Int
 */
public object StatusCode {
  public var NUMBER_200: Int? = 200

  public var NUMBER_404: Int? = 404

  public var NUMBER_500: Int? = 500
}

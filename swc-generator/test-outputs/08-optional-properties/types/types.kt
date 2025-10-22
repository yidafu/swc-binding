package dev.yidafu.swc.types

import kotlin.DslMarker
import kotlin.String
import kotlin.collections.Map
import kotlinx.serialization.json.JsonClassDiscriminator

@DslMarker
public annotation class SwcDslMarker

public typealias Record<T, S> = Map<T, String>

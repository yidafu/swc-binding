package dev.yidafu.swc.generator.model.typescript

/**
 * TypeScript 类型的扩展方法
 */
fun TypeScriptType.isPrimitive(): Boolean = when (this) {
    is TypeScriptType.Keyword -> true
    is TypeScriptType.Literal -> true
    is TypeScriptType.Any, is TypeScriptType.Unknown, is TypeScriptType.Never,
    is TypeScriptType.Void, is TypeScriptType.Null, is TypeScriptType.Undefined -> true
    else -> false
}

fun TypeScriptType.isComplex(): Boolean = !isPrimitive()

fun TypeScriptType.hasGenerics(): Boolean = when (this) {
    is TypeScriptType.Reference -> typeParams.isNotEmpty()
    is TypeScriptType.Array, is TypeScriptType.Tuple -> true
    is TypeScriptType.Union -> types.any { it.hasGenerics() }
    is TypeScriptType.Intersection -> types.any { it.hasGenerics() }
    is TypeScriptType.Function -> params.any { it.type.hasGenerics() } || returnType.hasGenerics()
    else -> false
}

/**
 * 类型名称提取
 */
fun TypeScriptType.getTypeName(): String = when (this) {
    is TypeScriptType.Keyword -> kind.name.lowercase()
    is TypeScriptType.Reference -> name
    is TypeScriptType.Array -> "Array"
    is TypeScriptType.Tuple -> "Tuple"
    is TypeScriptType.Union -> "Union"
    is TypeScriptType.Intersection -> "Intersection"
    is TypeScriptType.Literal -> "Literal"
    is TypeScriptType.TypeLiteral -> "TypeLiteral"
    is TypeScriptType.Function -> "Function"
    is TypeScriptType.IndexSignature -> "IndexSignature"
    is TypeScriptType.Any -> "any"
    is TypeScriptType.Unknown -> "unknown"
    is TypeScriptType.Never -> "never"
    is TypeScriptType.Void -> "void"
    is TypeScriptType.Null -> "null"
    is TypeScriptType.Undefined -> "undefined"
}

/**
 * 获取引用的类型名称
 */
fun TypeScriptType.getReferencedTypeNames(): Set<String> = when (this) {
    is TypeScriptType.Keyword -> emptySet()
    is TypeScriptType.Reference -> {
        val fromParams = typeParams.flatMap { it.getReferencedTypeNames() }.toSet()
        setOf(name) + fromParams
    }
    is TypeScriptType.Union -> types.flatMap { it.getReferencedTypeNames() }.toSet()
    is TypeScriptType.Intersection -> types.flatMap { it.getReferencedTypeNames() }.toSet()
    is TypeScriptType.Array -> elementType.getReferencedTypeNames()
    is TypeScriptType.Tuple -> types.flatMap { it.getReferencedTypeNames() }.toSet()
    is TypeScriptType.Literal -> emptySet()
    is TypeScriptType.TypeLiteral -> members.flatMap { it.type.getReferencedTypeNames() }.toSet()
    is TypeScriptType.Function -> {
        val fromParams = params.flatMap { it.type.getReferencedTypeNames() }.toSet()
        val fromReturn = returnType.getReferencedTypeNames()
        fromParams + fromReturn
    }
    is TypeScriptType.IndexSignature -> {
        keyType.getReferencedTypeNames() + valueType.getReferencedTypeNames()
    }
    is TypeScriptType.Any, is TypeScriptType.Unknown, is TypeScriptType.Never,
    is TypeScriptType.Void, is TypeScriptType.Null, is TypeScriptType.Undefined -> emptySet()
}

package dev.yidafu.swc.generator.model.typescript

/**
 * TypeScript 类型 ADT（代数数据类型）
 */
sealed class TypeScriptType {
    data class Keyword(val kind: KeywordKind) : TypeScriptType()
    data class Reference(
        val name: String,
        val typeParams: List<TypeScriptType> = emptyList()
    ) : TypeScriptType()
    data class Union(val types: List<TypeScriptType>) : TypeScriptType()
    data class Intersection(val types: List<TypeScriptType>) : TypeScriptType()
    data class Array(val elementType: TypeScriptType) : TypeScriptType()
    data class Tuple(val types: List<TypeScriptType>) : TypeScriptType()
    data class Literal(val value: LiteralValue) : TypeScriptType()
    data class TypeLiteral(val members: List<TypeMember>) : TypeScriptType()
    data class Function(
        val params: List<FunctionParam>,
        val returnType: TypeScriptType
    ) : TypeScriptType()
    data class IndexSignature(
        val keyType: TypeScriptType,
        val valueType: TypeScriptType
    ) : TypeScriptType()
    object Any : TypeScriptType()
    object Unknown : TypeScriptType()
    object Never : TypeScriptType()
    object Void : TypeScriptType()
    object Null : TypeScriptType()
    object Undefined : TypeScriptType()
}

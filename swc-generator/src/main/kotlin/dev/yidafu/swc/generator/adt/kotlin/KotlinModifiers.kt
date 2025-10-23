package dev.yidafu.swc.generator.adt.kotlin

/**
 * Kotlin 修饰符和表达式 ADT
 */

/**
 * 类修饰符
 */
sealed class ClassModifier {
    object Interface : ClassModifier()
    object SealedInterface : ClassModifier()
    object OpenClass : ClassModifier()
    object DataClass : ClassModifier()
    object Object : ClassModifier()
    object EnumClass : ClassModifier()
    object SealedClass : ClassModifier()
    object AbstractClass : ClassModifier()
    object FinalClass : ClassModifier()

    /**
     * 转换为字符串表示
     */
    fun toModifierString(): String = when (this) {
        is Interface -> "interface"
        is SealedInterface -> "sealed interface"
        is OpenClass -> "open class"
        is DataClass -> "data class"
        is Object -> "object"
        is EnumClass -> "enum class"
        is SealedClass -> "sealed class"
        is AbstractClass -> "abstract class"
        is FinalClass -> "class"
    }
}

/**
 * 属性修饰符
 */
sealed class PropertyModifier {
    object Var : PropertyModifier()
    object Val : PropertyModifier()
    object ConstVal : PropertyModifier()
    object LateinitVar : PropertyModifier()
    object OverrideVar : PropertyModifier()
    object OverrideVal : PropertyModifier()

    /**
     * 转换为字符串表示
     */
    fun toModifierString(): String = when (this) {
        is Var -> "var"
        is Val -> "val"
        is ConstVal -> "const val"
        is LateinitVar -> "lateinit var"
        is OverrideVar -> "override var"
        is OverrideVal -> "override val"
    }
}

/**
 * 函数修饰符
 */
sealed class FunctionModifier {
    object Fun : FunctionModifier()
    object OverrideFun : FunctionModifier()
    object AbstractFun : FunctionModifier()
    object OpenFun : FunctionModifier()
    object FinalFun : FunctionModifier()
    object InfixFun : FunctionModifier()
    object OperatorFun : FunctionModifier()
    object SuspendFun : FunctionModifier()

    /**
     * 转换为字符串表示
     */
    fun toModifierString(): String = when (this) {
        is Fun -> "fun"
        is OverrideFun -> "override fun"
        is AbstractFun -> "abstract fun"
        is OpenFun -> "open fun"
        is FinalFun -> "final fun"
        is InfixFun -> "infix fun"
        is OperatorFun -> "operator fun"
        is SuspendFun -> "suspend fun"
    }
}

/**
 * 表达式 ADT
 */
sealed class Expression {
    /**
     * 字面量表达式
     */
    data class Literal(val value: String) : Expression()

    /**
     * null 字面量
     */
    object NullLiteral : Expression()

    /**
     * 字符串字面量
     */
    data class StringLiteral(val value: String) : Expression()

    /**
     * 数字字面量
     */
    data class NumberLiteral(val value: String) : Expression()

    /**
     * 布尔字面量
     */
    data class BooleanLiteral(val value: Boolean) : Expression()

    /**
     * 函数调用表达式
     */
    data class FunctionCall(
        val name: String,
        val arguments: List<Expression> = emptyList()
    ) : Expression()

    /**
     * 属性访问表达式
     */
    data class PropertyAccess(
        val receiver: Expression?,
        val propertyName: String
    ) : Expression()

    /**
     * Lambda 表达式
     */
    data class LambdaExpression(
        val parameters: List<String>,
        val body: Expression
    ) : Expression()

    /**
     * 类引用表达式
     */
    data class ClassReference(val className: String) : Expression()

    /**
     * 类型转换表达式
     */
    data class TypeCast(
        val expression: Expression,
        val targetType: KotlinType
    ) : Expression()

    /**
     * 转换为字符串表示
     */
    fun toCodeString(): String = when (this) {
        is Literal -> value.escapeForKotlinPoet()
        is NullLiteral -> "null"
        is StringLiteral -> "\"${value.escapeForKotlinPoet()}\""
        is NumberLiteral -> value
        is BooleanLiteral -> value.toString()
        is FunctionCall -> {
            val args = arguments.joinToString(", ") { it.toCodeString() }
            "$name($args)"
        }
        is PropertyAccess -> {
            val receiverStr = receiver?.let { "${it.toCodeString()}." } ?: ""
            "$receiverStr$propertyName"
        }
        is LambdaExpression -> {
            val params = if (parameters.isEmpty()) "" else parameters.joinToString(", ")
            "{ $params -> ${body.toCodeString()} }"
        }
        is ClassReference -> "$className::class"
        is TypeCast -> "${expression.toCodeString()} as ${targetType.toTypeString()}"
    }

    /**
     * 为 KotlinPoet 转义字符串，防止 % 字符被误解释为格式化占位符
     */
    private fun String.escapeForKotlinPoet(): String {
        return this.replace("%", "%%")
    }
}

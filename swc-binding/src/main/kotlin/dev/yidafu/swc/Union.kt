package dev.yidafu.swc

import kotlinx.serialization.Serializable

/**
 * Union 类型用于表示 TypeScript 中的联合类型
 * 例如: string | number | boolean 对应 Union.U3<String, Number, Boolean>
 */
@Serializable
sealed class Union {

    /**
     * 二元联合类型: A | B
     */
    @Serializable
    data class U2<A, B>(
        val a: A? = null,
        val b: B? = null
    ) : Union() {
        fun isA(): Boolean = a != null
        fun isB(): Boolean = b != null

        fun getA(): A? = a
        fun getB(): B? = b

        companion object {
            fun <A, B> ofA(value: A): U2<A, B> = U2(a = value)
            fun <A, B> ofB(value: B): U2<A, B> = U2(b = value)
        }
    }

    /**
     * 三元联合类型: A | B | C
     */
    @Serializable
    data class U3<A, B, C>(
        val a: A? = null,
        val b: B? = null,
        val c: C? = null
    ) : Union() {
        fun isA(): Boolean = a != null
        fun isB(): Boolean = b != null
        fun isC(): Boolean = c != null

        fun getA(): A? = a
        fun getB(): B? = b
        fun getC(): C? = c

        companion object {
            fun <A, B, C> ofA(value: A): U3<A, B, C> = U3(a = value)
            fun <A, B, C> ofB(value: B): U3<A, B, C> = U3(b = value)
            fun <A, B, C> ofC(value: C): U3<A, B, C> = U3(c = value)
        }
    }

    /**
     * 四元联合类型: A | B | C | D
     */
    @Serializable
    data class U4<A, B, C, D>(
        val a: A? = null,
        val b: B? = null,
        val c: C? = null,
        val d: D? = null
    ) : Union() {
        fun isA(): Boolean = a != null
        fun isB(): Boolean = b != null
        fun isC(): Boolean = c != null
        fun isD(): Boolean = d != null

        fun getA(): A? = a
        fun getB(): B? = b
        fun getC(): C? = c
        fun getD(): D? = d

        companion object {
            fun <A, B, C, D> ofA(value: A): U4<A, B, C, D> = U4(a = value)
            fun <A, B, C, D> ofB(value: B): U4<A, B, C, D> = U4(b = value)
            fun <A, B, C, D> ofC(value: C): U4<A, B, C, D> = U4(c = value)
            fun <A, B, C, D> ofD(value: D): U4<A, B, C, D> = U4(d = value)
        }
    }

    /**
     * 五元联合类型: A | B | C | D | E
     */
    @Serializable
    data class U5<A, B, C, D, E>(
        val a: A? = null,
        val b: B? = null,
        val c: C? = null,
        val d: D? = null,
        val e: E? = null
    ) : Union() {
        fun isA(): Boolean = a != null
        fun isB(): Boolean = b != null
        fun isC(): Boolean = c != null
        fun isD(): Boolean = d != null
        fun isE(): Boolean = e != null

        fun getA(): A? = a
        fun getB(): B? = b
        fun getC(): C? = c
        fun getD(): D? = d
        fun getE(): E? = e

        companion object {
            fun <A, B, C, D, E> ofA(value: A): U5<A, B, C, D, E> = U5(a = value)
            fun <A, B, C, D, E> ofB(value: B): U5<A, B, C, D, E> = U5(b = value)
            fun <A, B, C, D, E> ofC(value: C): U5<A, B, C, D, E> = U5(c = value)
            fun <A, B, C, D, E> ofD(value: D): U5<A, B, C, D, E> = U5(d = value)
            fun <A, B, C, D, E> ofE(value: E): U5<A, B, C, D, E> = U5(e = value)
        }
    }
}
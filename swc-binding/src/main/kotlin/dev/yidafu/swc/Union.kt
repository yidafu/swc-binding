package dev.yidafu.swc

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.jvm.JvmStatic

/**
 * Union types for representing TypeScript union types in Kotlin.
 *
 * TypeScript union types like `string | number | boolean` are represented
 * using Union classes (U2, U3) in Kotlin.
 *
 * ## Usage Examples
 *
 * ### Binary Union (U2)
 * ```kotlin
 * // TypeScript: string | number
 * val value: Union.U2<String, Number> = Union.U2.ofA("hello")
 * // or
 * val value2: Union.U2<String, Number> = Union.U2.ofB(42)
 *
 * // Check which type it is
 * if (value.isA()) {
 *     println(value.valueOfA()) // "hello"
 * }
 * ```
 *
 * ### Ternary Union (U3)
 * ```kotlin
 * // TypeScript: string | number | boolean
 * val value: Union.U3<String, Number, Boolean> = Union.U3.ofA("hello")
 * // or
 * val value2: Union.U3<String, Number, Boolean> = Union.U3.ofB(42)
 * val value3: Union.U3<String, Number, Boolean> = Union.U3.ofC(true)
 * ```
 *
 * ### Serialization
 * Union types are automatically serialized/deserialized when used in AST nodes.
 * The serializer will try each type in order until one succeeds.
 *
 * @see Union.U2 for binary unions
 * @see Union.U3 for ternary unions
 */
@Serializable
sealed class Union {

    /**
     * Binary union type: A | B
     *
     * Represents a TypeScript union type with two possible types.
     * Only one of `a` or `b` should be non-null at a time.
     *
     * @param A First possible type
     * @param B Second possible type
     *
     * @example
     * ```kotlin
     * // TypeScript: string | number
     * val stringValue = Union.U2.ofA<String, Number>("hello")
     * val numberValue = Union.U2.ofB<String, Number>(42)
     *
     * // Check and access value
     * when {
     *     stringValue.isA() -> println(stringValue.valueOfA())
     *     stringValue.isB() -> println(stringValue.valueOfB())
     * }
     * ```
     */
    @Serializable(with = U2.U2Serializer::class)
    data class U2<A, B>(
        val a: A? = null,
        val b: B? = null
    ) : Union() {
        /**
         * Check if this union contains type A
         */
        fun isA(): Boolean = a != null

        /**
         * Check if this union contains type B
         */
        fun isB(): Boolean = b != null

        /**
         * Get the value of type A, or null if this union contains type B
         */
        fun valueOfA(): A? = a

        /**
         * Get the value of type B, or null if this union contains type A
         */
        fun valueOfB(): B? = b

        companion object {
            /**
             * Create a U2 union with type A value.
             *
             * This method is available as a static method in Java.
             *
             * @example Java usage:
             * ```java
             * Union.U2<String, Number> value = Union.U2.ofA("hello");
             * ```
             */
            @JvmStatic
            fun <A, B> ofA(value: A): U2<A, B> = U2(a = value)

            /**
             * Create a U2 union with type B value.
             *
             * This method is available as a static method in Java.
             *
             * @example Java usage:
             * ```java
             * Union.U2<String, Number> value = Union.U2.ofB(42);
             * ```
             */
            @JvmStatic
            fun <A, B> ofB(value: B): U2<A, B> = U2(b = value)

            fun <A, B> serializerFor(
                aSerializer: KSerializer<A>,
                bSerializer: KSerializer<B>
            ): KSerializer<U2<A, B>> = U2Serializer(aSerializer, bSerializer)
        }

        class U2Serializer<A, B>(
            private val aSerializer: KSerializer<A>,
            private val bSerializer: KSerializer<B>
        ) : KSerializer<U2<A, B>> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("dev.yidafu.swc.Union.U2")

            override fun serialize(encoder: Encoder, value: U2<A, B>) {
                value.a?.let {
                    encoder.encodeSerializableValue(aSerializer, it)
                    return
                }
                value.b?.let {
                    encoder.encodeSerializableValue(bSerializer, it)
                    return
                }
                throw SerializationException("Union.U2 requires either 'a' or 'b' to be non-null")
            }

            override fun deserialize(decoder: Decoder): U2<A, B> {
                if (decoder !is JsonDecoder) {
                    throw SerializationException("Union.U2 serializer currently supports only Json format")
                }
                val element = decoder.decodeJsonElement()
                val json = decoder.json

                runCatching { json.decodeFromJsonElement(aSerializer, element) }
                    .onSuccess { return U2(a = it) }

                runCatching { json.decodeFromJsonElement(bSerializer, element) }
                    .onSuccess { return U2(b = it) }

                throw SerializationException("Unable to deserialize value as either type A or type B")
            }
        }
    }

    /**
     * Ternary union type: A | B | C
     *
     * Represents a TypeScript union type with three possible types.
     * Only one of `a`, `b`, or `c` should be non-null at a time.
     *
     * @param A First possible type
     * @param B Second possible type
     * @param C Third possible type
     *
     * @example
     * ```kotlin
     * // TypeScript: string | number | boolean
     * val stringValue = Union.U3.ofA<String, Number, Boolean>("hello")
     * val numberValue = Union.U3.ofB<String, Number, Boolean>(42)
     * val boolValue = Union.U3.ofC<String, Number, Boolean>(true)
     * ```
     */
    @Serializable(with = U3.U3Serializer::class)
    data class U3<A, B, C>(
        val a: A? = null,
        val b: B? = null,
        val c: C? = null
    ) : Union() {
        /**
         * Check if this union contains type A
         */
        fun isA(): Boolean = a != null

        /**
         * Check if this union contains type B
         */
        fun isB(): Boolean = b != null

        /**
         * Check if this union contains type C
         */
        fun isC(): Boolean = c != null

        /**
         * Get the value of type A, or null if this union contains another type
         */
        fun valueOfA(): A? = a

        /**
         * Get the value of type B, or null if this union contains another type
         */
        fun valueOfB(): B? = b

        /**
         * Get the value of type C, or null if this union contains another type
         */
        fun valueOfC(): C? = c

        companion object {
            /**
             * Create a U3 union with type A value.
             * Available as a static method in Java.
             */
            @JvmStatic
            fun <A, B, C> ofA(value: A): U3<A, B, C> = U3(a = value)

            /**
             * Create a U3 union with type B value.
             * Available as a static method in Java.
             */
            @JvmStatic
            fun <A, B, C> ofB(value: B): U3<A, B, C> = U3(b = value)

            /**
             * Create a U3 union with type C value.
             * Available as a static method in Java.
             */
            @JvmStatic
            fun <A, B, C> ofC(value: C): U3<A, B, C> = U3(c = value)

            fun <A, B, C> serializerFor(
                aSerializer: KSerializer<A>,
                bSerializer: KSerializer<B>,
                cSerializer: KSerializer<C>
            ): KSerializer<U3<A, B, C>> = U3Serializer(aSerializer, bSerializer, cSerializer)
        }

        class U3Serializer<A, B, C>(
            private val aSerializer: KSerializer<A>,
            private val bSerializer: KSerializer<B>,
            private val cSerializer: KSerializer<C>
        ) : KSerializer<U3<A, B, C>> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("dev.yidafu.swc.Union.U3")

            override fun serialize(encoder: Encoder, value: U3<A, B, C>) {
                value.a?.let {
                    encoder.encodeSerializableValue(aSerializer, it)
                    return
                }
                value.b?.let {
                    encoder.encodeSerializableValue(bSerializer, it)
                    return
                }
                value.c?.let {
                    encoder.encodeSerializableValue(cSerializer, it)
                    return
                }
                throw SerializationException("Union.U3 requires one value to be non-null")
            }

            override fun deserialize(decoder: Decoder): U3<A, B, C> {
                if (decoder !is JsonDecoder) {
                    throw SerializationException("Union.U3 serializer currently supports only Json format")
                }
                val element = decoder.decodeJsonElement()
                val json = decoder.json

                runCatching { json.decodeFromJsonElement(aSerializer, element) }
                    .onSuccess { return U3(a = it) }
                runCatching { json.decodeFromJsonElement(bSerializer, element) }
                    .onSuccess { return U3(b = it) }
                runCatching { json.decodeFromJsonElement(cSerializer, element) }
                    .onSuccess { return U3(c = it) }

                throw SerializationException("Unable to deserialize value as type A, B, or C")
            }
        }
    }
}
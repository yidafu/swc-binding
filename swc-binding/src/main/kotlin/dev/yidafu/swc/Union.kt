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

/**
 * Union 类型用于表示 TypeScript 中的联合类型
 * 例如: string | number | boolean 对应 Union.U3<String, Number, Boolean>
 */
@Serializable
sealed class Union {

    /**
     * 二元联合类型: A | B
     */
    @Serializable(with = U2.U2Serializer::class)
    data class U2<A, B>(
        val a: A? = null,
        val b: B? = null
    ) : Union() {
        fun isA(): Boolean = a != null
        fun isB(): Boolean = b != null

        fun valueOfA(): A? = a
        fun valueOfB(): B? = b

        companion object {
            fun <A, B> ofA(value: A): U2<A, B> = U2(a = value)
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
     * 三元联合类型: A | B | C
     */
    @Serializable(with = U3.U3Serializer::class)
    data class U3<A, B, C>(
        val a: A? = null,
        val b: B? = null,
        val c: C? = null
    ) : Union() {
        fun isA(): Boolean = a != null
        fun isB(): Boolean = b != null
        fun isC(): Boolean = c != null

        fun valueOfA(): A? = a
        fun valueOfB(): B? = b
        fun valueOfC(): C? = c

        companion object {
            fun <A, B, C> ofA(value: A): U3<A, B, C> = U3(a = value)
            fun <A, B, C> ofB(value: B): U3<A, B, C> = U3(b = value)
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

    /**
     * 四元联合类型: A | B | C | D
     */
    @Serializable(with = U4.U4Serializer::class)
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

        fun valueOfA(): A? = a
        fun valueOfB(): B? = b
        fun valueOfC(): C? = c
        fun valueOfD(): D? = d

        companion object {
            fun <A, B, C, D> ofA(value: A): U4<A, B, C, D> = U4(a = value)
            fun <A, B, C, D> ofB(value: B): U4<A, B, C, D> = U4(b = value)
            fun <A, B, C, D> ofC(value: C): U4<A, B, C, D> = U4(c = value)
            fun <A, B, C, D> ofD(value: D): U4<A, B, C, D> = U4(d = value)

            fun <A, B, C, D> serializerFor(
                aSerializer: KSerializer<A>,
                bSerializer: KSerializer<B>,
                cSerializer: KSerializer<C>,
                dSerializer: KSerializer<D>
            ): KSerializer<U4<A, B, C, D>> =
                U4Serializer(aSerializer, bSerializer, cSerializer, dSerializer)
        }

        class U4Serializer<A, B, C, D>(
            private val aSerializer: KSerializer<A>,
            private val bSerializer: KSerializer<B>,
            private val cSerializer: KSerializer<C>,
            private val dSerializer: KSerializer<D>
        ) : KSerializer<U4<A, B, C, D>> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("dev.yidafu.swc.Union.U4")

            override fun serialize(encoder: Encoder, value: U4<A, B, C, D>) {
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
                value.d?.let {
                    encoder.encodeSerializableValue(dSerializer, it)
                    return
                }
                throw SerializationException("Union.U4 requires one value to be non-null")
            }

            override fun deserialize(decoder: Decoder): U4<A, B, C, D> {
                if (decoder !is JsonDecoder) {
                    throw SerializationException("Union.U4 serializer currently supports only Json format")
                }
                val element = decoder.decodeJsonElement()
                val json = decoder.json

                runCatching { json.decodeFromJsonElement(aSerializer, element) }
                    .onSuccess { return U4(a = it) }
                runCatching { json.decodeFromJsonElement(bSerializer, element) }
                    .onSuccess { return U4(b = it) }
                runCatching { json.decodeFromJsonElement(cSerializer, element) }
                    .onSuccess { return U4(c = it) }
                runCatching { json.decodeFromJsonElement(dSerializer, element) }
                    .onSuccess { return U4(d = it) }

                throw SerializationException("Unable to deserialize value as type A, B, C, or D")
            }
        }
    }

    /**
     * 五元联合类型: A | B | C | D | E
     */
    @Serializable(with = U5.U5Serializer::class)
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

        fun valueOfA(): A? = a
        fun valueOfB(): B? = b
        fun valueOfC(): C? = c
        fun valueOfD(): D? = d
        fun valueOfE(): E? = e

        companion object {
            fun <A, B, C, D, E> ofA(value: A): U5<A, B, C, D, E> = U5(a = value)
            fun <A, B, C, D, E> ofB(value: B): U5<A, B, C, D, E> = U5(b = value)
            fun <A, B, C, D, E> ofC(value: C): U5<A, B, C, D, E> = U5(c = value)
            fun <A, B, C, D, E> ofD(value: D): U5<A, B, C, D, E> = U5(d = value)
            fun <A, B, C, D, E> ofE(value: E): U5<A, B, C, D, E> = U5(e = value)

            fun <A, B, C, D, E> serializerFor(
                aSerializer: KSerializer<A>,
                bSerializer: KSerializer<B>,
                cSerializer: KSerializer<C>,
                dSerializer: KSerializer<D>,
                eSerializer: KSerializer<E>
            ): KSerializer<U5<A, B, C, D, E>> =
                U5Serializer(aSerializer, bSerializer, cSerializer, dSerializer, eSerializer)
        }

        class U5Serializer<A, B, C, D, E>(
            private val aSerializer: KSerializer<A>,
            private val bSerializer: KSerializer<B>,
            private val cSerializer: KSerializer<C>,
            private val dSerializer: KSerializer<D>,
            private val eSerializer: KSerializer<E>
        ) : KSerializer<U5<A, B, C, D, E>> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor("dev.yidafu.swc.Union.U5")

            override fun serialize(encoder: Encoder, value: U5<A, B, C, D, E>) {
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
                value.d?.let {
                    encoder.encodeSerializableValue(dSerializer, it)
                    return
                }
                value.e?.let {
                    encoder.encodeSerializableValue(eSerializer, it)
                    return
                }
                throw SerializationException("Union.U5 requires one value to be non-null")
            }

            override fun deserialize(decoder: Decoder): U5<A, B, C, D, E> {
                if (decoder !is JsonDecoder) {
                    throw SerializationException("Union.U5 serializer currently supports only Json format")
                }
                val element = decoder.decodeJsonElement()
                val json = decoder.json

                runCatching { json.decodeFromJsonElement(aSerializer, element) }
                    .onSuccess { return U5(a = it) }
                runCatching { json.decodeFromJsonElement(bSerializer, element) }
                    .onSuccess { return U5(b = it) }
                runCatching { json.decodeFromJsonElement(cSerializer, element) }
                    .onSuccess { return U5(c = it) }
                runCatching { json.decodeFromJsonElement(dSerializer, element) }
                    .onSuccess { return U5(d = it) }
                runCatching { json.decodeFromJsonElement(eSerializer, element) }
                    .onSuccess { return U5(e = it) }

                throw SerializationException("Unable to deserialize value as type A, B, C, D, or E")
            }
        }
    }
}
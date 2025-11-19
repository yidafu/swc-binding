// Auto-generated file. Do not edit. Generated at: 2025-11-20T00:07:52.273067

package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import kotlin.Array
import kotlin.Boolean
import kotlin.Double
import kotlin.String
import kotlin.Unit
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer

public object U2_String_ArrayString__Serializer : KSerializer<Union.U2<String, Array<String>>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U2<String, Array<String>>> =
      Union.U2.serializerFor(serializer<String>(), ArraySerializer(serializer<String>()))

  override fun serialize(encoder: Encoder, `value`: Union.U2<String, Array<String>>): Unit =
      delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): Union.U2<String, Array<String>> =
      delegate.deserialize(decoder)
}

public object U2_Boolean_String__Serializer : KSerializer<Union.U2<Boolean, String>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U2<Boolean, String>> =
      Union.U2.serializerFor(serializer<Boolean>(), serializer<String>())

  override fun serialize(encoder: Encoder, `value`: Union.U2<Boolean, String>): Unit =
      delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): Union.U2<Boolean, String> =
      delegate.deserialize(decoder)
}

public object U2_Boolean_ArrayString__Serializer : KSerializer<Union.U2<Boolean, Array<String>>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U2<Boolean, Array<String>>> =
      Union.U2.serializerFor(serializer<Boolean>(), ArraySerializer(serializer<String>()))

  override fun serialize(encoder: Encoder, `value`: Union.U2<Boolean, Array<String>>): Unit =
      delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): Union.U2<Boolean, Array<String>> =
      delegate.deserialize(decoder)
}

public object U2_TerserCompressOptions_Boolean__Serializer :
    KSerializer<Union.U2<TerserCompressOptions, Boolean>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U2<TerserCompressOptions, Boolean>> =
      Union.U2.serializerFor(serializer<TerserCompressOptions>(), serializer<Boolean>())

  override fun serialize(encoder: Encoder, `value`: Union.U2<TerserCompressOptions, Boolean>): Unit
      = delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): Union.U2<TerserCompressOptions, Boolean> =
      delegate.deserialize(decoder)
}

public object U2_TerserMangleOptions_Boolean__Serializer :
    KSerializer<Union.U2<TerserMangleOptions, Boolean>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U2<TerserMangleOptions, Boolean>> =
      Union.U2.serializerFor(serializer<TerserMangleOptions>(), serializer<Boolean>())

  override fun serialize(encoder: Encoder, `value`: Union.U2<TerserMangleOptions, Boolean>): Unit =
      delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): Union.U2<TerserMangleOptions, Boolean> =
      delegate.deserialize(decoder)
}

public object U3_Boolean_String_JsFormatOptionsComments__Serializer :
    KSerializer<Union.U3<Boolean, String, JsFormatOptions.JsFormatOptionsComments>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`:
      KSerializer<Union.U3<Boolean, String, JsFormatOptions.JsFormatOptionsComments>> =
      Union.U3.serializerFor(serializer<Boolean>(), serializer<String>(),
      serializer<JsFormatOptions.JsFormatOptionsComments>())

  override fun serialize(encoder: Encoder,
      `value`: Union.U3<Boolean, String, JsFormatOptions.JsFormatOptionsComments>): Unit =
      delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder):
      Union.U3<Boolean, String, JsFormatOptions.JsFormatOptionsComments> =
      delegate.deserialize(decoder)
}

public object U2_Double_Boolean__Serializer : KSerializer<Union.U2<Double, Boolean>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U2<Double, Boolean>> =
      Union.U2.serializerFor(serializer<Double>(), serializer<Boolean>())

  override fun serialize(encoder: Encoder, `value`: Union.U2<Double, Boolean>): Unit =
      delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): Union.U2<Double, Boolean> =
      delegate.deserialize(decoder)
}

public object U2_String_Boolean__Serializer : KSerializer<Union.U2<String, Boolean>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U2<String, Boolean>> =
      Union.U2.serializerFor(serializer<String>(), serializer<Boolean>())

  override fun serialize(encoder: Encoder, `value`: Union.U2<String, Boolean>): Unit =
      delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): Union.U2<String, Boolean> =
      delegate.deserialize(decoder)
}

public object U3_Boolean_MatchPattern_ArrayMatchPattern__Serializer :
    KSerializer<Union.U3<Boolean, MatchPattern, Array<MatchPattern>>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U3<Boolean, MatchPattern, Array<MatchPattern>>> =
      Union.U3.serializerFor(serializer<Boolean>(), serializer<MatchPattern>(),
      ArraySerializer(serializer<MatchPattern>()))

  override fun serialize(encoder: Encoder,
      `value`: Union.U3<Boolean, MatchPattern, Array<MatchPattern>>): Unit =
      delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): Union.U3<Boolean, MatchPattern, Array<MatchPattern>> =
      delegate.deserialize(decoder)
}

public object U2_Boolean_ReactConfigRefresh__Serializer :
    KSerializer<Union.U2<Boolean, ReactConfig.ReactConfigRefresh>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U2<Boolean, ReactConfig.ReactConfigRefresh>> =
      Union.U2.serializerFor(serializer<Boolean>(), serializer<ReactConfig.ReactConfigRefresh>())

  override fun serialize(encoder: Encoder,
      `value`: Union.U2<Boolean, ReactConfig.ReactConfigRefresh>): Unit =
      delegate.serialize(encoder, value)

  override fun deserialize(decoder: Decoder): Union.U2<Boolean, ReactConfig.ReactConfigRefresh> =
      delegate.deserialize(decoder)
}

public object U2_ArrayString_RecordStringString__Serializer :
    KSerializer<Union.U2<Array<String>, Record<String, String>>> {
  override val descriptor: SerialDescriptor
    get() = delegate.descriptor

  private val `delegate`: KSerializer<Union.U2<Array<String>, Record<String, String>>> =
      Union.U2.serializerFor(ArraySerializer(serializer<String>()),
      serializer<Record<String, String>>())

  override fun serialize(encoder: Encoder,
      `value`: Union.U2<Array<String>, Record<String, String>>): Unit = delegate.serialize(encoder,
      value)

  override fun deserialize(decoder: Decoder): Union.U2<Array<String>, Record<String, String>> =
      delegate.deserialize(decoder)
}

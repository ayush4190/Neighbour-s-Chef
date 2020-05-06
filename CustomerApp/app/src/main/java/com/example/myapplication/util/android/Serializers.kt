package com.example.myapplication.util.android

import android.os.ParcelUuid
import kotlinx.serialization.*
import java.util.*

@Serializer(forClass = ParcelUuid::class)
object ParcelUuidSerializer: KSerializer<ParcelUuid> {
    override val descriptor: SerialDescriptor =
        PrimitiveDescriptor(
            "com.example.myapplication.util.android.ParcelUuidSerializer",
            PrimitiveKind.STRING
        )

    override fun deserialize(decoder: Decoder): ParcelUuid =
        ParcelUuid(UUID.fromString(decoder.decodeString()))

    override fun serialize(encoder: Encoder, value: ParcelUuid) =
        encoder.encodeString(value.toString())
}
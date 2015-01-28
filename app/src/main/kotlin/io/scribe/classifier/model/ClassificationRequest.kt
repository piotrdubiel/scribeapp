package io.scribe.classifier.model

import com.google.gson.JsonElement

public trait ClassificationRequest {
    fun toByteArray(): ByteArray
    fun toJson(): JsonElement
}
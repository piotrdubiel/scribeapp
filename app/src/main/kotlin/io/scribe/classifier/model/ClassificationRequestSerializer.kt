package io.scribe.classifier.model

import com.google.gson.JsonSerializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type

public class ClassificationRequestSerializer : JsonSerializer<ClassificationRequest> {

    override fun serialize(src: ClassificationRequest, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return src.toJson()
    }
}
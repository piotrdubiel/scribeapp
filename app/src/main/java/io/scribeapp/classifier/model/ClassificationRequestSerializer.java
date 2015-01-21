package io.scribeapp.classifier.model;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ClassificationRequestSerializer implements JsonSerializer<ClassificationRequest> {

    @Override
    public JsonElement serialize(ClassificationRequest src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement jsonElement = src.toJson();
        Log.d("JSON", jsonElement.toString());
        return jsonElement;
    }
}

package io.scribeapp.classifier.model;

import com.google.gson.JsonElement;

public abstract class ClassificationRequest {

    public abstract byte[] toByteArray();
    public abstract JsonElement toJson();
}

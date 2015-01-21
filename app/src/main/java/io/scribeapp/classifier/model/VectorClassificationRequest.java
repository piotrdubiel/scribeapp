package io.scribeapp.classifier.model;

import android.util.Log;

import com.google.gson.JsonElement;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class VectorClassificationRequest extends ClassificationRequest {
    public float[] vector;

    public VectorClassificationRequest(float[] vector) {
        this.vector = vector;
        Log.d("A", Arrays.toString(vector));
    }

    @Override
    public byte[] toByteArray() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(os);
        try {
            for (float v : vector) {
                dataOutputStream.writeFloat(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    @Override
    public JsonElement toJson() {
        return null;
    }
}

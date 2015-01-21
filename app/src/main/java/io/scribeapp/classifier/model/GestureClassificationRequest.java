package io.scribeapp.classifier.model;

import android.gesture.Gesture;
import android.gesture.GestureStroke;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import io.scribeapp.classifier.utils.Utils;

public class GestureClassificationRequest extends ClassificationRequest {
    public Gesture gesture;

    public GestureClassificationRequest(Gesture gesture) {
        this.gesture = gesture;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Utils.getBitmapFromGesture(gesture).compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }

    @Override
    public JsonElement toJson() {
        JsonArray strokes = new JsonArray();
        for (GestureStroke stroke : gesture.getStrokes()) {
            JsonArray pointArray = new JsonArray();
            Field f = null;
            long[] timestamps = null;
            try {
                f = stroke.getClass().getDeclaredField("timestamps");
                f.setAccessible(true);
                timestamps = (long[]) f.get(stroke);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < stroke.points.length; i += 2) {
                JsonObject point = new JsonObject();
                point.addProperty("x", stroke.points[i]);
                point.addProperty("y", stroke.points[i + 1]);
                if (timestamps != null) {
                    point.addProperty("time", timestamps[i / 2]);
                }
                pointArray.add(point);
            }
            strokes.add(pointArray);
        }
        return strokes;
    }
}

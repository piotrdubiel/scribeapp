package io.scribe.classifier.model;

import android.gesture.Gesture;
import android.gesture.GesturePoint;
import android.gesture.GestureStroke;
import android.test.AndroidTestCase;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class GestureClassificationRequestTest extends AndroidTestCase {
    public void testConvertToJson() {
        Gesture gesture = new Gesture();
        ArrayList<GesturePoint> points = new ArrayList<>();
        points.add(new GesturePoint(0, 1, 2));
        points.add(new GesturePoint(3, 4, 5));
        gesture.addStroke(new GestureStroke(points));

        GestureClassificationRequest request = new GestureClassificationRequest(gesture);
        JsonElement json = request.toJson();

        assertTrue(json.isJsonArray());
        JsonArray array = json.getAsJsonArray();
        assertEquals(1, array.size());
        JsonArray pointsJson = array.get(0).getAsJsonArray();
        assertEquals(2, pointsJson.size());

        assertEquals(0.0, pointsJson.get(0).getAsJsonObject().get("x").getAsDouble());
        assertEquals(1.0, pointsJson.get(0).getAsJsonObject().get("y").getAsDouble());
        assertEquals(2, pointsJson.get(0).getAsJsonObject().get("time").getAsLong());

        assertEquals(3.0, pointsJson.get(1).getAsJsonObject().get("x").getAsDouble());
        assertEquals(4.0, pointsJson.get(1).getAsJsonObject().get("y").getAsDouble());
        assertEquals(5, pointsJson.get(1).getAsJsonObject().get("time").getAsLong());
    }
}

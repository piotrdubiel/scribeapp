package io.scribe.classifier.model

import android.gesture.Gesture
import java.io.ByteArrayOutputStream
import io.scribe.classifier.utils.Utils
import android.graphics.Bitmap
import com.google.gson.JsonElement
import com.google.gson.JsonArray
import java.lang.reflect.Field
import com.google.gson.JsonObject
import android.gesture.GestureStroke

public class GestureClassificationRequest(public var gesture: Gesture) : ClassificationRequest {

    override fun toByteArray(): ByteArray {
        val os = ByteArrayOutputStream()
        Utils.getBitmapFromGesture(gesture).compress(Bitmap.CompressFormat.PNG, 100, os)
        return os.toByteArray()
    }

    override fun toJson(): JsonElement {
        val strokes = JsonArray()
        for (stroke in gesture.getStrokes()) {
            val pointArray = JsonArray()

            val timestamps = extractTimestamp(stroke)

            for (i in 0..stroke.points.size() - 1 step 2) {
                val point = JsonObject()
                point.addProperty("x", stroke.points[i])
                point.addProperty("y", stroke.points[i + 1])
                point.addProperty("time", timestamps[i / 2])
                pointArray.add(point)
            }
            strokes.add(pointArray)
        }
        return strokes
    }

    private fun extractTimestamp(stroke: GestureStroke): LongArray {
        var f: Field?
        var timestamps = LongArray(0)
        try {
            f = stroke.javaClass.getDeclaredField("timestamps")
            f!!.setAccessible(true)
            timestamps = f?.get(stroke) as LongArray
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return timestamps
    }
}
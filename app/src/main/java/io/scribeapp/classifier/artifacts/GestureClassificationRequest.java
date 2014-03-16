package io.scribeapp.classifier.artifacts;

import android.gesture.Gesture;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import io.scribeapp.classifier.utils.Utils;

/**
 * Created by piotrekd on 17/03/14.
 */
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
}

package pl.scribeapp.classifier.artifacts;

import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import pl.scribeapp.classifier.utils.Utils;

/**
 * Created by piotrekd on 12/29/13.
 */
public class ClassificationRequest {
    public Gesture gesture;
    public ClassificationRequest(Gesture gesture) {
        this.gesture = gesture;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Utils.getBitmapFromGesture(gesture).compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }
}

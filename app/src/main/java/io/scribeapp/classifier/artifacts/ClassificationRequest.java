package io.scribeapp.classifier.artifacts;

import android.gesture.Gesture;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import io.scribeapp.classifier.utils.Utils;

/**
 * Created by piotrekd on 12/29/13.
 */
public abstract class ClassificationRequest {

    public abstract byte[] toByteArray();
}

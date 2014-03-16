package io.scribeapp.classifier.remote;

import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Bitmap;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.scribeapp.classifier.Classifier;
import io.scribeapp.classifier.artifacts.ClassificationRequest;
import io.scribeapp.classifier.artifacts.ClassificationResult;
import io.scribeapp.classifier.artifacts.VectorClassificationRequest;
import io.scribeapp.classifier.utils.PCA;
import io.scribeapp.classifier.utils.Utils;
import io.scribeapp.connection.ServiceConnector;
import io.scribeapp.connection.exceptions.RecognitionException;

/**
 * Created by piotrekd on 11/22/13.
 */
@Singleton
public class RemoteClassifier implements Classifier {
    private PCA pca;

    @Inject
    ServiceConnector serviceConnector;

    @Inject
    public RemoteClassifier(Context context) {
        try {
            pca = new PCA(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClassificationResult classify(Gesture gesture) throws RecognitionException {
        return serviceConnector.recognize(new VectorClassificationRequest(prepare(gesture)));
    }

    @Override
    public ClassificationResult classify(float[] sample) {
        return null;
    }

    @Override
    public float[] classifyRaw(float[] sample) {
        return new float[0];
    }

    private float[] prepare(Gesture gesture) {
        Bitmap in = Utils.getBitmapFromGesture(gesture);
        if (in == null) return null;
        if (in.getWidth() > in.getHeight()) {
            in = Bitmap.createScaledBitmap(in, 20, Math.max(20 * in.getHeight() / in.getWidth(), 1), true);
        }
        else {
            in = Bitmap.createScaledBitmap(in, Math.max(20 * in.getWidth() / in.getHeight(), 1), 20, true);
        }

        float[] sample = Utils.getVectorFromBitmap(in);

        return pca.applyPCA(sample);
    }
}

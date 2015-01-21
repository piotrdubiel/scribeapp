package io.scribeapp.classifier.remote;

import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.scribeapp.classifier.Classifier;
import io.scribeapp.classifier.model.ClassificationResult;
import io.scribeapp.classifier.model.GestureClassificationRequest;
import io.scribeapp.classifier.utils.PCA;
import io.scribeapp.classifier.utils.Utils;
import io.scribeapp.connection.APIService;

@Singleton
public class RemoteClassifier implements Classifier {
    private PCA pca;

    @Inject
    APIService apiService;

    @Inject
    public RemoteClassifier(Context context) {
        try {
            pca = new PCA(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClassificationResult classify(Gesture gesture) {
        try {
            return apiService.recognize(new GestureClassificationRequest(gesture));
        }
        catch (Exception err) {
            Log.e("RETROFIT", err.getMessage());
        }
        return null;
        //return serviceConnector.recognize(new VectorClassificationRequest(prepare(gesture)));
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
        } else {
            in = Bitmap.createScaledBitmap(in, Math.max(20 * in.getWidth() / in.getHeight(), 1), 20, true);
        }

        float[] sample = Utils.getVectorFromBitmap(in);

        return pca.applyPCA(sample);
    }
}

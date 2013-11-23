package pl.scribeapp.classifier.remote;

import android.gesture.Gesture;

import javax.inject.Inject;

import pl.scribeapp.classifier.ClassificationResult;
import pl.scribeapp.classifier.Classifier;
import pl.scribeapp.classifier.utils.ServiceConnector;

/**
 * Created by piotrekd on 11/22/13.
 */
public class RemoteClassifier implements Classifier {
    private static final String URI = "http://scribeserver.herokuapp.com";

    @Inject
    ServiceConnector serviceConnector;

    @Override
    public ClassificationResult classify(Gesture gesture, int type) {
        serviceConnector.request("recognize", gesture.toString());
        return null;
    }

    @Override
    public ClassificationResult classify(float[] sample) {
        return null;
    }

    @Override
    public float[] classifyRaw(float[] sample) {
        return new float[0];
    }
}

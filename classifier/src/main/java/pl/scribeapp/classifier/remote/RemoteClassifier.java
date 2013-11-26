package pl.scribeapp.classifier.remote;

import android.gesture.Gesture;

import javax.inject.Inject;

import pl.scribeapp.classifier.ClassificationResult;
import pl.scribeapp.classifier.Classifier;
import pl.scribeapp.classifier.net.HerokuConnector;
import pl.scribeapp.classifier.net.ServiceConnector;

/**
 * Created by piotrekd on 11/22/13.
 */
public class RemoteClassifier implements Classifier {
    ServiceConnector serviceConnector;

    public RemoteClassifier() {
        serviceConnector = new HerokuConnector();
    }

    @Override
    public ClassificationResult classify(Gesture gesture, int type) {
        return null;
    }

    @Override
    public String classify(Gesture gesture) {
        return serviceConnector.request("recognize", gesture.toString());
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

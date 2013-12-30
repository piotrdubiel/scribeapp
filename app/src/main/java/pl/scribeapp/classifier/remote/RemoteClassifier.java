package pl.scribeapp.classifier.remote;

import android.gesture.Gesture;

import javax.inject.Inject;

import pl.scribeapp.classifier.Classifier;
import pl.scribeapp.classifier.artifacts.ClassificationRequest;
import pl.scribeapp.classifier.artifacts.ClassificationResult;
import pl.scribeapp.connection.ServiceConnector;
import pl.scribeapp.connection.exceptions.RecognitionException;

/**
 * Created by piotrekd on 11/22/13.
 */
public class RemoteClassifier implements Classifier {
    @Inject
    ServiceConnector serviceConnector;

    public RemoteClassifier() {
    }

    @Override
    public ClassificationResult classify(Gesture gesture) throws RecognitionException {
        return serviceConnector.recognize(new ClassificationRequest(gesture));
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

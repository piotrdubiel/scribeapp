package io.scribeapp.classifier;

import android.gesture.Gesture;
import android.util.Log;

import javax.inject.Inject;

import io.scribeapp.classifier.meta.MetaClassifier;
import io.scribeapp.classifier.remote.RemoteClassifier;
import io.scribeapp.connection.exceptions.RecognitionException;

/**
 * Created by piotrek on 30.11.13.
 */
public class ClassificationHandler {
    @Inject
    public RemoteClassifier remoteClassifier;
    @Inject
    public MetaClassifier metaClassifier;

    @Inject
    public ClassificationHandler() {
    }

    public String classify(Gesture gesture) {
        try {
            return remoteClassifier.classify(gesture).toString();
        } catch (RecognitionException e) {
            Log.e("HANDLER", e.getMessage());
            return "None";
        }


    }
}

package pl.scribeapp.classifier;

import android.content.Context;
import android.gesture.Gesture;
import android.util.Log;

import javax.inject.Inject;

import pl.scribeapp.app.ScribeApplication;
import pl.scribeapp.classifier.meta.MetaClassifier;
import pl.scribeapp.classifier.remote.RemoteClassifier;
import pl.scribeapp.connection.exceptions.RecognitionException;

/**
 * Created by piotrek on 30.11.13.
 */
public class ClassificationHandler {
    @Inject
    public RemoteClassifier remoteClassifier;
    @Inject
    public MetaClassifier metaClassifier;

    public ClassificationHandler() {}

    public String classify(Gesture gesture) {
        try {
            return remoteClassifier.classify(gesture).toString();
        } catch (RecognitionException e) {
            Log.e("HANDLER", e.getMessage());
            return "None";
        }
    }
}

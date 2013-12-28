package pl.scribeapp.classifier;

import android.content.Context;
import android.gesture.Gesture;

import pl.scribeapp.classifier.remote.RemoteClassifier;

/**
 * Created by piotrek on 30.11.13.
 */
public class ClassificationHandler {
    private RemoteClassifier remoteClassifier;
    private MetaClassifier metaClassifier;

    public ClassificationHandler(Context context) {
        remoteClassifier = new RemoteClassifier();
        metaClassifier = new MetaClassifier(context);
    }

    public String classify(Gesture gesture) {
        return remoteClassifier.classify(gesture);
    }
}

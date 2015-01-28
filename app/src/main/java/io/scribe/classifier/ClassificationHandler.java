package io.scribe.classifier;

import android.gesture.Gesture;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.scribe.classifier.meta.MetaClassifier;
import io.scribe.classifier.model.ClassificationResult;
import io.scribe.classifier.remote.RemoteClassifier;

@Singleton
public class ClassificationHandler {
    @Inject
    public RemoteClassifier remoteClassifier;
    @Inject
    public MetaClassifier metaClassifier;

    public ClassificationHandler() {
    }

    public String classify(Gesture gesture) {
        ClassificationResult classify = remoteClassifier.classify(gesture);
        if (classify != null) {
            return classify.toString();
        }
        return null;
    }
}

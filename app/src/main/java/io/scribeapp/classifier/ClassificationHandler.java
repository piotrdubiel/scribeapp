package io.scribeapp.classifier;

import android.gesture.Gesture;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.scribeapp.classifier.meta.MetaClassifier;
import io.scribeapp.classifier.model.ClassificationRequest;
import io.scribeapp.classifier.model.ClassificationResult;
import io.scribeapp.classifier.remote.RemoteClassifier;
import io.scribeapp.classifier.utils.Meta;
import io.scribeapp.classifier.utils.PCA;
import io.scribeapp.classifier.utils.Remote;
import io.scribeapp.connection.exceptions.RecognitionException;

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

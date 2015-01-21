package io.scribeapp.classifier.remote

import android.gesture.Gesture

import javax.inject.Inject

import de.tavendo.autobahn.WebSocketConnection
import io.scribeapp.classifier.Classifier
import io.scribeapp.classifier.model.ClassificationResult
import io.scribeapp.connection.exceptions.RecognitionException

public class WebSocketClassifier : Classifier {
    var webSocketConnection: WebSocketConnection? = null
        [Inject] set

    var classifierConnectionHandler: ClassifierConnectionHandler? = null
        [Inject] set

    throws(javaClass<RecognitionException>())
    override fun classify(gesture: Gesture): ClassificationResult? {
        return null
    }

    override fun classify(sample: FloatArray): ClassificationResult? {
        return null
    }

    override fun classifyRaw(sample: FloatArray): FloatArray {
        return FloatArray(0)
    }
}

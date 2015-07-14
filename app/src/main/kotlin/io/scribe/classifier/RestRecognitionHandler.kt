package io.scribe.classifier

import io.scribe.connection.ApiService
import android.gesture.GestureOverlayView
import io.scribe.input.handwriting.HandwritingInputMethod
import io.scribe.classifier.model.GestureClassificationRequest
import java.util.concurrent.TimeUnit
import io.scribe.rx.GestureViewObservable

public class RestRecognitionHandler(inputMethod: HandwritingInputMethod, api: ApiService, view: GestureOverlayView) {
    init {
        GestureViewObservable.gestures(view)
                .sample(10, TimeUnit.MILLISECONDS)
                .flatMap { result -> api.recognize(GestureClassificationRequest(result.gesture)) }
                .subscribe { result -> inputMethod.enterWord(result.word) }
    }
}
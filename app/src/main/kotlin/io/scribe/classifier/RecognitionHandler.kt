package io.scribe.classifier

import io.scribe.connection.APIService
import android.gesture.GestureOverlayView
import io.scribe.input.handwriting.HandwritingInputMethod
import io.scribe.classifier.model.GestureClassificationRequest
import java.util.concurrent.TimeUnit
import io.scribe.rx.GestureViewObservable

public class RecognitionHandler(inputMethod: HandwritingInputMethod, api: APIService, view: GestureOverlayView) {
    {
        GestureViewObservable.gestures(view)
                .sample(10, TimeUnit.MILLISECONDS)
                .flatMap { result -> api?.recognize(GestureClassificationRequest(result.view!!.getGesture())) }
                .subscribe({ result -> inputMethod.enterWord(result.word) })
    }
}
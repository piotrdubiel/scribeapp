package io.scribe.classifier

import android.gesture.GestureOverlayView
import android.util.Log
import com.squareup.okhttp.OkHttpClient
import io.scribe.classifier.model.GestureClassificationRequest
import io.scribe.connection.APIModule
import io.scribe.rx.GestureViewObservable
import kotlinx.websocket.JetWebSocket
import kotlinx.websocket.gson.withGsonProducer
import kotlinx.websocket.newWebSocket
import kotlinx.websocket.open
import rx.lang.kotlin.toObservable
import java.util.concurrent.TimeUnit


public class WebsocketRecognitionHandler(/*inputMethod: HandwritingInputMethod, */val view: GestureOverlayView) {
    var socket: JetWebSocket? = null

    fun start() {
        Log.d("WebsocketRecognitionHandler", "STARTED")
        val gestureStream = GestureViewObservable.gestures(view)
                .map { GestureClassificationRequest(it.gesture) }

        val geoPositionObservable =
                kotlin.sequence { Math.random() }.toObservable()

        OkHttpClient().
                newWebSocket(APIModule.WEBSOCKET_URL).
                withGsonProducer(
                        geoPositionObservable
                                .sample(5L, TimeUnit.SECONDS)
                                .distinctUntilChanged()).
                open()

        socket = OkHttpClient()
                .newWebSocket(APIModule.WEBSOCKET_URL)
                .withGsonProducer(gestureStream)
                .open()



//        val observer = subscriber<ClassificationResult>()
//                .onNext { result ->
//                    inputMethod.enterWord(result.word)
//                    view.clear(false)
//
//                }

//        client.newWebSocket(APIModule.WEBSOCKET_URL)
//                .withGsonConsumer(observer).open()

    }
}
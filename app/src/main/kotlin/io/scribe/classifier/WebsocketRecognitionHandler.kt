package io.scribe.classifier

import android.content.Context
import android.gesture.GestureOverlayView
import android.util.Log
import com.google.gson.Gson
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Response
import io.scribe.app.ScribeApplication
import io.scribe.classifier.model.ClassificationResult
import io.scribe.classifier.model.GestureClassificationRequest
import io.scribe.connection.ApiModule
import io.scribe.connection.Websocket
import io.scribe.input.handwriting.HandwritingInputMethod
import io.scribe.rx.GestureViewObservable
import kotlinx.websocket.*
import kotlinx.websocket.gson.withGsonConsumer
import kotlinx.websocket.gson.withGsonProducer
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.subscriber
import javax.inject.Inject


public class WebsocketRecognitionHandler(val ctx: Context, val inputMethod: HandwritingInputMethod, val view: GestureOverlayView) {

    var websocket: Observable<String>? = null
        [Websocket] [Inject] set


    var gson: Gson? = null
        [Inject] set

    init {
        ScribeApplication.component(ctx).inject(this)
    }


    var socket: JetWebSocket? = null

    class LoggingInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            Log.d("Websocket" , "Sending request %s on ${request.url()} ${chain.connection()} ${request.headers()}")
            val response = chain.proceed(request)
            return response;
        }
    }

    fun start() {
        Log.d("WebsocketRecognitionHandler", "STARTED")
        val gestureStream = GestureViewObservable.gestures(view)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map { GestureClassificationRequest(it.gesture) }

        val observer = subscriber<ClassificationResult>()
                .onNext { result ->
                    Log.d("RESULT", result.word)
                    inputMethod.enterWord(result.word)
                    view.clear(false)

                }

        val stateObserver = subscriber<WebSocketState>()
                .onNext {
                }

        //        val gestureStream = kotlin.sequence { Math.random() }
        //                .toObservable()
        //                .sample(5L, TimeUnit.SECONDS)
        //                .distinctUntilChanged()

        val client = OkHttpClient()

        client.interceptors().add(LoggingInterceptor())

        val producer = client.newWebSocket(ApiModule.WEBSOCKET_URL)
                .withGsonConsumer(observer, gson!!)
                .withGsonProducer(gestureStream, gson!!) as JetSocketDuplex<GestureClassificationRequest, ClassificationResult>

        producer.open()
    }
}
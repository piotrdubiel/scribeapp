package io.scribe.rx.websocket

import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.squareup.okhttp.ws.WebSocket
import com.squareup.okhttp.ws.WebSocketCall
import com.squareup.okhttp.ws.WebSocketListener
import okio.Buffer
import okio.BufferedSource
import rx.Observable
import rx.Subscriber
import java.io.IOException

public class OnSubscribeWebsocket(val websocket: WebSocketCall) : Observable.OnSubscribe<String> {
    override fun call(observer: Subscriber<in String>) {
        val listener = SubscribedWebsocketListener(observer)
        websocket.enqueue(listener)
    }

    class SubscribedWebsocketListener(val subscriber: Subscriber<in String>) : WebSocketListener {
        override fun onClose(code: Int, reason: String?) {
            subscriber.onCompleted()
        }

        override fun onMessage(payload: BufferedSource?, type: WebSocket.PayloadType?) {
            when (type) {
                WebSocket.PayloadType.TEXT -> subscriber.onNext(payload?.readUtf8())
                WebSocket.PayloadType.BINARY -> subscriber.onNext(payload?.readByteString()?.hex())
                else -> throw IllegalStateException("Unknown payload type: " + type)
            }
        }

        override fun onPong(payload: Buffer?) {
        }

        override fun onFailure(e: IOException?) {
        }

        override fun onOpen(webSocket: WebSocket?, request: Request?, response: Response?) {
        }
    }
}
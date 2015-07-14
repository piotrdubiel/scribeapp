package io.scribe.rx.websocket

import com.squareup.okhttp.ws.WebSocketCall
import io.scribe.rx.OnSubscribeViewGesture
import rx.Observable
import kotlin.platform.platformStatic

public class WebsocketObservable {
    companion object {
        platformStatic fun connect(websocket: WebSocketCall): Observable<String> {
            return Observable.create(OnSubscribeWebsocket(websocket))
        }
    }
}
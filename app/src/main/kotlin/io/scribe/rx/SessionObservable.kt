package io.scribe.rx

import io.scribe.app.Navigator
import io.scribe.connection.Session
import rx.Observable
import kotlin.platform.platformStatic

public class SessionObservable private () {
    companion object {
        platformStatic fun sessions(navigator: Navigator): Observable<Session> =
                Observable.create(OnSubscribeSession(navigator))
    }
}
package io.scribe.rx

import io.scribe.app.Navigator
import io.scribe.connection.Session
import rx.Observable
import rx.Subscriber

public class OnSubscribeSession(val navigator: Navigator) : Observable.OnSubscribe<Session> {
    override fun call(t: Subscriber<in Session>?) {
        throw UnsupportedOperationException()
    }


}
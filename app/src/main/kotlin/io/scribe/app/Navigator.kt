package io.scribe.app

import io.scribe.connection.Session
import rx.Observable

public trait Navigator {
    public fun getSession(): Session
    public fun setSession(session: Session)
}
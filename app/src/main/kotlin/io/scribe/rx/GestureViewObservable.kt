package io.scribe.rx

import android.gesture.GestureOverlayView
import rx.Observable
import kotlin.platform.platformStatic

public class GestureViewObservable private () {
    companion object {
        platformStatic fun gestures(view: GestureOverlayView): Observable<OnGestureEvent> {
            return Observable.create(OnSubscribeViewGesture(view))
        }
    }
}
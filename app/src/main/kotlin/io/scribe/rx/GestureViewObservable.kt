package io.scribe.rx

import android.gesture.GestureOverlayView
import rx.Observable
import kotlin.platform.platformStatic

public class GestureViewObservable private () {
    class object {
        platformStatic fun gestures(view: GestureOverlayView): Observable<OnGestureEvent> {
            return Observable.create(OnSubscribeViewGesture(view))
        }
    }
}
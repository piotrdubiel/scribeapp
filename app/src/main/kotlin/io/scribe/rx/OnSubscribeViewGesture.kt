package io.scribe.rx

import rx.Observable
import rx.Subscriber
import android.gesture.GestureOverlayView
import rx.android.internal.Assertions
import android.view.MotionEvent
import rx.android.AndroidSubscriptions
import rx.functions.Action0

public class OnSubscribeViewGesture(val view: GestureOverlayView) : Observable.OnSubscribe<OnGestureEvent> {
    override fun call(observer: Subscriber<in OnGestureEvent>) {
        Assertions.assertUiThread()
        val listener = SubscribedOnGestureListener(observer)

        val subscription = AndroidSubscriptions.unsubscribeInUiThread({ view.removeOnGestureListener(listener) })

        view.addOnGestureListener(listener)
        observer.add(subscription)
    }

    class SubscribedOnGestureListener(val subscriber: Subscriber<in OnGestureEvent>) : GestureOverlayView.OnGestureListener {
        override fun onGestureStarted(overlay: GestureOverlayView?, event: MotionEvent?) {
        }

        override fun onGesture(overlay: GestureOverlayView?, event: MotionEvent?) {
        }

        override fun onGestureEnded(overlay: GestureOverlayView?, event: MotionEvent?) {
            subscriber.onNext(OnGestureEvent(overlay))
            subscriber.onCompleted()
        }

        override fun onGestureCancelled(overlay: GestureOverlayView?, event: MotionEvent?) {
        }

    }
}
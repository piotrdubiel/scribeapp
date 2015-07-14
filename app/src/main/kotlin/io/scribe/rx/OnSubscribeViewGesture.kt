package io.scribe.rx

import android.gesture.GestureOverlayView
import android.util.Log
import android.view.MotionEvent
import rx.Observable
import rx.Subscriber
import rx.android.AndroidSubscriptions
import rx.android.internal.Assertions

public class OnSubscribeViewGesture(val view: GestureOverlayView) : Observable.OnSubscribe<OnGestureEvent> {
    override fun call(observer: Subscriber<in OnGestureEvent>) {
        Assertions.assertUiThread()
        val listener = SubscribedOnGestureListener(observer)

        val subscription = AndroidSubscriptions.unsubscribeInUiThread({ view.removeOnGestureListener(listener) })

        view.addOnGestureListener(listener)
        observer.add(subscription)
        Log.d("GestureObserver", "CREATED")
    }

    class SubscribedOnGestureListener(val subscriber: Subscriber<in OnGestureEvent>) : GestureOverlayView.OnGestureListener {
        override fun onGestureStarted(overlay: GestureOverlayView?, event: MotionEvent?) {
        }

        override fun onGesture(overlay: GestureOverlayView?, event: MotionEvent?) {
        }

        override fun onGestureEnded(overlay: GestureOverlayView?, event: MotionEvent?) {
            val gesture = overlay?.getGesture()
            if (gesture != null) {
                Log.d("GestureObserver", "Gesture completed")
                subscriber.onNext(OnGestureEvent(gesture))
            }
        }

        override fun onGestureCancelled(overlay: GestureOverlayView?, event: MotionEvent?) {
        }

    }
}
package io.scribeapp.input.keyboard;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.util.AttributeSet;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by piotrek on 23.11.13.
 */
public class PathKeyboardView extends KeyboardView implements GestureOverlayView.OnGesturePerformedListener {
    private static final String TAG = "PathKeyboardView";

    private KeyboardView keyboardView;

    public PathKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        FrameLayout parent = new FrameLayout(context, attrs);

        keyboardView = new KeyboardView(context, attrs);

        GestureOverlayView gestureOverlayView = new GestureOverlayView(context);
        gestureOverlayView.addView(keyboardView);
        gestureOverlayView.addOnGesturePerformedListener(this);

        parent.addView(gestureOverlayView);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        Log.d(TAG, overlay.toString());

    }
}

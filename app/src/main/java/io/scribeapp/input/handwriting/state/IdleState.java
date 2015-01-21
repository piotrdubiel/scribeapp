package io.scribeapp.input.handwriting.state;

import android.gesture.GestureOverlayView;
import android.inputmethodservice.InputMethodService;
import android.view.MotionEvent;
import android.view.View;

import io.scribeapp.settings.registration.activity.RegistrationActivity;
import io.scribeapp.settings.registration.fragment.RegistrationFragment;
import io.scribeapp.utils.U;

/**
 * Created by piotrekd on 17/03/14.
 */
public class IdleState extends RecognitionState implements GestureOverlayView.OnGestureListener {


    @Override
    public void onStateEnter(InputMethodService service) {
        super.onStateEnter(service);


//        registrationFragment = (RegistrationFragment) registrationActivity.getCurrentFragment();
//        registrationFragment.errorLabel.setVisibility(View.VISIBLE);
//        registrationFragment.errorLabel.setText(reason);
//
//        registrationFragment.registerButton.setOnClickListener(this);
    }

    @Override
    public void onStateLeave(InputMethodService service) {
//        super.onStateLeave(registrationActivity);
//        registrationFragment.errorLabel.setVisibility(View.INVISIBLE);
//        registrationFragment.errorLabel.setText(U.EMPTY);
    }

    @Override
    public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
//        inputMethod.setButtonsState(false);
    }

    @Override
    public void onGesture(GestureOverlayView overlay, MotionEvent event) {}

    @Override
    public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {}

    @Override
    public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {}
}

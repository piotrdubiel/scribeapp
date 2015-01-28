package io.scribe.settings.registration.state;

import android.view.View;

import io.scribe.R;
import io.scribe.settings.registration.activity.RegistrationActivity;
import io.scribe.settings.registration.fragment.RegistrationFragment;
import io.scribe.settings.registration.state.generic.RegistrationState;
import io.scribe.utils.U;

/**
 * Created by piotrekd on 09/02/14.
 */
public class FailureState extends RegistrationState implements View.OnClickListener {
    private RegistrationFragment registrationFragment;
    private String reason;

    public FailureState(String s) {
        super();
        reason = s;
    }

    @Override
    public void onStateEnter(RegistrationActivity registrationActivity) {
        super.onStateEnter(registrationActivity);
        registrationFragment = (RegistrationFragment) registrationActivity.getCurrentFragment();
        registrationFragment.errorLabel.setVisibility(View.VISIBLE);
        registrationFragment.errorLabel.setText(reason);

        registrationFragment.registerButton.setOnClickListener(this);
    }

    @Override
    public void onStateLeave(RegistrationActivity registrationActivity) {
        super.onStateLeave(registrationActivity);
        registrationFragment.errorLabel.setVisibility(View.INVISIBLE);
        registrationFragment.errorLabel.setText(U.EMPTY);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_btn_registration_register) {
            stateContext.setState(new RegisteringState());
        }
    }
}

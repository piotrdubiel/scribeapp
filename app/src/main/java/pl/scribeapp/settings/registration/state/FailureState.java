package pl.scribeapp.settings.registration.state;

import android.view.View;

import pl.scribeapp.R;
import pl.scribeapp.settings.registration.activity.RegistrationActivity;
import pl.scribeapp.settings.registration.fragment.RegistrationFragment;
import pl.scribeapp.settings.registration.state.generic.RegistrationActivityState;
import pl.scribeapp.utils.U;

/**
 * Created by piotrekd on 09/02/14.
 */
public class FailureState extends RegistrationActivityState implements View.OnClickListener {
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

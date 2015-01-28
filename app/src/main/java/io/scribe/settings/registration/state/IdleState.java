package io.scribe.settings.registration.state;

import android.support.v4.app.Fragment;
import android.view.View;

import io.scribe.R;
import io.scribe.settings.registration.activity.RegistrationActivity;
import io.scribe.settings.registration.fragment.RegistrationFragment;
import io.scribe.settings.registration.state.generic.RegistrationState;
import io.scribe.utils.U;
import io.scribe.utils.fragment.FragmentViewCreatedListener;


public class IdleState extends RegistrationState implements View.OnClickListener, FragmentViewCreatedListener {
    private RegistrationFragment registrationFragment;

    @Override
    public void onStateEnter(RegistrationActivity stateContext) {
        super.onStateEnter(stateContext);

        registrationFragment = new RegistrationFragment();
        stateContext.changeFragment(registrationFragment);
        registrationFragment.setFragmentViewCreatedListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_btn_registration_register) {
            stateContext.setState(new RegisteringState());
        }

    }

    @Override
    public void onFragmentViewCreated(Fragment fragment) {
        registrationFragment.errorLabel.setText(U.EMPTY);
        registrationFragment.errorLabel.setVisibility(View.INVISIBLE);

        registrationFragment.emailField.setText("piotrek0d@gmail.com");
        registrationFragment.passwordField.setText("123456");
        registrationFragment.retypeField.setText("123456");
        registrationFragment.registerButton.setEnabled(true);
        registrationFragment.registerButton.setOnClickListener(this);
    }
}

package pl.scribeapp.settings.registration.state;

import android.support.v4.app.Fragment;
import android.view.View;

import pl.scribeapp.R;
import pl.scribeapp.settings.account.activity.AccountActivity;
import pl.scribeapp.settings.account.fragment.LoginFragment;
import pl.scribeapp.settings.account.state.LoggingState;
import pl.scribeapp.settings.account.state.generic.AccountActivityState;
import pl.scribeapp.settings.registration.activity.RegistrationActivity;
import pl.scribeapp.settings.registration.fragment.RegistrationFragment;
import pl.scribeapp.settings.registration.state.generic.RegistrationActivityState;
import pl.scribeapp.utils.U;
import pl.scribeapp.utils.fragment.FragmentViewCreatedListener;


public class IdleState extends RegistrationActivityState implements View.OnClickListener, FragmentViewCreatedListener {
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

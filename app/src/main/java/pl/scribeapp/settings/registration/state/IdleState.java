package pl.scribeapp.settings.registration.state;

import android.support.v4.app.Fragment;
import android.view.View;

import pl.scribeapp.R;
import pl.scribeapp.settings.account.activity.AccountActivity;
import pl.scribeapp.settings.account.fragment.LoginFragment;
import pl.scribeapp.settings.account.state.LoggingState;
import pl.scribeapp.settings.account.state.generic.AccountActivityState;
import pl.scribeapp.settings.registration.activity.RegistrationActivity;
import pl.scribeapp.settings.registration.state.generic.RegistrationActivityState;
import pl.scribeapp.utils.fragment.FragmentViewCreatedListener;


public class IdleState extends RegistrationActivityState implements View.OnClickListener, FragmentViewCreatedListener {
    private LoginFragment loginFragment;

    @Override
    public void onStateEnter(RegistrationActivity stateContext) {
        super.onStateEnter(stateContext);

        loginFragment = new LoginFragment();
        stateContext.changeFragment(loginFragment);
        loginFragment.setFragmentViewCreatedListener(this);
    }

    @Override
    public void onStateLeave(RegistrationActivity stateContext) {
        super.onStateLeave(stateContext);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onFragmentViewCreated(Fragment fragment) {
        loginFragment.emailField.setText("piotrek0d@gmail.com");
        loginFragment.passwordField.setText("123456");
        loginFragment.loginButton.setEnabled(true);
        loginFragment.loginButton.setOnClickListener(this);
    }
}

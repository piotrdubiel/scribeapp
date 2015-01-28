package io.scribe.settings.account.state;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import io.scribe.R;
import io.scribe.settings.account.activity.AccountActivity;
import io.scribe.settings.account.fragment.LoginFragment;
import io.scribe.settings.account.state.generic.AccountState;
import io.scribe.settings.registration.activity.RegistrationActivity;
import io.scribe.utils.fragment.FragmentViewCreatedListener;


public class IdleState extends AccountState implements View.OnClickListener, FragmentViewCreatedListener {
    private LoginFragment loginFragment;

    @Override
    public void onStateEnter(AccountActivity stateContext) {
        super.onStateEnter(stateContext);

        loginFragment = new LoginFragment();
        stateContext.changeFragment(loginFragment);
        loginFragment.setFragmentViewCreatedListener(this);
    }

    @Override
    public void onStateLeave(AccountActivity stateContext) {
        super.onStateLeave(stateContext);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_login_sign:
                stateContext.setState(new LoggingState());
                break;
            case R.id.id_btn_login_register:
                stateContext.startActivity(new Intent(stateContext, RegistrationActivity.class));
                stateContext.finish();
                break;
        }
    }

    @Override
    public void onFragmentViewCreated(Fragment fragment) {
        loginFragment.emailField.setText("piotrek0d@gmail.com");
        loginFragment.passwordField.setText("123456");
        loginFragment.loginButton.setEnabled(true);
        loginFragment.loginButton.setOnClickListener(this);
        loginFragment.registerButton.setEnabled(true);
        loginFragment.registerButton.setOnClickListener(this);
    }
}

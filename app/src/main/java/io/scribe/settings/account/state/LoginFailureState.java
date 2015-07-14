package io.scribe.settings.account.state;

import android.view.View;

import io.scribe.R;
import io.scribe.account.AccountActivity;
import io.scribe.settings.account.fragment.LoginFragment;
import io.scribe.settings.account.state.generic.AccountState;

public class LoginFailureState extends AccountState implements View.OnClickListener {

    private String reason;
    private LoginFragment loginFragment;

    public LoginFailureState(String reason) {
        this.reason = reason;
    }

    @Override
    public void onStateEnter(AccountActivity stateContext) {
        super.onStateEnter(stateContext);
        loginFragment = (LoginFragment) stateContext.getCurrentFragment();
        loginFragment.emailField.setText("Error");
        loginFragment.passwordField.setText("");
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.id_login_fragment_container) {
//            stateContext.setState(new IdleState());
//        }
    }
}

package pl.scribeapp.settings.account.state;

import android.view.View;

import pl.scribeapp.R;
import pl.scribeapp.settings.account.activity.AccountActivity;
import pl.scribeapp.settings.account.fragment.LoginFragment;
import pl.scribeapp.settings.account.state.generic.AccountActivityState;

public class LoginFailureState extends AccountActivityState implements View.OnClickListener {

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
        if (v.getId() == R.id.id_login_fragment_container) {
            stateContext.setState(new IdleState());
        }
    }
}

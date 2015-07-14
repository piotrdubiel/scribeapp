package io.scribe.settings.account.state;

import javax.inject.Inject;

import io.scribe.app.Navigator;
import io.scribe.connection.ServiceConnector;
import io.scribe.connection.Session;
import io.scribe.connection.utils.ConnectionListener;
import io.scribe.account.AccountActivity;
import io.scribe.settings.account.async.LoginAsyncTask;
import io.scribe.settings.account.fragment.LoginFragment;
import io.scribe.settings.account.state.generic.AccountState;
import io.scribe.settings.account.state.generic.Lockable;

public class LoggingState extends AccountState implements Lockable, ConnectionListener {
    @Inject
    ServiceConnector serviceConnector;

    @Inject
    Navigator navigator;

    private LoginFragment loginFragment;

    @Override
    public void onStateEnter(AccountActivity stateContext) {
        super.onStateEnter(stateContext);
//        stateContext.inject(this);
        loginFragment = (LoginFragment) stateContext.getCurrentFragment();
        String username = loginFragment.emailField.getText().toString();
        String password = loginFragment.passwordField.getText().toString();
        lock();
        new LoginAsyncTask(serviceConnector, username, password, this).execute();
    }

    @Override
    public void onOK(Session session) {
        navigator.setSession(session);
        stateContext.setState(new LoggedInState());
    }

    @Override
    public void onError(String message) {
        stateContext.setState(new LoginFailureState(message));
    }

    @Override
    public void onStateLeave(AccountActivity activity) {
        unlock();
        super.onStateLeave(activity);
    }

    @Override
    public void lock() {
        loginFragment.emailField.setEnabled(false);
        loginFragment.passwordField.setEnabled(false);
    }

    @Override
    public void unlock() {
        loginFragment.emailField.setEnabled(true);
        loginFragment.passwordField.setEnabled(true);
    }
}

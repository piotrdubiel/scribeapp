package io.scribeapp.settings.account.state;

import javax.inject.Inject;

import io.scribeapp.app.Navigator;
import io.scribeapp.connection.ServiceConnector;
import io.scribeapp.connection.Session;
import io.scribeapp.connection.utils.ConnectionListener;
import io.scribeapp.settings.account.activity.AccountActivity;
import io.scribeapp.settings.account.async.LoginAsyncTask;
import io.scribeapp.settings.account.fragment.LoginFragment;
import io.scribeapp.settings.account.state.generic.AccountActivityState;
import io.scribeapp.settings.account.state.generic.Lockable;

public class LoggingState extends AccountActivityState implements Lockable, ConnectionListener {
    @Inject
    ServiceConnector serviceConnector;

    @Inject
    Navigator navigator;

    private LoginFragment loginFragment;

    @Override
    public void onStateEnter(AccountActivity stateContext) {
        super.onStateEnter(stateContext);
        stateContext.inject(this);
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

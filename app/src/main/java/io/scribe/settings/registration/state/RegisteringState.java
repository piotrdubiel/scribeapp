package io.scribe.settings.registration.state;

import android.content.Intent;

import javax.inject.Inject;

import io.scribe.app.Navigator;
import io.scribe.connection.ServiceConnector;
import io.scribe.connection.Session;
import io.scribe.connection.utils.ConnectionListener;
import io.scribe.settings.account.activity.AccountActivity;
import io.scribe.settings.account.state.generic.Lockable;
import io.scribe.settings.registration.activity.RegistrationActivity;
import io.scribe.settings.registration.async.RegisterAsyncTask;
import io.scribe.settings.registration.fragment.RegistrationFragment;
import io.scribe.settings.registration.state.generic.RegistrationState;

/**
 * Created by piotrekd on 09/02/14.
 */
public class RegisteringState extends RegistrationState implements Lockable, ConnectionListener {
    @Inject
    ServiceConnector serviceConnector;

    @Inject
    Navigator navigator;

    private RegistrationFragment registrationFragment;

    @Override
    public void onStateEnter(RegistrationActivity stateContext) {
        super.onStateEnter(stateContext);
        stateContext.inject(this);
        registrationFragment = (RegistrationFragment) stateContext.getCurrentFragment();
        String username = registrationFragment.emailField.getText().toString();
        String password = registrationFragment.passwordField.getText().toString();
        String retype = registrationFragment.retypeField.getText().toString();
        if (!password.equals(retype)) {
            stateContext.setState(new FailureState("Passwords don't match"));
        }
        else {
            lock();
            new RegisterAsyncTask(serviceConnector, username, password, this).execute();
        }
    }

    @Override
    public void onOK(Session session) {
        navigator.setSession(session);
        stateContext.startActivity(new Intent(stateContext, AccountActivity.class));
        stateContext.finish();
    }

    @Override
    public void onError(String message) {
        stateContext.setState(new FailureState(message));
    }

    @Override
    public void lock() {

    }

    @Override
    public void unlock() {

    }
}

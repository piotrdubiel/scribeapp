package io.scribeapp.settings.registration.state;

import android.content.Intent;

import javax.inject.Inject;

import io.scribeapp.app.Navigator;
import io.scribeapp.connection.ServiceConnector;
import io.scribeapp.connection.Session;
import io.scribeapp.connection.utils.ConnectionListener;
import io.scribeapp.settings.account.activity.AccountActivity;
import io.scribeapp.settings.account.state.generic.Lockable;
import io.scribeapp.settings.registration.activity.RegistrationActivity;
import io.scribeapp.settings.registration.async.RegisterAsyncTask;
import io.scribeapp.settings.registration.fragment.RegistrationFragment;
import io.scribeapp.settings.registration.state.generic.RegistrationState;

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

package pl.scribeapp.settings.registration.state;

import android.content.Intent;

import javax.inject.Inject;

import pl.scribeapp.app.Navigator;
import pl.scribeapp.connection.ServiceConnector;
import pl.scribeapp.connection.Session;
import pl.scribeapp.connection.utils.ConnectionListener;
import pl.scribeapp.settings.account.activity.AccountActivity;
import pl.scribeapp.settings.account.async.LoginAsyncTask;
import pl.scribeapp.settings.account.fragment.LoginFragment;
import pl.scribeapp.settings.account.state.LoggedInState;
import pl.scribeapp.settings.account.state.generic.Lockable;
import pl.scribeapp.settings.registration.activity.RegistrationActivity;
import pl.scribeapp.settings.registration.async.RegisterAsyncTask;
import pl.scribeapp.settings.registration.fragment.RegistrationFragment;
import pl.scribeapp.settings.registration.state.generic.RegistrationActivityState;

/**
 * Created by piotrekd on 09/02/14.
 */
public class RegisteringState extends RegistrationActivityState implements Lockable, ConnectionListener {
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

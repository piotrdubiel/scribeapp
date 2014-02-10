package pl.scribeapp.settings.account.activity;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import pl.scribeapp.R;
import pl.scribeapp.app.Navigator;
import pl.scribeapp.settings.account.state.IdleState;
import pl.scribeapp.settings.account.state.LoggedInState;
import pl.scribeapp.settings.account.state.generic.AccountActivityState;
import pl.scribeapp.utils.U;
import pl.scribeapp.utils.fragment.BaseFragment;
import pl.scribeapp.utils.inject.InjectFragmentActivity;
import pl.scribeapp.utils.state.StateChanger;

public class AccountActivity extends InjectFragmentActivity<BaseFragment> {

    @Inject
    Navigator navigator;

    private StateChanger<AccountActivityState> stateChanger;

    @Inject
    public AccountActivity() {
        super();
    }

    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_login);
        container_id = R.id.id_login_fragment_container;
        stateChanger = new StateChanger<AccountActivityState>(this);
        if (navigator.getSession() == null) {
            setState(new IdleState());
        }
        else {
            setState(new LoggedInState());
        }
    }

    @Override
    protected List<Object> getModules() {
        return U.NO_MODULES;
    }

    public void setState(AccountActivityState state) {
        stateChanger.setState(state);
    }

    @Override
    public void finish() {
        super.finish();
    }

//    /**
//     * Attempts to sign in or register the account specified by the login form.
//     * If there are form errors (invalid email, missing fields, etc.), the
//     * errors are presented and no actual login attempt is made.
//     */
//    public void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }
//
//        // Reset errors.
//        mEmailView.setError(null);
//        mPasswordView.setError(null);
//
//        // Store values at the time of the login attempt.
//        mEmail = mEmailView.getText().toString();
//        mPassword = mPasswordView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password.
//        if (TextUtils.isEmpty(mPassword)) {
//            mPasswordView.setError(getString(R.string.error_field_required));
//            focusView = mPasswordView;
//            cancel = true;
//        } else if (mPassword.length() < 4) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(mEmail)) {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        } else if (!mEmail.contains("@")) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
//            showProgress(true);
//            mAuthTask = new UserLoginTask();
//            mAuthTask.execute((Void) null);
//        }
//    }

    /**
     * Shows the progress UI and hides the login form.

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    */

//    /**
//     * Represents an asynchronous login/registration task used to authenticate
//     * the user.
//     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }
//
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }
}

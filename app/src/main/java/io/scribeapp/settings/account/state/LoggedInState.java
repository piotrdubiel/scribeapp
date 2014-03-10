package io.scribeapp.settings.account.state;

import android.support.v4.app.Fragment;
import android.view.View;

import javax.inject.Inject;

import io.scribeapp.R;
import io.scribeapp.app.Navigator;
import io.scribeapp.connection.Session;
import io.scribeapp.settings.account.activity.AccountActivity;
import io.scribeapp.settings.account.fragment.LogoutFragment;
import io.scribeapp.settings.account.state.generic.AccountActivityState;
import io.scribeapp.utils.GravatarUtility;
import io.scribeapp.utils.fragment.FragmentViewCreatedListener;

/**
 * Created by piotrekd on 1/5/14.
 */
public class LoggedInState extends AccountActivityState implements View.OnClickListener, FragmentViewCreatedListener {
    @Inject
    Navigator navigator;

    private LogoutFragment logoutFragment;

    @Override
    public void onStateEnter(AccountActivity loginActivity) {
        super.onStateEnter(loginActivity);
        stateContext.inject(this);
        logoutFragment = new LogoutFragment();
        stateContext.changeFragment(logoutFragment);
        logoutFragment.setFragmentViewCreatedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_btn_logout) {
            navigator.setSession(null);
            stateContext.setState(new IdleState());
        }
    }

    @Override
    public void onFragmentViewCreated(Fragment fragment) {
        Session session = navigator.getSession();
        logoutFragment.usernameLabel.setText(session.username);
        logoutFragment.tokenLabel.setText(session.token);
        logoutFragment.logoutButton.setOnClickListener(this);
        logoutFragment.logoutButton.setEnabled(true);

        logoutFragment.imageView.setImageBitmap(GravatarUtility.hash(session.token));
    }
}

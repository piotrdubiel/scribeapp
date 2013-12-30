package pl.scribeapp.settings.account.state;

import android.support.v4.app.Fragment;
import android.view.View;

import javax.inject.Inject;

import pl.scribeapp.R;
import pl.scribeapp.app.Navigator;
import pl.scribeapp.connection.Session;
import pl.scribeapp.settings.account.activity.AccountActivity;
import pl.scribeapp.settings.account.fragment.LogoutFragment;
import pl.scribeapp.settings.account.state.generic.AccountActivityState;
import pl.scribeapp.utils.GravatarUtility;
import pl.scribeapp.utils.fragment.FragmentViewCreatedListener;

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
//        if (v.getId() == R.id.logout_btn) {
//            navigator.setSession(null);
//            stateContext.setState(new IdleState());
//        }
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

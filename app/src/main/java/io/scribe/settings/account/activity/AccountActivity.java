package io.scribe.settings.account.activity;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import io.scribe.R;
import io.scribe.app.Navigator;
import io.scribe.settings.account.state.IdleState;
import io.scribe.settings.account.state.LoggedInState;
import io.scribe.settings.account.state.generic.AccountState;
import io.scribe.utils.U;
import io.scribe.utils.fragment.BaseFragment;
import io.scribe.utils.inject.InjectFragmentActivity;
import io.scribe.utils.state.StateChanger;

public class AccountActivity extends InjectFragmentActivity<BaseFragment> {

    @Inject
    Navigator navigator;

    private StateChanger<AccountState> stateChanger;

    @Inject
    public AccountActivity() {
        super();
    }

    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_login);
        container_id = R.id.id_login_fragment_container;
        stateChanger = new StateChanger<>(this);
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

    public void setState(AccountState state) {
        stateChanger.setState(state);
    }

    @Override
    public void finish() {
        super.finish();
    }
}

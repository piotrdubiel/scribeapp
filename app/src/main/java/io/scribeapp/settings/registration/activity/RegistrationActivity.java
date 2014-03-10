package io.scribeapp.settings.registration.activity;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import io.scribeapp.R;
import io.scribeapp.app.Navigator;
import io.scribeapp.settings.registration.state.IdleState;
import io.scribeapp.settings.registration.state.generic.RegistrationActivityState;
import io.scribeapp.utils.U;
import io.scribeapp.utils.fragment.BaseFragment;
import io.scribeapp.utils.inject.InjectFragmentActivity;
import io.scribeapp.utils.state.StateChanger;

public class RegistrationActivity extends InjectFragmentActivity<BaseFragment> {

    @Inject
    Navigator navigator;

    private StateChanger<RegistrationActivityState> stateChanger;

    @Inject
    public RegistrationActivity() {
        super();
    }

    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_registration);
        container_id = R.id.id_registration_fragment_container;
        stateChanger = new StateChanger<RegistrationActivityState>(this);
        stateChanger.setState(new IdleState());
    }

    @Override
    protected List<Object> getModules() {
        return U.NO_MODULES;
    }

    public void setState(RegistrationActivityState state) {
        stateChanger.setState(state);
    }

    @Override
    public void finish() {
        super.finish();
    }
}

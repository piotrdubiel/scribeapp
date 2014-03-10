package io.scribeapp.settings.account.state;

import io.scribeapp.settings.account.activity.AccountActivity;
import io.scribeapp.settings.account.state.generic.AccountActivityState;

public class InvalidPasswordState extends AccountActivityState {
    private String messageKey;

    public InvalidPasswordState(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public void onStateEnter(AccountActivity stateContext) {
        super.onStateEnter(stateContext);
        stateContext.setState(new IdleState());
    }
}

package io.scribe.settings.account.state;

import io.scribe.settings.account.activity.AccountActivity;
import io.scribe.settings.account.state.generic.AccountState;

public class InvalidPasswordState extends AccountState {
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

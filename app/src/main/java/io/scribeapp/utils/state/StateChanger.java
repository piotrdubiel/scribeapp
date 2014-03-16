package io.scribeapp.utils.state;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/**
 * Created by piotrekd on 12/30/13.
 */
public class StateChanger<STATE extends State> {
    protected STATE currentState;
    protected STATE previousState;

    private Context context;

    public StateChanger(Context context) {
        this.context = context;
    }

    public void setState(STATE state) {
        Log.d("StateChanger", "Changing state from " + currentState + " to " + state);
        previousState = currentState;
        if (previousState != null) {
            previousState.onStateLeave(context);
        }
        currentState = state;
        currentState.onStateEnter(context);
    }

    public STATE currentState() {
        return currentState;
    }

    public STATE getPreviousState() {
        return this.previousState;
    }
}

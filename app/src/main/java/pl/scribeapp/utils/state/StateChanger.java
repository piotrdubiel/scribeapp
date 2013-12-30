package pl.scribeapp.utils.state;

import android.app.Activity;
import android.util.Log;

/**
 * Created by piotrekd on 12/30/13.
 */
public class StateChanger<STATE extends ActivityState> {
    protected STATE currentState;
    protected STATE previousState;

    private Activity activity;

    public StateChanger(Activity activity) {
        this.activity = activity;
    }

    public void setState(STATE state) {
        Log.d("StateChanger", "Changing state from " + currentState + " to " + state);
        previousState = currentState;
        if (previousState != null) {
            previousState.onStateLeave(activity);
        }
        currentState = state;
        currentState.onStateEnter(activity);
    }

    public STATE currentState() {
        return currentState;
    }

    public STATE getPreviousState() {
        return this.previousState;
    }
}

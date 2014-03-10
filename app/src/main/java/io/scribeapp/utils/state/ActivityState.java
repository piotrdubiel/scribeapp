package io.scribeapp.utils.state;

/**
 * Created by piotrekd on 12/30/13.
 */
public class ActivityState<CONTEXT> {
    protected CONTEXT stateContext;

    public void onStateEnter(CONTEXT context) {
        stateContext = context;
    }

    public void onStateLeave(CONTEXT context) {
        stateContext = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

package io.scribe.utils.inject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import dagger.ObjectGraph;
import io.scribe.app.ScribeApplication;

/**
 * Created by piotrekd on 12/29/13.
 */
public abstract class InjectFragmentActivity<F extends Fragment> extends FragmentActivity {
    private ObjectGraph objectGraph;
    private F currentFragment;
    private boolean visible;
    protected int container_id;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        ScribeApplication application = (ScribeApplication) getApplication();
        objectGraph = application.getObjectGraph().plus(getModules().toArray());
        objectGraph.inject(this);
    }

    public F getCurrentFragment() {
        return currentFragment;
    }

    public void changeFragment(F newFragment) {
        currentFragment = newFragment;
        // TODO
        if (visible) {
            setCurrentFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        visible = true;
        setCurrentFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
        visible = false;
    }

    private void setCurrentFragment() {
        if (currentFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container_id, currentFragment)
                    .commit();
        }
    }

    protected abstract List<Object> getModules();

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}

package io.scribe.utils.inject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.List;

public abstract class InjectFragmentActivity<F extends Fragment> extends FragmentActivity {
    private F currentFragment;
    private boolean visible;
    protected int container_id;

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
}

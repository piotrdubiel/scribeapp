package io.scribe.utils.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by piotrekd on 16/01/14.
 */
public interface FragmentViewCreatedListener {
    void onFragmentViewCreated(Fragment fragment);

    FragmentViewCreatedListener NULL = new FragmentViewCreatedListener() {
        @Override
        public void onFragmentViewCreated(Fragment fragment) {
        }
    };
}

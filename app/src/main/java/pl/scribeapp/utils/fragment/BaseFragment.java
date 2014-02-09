package pl.scribeapp.utils.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by piotrekd on 16/01/14.
 */
public class BaseFragment extends Fragment {
    FragmentViewCreatedListener fragmentViewCreatedListener = FragmentViewCreatedListener.NULL;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, getActivity());
        fragmentViewCreatedListener.onFragmentViewCreated(this);
    }

    public void setFragmentViewCreatedListener(FragmentViewCreatedListener fragmentViewCreatedListener) {
        this.fragmentViewCreatedListener = fragmentViewCreatedListener;
    }

    public void clearOnFragmentCreatedListener() {
        fragmentViewCreatedListener = FragmentViewCreatedListener.NULL;
    }
}

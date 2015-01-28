package io.scribe.settings.account.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.InjectView;
import io.scribe.R;
import io.scribe.utils.fragment.BaseFragment;

/**
 * Created by piotrekd on 1/5/14.
 */
public class LogoutFragment extends BaseFragment {
    @InjectView(R.id.id_btn_logout)
    public Button logoutButton;
    @InjectView(R.id.id_tv_logout_username)
    public  TextView usernameLabel;
    @InjectView(R.id.id_tv_logout_token)
    public  TextView tokenLabel;
    @InjectView(R.id.id_iv_logout_token)
    public ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, null);
    }
}

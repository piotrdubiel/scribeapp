package pl.scribeapp.settings.account.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.InjectView;
import pl.scribeapp.R;

/**
 * Created by piotrekd on 1/5/14.
 */
public class LogoutFragment extends BaseAccountFragment {
//    @InjectView(R.id.logout_btn)
    public Button logoutButton;
    @InjectView(R.id.logout_username_lb)
    public  TextView usernameLabel;
//    @InjectView(R.id.logout_token_lb)
    public  TextView tokenLabel;
//    @InjectView(R.id.logout_token_iv)
    public ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, null);
    }
}

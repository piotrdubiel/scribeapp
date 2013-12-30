package pl.scribeapp.settings.account.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.InjectView;
import pl.scribeapp.R;

/**
 * Created by piotrekd on 1/5/14.
 */
public class LoginFragment extends BaseAccountFragment {
    @InjectView(R.id.login_email_fd)
    public EditText emailField;
    @InjectView(R.id.login_password_fd)
    public EditText passwordField;
    @InjectView(R.id.login_sign_btn)
    public Button loginButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }
}

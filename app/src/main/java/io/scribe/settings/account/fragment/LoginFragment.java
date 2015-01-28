package io.scribe.settings.account.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.InjectView;
import io.scribe.R;
import io.scribe.utils.fragment.BaseFragment;

/**
 * Created by piotrekd on 1/5/14.
 */
public class LoginFragment extends BaseFragment {
    @InjectView(R.id.id_et_login_email)
    public EditText emailField;
    @InjectView(R.id.id_et_login_password)
    public EditText passwordField;
    @InjectView(R.id.id_btn_login_sign)
    public Button loginButton;
    @InjectView(R.id.id_btn_login_register)
    public Button registerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }
}

package pl.scribeapp.settings.registration.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.InjectView;
import pl.scribeapp.R;
import pl.scribeapp.utils.fragment.BaseFragment;

/**
 * Created by piotrekd on 09/02/14.
 */
public class RegistrationFragment extends BaseFragment {
    @InjectView(R.id.id_et_registration_email)
    public EditText emailField;
    @InjectView(R.id.id_et_registration_password)
    public EditText passwordField;
    @InjectView(R.id.id_et_registration_password_retype)
    public EditText retypeField;
    @InjectView(R.id.id_btn_registration_register)
    public Button registerButton;
    @InjectView(R.id.id_tv_registration_error_message)
    public TextView errorLabel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, null);
    }
}

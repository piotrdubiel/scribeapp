package io.scribe.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.bindView
import com.google.common.base.Strings.isNullOrEmpty
import io.scribe.R
import io.scribe.app.Navigator
import io.scribe.app.ScribeApplication
import io.scribe.connection.ApiService
import io.scribe.connection.LoginRequest
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.android.widget.OnTextChangeEvent
import rx.android.widget.WidgetObservable
import rx.schedulers.Schedulers
import javax.inject.Inject
import kotlin.properties.Delegates

public class LoginActivity : Activity(), View.OnClickListener {
    val emailTextView: TextView by bindView(R.id.id_et_login_email)

    val passwordTextView: TextView by bindView(R.id.id_et_login_password)
    val signInButton: Button by bindView(R.id.id_btn_login_sign)

    val registerButton: Button by bindView(R.id.id_btn_login_register)
    val emailObservable: Observable<OnTextChangeEvent> by Delegates.lazy {
        WidgetObservable.text(emailTextView, true)
    }

    val passwordObservable: Observable<OnTextChangeEvent> by Delegates.lazy {
        WidgetObservable.text(passwordTextView, true)
    }

    var navigator: Navigator? = null
        [Inject] set

    var api: ApiService? = null
        [Inject] set

    private var subscription: Subscription? = null

    companion object {
        fun intent(ctx: Context) = Intent(ctx, javaClass<AccountActivity>())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ScribeApplication.component(this).inject(this)

        with(signInButton) {
            setEnabled(false)
            setOnClickListener(this@LoginActivity)
            setEnabled(true)
        }

        with(registerButton) {
            setEnabled(false)
            setOnClickListener(this@LoginActivity)
        }

        emailTextView.setText("piotrek0d@gmail.com")
        passwordTextView.setText("123456")


        validateFields()
    }

    override fun onPause() {
        super<Activity>.onPause()
        subscription?.unsubscribe()
    }

    override fun onClick(view: View): Unit =
            when (view.getId()) {
                R.id.id_btn_login_sign -> login()
                else -> throw UnsupportedOperationException()
            }

    private fun validateFields() {
        subscription = Observable.combineLatest<OnTextChangeEvent, OnTextChangeEvent, Boolean>(
                emailObservable, passwordObservable,
                { emailChangeEvent, passwordChangeEvent ->
                    val emailValid = !isNullOrEmpty(emailChangeEvent.text().toString())
                            && EMAIL_ADDRESS.matcher(emailChangeEvent.text()).matches()
                    if (!emailValid) {
                        emailTextView.setError("Invalid Email")
                    }

                    val passValid = !isNullOrEmpty(passwordChangeEvent.text().toString())
                            && passwordChangeEvent.text().length() > 5
                    if (!passValid) {
                        passwordTextView.setError("Invalid Password")
                    }
                    emailValid && passValid
                }
        ).subscribe { valid ->
            signInButton.setEnabled(valid)
        }
    }

    private fun login() {
        val request = LoginRequest(
                emailTextView.getText().toString(),
                passwordTextView.getText().toString()
        )

        api!!.login(request)
                .map { session ->
                    navigator?.setSession(session)
                    session
                }
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { session -> startActivity(AccountActivity.intent(this)) }
    }
}

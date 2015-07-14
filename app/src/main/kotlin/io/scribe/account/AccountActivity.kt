package io.scribe.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.bindView
import io.scribe.R
import io.scribe.app.Navigator
import io.scribe.app.ScribeApplication
import io.scribe.connection.Session
import io.scribe.settings.account.state.IdleState
import io.scribe.settings.account.state.LoggedInState
import io.scribe.settings.account.state.generic.AccountState
import io.scribe.utils.U
import io.scribe.utils.fragment.BaseFragment
import io.scribe.utils.inject.InjectFragmentActivity
import io.scribe.utils.state.StateChanger
import javax.inject.Inject

public class AccountActivity : InjectFragmentActivity<BaseFragment>() {
    val usernameTextView: TextView by bindView(R.id.id_tv_account_username)
    val tokenTextView: TextView by bindView(R.id.id_tv_account_token)
    val sessionLayout: View by bindView(R.id.id_layout_account_session)

    val signInButton: Button by bindView(R.id.id_btn_account_sign)
    val registerButton: Button by bindView(R.id.id_btn_account_register)
    val logoutButton: Button by bindView(R.id.id_btn_account_logout)

    var navigator: Navigator? = null
        [Inject] set

    companion object {
        fun intent(ctx: Context) = Intent(ctx, javaClass<AccountActivity>())
    }

    override fun onCreate(savedInstanceBundle: Bundle?) {
        super.onCreate(savedInstanceBundle)
        setContentView(R.layout.activity_account)
        ScribeApplication.component(this).inject(this)

        initButtons()
    }

    override fun onResume() {
        super.onResume()
        val session = navigator?.getSession()
        if (session != null) {
            show(session)
        } else {
            setupForNoSession()
        }
    }

    public fun setState(state: AccountState) {}

    private fun setupForNoSession() {
        signInButton.setVisibility(View.VISIBLE)
        registerButton.setVisibility(View.VISIBLE)
        logoutButton.setVisibility(View.GONE)
        sessionLayout.setVisibility(View.GONE)
    }

    private fun show(session: Session) {
        usernameTextView.setText(session.username)
        tokenTextView.setText(session.token)

        signInButton.setVisibility(View.GONE)
        registerButton.setVisibility(View.GONE)
        logoutButton.setVisibility(View.VISIBLE)
        sessionLayout.setVisibility(View.VISIBLE)
    }

    private fun initButtons() {
        signInButton.setOnClickListener { actionSignin(it) }
        registerButton.setOnClickListener { actionRegister(it) }
        logoutButton.setOnClickListener { actionLogout(it) }
    }

    private fun actionSignin(view: View) {
        startActivityForResult(LoginActivity.intent(this), 0)
    }

    private fun actionRegister(view: View) {

    }

    private fun actionLogout(view: View) {

    }
}
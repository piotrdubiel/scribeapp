package io.scribe.app

import android.app.Application
import android.content.Context
import io.scribe.connection.Session
import io.scribe.input.MainInputService
import io.scribe.utils.SessionLoader
import io.scribe.utils.inject.RegistrableInputMethodService
import rx.Observable
import java.util.ArrayList
import javax.inject.Inject

public class ScribeApplication : BaseApplication() {
    private var session: Session? = null

    private var inputServiceInstance: RegistrableInputMethodService? = null
    var sessionLoader: SessionLoader? = null
        [Inject] set

    override fun onCreate() {
        super<BaseApplication>.onCreate()
        component.inject(this)
    }

    companion object {

        fun component(context: Context): ApplicationComponent {
            return (context.getApplicationContext() as ScribeApplication).component
        }
    }
    override fun getSession(): Session {
        if (session == null) {
            session = sessionLoader?.load()
        }
        return session!!
    }

    override fun setSession(session: Session) {
        sessionLoader?.save(session)
        this.session = session
    }

    public fun getInputMethodService(): RegistrableInputMethodService {
        if (inputServiceInstance == null) {
            throw IllegalStateException("No registered input method.")
        }
        return inputServiceInstance!!
    }

    public fun registerInputMethodService(ims: RegistrableInputMethodService) {
        this.inputServiceInstance = ims
    }

    public fun unregisterInputMethodService() {
        inputServiceInstance = null
    }
}
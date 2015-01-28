package io.scribe.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.scribe.connection.APIModule;
import io.scribe.input.InputModule;
import io.scribe.input.MainInputService;
import io.scribe.input.handwriting.HandwritingInputMethod;
import io.scribe.input.keyboard.KeyboardInputMethod;
import io.scribe.settings.account.activity.AccountActivity;
import io.scribe.settings.account.async.LoginAsyncTask;
import io.scribe.settings.account.state.LoggedInState;
import io.scribe.settings.account.state.LoggingState;
import io.scribe.settings.registration.activity.RegistrationActivity;
import io.scribe.settings.registration.state.RegisteringState;
import io.scribe.utils.SessionLoader;

/**
 * Created by piotrekd on 12/28/13.
 */
@Module(
        complete = false,
        includes = {
                APIModule.class,
                InputModule.class
        },
        injects = {
                ScribeApplication.class,
                AccountActivity.class,
                RegistrationActivity.class,
                LoginAsyncTask.class,
                HandwritingInputMethod.class,
                KeyboardInputMethod.class,
                LoggingState.class,
                LoggedInState.class,
                RegisteringState.class,
                SessionLoader.class,
                MainInputService.class,
        }
)
public class ApplicationModule {
    private final ScribeApplication application;

    public ApplicationModule(ScribeApplication application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    Navigator provideNavigator() {
        return application;
    }

    @Provides
    ScribeApplication provideApplication() {
        return application;
    }
}

package io.scribeapp.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.scribeapp.connection.APIModule;
import io.scribeapp.input.InputModule;
import io.scribeapp.input.MainInputService;
import io.scribeapp.input.handwriting.HandwritingInputMethod;
import io.scribeapp.input.keyboard.KeyboardInputMethod;
import io.scribeapp.settings.account.activity.AccountActivity;
import io.scribeapp.settings.account.async.LoginAsyncTask;
import io.scribeapp.settings.account.state.LoggedInState;
import io.scribeapp.settings.account.state.LoggingState;
import io.scribeapp.settings.registration.activity.RegistrationActivity;
import io.scribeapp.settings.registration.state.RegisteringState;
import io.scribeapp.utils.SessionLoader;

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
    private ScribeApplication application;

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

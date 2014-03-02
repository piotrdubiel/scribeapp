package pl.scribeapp.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pl.scribeapp.classifier.ClassifierModule;
import pl.scribeapp.classifier.remote.RemoteClassifier;
import pl.scribeapp.connection.ConnectionModule;
import pl.scribeapp.input.MainInputService;
import pl.scribeapp.input.handwriting.HandwritingInputMethod;
import pl.scribeapp.input.keyboard.KeyboardInputMethod;
import pl.scribeapp.settings.account.activity.AccountActivity;
import pl.scribeapp.settings.account.async.LoginAsyncTask;
import pl.scribeapp.settings.account.state.LoggedInState;
import pl.scribeapp.settings.account.state.LoggingState;
import pl.scribeapp.settings.registration.activity.RegistrationActivity;
import pl.scribeapp.settings.registration.state.RegisteringState;
import pl.scribeapp.utils.SessionLoader;
import pl.scribeapp.utils.inject.ForApplication;

/**
 * Created by piotrekd on 12/28/13.
 */
@Module(
        library = true,
        complete = false,
        includes = {
                ConnectionModule.class,
                ClassifierModule.class
        },
        injects = {
                AccountActivity.class,
                RegistrationActivity.class,
                LoginAsyncTask.class,
                HandwritingInputMethod.class,
                KeyboardInputMethod.class,
                LoggingState.class,
                LoggedInState.class,
                RegisteringState.class,
                SessionLoader.class,
                RemoteClassifier.class,
                MainInputService.class
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

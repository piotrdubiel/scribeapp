package pl.scribeapp.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pl.scribeapp.classifier.ClassifierModule;
import pl.scribeapp.classifier.remote.RemoteClassifier;
import pl.scribeapp.connection.ConnectionModule;
import pl.scribeapp.input.ScribeInputService;
import pl.scribeapp.input.handwriting.HandwritingInputMethod;
import pl.scribeapp.input.keyboard.KeyboardInputMethod;
import pl.scribeapp.settings.account.activity.AccountActivity;
import pl.scribeapp.settings.account.async.LoginAsyncTask;
import pl.scribeapp.settings.account.state.LoggedInState;
import pl.scribeapp.settings.account.state.LoggingState;
import pl.scribeapp.utils.SessionLoader;

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
                LoginAsyncTask.class,
                HandwritingInputMethod.class,
                KeyboardInputMethod.class,
                LoggingState.class,
                LoggedInState.class,
                SessionLoader.class,
                RemoteClassifier.class,
                ScribeInputService.class
        }
)
public class ScribeModule {
    private ScribeApplication application;

    public ScribeModule(ScribeApplication application) {
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
}

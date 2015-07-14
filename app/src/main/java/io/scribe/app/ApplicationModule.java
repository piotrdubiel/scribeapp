package io.scribe.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.scribe.classifier.ClassifierModule;
import io.scribe.connection.ApiModule;
import io.scribe.input.InputModule;

@Module(
        includes = {
                ApiModule.class,
                InputModule.class,
                ClassifierModule.class
        }
//        ,
//        injects = {
//                ScribeApplication.class,
//                AccountActivity.class,r
//                RegistrationActivity.class,
//                LoginAsyncTask.class,
//                HandwritingInputMethod.class,
//                KeyboardInputMethod.class,
//                LoggingState.class,
//                LoggedInState.class,
//                RegisteringState.class,
//                SessionLoader.class,
//                MainInputService.class,
//        }
)
public class ApplicationModule {
    private final BaseApplication application;

    public ApplicationModule(BaseApplication application) {
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
    BaseApplication provideApplication() {
        return application;
    }
}

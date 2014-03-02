package pl.scribeapp.test;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import dagger.Module;
import dagger.Provides;
import org.robolectric.Robolectric;
import pl.scribeapp.app.ApplicationModule;
import pl.scribeapp.app.Navigator;
import pl.scribeapp.app.ScribeApplication;
import pl.scribeapp.classifier.ann.NetworkTest;
import pl.scribeapp.classifier.remote.RemoteClassifierTest;
import pl.scribeapp.connection.ServiceConnector;
import pl.scribeapp.input.MainInputService;
import pl.scribeapp.input.handwriting.HandwritingInputMethodTest;
import pl.scribeapp.settings.account.state.LoggingStateTest;
import pl.scribeapp.utils.SessionLoaderTest;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;

@Module(
        injects = {
                LoggingStateTest.class,
                RemoteClassifierTest.class,
                NetworkTest.class,
                HandwritingInputMethodTest.class,
                SessionLoaderTest.class,
                MainInputService.class
        },
        includes = ApplicationModule.class,
        overrides = true,
        library = true
)
public class TestModule {
    @Provides
    @Singleton
    ServiceConnector provideMockServiceConnector() {
        return mock(ServiceConnector.class);
    }

    @Provides
    Context provideMockContext() {
        return mock(Context.class);
    }

    @Provides
    Navigator provideMockNavigator() {
        return mock(Navigator.class);
    }

    @Provides
    ScribeApplication provideTestApplication() {
        return (ScribeApplication) Robolectric.application;
    }

    @Provides
    InputMethodService provideMockInputMethodService() {
        return mock(MainInputService.class);
    }
}
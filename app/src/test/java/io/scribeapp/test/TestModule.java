package io.scribeapp.test;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import dagger.Module;
import dagger.Provides;
import org.robolectric.Robolectric;
import io.scribeapp.app.ApplicationModule;
import io.scribeapp.app.Navigator;
import io.scribeapp.app.ScribeApplication;
import io.scribeapp.classifier.ann.NetworkTest;
import io.scribeapp.classifier.remote.RemoteClassifierTest;
import io.scribeapp.connection.ServiceConnector;
import io.scribeapp.input.MainInputService;
import io.scribeapp.input.handwriting.HandwritingInputMethodTest;
import io.scribeapp.settings.account.state.LoggingStateTest;
import io.scribeapp.utils.SessionLoaderTest;

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
package io.scribe.test;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import org.robolectric.Robolectric;
import io.scribe.app.ApplicationModule;
import io.scribe.app.Navigator;
import io.scribe.app.ScribeApplication;
import io.scribe.classifier.ann.NetworkTest;
import io.scribe.classifier.remote.RemoteClassifierTest;
import io.scribe.connection.ServiceConnector;
import io.scribe.input.MainInputService;
import io.scribe.input.handwriting.HandwritingInputMethodTest;
import io.scribe.settings.account.state.LoggingStateTest;
import io.scribe.utils.SessionLoaderTest;

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
    MainInputService provideMockMainInputService() {
        return mock(MainInputService.class);
    }
}
package pl.scribeapp.test;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.scribeapp.app.Navigator;
import pl.scribeapp.app.ScribeModule;
import pl.scribeapp.classifier.ann.NetworkTest;
import pl.scribeapp.classifier.remote.RemoteClassifier;
import pl.scribeapp.classifier.remote.RemoteClassifierTest;
import pl.scribeapp.connection.ServiceConnector;
import pl.scribeapp.input.InputMethodServiceTest;
import pl.scribeapp.input.handwriting.HandwritingInputMethodTest;
import pl.scribeapp.settings.account.state.LoggingStateTest;
import pl.scribeapp.utils.SessionLoaderTest;

import static org.mockito.Mockito.mock;

@Module(
        injects = {
                LoggingStateTest.class,
                RemoteClassifierTest.class,
                NetworkTest.class,
                HandwritingInputMethodTest.class,
                InputMethodServiceTest.class,
                SessionLoaderTest.class,
        },
        includes = ScribeModule.class,
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
    Context provideContext() {
        return mock(Context.class);
    }

    @Provides
    Navigator provideNavigator() {
        return mock(Navigator.class);
    }
}
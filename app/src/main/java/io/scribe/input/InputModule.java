package io.scribe.input;

import dagger.Module;
import dagger.Provides;
import io.scribe.app.ScribeApplication;
import io.scribe.utils.inject.RegistrableInputMethodService;

@Module
public class InputModule {
    @Provides
    RegistrableInputMethodService provideInputMethodService(ScribeApplication application) {
        return application.getInputMethodService();
    }
}

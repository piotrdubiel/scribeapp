package io.scribe.input;

import dagger.Module;
import dagger.Provides;
import io.scribe.app.ScribeApplication;

@Module(
        library = true,
        complete = false
)
public class InputModule {
    @Provides
    MainInputService provideInputMethodService(ScribeApplication application) {
        return application.getInputMethodService();
    }
}

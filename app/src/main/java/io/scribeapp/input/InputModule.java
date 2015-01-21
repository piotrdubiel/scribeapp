package io.scribeapp.input;

import dagger.Module;
import dagger.Provides;
import io.scribeapp.app.ScribeApplication;

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

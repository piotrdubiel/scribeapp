package io.scribeapp.input;

import android.inputmethodservice.InputMethodService;

import dagger.Module;
import dagger.Provides;
import io.scribeapp.app.ScribeApplication;

/**
 * Created by piotrekd on 12/29/13.
 */
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

package pl.scribeapp.input;

import android.inputmethodservice.InputMethodService;

import dagger.Module;
import dagger.Provides;
import pl.scribeapp.app.ScribeApplication;

/**
 * Created by piotrekd on 12/29/13.
 */
@Module(
        library = true,
        complete = false
)
public class InputModule {
    @Provides
    InputMethodService provideInputMethodService(ScribeApplication application) {
        return application.getInputMethodService();
    }
}

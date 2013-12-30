package pl.scribeapp.input;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.scribeapp.input.handwriting.HandwritingInputMethod;
import pl.scribeapp.input.keyboard.KeyboardInputMethod;

/**
 * Created by piotrekd on 12/29/13.
 */
@Module(
        library = true,
        complete = false
)
public class InputModule {
    @Provides @Singleton
    HandwritingInputMethod provideHandwritingInputMethod() {
        return new HandwritingInputMethod();
    }

//    @Provides @Singleton
//    KeyboardInputMethod provideKeyboardInputMethod() {
//        return new KeyboardInputMethod();
//    }
}

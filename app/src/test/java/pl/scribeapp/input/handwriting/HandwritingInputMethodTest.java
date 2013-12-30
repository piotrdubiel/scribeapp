package pl.scribeapp.input.handwriting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import javax.inject.Inject;

import pl.scribeapp.R;
import pl.scribeapp.app.ScribeApplication;
import pl.scribeapp.classifier.remote.RemoteClassifier;
import pl.scribeapp.input.ScribeInputService;
import pl.scribeapp.test.RobolectricGradleTestRunner;

import static junit.framework.Assert.assertNotNull;
import static pl.scribeapp.test.TestInject.injectMocks;

/**
 * @author Piotr Dubiel
 */

@RunWith(RobolectricGradleTestRunner.class)
public class HandwritingInputMethodTest {
    HandwritingInputMethod inputMethod;

    @Before
    public void setUp() {
        injectMocks(this);
        //inputMethod.initWithService(scribeInputService, R.layout.gesture_input_view);
        inputMethod = new HandwritingInputMethod();
        ((ScribeApplication) Robolectric.application).inject(inputMethod);
    }

    @Test
    public void testInject() {
        assertNotNull(inputMethod);
        //assertNotNull(inputMethod.service);
        //assertNotNull(inputMethod.deleteKey);
        assertNotNull(inputMethod.classificationHandler);
    }
}

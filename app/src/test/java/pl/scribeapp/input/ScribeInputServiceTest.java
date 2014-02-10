package pl.scribeapp.input;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import pl.scribeapp.test.RobolectricGradleTestRunner;

import static junit.framework.Assert.assertNotNull;
import static pl.scribeapp.test.TestInject.injectMocks;

/**
 * Created by piotrekd on 1/2/14.
 */
@RunWith(RobolectricGradleTestRunner.class)
public class ScribeInputServiceTest {
    @Inject
    ScribeInputService scribeInputService;

    @Before
    public void setUp() {
        injectMocks(this);
    }

    @Test
    public void shouldInjectMembers() {
        assertNotNull(scribeInputService);
        assertNotNull(scribeInputService.getApplication());
        assertNotNull(scribeInputService.handwritingInputMethod);
        assertNotNull(scribeInputService.keyboardInputMethod);
        assertNotNull(scribeInputService.handwritingInputMethod.classificationHandler);
        assertNotNull(scribeInputService.handwritingInputMethod.classificationHandler.remoteClassifier);
        assertNotNull(scribeInputService.handwritingInputMethod.classificationHandler.metaClassifier);
    }

}

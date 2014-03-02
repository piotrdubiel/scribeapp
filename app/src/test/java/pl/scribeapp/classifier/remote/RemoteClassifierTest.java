package pl.scribeapp.classifier.remote;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import pl.scribeapp.app.ScribeApplication;
import pl.scribeapp.test.RobolectricGradleTestRunner;

import static pl.scribeapp.test.TestInject.injectMocks;

/**
 * @author Piotr Dubiel
 */

@RunWith(RobolectricGradleTestRunner.class)
public class RemoteClassifierTest {
    RemoteClassifier remoteClassifier;

    @Before
    public void setUp() {
        injectMocks(this);
        remoteClassifier = new RemoteClassifier();
        ((ScribeApplication)Robolectric.application).inject(remoteClassifier);
    }

    @Test
    public void shouldInjectServiceConnector() {
        Assert.assertNotNull(remoteClassifier);
        Assert.assertNotNull(remoteClassifier.serviceConnector);
    }
}

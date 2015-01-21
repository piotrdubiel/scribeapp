package io.scribeapp.classifier.remote;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import io.scribeapp.app.ScribeApplication;
import io.scribeapp.test.RobolectricGradleTestRunner;

import static io.scribeapp.test.TestInject.injectMocks;

/**
 * @author Piotr Dubiel
 */

@RunWith(RobolectricGradleTestRunner.class)
public class RemoteClassifierTest {
    RemoteClassifier remoteClassifier;

    @Before
    public void setUp() {
        injectMocks(this);
        final ScribeApplication application = (ScribeApplication) Robolectric.application;
        remoteClassifier = new RemoteClassifier(application.getApplicationContext());
        application.inject(remoteClassifier);
    }

    @Test
    public void shouldInjectServiceConnector() {
        Assert.assertNotNull(remoteClassifier);
        Assert.assertNotNull(remoteClassifier.serviceConnector);
    }
}

//package io.scribe.settings.account.state;
//
//import junit.framework.Assert;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import javax.inject.Inject;
//
//import io.scribe.test.RobolectricGradleTestRunner;
//
//import static io.scribe.test.TestInject.injectMocks;
//
///**
// * @author Piotr Dubiel
// */
//@RunWith(RobolectricGradleTestRunner.class)
//public class LoggingStateTest {
//    @Inject
//    LoggingState state;
//
//    @Before
//    public void setUp() {
//        injectMocks(this);
//    }
//
//    @Test
//    public void shouldInject() {
//        Assert.assertNotNull(state);
//        Assert.assertNotNull(state.navigator);
//        Assert.assertNotNull(state.serviceConnector);
//    }
//}

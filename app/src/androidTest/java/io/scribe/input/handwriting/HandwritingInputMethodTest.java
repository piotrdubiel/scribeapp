//package io.scribe.input.handwriting;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//
//import javax.inject.Inject;
//
//import io.scribe.app.ScribeApplication;
//import io.scribe.input.MainInputService;
//import io.scribe.test.RobolectricGradleTestRunner;
//
//import static junit.framework.Assert.assertNotNull;
//import static org.fest.assertions.api.Assertions.assertThat;
//import static io.scribe.test.TestInject.injectMocks;
//
///**
// * @author Piotr Dubiel
// */
//
//@RunWith(RobolectricGradleTestRunner.class)
//public class HandwritingInputMethodTest {
//    HandwritingInputMethod inputMethod;
//
//    @Inject
//    MainInputService service;
//
//    @Before
//    public void setUp() {
//        injectMocks(this);
//        final ScribeApplication application = (ScribeApplication) Robolectric.application;
//        application.registerInputMethodService(service);
//        inputMethod = new HandwritingInputMethod(Robolectric.application);
//    }
//
//    @Test
//    public void shouldReferenceInputMethodService() {
//        assertThat(inputMethod);
//        assertThat(inputMethod.service)
//                .isNotNull()
//                .isInstanceOf(MainInputService.class);
//    }
//
//    @Test
//    public void shouldInjectViews() {
//        // given
//        assertThat(inputMethod.deleteKey).isNull();
//        assertThat(inputMethod.gestureView).isNull();
//        assertThat(inputMethod.enterKey).isNull();
//        assertThat(inputMethod.keyboardSwitch).isNull();
//        assertThat(inputMethod.supportSymbolKeyboardView).isNull();
//        assertThat(inputMethod.spaceKey).isNull();
//        assertThat(inputMethod.service).isNotNull();
//
//        assertThat(inputMethod.service.getLayoutInflater()).isNotNull();
//
//        // when
//        inputMethod.onCreateView();
//
//        // then
//        assertThat(inputMethod.deleteKey).isNull();
//        assertThat(inputMethod.gestureView).isNull();
//        assertThat(inputMethod.enterKey).isNull();
//        assertThat(inputMethod.keyboardSwitch).isNull();
//        assertThat(inputMethod.supportSymbolKeyboardView).isNull();
//        assertThat(inputMethod.spaceKey).isNull();
//    }
//}

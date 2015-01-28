package io.scribe.input;


import android.app.Application;
import android.content.Intent;
import android.test.ServiceTestCase;

/**
 * Created by piotrekd on 1/2/14.
 */
public class MainInputServiceTest extends ServiceTestCase<MainInputService> {
    MainInputService mainInputService;

    public MainInputServiceTest() {
        super(MainInputService.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        Intent startIntent = new Intent();
        startIntent.setClass(getSystemContext(), MainInputService.class);
        startService(startIntent);

        mainInputService = getService();
        setApplication((Application) mainInputService.getApplicationContext());
        mainInputService.onCreate();
        mainInputService.onCreateInputView();
    }

    public void testInject() {
        assertNotNull(mainInputService);
        assertNotNull(mainInputService.getApplication());
        assertNotNull(mainInputService.handwritingInputMethod);
        assertNotNull(mainInputService.keyboardInputMethod);
        assertNotNull(mainInputService.handwritingInputMethod.classificationHandler);
        assertNotNull(mainInputService.handwritingInputMethod.classificationHandler.metaClassifier);
        assertNotNull(mainInputService.handwritingInputMethod.classificationHandler.remoteClassifier);
    }

}

package pl.scribeapp.test;

import org.robolectric.Robolectric;

import dagger.ObjectGraph;
import pl.scribeapp.app.ApplicationModule;
import pl.scribeapp.app.ScribeApplication;
import pl.scribeapp.settings.account.activity.AccountActivity;

/**
 * Created by piotrekd on 1/2/14.
 */
public class TestInject {
    public static void injectMocks(Object inject) {
        ObjectGraph.create(new ApplicationModule((ScribeApplication) Robolectric.application), new TestModule()).inject(inject);
    }
}

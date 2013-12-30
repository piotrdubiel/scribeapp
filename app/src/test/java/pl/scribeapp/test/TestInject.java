package pl.scribeapp.test;

import org.robolectric.Robolectric;

import java.net.URI;

import dagger.ObjectGraph;
import pl.scribeapp.app.ScribeApplication;
import pl.scribeapp.app.ScribeModule;

/**
 * Created by piotrekd on 1/2/14.
 */
public class TestInject {
    public static void injectMocks(Object inject) {
        ObjectGraph.create(new ScribeModule((ScribeApplication) Robolectric.application), new TestModule()).inject(inject);
    }
}

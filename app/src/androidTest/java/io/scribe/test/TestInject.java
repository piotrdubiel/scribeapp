package io.scribe.test;

import org.robolectric.Robolectric;

import dagger.ObjectGraph;
import io.scribe.app.ApplicationModule;
import io.scribe.app.ScribeApplication;

public class TestInject {
    public static void injectMocks(Object inject) {
        ObjectGraph.create(new ApplicationModule((ScribeApplication) Robolectric.application), new TestModule()).inject(inject);
    }
}

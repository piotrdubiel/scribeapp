package io.scribeapp.test;

import org.robolectric.Robolectric;

import dagger.ObjectGraph;
import io.scribeapp.app.ApplicationModule;
import io.scribeapp.app.ScribeApplication;

public class TestInject {
    public static void injectMocks(Object inject) {
        ObjectGraph.create(new ApplicationModule((ScribeApplication) Robolectric.application), new TestModule()).inject(inject);
    }
}

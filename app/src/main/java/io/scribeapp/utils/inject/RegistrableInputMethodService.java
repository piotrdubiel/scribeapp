package io.scribeapp.utils.inject;

import android.inputmethodservice.InputMethodService;

import java.util.List;

import dagger.ObjectGraph;
import io.scribeapp.app.ScribeApplication;

/**
 * Created by piotrekd on 12/29/13.
 */
public abstract class RegistrableInputMethodService extends InputMethodService {
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        ScribeApplication application = ScribeApplication.get(this);
        objectGraph = application.getObjectGraph().plus(getModules().toArray());
        application.registerInputMethodService(this);
        objectGraph.inject(this);
    }

    protected abstract List<Object> getModules();

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}
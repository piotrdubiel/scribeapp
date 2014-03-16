package io.scribeapp.utils.inject;

import android.inputmethodservice.InputMethodService;

import java.util.List;

import dagger.ObjectGraph;
import io.scribeapp.app.ScribeApplication;
import io.scribeapp.input.MainInputService;

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
        application.registerInputMethodService((MainInputService) this);
        objectGraph.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ScribeApplication.get(this).unregisterInputMethodService();
    }

    protected abstract List<Object> getModules();

    public void inject(Object object) {
        objectGraph.inject(object);
    }
}
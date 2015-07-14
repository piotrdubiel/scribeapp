package io.scribe.utils.inject;

import android.inputmethodservice.InputMethodService;

import java.util.List;

import io.scribe.app.ScribeApplication;

public abstract class RegistrableInputMethodService extends InputMethodService {

    @Override
    public void onCreate() {
        super.onCreate();
        ((ScribeApplication) getApplication()).registerInputMethodService(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ScribeApplication) getApplication()).unregisterInputMethodService();
    }

    protected abstract List<Object> getModules();
}
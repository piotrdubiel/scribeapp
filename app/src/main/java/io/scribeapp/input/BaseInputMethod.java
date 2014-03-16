package io.scribeapp.input;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import io.scribeapp.app.ScribeApplication;

public abstract class BaseInputMethod {

    public MainInputService service;
    public View inputView;
    private ObjectGraph objectGraph;
    private int viewId;

    public BaseInputMethod(Context context, int viewId) {
        this.viewId = viewId;
        service = ScribeApplication.get(context).getInputMethodService();
    }

    public void onCreateView() {
        inputView = service.getLayoutInflater().inflate(viewId, null);
        ButterKnife.inject(this, inputView);
    }

    public abstract void resetModifiers();

    public abstract void enterWord(CharSequence word);
}

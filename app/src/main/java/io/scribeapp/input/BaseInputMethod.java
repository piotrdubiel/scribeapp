package io.scribeapp.input;

import android.app.Application;
import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.View;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import io.scribeapp.app.ScribeApplication;

public abstract class BaseInputMethod {
public MainInputService service;
    public View inputView;
    private ObjectGraph objectGraph;
    private int viewId;

    public BaseInputMethod(Context context, int viewId) {
        final ScribeApplication scribeApplication = ScribeApplication.get(context);
        final InputMethodService inputMethodService = scribeApplication.getInputMethodService();
        this.service = (MainInputService) inputMethodService;
        this.viewId = viewId;
    }

    public void onCreateView() {
        inputView = service.getLayoutInflater().inflate(viewId, null);
        ButterKnife.inject(this, inputView);
    }
	
	public abstract void resetModifiers();

    public abstract void enterWord(CharSequence word);
}

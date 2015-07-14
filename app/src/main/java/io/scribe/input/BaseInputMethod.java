package io.scribe.input;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import io.scribe.app.ScribeApplication;

public abstract class BaseInputMethod {

    public MainInputService service;
    public View inputView;
    private int layoutId;
    private LayoutInflater inflater;

    public BaseInputMethod(Context context, LayoutInflater inflater, int layoutId) {
        this.layoutId = layoutId;
        this.inflater = inflater;
    }

    public BaseInputMethod(Context context, int layoutId) {
        this.layoutId = layoutId;
        this.service = (MainInputService) ((ScribeApplication) context.getApplicationContext()).getInputMethodService();
        this.inflater = service.getLayoutInflater();
    }

    public void onCreateView() {
        inputView = inflater.inflate(layoutId, null);
        ButterKnife.inject(this, inputView);
    }

    public abstract void resetModifiers();

    public abstract void enterWord(CharSequence word);
}

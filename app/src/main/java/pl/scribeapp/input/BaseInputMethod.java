package pl.scribeapp.input;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodSubtype;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import pl.scribeapp.app.ScribeApplication;
import pl.scribeapp.utils.inject.ForApplication;

public abstract class BaseInputMethod {
public MainInputService service;
    public View inputView;
    private ObjectGraph objectGraph;
    private int viewId;

    public BaseInputMethod(Context context, int viewId) {
        this.service = (MainInputService) ((ScribeApplication) context.getApplicationContext()).getInputMethodService();
        this.viewId = viewId;
    }

    public void onCreateView() {
        inputView = service.getLayoutInflater().inflate(viewId, null);
        ButterKnife.inject(this, inputView);
    }
	
	public abstract void resetModifiers();

    public abstract void enterWord(CharSequence word);
}

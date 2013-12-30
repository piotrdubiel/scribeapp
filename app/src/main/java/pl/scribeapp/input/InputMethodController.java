package pl.scribeapp.input;

import android.view.View;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import pl.scribeapp.app.ScribeApplication;

public abstract class InputMethodController {
	public View inputView;
    public ScribeInputService service;
    private ObjectGraph objectGraph;

    public void initWithService(ScribeInputService service, int viewId) {
        this.service = service;
        ScribeApplication application = (ScribeApplication) service.getApplication();
        objectGraph = application.getObjectGraph();
        objectGraph.inject(this);
        inputView = service.getLayoutInflater().inflate(viewId, null);
        ButterKnife.inject(this, inputView);
    }
	
	public abstract void resetModifiers();
}

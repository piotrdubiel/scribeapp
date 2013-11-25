package pl.scribeapp.input;

import android.view.View;

public abstract class InputMethodController {
	public View inputView;
	public ScribeInputService service;
	
	public InputMethodController(ScribeInputService s,int viewId) {
		service = s;
		inputView = service.getLayoutInflater().inflate(viewId, null);
	}
	
	public abstract void resetModifiers();
}

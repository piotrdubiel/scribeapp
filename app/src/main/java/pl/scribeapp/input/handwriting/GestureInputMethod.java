package pl.scribeapp.input.handwriting;

import pl.scribeapp.R;
import pl.scribeapp.classifier.ClassificationResult;
import pl.scribeapp.classifier.Classifier;
import pl.scribeapp.classifier.MetaClassifier;
import pl.scribeapp.classifier.remote.RemoteClassifier;
import pl.scribeapp.input.InputMethodController;
import pl.scribeapp.input.ScribeInputService;
import pl.scribeapp.settings.SettingsActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GestureInputMethod extends InputMethodController implements OnClickListener,
		OnLongClickListener {
	private static final String TAG = "GestureInput";

	ImageButton deleteKey;
	ImageButton enterKey;
	ImageButton spaceKey;
	ToggleButton symbolSwitch;
	KeyboardView supportSymbolKeyboardView;
	GestureOverlayView gestureView;
	ImageButton keyboardSwitch;

	String word_separators;
	String[] input_modes;

	private MetaClassifier classHandler;

	int current_mode;

	private int[] modes = { Classifier.CAPITAL_ALPHA, Classifier.SMALL_ALPHA,
			Classifier.DIGIT };

	int gestureInterval;
	boolean capsLock = false;

	/**
	 * Konstruktor inicjuje elementy widoku i ładuje klasyfikatory. Wymaga
	 * podania klasy ScribeInputService, z którą będzie powiązany.
	 */
	public GestureInputMethod(ScribeInputService s) {
		super(s, R.layout.gesture_input_view);

		deleteKey = (ImageButton) inputView.findViewById(R.id.deleteKey);
		enterKey = (ImageButton) inputView.findViewById(R.id.enterKey);
		spaceKey = (ImageButton) inputView.findViewById(R.id.spaceKey);
		symbolSwitch = (ToggleButton) inputView.findViewById(R.id.symbolSwitch);
		supportSymbolKeyboardView = (KeyboardView) inputView.findViewById(R.id.support_keyboard);
		gestureView = (GestureOverlayView) inputView.findViewById(R.id.gesture_overlay);
		keyboardSwitch = (ImageButton) inputView.findViewById(R.id.keyboardToggle);

		deleteKey.setOnClickListener(this);
		deleteKey.setOnLongClickListener(this);
		enterKey.setOnClickListener(this);
		enterKey.setOnLongClickListener(this);
		spaceKey.setOnClickListener(this);
		symbolSwitch.setOnClickListener(this);
		keyboardSwitch.setOnClickListener(this);

		gestureView.addOnGestureListener(new WordRecognizer(this));
		gestureView.setGestureStrokeLengthThreshold(0.0f);
		gestureView.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);

		supportSymbolKeyboardView.setOnKeyboardActionListener(new SymbolProcessor());
		supportSymbolKeyboardView.setKeyboard(new Keyboard(service, R.xml.symbols));

        word_separators = inputView.getResources().getString(R.string.word_separators);
        input_modes = inputView.getResources().getStringArray(R.array.input_modes);

		// currentType = Classifier.ALPHA;
		current_mode = 0;

		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(service);

		gestureInterval = Integer.parseInt(sharedPrefs.getString("gesture_interval", "300"));
		gestureView.setFadeOffset(gestureInterval);


		Log.d(TAG, "Interval preference: " + String.valueOf(gestureInterval));
	}

	/**
	 * Obsługuje długie wciśnięcie przycisków: - Delete – usunięcie ostatniego
	 * wyrazu - Return – przejście do ustawień
	 * 
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	public boolean onLongClick(View v) {
		if (v.getId() == R.id.enterKey) {
			Intent intent = new Intent(service.getBaseContext(), SettingsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			service.getApplication().startActivity(intent);
		}
		if (v.getId() == R.id.deleteKey) {
			Log.d(TAG, "DELETE LONG CLICK");
			Toast.makeText(service, R.string.remove_word, Toast.LENGTH_SHORT).show();
			service.deleteAfterLongClick();
		}
		return true;
	}

	/**
	 * Obsługuje wciśnięcie przycisków: - zmiany widoku - przycisku Delete -
	 * zmiany typu rozpoznawanych gestów - pokazania/schowania klawiatury z
	 * symbolami - przycisku Spacja - przycisku Return
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		if (v.getId() == R.id.typeSwitch) {
			current_mode++;
			if (current_mode >= modes.length) current_mode = 0;
		}
		if (v.getId() == R.id.deleteKey) {
			Log.d(TAG, "delete key");
			service.delete();
		}
		if (v.getId() == R.id.enterKey) {
			Log.d(TAG, "enter key");
			service.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
			service.refreshSuggestions();
		}
		if (v.getId() == R.id.spaceKey) {
			Log.d(TAG, "space key");
			service.enterCharacter('\u0020');
		}
		if (v.getId() == R.id.symbolSwitch) {
			showSymbols(symbolSwitch.isChecked());
		}
		if (v.getId() == R.id.keyboardToggle) {
			Log.i(TAG, "Input switch requested");
			service.switchInputMethod();
		}
	}

	/**
	 * Pokazuje lub chowa klawiaturę z symbolami
	 * 
	 * @param visible
	 *            true, jeśli klawiatura ma być widoczna, false w przeciwnym
	 *            przypadku
	 */
	private void showSymbols(boolean visible) {
		if (visible) {
			supportSymbolKeyboardView.setVisibility(View.VISIBLE);
			gestureView.setVisibility(View.GONE);
		}
		else {
			supportSymbolKeyboardView.setVisibility(View.GONE);
			gestureView.setVisibility(View.VISIBLE);
		}
		symbolSwitch.setChecked(visible);
	}

    void setButtonsState(boolean state) {
        keyboardSwitch.setEnabled(state);
        symbolSwitch.setEnabled(state);
        deleteKey.setEnabled(state);
        spaceKey.setEnabled(state);
        enterKey.setEnabled(state);
    }

	private class SymbolProcessor implements OnKeyboardActionListener {
		public void onKey(int primaryCode, int[] keyCodes) {
			service.enterCharacter((char) primaryCode);
			showSymbols(false);
		}

		public void onPress(int primaryCode) {}

		public void onRelease(int primaryCode) {}

		public void onText(CharSequence text) {}

		public void swipeDown() {}

		public void swipeLeft() {}

		public void swipeRight() {}

		public void swipeUp() {}
	}

	@Override
	public void resetModifiers() {}
}
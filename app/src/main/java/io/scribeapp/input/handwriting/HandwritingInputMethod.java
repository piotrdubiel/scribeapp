package io.scribeapp.input.handwriting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.GestureOverlayView;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.InjectView;
import io.scribeapp.R;
import io.scribeapp.classifier.ClassificationHandler;
import io.scribeapp.classifier.Classifier;
import io.scribeapp.input.BaseInputMethod;
import io.scribeapp.input.handwriting.state.IdleState;
import io.scribeapp.input.handwriting.state.RecognitionState;
import io.scribeapp.settings.SettingsActivity;
import io.scribeapp.utils.state.StateChanger;

public class HandwritingInputMethod extends BaseInputMethod implements OnClickListener, OnLongClickListener {
    private static final String TAG = "GestureInput";
    final StateChanger<RecognitionState> stateChanger;

    @InjectView(R.id.deleteKey)
    ImageButton deleteKey;
    ImageButton enterKey;
    ImageButton spaceKey;
    KeyboardView supportSymbolKeyboardView;
    GestureOverlayView gestureView;
    ImageButton keyboardSwitch;

    String word_separators;
    String[] input_modes;

    int current_mode;

    private int[] modes = {Classifier.CAPITAL_ALPHA, Classifier.SMALL_ALPHA, Classifier.DIGIT};

    int gestureInterval;
    boolean capsLock = false;

    @Inject
    public ClassificationHandler classificationHandler;

    @Inject
    public HandwritingInputMethod(Context context) {
        super(context, R.layout.gesture_input_view);
        current_mode = 0;

        word_separators = context.getResources().getString(R.string.word_separators);
        input_modes = context.getResources().getStringArray(R.array.input_modes);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        gestureInterval = Integer.parseInt(sharedPrefs.getString("gesture_interval", "300"));
        Log.d(TAG, "Interval preference: " + String.valueOf(gestureInterval));
        stateChanger = new StateChanger<RecognitionState>(service);
        stateChanger.setState(new IdleState());
    }

    @Override
    public void onCreateView() {
        super.onCreateView();
        enterKey = (ImageButton) inputView.findViewById(R.id.enterKey);
        spaceKey = (ImageButton) inputView.findViewById(R.id.spaceKey);
        supportSymbolKeyboardView = (KeyboardView) inputView.findViewById(R.id.support_keyboard);
        gestureView = (GestureOverlayView) inputView.findViewById(R.id.gesture_overlay);
        keyboardSwitch = (ImageButton) inputView.findViewById(R.id.keyboardToggle);

        deleteKey.setOnClickListener(this);
        deleteKey.setOnLongClickListener(this);
        enterKey.setOnClickListener(this);
        enterKey.setOnLongClickListener(this);
        spaceKey.setOnClickListener(this);
        keyboardSwitch.setOnClickListener(this);

        gestureView.addOnGestureListener(new WordRecognizer(this));
        gestureView.setGestureStrokeLengthThreshold(0.0f);
        gestureView.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
        gestureView.setFadeOffset(gestureInterval);

        supportSymbolKeyboardView.setOnKeyboardActionListener(new SymbolProcessor());
        supportSymbolKeyboardView.setKeyboard(new Keyboard(service, R.xml.symbols));
    }

    /**
     * Obsługuje długie wciśnięcie przycisków:
     * - Delete – usunięcie ostatniego wyrazu
     * - Return – przejście do ustawień
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

    public void onClick(View v) {
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
        if (v.getId() == R.id.keyboardToggle) {
            Log.i(TAG, "Input switch requested");
            service.switchInputMethod();
        }
    }

    private void showSymbols(boolean visible) {
        if (visible) {
            supportSymbolKeyboardView.setVisibility(View.VISIBLE);
            gestureView.setVisibility(View.GONE);
        } else {
            supportSymbolKeyboardView.setVisibility(View.GONE);
            gestureView.setVisibility(View.VISIBLE);
        }
    }

    void setButtonsState(boolean state) {
        keyboardSwitch.setEnabled(state);
        deleteKey.setEnabled(state);
        spaceKey.setEnabled(state);
        enterKey.setEnabled(state);
    }

    private class SymbolProcessor implements OnKeyboardActionListener {
        public void onKey(int primaryCode, int[] keyCodes) {
            service.enterCharacter((char) primaryCode);
            showSymbols(false);
        }

        public void onPress(int primaryCode) {
        }

        public void onRelease(int primaryCode) {
        }

        public void onText(CharSequence text) {
        }

        public void swipeDown() {
        }

        public void swipeLeft() {
        }

        public void swipeRight() {
        }

        public void swipeUp() {
        }
    }

    @Override
    public void resetModifiers() {
    }

    @Override
    public void enterWord(CharSequence word) {
        service.enterWord(word);
    }
}
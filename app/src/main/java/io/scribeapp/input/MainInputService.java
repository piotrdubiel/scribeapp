package io.scribeapp.input;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import java.util.Arrays;
import java.util.List;

import io.scribeapp.input.dictionary.TrigramDatabase;
import io.scribeapp.input.handwriting.HandwritingInputMethod;
import io.scribeapp.input.keyboard.KeyboardInputMethod;
import io.scribeapp.input.suggestions.SuggestionView;
import io.scribeapp.utils.U;
import io.scribeapp.utils.inject.RegistrableInputMethodService;

public class MainInputService extends RegistrableInputMethodService implements OnSharedPreferenceChangeListener {
	private static final String TAG = "MainInputService";
    private Composer composer;

    SuggestionView suggestionView;

	private BaseInputMethod currentInputMethod;

    public HandwritingInputMethod handwritingInputMethod;
    public KeyboardInputMethod keyboardInputMethod;

	private boolean vibrateOn;

    private boolean trigramsOn;
	//private TrigramDatabase trigram_database;

    protected List<Object> getModules() {
        return U.NO_MODULES;
    }

    /**
	 * Ładuje ustawienia i inicjuje pomocnicze narzędzia: słownik, bazę trigramów.
	 * @see android.inputmethodservice.InputMethodService#onCreate()
	 */
	@Override
	public void onCreate() {
        super.onCreate();
        Log.d("Modules", Arrays.toString(getModules().toArray()));
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        loadPreferences();
        //trigram_database = new TrigramDatabase(this);
        composer = new Composer(this);

        handwritingInputMethod = new HandwritingInputMethod(this);
        keyboardInputMethod = new KeyboardInputMethod(this);
	}

	/**
	 * Tworzy widoki trybów wpisywania tekstu.
	 * @see android.inputmethodservice.InputMethodService#onCreateInputView()
	 */
	@Override
	public View onCreateInputView() {
        super.onCreateInputView();
        handwritingInputMethod.onCreateView();
        keyboardInputMethod.onCreateView();
		currentInputMethod = handwritingInputMethod;
		return currentInputMethod.inputView;
	}

    /**
	 * Metoda jest wywoływana przy rozpoczęciu wprowadzania  tekstu do innego pola.
	 * Wykrywa typ pola i wyłącza sugestie dla pól, które oczekują na wpisanie hasła,  adresu e-mail czy adresu URL lub zażądały wyłączenia sugestii.
	 * @see android.inputmethodservice.InputMethodService#onStartInput(android.view.inputmethod.EditorInfo, boolean)
	 */
	@Override
	public void onStartInput(EditorInfo info, boolean restarting) {
		super.onStartInput(info, restarting);
        composer.onStartInput(info);
	}

	@Override
	public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd,
			int candidatesStart, int candidatesEnd) {
		super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
        // TODO
//		if (composing_text.length() > 0 && (newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
//			composing_text.setLength(0);
//            composer.refreshSuggestions();
//			InputConnection ic = getCurrentInputConnection();
//			if (ic != null) {
//				ic.finishComposingText();
//			}
//		}
	}

	/**
	 * Tworzy widok z listą sugestii
	 * @see android.inputmethodservice.InputMethodService#onCreateCandidatesView()
	 */
	@Override
	public View onCreateCandidatesView() {
		suggestionView = new SuggestionView(this);
		suggestionView.setService(this);
		return suggestionView;
	}

	/**
	 * Metoda jest wywoływana przy zakończeniu wprowadzania tekstu do pola.
	 * Resetuje ona stan aplikacji, aby przygotować ją na przejście do następnego pola.
	 * @see android.inputmethodservice.InputMethodService#onFinishInput()
	 */
	@Override
	public void onFinishInput() {
		super.onFinishInput();
        // TODO
		//composing_text.setLength(0);
        composer.refreshSuggestions();
		setCandidatesViewShown(false);
	}

    public void enterWord(CharSequence word) {
        composer.enterWord(word);
    }

	/**
	 * Wprowadza podaną literę do aktualnie komponowanego tekstu.
	 * Jeśli znak jest separatorem słowa, to wywołuje metodę commitText.
	 * @param c znak, który ma zostać wpisany
	 */
	public void enterCharacter(Character c) {

        // recentLabel.setText(c.toString());
        composer.enterCharacter(c);
    }

	@Override
	public void onDestroy() {
		Log.i(TAG, "Destroy called - closing databases");
		// TODO
		//suggest.close();
		//trigram_database.close();
		super.onDestroy();
	}

	public void delete() {
        composer.delete();
    }

	public void deleteAfterLongClick() {
        composer.deleteAfterLongClick();
    }

	public void pickSuggestion(String word) {
        composer.pickSuggestion(word);
    }

	public void refreshSuggestions() {
        composer.refreshSuggestions();
    }

	public void switchInputMethod() {
		if (currentInputMethod == handwritingInputMethod) currentInputMethod = keyboardInputMethod;
		else if (currentInputMethod == keyboardInputMethod) currentInputMethod = handwritingInputMethod;
		setInputView(currentInputMethod.inputView);
		currentInputMethod.resetModifiers();
	}

	private void loadPreferences() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // TODO
//		completion_settings = sharedPrefs.getBoolean("use_dictionary", true);
//		Log.d(TAG, "Completion: " + String.valueOf(completion_settings));
//
//		if (completion_settings) {
//			suggest = new SuggestionManager(this);
//		}
//		else {
//			suggest = null;
//		}

		vibrateOn = sharedPrefs.getBoolean("vibrate_on", true);
		trigramsOn = sharedPrefs.getBoolean("use_trigrams", true);
		Log.d(TAG, "Vibration: " + String.valueOf(vibrateOn));
		Log.d(TAG, "Trigrams: " + String.valueOf(trigramsOn));
	}

	public void vibrate() {
		if (vibrateOn) {
			currentInputMethod.inputView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
		}
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		loadPreferences();
	}

    public SuggestionView getSuggestionView() {
        return suggestionView;
    }
}

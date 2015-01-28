package io.scribe.input;

import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import io.scribe.R;
import io.scribe.input.dictionary.SuggestionManager;

public class Composer {
    private static final String TAG = "Composer";
    private final MainInputService mainInputService;
    @Inject
    SuggestionManager suggestionManager;
    StringBuilder composingText = new StringBuilder();

    String wordSeparators;

    private boolean useDictionary;
    private boolean completionOn;

    public Composer(MainInputService mainInputService) {
        this.mainInputService = mainInputService;
        wordSeparators = mainInputService.getResources().getString(R.string.word_separators);
    }

    /**
     * Wprowadza aktualnie komponowany tekst do pola i odświeża listę sugestii.
     */
    public void commitText() {
        InputConnection ic = mainInputService.getCurrentInputConnection();
        if (composingText.length() > 0) {
            ic.commitText(composingText, composingText.length());
            if (suggestionManager != null && suggestionManager.isReady()) {
                Log.i(TAG, "Word " + composingText + " is valid: " + suggestionManager.isValid(composingText.toString()));
                if (suggestionManager.isValid(composingText.toString())) {
                    suggestionManager.addToDictionary(composingText.toString());
                }
            }
            composingText.setLength(0);
            refreshSuggestions();
        }
    }

    public void onStartInput(EditorInfo info) {
        completionOn = true;
        switch (info.inputType & EditorInfo.TYPE_MASK_CLASS) {
            case EditorInfo.TYPE_CLASS_TEXT:
                int variation = info.inputType & EditorInfo.TYPE_MASK_VARIATION;
                if (variation == EditorInfo.TYPE_TEXT_VARIATION_PASSWORD || variation == EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    completionOn = false;
                }

                if (variation == EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS || variation == EditorInfo.TYPE_TEXT_VARIATION_URI || variation == EditorInfo.TYPE_TEXT_VARIATION_FILTER) {
                    completionOn = false;
                }

                if ((info.inputType & EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE) != 0) {
                    completionOn = false;
                }
                break;
        }
        composingText.setLength(0);
        refreshSuggestions();

        Log.d(TAG, "Completion: " + String.valueOf(useDictionary && completionOn));
    }

    public void enterWord(CharSequence word) {
        if (word == null) return;
        InputConnection ic = mainInputService.getCurrentInputConnection();
        ic.commitText(word, word.length());
        composingText.setLength(0);
        refreshSuggestions();
    }

    /**
     * Wprowadza podaną literę do aktualnie komponowanego tekstu.
     * Jeśli znak jest separatorem słowa, to wywołuje metodę commitText.
     *
     * @param c znak, który ma zostać wpisany
     */
    public void enterCharacter(Character c) {
        if (c == null) return;

        InputConnection ic = mainInputService.getCurrentInputConnection();

        composingText.append(c);
        if (!wordSeparators.contains(c.toString())) {
            ic.setComposingText(composingText, composingText.length());
        } else {
            commitText();
            // ic.commitText(c.toString(), 1);
        }

        // recentLabel.setText(c.toString());
        mainInputService.vibrate();
        refreshSuggestions();
    }

    public void delete() {
        Log.d(TAG, "delete");
        InputConnection ic = mainInputService.getCurrentInputConnection();
        if (composingText.length() > 0) {
            composingText.deleteCharAt(composingText.length() - 1);
            ic.setComposingText(composingText, composingText.length());
        } else {
            ic.deleteSurroundingText(1, 0);
        }
        refreshSuggestions();
    }

    public void deleteAfterLongClick() {
        InputConnection ic = mainInputService.getCurrentInputConnection();
        if (composingText.length() > 0) {
            composingText.setLength(0);
            ic.setComposingText(composingText, composingText.length());
        } else {
            String text = mainInputService.getCurrentInputConnection().getExtractedText(new ExtractedTextRequest(), 0).text.toString();
            int n = 1;
            if (text.length() > 0) {
                if (wordSeparators.contains(Character.toString(text.charAt(text.length() - 1)))) {
                    while (n < text.length() && wordSeparators.contains(Character.toString(text.charAt(text.length() - n - 1))))
                        n++;
                } else {
                    while (n < text.length() && !wordSeparators.contains(Character.toString(text.charAt(text.length() - n - 1))))
                        n++;
                }
                Log.v(TAG, "To delete - " + mainInputService.getCurrentInputConnection().getTextBeforeCursor(n, 0));
                mainInputService.getCurrentInputConnection().deleteSurroundingText(n, 0);
            }
        }
        refreshSuggestions();
    }

    public void pickSuggestion(String word) {
        if (completionOn) {
            mainInputService.getCurrentInputConnection().commitText(word, word.length());
            if (!suggestionManager.isValid(word)) {
                suggestionManager.addToUserDictionary(word);
                Toast.makeText(mainInputService, "Added " + word + " to dictionary", Toast.LENGTH_SHORT).show();
            }
            mainInputService.setCandidatesViewShown(false);
        }
    }

    public void refreshSuggestions() {
        Log.i(TAG, "REFRESH Suggestions " + String.valueOf(suggestionManager != null ? suggestionManager.isReady() : false));
        if (completionOn && suggestionManager != null && suggestionManager.isReady()) {
            // if (getCurrentInputConnection().getExtractedText(
            // new ExtractedTextRequest(), 0) == null) return;
            // String text = getCurrentInputConnection().getExtractedText(
            // new ExtractedTextRequest(), 0).text.toString();
            // int n = 1;
            // if (text.length() > 0) {
            // if (!wordSeparators.contains(Character.toString(text
            // .charAt(text.length() - 1)))) {
            // while (n < text.length()
            // && !wordSeparators.contains(Character.toString(text
            // .charAt(text.length() - n - 1))))
            // n++;
            // String word = (String) getCurrentInputConnection()
            // .getTextBeforeCursor(n, 0);
            // lastWordStart = n;
            // Log.d(TAG,
            // "Word is valid: "
            // + String.valueOf(suggest.isValid(word)));
            // if (word.length() > 1) {
            // List<String> suggestions = suggest.getSuggestions(word);
            // candidateView.setSuggestions(suggestions,
            // suggest.isValid(word));
            // setCandidatesViewShown(true);
            // return;
            // }
            // }
            // }
            if (composingText.length() > 0) {
                String word = composingText.toString();
                List<String> suggestions = suggestionManager.getSuggestions(word);
                mainInputService.getSuggestionView().setSuggestions(suggestions, suggestionManager.isValid(word));
                mainInputService.setCandidatesViewShown(true);

            } else mainInputService.setCandidatesViewShown(false);
        } else mainInputService.setCandidatesViewShown(false);
    }

    public boolean isCompletionOn() {
        return completionOn;
    }

    public void setCompletionOn(boolean completionOn) {
        this.completionOn = completionOn;
    }
}
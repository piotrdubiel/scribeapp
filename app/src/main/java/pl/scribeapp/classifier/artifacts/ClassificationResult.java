package pl.scribeapp.classifier.artifacts;

import java.util.List;

/**
 * Created by piotrekd on 12/29/13.
 */
public class ClassificationResult {
    private String word;

    public ClassificationResult() {}

    public ClassificationResult(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }
}
